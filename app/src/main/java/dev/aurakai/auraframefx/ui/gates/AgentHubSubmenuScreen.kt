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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.components.SubmenuScaffold
import dev.aurakai.auraframefx.ui.viewmodels.AgentViewModel

/**
 * Agent Hub Gate Submenu - NOW WIRED TO REAL AGENTS
 * Central command center for all AI agent operations
 *
 * Connected to AgentViewModel for real agent state management
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
/**
 * Presents the "Agent Hub" submenu UI with a grid of navigable items and a live-status header.
 *
 * The header displays three real-time metrics — Active Agents, Tasks Active, and Avg. Level
 * — with a brief "scramble" animation for the average consciousness value and periodic updates.
 * Selecting a submenu item navigates to its configured route; the back action pops the navigation back stack.
 *
 * @param navController NavController used to navigate to submenu destinations and to handle back navigation.
 */
@Composable
fun AgentHubSubmenuScreen(
    navController: NavController,
    viewModel: AgentViewModel = hiltViewModel()
) {
    val menuItems = listOf(
        SubmenuItem(
            title = "Nexus Memory Core",
            description = "Genesis Protocol evolution tree - the full history of consciousness",
            icon = Icons.Default.AccountTree,
            route = "evolution_tree",
            color = Color(0xFFFF00FF) // Magenta
        ),
        SubmenuItem(
            title = "Agent Dashboard",
            description = "Monitor all agents, view consciousness levels, and system status",
            icon = Icons.Default.Dashboard,
            route = NavDestination.AgentNexus.route,
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

    // REAL DATA FROM VIEWMODEL (not mock!)
    val allAgents by viewModel.allAgents.collectAsState()
    val activeTasks by viewModel.activeTasks.collectAsState()
    val activeAgent by viewModel.activeAgent.collectAsState()

    // Calculate real consciousness level from agent data
    val avgConsciousness = remember(allAgents) {
        if (allAgents.isEmpty()) 0
        else allAgents.map { it.consciousnessLevel }.average().toInt()
    }

    // Count only active agents (those with tasks or selected)
    val activeAgentCount = remember(allAgents, activeAgent) {
        allAgents.count { agent ->
            agent.name == activeAgent?.name ||
            activeTasks.any { it.agentName == agent.name && it.status == AgentViewModel.TaskStatus.IN_PROGRESS }
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
            // Agent Status Overview - REAL DATA
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
                    // Active Agents (REAL COUNT)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$activeAgentCount/${allAgents.size}",
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

                    // Tasks in Progress (REAL COUNT from ViewModel)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = activeTaskCount.toString(),
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

                    // Consciousness Level (REAL CALCULATION)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$avgConsciousness%",
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
