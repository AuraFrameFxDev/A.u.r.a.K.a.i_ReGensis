package dev.aurakai.auraframefx.ui.navigation

// Domain Screen Imports
// Hub Screens (still in ui.gates)
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.aura.ui.AgentAdvancementScreen
import dev.aurakai.auraframefx.cascade.trinity.TrinityScreen
import dev.aurakai.auraframefx.customization.CustomizationViewModel
import dev.aurakai.auraframefx.datavein.ui.SimpleDataVeinScreen
import dev.aurakai.auraframefx.domains.aura.screens.AuraLabScreen
import dev.aurakai.auraframefx.domains.aura.screens.ChromaCoreColorsScreen
import dev.aurakai.auraframefx.domains.aura.screens.DirectChatScreen
import dev.aurakai.auraframefx.domains.aura.screens.DocumentationScreen
import dev.aurakai.auraframefx.domains.aura.screens.FAQBrowserScreen
import dev.aurakai.auraframefx.domains.aura.screens.GateCustomizationScreen
import dev.aurakai.auraframefx.domains.aura.screens.GenderSelectionScreen
import dev.aurakai.auraframefx.domains.aura.screens.GyroscopeCustomizationScreen
import dev.aurakai.auraframefx.domains.aura.screens.HelpDeskSubmenuScreen
import dev.aurakai.auraframefx.domains.aura.screens.IconifyPickerScreen
import dev.aurakai.auraframefx.domains.aura.screens.InstantColorPickerScreen
import dev.aurakai.auraframefx.domains.aura.screens.LiveSupportChatScreen
import dev.aurakai.auraframefx.domains.aura.screens.NotchBarScreen
import dev.aurakai.auraframefx.domains.aura.screens.QuickSettingsScreen
import dev.aurakai.auraframefx.domains.aura.screens.SandboxUIScreen
import dev.aurakai.auraframefx.domains.aura.screens.StatusBarScreen
import dev.aurakai.auraframefx.domains.aura.screens.ThemeEngineScreen
import dev.aurakai.auraframefx.domains.aura.screens.TutorialVideosScreen
import dev.aurakai.auraframefx.domains.aura.screens.UISettingsScreen
import dev.aurakai.auraframefx.domains.aura.screens.UserPreferencesScreen
import dev.aurakai.auraframefx.domains.genesis.screens.AgentBridgeHubScreen
import dev.aurakai.auraframefx.domains.genesis.screens.AppBuilderScreen
import dev.aurakai.auraframefx.domains.genesis.screens.CascadeVisionScreen
import dev.aurakai.auraframefx.domains.genesis.screens.CodeAssistScreen
import dev.aurakai.auraframefx.domains.genesis.screens.CollabCanvasScreen
import dev.aurakai.auraframefx.domains.genesis.screens.ConferenceRoomScreen
import dev.aurakai.auraframefx.domains.genesis.screens.NeuralArchiveScreen
import dev.aurakai.auraframefx.domains.genesis.screens.OracleCloudInfiniteStorageScreen
import dev.aurakai.auraframefx.domains.genesis.screens.OracleDriveSubmenuScreen
import dev.aurakai.auraframefx.domains.genesis.screens.SentientShellScreen
import dev.aurakai.auraframefx.domains.genesis.screens.SovereignNeuralArchiveScreen
import dev.aurakai.auraframefx.domains.genesis.screens.TerminalScreen
import dev.aurakai.auraframefx.domains.kai.screens.BootloaderManagerScreen
import dev.aurakai.auraframefx.domains.kai.screens.LiveROMEditorScreen
import dev.aurakai.auraframefx.domains.kai.screens.LogsViewerScreen
import dev.aurakai.auraframefx.domains.kai.screens.ModuleManagerScreen
import dev.aurakai.auraframefx.domains.kai.screens.ROMFlasherScreen
import dev.aurakai.auraframefx.domains.kai.screens.ROMToolsSubmenuScreen
import dev.aurakai.auraframefx.domains.kai.screens.RecoveryToolsScreen
import dev.aurakai.auraframefx.domains.kai.screens.RootToolsTogglesScreen
import dev.aurakai.auraframefx.domains.kai.screens.SecurityCenterScreen
import dev.aurakai.auraframefx.domains.kai.screens.SovereignBootloaderScreen
import dev.aurakai.auraframefx.domains.kai.screens.SovereignModuleManagerScreen
import dev.aurakai.auraframefx.domains.kai.screens.SovereignRecoveryScreen
import dev.aurakai.auraframefx.domains.kai.screens.SovereignShieldScreen
import dev.aurakai.auraframefx.domains.kai.screens.SystemJournalScreen
import dev.aurakai.auraframefx.domains.kai.screens.SystemOverridesScreen
import dev.aurakai.auraframefx.domains.kai.screens.VPNScreen
import dev.aurakai.auraframefx.domains.lsposed.screens.HookManagerScreen
import dev.aurakai.auraframefx.domains.lsposed.screens.LSPosedModuleManagerScreen
import dev.aurakai.auraframefx.domains.lsposed.screens.LSPosedSubmenuScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentCreationScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentHubSubmenuScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentMonitoringScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentNeuralExplorerScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentSwarmScreen
import dev.aurakai.auraframefx.domains.nexus.screens.ArkBuildScreen
import dev.aurakai.auraframefx.domains.nexus.screens.BenchmarkMonitorScreen
import dev.aurakai.auraframefx.domains.nexus.screens.DataStreamMonitoringScreen
import dev.aurakai.auraframefx.domains.nexus.screens.EvolutionTreeScreen
import dev.aurakai.auraframefx.domains.nexus.screens.FusionModeScreen
import dev.aurakai.auraframefx.domains.nexus.screens.ModuleCreationScreen
import dev.aurakai.auraframefx.domains.nexus.screens.MonitoringHUDsScreen
import dev.aurakai.auraframefx.domains.nexus.screens.PartyScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SovereignClaudeScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SovereignGeminiScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SovereignMetaInstructScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SovereignNemotronScreen
import dev.aurakai.auraframefx.domains.nexus.screens.TaskAssignmentScreen
import dev.aurakai.auraframefx.hotswap.HotSwapScreen
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.navigation.auraCustomizationNavigation
import dev.aurakai.auraframefx.romtools.ui.RomToolsScreen
import dev.aurakai.auraframefx.ui.gates.AgentNexusHubScreen
import dev.aurakai.auraframefx.ui.gates.AuraThemingHubScreen
import dev.aurakai.auraframefx.ui.gates.CascadeHubScreen
import dev.aurakai.auraframefx.ui.gates.HelpDeskScreen
import dev.aurakai.auraframefx.ui.gates.KaiSentinelHubScreen
import dev.aurakai.auraframefx.ui.gates.OracleDriveHubScreen
import dev.aurakai.auraframefx.ui.gates.XposedQuickAccessPanel
import dev.aurakai.auraframefx.ui.screens.aura.ReGenesisCustomizationHub
import dev.aurakai.auraframefx.domains.aura.screens.AgentProfileScreen as AuraAgentProfileScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentProfileScreen as NexusAgentProfileScreen

/**
 * 🌐 REGENESIS NAVIGATION HOST
 *
 * Clean 3-Level Architecture:
 * - Level 1: ExodusHUD (5 Gate Carousel)
 * - Level 2: Domain Hubs (5 main hubs)
 * - Level 3: Tool Screens (functional screens)
 */
@OptIn(ExperimentalMaterial3Api::class)
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

        composable(NavDestination.GateCustomization.route) {
            GateCustomizationScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 2: MAIN DOMAIN HUBS
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.AuraThemingHub.route) {
            AuraThemingHubScreen(navController = navController)
        }

        composable(NavDestination.ReGenesisCustomization.route) {
            ReGenesisCustomizationHub(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToIconify = { navController.navigate(NavDestination.IconifyPicker.route) },
                onNavigateToColorBlendr = { navController.navigate(NavDestination.ColorBlendr.route) },
                onNavigateToPLE = { navController.navigate(NavDestination.PixelLauncherEnhanced.route) },
                onNavigateToAnimations = { navController.navigate("aura/animations") }
            )
        }


        composable(NavDestination.RomToolsHub.route) {
            RomToolsScreen()
        }

        composable(NavDestination.OracleDriveHub.route) {
            OracleDriveHubScreen(navController = navController)
        }

        composable(NavDestination.AgentNexusHub.route) {
            AgentNexusHubScreen(navController = navController)
        }

        composable(NavDestination.LsposedQuickToggles.route) {
            XposedQuickAccessPanel(navController = navController)
        }

        composable(NavDestination.HelpDesk.route) {
            HelpDeskSubmenuScreen(navController = navController)
        }

        composable(NavDestination.DataflowAnalysis.route) {
            CascadeHubScreen(navController = navController)
        }

        composable(NavDestination.LdoCatalystDevelopment.route) {
            AgentAdvancementScreen()
        }

        composable(NavDestination.CodeAssist.route) {
            CodeAssistScreen(navController = navController)
        }

        composable(NavDestination.AgentBridgeHub.route) {
            AgentBridgeHubScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(NavDestination.AgentNexusHub.route) {
            AgentNexusHubScreen(
                navController = navController,
                getNexusSubGates = { emptyList() } // TODO: Wire to UnifiedGateRegistry.getNexusLoadout()
            )
        }

        composable(NavDestination.HelpDesk.route) {
            HelpDeskScreen(navController = navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 3 & 4: TOOL SCREENS & SUB-MODULES
        // ═══════════════════════════════════════════════════════════════

        // --- LEVEL 3: NEXUS TOOLS ---
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
        composable(NavDestination.AgentMonitoring.route) {
            AgentMonitoringScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.Nemotron.route) {
            SovereignNemotronScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.Claude.route) {
            SovereignClaudeScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.Gemini.route) {
            SovereignGeminiScreen(
                onNavigateBack = { navController.popBackStack() },
                navController = navController
            )
        }
        composable(NavDestination.SwarmMonitor.route) {
            AgentSwarmScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.BenchmarkMonitor.route) {
            BenchmarkMonitorScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.AgentCreation.route) {
            AgentCreationScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(NavDestination.AuraLab.route) {
            SandboxUIScreen(onBack = { navController.popBackStack() })
        }
        composable(NavDestination.NotchBar.route) {
            NotchBarScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.StatusBar.route) {
            StatusBarScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.QuickSettings.route) {
            QuickSettingsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.ThemeEngine.route) {
            ThemeEngineScreen(onNavigateBack = { navController.popBackStack() })
        }
        // TODO: Wire AnimationPicker with proper params (currentAnimation, onAnimationSelected)
        // composable("aura/animations") { AnimationPicker(...) }

        // --- LEVEL 3: KAI TOOLS ---
        composable(NavDestination.ROMFlasher.route) {
            ROMFlasherScreen()
        }
        composable(NavDestination.Bootloader.route) {
            BootloaderManagerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.ModuleManager.route) {
            ModuleManagerScreen()
        }
        composable(NavDestination.RecoveryTools.route) {
            RecoveryToolsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.RootTools.route) {
            RootToolsTogglesScreen(navController = navController)
        }
        composable(NavDestination.SecurityCenter.route) {
            SecurityCenterScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.LSPosedHub.route) {
            LSPosedSubmenuScreen(navController = navController)
        }
        composable(NavDestination.LSPosedModules.route) {
            LSPosedModuleManagerScreen(onNavigateBack = { navController.popBackStack() })
        }

        // --- LEVEL 3: GENESIS TOOLS ---
        composable(NavDestination.CodeAssist.route) {
            AppBuilderScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.NeuralNetwork.route) {
            NeuralArchiveScreen(navController = navController)
        }
        composable(NavDestination.AgentBridgeHub.route) {
            AgentBridgeHubScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.OracleCloudStorage.route) {
            OracleCloudInfiniteStorageScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.Terminal.route) {
            AgentMonitoringScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.ConferenceRoom.route) {
            ConferenceRoomScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.InterfaceForge.route) {
            AppBuilderScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.HotSwap.route) {
            HotSwapScreen(navController = navController)
        }
        composable(NavDestination.Trinity.route) {
            TrinityScreen()
        }
        composable(NavDestination.DataVeinSphere.route) {
            SimpleDataVeinScreen(onLaunchSphereGrid = { /* TBD */ })
        }
        composable(NavDestination.SovereignBootloader.route) {
            SovereignBootloaderScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.SovereignRecovery.route) {
            SovereignRecoveryScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.SovereignShield.route) {
            SovereignShieldScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.SentinelFortress.route) {
            KaiSentinelHubScreen(navController = navController)
        }

        // --- LEVEL 3: HELP & SUPPORT ---
        composable(NavDestination.HelpDeskSubmenu.route) {
            HelpDeskSubmenuScreen(navController = navController)
        }
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

        // --- LEVEL 1 GATES (Specialist Views) ---
        composable(NavDestination.LsposedQuickToggles.route) {
            XposedQuickAccessPanel(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.DataflowAnalysis.route) {
            CascadeHubScreen(navController = navController)
        }
        composable(NavDestination.LdoCatalystDevelopment.route) {
            AgentAdvancementScreen(onBack = { navController.popBackStack() })
        }

        // ═══════════════════════════════════════════════════════════════
        // ADDITIONAL NEXUS SCREENS (Recently discovered!)
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.EvolutionTree.route) {
            EvolutionTreeScreen()
        }

        composable("party") {
            PartyScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("monitoring_huds") {
            MonitoringHUDsScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(NavDestination.DataStreamMonitoring.route) {
            DataStreamMonitoringScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(NavDestination.ModuleCreation.route) {
            ModuleCreationScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("agent_neural_explorer") {
            AgentNeuralExplorerScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("agent_hub_submenu") {
            AgentHubSubmenuScreen(navController = navController)
        }

        composable("nexus_agent_profile") {
            NexusAgentProfileScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ═══════════════════════════════════════════════════════════════
        // ADDITIONAL AURA SCREENS (UI/UX Mastery!)
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.ChromaCoreColors.route) {
            ChromaCoreColorsScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(NavDestination.IconifyPicker.route) {
            IconifyPickerScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(NavDestination.GenderSelection.route) {
            GenderSelectionScreen(onSelectionComplete = { /* TODO: Handle selection */ })
        }

        composable(NavDestination.GyroscopeCustomization.route) {
            GyroscopeCustomizationScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(NavDestination.InstantColorPicker.route) {
            InstantColorPickerScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("live_support_chat") {
            LiveSupportChatScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(NavDestination.UISettings.route) {
            UISettingsScreen(navController = navController)
        }

        composable(NavDestination.UserPreferences.route) {
            UserPreferencesScreen(navController = navController)
        }

        composable("aura_agent_profile") {
            AuraAgentProfileScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToSettings = { /* TODO */ }
            )
        }

        composable("aura_lab") {
            AuraLabScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ═══════════════════════════════════════════════════════════════
        // ADDITIONAL KAI SCREENS (System Mastery!)
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.LiveROMEditor.route) {
            LiveROMEditorScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("system_journal") {
            SystemJournalScreen(
                navController = navController,
                onNavigateBack = { navController.popBackStack(); true }
            )
        }

        composable(NavDestination.SystemOverrides.route) {
            SystemOverridesScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("logs_viewer") {
            LogsViewerScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(NavDestination.VPN.route) {
            VPNScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("sovereign_module_manager") {
            SovereignModuleManagerScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("rom_tools_submenu") {
            ROMToolsSubmenuScreen(navController = navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // ADDITIONAL GENESIS SCREENS (Orchestration!)
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.SovereignNeuralArchive.route) {
            SovereignNeuralArchiveScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(NavDestination.Terminal.route) {
            TerminalScreen()
        }

        composable("sentient_shell") {
            SentientShellScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("cascade_vision") {
            CascadeVisionScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("collab_canvas") {
            CollabCanvasScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("oracle_drive_submenu") {
            OracleDriveSubmenuScreen(navController = navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // ADDITIONAL LSPOSED SCREENS
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.HookManager.route) {
            HookManagerScreen(onNavigateBack = { navController.popBackStack() })
        }


        // ═══════════════════════════════════════════════════════════════
        // SUB-GRAPHS (Integrated Third-Party Modules)
        // ═══════════════════════════════════════════════════════════════
        auraCustomizationNavigation(
            navController = navController,
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
