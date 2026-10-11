package dev.aurakai.auraframefx.ui.dashboard

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import dev.aurakai.auraframefx.core.binder.BinderTelemetryConduit
import kotlinx.coroutines.flow.collectLatest
import java.util.concurrent.ConcurrentLinkedQueue

@Composable
fun AndaruaMirrorVisualizer(modifier: Modifier = Modifier) {
    val pulses = remember { ConcurrentLinkedQueue<BinderTelemetryConduit.TransactionPulse>() }
    val infiniteTransition = rememberInfiniteTransition(label = "AndaruaMirror")
    val rotationState = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(28000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    LaunchedEffect(Unit) {
        BinderTelemetryConduit.transactionFlow.collectLatest { pulse ->
            pulses.add(pulse)
            if (pulses.size > 60) pulses.poll()
        }
    }

    // ⚡ Bolt Optimization: Hoist static color and style objects to avoid per-frame allocations
    val bgColor = remember { Color(0xFF0A001F) }
    val pulseRingColor = remember { Color(0xFFFF00B4) }
    val pulseLineColor = remember { Color(0xFF00D9FF) }
    val coreColor = remember { Color(0xFF00BFFF) }
    val pulseStroke = remember { Stroke(width = 3.5f) }

    // ⚡ Bolt Optimization: Hoist loop-invariant numerical constants to avoid per-frame math
    val ageThreshold = remember { 4200f }
    val angleStep = remember { 17f }
    val payloadScale = remember { 8f }
    val minRadius = remember { 20f }
    val maxRadius = remember { 280f }
    val radiusFactor = remember { 0.6f }
    val pulseLineStrokeWidth = remember { 1.5f }
    val coreRadius = remember { 42f }
    val degToRad = remember { 0.017453292f }

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val center = Offset(w / 2, h / 2)
        val now = System.currentTimeMillis()

        // Mirror grid base
        drawRect(color = bgColor, size = size)

        rotate(rotationState.value, center) {
            pulses.forEachIndexed { i, pulse ->
                val age = (now - pulse.timestamp).toFloat()
                if (age > ageThreshold) return@forEachIndexed
                val alpha = (1f - age / ageThreshold).coerceIn(0.08f, 0.95f)
                val radius = (pulse.payloadSize / payloadScale).coerceIn(minRadius, maxRadius)
                val angle = (i * angleStep) % 360f
                val angleRad = angle * degToRad
                val x = center.x + radius * kotlin.math.cos(angleRad)
                val y = center.y + radius * kotlin.math.sin(angleRad)

                // Reversed creative stroke
                drawCircle(
                    color = pulseRingColor.copy(alpha = alpha * 0.7f),
                    radius = radius * radiusFactor,
                    center = Offset(x, y),
                    style = pulseStroke
                )

                drawLine(
                    color = pulseLineColor.copy(alpha = alpha),
                    start = center,
                    end = Offset(x, y),
                    strokeWidth = pulseLineStrokeWidth
                )
            }
        }

        // Central Andarua core
        drawCircle(
            color = coreColor,
            radius = coreRadius,
            center = center
        )
    }
}
