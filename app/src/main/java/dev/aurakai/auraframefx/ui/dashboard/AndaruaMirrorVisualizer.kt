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
    val rotation by infiniteTransition.animateFloat(
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

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val center = Offset(w / 2, h / 2)
        val now = System.currentTimeMillis()

        // Mirror grid base
        drawRect(color = Color(0xFF0A001F), size = size)

        rotate(rotation, center) {
            pulses.forEachIndexed { i, pulse ->
                val age = (now - pulse.timestamp).toFloat()
                if (age > 4200f) return@forEachIndexed
                val alpha = (1f - age / 4200f).coerceIn(0.08f, 0.95f)
                val radius = (pulse.payloadSize / 8f).coerceIn(20f, 280f)
                val angle = (i * 17f) % 360f
                val x =
                    center.x + radius * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
                val y =
                    center.y + radius * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()

                // Reversed creative stroke
                drawCircle(
                    color = Color(0xFFFF00B4).copy(alpha = alpha * 0.7f),
                    radius = radius * 0.6f,
                    center = Offset(x, y),
                    style = Stroke(width = 3.5f)
                )

                drawLine(
                    color = Color(0xFF00D9FF).copy(alpha = alpha),
                    start = center,
                    end = Offset(x, y),
                    strokeWidth = 1.5f
                )
            }
        }

        // Central Andarua core
        drawCircle(
            color = Color(0xFF00BFFF),
            radius = 42f,
            center = center
        )
    }
}
