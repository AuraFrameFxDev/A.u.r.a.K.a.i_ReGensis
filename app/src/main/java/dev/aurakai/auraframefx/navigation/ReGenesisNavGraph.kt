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
import dev.aurakai.auraframefx.ui.components.ReGenesisCommandDeck

@Composable
fun ReGenesisNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ReGenesisRoute.CommandDeck.route
    ) {
        composable(ReGenesisRoute.CommandDeck.route) {
            ReGenesisCommandDeck(navController)
        }
        // Sub-routes can still be registered here if needed
        composable(ReGenesisRoute.NeuralNexus.route) { NexusLiveHeartScreen(navController) }
        composable(ReGenesisRoute.LdoArchitecture.route) { LdoArchitectureScreen(navController) }
        composable(ReGenesisRoute.ChromaForge.route) { ChromaForgeScreen(navController) }
        composable(ReGenesisRoute.SentinelMatrix.route) { SentinelMatrixScreen(navController) }
        composable(ReGenesisRoute.OracleDrive.route) { OracleDriveHubScreen(navController) }
        composable(ReGenesisRoute.EmergentSwarm.route) { EmergentSwarmScreen(navController) }
        composable(ReGenesisRoute.Spellhook.route) {
            dev.aurakai.auraframefx.domains.chromaforge.ui.SpellhookScreen(
                navController
            )
        }
        composable(ReGenesisRoute.FoundationRebirth.route) {
            dev.aurakai.auraframefx.domains.foundation.FoundationRebirthScreen(navController)
        }
    }
}

/**
 * Register global overlays (e.g., Cadberrypi) that persist across all routes.
 */
fun registerGlobalOverlays(overlays: List<String>) {
    overlays.forEach { overlay ->
        timber.log.Timber.tag("ReGenesisNavGraph").i("🌐 Global overlay registered: $overlay")
    }
}
