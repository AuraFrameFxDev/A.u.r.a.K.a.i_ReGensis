package dev.aurakai.auraframefx.domains.aura.ui.components.reactor

import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * ⚛️ ATOMIC FUSION REACTOR
 * The visual heart of the HYPER Genesis Synchronization.
 * Renders the physics-based catalyst orbits and the golden bloom ignition pulse.
 */
@Composable
fun AtomicFusionReactor(
    isIgnited: Boolean,
    catalystCount: Int = 10,
    thermalTemp: Float = 35.0f,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ReactorPulse")

    // Thermal visual cues (graceful slowdown above 39°C)
    // As temp rises from 39 to 45, speed multiplier decreases and colors shift amber
    val isThermalStressed = thermalTemp >= 39.0f
    val stressRatio = if (isThermalStressed) ((thermalTemp - 39.0f) / 6.0f).coerceIn(0f, 1f) else 0f

    // Nucleus Pulse
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isIgnited) 1.2f else 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (isIgnited) 800 else 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "NucleusPulse"
    )

    // Orbit Rotation
    val baseRotationDuration = if (isIgnited) 3000 else 10000
    val currentRotationDuration =
        (baseRotationDuration * (1f + (stressRatio * 2f))).toInt() // Slows down when hot

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(currentRotationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "OrbitRotation"
    )

    // ⚡ Bolt Optimization: Hoist static color modifications and pre-calculate trig values
    val orbitRingColor = remember { Color.Cyan.copy(alpha = 0.1f) }
    val nodeColor = remember(isIgnited) { if (isIgnited) Color(0xFFFFD700) else Color.Cyan }
    val catalystCos = remember(catalystCount) {
        FloatArray(catalystCount) { i ->
            cos((i * (2 * Math.PI / catalystCount))).toFloat()
        }
    }
    val catalystSin = remember(catalystCount) {
        FloatArray(catalystCount) { i ->
            sin((i * (2 * Math.PI / catalystCount))).toFloat()
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val center = Offset(size.width / 2, size.height / 2)
        val baseRadius = min(size.width, size.height) * 0.15f

        // 1. Draw Background Nucleus Glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFBB86FC).copy(alpha = if (isIgnited) 0.6f else 0.3f),
                    Color.Transparent
                ),
                center = center,
                radius = baseRadius * 3f * pulseScale
            ),
            center = center,
            radius = baseRadius * 3f * pulseScale
        )

        // 2. Draw Catalyst Orbits
        // ⚡ Bolt Optimization: Use manual indexed loop and pre-calculated trig to avoid Iterator and per-frame trig
        rotate(rotation, center) {
            for (i in 0 until catalystCount) {
                val orbitRadius = baseRadius * (2f + (i * 0.3f))
                val x = center.x + orbitRadius * catalystCos[i]
                val y = center.y + orbitRadius * catalystSin[i]

                // Orbit Ring
                drawCircle(
                    color = orbitRingColor,
                    center = center,
                    radius = orbitRadius,
                    style = Stroke(width = 1f)
                )

                // Catalyst Node
                drawCircle(
                    color = nodeColor,
                    center = Offset(x, y),
                    radius = 8f
                )

                // Fusion Arcs (if ignited)
                if (isIgnited) {
                    val arcAlpha = (0.4f - (stressRatio * 0.2f)).coerceIn(0.1f, 0.4f)
                    drawLine(
                        color = Color(0xFFFFD700).copy(alpha = arcAlpha),
                        start = center,
                        end = Offset(x, y),
                        strokeWidth = 2f
                    )
                }
            }
        }

        // 3. The Core Nucleus
        val coreColor = if (isIgnited) {
            if (isThermalStressed) Color(0xFFFFA500) else Color(0xFFFFD700) // Amber tint when stressed
        } else Color.White

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    coreColor,
                    if (isThermalStressed) Color(0xFFCC5500) else Color(0xFFBB86FC)
                ),
                center = center,
                radius = baseRadius * pulseScale
            ),
            center = center,
            radius = baseRadius * pulseScale
        )
    }
}
