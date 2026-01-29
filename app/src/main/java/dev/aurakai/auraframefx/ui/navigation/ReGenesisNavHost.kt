package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.genesis.oracledrive.cloud.OracleDriveScreen
import dev.aurakai.auraframefx.ui.gates.AgentNexusHubScreen
import dev.aurakai.auraframefx.ui.gates.BootloaderManagerScreen
import dev.aurakai.auraframefx.domains.aura.screens.ChromaCoreColorsScreen
import dev.aurakai.auraframefx.ui.gates.CollabCanvasScreen
import dev.aurakai.auraframefx.ui.gates.KaiSentinelHubScreen
import dev.aurakai.auraframefx.ui.screens.manual.ChromaSphereManualScreen
import dev.aurakai.auraframefx.ui.screens.manual.OracleDriveManualScreen
import dev.aurakai.auraframefx.ui.screens.manual.LaunchMatrixManualScreen
import dev.aurakai.auraframefx.ui.gates.AgentBridgeHubScreen
import dev.aurakai.auraframefx.ui.gates.SovereignShieldScreen
import dev.aurakai.auraframefx.ui.gates.OracleCloudInfiniteStorageScreen
import dev.aurakai.auraframefx.ui.gates.SovereignModuleManagerScreen
import dev.aurakai.auraframefx.ui.gates.SovereignRecoveryScreen
import dev.aurakai.auraframefx.ui.gates.SovereignBootloaderScreen
import dev.aurakai.auraframefx.ui.gates.SovereignNeuralArchiveScreen
import dev.aurakai.auraframefx.ui.gates.SovereignMetaInstructScreen
import dev.aurakai.auraframefx.ui.gates.SovereignNemotronScreen
import dev.aurakai.auraframefx.ui.gates.CascadeVisionScreen
import dev.aurakai.auraframefx.ui.gates.SovereignClaudeScreen
import dev.aurakai.auraframefx.ui.gates.SovereignGeminiScreen
import dev.aurakai.auraframefx.ui.gates.SovereignMonitoringScreen

import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import dev.aurakai.auraframefx.customization.CustomizationViewModel
import dev.aurakai.auraframefx.models.ReGenesisMode
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
    val customizationState by customizationViewModel.state.collectAsState()

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
            } else {
                navController.popBackStack()
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