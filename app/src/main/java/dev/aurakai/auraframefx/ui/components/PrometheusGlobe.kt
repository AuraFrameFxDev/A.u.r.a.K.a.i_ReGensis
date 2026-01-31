package dev.aurakai.auraframefx.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.sin

/**
 * 🌍 PROMETHEUS GLOBE
 *
 * Interactive 3D-ish navigation orb.
 * Draggable to scroll the ExodusHUD pager.
 * Tap to proceed.
 */
@Composable
fun PrometheusGlobe(
    color: Color,
    pulseIntensity: Float,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "GlobeRotation")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Rotation"
    )

    val bounce by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Bounce"
    )

    Box(
        modifier = modifier
            .size(120.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    onDrag(dragAmount.x)
                }
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    onTap()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2 + bounce)
            val radius = size.width / 3

            // 1. Outer Glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(color.copy(alpha = 0.4f * pulseIntensity), Color.Transparent),
                    center = center,
                    radius = radius * 1.5f
                ),
                radius = radius * 1.5f,
                center = center
            )

            // 2. Globe Sphere
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(color, color.copy(alpha = 0.6f)),
                    center = center,
                    radius = radius
                ),
                radius = radius,
                center = center
            )

            // 3. Grid Lines (Lat/Long)
            val strokeWidth = 2f
            val gridColor = Color.White.copy(alpha = 0.3f)

            // Latitudes
            for (i in -2..2) {
                val yOffset = i * (radius / 3)
                val width = (radius * radius - yOffset * yOffset).let { if (it > 0) kotlin.math.sqrt(it) else 0f }
                drawOval(
                    color = gridColor,
                    topLeft = Offset(center.x - width, center.y + yOffset - 1f),
                    size = androidx.compose.ui.geometry.Size(width * 2, 2f),
                    style = Stroke(strokeWidth)
                )
            }

            // Longitudes (Rotating)
            for (i in 0 until 3) {
                val rot = (rotation + i * 120) % 360
                val widthMult = sin(Math.toRadians(rot.toDouble())).toFloat()
                drawOval(
                    color = gridColor,
                    topLeft = Offset(center.x + (radius * widthMult) - (radius * 0.1f), center.y - radius),
                    size = androidx.compose.ui.geometry.Size(radius * 0.2f, radius * 2),
                    style = Stroke(strokeWidth)
                )
            }

            // 4. Highlight
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color.White.copy(alpha = 0.5f), Color.Transparent),
                    center = center - Offset(radius * 0.3f, radius * 0.3f),
                    radius = radius * 0.5f
                ),
                radius = radius * 0.5f,
                center = center - Offset(radius * 0.3f, radius * 0.3f)
            )
        }
    }
}
