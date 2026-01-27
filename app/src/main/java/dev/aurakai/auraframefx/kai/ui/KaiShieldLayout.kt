package dev.aurakai.auraframefx.kai.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import dev.aurakai.auraframefx.kai.ui.theme.KaiColor

@Composable
fun KaiShieldLayout(
    modifier: Modifier = Modifier,
    securityLevel: Float = 0.5f, // 0.0 to 1.0
    isActive: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ShieldPulse")
    
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseAlpha"
    )

    val rotateAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "RotateAngle"
    )

    // Density Effect: Higher security level = smaller, tighter hexagons
    val hexSize = lerp(40f, 20f, securityLevel)
    val spacing = hexSize * 1.8f

    Canvas(modifier = modifier.fillMaxSize()) {
        val center = size.center
        val grid = KaiShieldMap.generateGrid(size.width * 1.5f, size.height * 1.5f, spacing)
        
        rotate(rotateAngle, center) {
            grid.forEach { pos ->
                // Center the grid relative to the canvas center
                val drawPos = pos - (size.center * 0.5f)
                val hexPoints = KaiShieldMap.calculateHexPoints(drawPos, hexSize)
                val path = Path().apply {
                    moveTo(hexPoints[0].x, hexPoints[0].y)
                    hexPoints.drop(1).forEach { lineTo(it.x, it.y) }
                    close()
                }

                // Draw hexagon fill
                drawPath(
                    path = path,
                    color = KaiColor.HexagonFill.copy(alpha = KaiColor.HexagonFill.alpha * pulseAlpha),
                    style = Fill
                )

                // Draw hexagon border
                drawPath(
                    path = path,
                    color = KaiColor.HexagonBorder.copy(alpha = securityLevel.coerceAtLeast(0.2f)),
                    style = Stroke(width = 2f)
                )
            }
        }

        // Central "Fortress" Core
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(KaiColor.ShieldCyan.copy(alpha = 0.2f), Color.Transparent),
                center = center,
                radius = size.minDimension / 3
            ),
            radius = size.minDimension / 3,
            center = center
        )
    }
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + fraction * (stop - start)
}
