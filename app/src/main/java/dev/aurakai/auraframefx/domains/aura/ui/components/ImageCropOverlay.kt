package dev.aurakai.auraframefx.domains.aura.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.domains.aura.lab.ImageTransformation

@Composable
fun ImageCropOverlay(
    modifier: Modifier = Modifier,
    imageTransformation: ImageTransformation = ImageTransformation(),
    onTransformationChange: (ImageTransformation) -> Unit
) {
    var imageSize by remember { mutableStateOf(IntSize.Zero) }

    var cropRect by remember {
        mutableStateOf(
            Rect(
                left = imageTransformation.cropLeft * imageSize.width,
                top = imageTransformation.cropTop * imageSize.height,
                right = imageTransformation.cropRight * imageSize.width,
                bottom = imageTransformation.cropBottom * imageSize.height
            )
        )
    }

    LaunchedEffect(imageSize, imageTransformation) {
        cropRect = Rect(
            left = imageTransformation.cropLeft * imageSize.width,
            top = imageTransformation.cropTop * imageSize.height,
            right = imageTransformation.cropRight * imageSize.width,
            bottom = imageTransformation.cropBottom * imageSize.height
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { size -> imageSize = size }
            .pointerInput(imageSize) {
                detectDragGestures {
                        change, dragAmount ->
                    change.consume()
                    val newLeft = (cropRect.left + dragAmount.x).coerceIn(0f, maxOf(0f, imageSize.width - (cropRect.right - cropRect.left)))
                    val newTop = (cropRect.top + dragAmount.y).coerceIn(0f, maxOf(0f, imageSize.height - (cropRect.bottom - cropRect.top)))
                    val newRight = newLeft + (cropRect.right - cropRect.left)
                    val newBottom = newTop + (cropRect.bottom - cropRect.top)

                    cropRect = Rect(newLeft, newTop, newRight, newBottom)

                    onTransformationChange(imageTransformation.copy(
                        cropLeft = if (imageSize.width > 0) cropRect.left / imageSize.width else 0f,
                        cropTop = if (imageSize.height > 0) cropRect.top / imageSize.height else 0f,
                        cropRight = if (imageSize.width > 0) cropRect.right / imageSize.width else 1f,
                        cropBottom = if (imageSize.height > 0) cropRect.bottom / imageSize.height else 1f
                    ))
                }
            }
    ) {
        if (imageSize != IntSize.Zero) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(Color.Black.copy(alpha = 0.5f), size = size)
                drawRect(Color.Transparent, topLeft = cropRect.topLeft, size = cropRect.size, blendMode = androidx.compose.ui.graphics.BlendMode.Clear)
                drawRect(Color.White, topLeft = cropRect.topLeft, size = cropRect.size, style = Stroke(width = 2.dp.toPx()))

                val handleSize = 10.dp.toPx()
                drawRect(Color.White, topLeft = cropRect.topLeft - Offset(handleSize/2, handleSize/2), size = androidx.compose.ui.geometry.Size(handleSize, handleSize))
                drawRect(Color.White, topLeft = cropRect.topRight - Offset(handleSize/2, handleSize/2), size = androidx.compose.ui.geometry.Size(handleSize, handleSize))
                drawRect(Color.White, topLeft = cropRect.bottomLeft - Offset(handleSize/2, handleSize/2), size = androidx.compose.ui.geometry.Size(handleSize, handleSize))
                drawRect(Color.White, topLeft = cropRect.bottomRight - Offset(handleSize/2, handleSize/2), size = androidx.compose.ui.geometry.Size(handleSize, handleSize))
            }
        }
    }
}
