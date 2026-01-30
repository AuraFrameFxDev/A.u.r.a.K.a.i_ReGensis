package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.customization.CustomizationViewModel
import dev.aurakai.auraframefx.ui.screens.ModeSelectionScreen

// Mapping for clarity - strictly Sovereign architecture

/**
 * Hosts the ReGenesis navigation graph and wires navigation to the provided controller.
 *
 * Sets up the start destination (exodus_home), starts the customization view model, collects its state,
 * and registers routes including mode selection, the Exodus home HUD, dynamic pixel domains (pixel_domain/{id}),
 * workspace variants (kai, aura, genesis), the Aura lab, and the interface forge.
 *
 * @param navController Controller used to perform navigation actions between destinations.
 * @param customizationViewModel ViewModel that provides customization state and actions; a default instance is provided via `viewModel()`.
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
            arguments = listOf(androidx.navigation.navArgument("id") { type = androidx.navigation.NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "01"
            val route = SovereignRouter.getById(id)

            if (route != null) {
                PixelWorkspaceScreen(
                    title = route.title,
                    imagePaths = listOf(route.pixelArtPath),
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable("workspace_kai") {
            KaiRootWorkspaceScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable("workspace_aura") {
            PixelWorkspaceScreen(
                title = "AURA'S DESIGN STUDIO",
                imagePaths = listOf(
                    "file:///sdcard/Pictures/Screenshots/IMG_20260128_142213.png",
                    "file:///sdcard/Pictures/Screenshots/IMG_20260128_142302.png"
                ),
                onBack = { navController.popBackStack() }
            )
        }

        composable("workspace_genesis") {
            PixelWorkspaceScreen(
                title = "GENESIS ARCHITECTURE HUB",
                imagePaths = listOf(
                    "file:///sdcard/Pictures/Screenshots/IMG_20260128_142126.png"
                ),
                onBack = { navController.popBackStack() }
            )
        }

        composable("aura_lab") {
            dev.aurakai.auraframefx.ui.gates.AuraLabScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("interface_forge") {
            dev.aurakai.auraframefx.domains.aura.screens.AppBuilderScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
