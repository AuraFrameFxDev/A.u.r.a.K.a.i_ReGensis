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
import dev.aurakai.auraframefx.ui.gates.AuraLabScreen
import dev.aurakai.auraframefx.ui.screens.ModeSelectionScreen

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
    val startDest = "exodus_home"

    NavHost(navController = navController, startDestination = startDest) {

        // --- THE MODE SELECTOR ---
        composable("mode_selection") {
            ModeSelectionScreen(
                onModeSelected = { mode ->
                    customizationViewModel.setReGenesisMode(context, mode)
                    navController.navigate("exodus_home") {
                        popUpTo("mode_selection") { inclusive = true }
                    }
                }
            )
        }

        // --- THE HOME: THE 11 SOVEREIGN MONOLITHS ---
        composable("exodus_home") {
            ExodusHUD(navController = navController)
        }

        // --- LEVEL 2 PIXEL WORKSPACES ---
        composable(
            "pixel_domain/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "01"

            // Aggressive Manifest: Fetch directly from Sovereign Registry
            val gateInfo = SovereignRegistry.getGate(id)

            PixelWorkspaceScreen(
                title = gateInfo.title,
                imagePaths = listOf(gateInfo.pixelArtPath),
                onBack = { navController.popBackStack() }
            )
        }

        composable("workspace_kai") {
            KaiRootWorkspaceScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable("workspace_aura") {
            val gateInfo = SovereignRegistry.getGate("03")
            PixelWorkspaceScreen(
                title = "AURA'S DESIGN STUDIO",
                imagePaths = listOf(gateInfo.pixelArtPath),
                onBack = { navController.popBackStack() }
            )
        }

        composable("workspace_genesis") {
            val gateInfo = SovereignRegistry.getGate("01")
            PixelWorkspaceScreen(
                title = "GENESIS ARCHITECTURE HUB",
                imagePaths = listOf(gateInfo.pixelArtPath),
                onBack = { navController.popBackStack() }
            )
        }

        composable("aura_lab") {
            AuraLabScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("interface_forge") {
            AppBuilderScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
