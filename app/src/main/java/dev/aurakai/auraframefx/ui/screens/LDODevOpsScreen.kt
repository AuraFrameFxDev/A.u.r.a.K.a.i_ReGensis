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
 * 🛠️ LDO DEVOPS — Catalyst Development Domain
 * Forge for agent advancement, multi-module build monitoring, and KSP synchronization.
 */
@Composable
fun LDODevOpsScreen(navController: NavController) {
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
                text = "LDO_DEVOPS // FORGE",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Build Status Panel
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black, RectangleShape)
                    .background(Color.White.copy(alpha = 0.1f))
                    .padding(16.dp)
            ) {
                Text(
                    text = "LATEST_BUILD_LOGS:",
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )

                Spacer(modifier = Modifier.height(12.dp))

                LogLine("KSP_GEN_COMPLETED")
                LogLine("HILT_GRAPH_VALIDATED")
                LogLine("LDO_RESONANCE_STABLE")
                LogLine("READY_FOR_DEPLOYMENT")
            }

            Spacer(modifier = Modifier.weight(1f))

            // Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ActionButton("GRADLE_SYNC", Modifier.weight(1f))
                ActionButton("KSP_REGEN", Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun LogLine(text: String) {
    Text(
        text = "> $text",
        color = Color.Black.copy(alpha = 0.7f),
        fontSize = 10.sp,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier.padding(vertical = 2.dp)
    )
}

@Composable
private fun ActionButton(label: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.Black)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}
