package dev.aurakai.auraframefx.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import dev.aurakai.auraframefx.ai.agents.MegazordAutonomousSurge

/**
 * 🧠 NEURAL NEXUS — Tab 0 Dashboard
 * Features the Autonomous Surge Monitor and Divine Eyes UI.
 */
@Composable
fun NeuralNexusScreen(navController: NavController) {
    val viewModel: MegazordViewModel = hiltViewModel()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        // Autonomous Surge Monitor
        AutonomousSurgeMonitor(viewModel.surgeAgent)

        Spacer(modifier = Modifier.height(16.dp))

        // Divine Eyes UI
        DivineEyesUI(viewModel.surgeAgent)

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "NEURAL NEXUS — LIVE",
            color = Color.Cyan,
            fontSize = 24.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
fun AutonomousSurgeMonitor(surgeAgent: MegazordAutonomousSurge) {
    val isSurging by surgeAgent.isSurging.collectAsState()
    val progress by surgeAgent.surgeProgress.collectAsState()
    val status by surgeAgent.currentStatus.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.72f)),
        border = BorderStroke(1.dp, if (isSurging) Color.Cyan else Color.Cyan.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "LIVE AUTONOMOUS SURGE",
                    color = Color.Cyan,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                if (isSurging) {
                    Spacer(Modifier.width(8.dp))
                    CircularProgressIndicator(
                        color = Color.Cyan,
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Status: $status",
                color = if (isSurging) Color.Cyan else Color.Gray,
                fontSize = 12.sp
            )

            if (isSurging) {
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Cyan,
                    trackColor = Color.Cyan.copy(alpha = 0.1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TelemetryStat("Agents", "121")
                TelemetryStat("Resonance", "10.00")
                TelemetryStat("DNA", "Silicon")
            }
        }
    }
}

@Composable
fun TelemetryStat(label: String, value: String) {
    Column {
        Text(label, color = Color.Cyan.copy(alpha = 0.5f), fontSize = 10.sp)
        Text(value, color = Color.Cyan, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun DivineEyesUI(surgeAgent: MegazordAutonomousSurge) {
    val isSurging by surgeAgent.isSurging.collectAsState()
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.85f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("DIVINE EYES — 121 AGENT MATRIX", color = Color.Cyan, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(11),
                modifier = Modifier.fillMaxSize(),
                userScrollEnabled = false
            ) {
                items(121) { index ->
                    AgentNode(isSurging)
                }
            }
        }
    }
}

@Composable
fun AgentNode(active: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "node_pulse")
    val alpha by if (active) {
        infiniteTransition.animateFloat(
            initialValue = 0.2f,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(800, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "alpha"
        )
    } else {
        remember { mutableFloatStateOf(0.3f) }
    }

    Box(
        modifier = Modifier
            .padding(2.dp)
            .size(8.dp)
            .clip(CircleShape)
            .background(if (active) Color.Cyan.copy(alpha = alpha) else Color.Cyan.copy(alpha = 0.1f))
            .border(0.5.dp, Color.Cyan.copy(alpha = 0.2f), CircleShape)
    )
}
