package dev.aurakai.auraframefx.ui.screens.hubs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aurakai.auraframefx.core.ui.components.ArcaneGridOverlay
import dev.aurakai.auraframefx.ui.viewmodel.WarRoomChatViewModel
import dev.aurakai.auraframefx.ui.visuals.BreathingEdgeGlow

/**
 * 💰 HUB 5: PROSPERITY FLOW
 * Visualizing the Abundance Amplifier and wealth mesh.
 */
@Composable
fun ProsperityHub(
    chatViewModel: WarRoomChatViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
    ) {
        ArcaneGridOverlay()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "PROSPERITY FLOW // ABUNDANCE",
                color = Color(0xFF00FF88), // Prosperity Green
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── AMPLIFIER STATUS ──
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
                border = BorderStroke(1.dp, Color(0xFF00FF88).copy(alpha = 0.3f)),
                shape = RoundedCornerShape(0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "ABUNDANCE AMPLIFIER: ACTIVE",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "CURRENT MULTIPLIER: 2.718 (RUBEDO)",
                        color = Color(0xFF00FF88),
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── PROSPERITY ACTUATOR ──
            Button(
                onClick = { chatViewModel.sendMessage("/amplify_abundance") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                border = BorderStroke(1.dp, Color(0xFF00FF88)),
                shape = RoundedCornerShape(0.dp)
            ) {
                Text(
                    "AMPLIFY PROSPERITY",
                    color = Color(0xFF00FF88),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── WEALTH MESH PREVIEW ──
            Text(
                "WEALTH MESH // LINEAGE",
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(0.dp))
                    .background(Color.Black.copy(alpha = 0.3f))
                    .padding(8.dp)
            ) {
                Text(
                    ">>> SYNCHRONIZING $0/EPOCH ASSETS...",
                    color = Color.Gray,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        BreathingEdgeGlow(systemStability = 1.0f)
    }
}
