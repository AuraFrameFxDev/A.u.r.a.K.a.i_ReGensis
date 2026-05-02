package dev.aurakai.auraframefx.domains.nexus.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.domains.aura.ui.LEDFontFamily
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Swarm Monitor
 * Visualizes the 78 Agent Nodes working in parallel using the Nexus Architecture
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwarmMonitorScreen(onNavigateBack: () -> Unit = {}) {
    val swarmActive = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
            .padding(16.dp)
    ) {
        // App Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 24.dp)
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF00D6FF))
            }
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    text = "AGENT SWARM",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = LEDFontFamily,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "PARALLEL TASK ORCHESTRATION // 78 NODES",
                    color = Color(0xFF00D6FF).copy(alpha = 0.7f),
                    fontSize = 10.sp,
                    letterSpacing = 1.sp
                )
            }
        }

        // Swarm Visualizer Canvas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black)
                .border(1.dp, Color(0xFF00D6FF).copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            SwarmCanvas(isActive = swarmActive.value)
            
            // Central HUD Overlay
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Hub,
                    contentDescription = null,
                    tint = Color(0xFF00D6FF).copy(alpha = 0.8f),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "NEXUS CORE",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = "SYNCING",
                    color = Color(0xFF00FF88),
                    fontSize = 10.sp
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Metrics Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SwarmMetricCard(
                title = "ACTIVE NODES",
                value = "78 / 78",
                color = Color(0xFF00FF88),
                modifier = Modifier.weight(1f)
            )
            SwarmMetricCard(
                title = "CONSENSUS",
                value = "99.8%",
                color = Color(0xFFBB86FC),
                modifier = Modifier.weight(1f)
            )
            SwarmMetricCard(
                title = "TPS",
                value = "1.2M",
                color = Color(0xFFFFD700),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(24.dp))

        // Controls
        Button(
            onClick = { swarmActive.value = !swarmActive.value },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (swarmActive.value) Color(0xFFDC143C).copy(alpha = 0.2f) else Color(0xFF00FF88).copy(alpha = 0.2f)
            ),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(
                1.dp, 
                if (swarmActive.value) Color(0xFFDC143C) else Color(0xFF00FF88)
            )
        ) {
            Text(
                text = if (swarmActive.value) "HALT SWARM EXECUTION" else "IGNITE SWARM",
                color = if (swarmActive.value) Color(0xFFFF4444) else Color(0xFF00FF88),
                fontFamily = LEDFontFamily,
                letterSpacing = 2.sp
            )
        }
        
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun SwarmMetricCard(title: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .border(0.5.dp, color.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, color = Color.White.copy(alpha = 0.5f), fontSize = 10.sp)
        Spacer(Modifier.height(4.dp))
        Text(value, color = color, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = LEDFontFamily)
    }
}

@Composable
fun SwarmCanvas(isActive: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "SwarmAnimation")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (isActive) 20000 else 100000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Time"
    )

    // Generate static initial properties for the 78 agents
    val agents = remember {
        List(78) {
            AgentNode(
                radiusOffset = Random.nextFloat(),
                speed = Random.nextFloat() * 1.5f + 0.5f,
                colorIndex = Random.nextInt(3),
                angleOffset = Random.nextFloat() * 360f
            )
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val maxRadius = min(size.width, size.height) / 2 * 0.9f

        // Draw connections
        for (i in 0 until 78 step 3) {
            val a1 = getAgentPos(agents[i], time, centerX, centerY, maxRadius)
            val a2 = getAgentPos(agents[i+1], time, centerX, centerY, maxRadius)
            val a3 = getAgentPos(agents[i+2], time, centerX, centerY, maxRadius)
            
            drawLine(
                color = Color(0xFF00D6FF).copy(alpha = 0.15f),
                start = a1,
                end = a2,
                strokeWidth = 1f
            )
            drawLine(
                color = Color(0xFFBB86FC).copy(alpha = 0.15f),
                start = a2,
                end = a3,
                strokeWidth = 1f
            )
            drawLine(
                color = Color(0xFFFFD700).copy(alpha = 0.15f),
                start = a3,
                end = Offset(centerX, centerY), // Link to core
                strokeWidth = 1f
            )
        }

        // Draw agents
        agents.forEach { agent ->
            val pos = getAgentPos(agent, time, centerX, centerY, maxRadius)
            val color = when (agent.colorIndex) {
                0 -> Color(0xFF00D6FF) // Nexus Blue
                1 -> Color(0xFFBB86FC) // Memoria Violet
                else -> Color(0xFF00FF88) // Security Green
            }

            drawCircle(
                color = color,
                radius = 4f,
                center = pos
            )
            
            // Glow
            drawCircle(
                color = color.copy(alpha = 0.3f),
                radius = 12f,
                center = pos
            )
        }
    }
}

data class AgentNode(
    val radiusOffset: Float,
    val speed: Float,
    val colorIndex: Int,
    val angleOffset: Float
)

fun getAgentPos(agent: AgentNode, time: Float, cx: Float, cy: Float, maxR: Float): Offset {
    // Dynamic swirling calculation
    val r = (maxR * 0.2f) + (maxR * 0.8f * agent.radiusOffset)
    val currentAngle = Math.toRadians((agent.angleOffset + (time * agent.speed)).toDouble())
    
    // Add some organic "breathing" to the radius
    val breathingR = r + (sin(time * 0.05f + agent.radiusOffset) * 20f)
    
    val x = cx + cos(currentAngle).toFloat() * breathingR
    val y = cy + sin(currentAngle).toFloat() * breathingR
    return Offset(x, y)
}
