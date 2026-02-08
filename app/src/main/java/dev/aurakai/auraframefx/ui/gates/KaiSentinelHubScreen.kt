package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.components.IcyTundraBackground
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🛡️ KAI SENTINEL HUB (Level 3)
 * The main control panel for the Sentinel Fortress ecosystem.
 * Consolidates ROM management, Bootloader, and Root protocols.
 */

data class SentinelToolCard(
    val title: String,
    val subtitle: String,
    val destination: NavDestination,
    val icon: ImageVector,
    val isWired: Boolean = true,
    val accentColor: Color = Color(0xFFFF3366)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KaiSentinelHubScreen(navController: NavController) {
    
    val tools = listOf(
        SentinelToolCard(
            title = "ROM Flasher",
            subtitle = "Partition & Image Management",
            destination = NavDestination.ROMFlasher,
            icon = Icons.Default.SystemUpdate,
            accentColor = Color(0xFFFF3366)
        ),
        SentinelToolCard(
            title = "Bootloader",
            subtitle = "Lock/Unlock & Fastboot Tools",
            destination = NavDestination.Bootloader,
            icon = Icons.Default.Security,
            accentColor = Color(0xFFFF1111)
        ),
        SentinelToolCard(
            title = "Sovereign Modules",
            subtitle = "Magisk, LSPosed & Shizuku",
            destination = NavDestination.ModuleManager,
            icon = Icons.Default.Build,
            accentColor = Color(0xFF00FF85)
        ),
        SentinelToolCard(
            title = "Recovery",
            subtitle = "OrangeFox & TWRP Protocols",
            destination = NavDestination.RecoveryTools,
            icon = Icons.Default.Settings,
            accentColor = Color(0xFF00E5FF)
        ),
        SentinelToolCard(
            title = "Sovereign Shield",
            subtitle = "Zero-Telemetry & Ad-Block Protocols",
            destination = NavDestination.SecurityCenter,
            icon = Icons.Default.Shield,
            accentColor = Color(0xFFFF1111)
        ),
        SentinelToolCard(
            title = "Live Editor",
            subtitle = "On-the-fly System Edits",
            destination = NavDestination.LiveROMEditor,
            icon = Icons.Default.Build,
            accentColor = Color(0xFFFFCC00)
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // High-Fidelity Background
        IcyTundraBackground()
        
        // Semi-transparent Overlay for "Fortress" feel
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { 
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "SENTINEL FORTRESS", 
                                fontFamily = LEDFontFamily, 
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 2.sp
                            )
                            Text(
                                "KAI SECURITY & SYSTEM SUITE", 
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFFF3366)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Header Info
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.4f))
                        .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Shield, null, tint = Color(0xFFFF3366), modifier = Modifier.size(32.dp))
                        Spacer(Modifier.width(16.dp))
                        Text(
                            "Fortress Access Granted. Monitoring system integrity and root permissions. All operations are logged to the System Journal.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(tools) { tool ->
                        SentinelCard(tool = tool) {
                            if (tool.isWired) {
                                navController.navigate(tool.destination.route)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SentinelCard(tool: SentinelToolCard, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    listOf(tool.accentColor.copy(alpha = 0.6f), Color.Transparent)
                ),
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF121212).copy(alpha = 0.8f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(tool.accentColor.copy(alpha = 0.15f))
                    .border(1.dp, tool.accentColor.copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    tool.icon,
                    null,
                    tint = tool.accentColor,
                    modifier = Modifier.size(26.dp)
                )
            }
            
            Column {
                Text(
                    text = tool.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = tool.subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }
    }
}
