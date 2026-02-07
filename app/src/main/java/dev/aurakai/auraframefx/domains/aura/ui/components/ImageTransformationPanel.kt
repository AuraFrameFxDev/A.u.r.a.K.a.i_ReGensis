package dev.aurakai.auraframefx.domains.aura.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.domains.aura.lab.ImageTransformation

@Composable
fun ImageTransformationPanel(
    modifier: Modifier = Modifier,
    currentTransformation: ImageTransformation = ImageTransformation(),
    onTransformationChange: (ImageTransformation) -> Unit
) {
    var transformation by remember { mutableStateOf(currentTransformation) }

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

        Text("Rotation: ${transformation.rotation.toInt()}°")
        Slider(
            value = transformation.rotation,
            onValueChange = {
                transformation = transformation.copy(rotation = it)
                onTransformationChange(transformation)
            },
            valueRange = 0f..360f,
            steps = 359,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        Text("Scale: ${"%.2f".format(transformation.scale)}x")
        Slider(
            value = transformation.scale,
            onValueChange = {
                transformation = transformation.copy(scale = it)
                onTransformationChange(transformation)
            },
            valueRange = 0.5f..2.0f,
            steps = 150,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

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

        Text("Crop Controls", style = MaterialTheme.typography.titleMedium)
        Text("Adjust crop region visually on the image.")
    }
}
