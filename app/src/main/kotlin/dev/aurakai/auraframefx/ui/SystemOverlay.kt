
package dev.aurakai.auraframefx.ui

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog // Import for Dialog
import dev.aurakai.auraframefx.domains.aura.lab.ImageTransformation
import dev.aurakai.auraframefx.domains.aura.ui.ImageResourceManager
import dev.aurakai.auraframefx.domains.aura.ui.components.ImagePicker
import dev.aurakai.auraframefx.domains.aura.ui.components.ImageTransformationPanel
import dev.aurakai.auraframefx.domains.aura.ui.components.TransformedImage
import dev.aurakai.auraframefx.domains.aura.ui.components.ImageCropOverlay
import dev.aurakai.auraframefx.domains.aura.lab.CustomizationPreferences
import androidx.compose.material3.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.TextField
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Divider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemOverlay() {
    val context = LocalContext.current
    var showImagePicker by remember { mutableStateOf(false) }
    var showHeaderTransformationPanel by remember { mutableStateOf(false) } // New state for transformation dialog

    // State to hold the current header image URI, transformation, and scale
    var headerImageUri by remember { mutableStateOf<Uri?>(null) }
    var headerImageTransformation by remember { mutableStateOf(ImageTransformation()) }
    var headerImageScale by remember { mutableStateOf(ContentScale.Crop) }

    // Load preferences on launch
    LaunchedEffect(Unit) {
        headerImageScale = CustomizationPreferences.getHeaderImageScale(context)
        val (uri, transformation) = CustomizationPreferences.getImageWithTransformation(context, "header_image")
        headerImageUri = uri
        headerImageTransformation = transformation ?: ImageTransformation()
    }

    // Updated onImageSelected callback to handle ImageTransformation
    val onImageSelected: (Uri?, ImageTransformation) -> Unit = { uri, transformation ->
        showImagePicker = false
        headerImageUri = uri
        headerImageTransformation = transformation
        // Save both URI and transformation to preferences
        CustomizationPreferences.saveImageWithTransformation(context, "header_image", uri, transformation)
        // Also save the scale if it's managed separately (for now, default Crop)
        CustomizationPreferences.saveHeaderImage(context, uri, headerImageScale) // This will update URI and scale
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Placeholder Header Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Example fixed height for header
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            if (headerImageUri != null) {
                TransformedImage(
                    uri = headerImageUri,
                    transformation = headerImageTransformation,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "Custom Header Background",
                    contentScale = headerImageScale
                )
            } else {
                Text(
                    text = "System Header (No Custom Image)",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Controls for Header Customization
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Header Customization",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Button(onClick = { showImagePicker = true }) {
                    Text("Select Image")
                }

                if (headerImageUri != null) {
                    Button(onClick = { showHeaderTransformationPanel = true }) {
                        Text("Transform")
                    }
                    // Placeholder for ContentScale selection
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = headerImageScale.javaClass.simpleName,
                            onValueChange = {}, // Read-only
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
                                    headerImageScale = scale
                                    expanded = false
                                    CustomizationPreferences.saveHeaderImage(context, headerImageUri, headerImageScale)
                                })
                            }
                        }
                    }
                    Button(onClick = {
                        ImageResourceManager.removeImageUsage(context, "header_image")
                        headerImageUri = null
                        headerImageTransformation = ImageTransformation()
                        CustomizationPreferences.clearImageWithTransformation(context, "header_image") // Clear transformation data
                        CustomizationPreferences.saveHeaderImage(context, null, ContentScale.Crop) // Reset scale preference
                    }) {
                        Text("Remove")
                    }
                }
            }
        }
    }

    if (showImagePicker) {
        // Display ImagePicker in a dialog
        Dialog(onDismissRequest = { showImagePicker = false }) {
            Surface(modifier = Modifier.fillMaxSize()) {
                ImagePicker(onImageSelected = onImageSelected)
            }
        }
    }

    // Image Transformation Dialog
    if (showHeaderTransformationPanel && headerImageUri != null) {
        Dialog(onDismissRequest = { showHeaderTransformationPanel = false }) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text("Adjust Header Image", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))
                    // Preview of the image being transformed
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()) {
                        TransformedImage(
                            uri = headerImageUri,
                            transformation = headerImageTransformation,
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = "Header Image for Transformation",
                            contentScale = ContentScale.Fit // Show full image for transformation
                        )
                        ImageCropOverlay(
                            imageTransformation = headerImageTransformation,
                            onTransformationChange = { headerImageTransformation = it }
                        )
                    }

                    // Transformation controls
                    ImageTransformationPanel(
                        currentTransformation = headerImageTransformation,
                        onTransformationChange = { newTrans ->
                            headerImageTransformation = newTrans
                            // Persist changes immediately on slider/switch interaction
                            CustomizationPreferences.saveImageWithTransformation(context, "header_image", headerImageUri, newTrans)
                        }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(onClick = { showHeaderTransformationPanel = false }) {
                            Text("Cancel")
                        }
                        Button(onClick = {
                            showHeaderTransformationPanel = false
                            // Save final transformation (already saved on change, but confirm)
                            CustomizationPreferences.saveImageWithTransformation(context, "header_image", headerImageUri, headerImageTransformation)
                        }) {
                            Text("Apply")
                        }
                    }
                }
            }
        }
    }
}
