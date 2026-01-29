package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VpnLock
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.SettingsSystemDaydream
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.aurakai.auraframefx.ui.theme.KaiNeonGreen
import dev.aurakai.auraframefx.ui.theme.SovereignTeal

/**
 * 🛡️ KAI'S ROOT COMMAND WORKSPACE
 * High-fidelity hub for VPN, AdBlock, Gate System, Scan, and Monitoring.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KaiRootWorkspaceScreen(
    onBack: () -> Unit
) {
    val rootTools = listOf(
        RootTool("VPN", "Secure encrypted tunnel", Icons.Default.VpnLock, "file:///sdcard/Pictures/Screenshots/kai_root_vpn.jpg"),
        RootTool("ADBLOCK", "DNS-level ad filtration", Icons.Default.Block, "file:///sdcard/Pictures/Screenshots/kai_root_adblock.jpg"),
        RootTool("GATE SYSTEM", "Inbound/Outbound traffic gates", Icons.Default.SettingsSystemDaydream, "file:///sdcard/Pictures/Screenshots/kai_root_gatesystem.jpg"),
        RootTool("SCAN", "Neural integrity & file scan", Icons.Default.Radar, "file:///sdcard/Pictures/Screenshots/kai_gate_sentinel.jpg"),
        RootTool("MONITORING", "Real-time substrate performance", Icons.Default.MonitorHeart, "file:///sdcard/Pictures/Screenshots/IMG_20260128_141756.png")
    )

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text("KAI'S ROOT COMMAND", color = KaiNeonGreen, fontWeight = FontWeight.ExtraBold) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = KaiNeonGreen)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            items(rootTools) { tool ->
                RootToolCard(tool)
            }
        }
    }
}

data class RootTool(
    val name: String,
    val description: String,
    val icon: ImageVector,
    val imagePath: String
)

@Composable
fun RootToolCard(tool: RootTool) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, KaiNeonGreen.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            .clickable { /* Logic */ }
    ) {
        // High-Fi Background Asset
        AsyncImage(
            model = tool.imagePath,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        
        // Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                    )
                )
        )
        
        // Tool Info
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = tool.name,
                    color = KaiNeonGreen,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = tool.description,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
            }
            
            Icon(
                imageVector = tool.icon,
                contentDescription = null,
                tint = KaiNeonGreen,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
