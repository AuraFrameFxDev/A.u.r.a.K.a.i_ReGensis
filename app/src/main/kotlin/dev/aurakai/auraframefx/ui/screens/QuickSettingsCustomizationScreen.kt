
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
import dev.aurakai.auraframefx.aura.ui.ImageResourceManager
import dev.aurakai.auraframefx.aura.ui.components.ImagePicker
import dev.aurakai.auraframefx.aura.ui.components.ImageCropOverlay
import dev.aurakai.auraframefx.aura.ui.components.ImageTransformationPanel
import dev.aurakai.auraframefx.aura.ui.components.TransformedImage # Import TransformedImage
import dev.aurakai.auraframefx.domains.aura.lab.CustomizationPreferences
import dev.aurakai.auraframefx.ui.system.toBlendMode # Import the extension function

private const val TAG = "QSCustomizationScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickSettingsCustomizationScreen(navController: NavController) {
    val context = LocalContext.current

    var customQsBackgroundEnabled by remember {
        mutableStateOf(CustomizationPreferences.getCustomQsBackgroundEnabled(context))
    }
    var customQsBackgroundUri by remember {
        mutableStateOf<Uri?>(null) # Initialized to null for better control
    }
    var customQsBackgroundTransformation by remember { mutableStateOf(ImageTransformation()) }
    var customQsBackgroundOpacity by remember {
        mutableStateOf(CustomizationPreferences.getCustomQsBackgroundOpacity(context))
    }
    var customQsBackgroundBlendMode by remember {
        mutableStateOf(CustomizationPreferences.getCustomQsBackgroundBlendMode(context))
    }

    var showImagePicker by remember { mutableStateOf(false) }
    var showTransformationPanel by remember { mutableStateOf(false) }
    var expandedBlendMode by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        customQsBackgroundEnabled = CustomizationPreferences.getCustomQsBackgroundEnabled(context)
        val (uri, transformation) = CustomizationPreferences.getImageWithTransformation(context, "quick_settings_background")
        customQsBackgroundUri = uri
        customQsBackgroundTransformation = transformation ?: ImageTransformation()
        customQsBackgroundOpacity = CustomizationPreferences.getCustomQsBackgroundOpacity(context)
        customQsBackgroundBlendMode = CustomizationPreferences.getCustomQsBackgroundBlendMode(context)
    }

    val onSavePreferences: () -> Unit = {
        CustomizationPreferences.saveCustomQsBackgroundSettings(
            context,
            customQsBackgroundEnabled,
            customQsBackgroundUri,
            customQsBackgroundOpacity,
            customQsBackgroundBlendMode
        )
        CustomizationPreferences.saveImageWithTransformation(context, "quick_settings_background", customQsBackgroundUri, customQsBackgroundTransformation)
        println("Quick Settings Background preferences saved.")
    }

    val onImageSelected: (Uri?, ImageTransformation) -> Unit = { uri, transformation ->
        showImagePicker = false
        customQsBackgroundUri = uri
        customQsBackgroundTransformation = transformation
        onSavePreferences() # Save immediately after image selection/transformation
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Quick Settings Customization") })
        }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            # Enable/Disable Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Enable Custom QS Background", style = MaterialTheme.typography.titleMedium)
                Switch(
                    checked = customQsBackgroundEnabled,
                    onCheckedChange = {
                        customQsBackgroundEnabled = it
                        onSavePreferences()
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            # Image Selection
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Button(onClick = { showImagePicker = true }, enabled = customQsBackgroundEnabled) {
                    Text("Select Background Image")
                }
                if (customQsBackgroundUri != null) {
                    Button(onClick = { showTransformationPanel = true }, enabled = customQsBackgroundEnabled) {
                        Text("Transform")
                    }
                    Button(onClick = {
                        ImageResourceManager.removeImageUsage(context, "quick_settings_background")
                        customQsBackgroundUri = null
                        customQsBackgroundTransformation = ImageTransformation()
                        onSavePreferences() # Save to clear preferences
                    }, enabled = customQsBackgroundEnabled) {
                        Text("Remove")
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            # Image Preview
            if (customQsBackgroundUri != null && customQsBackgroundEnabled) {
                Text("Selected Image Preview:")
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp) # Example preview height
                    .padding(vertical = 8.dp)
                ) {
                    TransformedImage(
                        uri = customQsBackgroundUri,
                        transformation = customQsBackgroundTransformation,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "Quick Settings Background Preview"
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            # Opacity Slider
            Text("Background Opacity: ${(customQsBackgroundOpacity * 100).toInt()}%", style = MaterialTheme.typography.titleMedium)
            Slider(
                value = customQsBackgroundOpacity,
                onValueChange = { newValue ->
                    customQsBackgroundOpacity = newValue
                    onSavePreferences()
                },
                valueRange = 0f..1f,
                steps = 99, # 0.01 increments
                enabled = customQsBackgroundEnabled
            )
            Spacer(modifier = Modifier.height(16.dp))

            # Blend Mode Selection
            Text("Blend Mode", style = MaterialTheme.typography.titleMedium)
            ExposedDropdownMenuBox(
                expanded = expandedBlendMode,
                onExpandedChange = { expandedBlendMode = !expandedBlendMode },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                enabled = customQsBackgroundEnabled
            ) {
                TextField(
                    value = customQsBackgroundBlendMode,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Blend Mode") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedBlendMode) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedBlendMode,
                    onDismissRequest = { expandedBlendMode = false }
                ) {
                    val blendModes = listOf("SrcOver", "SrcIn", "SrcOut", "SrcAtop", "DstOver", "DstIn", "DstOut", "DstAtop", "Xor", "Multiply", "Screen", "Darken", "Lighten", "Overlay", "Add", "Clear", "ColorDodge", "ColorBurn", "Hardlight", "Softlight", "Difference", "Modulate", "Saturation", "Color", "Luminosity")
                    blendModes.forEach { selectionOption ->
                        DropdownMenuItem(text = { Text(selectionOption) }, onClick = {
                            customQsBackgroundBlendMode = selectionOption
                            expandedBlendMode = false
                            onSavePreferences()
                        })
                    }
                }
            }
        }
    }

    if (showImagePicker) {
        Dialog(onDismissRequest = { showImagePicker = false }) {
            Surface(modifier = Modifier.fillMaxSize()) {
                ImagePicker(onImageSelected = onImageSelected)
            }
        }
    }

    # Transformation Dialog
    if (showTransformationPanel && customQsBackgroundUri != null) {
        Dialog(onDismissRequest = { showTransformationPanel = false }) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text("Adjust Quick Settings Image", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))
                    # Preview of the image being transformed
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()) {
                        TransformedImage(
                            uri = customQsBackgroundUri,
                            transformation = customQsBackgroundTransformation,
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = "Quick Settings Image for Transformation",
                            contentScale = ContentScale.Fit
                        )
                        ImageCropOverlay(
                            imageTransformation = customQsBackgroundTransformation,
                            onTransformationChange = { customQsBackgroundTransformation = it }
                        )
                    }
                    ImageTransformationPanel(
                        currentTransformation = customQsBackgroundTransformation,
                        onTransformationChange = { newTrans ->
                            customQsBackgroundTransformation = newTrans
                            onSavePreferences() # Save preferences as transformation changes
                        }
                    )
                    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceAround) {
                        Button(onClick = { showTransformationPanel = false }) { Text("Cancel") }
                        Button(onClick = {
                            showTransformationPanel = false
                            # Re-save to ensure latest transformation is persisted
                            onSavePreferences()
                        }) { Text("Apply") }
                    }
                }
            }
        }
    }
}
