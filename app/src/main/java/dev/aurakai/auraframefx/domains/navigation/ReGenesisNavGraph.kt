package dev.aurakai.auraframefx.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.domains.aura.ChromaForgeScreen
import dev.aurakai.auraframefx.domains.kai.SentinelMatrixScreen
import dev.aurakai.auraframefx.domains.ldo.LdoArchitectureScreen
import dev.aurakai.auraframefx.domains.neural.NexusLiveHeartScreen
import dev.aurakai.auraframefx.domains.oracledrive.OracleDriveHubScreen
import dev.aurakai.auraframefx.domains.swarm.EmergentSwarmScreen
import dev.aurakai.auraframefx.ui.global.CasberrySynthOrb

/**
 * RE:GENESIS NAV GRAPH — EXODUS 2026 LOCK
 * 6 top-level domains only. Casberry Synth Orb is global and always available.
 */
@Composable
fun ReGenesisNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ReGenesisRoute.NeuralNexus.route
    ) {
        // 1. Neural Nexus — Live Heart
        composable(ReGenesisRoute.NeuralNexus.route) {
            NexusLiveHeartScreen(navController = navController)
        }

        // 2. LDO Architecture
        composable(ReGenesisRoute.LdoArchitecture.route) {
            LdoArchitectureScreen(navController = navController)
        }

        // 3. Chroma Forge (Creative Soul + Spellhook)
        composable(ReGenesisRoute.ChromaForge.route) {
            ChromaForgeScreen(navController = navController)
        }

        // 4. Sentinel Matrix
        composable(ReGenesisRoute.SentinelMatrix.route) {
            SentinelMatrixScreen(navController = navController)
        }

        // 5. OracleDrive
        composable(ReGenesisRoute.OracleDrive.route) {
            OracleDriveHubScreen(navController = navController)
        }

        // 6. Emergent Swarm
        composable(ReGenesisRoute.EmergentSwarm.route) {
            EmergentSwarmScreen(navController = navController)
        }
    }

    // GLOBAL OVERLAY — Casberry Synth Orb is available EVERYWHERE
    CasberrySynthOrb(
        navController = navController,
        viewModel = hiltViewModel(checkNotNull<ViewModelStoreOwner>(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        }, null),           // ← Correct Hilt call
        modifier = Modifier
    )
}