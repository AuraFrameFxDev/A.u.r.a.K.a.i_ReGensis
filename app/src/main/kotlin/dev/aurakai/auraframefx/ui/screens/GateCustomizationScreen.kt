package dev.aurakai.auraframefx.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import dev.aurakai.auraframefx.domains.aura.lab.CustomizationPreferences

/**
 * GateCustomizationScreen allows users to customize navigation drawer and splash screen images.
 *
 * Note: Image transformation, cropping, and dynamic spacing systems have been discontinued.
 * Current implementation supports basic persistence of URI and scale for select backgrounds.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GateCustomizationScreen(navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Navigation Drawer Background Image States
    var navDrawerBgUri by remember { mutableStateOf<Uri?>(null) }
    var navDrawerBgScale by remember { mutableStateOf(ContentScale.Crop) }

    // Splash Screen Image States
    var splashScreenUri by remember { mutableStateOf<Uri?>(null) }
    var splashScreenScale by remember { mutableStateOf(ContentScale.Crop) }

    // Load preferences on launch
    LaunchedEffect(Unit) {
        navDrawerBgUri = CustomizationPreferences.getNavDrawerBackgroundUri(context)
        navDrawerBgScale = CustomizationPreferences.getNavDrawerBackgroundScale(context)
        
        splashScreenUri = CustomizationPreferences.getSplashScreenImageUri(context)
        splashScreenScale = CustomizationPreferences.getSplashScreenImageScale(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Gate Customization") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Text("Gate Appearance Settings", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))

            Text("The advanced image transformation and dynamic spacing systems have been removed to streamline the core experience.")

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // Navigation Drawer Background Section
            Text("Navigation Drawer Background", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { /* TODO: Launch system image picker */ }) {
                    Text("Select Nav Drawer Image")
                }
                
                if (navDrawerBgUri != null) {
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = navDrawerBgScale.toString(),
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
                            listOf(
                                ContentScale.Crop,
                                ContentScale.Fit,
                                ContentScale.FillBounds,
                                ContentScale.Inside
                            ).forEach { scale ->
                                DropdownMenuItem(text = { Text(scale.toString()) }, onClick = {
                                    navDrawerBgScale = scale
                                    expanded = false
                                    CustomizationPreferences.saveNavDrawerBackground(context, navDrawerBgUri, navDrawerBgScale)
                                })
                            }
                        }
                    }
                    Button(onClick = {
                        navDrawerBgUri = null
                        CustomizationPreferences.saveNavDrawerBackground(context, null, ContentScale.Crop)
                    }) {
                        Text("Remove")
                    }
                }
            }
            navDrawerBgUri?.let {
                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    model = it,
                    contentDescription = "Navigation Drawer Background",
                    modifier = Modifier.size(100.dp).clip(MaterialTheme.shapes.small),
                    contentScale = navDrawerBgScale
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))

            // Splash Screen Image Section
            Text("Splash Screen Image", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { /* TODO: Launch system image picker */ }) {
                    Text("Select Splash Screen Image")
                }
                
                if (splashScreenUri != null) {
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = splashScreenScale.toString(),
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
                            listOf(
                                ContentScale.Crop,
                                ContentScale.Fit,
                                ContentScale.FillBounds,
                                ContentScale.Inside
                            ).forEach { scale ->
                                DropdownMenuItem(text = { Text(scale.toString()) }, onClick = {
                                    splashScreenScale = scale
                                    expanded = false
                                    CustomizationPreferences.saveSplashScreenImage(context, splashScreenUri, splashScreenScale)
                                })
                            }
                        }
                    }
                    Button(onClick = {
                        splashScreenUri = null
                        CustomizationPreferences.saveSplashScreenImage(context, null, ContentScale.Crop)
                    }) {
                        Text("Remove")
                    }
                }
            }
            splashScreenUri?.let {
                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    model = it,
                    contentDescription = "Splash Screen Image",
                    modifier = Modifier.size(100.dp).clip(MaterialTheme.shapes.small),
                    contentScale = splashScreenScale
                )
            }

            Spacer(Modifier.height(32.dp))
            
            Text(
                text = "Note: Development focus has shifted to core AI integration and performance. Background customization is now limited to standard image scaling.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
