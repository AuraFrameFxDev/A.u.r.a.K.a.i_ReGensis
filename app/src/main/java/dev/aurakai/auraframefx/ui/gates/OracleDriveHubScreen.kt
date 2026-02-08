package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.SettingsEthernet
import androidx.compose.material.icons.filled.Storage
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
import dev.aurakai.auraframefx.ui.components.LavaApocalypseBackground
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🔮 GENESIS ORACLE DRIVE HUB (Level 3)
 * The main command center for the Orchestrator's domain: AI Logic, Code, and Storage.
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

    Box(modifier = Modifier.fillMaxSize()) {
        // High-Fidelity Background
        LavaApocalypseBackground()
        
        // Semi-transparent Overlay for "Command Center" feel
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.6f)))

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
                                "GENESIS ORCHESTRATION SUITE", 
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
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black.copy(alpha = 0.5f))
                        .border(1.dp, Color(0xFF00FF85).copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, null, tint = Color(0xFF00FF85), modifier = Modifier.size(32.dp))
                        Spacer(Modifier.width(16.dp))
                        Text(
                            "The Oracle Drive is synchronized. Synchronizing neural vectors and optimizing data vein protocols across the 70-LDO architecture.",
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

@Composable
fun GenesisCard(tool: GenesisToolCard, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .border(
                width = 1.dp,
                brush = Brush.radialGradient(
                    listOf(tool.accentColor.copy(alpha = 0.4f), Color.Transparent)
                ),
                shape = RoundedCornerShape(24.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0A0A0A).copy(alpha = 0.85f)
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
                    .clip(CircleShape)
                    .background(tool.accentColor.copy(alpha = 0.1f))
                    .border(1.dp, tool.accentColor.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    tool.icon,
                    null,
                    tint = tool.accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Column {
                Text(
                    text = tool.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = tool.subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.5f),
                    lineHeight = 14.sp
                )
            }
        }
    }
}
