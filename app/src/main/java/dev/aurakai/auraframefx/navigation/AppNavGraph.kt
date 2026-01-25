package dev.aurakai.auraframefx.navigation

// Level 1: Gate carousel

// Level 2: Gate screens

// Level 3: Feature screens - ALL REAL SCREENS (no more wildcards)
// ui/gates screens (NOTE: Some physically in domains/aura/screens but declare package ui.gates)

// domains/aura/screens (those that actually use domains package)
import androidx.compose.animation.AnimatedContentScope
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
import dev.aurakai.auraframefx.aura.ui.ConferenceRoomScreen
import dev.aurakai.auraframefx.ui.gates.LiveSupportChatScreen
import dev.aurakai.auraframefx.ui.components.carousel.EnhancedGateCarousel
import dev.aurakai.auraframefx.ui.gates.AgentHubSubmenuScreen
import dev.aurakai.auraframefx.ui.gates.AgentMonitoringScreen
import dev.aurakai.auraframefx.ui.gates.AurasLabScreen
import dev.aurakai.auraframefx.ui.gates.BootloaderManagerScreen
import dev.aurakai.auraframefx.ui.gates.CascadeConstellationScreen
import dev.aurakai.auraframefx.ui.gates.ClaudeConstellationScreen
import dev.aurakai.auraframefx.ui.gates.CodeAssistScreen
import dev.aurakai.auraframefx.ui.gates.ConstellationScreen
import dev.aurakai.auraframefx.ui.gates.DirectChatScreen
import dev.aurakai.auraframefx.ui.gates.DocumentationScreen
import dev.aurakai.auraframefx.ui.gates.FAQBrowserScreen
import dev.aurakai.auraframefx.ui.gates.FusionModeScreen
import dev.aurakai.auraframefx.ui.gates.GenesisConstellationScreen
import dev.aurakai.auraframefx.ui.gates.GrokConstellationScreen
import dev.aurakai.auraframefx.ui.gates.HelpDeskScreen
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
import dev.aurakai.auraframefx.ui.gates.OracleDriveSubmenuScreen
import dev.aurakai.auraframefx.ui.gates.OverlayMenusScreen
import dev.aurakai.auraframefx.ui.gates.QuickActionsScreen
import dev.aurakai.auraframefx.ui.gates.ROMFlasherScreen
import dev.aurakai.auraframefx.ui.gates.ROMToolsSubmenuScreen
import dev.aurakai.auraframefx.ui.gates.RecoveryToolsScreen
import dev.aurakai.auraframefx.ui.gates.RootToolsTogglesScreen
import dev.aurakai.auraframefx.ui.gates.SphereGridScreen
import dev.aurakai.auraframefx.ui.gates.SystemJournalScreen
import dev.aurakai.auraframefx.ui.gates.SystemOverridesScreen
import dev.aurakai.auraframefx.ui.gates.TaskAssignmentScreen
import dev.aurakai.auraframefx.ui.gates.TutorialVideosScreen
import dev.aurakai.auraframefx.ui.gates.UIUXGateSubmenuScreen
import dev.aurakai.auraframefx.ui.screens.WorkingLabScreen
import dev.aurakai.auraframefx.ui.gates.UIUXDesignStudioScreen
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
        // LEVEL 2: GATE GRID SCREENS
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
        // LEVEL 3: AURA DOMAIN SCREENS
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.ThemeEngineSubmenu.route) {
            UIUXGateSubmenuScreen(navController)
        }
        composable(NavDestination.UIUXGateSubmenu.route) {
            UIUXDesignStudioScreen(navController)
        }
        composable(NavDestination.AuraLab.route) {
             AurasLabScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.CollabCanvas.route) {
            WorkingLabScreen(onNavigate = { route -> navController.navigate(route) })
        }

        // UI/UX Sub-screens
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

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 3: GENESIS DOMAIN SCREENS
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.OracleDriveSubmenu.route) {
            OracleDriveSubmenuScreen(navController)
        }

        // AI & Code - FIXED: CodeAssist now points to correct screen!
        composable(NavDestination.CodeAssist.route) {
            CodeAssistScreen(navController) // FIXED! Was OracleDriveSubmenuScreen
        }
        composable(NavDestination.NeuralNetwork.route) {
            NeuralArchiveScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 3: KAI DOMAIN SCREENS
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.ROMToolsSubmenu.route) {
            ROMToolsSubmenuScreen(navController)
        }

        // ROM & Boot Management - ALL REAL SCREENS WIRED!
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

        // LSPosed & Hooks - ALL REAL SCREENS WIRED!
        composable(NavDestination.LSPosedPanel.route) {
            LSPosedGateScreen()
        }
        composable(NavDestination.LSPosedModules.route) {
            LSPosedModuleManagerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.HookManager.route) {
            HookManagerScreen(onNavigateBack = { navController.popBackStack() })
        }

        // System Tools - ALL REAL SCREENS WIRED!
        composable(NavDestination.LogsViewer.route) {
            LogsViewerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.SystemJournal.route) {
            SystemJournalScreen(navController)
        }
        composable(NavDestination.QuickActions.route) {
            QuickActionsScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 3: AGENT NEXUS SCREENS
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.PartyScreen.route) {
            AgentHubSubmenuScreen(navController)
        }
        composable(NavDestination.ClaudeConstellation.route) {
            ClaudeConstellationScreen(navController)
        }
        composable(NavDestination.SphereGrids.route) {
            SphereGridScreen(navController)
        }

        // Constellation Screens
        composable(NavDestination.Constellation.route) {
            ConstellationScreen(navController)
        }
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
            AgentMonitoringScreen()
        }
        composable(NavDestination.FusionMode.route) {
            FusionModeScreen()
        }
        composable(NavDestination.ConferenceRoom.route) {
            ConferenceRoomScreen(
                onNavigateToChat = { /* navController.navigate(...) */ },
                onNavigateToAgents = { /* navController.navigate(...) */ }
            )
        }
        composable(NavDestination.SphereGrid.route) {
            SphereGridScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 3: HELP SERVICES SCREENS - ALL REAL SCREENS WIRED!
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.HelpDeskSubmenu.route) {
            HelpServicesGateScreen(navController)
        }
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
        composable(NavDestination.LiveHelp.route) {
            LiveSupportChatScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ═══════════════════════════════════════════════════════════════
        // LSPOSED SCREENS
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.LSPosedSubmenu.route) {
            LSPosedSubmenuScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // ADDITIONAL ROUTES (from MainActivity sidebar)
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.OverlayMenus.route) {
            OverlayMenusScreen(navController = navController)
        }

        composable(NavDestination.AgentHub.route) {
            AgentHubSubmenuScreen(navController)
        }

        composable(NavDestination.TaskAssignment.route) {
            TaskAssignmentScreen()
        }

        composable(NavDestination.ModuleCreation.route) {
            ModuleCreationScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(NavDestination.DirectChat.route) {
            DirectChatScreen(navController)
        }

        composable(NavDestination.SystemOverrides.route) {
            SystemOverridesScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(NavDestination.ModuleManager.route) {
            ModuleManagerScreen()
        }
    }
}

@Composable
private fun SimpleTitle(title: String) {
    Text("Screen: $title")
}
