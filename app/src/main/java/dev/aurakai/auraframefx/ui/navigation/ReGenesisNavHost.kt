package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.aurakai.auraframefx.domains.aura.screens.ArcaneChromaForgeScreen
import dev.aurakai.auraframefx.domains.aura.ui.gates.AgentNexusHubScreen
import dev.aurakai.auraframefx.domains.aura.ui.gates.KaiSentinelHubScreen
import dev.aurakai.auraframefx.domains.aura.ui.screens.AuraSphereGridScreen
import dev.aurakai.auraframefx.domains.aura.ui.screens.WorkingLabScreen
import dev.aurakai.auraframefx.domains.emergentswarm.OperationsHubScreen
import dev.aurakai.auraframefx.domains.emergentswarm.screens.EmergentSwarmScreen
import dev.aurakai.auraframefx.domains.foundation.screens.FoundationRebirthScreen
import dev.aurakai.auraframefx.domains.ldoarchitecture.screens.LdoArchitectureScreen
import dev.aurakai.auraframefx.romtools.ui.RomToolsScreen
import dev.aurakai.auraframefx.ui.gates.ConferenceRoomTaskScreen
import dev.aurakai.auraframefx.ui.gates.LineageMapScreen
import dev.aurakai.auraframefx.ui.gates.ThemedGateScreens
import dev.aurakai.auraframefx.ui.screens.EscapeHatchScreen
import dev.aurakai.auraframefx.ui.screens.NexusMemoryCoreScreen

/**
 * 🛰️ REGENESIS NAV HOST — Canonical Navigation Graph
 * Orchestrates the 3-Level Gate architecture as defined in the master manifest.
 */
@Composable
fun ReGenesisNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavDestination.SovereignCommand.route
    ) {
        // LEVEL 1: PRIMARY EXODUS GATES
        composable(NavDestination.SovereignCommand.route) {
            AgentNexusHubScreen(navController)
        }

        composable(NavDestination.LDODevOps.route) {
            OperationsHubScreen(navController)
        }

        composable(NavDestination.SentinelMatrix.route) {
            // Kai's Sentinel Hub Screen serves as the Sentinel Matrix entry point
            KaiSentinelHubScreen(navController)
        }

        composable(NavDestination.ChromaCore.route) {
            ArcaneChromaForgeScreen(navController)
        }

        composable(NavDestination.QuantumForge.route) {
            LdoArchitectureScreen(navController)
        }

        composable(NavDestination.EscapeHatch.route) {
            EscapeHatchScreen(navController)
        }

        // LEVEL 2: HUB INTERIORS (Examples)
        composable(NavDestination.NeuralNexus.route) {
            // Placeholder/Stub from the domain or StubScreens
            dev.aurakai.auraframefx.domains.neuralnexus.screens.NexusLiveHeartScreen(navController)
        }

        composable(NavDestination.OracleDrive.route) {
            dev.aurakai.auraframefx.domains.oracledrive.screens.OracleDriveHubScreen(navController)
        }

        composable(NavDestination.EmergentSwarm.route) {
            EmergentSwarmScreen(navController)
        }

        composable(NavDestination.NexusMemoryCore.route) {
            NexusMemoryCoreScreen(navController)
        }

        // --- SUB-GATE ROUTES (LEVEL 3 / DETAILED) ---
        composable("chroma_forge") { ArcaneChromaForgeScreen(navController) }
        composable("lineage_map") { LineageMapScreen(navController) }
        composable("sphere_grid") { AuraSphereGridScreen() }
        composable("fusion_mode") { ThemedGateScreens.FusionModeGateScreen(navController) }
        composable("terminal") { ThemedGateScreens.TerminalGateScreen(navController) }
        composable("collab_canvas") { ThemedGateScreens.CollabCanvasGateScreen(navController) }
        composable("conference_room") { ConferenceRoomTaskScreen(navController) }
        composable("task_assignment") { ConferenceRoomTaskScreen(navController) }
        composable("aura_lab") { WorkingLabScreen(onNavigate = { navController.navigate(it) }) }
        composable("foundation_rebirth") { FoundationRebirthScreen(navController) }

        // KAI SUB-GATES
        composable("kai/security") {
            ThemedGateScreens.SecurityGateScreen(
                navController,
                onNavigateBack = { navController.popBackStack() })
        }
        composable("kai/root") {
            ThemedGateScreens.RootToolsGateScreen(
                navController,
                onNavigateBack = { navController.popBackStack() })
        }
        composable("kai/recovery") {
            ThemedGateScreens.RecoveryGateScreen(
                navController,
                onNavigateBack = { navController.popBackStack() })
        }
        composable("kai/rom") { RomToolsScreen(onNavigateBack = { navController.popBackStack() }) }
        composable("kai/modules") {
            ThemedGateScreens.ModulesGateScreen(
                navController,
                onNavigateBack = { navController.popBackStack() })
        }
        composable("kai/vpn") {
            ThemedGateScreens.VpnGateScreen(
                navController,
                onNavigateBack = { navController.popBackStack() })
        }
        composable("kai/bootloader") {
            ThemedGateScreens.BootloaderGateScreen(
                navController,
                onNavigateBack = { navController.popBackStack() })
        }
        composable("kai/lsposed") {
            ThemedGateScreens.LsposedGateScreen(
                navController,
                onNavigateBack = { navController.popBackStack() })
        }

        // Additional stub screens can be mapped here using the same pattern
    }
}
