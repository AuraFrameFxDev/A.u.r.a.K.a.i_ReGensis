package dev.aurakai.auraframefx.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.domains.aura.screens.ChromaCoreColorsScreen
import dev.aurakai.auraframefx.domains.aura.screens.IconifyPickerScreen
import dev.aurakai.auraframefx.domains.aura.screens.QuickSettingsScreen
import dev.aurakai.auraframefx.domains.aura.screens.StatusBarScreen
import dev.aurakai.auraframefx.domains.aura.screens.ThemeEngineScreen
import dev.aurakai.auraframefx.domains.aura.screens.ConferenceRoomScreen
import dev.aurakai.auraframefx.domains.aura.screens.LiveSupportChatScreen
import dev.aurakai.auraframefx.ui.components.carousel.EnhancedGateCarousel
import dev.aurakai.auraframefx.ui.gates.AgentNexusHubScreen
import dev.aurakai.auraframefx.ui.gates.AgentMonitoringScreen
import dev.aurakai.auraframefx.domains.aura.screens.AuraLabScreen
import dev.aurakai.auraframefx.ui.gates.BootloaderManagerScreen
import dev.aurakai.auraframefx.ui.gates.CascadeConstellationScreen
import dev.aurakai.auraframefx.ui.gates.ClaudeConstellationScreen
import dev.aurakai.auraframefx.ui.gates.CodeAssistScreen
import dev.aurakai.auraframefx.ui.gates.CollabCanvasScreen
import dev.aurakai.auraframefx.ui.gates.ConstellationScreen
import dev.aurakai.auraframefx.domains.aura.screens.DirectChatScreen
import dev.aurakai.auraframefx.domains.aura.screens.DocumentationScreen
import dev.aurakai.auraframefx.domains.aura.screens.FAQBrowserScreen
import dev.aurakai.auraframefx.ui.gates.FusionModeScreen
import dev.aurakai.auraframefx.ui.gates.GenesisConstellationScreen
import dev.aurakai.auraframefx.ui.gates.GrokConstellationScreen
import dev.aurakai.auraframefx.domains.aura.screens.HelpDeskScreen
import dev.aurakai.auraframefx.ui.gates.HookManagerScreen
import dev.aurakai.auraframefx.ui.gates.KaiConstellationScreen
import dev.aurakai.auraframefx.ui.gates.LSPosedGateScreen
import dev.aurakai.auraframefx.ui.gates.LSPosedModuleManagerScreen
import dev.aurakai.auraframefx.ui.gates.LSPosedSubmenuScreen
import dev.aurakai.auraframefx.ui.gates.LiveROMEditorScreen
import dev.aurakai.auraframefx.ui.gates.LogsViewerScreen
import dev.aurakai.auraframefx.ui.gates.ModuleCreationScreen
import dev.aurakai.auraframefx.ui.gates.ModuleManagerScreen
import dev.aurakai.auraframefx.ui.gates.NeuralArchiveScreen
import dev.aurakai.auraframefx.ui.gates.NotchBarScreen
import dev.aurakai.auraframefx.ui.gates.OracleDriveHubScreen
import dev.aurakai.auraframefx.ui.gates.OracleDriveSubmenuScreen
import dev.aurakai.auraframefx.ui.gates.OverlayMenusScreen
import dev.aurakai.auraframefx.ui.gates.QuickActionsScreen
import dev.aurakai.auraframefx.ui.gates.ROMFlasherScreen
import dev.aurakai.auraframefx.ui.gates.KaiSentinelHubScreen
import dev.aurakai.auraframefx.ui.gates.RecoveryToolsScreen
import dev.aurakai.auraframefx.ui.gates.RootToolsTogglesScreen
import dev.aurakai.auraframefx.ui.gates.SphereGridScreen
import dev.aurakai.auraframefx.ui.gates.SystemJournalScreen
import dev.aurakai.auraframefx.ui.gates.SystemOverridesScreen
import dev.aurakai.auraframefx.ui.gates.TaskAssignmentScreen
import dev.aurakai.auraframefx.domains.aura.screens.TutorialVideosScreen
import dev.aurakai.auraframefx.ui.screens.WorkingLabScreen
import dev.aurakai.auraframefx.ui.gates.AuraThemingHubScreen
import dev.aurakai.auraframefx.ui.navigation.gates.AgentNexusGateScreen
import dev.aurakai.auraframefx.ui.navigation.gates.AuraGateScreen
import dev.aurakai.auraframefx.ui.navigation.gates.GenesisGateScreen
import dev.aurakai.auraframefx.ui.navigation.gates.HelpServicesGateScreen
import dev.aurakai.auraframefx.ui.navigation.gates.KaiGateScreen

/**
 * Main Navigation Graph
 * 3-Level Architecture: Carousel → Gate Grids → Feature Screens
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = NavDestination.HomeGateCarousel.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // ═══════════════════════════════════════════════════════════════
        // LEVEL 1: 3D GATE CAROUSEL
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.HomeGateCarousel.route) {
            EnhancedGateCarousel(
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 2: MAIN GATES (The Domain Grids)
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.AuraGate.route) {
            AuraGateScreen(navController)
        }
        composable(NavDestination.KaiGate.route) {
            KaiGateScreen(navController)
        }
        composable(NavDestination.GenesisGate.route) {
            GenesisGateScreen(navController)
        }
        composable(NavDestination.AgentNexusGate.route) {
            AgentNexusGateScreen(navController)
        }
        composable(NavDestination.HelpServicesGate.route) {
            HelpServicesGateScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 3: DOMAIN HUBS (Management Suites)
        // ═══════════════════════════════════════════════════════════════

        // -- Aura Hubs --
        composable(NavDestination.AuraThemingHub.route) {
            AuraThemingHubScreen(navController) 
        }
        composable(NavDestination.AuraLab.route) {
            AuraLabScreen(onNavigateBack = { navController.popBackStack() })
        }

        // -- Kai Hubs --
        composable(NavDestination.RomToolsHub.route) {
            KaiSentinelHubScreen(navController)
        }
        composable(NavDestination.LSPosedHub.route) {
            LSPosedSubmenuScreen(navController)
        }
        composable(NavDestination.SystemToolsHub.route) {
            KaiSentinelHubScreen(navController) 
        }

        // -- Genesis Hubs --
        composable(NavDestination.OracleDriveHub.route) {
            OracleDriveHubScreen(navController)
        }
        composable(NavDestination.AgentBridgeHub.route) {
            OracleDriveHubScreen(navController)
        }

        // -- Nexus Hubs --
        composable(NavDestination.ConstellationHub.route) {
            AgentNexusHubScreen(navController)
        }
        composable(NavDestination.MonitoringHub.route) {
            AgentNexusHubScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 4: TOOL SCREENS (Individual Controllers)
        // ═══════════════════════════════════════════════════════════════

        // Aura Tools
        composable(NavDestination.ChromaCoreColors.route) {
            ChromaCoreColorsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.IconifyPicker.route) {
            IconifyPickerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.ThemeEngine.route) {
            ThemeEngineScreen(onNavigateBack = { navController.popBackStack() })
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

        // Kai Tools
        composable(NavDestination.Bootloader.route) {
            BootloaderManagerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.RootTools.route) {
            RootToolsTogglesScreen(navController)
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
        composable(NavDestination.LSPosedModules.route) {
            LSPosedModuleManagerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.HookManager.route) {
            HookManagerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.SystemOverrides.route) {
            SystemOverridesScreen(onNavigateBack = { navController.popBackStack() })
        }

        // Genesis Tools
        composable(NavDestination.CodeAssist.route) {
            CodeAssistScreen(navController)
        }
        composable(NavDestination.NeuralNetwork.route) {
            NeuralArchiveScreen(navController)
        }

        // Nexus / Monitoring Tools
        composable(NavDestination.GenesisConstellation.route) {
            GenesisConstellationScreen(navController)
        }
        composable(NavDestination.ClaudeConstellation.route) {
            ClaudeConstellationScreen(navController)
        }
        composable(NavDestination.KaiConstellation.route) {
            KaiConstellationScreen(navController)
        }
        composable(NavDestination.CascadeConstellation.route) {
            CascadeConstellationScreen(navController)
        }
        composable(NavDestination.GrokConstellation.route) {
            GrokConstellationScreen(navController)
        }
        composable(NavDestination.AgentMonitoring.route) {
            AgentNexusHubScreen(navController)
        }
        composable(NavDestination.FusionMode.route) {
            FusionModeScreen()
        }
        composable(NavDestination.SphereGrid.route) {
            SphereGridScreen(navController)
        }

        // Utility & Infrastructure
        composable(NavDestination.HelpDesk.route) {
            HelpDeskScreen(onNavigateBack = { navController.popBackStack() })
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
        composable(NavDestination.ConferenceRoom.route) {
            ConferenceRoomScreen(
                onNavigateToChat = { },
                onNavigateToAgents = { },
                viewModel = hiltViewModel()
            )
        }
        composable(NavDestination.Settings.route) {
            Text("Settings Screen")
        }

        // Legacy / Compatibility Redirects
        composable(NavDestination.UIUXGateSubmenu.route) {
            AuraThemingHubScreen(navController)
        }
        composable(NavDestination.ROMToolsSubmenu.route) {
            KaiSentinelHubScreen(navController)
        }
        composable(NavDestination.OracleDriveSubmenu.route) {
            OracleDriveHubScreen(navController)
        }
    }
}

@Composable
private fun SimpleTitle(title: String) {
    Text("Screen: $title")
}
