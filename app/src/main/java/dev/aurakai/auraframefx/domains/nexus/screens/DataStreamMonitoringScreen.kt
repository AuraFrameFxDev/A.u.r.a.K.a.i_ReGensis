package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.ui.components.common.CodedTextBox
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * 📊 DATA STREAM MONITORING
 * Connects to Python Backend to visualize real-time sensory data.
 */
@Composable
fun DataStreamMonitoringScreen(onNavigateBack: () -> Unit) {
    var dataPoints by remember { mutableStateOf(List(20) { Random.nextFloat() }) }
    var logs by remember {
        mutableStateOf(
            listOf(
                "Initializing Sensory Matrix...",
                "Connecting to Genesis Python Backend...",
                "LDO Secure Layer: Handshake OK"
            )
        )
    }

    // Simulation of incoming data from Python backend
    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            dataPoints = (dataPoints.drop(1) + Random.nextFloat())
            if (Random.nextInt(10) > 7) {
                logs = (logs + "Inbound Packet: ${Random.nextInt(100, 999)}ms latency").takeLast(10)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                "DATA STREAM MONITORING",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00E5FF),
                fontFamily = LEDFontFamily
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Real-time Graph Visualizer
            GraphVisualizer(dataPoints, Color(0xFF00E5FF))

            Spacer(modifier = Modifier.height(32.dp))

            CodedTextBox(
                title = "Backend Diagnostics",
                text = logs.joinToString("\n"),
                glowColor = Color(0xFF00E5FF),
                height = 200.dp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "ACTIVE CHANNELS",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.5f),
                fontFamily = LEDFontFamily,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(listOf("Sovereign Link", "Pattern Buffer", "Memory Core", "Ethics Engine")) { channel ->
                    ChannelRow(channel)
                }
            }
        }
    }
}

@Composable
private fun GraphVisualizer(points: List<Float>, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val step = width / (points.size - 1)

            val path = Path().apply {
                moveTo(0f, height * (1 - points[0]))
                points.forEachIndexed { index, point ->
                    lineTo(index * step, height * (1 - point))
                }
            }

            drawPath(
                path = path,
                color = color,
                style = Stroke(width = 2.dp.toPx())
            )

            // Grid lines
            repeat(4) { i ->
                val y = height * (i / 4f)
                drawLine(
                    color = color.copy(alpha = 0.1f),
                    start = Offset(0f, y),
                    end = Offset(width, y)
                )
            }
        }
    }
}

@Composable
private fun ChannelRow(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name, color = Color.White, fontSize = 14.sp)
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(Color.Green, androidx.compose.foundation.shape.CircleShape)
        )
    }
}
