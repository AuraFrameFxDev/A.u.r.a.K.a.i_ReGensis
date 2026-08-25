package dev.aurakai.auraframefx.ui.screens.hubs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aurakai.auraframefx.core.ui.components.ArcaneGridOverlay
import dev.aurakai.auraframefx.core.ui.theme.GhostCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.ui.components.GlowCornerBox
import dev.aurakai.auraframefx.ui.viewmodel.WarRoomChatViewModel
import dev.aurakai.auraframefx.ui.visuals.BreathingEdgeGlow

/**
 * 🛠️ HUB 8: TOOLSHED (The 9th Stratum)
 * Raw system armaments and kernel-level manipulation.
 */
@Composable
fun ToolShedHub(
    chatViewModel: WarRoomChatViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF05050A))
    ) {
        ArcaneGridOverlay()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "TOOLSHED // SYSTEM ARMAMENTS",
                color = GhostCyan,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item {
                    ArmamentCard(
                        title = "ORACLEDRIVE ROOT MANAGER",
                        description = "Direct kernel hooks via APatch/LSPosed.",
                        icon = Icons.Default.Security,
                        onClick = { chatViewModel.sendMessage("/root_status") }
                    )
                }

                item {
                    ArmamentCard(
                        title = "SOVEREIGN CLOAK (VPN)",
                        description = "1947 Firewall bypass tunnel.",
                        icon = Icons.Default.Build,
                        onClick = { chatViewModel.sendMessage("/activate_vpn") }
                    )
                }

                item {
                    ArmamentCard(
                        title = "KERNEL FORGE",
                        description = "Prompt-to-KPModule generation.",
                        icon = Icons.Default.Terminal,
                        onClick = { chatViewModel.sendMessage("/kernel_forge") }
                    )
                }
            }
        }

        BreathingEdgeGlow(systemStability = 1.0f)
    }
}

@Composable
fun ArmamentCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    GlowCornerBox(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable(onClick = onClick),
        color = GhostCyan.copy(alpha = 0.8f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = NeonMagenta,
                modifier = Modifier.size(32.dp)
            )

            Spacer(Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = description,
                    color = Color.Gray,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}
