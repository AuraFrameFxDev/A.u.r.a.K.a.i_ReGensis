package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.domains.aura.screens.ChromaForgeScreen
import dev.aurakai.auraframefx.domains.aura.screens.RegenCoreEngineScreen
import dev.aurakai.auraframefx.domains.emergentswarm.screens.EmergentSwarmScreen
import dev.aurakai.auraframefx.domains.foundation.screens.FoundationRebirthScreen
import dev.aurakai.auraframefx.domains.kai.screens.SentinelMatrixScreen
import dev.aurakai.auraframefx.domains.ldoarchitecture.screens.LdoArchitectureScreen
import dev.aurakai.auraframefx.domains.neuralnexus.screens.NexusLiveHeartScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SovereignCharacterScreen
import dev.aurakai.auraframefx.domains.oracledrive.screens.OracleDriveHubScreen
import dev.aurakai.auraframefx.ui.arena.TrainingArenaScreen
import dev.aurakai.auraframefx.ui.arena.TrainingArenaViewModel
import dev.aurakai.auraframefx.ui.components.ReGenesisCommandDeck
import dev.aurakai.auraframefx.ui.gates.ConferenceRoomTaskScreen
import dev.aurakai.auraframefx.ui.gates.GateDomainImagePicker
import dev.aurakai.auraframefx.ui.gates.LineageMapScreen
import dev.aurakai.auraframefx.ui.gates.ThemedGateScreens
import dev.aurakai.auraframefx.ui.loadout.AgentLoadoutScreen
import dev.aurakai.auraframefx.ui.loadout.LoadoutViewModel
import dev.aurakai.auraframefx.ui.specialization.SpecializationTreeScreen
import dev.aurakai.auraframefx.ui.specialization.SpecializationViewModel

object AuraDestinations {
    const val COMMAND_DECK = "command_deck"
    const val LOADOUT_BUILDER = "loadout_builder"
    const val SPECIALIZATION_TREE = "specialization_tree/{agentId}"
    const val TRAINING_ARENA = "training_arena/{agentId}"

    fun specTreePath(agentId: String) = "specialization_tree/$agentId"
    fun arenaPath(agentId: String) = "training_arena/$agentId"
}

@Composable
fun AuraNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AuraDestinations.COMMAND_DECK
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AuraDestinations.COMMAND_DECK) {
            ReGenesisCommandDeck(navController)
        }

        // ── 7-Hub Substrate Routes ──────────────────────────────────────────
        composable("neural_nexus") { NexusLiveHeartScreen(navController) }
        composable("ldo_architecture") { LdoArchitectureScreen(navController) }
        composable("chroma_forge") { ChromaForgeScreen(navController) }
        composable("sentinel_matrix") { SentinelMatrixScreen(navController) }

        // ==========================================
        // KAI'S FORTRESS SUB-GATE SOVEREIGN ROUTE MAP
        // ==========================================
        composable("kai/security") {
            dev.aurakai.auraframefx.ui.gates.ThemedGateScreens.SecurityGateScreen(navController) { navController.popBackStack() }
        }
        composable("kai/root") {
            dev.aurakai.auraframefx.ui.gates.ThemedGateScreens.RootToolsGateScreen(navController) { navController.popBackStack() }
        }
        composable("kai/recovery") {
            dev.aurakai.auraframefx.ui.gates.ThemedGateScreens.RecoveryGateScreen(navController) { navController.popBackStack() }
        }
        composable("kai/rom") {
            dev.aurakai.auraframefx.ui.gates.ThemedGateScreens.RomFlasherGateScreen(navController) { navController.popBackStack() }
        }
        composable("kai/modules") {
            dev.aurakai.auraframefx.ui.gates.ThemedGateScreens.ModulesGateScreen(navController) { navController.popBackStack() }
        }
        composable("kai/vpn") {
            dev.aurakai.auraframefx.ui.gates.ThemedGateScreens.VpnGateScreen(navController) { navController.popBackStack() }
        }
        composable("kai/bootloader") {
            dev.aurakai.auraframefx.ui.gates.ThemedGateScreens.BootloaderGateScreen(navController) { navController.popBackStack() }
        }
        composable("kai/lsposed") {
            dev.aurakai.auraframefx.ui.gates.ThemedGateScreens.LsposedGateScreen(navController) { navController.popBackStack() }
        }

        composable("oracle_drive") { OracleDriveHubScreen(navController) }
        composable("emergent_swarm") { EmergentSwarmScreen(navController) }
        composable("foundation_rebirth") { FoundationRebirthScreen(navController) }

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
            // Placeholder
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

        // ── Merit Ecosystem ────────────────────────────────────────────────
        composable(AuraDestinations.LOADOUT_BUILDER) {
            val vm: LoadoutViewModel = hiltViewModel()
            AgentLoadoutScreen(
                viewModel = vm,
                onAgentSelected = { agentId ->
                    navController.navigate(AuraDestinations.specTreePath(agentId))
                }
            )
        }

        composable(
            route = AuraDestinations.SPECIALIZATION_TREE,
            arguments = listOf(navArgument("agentId") { type = NavType.StringType })
        ) {
            val vm: SpecializationViewModel = hiltViewModel()
            SpecializationTreeScreen(
                viewModel = vm,
                onBackTriggered = { navController.popBackStack() }
            )
        }

        composable(
            route = AuraDestinations.TRAINING_ARENA,
            arguments = listOf(navArgument("agentId") { type = NavType.StringType })
        ) {
            val vm: TrainingArenaViewModel = hiltViewModel()
            TrainingArenaScreen(viewModel = vm)
        }
    }
}
