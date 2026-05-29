package dev.aurakai.auraframefx.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

/**
 * NeonFrame — A sharp-edged, glowing container following the Sovereign 4D aesthetic.
 * 70% transparency, sharp corners, and neon edge glow.
 */
@Composable
fun NeonFrame(
    color: Color,
    modifier: Modifier = Modifier,
    showScanlines: Boolean = true, // L7 Polish: Toggleable scanlines
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.7f), RectangleShape) // Standard 70% transparency
            .border(1.dp, color.copy(alpha = 0.8f), RectangleShape) // Sharp outer border
            .drawBehind {
                // Outer glow
                val glowWidth = 2.dp.toPx()
                drawLine(
                    color.copy(alpha = 0.3f),
                    Offset(0f, 0f),
                    Offset(size.width, 0f),
                    strokeWidth = glowWidth * 2
                )
                drawLine(
                    color.copy(alpha = 0.3f),
                    Offset(0f, 0f),
                    Offset(0f, size.height),
                    strokeWidth = glowWidth * 2
                )
                drawLine(
                    color.copy(alpha = 0.3f),
                    Offset(size.width, 0f),
                    Offset(size.width, size.height),
                    strokeWidth = glowWidth * 2
                )
                drawLine(
                    color.copy(alpha = 0.3f),
                    Offset(0f, size.height),
                    Offset(size.width, size.height),
                    strokeWidth = glowWidth * 2
                )

                if (showScanlines) {
                    // Scanline Effect
                    val scanlineSpacing = 4.dp.toPx()
                    var y = 0f
                    while (y < size.height) {
                        drawLine(
                            color = Color.White.copy(alpha = 0.03f),
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = 1.5f
                        )
                        y += scanlineSpacing
                    }
                }
            }
            .padding(1.dp)
    ) {
        content()
    }
}

/**
 * NeuralStarfield — A chaotic, drifting starfield for sovereign domain backgrounds.
 */
@Composable
fun NeuralStarfield() {
    androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
        repeat(80) {
            val alpha = (10..30).random() / 100f
            val radius = (1..3).random().dp.toPx()
            drawCircle(
                color = Color.White.copy(alpha = alpha),
                radius = radius,
                center = Offset(
                    size.width * (0..1000).random() / 1000f,
                    size.height * (0..1000).random() / 1000f
                )
            )
        }
    }
}
