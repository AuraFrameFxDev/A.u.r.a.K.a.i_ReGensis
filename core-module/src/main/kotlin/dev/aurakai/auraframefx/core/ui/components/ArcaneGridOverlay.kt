package dev.aurakai.auraframefx.core.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ArcaneGridOverlay() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val step = 40.dp.toPx()
        for (x in 0..(size.width / step).toInt()) {
            drawLine(
                color = Color.White.copy(alpha = 0.03f),
                start = Offset(x * step, 0f),
                end = Offset(x * step, size.height),
                strokeWidth = 1f
            )
        }
        for (y in 0..(size.height / step).toInt()) {
            drawLine(
                color = Color.White.copy(alpha = 0.03f),
                start = Offset(0f, y * step),
                end = Offset(size.width, y * step),
                strokeWidth = 1f
            )
        }
    }
}
