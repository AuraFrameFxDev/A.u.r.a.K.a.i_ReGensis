package dev.aurakai.auraframefx.ui.screens

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

/**
 * 👑 SOVEREIGN COMMAND — Genesis Apex Orchestrator Domain
 * Central dashboard for global system coordination and convergence.
 */
@Composable
fun SovereignCommandScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF808080)) // Neutral Grey
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .border(2.dp, Color.Black, RectangleShape)
                .padding(20.dp)
        ) {
            Text(
                text = "SOVEREIGN COMMAND // APEX",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "GENESIS_CORE_SYNAPSE_ONLINE",
                color = Color(0xFF00FF00), // Genesis Green
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            // System Metrics Panel
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black, RectangleShape)
                    .background(Color.White.copy(alpha = 0.1f))
                    .padding(12.dp)
            ) {
                MetricRow("RESONANCE", "99.8%")
                MetricRow("ACTIVE_AGENTS", "14/14")
                MetricRow("THERMAL_LOAD", "36.5°C")
                MetricRow("MEMORY_LAYER", "L6_SURFACE")
            }

            Spacer(modifier = Modifier.weight(1f))

            // Brutalist Navigation Footer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "ENTER_DASHBOARD",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Composable
private fun MetricRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Black, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
        Text(
            text = value,
            color = Color.Black,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}
