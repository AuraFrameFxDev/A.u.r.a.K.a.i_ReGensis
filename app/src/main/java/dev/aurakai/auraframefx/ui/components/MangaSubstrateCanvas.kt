package dev.aurakai.auraframefx.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp

@Composable
fun MangaSubstrateCanvas(
    modifier: Modifier = Modifier,
    drawingBlock: androidx.compose.ui.graphics.drawscope.DrawScope.() -> Unit
) {
    Canvas(
        modifier = modifier
            .size(350.dp, 350.dp) // Rigid HD-2D Resolution standard Bounds
            .background(Color(0xFF000000)) // The Abyssal Pure Void 
    ) {
        drawIntoCanvas { canvas ->
            val nativePaint = Paint().asFrameworkPaint().apply {
                isAntiAlias = false // ZERO ANTI-ALIASING MANDATE
                isDither = false
            }
            // Execute sharp code stream rendering
            drawingBlock()
        }
    }
}
