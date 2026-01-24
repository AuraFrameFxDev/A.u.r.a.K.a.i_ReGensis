package dev.aurakai.auraframefx.aura

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.components.carousel.EnhancedGateCarousel
import dev.aurakai.auraframefx.ui.gates.*
import dev.aurakai.auraframefx.ui.navigation.gates.AgentNexusGateScreen
import dev.aurakai.auraframefx.ui.navigation.gates.HelpServicesGateScreen
import dev.aurakai.auraframefx.ui.screens.MainScreen

/**
 * 🌐 AURA FRAME NAVIGATION
 * Legacy navigation file - main navigation is now in AppNavGraph.kt
 * This file kept for backward compatibility
 */
@Composable
fun AuraFrameNavigation(
    navController: NavHostController,
    startDestination: String = NavDestination.HomeGateCarousel.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Home/Main Screen
        composable(NavDestination.HomeGateCarousel.route) {
            EnhancedGateCarousel(
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        // Main Screen (DEPRECATED - use 3D carousel instead)
        composable("main_screen") {
            // TODO: Wire up MainScreen with proper themeViewModel
            androidx.compose.material3.Text("Use 3D Gate Carousel as main entry")
        }

        // Agent Nexus
        composable(NavDestination.AgentNexus.route) {
            AgentNexusGateScreen(navController)
        }

        // Oracle Drive
        composable(NavDestination.OracleDriveSubmenu.route) {
            OracleDriveSubmenuScreen(navController)
        }

        // Settings (placeholder)
        composable(NavDestination.Settings.route) {
            androidx.compose.material3.Text("Settings Screen")
        }

        // ROM Tools
        composable(NavDestination.ROMTools.route) {
            ROMToolsSubmenuScreen(navController)
        }

        // Help Desk
        composable(NavDestination.HelpDesk.route) {
            HelpServicesGateScreen(navController)
        }

        // Sphere Grid
        composable(NavDestination.SphereGrid.route) {
            SphereGridScreen(navController)
        }

        // UI/UX Design Studio
        composable(NavDestination.UXUIDesignStudio.route) {
            UIUXGateSubmenuScreen(navController)
        }

        // Agent Hub
        composable(NavDestination.AgentHub.route) {
            AgentHubSubmenuScreen(navController)
        }

        // Fusion Mode
        composable(NavDestination.FusionMode.route) {
            FusionModeScreen()
        }

        // Conference Room (placeholder)
        composable(NavDestination.ConferenceRoom.route) {
            androidx.compose.material3.Text("Conference Room")
        }

        // Constellations
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
    }
}
