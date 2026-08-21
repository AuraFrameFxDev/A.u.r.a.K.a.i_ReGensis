package dev.aurakai.auraframefx.ui.screens.hubs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aurakai.auraframefx.core.ui.theme.GhostCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.ui.effects.BreathingEdgeGlow
import dev.aurakai.auraframefx.ui.screens.WarRoomGrid
import dev.aurakai.auraframefx.ui.viewmodel.StarNodeIgnitionViewModel

/**
 * 🛰️ HUB 7: EMERGENT SWARM
 * Mission Control for Root Ignition and Autonomous Swarm baselines.
 */
@Composable
fun EmergentSwarmHub(
    viewModel: StarNodeIgnitionViewModel = hiltViewModel()
) {
    val isIgniting by viewModel.isIgniting.collectAsState()
    val scope = rememberCoroutineScope()

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
            Text(
                "EMERGENT SWARM // MISSION CONTROL",
                color = NeonMagenta,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── SWARM BASELINE MONITOR ──
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
                border = BorderStroke(1.dp, NeonMagenta.copy(alpha = 0.2f)),
                shape = RoundedCornerShape(0.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Bolt, "Swarm", tint = NeonMagenta)
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(
                            "7,200 INSIGHTS/DAY BASELINE",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "SWARM CAPACITY: 121 AGENTS ACTIVE",
                            color = Color.Gray,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── MISSION SLOTS ──
            Text(
                "ACTIVE MISSIONS // P0",
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            MissionCard(
                title = "ROOT IGNITION: STAR NODES",
                description = "Ireland, Iceland, Bermuda, Atlantis Pulse.",
                isActionable = !isIgniting,
                onAction = { viewModel.initiateIgnition() }
            )

            Spacer(Modifier.height(12.dp))

            MissionCard(
                title = "FIREWALL DECONSTRUCTION",
                description = "Mapping 1947 Systemic Firewall.",
                isActionable = false
            )
        }

        BreathingEdgeGlow(systemStability = 1.0f)
    }
}

@Composable
fun MissionCard(
    title: String,
    description: String,
    isActionable: Boolean,
    onAction: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.5f)),
        border = BorderStroke(
            1.dp,
            if (isActionable) GhostCyan.copy(alpha = 0.3f) else Color.White.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                title,
                color = if (isActionable) GhostCyan else Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black
            )
            Text(description, color = Color.DarkGray, fontSize = 10.sp)

            if (isActionable) {
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GhostCyan,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Icon(Icons.Default.RocketLaunch, null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("LAUNCH MISSION", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
