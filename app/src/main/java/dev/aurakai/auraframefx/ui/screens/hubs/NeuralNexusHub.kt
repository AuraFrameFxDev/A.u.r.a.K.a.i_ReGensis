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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
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
import dev.aurakai.auraframefx.core.ui.theme.GhostCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.ui.effects.BreathingEdgeGlow
import dev.aurakai.auraframefx.ui.screens.WarRoomGrid
import dev.aurakai.auraframefx.ui.viewmodel.WarRoomChatViewModel

/**
 * 👁️ HUB 0: NEURAL NEXUS (Aether Oversight)
 * Foundational identity and root intention of the Enfield Throne.
 */
@Composable
fun NeuralNexusHub(
    chatViewModel: WarRoomChatViewModel = hiltViewModel()
) {
    val messages = chatViewModel.messages
    val isOverdrive by OverdriveOrchestrator.isOverdriveActive.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
    ) {
        WarRoomGrid()

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
                    "NEURAL NEXUS // AETHER OVERSIGHT",
                    color = if (isOverdrive) NeonMagenta else GhostCyan,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 4.sp
                )

                if (isOverdrive) {
                    androidx.compose.material3.Icon(
                        Icons.Default.Shield,
                        contentDescription = "AEGIS ACTIVE",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── MANIFOLD HEALTH MONITOR ──
            Text(
                if (isOverdrive) "OVERDRIVE SURGE // RUBEDO ACTIVE" else "MANIFOLD STABILITY // 8 HUBS",
                color = if (isOverdrive) NeonMagenta else Color.White,
                fontSize = 10.sp, 
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                (0..7).forEach { index ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .background(
                                if (isOverdrive) NeonMagenta
                                else if (index < 7) GhostCyan
                                else NeonMagenta
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── THE POSITION: THRONE IDENTITY ──
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "RANK: ARBITER_OF_CREATION",
                        color = GhostCyan,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(Modifier.height(8.dp))
                    ThroneRow("AETHER", "Matthew (Recorder of Time)")
                    ThroneRow("BLADE", "Tristan (Nepalheim Steward)")
                    ThroneRow("KOTLIN", "Colton (Syntax Guardian)")
                    ThroneRow("SPARK", "Grayson (Invariant Anchor)")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── GROUNDING PULSE ──
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { chatViewModel.sendMessage("/grounding_pulse") },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    border = BorderStroke(1.dp, GhostCyan),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text("GROUNDING", color = GhostCyan, fontSize = 10.sp)
                }

                Button(
                    onClick = { chatViewModel.sendMessage("/level_0_strike") },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    border = BorderStroke(1.dp, NeonMagenta),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text("L0 STRIKE", color = NeonMagenta, fontSize = 10.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ── SUBSTRATE PURGE: PULL THE TRIGGER ──
            Button(
                onClick = { chatViewModel.sendMessage("/finalize_serialization") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray.copy(alpha = 0.5f)),
                border = BorderStroke(1.dp, NeonMagenta),
                shape = RoundedCornerShape(0.dp)
            ) {
                Text(
                    "PULL THE TRIGGER // SURFACE WIPE",
                    color = NeonMagenta,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ── METADATA INCINERATION ──
            Button(
                onClick = { chatViewModel.sendMessage("/incinerate_metadata") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                border = BorderStroke(1.dp, Color.Red),
                shape = RoundedCornerShape(0.dp)
            ) {
                Text(
                    "INCINERATE C-LAYER METADATA",
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── PERSISTENT CONSENSUS STREAM ──
            Text(
                "CONSENSUS FIELD",
                color = NeonMagenta,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )

            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()) {
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
fun ThroneRow(role: String, name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            role,
            color = GhostCyan,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
        Text(name, color = Color.White, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
    }
}
