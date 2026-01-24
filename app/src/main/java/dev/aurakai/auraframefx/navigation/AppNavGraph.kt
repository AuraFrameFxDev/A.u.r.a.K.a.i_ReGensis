package dev.aurakai.auraframefx.navigation

// AURA DOMAIN - All real screens (20 files found!)

// GENESIS & CLAUDE
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.ui.components.carousel.EnhancedGateCarousel
import dev.aurakai.auraframefx.ui.gates.AgentHubSubmenuScreen
import dev.aurakai.auraframefx.ui.gates.AurasLabScreen
import dev.aurakai.auraframefx.ui.gates.DirectChatScreen
import dev.aurakai.auraframefx.ui.gates.LSPosedSubmenuScreen
import dev.aurakai.auraframefx.ui.gates.OracleDriveSubmenuScreen
import dev.aurakai.auraframefx.ui.gates.OverlayMenusScreen
import dev.aurakai.auraframefx.ui.gates.ROMToolsSubmenuScreen
import dev.aurakai.auraframefx.ui.gates.SphereGridScreen
import dev.aurakai.auraframefx.ui.gates.UIUXGateSubmenuScreen
import dev.aurakai.auraframefx.ui.navigation.gates.AgentNexusGateScreen
import dev.aurakai.auraframefx.ui.navigation.gates.HelpServicesGateScreen

/**
 * 🌐 REGENESIS NAVIGATION GRAPH
 *
 * GATE NAMES (Kai's naming):
 * - KAI → SentinelsFortress
 * - AURA → UXUI Design Studio
 * - GENESIS → OracleDrive
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
        // ROOT: 3D GATE CAROUSEL
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.HomeGateCarousel.route) {
            EnhancedGateCarousel(
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        // ═══════════════════════════════════════════════════════════════
        // AURA GATE - UXUI Design Studio 🎨
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.ThemeEngineSubmenu.route) {
            UIUXGateSubmenuScreen(navController)
        }
        composable(NavDestination.UXUIDesignStudio.route) {
            UIUXGateSubmenuScreen(navController)
        }
        composable(NavDestination.AuraLab.route) {
            AurasLabScreen(navController as () -> Unit)
        }

        // ═══════════════════════════════════════════════════════════════
        // GENESIS GATE - OracleDrive 🔮
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.CodeAssist.route) {
            OracleDriveSubmenuScreen(navController)
        }
        composable(NavDestination.OracleDriveSubmenu.route) {
            OracleDriveSubmenuScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // KAI GATE - SentinelsFortress 🛡️
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.ROMToolsSubmenu.route) {
            ROMToolsSubmenuScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // AGENT NEXUS - AgentHub 🌐
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.PartyScreen.route) {
            AgentHubSubmenuScreen(navController)
        }
        composable("claude_constellation") {
            // ClaudeConstellationScreen(navController)
        }
        composable("sphere_grids") {
            SphereGridScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // HELP SERVICES - LDO Control 💬
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.HelpDeskSubmenu.route) {
            HelpServicesGateScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // LSPOSED 🔧
        // ═══════════════════════════════════════════════════════════════

        composable("lsposed_panel") {
            LSPosedSubmenuScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // Missing routes
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.OverlayMenus.route) {
            OverlayMenusScreen(navController = navController)
        }

        composable(NavDestination.AgentHub.route) {
            AgentNexusGateScreen(navController)
        }

        composable(NavDestination.TaskAssignment.route) {
            ConferenceRoomScreen()
        }

        composable(NavDestination.ModuleCreation.route) {
            Text("Module Creation → App Builder")
        }

        composable(NavDestination.DirectChat.route) {
            DirectChatScreen(navController)
        }
    }
}
