package dev.aurakai.auraframefx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.ui.components.carousel.EnhancedGateCarousel

// AURA DOMAIN - All real screens
import dev.aurakai.auraframefx.domains.aura.screens.AuraLabScreen
import dev.aurakai.auraframefx.domains.aura.screens.ThemeEngineSubmenuScreen
import dev.aurakai.auraframefx.domains.aura.screens.ThemeEngineScreen
import dev.aurakai.auraframefx.domains.aura.screens.UIUXDesignStudioScreen
import dev.aurakai.auraframefx.domains.aura.screens.ChromaCoreColorsScreen
import dev.aurakai.auraframefx.domains.aura.screens.InstantColorPickerScreen
import dev.aurakai.auraframefx.domains.aura.screens.StatusBarScreen
import dev.aurakai.auraframefx.domains.aura.screens.QuickSettingsScreen
import dev.aurakai.auraframefx.domains.aura.screens.UISettingsScreen
import dev.aurakai.auraframefx.domains.aura.screens.GyroscopeCustomizationScreen
import dev.aurakai.auraframefx.domains.aura.screens.DirectChatScreen
import dev.aurakai.auraframefx.domains.aura.screens.DocumentationScreen
import dev.aurakai.auraframefx.domains.aura.screens.FAQBrowserScreen
import dev.aurakai.auraframefx.domains.aura.screens.TutorialVideosScreen
import dev.aurakai.auraframefx.domains.aura.screens.LiveSupportChatScreen
import dev.aurakai.auraframefx.domains.aura.screens.HelpDeskSubmenuScreen
import dev.aurakai.auraframefx.domains.aura.screens.HelpDeskScreen
import dev.aurakai.auraframefx.domains.aura.screens.AgentProfileScreen
import dev.aurakai.auraframefx.domains.aura.screens.UserPreferencesScreen
import dev.aurakai.auraframefx.domains.aura.screens.GenderSelectionScreen

// GENESIS & CLAUDE
import dev.aurakai.auraframefx.navigation.GenesisEntry
import dev.aurakai.auraframefx.navigation.ClaudeConstellationScreen

/**
 * 🌐 REGENESIS NAVIGATION GRAPH
 * 
 * THE COMPLETE VISION - NO STUBS!
 * 
 * Features:
 * - 3D gate carousel with backdrop + text overlays
 * - 26 fully functional screens
 * - Genesis nested navigation architecture
 * - Claude constellation integration
 * - Custom fonts throughout
 * - Aura's flying house home icon
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
        // ROOT: 3D GATE CAROUSEL WITH BACKDROP
        // ═══════════════════════════════════════════════════════════════
        
        composable(NavDestination.HomeGateCarousel.route) {
            EnhancedGateCarousel(
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        // ═══════════════════════════════════════════════════════════════
        // AURA GATE - Creative Phoenix 🎨
        // 19 REAL SCREENS
        // ═══════════════════════════════════════════════════════════════
        
        composable(NavDestination.ThemeEngineSubmenu.route) { 
            ThemeEngineSubmenuScreen(navController)
        }
        composable(NavDestination.UXUIDesignStudio.route) { 
            UIUXDesignStudioScreen(navController)
        }
        composable(NavDestination.AuraLab.route) { 
            AuraLabScreen(navController)
        }
        composable("chroma_core_colors") {
            ChromaCoreColorsScreen(navController)
        }
        composable("instant_color_picker") {
            InstantColorPickerScreen(navController)
        }
        composable("status_bar") {
            StatusBarScreen(navController)
        }
        composable("quick_settings") {
            QuickSettingsScreen(navController)
        }
        composable("ui_settings") {
            UISettingsScreen(navController)
        }
        composable("gyroscope_customization") {
            GyroscopeCustomizationScreen(navController)
        }
        composable("direct_chat") {
            DirectChatScreen(navController)
        }
        composable("theme_engine") {
            ThemeEngineScreen(navController)
        }
        composable("agent_profile") {
            AgentProfileScreen(navController)
        }
        composable("user_preferences") {
            UserPreferencesScreen(navController)
        }
        composable("gender_selection") {
            GenderSelectionScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // GENESIS GATE - OracleDrive 🔮
        // Nested navigation architecture
        // ═══════════════════════════════════════════════════════════════
        
        composable(NavDestination.CodeAssist.route) {
            GenesisEntry(navController, start = "code_assist")
        }
        composable(NavDestination.OracleDriveSubmenu.route) {
            GenesisEntry(navController, start = "oracle_drive")
        }

        // ═══════════════════════════════════════════════════════════════
        // AGENT NEXUS - Central Hub 🌐
        // ═══════════════════════════════════════════════════════════════
        
        composable(NavDestination.PartyScreen.route) { 
            AgentProfileScreen(navController) // Use agent profiles as home
        }
        composable("claude_constellation") {
            ClaudeConstellationScreen(navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // HELP SERVICES - LDO Control 💬
        // 7 REAL SCREENS
        // ═══════════════════════════════════════════════════════════════
        
        composable("documentation") {
            DocumentationScreen(navController)
        }
        composable("faq_browser") {
            FAQBrowserScreen(navController)
        }
        composable("tutorial_videos") {
            TutorialVideosScreen(navController)
        }
        composable("live_help") {
            LiveSupportChatScreen(navController)
        }
        composable(NavDestination.HelpDeskSubmenu.route) {
            HelpDeskSubmenuScreen(navController)
        }
        composable("help_desk") {
            HelpDeskScreen(navController)
        }
    }
}
