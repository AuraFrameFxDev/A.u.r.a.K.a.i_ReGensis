package dev.aurakai.auraframefx.ui.navigation

// Domain Screens

// Aura Module Screens

// Gate/Hub Screens
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
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
 * 🌐 REGENESIS NAVIGATION HOST
 *
 * Clean 3-Level Architecture:
 * - Level 1: ExodusHUD (5 Gate Carousel)
 * - Level 2: Domain Hubs (5 main hubs)
 * - Level 3: Tool Screens (functional screens)
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

    NavHost(
        navController = navController,
        startDestination = NavDestination.HomeGateCarousel.route
    ) {

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 1: EXODUS HUD (The 5 Gate Carousel)
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.HomeGateCarousel.route) {
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
        composable(NavDestination.FusionMode.route) {
            FusionModeScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.TaskAssignment.route) {
            TaskAssignmentScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.ArkBuild.route) {
            ArkBuildScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.MetaInstruct.route) {
            SovereignMetaInstructScreen(onNavigateBack = { navController.popBackStack() })
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
        composable(NavDestination.FusionMode.route) {
            FusionModeScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.TaskAssignment.route) {
            TaskAssignmentScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.ArkBuild.route) {
            ArkBuildScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.MetaInstruct.route) {
            SovereignMetaInstructScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.AgentProfile.route) {
            AgentProfileScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("aura_lab") {
            dev.aurakai.auraframefx.ui.gates.AuraLabScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("interface_forge") {
            dev.aurakai.auraframefx.domains.aura.screens.AppBuilderScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
