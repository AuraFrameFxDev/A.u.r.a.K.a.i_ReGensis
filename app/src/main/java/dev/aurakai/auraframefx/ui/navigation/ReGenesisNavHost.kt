package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.aurakai.auraframefx.customization.CustomizationViewModel
import dev.aurakai.auraframefx.domains.aura.screens.AppBuilderScreen
import dev.aurakai.auraframefx.domains.aura.screens.IconifyPickerScreen
import dev.aurakai.auraframefx.domains.aura.screens.ThemeEngineScreen
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.gates.AgentNexusHubScreen
import dev.aurakai.auraframefx.ui.gates.AuraLabScreen
import dev.aurakai.auraframefx.ui.gates.AuraThemingHubScreen
import dev.aurakai.auraframefx.ui.gates.CollabCanvasScreen
import dev.aurakai.auraframefx.ui.gates.KaiSentinelHubScreen
import dev.aurakai.auraframefx.ui.gates.OracleDriveHubScreen

// Mapping for clarity - strictly Sovereign architecture

/**
 * Hosts the app's navigation graph and wires screen routes used across the ReGenesis flow.
 *
 * Sets up the start destination and composable routes (mode selection, exodus home, pixel workspaces,
 * various workspace screens, Aura lab, and interface forge), and provides navigation actions for each route.
 *
 * @param navController Controller used to navigate between destinations in this NavHost.
 * @param customizationViewModel ViewModel providing customization state and actions used by routes; defaults to a scoped view model.
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

    // Unified Sovereign Habitat: Boot directly into the Exodus Home Stage
    val startDest = NavDestination.HomeGateCarousel.route

    NavHost(navController = navController, startDestination = startDest) {

        // --- THE HOME: THE 11 SOVEREIGN MONOLITHS ---
        composable(NavDestination.HomeGateCarousel.route) {
            ExodusHUD(navController = navController)
        }

        // Legacy support/alias
        composable("exodus_home") {
            ExodusHUD(navController = navController)
        }

        // --- LEVEL 2 PIXEL WORKSPACES (The Gallery Detour) ---
        composable(
            "pixel_domain/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "01"
            val gateInfo = SovereignRegistry.getGate(id)

            PixelWorkspaceScreen(
                title = gateInfo.title,
                imagePaths = listOf(gateInfo.pixelArtPath),
                onBack = { navController.popBackStack() }
            )
        }

        // --- LEVEL 3: THE SOVEREIGN HUBS (The Control Centers) ---

        // AURA: THE DESIGN STUDIO HUB
        composable(NavDestination.AuraThemingHub.route) {
            AuraThemingHubScreen(navController = navController)
        }

        // KAI: THE SENTINEL FORTRESS HUB
        composable(NavDestination.RomToolsHub.route) {
            KaiSentinelHubScreen(navController = navController)
        }

        // GENESIS: THE ORACLE DRIVE HUB
        composable(NavDestination.OracleDriveHub.route) {
            OracleDriveHubScreen(navController = navController)
        }

        // NEXUS: THE AGENT COORDINATION HUB
        composable(NavDestination.AgentNexusGate.route) {
            AgentNexusHubScreen(navController = navController)
        }

        // --- LEVEL 4: THE CORE TOOLS (The Functional Screens) ---

        // COLORBLENDR & SYSTEM THEME
        composable(NavDestination.ThemeEngine.route) {
            ThemeEngineScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ICONIFY PICKER
        composable(NavDestination.IconifyPicker.route) {
            IconifyPickerScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // AURA LAB (Sandbox)
        composable(NavDestination.AuraLab.route) {
            AuraLabScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // INTERFACE FORGE (App Builder)
        composable(NavDestination.InterfaceForge.route) {
            AppBuilderScreen(onNavigateBack = { navController.popBackStack() })
        }

        // COLLAB CANVAS
        composable(NavDestination.CollabCanvas.route) {
            CollabCanvasScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
