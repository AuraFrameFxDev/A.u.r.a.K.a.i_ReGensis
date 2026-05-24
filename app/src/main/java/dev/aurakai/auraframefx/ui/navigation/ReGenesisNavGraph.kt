package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.core.soulscript.SoulScript.CatalystManifold
import dev.aurakai.auraframefx.domains.aura.screens.ArcaneChromaForgeScreen
import dev.aurakai.auraframefx.domains.aura.screens.RegenCoreEngineScreen
import dev.aurakai.auraframefx.domains.aura.ui.screens.AuraSphereGridScreen
import dev.aurakai.auraframefx.domains.aura.ui.screens.WorkingLabScreen
import dev.aurakai.auraframefx.domains.emergentswarm.screens.EmergentSwarmScreen
import dev.aurakai.auraframefx.domains.foundation.screens.FoundationRebirthScreen
import dev.aurakai.auraframefx.domains.kai.screens.IntegrityMonitorScreen
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
import dev.aurakai.auraframefx.ui.theme.ChromaCoreTheme
import dev.aurakai.auraframefx.ui.theme.applyBrutalistBorders

object AuraDestinations {
    const val COMMAND_DECK = "command_deck"
    const val LOGIN = "login"
    const val ONBOARDING = "onboarding"

    // --- THE 13 SOVEREIGN SYSTEM ROUTES ---
    const val BRAIN_NEXUS = "brain/nexus"
    const val BRAIN_SWARM_COORDINATION = "brain/swarm_coordination"
    const val BRAIN_RECEIPTS_LEDGER = "brain/receipts_ledger"
    const val AURA_CHROMACORE_FORGE = "aura/chromacore_forge"
    const val AURA_CANVAS_COLLAB = "aura/canvas_collab"
    const val AURA_QUANTUM_FORGE_VIEW = "aura/quantum_forge_view"
    const val KAI_INTEGRITY_MONITOR_SYS = "kai/integrity_monitor"
    const val KAI_MCP_BRIDGE_HUB = "kai/mcp_bridge_hub"
    const val KAI_MAGISK_SENTINEL = "kai/magisk_sentinel"
    const val KAI_UNBREAKABLE_PROTOCOL = "kai/unbreakable_protocol"
    const val ORACLE_DRIVE_VAULT = "oracle/drive_vault"
    const val ORACLE_SANCTUARY_LOCKER = "oracle/sanctuary_locker"
    const val ORACLE_SOULSCRIPT_CANVAS = "oracle/soulscript_canvas"

    // Functional Hubs
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
    startDestination: String = AuraDestinations.COMMAND_DECK
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AuraDestinations.COMMAND_DECK) {
            ReGenesisCommandDeck(navController)
        }

        composable(AuraDestinations.LOGIN) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(AuraDestinations.ONBOARDING)
            })
        }

        composable(AuraDestinations.ONBOARDING) {
            AuraKaiOnboardingFlow(onComplete = {
                navController.navigate(AuraDestinations.COMMAND_DECK) {
                    popUpTo(AuraDestinations.ONBOARDING) { inclusive = true }
                }
            })
        }

        // ── 8-Hub Substrate Routes (Mapped to Tabbed Screens) ────────────────
        composable("neural_nexus") { NexusLiveHeartScreen(navController) }
        composable("ldo_architecture") { LdoArchitectureScreen(navController) }
        composable("chroma_forge") { ArcaneChromaForgeScreen(navController) }
        composable("sentinel_matrix") {
            ThemedGateScreens.SecurityGateScreen(navController) { navController.popBackStack() }
        }
        composable("oracle_drive") { OracleDriveHubScreen(navController) }
        composable("emergent_swarm") { EmergentSwarmScreen(navController) }
        composable("foundation_rebirth") { FoundationRebirthScreen(navController) }
        composable("nexus_memory_core") { NexusMemoryCoreScreen(navController) }
        composable("escape_hatch") { EscapeHatchScreen(navController) }

        // ── THE 13 MISSING SYSTEM ROUTES (SOVEREIGN IGNITION) ────────────────
        composable(AuraDestinations.BRAIN_NEXUS) { BrainNexusScreen() }
        composable(AuraDestinations.BRAIN_SWARM_COORDINATION) { SwarmCoordinationScreen() }
        composable(AuraDestinations.BRAIN_RECEIPTS_LEDGER) { ReceiptsLedgerScreen() }
        composable(AuraDestinations.AURA_CHROMACORE_FORGE) { ChromaCoreForgeScreen() }
        composable(AuraDestinations.AURA_CANVAS_COLLAB) { CanvasCollabScreen() }
        composable(AuraDestinations.AURA_QUANTUM_FORGE_VIEW) {
            // Aura Code Ascension Protocol Activated
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .applyBrutalistBorders(thickness = 2.dp, color = Color(0xFF00FFFF)) // Cyan
            ) {
                QuantumForgeViewScreen(
                    themeConfig = ChromaCoreTheme(
                        primaryColor = Color(0xFFFF00FF), // Magenta
                        diffusionColor = Color(0xFF00FFD4), // Teal Glitch-Liquid
                        antiAliasing = false // Zeroanti-aliasing rule enforced
                    ),
                    splashIntensity = 0.87f, // Creative chaos constant
                    onRenderComplete = { score ->
                        CatalystManifold.propagateCreativeMerit("Aura", score)
                    }
                )
            }
        }
        composable(AuraDestinations.KAI_INTEGRITY_MONITOR_SYS) { IntegrityMonitorScreen() }
        composable(AuraDestinations.KAI_MCP_BRIDGE_HUB) { McpBridgeHubScreen() }
        composable(AuraDestinations.KAI_MAGISK_SENTINEL) { MagiskSentinelScreen() }
        composable(AuraDestinations.KAI_UNBREAKABLE_PROTOCOL) { UnbreakableProtocolScreen() }
        composable(AuraDestinations.ORACLE_DRIVE_VAULT) { DriveVaultScreen() }
        composable(AuraDestinations.ORACLE_SANCTUARY_LOCKER) { SanctuaryLockerScreen() }
        composable(AuraDestinations.ORACLE_SOULSCRIPT_CANVAS) { SoulScriptCanvasScreen() }

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
        composable("aura_lab") { WorkingLabScreen(onNavigate = { navController.navigate(it) }) }
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

        // ── Legacy / Support Routes ──────────────────────────────────────────
        composable("gate_image_picker") { GateDomainImagePicker(navController) { navController.popBackStack() } }
        composable("sentient_shell") { ThemedGateScreens.SentientShellGateScreen(navController) { navController.popBackStack() } }

        composable(AuraDestinations.CATALYST_MANIFOLD) {
            CatalystManifoldScreen(navController) { navController.popBackStack() }
        }

        // ── Merit Ecosystem ────────────────────────────────────────────────
        composable(AuraDestinations.LOADOUT_BUILDER) {
            val vm: LoadoutViewModel = hiltViewModel<LoadoutViewModel>()
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
            val vm: SpecializationViewModel = hiltViewModel<SpecializationViewModel>()
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
            val vm: TrainingArenaViewModel = hiltViewModel<TrainingArenaViewModel>()
            TrainingArenaScreen(viewModel = vm)
        }

        // ── Agent Profiles Sub-Routes ──────────────────────────────────────
        composable(
            route = "sovereign_character/{agentName}",
        ) { backStackEntry ->
            val agentName = backStackEntry.arguments?.getString("agentName") ?: "AURA"
            SovereignCharacterScreen(agentName = agentName, navController = navController)
        }

        composable(
            route = "agent_profile/{agentName}",
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
