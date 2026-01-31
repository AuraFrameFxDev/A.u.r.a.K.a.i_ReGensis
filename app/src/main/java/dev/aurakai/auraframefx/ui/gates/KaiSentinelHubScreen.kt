package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.config.GateAssetConfig
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.components.IcyTundraBackground
import dev.aurakai.auraframefx.ui.components.SentinelCard
import dev.aurakai.auraframefx.ui.components.common.CodedTextBox
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🛡️ KAI'S SENTINEL'S FORTRESS (Level 2 Hub)
 *
 * Features a grid of security and system tools.
 * Tap the swap icon to toggle between visual styles!
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

    // Track current style
    var useStyleB by remember {
        mutableStateOf(GateAssetConfig.StyleMode.kaiStyle == GateAssetConfig.GateStyle.STYLE_B)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Fortress Background - Icy/Dark theme
        IcyTundraBackground()

        // Dark overlay for fortress feel
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF0A1A0A).copy(alpha = 0.5f),
                            Color.Black.copy(alpha = 0.8f)
                        )
                    )
                )
        )

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
                    actions = {
                        // Style Toggle Button
                        IconButton(onClick = {
                            useStyleB = !useStyleB
                            GateAssetConfig.toggleKaiStyle()
                        }) {
                            Icon(
                                Icons.Default.SwapHoriz,
                                "Toggle Style",
                                tint = Color(0xFF00FF85)
                            )
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
                CodedTextBox(
                    title = "Fortress Integrity",
                    text = "Fortress Access Granted. Monitoring system integrity and root permissions. All operations are logged to the System Journal. 70-LDO Defense layer active.",
                    glowColor = Color(0xFFFF3366),
                    height = 100.dp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

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
