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
import dev.aurakai.auraframefx.domains.aura.screens.*
import dev.aurakai.auraframefx.domains.genesis.screens.CodeAssistScreen
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.gates.*

// Mapping for clarity - strictly Sovereign architecture

/**
 * 🌐 REGENESIS NAVIGATION HOST
 * The neural backbone of the Sovereign Architecture.
 *
 * Wired Layers:
 * - Level 1: Exodus Home (Monolith Carousel)
 * - Level 2: Pixel Workspaces (Detailed Monolith Views)
 * - Level 3: Domain Hubs (Aura, Kai, Genesis, Nexus)
 * - Level 4: Sovereign Tools (74+ specialized system screens)
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

        // --- LEVEL 1: THE HOME (THE 11 SOVEREIGN MONOLITHS) ---
        composable(NavDestination.HomeGateCarousel.route) {
            ExodusHUD(navController = navController)
        }

        // Legacy support/alias
        composable("exodus_home") { ExodusHUD(navController = navController) }

        // --- LEVEL 2: PIXEL WORKSPACES (Detailed Monolith Gallery) ---
        composable(
            "pixel_domain/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "01"
            val gateInfo = dev.aurakai.auraframefx.customization.SovereignRegistry.getGate(id)

            PixelWorkspaceScreen(
                title = gateInfo.title,
                imagePaths = listOf(gateInfo.pixelArtPath),
                onBack = { navController.popBackStack() }
            )
        }

        // --- LEVEL 3: THE SOVEREIGN HUBS (Control Centers) ---

        // AURA: THE DESIGN STUDIO HUB
        composable(NavDestination.AuraThemingHub.route) { AuraThemingHubScreen(navController = navController) }

        // KAI: THE SENTINEL FORTRESS HUB
        composable(NavDestination.RomToolsHub.route) { KaiSentinelHubScreen(navController = navController) }

        // GENESIS: THE ORACLE DRIVE HUB
        composable(NavDestination.OracleDriveHub.route) { OracleDriveHubScreen(navController = navController) }

        // NEXUS: THE AGENT COORDINATION HUB
        composable(NavDestination.AgentNexusGate.route) { AgentNexusHubScreen(navController = navController) }
        composable(NavDestination.AgentBridgeHub.route) { AgentBridgeHubScreen(onNavigateBack = { navController.popBackStack() }) }

        // --- LEVEL 4: THE CORE TOOLS (Functional Engines) ---

        // -- Aura & Design Studio --
        composable(NavDestination.ThemeEngine.route) { ThemeEngineScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.IconifyPicker.route) { IconifyPickerScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.ChromaCoreColors.route) { ChromaCoreColorsScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.AuraLab.route) { AuraLabScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.CollabCanvas.route) { CollabCanvasScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.NotchBar.route) { NotchBarScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.StatusBar.route) { StatusBarScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.QuickSettings.route) { QuickActionsScreen() }
        composable(NavDestination.InterfaceForge.route) { AppBuilderScreen(onNavigateBack = { navController.popBackStack() }) }

        // -- Kai Sentinel Security --
        composable(NavDestination.ROMFlasher.route) { ROMFlasherScreen() }
        composable(NavDestination.Bootloader.route) { SovereignBootloaderScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.ModuleManager.route) { SovereignModuleManagerScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.RecoveryTools.route) { RecoveryToolsScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.SecurityCenter.route) { SovereignShieldScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.LiveROMEditor.route) { LiveROMEditorScreen(onNavigateBack = { navController.popBackStack() }) }

        // -- Genesis Oracle & AI --
        composable(NavDestination.CodeAssist.route) { CodeAssistScreen(navController = navController) }
        composable(NavDestination.NeuralNetwork.route) { NeuralArchiveScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.OracleCloudStorage.route) { OracleCloudInfiniteStorageScreen(onNavigateBack = { navController.popBackStack() }) }

        // -- Agent Nexus Hub Tools --
        composable(NavDestination.Constellation.route) { ConstellationScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.AgentMonitoring.route) { AgentMonitoringScreen() }
        composable(NavDestination.SphereGrid.route) { SphereGridScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.TaskAssignment.route) { TaskAssignmentScreen() }
        composable(NavDestination.FusionMode.route) { FusionModeScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.MetaInstruct.route) { SovereignMetaInstructScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.Nemotron.route) { SovereignNemotronScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.Claude.route) { SovereignClaudeScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.Gemini.route) { SovereignGeminiScreen(onNavigateBack = { navController.popBackStack() }) }
    }
}
