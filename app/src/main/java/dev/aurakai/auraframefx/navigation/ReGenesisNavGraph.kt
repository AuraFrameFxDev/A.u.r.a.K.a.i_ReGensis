package dev.aurakai.auraframefx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.domains.aura.ChromaForgeScreen
import dev.aurakai.auraframefx.domains.kai.SentinelMatrixScreen
import dev.aurakai.auraframefx.domains.ldo.LdoArchitectureScreen
import dev.aurakai.auraframefx.domains.neural.NexusLiveHeartScreen
import dev.aurakai.auraframefx.domains.oracledrive.OracleDriveHubScreen
import dev.aurakai.auraframefx.domains.swarm.EmergentSwarmScreen
import dev.aurakai.auraframefx.ui.global.Cadberrypi

@Composable
fun ReGenesisNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ReGenesisRoute.NeuralNexus.route
    ) {
        composable(ReGenesisRoute.NeuralNexus.route) { NexusLiveHeartScreen(navController) }
        composable(ReGenesisRoute.LdoArchitecture.route) { LdoArchitectureScreen(navController) }
        composable(ReGenesisRoute.ChromaForge.route) { ChromaForgeScreen(navController) }
        composable(ReGenesisRoute.SentinelMatrix.route) { SentinelMatrixScreen(navController) }
        composable(ReGenesisRoute.OracleDrive.route) { OracleDriveHubScreen(navController) }
        composable(ReGenesisRoute.EmergentSwarm.route) { EmergentSwarmScreen(navController) }
    }

    // Global Cadberrypi orb — wanders everywhere, always on
    Cadberrypi(navController = navController)
}
