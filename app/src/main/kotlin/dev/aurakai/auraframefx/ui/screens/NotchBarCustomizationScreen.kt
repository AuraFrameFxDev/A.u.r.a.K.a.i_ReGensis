
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
import dev.aurakai.auraframefx.aura.ui.components.ImageTransformationPanel
import dev.aurakai.auraframefx.domains.aura.lab.CustomizationPreferences
import dev.aurakai.auraframefx.ui.system.toBlendMode // Import the extension function

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotchBarCustomizationScreen(navController: NavController) {
    val context = LocalContext.current

    var notchBarBgEnabled by remember { mutableStateOf(false) }
    var notchBarBgUri by remember { mutableStateOf<Uri?>(null) }
    var notchBarBgTransformation by remember { mutableStateOf(ImageTransformation()) }
    var notchBarBgOpacity by remember { mutableStateOf(1.0f) }
    var notchBarBgBlendMode by remember { mutableStateOf("SrcOver") }

    var showImagePicker by remember { mutableStateOf(false) }
    var showTransformationPanel by remember { mutableStateOf(false) }
    var expandedBlendMode by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        notchBarBgEnabled = CustomizationPreferences.getNotchBarBackgroundEnabled(context)
        notchBarBgUri = CustomizationPreferences.getNotchBarBackgroundUri(context)
        notchBarBgTransformation = CustomizationPreferences.getNotchBarImageTransformation(context) ?: ImageTransformation()
        notchBarBgOpacity = CustomizationPreferences.getNotchBarBackgroundOpacity(context)
        notchBarBgBlendMode = CustomizationPreferences.getNotchBarBackgroundBlendMode(context)
    }

    val onSavePreferences: () -> Unit = {
        CustomizationPreferences.saveNotchBarBackgroundSettings(
            context,
            notchBarBgEnabled,
            notchBarBgUri,
            notchBarBgTransformation,
            notchBarBgOpacity,
            notchBarBgBlendMode
        )
        println("NotchBar Background preferences saved.")
    }

    val onImageSelected: (Uri?, ImageTransformation) -> Unit = { uri, transformation ->
        showImagePicker = false
        notchBarBgUri = uri
        notchBarBgTransformation = transformation
        onSavePreferences()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("NotchBar Customization") })
        }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Enable/Disable Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Enable Custom NotchBar Background", style = MaterialTheme.typography.titleMedium)
                Switch(
                    checked = notchBarBgEnabled,
                    onCheckedChange = {
                        notchBarBgEnabled = it
                        onSavePreferences()
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Image Selection
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Button(onClick = { showImagePicker = true }, enabled = notchBarBgEnabled) {
                    Text("Select Image")
                }
                if (notchBarBgUri != null) {
                    Button(onClick = { showTransformationPanel = true }, enabled = notchBarBgEnabled) {
                        Text("Transform")
                    }
                    Button(onClick = {
                        ImageResourceManager.removeImageUsage(context, "notch_bar_background")
                        notchBarBgUri = null
                        notchBarBgTransformation = ImageTransformation()
                        onSavePreferences()
                    }, enabled = notchBarBgEnabled) {
                        Text("Remove")
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Image Preview
            if (notchBarBgUri != null && notchBarBgEnabled) {
                Text("Selected Image Preview:")
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp) // Example preview height
                    .padding(vertical = 8.dp)
                ) {
                    TransformedImage(
                        uri = notchBarBgUri,
                        transformation = notchBarBgTransformation,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "NotchBar Background Preview"
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Opacity Slider
            Text("Background Opacity: ${(notchBarBgOpacity * 100).toInt()}%", style = MaterialTheme.typography.titleMedium)
            Slider(
                value = notchBarBgOpacity,
                onValueChange = { newValue ->
                    notchBarBgOpacity = newValue
                    onSavePreferences()
                },
                valueRange = 0f..1f,
                steps = 99, // 0.01 increments
                enabled = notchBarBgEnabled
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Blend Mode Selection
            Text("Blend Mode", style = MaterialTheme.typography.titleMedium)
            ExposedDropdownMenuBox(
                expanded = expandedBlendMode,
                onExpandedChange = { expandedBlendMode = !expandedBlendMode },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                enabled = notchBarBgEnabled
            ) {
                TextField(
                    value = notchBarBgBlendMode,
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
                            notchBarBgBlendMode = selectionOption
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

    if (showTransformationPanel && notchBarBgUri != null) {
        Dialog(onDismissRequest = { showTransformationPanel = false }) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text("Adjust NotchBar Image", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()) {
                        TransformedImage(
                            uri = notchBarBgUri,
                            transformation = notchBarBgTransformation,
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = "NotchBar Image for Transformation",
                            contentScale = ContentScale.Fit
                        )
                        ImageCropOverlay(
                            imageTransformation = notchBarBgTransformation,
                            onTransformationChange = { notchBarBgTransformation = it }
                        )
                    }
                    ImageTransformationPanel(
                        currentTransformation = notchBarBgTransformation,
                        onTransformationChange = { newTrans ->
                            notchBarBgTransformation = newTrans
                            onSavePreferences()
                        }
                    )
                    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceAround) {
                        Button(onClick = { showTransformationPanel = false }) { Text("Cancel") }
                        Button(onClick = {
                            showTransformationPanel = false
                            onSavePreferences()
                        }) { Text("Apply") }
                    }
                }
            }
        }
    }
}
