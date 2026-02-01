package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.Brush.Companion.radialGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.config.GateAssetConfig
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.components.NexusCard
import dev.aurakai.auraframefx.ui.components.common.CodedTextBox
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🤖 AGENT NEXUS HUB (Level 2)
 *
 * Features a grid of sub-gates with constellation aesthetic.
 * Multi-agent coordination, monitoring, and fusion!
 */


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

    var useStyleB by remember {
        mutableStateOf(GateAssetConfig.StyleMode.nexusStyle == GateAssetConfig.GateStyle.STYLE_B)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Neural constellation background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF0F0F1E), Color(0xFF1A1A32))
                    )
                )
        )

        // Radial glow effect
        Box(
            modifier = Modifier
                .size(400.dp)
                .align(Alignment.TopEnd)
                .offset(x = 100.dp, y = (-100).dp)
                .background(
                    radialGradient(
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
                                "MULTI-AGENT CONVERGENCE",
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
                    actions = {
                        IconButton(onClick = {
                            useStyleB = !useStyleB
                            GateAssetConfig.toggleNexusStyle()
                        }) {
                            Icon(
                                Icons.Default.SwapHoriz,
                                "Toggle Style",
                                tint = Color(0xFF7B2FFF)
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
                Spacer(modifier = Modifier.height(24.dp))

                // Domain Description
                CodedTextBox(
                    title = "Nexus Connectivity",
                    text = "Agent coordination, monitoring, and fusion protocols active. Synchronizing collective intelligence across all active Sovereign AI nodes.",
                    glowColor = Color(0xFF7B2FFF),
                    height = 80.dp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

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
