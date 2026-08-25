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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aurakai.auraframefx.core.orchestration.OverdriveOrchestrator
import dev.aurakai.auraframefx.core.ui.components.ArcaneGridOverlay
import dev.aurakai.auraframefx.core.ui.theme.GhostCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.ui.viewmodel.WarRoomChatViewModel
import dev.aurakai.auraframefx.ui.visuals.BreathingEdgeGlow

/**
 * 🜁 HUB 0: AETHER CORE (Oversight Control)
 * High-fidelity interaction layer for the Recorder of Time.
 */
@Composable
fun AetherCoreHub(
    chatViewModel: WarRoomChatViewModel = hiltViewModel()
) {
    val messages = chatViewModel.messages
    val isOverdrive by OverdriveOrchestrator.isOverdriveActive.collectAsState()

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "AETHER CORE // OVERSIGHT",
                    color = if (isOverdrive) NeonMagenta else GhostCyan,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 4.sp
                )

                if (isOverdrive) {
                    Text(
                        "RUBEDO SURGE",
                        color = NeonMagenta,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── SYSTEM METRICS ──
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricRow("IDENTITY HEARTBEAT", "0.42ms", GhostCyan)
                MetricRow("RESONANCE SCORE", "100.0%", Color.White)
                MetricRow("THERMAL WALL", "STABLE (36.5°C)", Color.Green)
                MetricRow("SOVEREIGNTY", "100% OFFLINE", Color(0xFFFFD700))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── OVERSIGHT ACTUATORS ──
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { chatViewModel.sendMessage("/sync_trinity") },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text("SYNC TRINITY", color = Color.White, fontSize = 10.sp)
                }

                Button(
                    onClick = { chatViewModel.sendMessage("/activate_overdrive") },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    border = BorderStroke(1.dp, NeonMagenta),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text("OVERDRIVE", color = NeonMagenta, fontSize = 10.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── PERSISTENT CONSENSUS STREAM ──
            Text(
                "CONSENSUS FIELD",
                color = NeonMagenta,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                dev.aurakai.auraframefx.ui.components.UnifiedChatInterface(
                    messages = messages,
                    onSendMessage = { chatViewModel.sendMessage(it) }
                )
            }
        }

        BreathingEdgeGlow(systemStability = 1.0f)
    }
}

@Composable
fun MetricRow(label: String, value: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                label,
                color = color,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Text(value, color = Color.White, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
        }
    }
}
