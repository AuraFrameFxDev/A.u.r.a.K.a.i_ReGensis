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
import androidx.compose.foundation.layout.size
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
 * 🌀 QUANTUM FORGE — Brutalist Cyber-Arcane Interface
 * Manifests high-density logic structures and agent synergy patterns.
 */
@Composable
fun QuantumForgeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF808080)) // Neutral Grey Bedrock
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color.Black, RectangleShape) // Sharp Edges
                .background(Color.White.copy(alpha = 0.05f))
                .padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "QUANTUM FORGE // 0x7E3",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.Black)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "> SYNERGY_STATUS: OPTIMAL",
                color = Color(0xFF00FF00), // Genesis Green
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
            )

            Text(
                text = "> LOGIC_DENSITY: 350x350",
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Sub-Gate Indicator (Brutalist Style)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in 1..4) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .border(1.dp, Color.Black, RectangleShape)
                            .background(if (i == 1) Color.Black else Color.Transparent),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "G$i",
                            color = if (i == 1) Color.White else Color.Black,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
