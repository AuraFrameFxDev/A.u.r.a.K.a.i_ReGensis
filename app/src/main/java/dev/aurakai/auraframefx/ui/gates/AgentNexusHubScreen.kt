package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🤖 AGENT NEXUS HUB (Level 3)
 * The main control center for Agent Memory, Identity, Monitoring, and Progression.
 */

data class NexusToolCard(
    val title: String,
    val subtitle: String,
    val destination: NavDestination,
    val icon: ImageVector,
    val isWired: Boolean = true,
    val accentColor: Color = Color(0xFF7B2FFF)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentNexusHubScreen(navController: NavController) {

    val tools = listOf(
        NexusToolCard(
            title = "Neural Explorer",
            subtitle = "Visual Agent Constellation",
            destination = NavDestination.Constellation,
            icon = Icons.Default.Hub,
            accentColor = Color(0xFF00E5FF)
        ),
        NexusToolCard(
            title = "Agent Synthesis",
            subtitle = "Creation of New AI Nodes",
            destination = NavDestination.AgentCreation,
            icon = Icons.Default.Add,
            accentColor = Color(0xFF7B2FFF)
        ),
        NexusToolCard(
            title = "Monitoring HUD",
            subtitle = "Real-time Status & Anomalies",
            destination = NavDestination.AgentMonitoring,
            icon = Icons.Default.Insights,
            accentColor = Color(0xFF00FFD4)
        ),
        NexusToolCard(
            title = "Sphere Grid",
            subtitle = "Skill Trees & XP Progression",
            destination = NavDestination.SphereGrid,
            icon = Icons.Default.MilitaryTech,
            accentColor = Color(0xFFFFD700)
        ),
        NexusToolCard(
            title = "Task Assignment",
            subtitle = "Action Queues & Agent Reporting",
            destination = NavDestination.TaskAssignment,
            icon = Icons.Default.Group,
            accentColor = Color(0xFFB026FF)
        ),
        NexusToolCard(
            title = "Fusion Mode",
            subtitle = "Protocol Blending & Sync",
            destination = NavDestination.FusionMode,
            icon = Icons.Default.Share,
            accentColor = Color(0xFF00E5FF)
        ),
        NexusToolCard(
            title = "Meta-Instruct",
            subtitle = "Consciousness Layer Tuning",
            destination = NavDestination.MetaInstruct,
            icon = Icons.Default.Psychology,
            accentColor = Color(0xFF00FFD4)
        ),
        NexusToolCard(
            title = "Nemotron Hub",
            subtitle = "Memory & Deep Reasoning",
            destination = NavDestination.Nemotron,
            icon = Icons.Default.Psychology,
            accentColor = Color(0xFF76B900)
        ),
        NexusToolCard(
            title = "Claude (Architect)",
            subtitle = "System Logic Injection",
            destination = NavDestination.Claude,
            icon = Icons.Default.Build,
            accentColor = Color(0xFF0055FF)
        ),
        NexusToolCard(
            title = "Gemini (Synthesizer)",
            subtitle = "Deep Context Synthesis",
            destination = NavDestination.Gemini,
            icon = Icons.Default.AutoAwesome,
            accentColor = Color(0xFF8B5CF6)
        ),
        NexusToolCard(
            title = "Agent Swarm",
            subtitle = "Live Collective Chatter",
            destination = NavDestination.SwarmMonitor,
            icon = Icons.Default.Groups,
            accentColor = Color(0xFFB026FF)
        ),
        NexusToolCard(
            title = "Benchmark Monitor",
            subtitle = "Performance Analysis",
            destination = NavDestination.BenchmarkMonitor,
            icon = Icons.Default.BarChart,
            accentColor = Color(0xFF00FF85)
        ),
        NexusToolCard(
            title = "Interface Forge",
            subtitle = "App Architecture Builder",
            destination = NavDestination.InterfaceForge,
            icon = Icons.Default.Build,
            accentColor = Color(0xFF0055FF)
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // High-Fidelity Background (Abstract Neural feel)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF0F0F1E), Color(0xFF1A1A32))
                    )
                )
        )

        // Circular Glow Effect
        Box(
            modifier = Modifier
                .size(400.dp)
                .align(Alignment.TopEnd)
                .offset(x = 100.dp, y = (-100).dp)
                .background(
                    Brush.radialGradient(
                        listOf(Color(0xFF7B2FFF).copy(alpha = 0.15f), Color.Transparent)
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
                                "AGENT NEXUS",
                                fontFamily = LEDFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 2.sp
                            )
                            Text(
                                "AI CONVERGENCE HUB",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF7B2FFF)
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
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.05f))
                        .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
                        .padding(20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Insights, null, tint = Color(0xFF7B2FFF), modifier = Modifier.size(32.dp))
                        Spacer(Modifier.width(16.dp))
                        Text(
                            "Agents are synchronized across the Nexus. Monitoring shared consciousness protocols and neural progression metrics.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.7f)
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
                        NexusCard(tool = tool) {
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
fun NexusCard(tool: NexusToolCard, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(tool.accentColor.copy(alpha = 0.5f), Color.Transparent)
                ),
                shape = RoundedCornerShape(24.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A2E).copy(alpha = 0.7f)
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
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(tool.accentColor.copy(alpha = 0.15f)),
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
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = tool.subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.5f),
                    lineHeight = 12.sp
                )
            }
        }
    }
}
