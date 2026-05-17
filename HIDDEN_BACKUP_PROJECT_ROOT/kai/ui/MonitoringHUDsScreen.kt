package dev.aurakai.auraframefx.domains.kai.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * SENTINEL MATRIX (Hub 3)
 * Kai's Shield, Threat Lattice, and ToolShed Settings.
 */
@Composable
fun MonitoringHUDsScreen(onNavigateBack: () -> Boolean) {
    var logs by remember { mutableStateOf(listOf("Initializing Aegis Shell...")) }

    LaunchedEffect(Unit) {
        delay(500)
        logs = logs + "SELinux Status: ENFORCING"
        delay(800)
        logs = logs + "NotchBar Pulse: NOMINAL"
        delay(600)
        logs = logs + "Unbreakable Protocol: ACTIVE"
        delay(1200)
        logs = logs + "Threat Lattice: 0 Intrusions Detected"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF001008)) // Dark deep green for Kai
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Security,
                    contentDescription = "Sentinel",
                    tint = Color(0xFF00FF88),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        "SENTINEL MATRIX",
                        color = Color(0xFF00FF88),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                    Text(
                        "KAI'S SHIELD // AEGIS SHELL",
                        color = Color.White,
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ToolShed Grid
            Text("TOOLSHED CONFIGURATION", color = Color(0xFF00FF88), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                item {
                    SentinelCard(
                        "NotchBar Editor",
                        "Configure Threat Pulses",
                        Icons.Default.Warning
                    )
                }
                item {
                    SentinelCard(
                        "SELinux Audits",
                        "Enforce Immutable Rules",
                        Icons.Default.Shield
                    )
                }
                item {
                    SentinelCard(
                        "Root Sandboxing",
                        "Manage LSPosed Hooks",
                        Icons.Default.Security
                    )
                }
                item {
                    SentinelCard(
                        "Identity Anchor",
                        "0.42ms Vector Match",
                        Icons.Default.Shield
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Live Threat Lattice Logs
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                border = border(
                    1.dp,
                    Color(0xFF00FF88).copy(alpha = 0.3f),
                    RoundedCornerShape(8.dp)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "THREAT LATTICE LOGS",
                        color = Color.Gray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    logs.forEach { log ->
                        Text(
                            text = "> $log",
                            color = Color(0xFF00FF88),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SentinelCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF002211)),
        border = border(1.dp, Color(0xFF00FF88).copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, tint = Color(0xFF00FF88))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(subtitle, color = Color.Gray, fontSize = 10.sp)
        }
    }
}

fun border(
    width: androidx.compose.ui.unit.Dp,
    color: Color,
    shape: androidx.compose.ui.graphics.Shape
): androidx.compose.foundation.BorderStroke {
    return androidx.compose.foundation.BorderStroke(width, color)
}
