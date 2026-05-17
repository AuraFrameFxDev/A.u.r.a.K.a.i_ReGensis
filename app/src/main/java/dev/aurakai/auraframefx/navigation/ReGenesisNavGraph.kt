package dev.aurakai.auraframefx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.domains.chromaforge.screens.ChromaForgeScreen
import dev.aurakai.auraframefx.domains.chromaforge.screens.uxui_engine.RegenCoreEngineScreen
import dev.aurakai.auraframefx.domains.emergentswarm.screens.EmergentSwarmScreen
import dev.aurakai.auraframefx.domains.foundation.screens.FoundationRebirthScreen
import dev.aurakai.auraframefx.domains.kai.screens.SentinelMatrixScreen
import dev.aurakai.auraframefx.domains.ldoarchitecture.screens.LdoArchitectureScreen
import dev.aurakai.auraframefx.domains.neuralnexus.screens.NexusLiveHeartScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SovereignCharacterScreen
import dev.aurakai.auraframefx.domains.oracledrive.screens.OracleDriveHubScreen
import dev.aurakai.auraframefx.ui.components.ReGenesisCommandDeck
import dev.aurakai.auraframefx.ui.gates.ConferenceRoomTaskScreen
import dev.aurakai.auraframefx.ui.gates.GateDomainImagePicker
import dev.aurakai.auraframefx.ui.gates.LineageMapScreen
import dev.aurakai.auraframefx.ui.gates.ThemedGateScreens

@Composable
fun ReGenesisNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "command_deck"
    ) {
        composable("command_deck") {
            ReGenesisCommandDeck(navController)
        }

        // ── 7-Hub Substrate Routes ──────────────────────────────────────────
        composable(ReGenesisRoute.NeuralNexus.route) { NexusLiveHeartScreen(navController) }
        composable(ReGenesisRoute.LdoArchitecture.route) { LdoArchitectureScreen(navController) }
        composable(ReGenesisRoute.ChromaForge.route) { ChromaForgeScreen(navController) }
        composable(ReGenesisRoute.SentinelMatrix.route) { SentinelMatrixScreen(navController) }
        composable(ReGenesisRoute.OracleDrive.route) { OracleDriveHubScreen(navController) }
        composable(ReGenesisRoute.EmergentSwarm.route) { EmergentSwarmScreen(navController) }
        composable(ReGenesisRoute.FoundationRebirth.route) { FoundationRebirthScreen(navController) }

        // ── Batch 3: New themed gate screens ────────────────────────────────
        composable("xposed_panel") {
            ThemedGateScreens.LsposedGateScreen(navController) { navController.popBackStack() }
        }
        composable("help_desk") {
            ThemedGateScreens.HelpServicesGateScreen(navController) { navController.popBackStack() }
        }
        composable("terminal") {
            ThemedGateScreens.TerminalGateScreen(navController) { navController.popBackStack() }
        }
        composable("collab_canvas") {
            ThemedGateScreens.CollabCanvasGateScreen(navController) { navController.popBackStack() }
        }
        composable("notch_bar") {
            // Placeholder for now or direct call if exists
        }

        // ── Task command center ────────────────────────────────────────────
        composable("task_assignment") {
            ConferenceRoomTaskScreen(navController) { navController.popBackStack() }
        }

        // ── Lineage map ───────────────────────────────────────────────────
        composable("lineage_map") {
            LineageMapScreen(navController) { navController.popBackStack() }
        }

        // ── Gate image domain picker ───────────────────────────────────────
        composable("gate_image_picker") {
            GateDomainImagePicker(navController) { navController.popBackStack() }
        }

        // ── Regen Core Engine ──────────────────────────────────────────────
        composable("regencore_engine") {
            RegenCoreEngineScreen(navController)
        }

        // ── Agent Profiles Sub-Routes ──────────────────────────────────────
        composable("sovereign_character/{agentName}") { backStackEntry ->
            val agentName = backStackEntry.arguments?.getString("agentName") ?: "AURA"
            SovereignCharacterScreen(
                agentName = agentName,
                navController = navController
            )
        }

        composable("agent_profile/{agentName}") { backStackEntry ->
            val agentName = backStackEntry.arguments?.getString("agentName") ?: "AURA"
            val agentType = try {
                AgentType.valueOf(agentName.uppercase())
            } catch (e: Exception) {
                AgentType.AURA
            }
            dev.aurakai.auraframefx.domains.aura.screens.AgentProfileScreen(
                agentType = agentType,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

fun registerGlobalOverlays(overlays: List<String>) {
    overlays.forEach { overlay ->
        timber.log.Timber.tag("ReGenesisNavGraph").i("🌐 Global overlay registered: $overlay")
    }
}
