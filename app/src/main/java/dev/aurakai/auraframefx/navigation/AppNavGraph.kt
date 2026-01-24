package dev.aurakai.auraframefx.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

// Level 1: Gate carousel
import dev.aurakai.auraframefx.ui.components.carousel.EnhancedGateCarousel

// Level 2: Gate screens  
import dev.aurakai.auraframefx.ui.navigation.gates.AgentNexusGateScreen
import dev.aurakai.auraframefx.ui.navigation.gates.AuraGateScreen
import dev.aurakai.auraframefx.ui.navigation.gates.GenesisGateScreen
import dev.aurakai.auraframefx.ui.navigation.gates.HelpServicesGateScreen
import dev.aurakai.auraframefx.ui.navigation.gates.KaiGateScreen

// Level 3: Feature screens (import only the ones that exist)
import dev.aurakai.auraframefx.domains.aura.screens.*
import dev.aurakai.auraframefx.ui.gates.*

/**
 * Main Navigation Graph
 * 3-Level Architecture: Carousel → Gate Grids → Feature Screens
 */
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
            UIUXGateSubmenuScreen(navController)
        }
        composable(NavDestination.AuraLab.route) {
            SimpleTitle("Aura Lab - Coming Soon")
        }

        // UI/UX Sub-screens
        composable(NavDestination.ChromaCoreColors.route) {
            ChromaCoreColorsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.IconifyPicker.route) {
            // TODO: Inject IconifyService from Hilt
            // IconifyPickerScreen(iconifyService, onNavigateBack = { navController.popBackStack() })
            SimpleTitle("Iconify Picker - Service injection needed")
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
        
        composable(NavDestination.CodeAssist.route) {
            OracleDriveSubmenuScreen(navController)
        }
        composable(NavDestination.OracleDriveSubmenu.route) {
            OracleDriveSubmenuScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 3: KAI DOMAIN SCREENS
        // ═══════════════════════════════════════════════════════════════
        
        composable(NavDestination.ROMToolsSubmenu.route) {
            ROMToolsSubmenuScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 3: AGENT NEXUS SCREENS
        // ═══════════════════════════════════════════════════════════════
        
        composable(NavDestination.PartyScreen.route) {
            AgentHubSubmenuScreen(navController)
        }
        composable("claude_constellation") {
            ClaudeConstellationScreen(navController)
        }
        composable("sphere_grids") {
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
            ConferenceRoomScreen()
        }
        composable(NavDestination.SphereGrid.route) {
            SphereGridScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 3: HELP SERVICES SCREENS
        // ═══════════════════════════════════════════════════════════════
        
        composable(NavDestination.HelpDeskSubmenu.route) {
            HelpServicesGateScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // LSPOSED SCREENS
        // ═══════════════════════════════════════════════════════════════
        
        composable("lsposed_panel") {
            LSPosedSubmenuScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // ADDITIONAL ROUTES (from MainActivity sidebar)
        // ═══════════════════════════════════════════════════════════════
        
        composable(NavDestination.OverlayMenus.route) {
            OverlayMenusScreen(navController = navController)
        }

        composable(NavDestination.AgentHub.route) {
            AgentNexusGateScreen(navController)
        }

        composable(NavDestination.TaskAssignment.route) {
            // TaskAssignmentScreen expects AgentViewModel, use placeholder
            SimpleTitle("Task Assignment")
        }

        composable(NavDestination.ModuleCreation.route) {
            // ModuleCreationScreen expects () -> Unit, use placeholder
            SimpleTitle("Module Creation")
        }

        composable(NavDestination.DirectChat.route) {
            DirectChatScreen(navController)
        }
        
        composable(NavDestination.SystemOverrides.route) {
            // SystemOverridesScreen expects () -> Unit, use placeholder
            SimpleTitle("System Overrides")
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
