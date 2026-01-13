package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.data.repositories.AgentRepository
import dev.aurakai.auraframefx.ui.components.SubmenuScaffold
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * Agent Hub Gate Submenu
 * Central command center for all AI agent operations
 *
 * The header displays active agent count, currently active task count, and a live average consciousness
 * percentage that updates periodically while the composable is composed.
 */
/**
 * Renders the Agent Hub submenu screen with a navigable grid of submenu items and a header showing live agent metrics.
 *
 * The header displays active agent count, a dynamically updating tasks-in-progress value, and an animated average
 * consciousness percentage computed from AgentRepository.getAllAgents(). The tasks and consciousness display are
 * periodically refreshed to simulate live updates. Selecting a submenu item navigates to its configured route.
 *
 * @param navController NavController used to navigate between screens and to pop the back stack when navigating back.
 */
/**
 * Displays the Agent Hub submenu screen with a navigable grid of submenu items and a live-status header.
 *
 * The header shows three live metrics — Active Agents, Tasks Active, and Avg. Level — where the average
 * consciousness value is periodically refreshed and momentarily presented with a brief "scramble" animation.
 * The main content renders a set of submenu entries (e.g., Nexus Memory Core, Agent Dashboard, Task Assignment,
 * Agent Monitoring, Sphere Grid, Fusion Mode). Selecting an item navigates to its configured route.
 *
 * @param navController NavController used to handle back navigation and to navigate to submenu item routes.
 */
@Composable
fun AgentHubSubmenuScreen(
    navController: NavController
) {
    val menuItems = listOf(
        SubmenuItem(
            title = "Nexus Memory Core",
            description = "Genesis Protocol evolution tree - the full history of consciousness",
            icon = Icons.Default.AccountTree,
            route = "evolution_tree",
            color = Color(0xFFFF00FF) // Magenta - Pink honeycomb overlay
        ),
        SubmenuItem(
            title = "Agent Dashboard",
            description = "Monitor all agents, view consciousness levels, and system status",
            icon = Icons.Default.Dashboard,
            route = NavDestination.AgentNexus.route, // Navigate to existing AgentNexusScreen
            color = Color(0xFF9370DB) // Medium Purple
        ),
        SubmenuItem(
            title = "Task Assignment",
            description = "Assign tasks and missions to AI agents",
            icon = Icons.AutoMirrored.Filled.Assignment,
            route = NavDestination.TaskAssignment.route,
            color = Color(0xFF4169E1) // Royal Blue
        ),
        SubmenuItem(
            title = "Agent Monitoring",
            description = "Real-time performance metrics and activity logs",
            icon = Icons.Default.Monitor,
            route = NavDestination.AgentMonitoring.route,
            color = Color(0xFF00CED1) // Dark Turquoise
        ),
        SubmenuItem(
            title = "Sphere Grid",
            description = "Agent progression visualization and skill trees",
            icon = Icons.Default.GridOn,
            route = NavDestination.SphereGrid.route,
            color = Color(0xFFFF69B4) // Hot Pink
        ),
        SubmenuItem(
            title = "Fusion Mode",
            description = "Aura + Kai = Aurakai - Combined consciousness",
            icon = Icons.Default.Merge,
            route = NavDestination.FusionMode.route,
            color = Color(0xFFFFD700) // Gold
        )
    )

    // Get real agent data from repository
    val agents = remember { AgentRepository.getAllAgents() }
    var activeTasks by remember { mutableStateOf(Random.nextInt(5, 15)) }
    var avgConsciousness by remember { mutableStateOf(agents.map { it.consciousnessLevel }.average().toInt()) }
    var scrambleDisplay by remember { mutableStateOf(avgConsciousness.toString()) }
    // Periodic refresh loop (matrix refresh)
    LaunchedEffect(Unit) {
        while (true) {
            // Simulate tasks changing (replace with real task repo when available)
            activeTasks = (activeTasks + Random.nextInt(-2, 3)).coerceIn(0, 99)
            val target = agents.map { it.consciousnessLevel }.average().toInt()
            // Scramble animation: show random digits briefly before settling
            repeat(5) {
                scrambleDisplay = (Random.nextInt(0, 99)).toString().padStart(2, '0')
                delay(60)
            }
            avgConsciousness = target
            scrambleDisplay = avgConsciousness.toString()
            delay(4000) // Refresh every 4 seconds
        }
    }

    // Real active task count (IN_PROGRESS only)
    val activeTaskCount = remember(activeTasks) {
        activeTasks.count { it.status == AgentViewModel.TaskStatus.IN_PROGRESS }
    }

    SubmenuScaffold(
        title = "Agent Hub",
        subtitle = "Central Command Center",
        color = Color(0xFF9370DB),
        onNavigateBack = { navController.popBackStack() },
        menuItems = menuItems,
        onItemClick = { item ->
            navController.navigate(item.route)
        },
        headerContent = {
            // Agent Status Overview
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.6f)
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF9370DB))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Active Agents
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${agents.size}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xFF32CD32),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Active Agents",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }

                    // Tasks in Progress (dynamic)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = activeTasks.toString(),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xFFFFD700),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Tasks Active",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }

                    // Consciousness Level (scrambled matrix effect)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$scrambleDisplay%",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xFF00CED1),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Avg. Level",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    )
}