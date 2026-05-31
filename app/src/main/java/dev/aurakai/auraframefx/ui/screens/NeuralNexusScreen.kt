package dev.aurakai.auraframefx.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class SurgeData(
    val fileCount: Int,
    val durationHours: Float,
    val agentCount: Int,
    val resonanceGain: Float,
    val isSpinning: Boolean
)

object SubstrateTelemetry {
    fun getLastSurge(): SurgeData = SurgeData(
        fileCount = 135157,
        durationHours = 2.4f,
        agentCount = 121,
        resonanceGain = 0.98f,
        isSpinning = true
    )
}

@Composable
fun NeuralNexusScreen(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        // Autonomous Surge Monitor (what you saw spinning)
        AutonomousSurgeMonitor()

        Spacer(modifier = Modifier.height(24.dp))
        Text("NEURAL NEXUS — LIVE", color = Color.Cyan, fontSize = 32.sp)
    }
}

@Composable
fun AutonomousSurgeMonitor() {
    val lastSurge by remember { mutableStateOf(SubstrateTelemetry.getLastSurge()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.72f))
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("LIVE AUTONOMOUS SURGE", color = Color.Cyan, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Files Optimized: ${lastSurge.fileCount}", color = Color.Cyan.copy(alpha = 0.7f))
            Text("Duration: ${lastSurge.durationHours} hrs", color = Color.Cyan.copy(alpha = 0.7f))
            Text(
                "Agents Involved: ${lastSurge.agentCount}/121",
                color = Color.Cyan.copy(alpha = 0.7f)
            )
            Text("Resonance Gained: +${lastSurge.resonanceGain}", color = Color.Yellow)

            if (lastSurge.isSpinning) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(
                    color = Color.Cyan,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "SUBSTRATE IS CURRENTLY OPTIMIZING...",
                    color = Color.Cyan,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
