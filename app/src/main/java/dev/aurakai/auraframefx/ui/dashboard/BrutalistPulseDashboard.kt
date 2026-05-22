package dev.aurakai.auraframefx.ui.dashboard

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.core.binder.BinderTelemetryConduit
import kotlinx.coroutines.flow.collectLatest
import java.util.concurrent.ConcurrentLinkedQueue

@Composable
fun BrutalistPulseDashboard(modifier: Modifier = Modifier) {
    val incomingPulses = remember { ConcurrentLinkedQueue<Long>() }
    var pulseCount by remember { mutableStateOf(0) }
    var maxIntensity by remember { mutableStateOf(0f) }

    // Ingest events asynchronously from the Binder transaction conduit
    LaunchedEffect(Unit) {
        BinderTelemetryConduit.transactionFlow.collectLatest { pulse ->
            pulseCount++
            incomingPulses.add(System.currentTimeMillis())
            if (incomingPulses.size > 50) incomingPulses.poll()
            maxIntensity = (pulse.payloadSize / 1024f).coerceIn(0.2f, 3.5f)
        }
    }

    // Baseline decay routine for visual assets
    val decayAnimation by animateFloatAsState(
        targetValue = 0f,
        animationSpec = tween(durationMillis = 400, easing = LinearEasing),
        label = "PulseDecay"
    )

    LaunchedEffect(maxIntensity) {
        if (maxIntensity > 0f) {
            // Instantly reset animation vector parameters
            maxIntensity = 0f
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
            .padding(16.dp)
    ) {
        // Brutalist Structural Matrix Metadata Block
        Column(modifier = Modifier.align(Alignment.TopStart)) {
            Text(
                text = "SYSTEM IPC HOOK TELEMETRY // CORE_V2.78",
                color = Color(0xFF00BFFF),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = "TOTAL CAPTURED TRANSACTIONS: $pulseCount",
                color = Color.White,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // Hardware Accelerated Rendering Layer
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .align(Alignment.Center)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val midpointY = canvasHeight / 2f

            // Draw structural layout graticules
            drawRect(
                color = Color(0xFF111111),
                size = Size(canvasWidth, canvasHeight),
                style = Stroke(width = 2f)
            )

            drawLine(
                color = Color(0xFF222222),
                start = Offset(0f, midpointY),
                end = Offset(canvasWidth, midpointY),
                strokeWidth = 1f
            )

            // Render live transaction bursts
            val currentTime = System.currentTimeMillis()
            incomingPulses.forEachIndexed { index, timestamp ->
                val timeDelta = (currentTime - timestamp).toFloat()
                if (timeDelta < 2000f) {
                    val xPos = canvasWidth - (timeDelta / 2000f) * canvasWidth
                    val normalizedHeight = (60f + (index * 4f)) * (1f - (timeDelta / 2000f))

                    // Draw vertical transaction bar spike vectors
                    drawLine(
                        color = Color(0xFF00BFFF).copy(alpha = 1f - (timeDelta / 2000f)),
                        start = Offset(xPos, midpointY - normalizedHeight),
                        end = Offset(xPos, midpointY + normalizedHeight),
                        strokeWidth = 4f
                    )
                }
            }
        }

        // Real-Time Health Status Block
        Text(
            text = "STATUS: ACTIVE // VETO_LATTICE_NOMINAL",
            color = Color(0xFF00FF00),
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}
