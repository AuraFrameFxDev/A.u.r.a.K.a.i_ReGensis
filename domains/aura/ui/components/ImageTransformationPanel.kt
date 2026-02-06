
package dev.aurakai.auraframefx.aura.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.* // For Slider, Switch, Button, Text, etc.
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.aura.lab.ImageTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageTransformationPanel(
    modifier: Modifier = Modifier,
    currentTransformation: ImageTransformation = ImageTransformation(),
    onTransformationChange: (ImageTransformation) -> Unit
) {
    var transformation by remember { mutableStateOf(currentTransformation) }

    // Update internal state when external currentTransformation changes
    LaunchedEffect(currentTransformation) {
        transformation = currentTransformation
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Image Transformations", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        // Rotation Slider
        Text("Rotation: ${transformation.rotation.toInt()}°")
        Slider(
            value = transformation.rotation,
            onValueChange = {
                transformation = transformation.copy(rotation = it)
                onTransformationChange(transformation)
            },
            valueRange = 0f..360f,
            steps = 359, // 1 degree increments
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        // Scale Slider
        Text("Scale: ${String.format("%.2f", transformation.scale)}x")
        Slider(
            value = transformation.scale,
            onValueChange = {
                transformation = transformation.copy(scale = it)
                onTransformationChange(transformation)
            },
            valueRange = 0.5f..2.0f,
            steps = 150, // 0.01 increments
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        // Flip Horizontal Switch
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Flip Horizontal")
            Switch(
                checked = transformation.flipHorizontal,
                onCheckedChange = {
                    transformation = transformation.copy(flipHorizontal = it)
                    onTransformationChange(transformation)
                }
            )
        }
        Spacer(Modifier.height(8.dp))

        // Flip Vertical Switch
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Flip Vertical")
            Switch(
                checked = transformation.flipVertical,
                onCheckedChange = {
                    transformation = transformation.copy(flipVertical = it)
                    onTransformationChange(transformation)
                }
            )
        }
        Spacer(Modifier.height(16.dp))

        // Crop Overlay Integration (visual representation of crop region)
        // This Composable would be overlaid on the image being transformed.
        // For this panel, we'll just represent the controls for it.
        Text("Crop Controls", style = MaterialTheme.typography.titleMedium)
        // The actual ImageCropOverlay is expected to be layered over the image.
        // Here, we provide text to indicate where crop adjustments would happen.
        Text("Adjust crop region visually on the image.")
        // If ImageCropOverlay was integrated directly here, it would look something like:
        // ImageCropOverlay(
        //    imageTransformation = transformation,
        //    onTransformationChange = { newTrans ->
        //        transformation = newTrans
        //        onTransformationChange(transformation)
        //    }
        // )
    }
}
