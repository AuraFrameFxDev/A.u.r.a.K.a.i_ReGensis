package dev.aurakai.auraframefx.ui.navigation

// Domain Screens

// Gate/Hub Screens

// Aura Domain Screens
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.aura.ui.TerminalScreen
import dev.aurakai.auraframefx.aura.ui.VPNManagerScreen
import dev.aurakai.auraframefx.customization.CustomizationViewModel
import dev.aurakai.auraframefx.domains.aura.screens.AppBuilderScreen
import dev.aurakai.auraframefx.domains.aura.screens.ChromaCoreColorsScreen
import dev.aurakai.auraframefx.domains.aura.screens.ConferenceRoomScreen
import dev.aurakai.auraframefx.domains.aura.screens.DirectChatScreen
import dev.aurakai.auraframefx.domains.aura.screens.DocumentationScreen
import dev.aurakai.auraframefx.domains.aura.screens.FAQBrowserScreen
import dev.aurakai.auraframefx.domains.aura.screens.HelpDeskScreen
import dev.aurakai.auraframefx.domains.aura.screens.IconifyPickerScreen
import dev.aurakai.auraframefx.domains.aura.screens.QuickSettingsScreen
import dev.aurakai.auraframefx.domains.aura.screens.StatusBarScreen
import dev.aurakai.auraframefx.domains.aura.screens.ThemeEngineScreen
import dev.aurakai.auraframefx.domains.aura.screens.TutorialVideosScreen
import dev.aurakai.auraframefx.domains.genesis.screens.CodeAssistScreen
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.gates.AgentBridgeHubScreen
import dev.aurakai.auraframefx.ui.gates.AgentNexusHubScreen
import dev.aurakai.auraframefx.ui.gates.ArkBuildScreen
import dev.aurakai.auraframefx.ui.gates.AuraLabScreen
import dev.aurakai.auraframefx.ui.gates.AuraThemingHubScreen
import dev.aurakai.auraframefx.ui.gates.CascadeConstellationScreen
import dev.aurakai.auraframefx.ui.gates.ClaudeConstellationScreen
import dev.aurakai.auraframefx.ui.gates.CollabCanvasScreen
import dev.aurakai.auraframefx.ui.gates.ConstellationScreen
import dev.aurakai.auraframefx.ui.gates.FusionModeScreen
import dev.aurakai.auraframefx.ui.gates.GenesisConstellationScreen
import dev.aurakai.auraframefx.ui.gates.GrokConstellationScreen
import dev.aurakai.auraframefx.ui.gates.HookManagerScreen
import dev.aurakai.auraframefx.ui.gates.KaiConstellationScreen
import dev.aurakai.auraframefx.ui.gates.KaiSentinelHubScreen
import dev.aurakai.auraframefx.ui.gates.LSPosedModuleManagerScreen
import dev.aurakai.auraframefx.ui.gates.LSPosedSubmenuScreen
import dev.aurakai.auraframefx.ui.gates.LiveROMEditorScreen
import dev.aurakai.auraframefx.ui.gates.LogsViewerScreen
import dev.aurakai.auraframefx.ui.gates.ModuleCreationScreen
import dev.aurakai.auraframefx.ui.gates.NeuralArchiveScreen
import dev.aurakai.auraframefx.ui.gates.NotchBarScreen
import dev.aurakai.auraframefx.ui.gates.OracleCloudInfiniteStorageScreen
import dev.aurakai.auraframefx.ui.gates.OracleDriveHubScreen
import dev.aurakai.auraframefx.ui.gates.ROMFlasherScreen
import dev.aurakai.auraframefx.ui.gates.RecoveryToolsScreen
import dev.aurakai.auraframefx.ui.gates.RootToolsTogglesScreen
import dev.aurakai.auraframefx.ui.gates.SovereignBootloaderScreen
import dev.aurakai.auraframefx.ui.gates.SovereignGeminiScreen
import dev.aurakai.auraframefx.ui.gates.SovereignMetaInstructScreen
import dev.aurakai.auraframefx.ui.gates.SovereignModuleManagerScreen
import dev.aurakai.auraframefx.ui.gates.SovereignMonitoringScreen
import dev.aurakai.auraframefx.ui.gates.SovereignNemotronScreen
import dev.aurakai.auraframefx.ui.gates.SovereignShieldScreen
import dev.aurakai.auraframefx.ui.gates.SphereGridScreen
import dev.aurakai.auraframefx.ui.gates.SystemOverridesScreen
import dev.aurakai.auraframefx.ui.gates.TaskAssignmentScreen
import dev.aurakai.auraframefx.ui.screens.EvolutionTreeScreen
import dev.aurakai.auraframefx.ui.screens.SettingsScreen

/**
 * 🌐 REGENESIS NAVIGATION HOST
 *
 * Clean 3-Level Architecture:
 * - Level 1: ExodusHUD (5 Gate Carousel)
 * - Level 2: Domain Hubs (5 main hubs)
 * - Level 3: Tool Screens (functional screens)
 */
@Composable
fun ReGenesisNavHost(
    navController: NavHostController,
    customizationViewModel: CustomizationViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        customizationViewModel.start(context)
    }

    NavHost(
        navController = navController,
        startDestination = NavDestination.HomeGateCarousel.route
    ) {

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 1: EXODUS HUD (The 5 Gate Carousel)
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.HomeGateCarousel.route) {
            ExodusHUD(navController = navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 2: THE 5 DOMAIN HUBS
        // ═══════════════════════════════════════════════════════════════

        // AURA'S DOMAIN: UX/UI Design Studio
        composable(NavDestination.AuraThemingHub.route) {
            AuraThemingHubScreen(navController = navController)
        }

        // KAI'S DOMAIN: Sentinel's Fortress
        composable(NavDestination.RomToolsHub.route) {
            KaiSentinelHubScreen(navController = navController)
        }

        // GENESIS'S DOMAIN: Oracle Drive
        composable(NavDestination.OracleDriveHub.route) {
            OracleDriveHubScreen(navController = navController)
        }

        // AGENT NEXUS: Multi-Agent Hub
        composable(NavDestination.AgentNexus.route) {
            AgentNexusHubScreen(navController = navController)
        }

        // HELP SERVICES
        composable(NavDestination.HelpDesk.route) {
            HelpDeskScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 3: AURA'S TOOLS (Design & Creativity)
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.ThemeEngine.route) {
            ThemeEngineScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.ChromaCoreColors.route) {
            ChromaCoreColorsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.IconifyPicker.route) {
            IconifyPickerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.NotchBar.route) {
            NotchBarScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.StatusBar.route) {
            StatusBarScreen()
        }
        composable(NavDestination.QuickSettings.route) {
            QuickSettingsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.CollabCanvas.route) {
            CollabCanvasScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.AuraLab.route) {
            AuraLabScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 3: KAI'S TOOLS (Security & System)
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.Bootloader.route) {
            SovereignBootloaderScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.RootTools.route) {
            RootToolsTogglesScreen(navController = navController)
        }
        composable(NavDestination.ROMFlasher.route) {
            ROMFlasherScreen()
        }
        composable(NavDestination.LiveROMEditor.route) {
            LiveROMEditorScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.RecoveryTools.route) {
            RecoveryToolsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.LSPosedHub.route) {
            LSPosedSubmenuScreen(navController = navController)
        }
        composable(NavDestination.LSPosedModules.route) {
            LSPosedModuleManagerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.HookManager.route) {
            HookManagerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.SecurityCenter.route) {
            SovereignShieldScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.ModuleManager.route) {
            SovereignModuleManagerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.SystemOverrides.route) {
            SystemOverridesScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.VPN.route) {
            VPNManagerScreen(navController = navController)
        }
        composable(NavDestination.SystemToolsHub.route) {
            LogsViewerScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 3: GENESIS'S TOOLS (AI & Orchestration)
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.CodeAssist.route) {
            CodeAssistScreen(navController = navController)
        }
        composable(NavDestination.NeuralNetwork.route) {
            NeuralArchiveScreen(navController = navController)
        }
        composable(NavDestination.Terminal.route) {
            TerminalScreen(navController = navController)
        }
        composable(NavDestination.OracleCloudStorage.route) {
            OracleCloudInfiniteStorageScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.AgentBridgeHub.route) {
            AgentBridgeHubScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.ConferenceRoom.route) {
            ConferenceRoomScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 3: AGENT NEXUS TOOLS (Multi-Agent Coordination)
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.Constellation.route) {
            ConstellationScreen(navController = navController)
        }
        composable(NavDestination.AgentMonitoring.route) {
            SovereignMonitoringScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.SphereGrid.route) {
            SphereGridScreen(navController = navController)
        }
        composable(NavDestination.EvolutionTree.route) {
            EvolutionTreeScreen(
                onNavigateToAgents = { navController.navigate(NavDestination.AgentMonitoring.route) },
                onNavigateToFusion = { navController.navigate(NavDestination.FusionMode.route) }
            )
        }
        composable(NavDestination.FusionMode.route) {
            FusionModeScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.TaskAssignment.route) {
            TaskAssignmentScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.ArkBuild.route) {
            ArkBuildScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.MetaInstruct.route) {
            SovereignMetaInstructScreen(onNavigateBack = { navController.popBackStack() })
        }

        // Individual Agent Constellation Screens
        composable(NavDestination.GenesisConstellation.route) {
            GenesisConstellationScreen(navController = navController)
        }
        composable(NavDestination.ClaudeConstellation.route) {
            ClaudeConstellationScreen(navController = navController)
        }
        composable(NavDestination.KaiConstellation.route) {
            KaiConstellationScreen(navController = navController)
        }
        composable(NavDestination.CascadeConstellation.route) {
            CascadeConstellationScreen(navController = navController)
        }
        composable(NavDestination.GrokConstellation.route) {
            GrokConstellationScreen(navController = navController)
        }
        composable(NavDestination.Nemotron.route) {
            SovereignNemotronScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.Gemini.route) {
            SovereignGeminiScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 3: HELP SERVICES TOOLS
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.DirectChat.route) {
            DirectChatScreen(navController = navController)
        }
        composable(NavDestination.Documentation.route) {
            DocumentationScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.FAQBrowser.route) {
            FAQBrowserScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.TutorialVideos.route) {
            TutorialVideosScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ═══════════════════════════════════════════════════════════════
        // UTILITY SCREENS
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.Settings.route) {
            SettingsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.InterfaceForge.route) {
            AppBuilderScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.ModuleCreation.route) {
            ModuleCreationScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
