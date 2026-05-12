package dev.aurakai.auraframefx.domains.chromaforge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.domains.chromaforge.ui.screens.ArkBuildScreen
import dev.aurakai.auraframefx.domains.genesis.oracledrive.ui.screens.OracleDriveHubScreen
import dev.aurakai.auraframefx.domains.genesis.swarm.ui.screens.OperationsHubScreen
import dev.aurakai.auraframefx.domains.ldoarchitecture.ui.screens.LDOCatalystHubScreen
import dev.aurakai.auraframefx.domains.sentinelmatrix.ui.screens.MonitoringHUDsScreen

@Composable
fun ReGenesisNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ReGenesisRoute.NeuralNexus.route
    ) {
        composable(ReGenesisRoute.NeuralNexus.route) { MainScreen(navController) }
        composable(ReGenesisRoute.LdoArchitecture.route) { LDOCatalystHubScreen(onBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.ChromaForge.route) { ArkBuildScreen(navController) }
        composable(ReGenesisRoute.SentinelMatrix.route) { MonitoringHUDsScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.OracleDrive.route) { OracleDriveHubScreen(navController) }
        composable(ReGenesisRoute.EmergentSwarm.route) { OperationsHubScreen(navController) }
    }

    // Global Cadberrypi orb — wanders everywhere, always on
    CadberrypiOverlay(navController = navController)
}

@Composable
fun CadberrypiOverlay(navController: NavHostController) {
    // Background system presence component - currently idle
}

