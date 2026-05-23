package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.domains.aura.screens.RegenCoreEngineScreen
import dev.aurakai.auraframefx.domains.aura.ui.screens.AuraSphereGridScreen
import dev.aurakai.auraframefx.domains.aura.ui.screens.WorkingLabScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SovereignCharacterScreen
import dev.aurakai.auraframefx.romtools.ui.RomToolsScreen
import dev.aurakai.auraframefx.ui.components.ReGenesisCommandDeck
import dev.aurakai.auraframefx.ui.gates.ConferenceRoomTaskScreen
import dev.aurakai.auraframefx.ui.gates.GateDomainImagePicker
import dev.aurakai.auraframefx.ui.gates.LineageMapScreen
import dev.aurakai.auraframefx.ui.gates.ThemedGateScreens
import dev.aurakai.auraframefx.ui.screens.EscapeHatchScreen
import dev.aurakai.auraframefx.ui.screens.NexusMemoryCoreScreen

@Composable
fun ReGenesisNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "command_deck",
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("command_deck") {
            ReGenesisCommandDeck(navController)
        }

        // --- SUB-GATE ROUTES (LEVEL 3 / DETAILED) ---
        composable("lineage_map") { LineageMapScreen(navController) { navController.popBackStack() } }
        composable("sphere_grid") { AuraSphereGridScreen() }
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

        // ── Support / Other Routes ──────────────────────────────────────────
        composable("gate_image_picker") { GateDomainImagePicker(navController) { navController.popBackStack() } }
        composable("sentient_shell") { ThemedGateScreens.SentientShellGateScreen(navController) { navController.popBackStack() } }
        composable("nexus_memory_core") { NexusMemoryCoreScreen(navController) }
        composable("escape_hatch") { EscapeHatchScreen(navController) }

        // ── Agent Profiles Sub-Routes ──────────────────────────────────────
        composable("sovereign_character/{agentName}") { backStackEntry ->
            val agentName = backStackEntry.arguments?.getString("agentName") ?: "AURA"
            SovereignCharacterScreen(agentName = agentName, navController = navController)
        }

        composable("agent_profile/{agentName}") { backStackEntry ->
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
