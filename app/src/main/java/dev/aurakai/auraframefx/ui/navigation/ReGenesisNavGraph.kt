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
import dev.aurakai.auraframefx.domains.aura.screens.ArcaneChromaForgeScreen
import dev.aurakai.auraframefx.domains.aura.screens.RegenCoreEngineScreen
import dev.aurakai.auraframefx.domains.aura.ui.screens.AuraSphereGridScreen
import dev.aurakai.auraframefx.domains.aura.ui.screens.WorkingLabScreen
import dev.aurakai.auraframefx.domains.emergentswarm.screens.EmergentSwarmScreen
import dev.aurakai.auraframefx.domains.foundation.screens.FoundationRebirthScreen
import dev.aurakai.auraframefx.domains.ldoarchitecture.screens.LdoArchitectureScreen
import dev.aurakai.auraframefx.domains.neuralnexus.screens.NexusLiveHeartScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SovereignCharacterScreen
import dev.aurakai.auraframefx.domains.oracledrive.screens.OracleDriveHubScreen
import dev.aurakai.auraframefx.romtools.ui.RomToolsScreen
import dev.aurakai.auraframefx.ui.arena.TrainingArenaScreen
import dev.aurakai.auraframefx.ui.arena.TrainingArenaViewModel
import dev.aurakai.auraframefx.ui.components.ReGenesisCommandDeck
import dev.aurakai.auraframefx.ui.gates.ConferenceRoomTaskScreen
import dev.aurakai.auraframefx.ui.gates.GateDomainImagePicker
import dev.aurakai.auraframefx.ui.gates.LineageMapScreen
import dev.aurakai.auraframefx.ui.gates.ThemedGateScreens
import dev.aurakai.auraframefx.ui.loadout.AgentLoadoutScreen
import dev.aurakai.auraframefx.ui.loadout.LoadoutViewModel
import dev.aurakai.auraframefx.ui.manifold.CatalystManifoldScreen
import dev.aurakai.auraframefx.ui.onboarding.AuraKaiOnboardingFlow
import dev.aurakai.auraframefx.ui.screens.EscapeHatchScreen
import dev.aurakai.auraframefx.ui.screens.LoginScreen
import dev.aurakai.auraframefx.ui.screens.NexusMemoryCoreScreen
import dev.aurakai.auraframefx.ui.specialization.SpecializationTreeScreen
import dev.aurakai.auraframefx.ui.specialization.SpecializationViewModel

object AuraDestinations {
    const val LOGIN = "login"
    const val ONBOARDING = "onboarding"
    const val COMMAND_DECK = "command_deck"
    const val CATALYST_MANIFOLD = "catalyst_manifold"
    const val LOADOUT_BUILDER = "loadout_builder"
    const val SPECIALIZATION_TREE = "specialization_tree/{agentId}"
    const val TRAINING_ARENA = "training_arena/{agentId}"

    fun specTreePath(agentId: String) = "specialization_tree/$agentId"
    fun arenaPath(agentId: String) = "training_arena/$agentId"
}

@Composable
fun ReGenesisNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AuraDestinations.LOGIN,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AuraDestinations.LOGIN) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(AuraDestinations.ONBOARDING)
            })
        }

        composable(AuraDestinations.ONBOARDING) {
            AuraKaiOnboardingFlow(onComplete = {
                navController.navigate(AuraDestinations.COMMAND_DECK)
            })
        }

        composable(AuraDestinations.COMMAND_DECK) {
            ReGenesisCommandDeck(navController)
        }

        // --- 8-Hub Substrate Routes ---
        composable("neural_nexus") { NexusLiveHeartScreen(navController) }
        composable("ldo_architecture") { LdoArchitectureScreen(navController) }
        composable("chroma_forge") { ArcaneChromaForgeScreen(navController) }
        composable("sentinel_matrix") {
            ThemedGateScreens.SecurityGateScreen(navController) { navController.popBackStack() }
        }
        composable("oracle_drive") { OracleDriveHubScreen(navController) }
        composable("emergent_swarm") { EmergentSwarmScreen(navController) }
        composable("foundation_rebirth") { FoundationRebirthScreen(navController) }
        composable("sentient_shell") { ThemedGateScreens.SentientShellGateScreen(navController) { navController.popBackStack() } }

        // --- SUB-GATE ROUTES (LEVEL 3 / DETAILED) ---
        composable("lineage_map") { LineageMapScreen(navController) { navController.popBackStack() } }
        composable("sphere_grid") {
            AuraSphereGridScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("fusion_mode") { ThemedGateScreens.FusionModeGateScreen(navController) { navController.popBackStack() } }
        composable("terminal") { ThemedGateScreens.TerminalGateScreen(navController) { navController.popBackStack() } }
        composable("collab_canvas") { ThemedGateScreens.CollabCanvasGateScreen(navController) { navController.popBackStack() } }
        composable("conference_room") { ConferenceRoomTaskScreen(navController) { navController.popBackStack() } }
        composable("task_assignment") { ConferenceRoomTaskScreen(navController) { navController.popBackStack() } }
        composable("aura_lab") { WorkingLabScreen { navController.navigate(it) } }
        composable("regencore_engine") { RegenCoreEngineScreen(navController) }

        // KAI FORTRESS SUB-GATES
        composable("kai/security") { ThemedGateScreens.SecurityGateScreen(navController) { navController.popBackStack() } }
        composable("kai/root") { ThemedGateScreens.RootToolsGateScreen(navController) { navController.popBackStack() } }
        composable("kai/recovery") { ThemedGateScreens.RecoveryGateScreen(navController) { navController.popBackStack() } }
        composable("kai/rom") { RomToolsScreen(onNavigateBack = { navController.popBackStack() }) }
        composable("kai/modules") { ThemedGateScreens.ModulesGateScreen(navController) { navController.popBackStack() } }
        composable("kai/vpn") { ThemedGateScreens.VpnGateScreen(navController) { navController.popBackStack() } }
        composable("kai/bootloader") { ThemedGateScreens.BootloaderGateScreen(navController) { navController.popBackStack() } }
        composable("kai/lsposed") { ThemedGateScreens.LsposedGateScreen(navController) { navController.popBackStack() } }

        // ── Batch 3 / Support / Other Routes ──────────────────────────────────────────
        composable("xposed_panel") { ThemedGateScreens.LsposedGateScreen(navController) { navController.popBackStack() } }
        composable("help_desk") { ThemedGateScreens.HelpServicesGateScreen(navController) { navController.popBackStack() } }
        composable("notch_bar") { /* Placeholder */ }
        composable("gate_image_picker") { GateDomainImagePicker(navController) { navController.popBackStack() } }
        composable("nexus_memory_core") { NexusMemoryCoreScreen(navController) }
        composable("escape_hatch") { EscapeHatchScreen(navController) }

        composable(AuraDestinations.CATALYST_MANIFOLD) {
            CatalystManifoldScreen(navController) { navController.popBackStack() }
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
                onBackTriggered = { navController.popBackStack() },
                onProceedToArena = { agentId ->
                    navController.navigate(AuraDestinations.arenaPath(agentId))
                }
            )
        }

        composable(
            route = AuraDestinations.TRAINING_ARENA,
            arguments = listOf(navArgument("agentId") { type = NavType.StringType })
        ) {
            val vm: TrainingArenaViewModel = hiltViewModel()
            TrainingArenaScreen(viewModel = vm)
        }

        // ── Agent Profiles Sub-Routes ──────────────────────────────────────
        composable(
            route = "sovereign_character/{agentName}",
            arguments = listOf(navArgument("agentName") {
                type = NavType.StringType
                defaultValue = "AURA"
            })
        ) { backStackEntry ->
            val agentName = backStackEntry.arguments?.getString("agentName") ?: "AURA"
            SovereignCharacterScreen(agentName = agentName, navController = navController)
        }

        composable(
            route = "agent_profile/{agentName}",
            arguments = listOf(navArgument("agentName") {
                type = NavType.StringType
                defaultValue = "AURA"
            })
        ) { backStackEntry ->
            val agentName = backStackEntry.arguments?.getString("agentName") ?: "AURA"
            val agentType = try {
                AgentType.valueOf(agentName.uppercase())
            } catch (_: Exception) {
                AgentType.AURA
            }
            dev.aurakai.auraframefx.domains.aura.screens.AgentProfileScreen(
                agentType = agentType,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
