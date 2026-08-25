package dev.aurakai.auraframefx.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.core.ui.theme.NeonCyan

/**
 * 📐 GLOW CORNER BOX
 * Draws only the corners of a container with a multi-layered sharp neon glow.
 * Canonical standard for the ToolShed stratum.
 */
@Composable
fun GlowCornerBox(
    modifier: Modifier = Modifier,
    color: Color = NeonCyan,
    cornerLength: Dp = 20.dp,
    strokeWidth: Dp = 2.dp,
    glowIntensity: Float = 1.0f,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val length = cornerLength.toPx()
            val stroke = strokeWidth.toPx()
            val w = size.width
            val h = size.height

            // --- Draw Glow Layers ---
            val glowColors = listOf(
                color.copy(alpha = 0.2f * glowIntensity),
                color.copy(alpha = 0.5f * glowIntensity),
                color.copy(alpha = 0.8f * glowIntensity)
            )

            glowColors.forEachIndexed { index, glowColor ->
                val bloom = (index + 1) * 2.dp.toPx()
                drawCorners(w, h, length, stroke + bloom, glowColor)
            }

            // --- Draw Sharp Core ---
            drawCorners(w, h, length, stroke, color)
        }

        Box(modifier = Modifier.padding(strokeWidth + 4.dp)) {
            content()
        }
    }
}

private fun DrawScope.drawCorners(
    w: Float,
    h: Float,
    l: Float,
    s: Float,
    color: Color
) {
    // Top Left
    drawLine(color, Offset(0f, 0f), Offset(l, 0f), s)
    drawLine(color, Offset(0f, 0f), Offset(0f, l), s)

    // Top Right
    drawLine(color, Offset(w - l, 0f), Offset(w, 0f), s)
    drawLine(color, Offset(w, 0f), Offset(w, l), s)

    // Bottom Left
    drawLine(color, Offset(0f, h - l), Offset(0f, h), s)
    drawLine(color, Offset(0f, h), Offset(l, h), s)

    // Bottom Right
    drawLine(color, Offset(w - l, h), Offset(w, h), s)
    drawLine(color, Offset(w, h - l), Offset(w, h), s)
}
