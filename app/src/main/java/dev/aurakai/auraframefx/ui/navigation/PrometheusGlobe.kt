package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import dev.aurakai.auraframefx.ui.theme.SovereignTeal

/**
 * 🌍 PROMETHEUS GLOBE
 * The Celestial Navigation Anchor.
 * Replaces the mechanical gear with a globe of light.
 * Wires pulse directly to touch interaction (12th sense).
 */
@Composable
fun PrometheusGlobe(
    modifier: Modifier = Modifier,
    color: Color = SovereignTeal,
    pulseIntensity: Float = 0f,
    onDrag: (Float) -> Unit = {}
) {
    var dragOffset by remember { mutableStateOf(0f) }
    val infiniteTransition = rememberInfiniteTransition(label = "globe_pulse")
    val ambientPulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing), // Simplified easing
            repeatMode = RepeatMode.Reverse
        ),
        label = "ambient"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = modifier
            .size(140.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        dragOffset += dragAmount.x
                        onDrag(dragAmount.x)
                    },
                    onDragEnd = {
                        dragOffset = 0f
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val baseRadius = size.minDimension / 2.2f

            // The Globe (Circle)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha = 0.2f + (pulseIntensity * 0.5f)),
                        color.copy(alpha = 0.05f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = baseRadius * (ambientPulse + (pulseIntensity * 0.2f))
                ),
                center = center,
                radius = baseRadius * (ambientPulse + (pulseIntensity * 0.2f))
            )

            // The Core
            drawCircle(
                color = color.copy(alpha = 0.8f + (pulseIntensity * 0.2f)),
                radius = baseRadius * 0.8f,
                style = Stroke(width = 2.dp.toPx()),
                center = center
            )

            // "Celestial" Lines / Meridian hints
             rotate(rotation, center) {
                 drawCircle(
                     color = color.copy(alpha = 0.3f),
                     radius = baseRadius * 0.8f,
                     style = Stroke(width = 1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)),
                     center = center
                 )

                 drawLine(
                     color = color.copy(alpha = 0.3f),
                     start = Offset(center.x, center.y - baseRadius * 0.8f),
                     end = Offset(center.x, center.y + baseRadius * 0.8f),
                     strokeWidth = 1.dp.toPx()
                 )
                 drawLine(
                     color = color.copy(alpha = 0.3f),
                     start = Offset(center.x - baseRadius * 0.8f, center.y),
                     end = Offset(center.x + baseRadius * 0.8f, center.y),
                     strokeWidth = 1.dp.toPx()
                 )
             }

             // The 12th Sense Spike Visualization
             if (pulseIntensity > 0.1f) {
                 drawCircle(
                     color = color.copy(alpha = pulseIntensity * 0.6f),
                     radius = baseRadius * (1.0f + pulseIntensity * 0.5f),
                     style = Stroke(width = 4.dp.toPx()),
                     center = center
                 )
             }
        }
    }
}
