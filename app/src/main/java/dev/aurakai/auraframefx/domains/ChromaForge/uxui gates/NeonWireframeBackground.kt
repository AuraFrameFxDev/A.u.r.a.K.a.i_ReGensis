package dev.aurakai.auraframefx.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

/**
 * ⚛️ NEON WIREFRAME BACKGROUND
 * 
 * Cinematic depth with 2px lines, neon wireframes, and volumetric shadows.
 * Core visual anchor for LDO DEVELOPMENT NEXUS and general ReGenesis branding.
 */
@Composable
fun NeonWireframeBackground(
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "wireframe")

    val gridOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "offset"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val gridSpacing = 60.dp.toPx()

        // 1. VOLUMETRIC GRADIENT (Deep Depth)
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF020205),
                    accentColor.copy(alpha = 0.05f),
                    Color(0xFF020205)
                )
            )
        )

        // 2. PERSPECTIVE GRID (FLOOR)
        val floorHeight = height * 0.4f
        val horizonY = height - floorHeight

        // Horizontal Lines (Perspective)
        for (i in 0..10) {
            val progress = i.toFloat() / 10f
            val yPos =
                horizonY + (floorHeight * (progress * progress)) // Exponential for perspective

            drawLine(
                color = accentColor.copy(alpha = 0.2f * progress),
                start = Offset(0f, yPos),
                end = Offset(width, yPos),
                strokeWidth = 1.dp.toPx()
            )
        }

        // Vertical Lines (Vanish Point)
        val vanishPoint = Offset(width / 2, horizonY - 100.dp.toPx())
        val gridCount = 12
        for (i in -gridCount..gridCount) {
            val xStart = width / 2 + (i * gridSpacing)

            drawLine(
                color = accentColor.copy(alpha = 0.15f),
                start = Offset(xStart, height),
                end = vanishPoint,
                strokeWidth = 1.dp.toPx()
            )
        }

        // 3. FLOATING NEON WIREFRAME (Architectural)
        val path = Path().apply {
            moveTo(width * 0.1f, height * 0.3f)
            lineTo(width * 0.3f, height * 0.25f)
            lineTo(width * 0.35f, height * 0.4f)
            lineTo(width * 0.15f, height * 0.45f)
            close()
        }

        drawPath(
            path = path,
            color = accentColor.copy(alpha = 0.3f),
            style = Stroke(width = 2.dp.toPx())
        )

        // Glow points at vertices
        drawCircle(accentColor, radius = 4.dp.toPx(), center = Offset(width * 0.1f, height * 0.3f))
        drawCircle(accentColor, radius = 4.dp.toPx(), center = Offset(width * 0.3f, height * 0.25f))
    }
}
