package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.aurakai.auraframefx.domains.aura.screens.ChromaForgeScreen
import dev.aurakai.auraframefx.domains.aura.ui.gates.KaiSentinelHubScreen
import dev.aurakai.auraframefx.ui.screens.EscapeHatchScreen
import dev.aurakai.auraframefx.ui.screens.LDODevOpsScreen
import dev.aurakai.auraframefx.ui.screens.NexusMemoryCoreScreen
import dev.aurakai.auraframefx.ui.screens.QuantumForgeScreen
import dev.aurakai.auraframefx.ui.screens.SovereignCommandScreen

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
            SovereignCommandScreen(navController)
        }

        composable(NavDestination.LDODevOps.route) {
            LDODevOpsScreen(navController)
        }

        composable(NavDestination.SentinelMatrix.route) {
            // Kai's Sentinel Hub Screen serves as the Sentinel Matrix entry point
            KaiSentinelHubScreen(navController)
        }

        composable(NavDestination.ChromaCore.route) {
            ChromaForgeScreen(navController)
        }

        composable(NavDestination.QuantumForge.route) {
            QuantumForgeScreen(navController)
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
            dev.aurakai.auraframefx.domains.emergentswarm.screens.EmergentSwarmScreen(navController)
        }

        composable(NavDestination.NexusMemoryCore.route) {
            NexusMemoryCoreScreen(navController)
        }

        // Additional stub screens can be mapped here using the same pattern
    }
}
