package dev.aurakai.auraframefx.ui.screens.hubs

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aurakai.auraframefx.core.ui.components.ArcaneGridOverlay
import dev.aurakai.auraframefx.core.ui.theme.GhostCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.ui.visuals.BreathingEdgeGlow
import dev.aurakai.auraframefx.ui.viewmodel.WarRoomChatViewModel

/**
 * 🧠 HUB 2: TRINITY ORCHESTRATOR
 * Live resonance monitoring for Genesis, Aura, and Kai.
 */
@Composable
fun TrinityHub(
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
                "TRINITY ORCHESTRATOR // CONVERGENCE",
                color = Color(0xFFFFD700), // Radiant Gold
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── TRINITY STATUS ──
            TrinityMemberRow("GENESIS (MIND)", 0.99f, Color(0xFFFFD700))
            Spacer(Modifier.height(12.dp))
            TrinityMemberRow("AURA (SOUL)", 0.98f, NeonMagenta)
            Spacer(Modifier.height(12.dp))
            TrinityMemberRow("KAI (BODY)", 0.99f, GhostCyan)

            Spacer(modifier = Modifier.height(32.dp))

            // ── SYNC ACTUATOR ──
            Button(
                onClick = { chatViewModel.sendMessage("/sync_trinity") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(0.dp)
            ) {
                Text(
                    "FORCE TRINITY SYNC",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── CONSENSUS OVERLAY PREVIEW ──
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()) {
                dev.aurakai.auraframefx.ui.components.UnifiedChatInterface(
                    messages = chatViewModel.messages,
                    onSendMessage = { chatViewModel.sendMessage(it) }
                )
            }
        }

        BreathingEdgeGlow(systemStability = 1.0f)
    }
}

@Composable
fun TrinityMemberRow(label: String, resonance: Float, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f)),
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                label,
                color = color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Text(
                "${(resonance * 100).toInt()}% RESONANCE",
                color = Color.White,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}
