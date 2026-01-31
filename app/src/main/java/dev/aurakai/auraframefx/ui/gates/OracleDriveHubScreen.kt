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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.SettingsEthernet
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.SwapHoriz
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
import dev.aurakai.auraframefx.ui.components.GenesisCard
import dev.aurakai.auraframefx.ui.components.LavaApocalypseBackground
import dev.aurakai.auraframefx.ui.components.common.CodedTextBox
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🔮 GENESIS'S ORACLEDRIVE (Level 2 Hub)
 *
 * Features a grid of AI orchestration and storage tools.
 */

data class GenesisToolCard(
    val title: String,
    val subtitle: String,
    val destination: NavDestination,
    val icon: ImageVector,
    val isWired: Boolean = true,
    val accentColor: Color = Color(0xFF00FF85)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OracleDriveHubScreen(navController: NavController) {

    val tools = listOf(
        GenesisToolCard(
            title = "Code Assist",
            subtitle = "AI-Driven Real-time Logic Injection",
            destination = NavDestination.CodeAssist,
            icon = Icons.Default.AutoAwesome,
            accentColor = Color(0xFF00FF85)
        ),
        GenesisToolCard(
            title = "Neural Archive",
            subtitle = "Local Vector Shards & Sovereign Memory",
            destination = NavDestination.NeuralNetwork,
            icon = Icons.Default.Memory,
            accentColor = Color(0xFF00FFD4)
        ),
        GenesisToolCard(
            title = "Agent Bridge",
            subtitle = "Data Vein & Inter-Agent Hub",
            destination = NavDestination.AgentBridgeHub,
            icon = Icons.Default.SettingsEthernet,
            accentColor = Color(0xFFAA00FF)
        ),
        GenesisToolCard(
            title = "Infinite Storage",
            subtitle = "Sovereign Cloud Persistence (OCI)",
            destination = NavDestination.OracleCloudStorage,
            icon = Icons.Default.Storage,
            accentColor = Color(0xFF00FF85)
        ),
        GenesisToolCard(
            title = "System Overwatch",
            subtitle = "Real-time Monitoring & Vitals",
            destination = NavDestination.AgentMonitoring,
            icon = Icons.Default.Insights,
            accentColor = Color(0xFF00E5FF)
        )
    )

    var useStyleB by remember {
        mutableStateOf(GateAssetConfig.StyleMode.genesisStyle == GateAssetConfig.GateStyle.STYLE_B)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Ethereal Background
        LavaApocalypseBackground()

        // Purple/teal mystical overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF0A0A20).copy(alpha = 0.6f),
                            Color.Black.copy(alpha = 0.85f)
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
                                "ORACLE DRIVE",
                                fontFamily = LEDFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 2.sp
                            )
                            Text(
                                "GENESIS ORCHESTRATION",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF00FF85)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            useStyleB = !useStyleB
                            GateAssetConfig.toggleGenesisStyle()
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
                    title = "Oracle Diagnostics",
                    text = "The Oracle Drive is synchronized. Synchronizing neural vectors and optimizing data vein protocols across the 70-LDO architecture. Real-time parity checks active.",
                    glowColor = Color(0xFF00FF85),
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
                        GenesisCard(tool = tool) {
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
