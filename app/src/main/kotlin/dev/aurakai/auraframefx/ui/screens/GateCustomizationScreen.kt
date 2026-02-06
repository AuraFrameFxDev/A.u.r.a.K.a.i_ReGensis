
package dev.aurakai.auraframefx.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import dev.aurakai.auraframefx.aura.lab.ImageTransformation
import dev.aurakai.auraframefx.aura.lab.SpacingConfig
import dev.aurakai.auraframefx.aura.ui.components.ImagePicker
import dev.aurakai.auraframefx.aura.ui.components.ImageTransformationPanel
import dev.aurakai.auraframefx.aura.ui.components.SpacingControlPanel
import dev.aurakai.auraframefx.domains.aura.lab.CustomizationPreferences
import dev.aurakai.auraframefx.aura.ui.ImageResourceManager // Used for potential cleanup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GateCustomizationScreen(navController: NavController) {
    val context = LocalContext.current

    // Spacing Config (existing)

    // Navigation Drawer Background Image States
    var navDrawerBgUri by remember { mutableStateOf<Uri?>(null) }
    var navDrawerBgTransformation by remember { mutableStateOf(ImageTransformation()) }
    var navDrawerBgScale by remember { mutableStateOf(ContentScale.Crop) }
    var showNavDrawerImagePicker by remember { mutableStateOf(false) }
    var showNavDrawerTransformationPanel by remember { mutableStateOf(false) }

    // Splash Screen Image States
    var splashScreenUri by remember { mutableStateOf<Uri?>(null) }
    var splashScreenTransformation by remember { mutableStateOf(ImageTransformation()) }
    var splashScreenScale by remember { mutableStateOf(ContentScale.Crop) }
    var showSplashScreenImagePicker by remember { mutableStateOf(false) }
    var showSplashScreenTransformationPanel by remember { mutableStateOf(false) }

    // Load preferences on launch
    LaunchedEffect(Unit) {
        navDrawerBgUri = CustomizationPreferences.getNavDrawerBackgroundUri(context)
        navDrawerBgScale = CustomizationPreferences.getNavDrawerBackgroundScale(context)
        val (uriNav, transNav) = CustomizationPreferences.getImageWithTransformation(context, "nav_drawer_background")
        navDrawerBgUri = uriNav
        navDrawerBgTransformation = transNav ?: ImageTransformation()

        splashScreenUri = CustomizationPreferences.getSplashScreenImageUri(context)
        splashScreenScale = CustomizationPreferences.getSplashScreenImageScale(context)
        val (uriSplash, transSplash) = CustomizationPreferences.getImageWithTransformation(context, "splash_screen_image")
        splashScreenUri = uriSplash
        splashScreenTransformation = transSplash ?: ImageTransformation()
    }

    val onNavDrawerImageSelected: (Uri?, ImageTransformation) -> Unit = { uri, transformation ->
        showNavDrawerImagePicker = false
        navDrawerBgUri = uri
        navDrawerBgTransformation = transformation
        CustomizationPreferences.saveImageWithTransformation(context, "nav_drawer_background", uri, transformation)
        // Save ContentScale explicitly if needed or rely on a default
        CustomizationPreferences.saveNavDrawerBackground(context, uri, navDrawerBgScale) // Existing, update only URI and Scale
    }

    val onSplashScreenImageSelected: (Uri?, ImageTransformation) -> Unit = { uri, transformation ->
        showSplashScreenImagePicker = false
        splashScreenUri = uri
        splashScreenTransformation = transformation
        CustomizationPreferences.saveImageWithTransformation(context, "splash_screen_image", uri, transformation)
        // Save ContentScale explicitly if needed or rely on a default
        CustomizationPreferences.saveSplashScreenImage(context, uri, splashScreenScale) // Existing, update only URI and Scale
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Gate Customization") })
        }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Gate Appearance Settings", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))

            // Placeholder for other gate customization options
            Text("Other customization options for gates go here.")

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            Text("Gate Spacing Controls", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))

            SpacingControlPanel(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                onSpacingConfigChange = { newConfig ->
                    println("Gate Spacing Config changed: $newConfig")
                    // In a real app, save this newConfig
                }
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // Navigation Drawer Background Section
            Text("Navigation Drawer Background", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { showNavDrawerImagePicker = true }) {
                    Text("Select Nav Drawer Image")
                }
                if (navDrawerBgUri != null) {
                    Button(onClick = { showNavDrawerTransformationPanel = true }) {
                        Text("Transform")
                    }
                    // ContentScale selection
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = navDrawerBgScale.javaClass.simpleName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Scale") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            ContentScale.values().forEach { scale ->
                                DropdownMenuItem(text = { Text(scale.javaClass.simpleName) }, onClick = {
                                    navDrawerBgScale = scale
                                    expanded = false
                                    CustomizationPreferences.saveNavDrawerBackground(context, navDrawerBgUri, navDrawerBgScale)
                                })
                            }
                        }
                    }
                    Button(onClick = {
                        ImageResourceManager.removeImageUsage(context, "nav_drawer_background")
                        navDrawerBgUri = null
                        navDrawerBgTransformation = ImageTransformation()
                        CustomizationPreferences.saveNavDrawerBackground(context, null, ContentScale.Crop)
                    }) {
                        Text("Remove")
                    }
                }
            }
            navDrawerBgUri?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Navigation Drawer Background",
                    modifier = Modifier.size(100.dp).clip(MaterialTheme.shapes.small),
                    contentScale = navDrawerBgScale // Use selected scale
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Splash Screen Image Section
            Text("Splash Screen Image", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { showSplashScreenImagePicker = true }) {
                    Text("Select Splash Screen Image")
                }
                if (splashScreenUri != null) {
                    Button(onClick = { showSplashScreenTransformationPanel = true }) {
                        Text("Transform")
                    }
                    // ContentScale selection
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = splashScreenScale.javaClass.simpleName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Scale") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            ContentScale.values().forEach { scale ->
                                DropdownMenuItem(text = { Text(scale.javaClass.simpleName) }, onClick = {
                                    splashScreenScale = scale
                                    expanded = false
                                    CustomizationPreferences.saveSplashScreenImage(context, splashScreenUri, splashScreenScale)
                                })
                            }
                        }
                    }
                    Button(onClick = {
                        ImageResourceManager.removeImageUsage(context, "splash_screen_image")
                        splashScreenUri = null
                        splashScreenTransformation = ImageTransformation()
                        CustomizationPreferences.saveSplashScreenImage(context, null, ContentScale.Crop)
                    }) {
                        Text("Remove")
                    }
                }
            }
            splashScreenUri?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Splash Screen Image",
                    modifier = Modifier.size(100.dp).clip(MaterialTheme.shapes.small),
                    contentScale = splashScreenScale // Use selected scale
                )
            }
        }
    }

    if (showNavDrawerImagePicker) {
        Dialog(onDismissRequest = { showNavDrawerImagePicker = false }) {
            Surface(modifier = Modifier.fillMaxSize()) {
                ImagePicker(onImageSelected = onNavDrawerImageSelected)
            }
        }
    }

    if (showSplashScreenImagePicker) {
        Dialog(onDismissRequest = { showSplashScreenImagePicker = false }) {
            Surface(modifier = Modifier.fillMaxSize()) {
                ImagePicker(onImageSelected = onSplashScreenImageSelected)
            }
        }
    }

    if (showNavDrawerTransformationPanel) {
        Dialog(onDismissRequest = { showNavDrawerTransformationPanel = false }) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Preview of the image being transformed
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()) {
                        navDrawerBgUri?.let { uri ->
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = "Nav Drawer Image for Transformation",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                            ImageCropOverlay(
                                imageTransformation = navDrawerBgTransformation,
                                onTransformationChange = { navDrawerBgTransformation = it }
                            )
                        }
                    }
                    ImageTransformationPanel(
                        currentTransformation = navDrawerBgTransformation,
                        onTransformationChange = { navDrawerBgTransformation = it }
                    )
                    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceAround) {
                        Button(onClick = { showNavDrawerTransformationPanel = false }) { Text("Cancel") }
                        Button(onClick = {
                            showNavDrawerTransformationPanel = false
                            navDrawerBgUri?.let { uri ->
                                ImageResourceManager.copyImageToInternalStorage(context, uri, "nav_drawer_background", navDrawerBgTransformation)
                                CustomizationPreferences.saveImageWithTransformation(context, "nav_drawer_background", uri, navDrawerBgTransformation)
                            }
                        }) { Text("Apply") }
                    }
                }
            }
        }
    }

    if (showSplashScreenTransformationPanel) {
        Dialog(onDismissRequest = { showSplashScreenTransformationPanel = false }) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Preview of the image being transformed
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()) {
                        splashScreenUri?.let { uri ->
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = "Splash Screen Image for Transformation",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                            ImageCropOverlay(
                                imageTransformation = splashScreenTransformation,
                                onTransformationChange = { splashScreenTransformation = it }
                            )
                        }
                    }
                    ImageTransformationPanel(
                        currentTransformation = splashScreenTransformation,
                        onTransformationChange = { splashScreenTransformation = it }
                    )
                    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceAround) {
                        Button(onClick = { showSplashScreenTransformationPanel = false }) { Text("Cancel") }
                        Button(onClick = {
                            showSplashScreenTransformationPanel = false
                            splashScreenUri?.let { uri ->
                                ImageResourceManager.copyImageToInternalStorage(context, uri, "splash_screen_image", splashScreenTransformation)
                                CustomizationPreferences.saveImageWithTransformation(context, "splash_screen_image", uri, splashScreenTransformation)
                            }
                        }) { Text("Apply") }
                    }
                }
            }
        }
    }
}
