package dev.aurakai.auraframefx.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Support
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.filled.ViewQuilt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.screens.HomeScreen
import dev.aurakai.auraframefx.ui.customization.AnimationType
import dev.aurakai.auraframefx.ui.customization.ComponentEditor
import dev.aurakai.auraframefx.ui.customization.ComponentType
import dev.aurakai.auraframefx.ui.customization.UIComponent
import dev.aurakai.auraframefx.ui.customization.ZOrderEditor
import dev.aurakai.auraframefx.iconify.IconPicker
import dev.aurakai.auraframefx.ui.gates.ChromaCoreColorsScreen
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
import dev.aurakai.auraframefx.ui.gates.HelpDeskSubmenuScreen
import dev.aurakai.auraframefx.ui.gates.HookManagerScreen
import dev.aurakai.auraframefx.ui.gates.KaiConstellationScreen
import dev.aurakai.auraframefx.ui.gates.LSPosedModuleManagerScreen
import dev.aurakai.auraframefx.ui.gates.LSPosedSubmenuScreen
import dev.aurakai.auraframefx.ui.gates.Level1GateScreen
import dev.aurakai.auraframefx.ui.gates.Level2GateItem
import dev.aurakai.auraframefx.ui.gates.Level2GateScreen
import dev.aurakai.auraframefx.ui.gates.LiveROMEditorScreen
import dev.aurakai.auraframefx.ui.gates.LiveSupportChatScreen
import dev.aurakai.auraframefx.ui.gates.LogsViewerScreen
import dev.aurakai.auraframefx.ui.gates.ModuleCreationScreen
import dev.aurakai.auraframefx.ui.gates.ModuleManagerScreen
import dev.aurakai.auraframefx.ui.gates.NotchBarScreen
import dev.aurakai.auraframefx.ui.gates.OverlayMenusScreen
import dev.aurakai.auraframefx.ui.gates.QuickActionsScreen
import dev.aurakai.auraframefx.ui.gates.QuickSettingsScreen
import dev.aurakai.auraframefx.ui.gates.ROMFlasherScreen
import dev.aurakai.auraframefx.ui.gates.ROMToolsSubmenuScreen
import dev.aurakai.auraframefx.ui.gates.RecoveryToolsScreen
import dev.aurakai.auraframefx.ui.gates.SphereGridScreen
import dev.aurakai.auraframefx.ui.gates.StatusBarScreen
import dev.aurakai.auraframefx.ui.gates.SupportChatViewModel
import dev.aurakai.auraframefx.ui.gates.SystemOverridesScreen
import dev.aurakai.auraframefx.ui.gates.TaskAssignmentScreen
import dev.aurakai.auraframefx.ui.gates.ThemeEngineScreen
import dev.aurakai.auraframefx.ui.gates.TutorialVideosScreen
import dev.aurakai.auraframefx.ui.gates.UIUXGateSubmenuScreen
import dev.aurakai.auraframefx.ui.identity.GenderSelectionNavigator
import dev.aurakai.auraframefx.ui.screens.AgentProfileScreen
import dev.aurakai.auraframefx.ui.screens.EcosystemMenuScreen
import dev.aurakai.auraframefx.ui.screens.HolographicMenuScreen
import dev.aurakai.auraframefx.ui.screens.IntroScreen
import dev.aurakai.auraframefx.ui.screens.JournalPDAScreen
import dev.aurakai.auraframefx.ui.screens.UISettingsScreen
import dev.aurakai.auraframefx.ui.screens.WorkingLabScreen
import dev.aurakai.auraframefx.ui.viewmodels.AgentViewModel

/**
 * Defines the application's navigation graph and registers all composable destinations used by the app.
 *
 * The provided NavHostController is used as the host for the NavHost and for performing navigation actions between routes.
 *
 * @param navController Controller that hosts navigation and executes route navigation actions.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavDestination.Gates.route
    ) {
        // ==================== MAIN SCREENS ====================

        composable(route = NavDestination.Home.route) {
            HomeScreen(navController = navController)
        }

        // --- NEW GATE SYSTEM ---

        // Level 1: Main Gate Hub
        composable(route = NavDestination.Gates.route) {
            Level1GateScreen(
                navController = navController,
                onGateClick = { route -> navController.navigate(route) }
            )
        }

        // Level 2: AURA GATE - THE FACE (All UI/UX, Visual Design, Customization)
        composable(route = "aura_gate") {
            val items = listOf(
                Level2GateItem("ChromaCore", "System Colors", Icons.Filled.ColorLens, NavDestination.ChromaCoreColors.route),
                Level2GateItem("UI/UX Studio", "Design Hub", Icons.Filled.Palette, NavDestination.UIUXDesignStudio.route),
                Level2GateItem("Theme Engine", "Styles & Fonts", Icons.Filled.Palette, NavDestination.ThemeEngine.route),
                Level2GateItem("Iconify", "250K+ Icons", Icons.Filled.ViewQuilt, NavDestination.IconifyPicker.route),
                Level2GateItem("Status Bar", "Top Bar Style", Icons.Filled.Palette, NavDestination.StatusBar.route),
                Level2GateItem("Notch Bar", "Display Notch", Icons.Filled.Palette, NavDestination.NotchBar.route),
                Level2GateItem("Quick Settings", "QS Tiles", Icons.Filled.Palette, NavDestination.QuickSettings.route),
                Level2GateItem("Overlay Menus", "Floating UI", Icons.Filled.ViewQuilt, NavDestination.OverlayMenus.route),
                Level2GateItem("3D Lab", "Gyro Editor", Icons.Filled.ViewQuilt, "gyroscope_customization"),
                Level2GateItem("Aura's Lab", "Wild Studio", Icons.Filled.Palette, NavDestination.AurasLab.route),
                Level2GateItem("Component Edit", "UI Editor", Icons.Filled.Palette, NavDestination.ComponentEditor.route),
                Level2GateItem("Z-Order Edit", "Layer Control", Icons.Filled.ViewQuilt, NavDestination.ZOrderEditor.route)
            )
            Level2GateScreen(navController, "AURA GATE", items, onBack = { navController.popBackStack() })
        }

        // Level 2: KAI GATE - ROOT TOOLS (Bootloader, ROM, Security, Hooks)
        composable(route = "kai_gate") {
            val items = listOf(
                Level2GateItem("LSPosed", "1440 Hooks", Icons.Filled.Extension, NavDestination.LSPosedGate.route),
                Level2GateItem("Hook Manager", "Xposed Mgmt", Icons.Filled.Extension, NavDestination.HookManager.route),
                Level2GateItem("Module Manager", "LSP Modules", Icons.Filled.Extension, NavDestination.ModuleManager.route),
                Level2GateItem("Module Create", "New Module", Icons.Filled.Extension, NavDestination.ModuleCreation.route),
                Level2GateItem("ROM Tools", "ROM Editor", Icons.Filled.Build, NavDestination.ROMTools.route),
                Level2GateItem("ROM Flasher", "System Flash", Icons.Filled.Build, NavDestination.ROMFlasher.route),
                Level2GateItem("Bootloader", "Boot Mgmt", Icons.Filled.Build, NavDestination.BootloaderManager.route),
                Level2GateItem("Recovery", "TWRP Tools", Icons.Filled.Build, NavDestination.RecoveryTools.route),
                Level2GateItem("System Override", "God Mode", Icons.Filled.Security, NavDestination.SystemOverrides.route),
                Level2GateItem("Logs Viewer", "System Logs", Icons.Filled.Terminal, NavDestination.LogsViewer.route)
            )
            Level2GateScreen(navController, "KAI GATE", items, onBack = { navController.popBackStack() })
        }

        // Level 2: GENESIS GATE - ORCHESTRATION (Agent Creation, Module Creator, Backend)
        composable(route = "genesis_gate") {
            val items = listOf(
                Level2GateItem("Conference", "6 Agents", Icons.Filled.Groups, NavDestination.CONFERENCE_ROOM),
                Level2GateItem("Agent Fusion", "Trinity Mode", Icons.Filled.Groups, NavDestination.FusionMode.route),
                Level2GateItem("Direct Chat", "Agent Talk", Icons.Filled.Groups, NavDestination.DirectChat.route),
                Level2GateItem("Code Assist", "AI Dev", Icons.Filled.Code, NavDestination.CodeAssist.route),
                Level2GateItem("Oracle Drive", "Data Core", Icons.Filled.Storage, NavDestination.OracleDrive.route),
                Level2GateItem("Module Create", "Gen Module", Icons.Filled.Extension, NavDestination.ModuleCreation.route)
            )
            Level2GateScreen(navController, "GENESIS GATE", items, onBack = { navController.popBackStack() })
        }

        // Level 2: CASCADE HUB - SPECIAL FUSION TOOLS
        composable(route = "cascade_gate") {
            val items = listOf(
                Level2GateItem("Cascade Vision", "Dashboard", Icons.Filled.ViewQuilt, NavDestination.CascadeConstellation.route),
                Level2GateItem("Agent Fusion", "Trinity Mode", Icons.Filled.Groups, NavDestination.FusionMode.route),
                Level2GateItem("Consensus", "Vote System", Icons.Filled.Groups, NavDestination.FusionMode.route),
                Level2GateItem("Trait Editor", "Personalities", Icons.Filled.Palette, NavDestination.AurasLab.route),
                Level2GateItem("Learning Models", "Evolution", Icons.Filled.Groups, NavDestination.Evolution.route),
                Level2GateItem("Blueprint", "Agent Factory", Icons.Filled.Groups, NavDestination.AgentNexus.route)
            )
            Level2GateScreen(navController, "CASCADE HUB", items, onBack = { navController.popBackStack() })
        }

        // Level 2: AGENT NEXUS - MONITORING & PROGRESSION
        composable(route = "agent_nexus") {
            // Direct to Agent Nexus screen with stats, monitoring, progression
             AgentNexusScreen()
        }

        // Level 2: Help Services
        composable(route = "help_gate") {
             val items = listOf(
                Level2GateItem("Live Help", "Chat Support", Icons.Filled.Support, NavDestination.LiveSupport.route),
                Level2GateItem("Documentation", "User Guides", Icons.Filled.Description, NavDestination.Documentation.route),
                Level2GateItem("Wiki/GitHub", "Community Knowledge", Icons.Filled.Groups, NavDestination.FAQBrowser.route),
                Level2GateItem("Community Hub", "Forums & Social", Icons.Filled.Groups, NavDestination.TutorialVideos.route) // Placeholder
            )
            Level2GateScreen(navController, "HELP SERVICES", items, onBack = { navController.popBackStack() })
        }

        // --- END NEW GATE SYSTEM ---

        composable(route = NavDestination.JournalPDA.route) {
            JournalPDAScreen(navController = navController)
        }

        composable(route = NavDestination.IntroScreen.route) {
            IntroScreen(onIntroComplete = {
                navController.navigate(NavDestination.Gates.route) {
                    popUpTo(NavDestination.IntroScreen.route) { inclusive = true }
                }
            })
        }

        composable(route = NavDestination.MainScreen.route) {
            MainScreen(
                onNavigateToAgentNexus = { navController.navigate(NavDestination.AgentHub.route) },
                onNavigateToOracleDrive = { navController.navigate(NavDestination.OracleDrive.route) },
                onNavigateToSettings = { navController.navigate(NavDestination.UISettings.route) },
            )
        }

        composable(route = NavDestination.WorkingLab.route) {
            WorkingLabScreen(onNavigate = { route -> navController.navigate(route) })
        }

        composable(route = NavDestination.AgentProfile.route) {
            AgentProfileScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.EcosystemMenu.route) {
            EcosystemMenuScreen()
        }

        composable(route = NavDestination.HolographicMenu.route) {
            HolographicMenuScreen(onNavigate = { route -> navController.navigate(route) })
        }

        composable(route = NavDestination.UISettings.route) {
            UISettingsScreen(navController = navController)
        }

        // ==================== AGENT HUB ====================

        composable(route = NavDestination.AgentHub.route) {
            AgentHubSubmenuScreen(navController = navController)
        }

        composable(route = NavDestination.DirectChat.route) {
            val viewModel = hiltViewModel<AgentViewModel>()
            DirectChatScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = NavDestination.TaskAssignment.route) {
            TaskAssignmentScreen()
        }

        composable(route = NavDestination.AgentMonitoring.route) {
            AgentMonitoringScreen()
        }

        composable(route = NavDestination.FusionMode.route) {
            FusionModeScreen()
        }

        composable(route = NavDestination.CodeAssist.route) {
            CodeAssistScreen(navController = navController)
        }

        // ==================== ORACLE DRIVE ====================

        composable(route = NavDestination.OracleDrive.route) {
        }

        composable(route = NavDestination.SphereGrid.route) {
            SphereGridScreen(navController = navController)
        }

        composable(route = NavDestination.Constellation.route) {
            ConstellationScreen(navController = navController)
        }

        composable(route = NavDestination.GenesisConstellation.route) {
            GenesisConstellationScreen(navController = navController)
        }

        composable(route = NavDestination.ClaudeConstellation.route) {
            ClaudeConstellationScreen(navController = navController)
        }

        composable(route = NavDestination.KaiConstellation.route) {
            KaiConstellationScreen(navController = navController)
        }

        composable(route = NavDestination.CascadeConstellation.route) {
            CascadeConstellationScreen(navController = navController)
        }

        composable(route = NavDestination.GrokConstellation.route) {
            GrokConstellationScreen(navController = navController)
        }

        // ==================== ROM TOOLS ====================

        composable(route = NavDestination.ROMTools.route) {
            ROMToolsSubmenuScreen(navController = navController)
        }

        composable(route = NavDestination.LiveROMEditor.route) {
            LiveROMEditorScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.ROMFlasher.route) {
            ROMFlasherScreen()
        }

        composable(route = NavDestination.RecoveryTools.route) {
            RecoveryToolsScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.BootloaderManager.route) {
            BootloaderManagerScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ==================== LSPOSED INTEGRATION ====================

        composable(route = NavDestination.LSPosedGate.route) {
            LSPosedSubmenuScreen(navController = navController)
        }

        composable(route = NavDestination.ModuleManager.route) {
            ModuleManagerScreen()
        }

        composable(route = NavDestination.LSPosedModuleManager.route) {
            LSPosedModuleManagerScreen()
        }

        composable(route = NavDestination.ModuleCreation.route) {
            ModuleCreationScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.HookManager.route) {
            HookManagerScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.LogsViewer.route) {
            LogsViewerScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ==================== UI/UX DESIGN STUDIO ====================

        composable(route = NavDestination.UIUXDesignStudio.route) {
            UIUXGateSubmenuScreen(navController = navController)
        }

        composable(route = NavDestination.ThemeEngine.route) {
            ThemeEngineScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.IconifyPicker.route) {
            IconPicker(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.ChromaCoreColors.route) {
            ChromaCoreColorsScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.StatusBar.route) {
            StatusBarScreen()
        }

        composable(route = NavDestination.NotchBar.route) {
            NotchBarScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.QuickSettings.route) {
            QuickSettingsScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.OverlayMenus.route) {
            OverlayMenusScreen()
        }

        composable(route = NavDestination.QuickActions.route) {
            QuickActionsScreen()
        }

        composable(route = NavDestination.SystemOverrides.route) {
            SystemOverridesScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ==================== HELP DESK ====================

        composable(route = NavDestination.HelpDesk.route) {
            HelpDeskSubmenuScreen(navController = navController)
        }

        composable(route = NavDestination.LiveSupport.route) {
            val viewModel = hiltViewModel<SupportChatViewModel>()
            LiveSupportChatScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = NavDestination.Documentation.route) {
            DocumentationScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.FAQBrowser.route) {
            FAQBrowserScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = NavDestination.TutorialVideos.route) {
            TutorialVideosScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ==================== AURA'S LAB ====================

        composable(route = NavDestination.AurasLab.route) {
            AurasLabScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ==================== CUSTOMIZATION TOOLS ====================

        composable(route = NavDestination.ComponentEditor.route) {
            // Recommendation: If you have a state-holder for the selected component, use it here
            ComponentEditor(
                component = UIComponent(
                    id = "current",
                    name = "Editor",
                    type = ComponentType.STATUS_BAR,
                    height = 50f,
                    backgroundColor = Color.White,
                    animationType = AnimationType.NONE,
                ),
                onUpdate = { },
                onClose = { navController.popBackStack() }
            )
        }

        composable(route = NavDestination.ZOrderEditor.route) {
            ZOrderEditor(
                elements = emptyList(),
                onReorder = { },
                onElementSelected = { },
                onClose = { navController.popBackStack() }
            )
        }

        // ==================== IDENTITY ====================

        composable(route = NavDestination.GenderSelection.route) {
            GenderSelectionNavigator(
                onGenderSelected = { navController.popBackStack() }
            )
        }

        // ==================== MISSING ROUTES FIX ====================

        // Agent Nexus - Legacy gamification screen
        composable(route = NavDestination.AgentNexus.route) {
            // Redirect to AgentHub for now
            AgentHubSubmenuScreen(navController = navController)
        }

        // Evolution Tree - Agent advancement visualization  
        composable(route = NavDestination.EvolutionTree.route) {
            // Redirect to Constellation for now
            ConstellationScreen(navController = navController)
        }

        // Live Support Chat (alternate route name)
        composable(route = NavDestination.LiveSupportChat.route) {
            val viewModel = hiltViewModel<SupportChatViewModel>()
            LiveSupportChatScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

private fun AnimatedContentScope.MainScreen(
    onNavigateToAgentNexus: () -> Unit,
    onNavigateToOracleDrive: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    // TODO: Implement MainScreen or remove usage if not needed
}
