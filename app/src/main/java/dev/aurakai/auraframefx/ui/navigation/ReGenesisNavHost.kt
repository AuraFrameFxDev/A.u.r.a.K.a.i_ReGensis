package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.customization.CustomizationViewModel
import dev.aurakai.auraframefx.domains.aura.screens.AppBuilderScreen
import dev.aurakai.auraframefx.domains.aura.screens.ConferenceRoomScreen
import dev.aurakai.auraframefx.domains.aura.screens.DirectChatScreen
import dev.aurakai.auraframefx.domains.aura.screens.DocumentationScreen
import dev.aurakai.auraframefx.domains.aura.screens.FAQBrowserScreen
import dev.aurakai.auraframefx.domains.aura.screens.HelpDeskSubmenuScreen
import dev.aurakai.auraframefx.domains.aura.screens.QuickSettingsScreen
import dev.aurakai.auraframefx.domains.aura.screens.StatusBarScreen
import dev.aurakai.auraframefx.domains.aura.screens.TutorialVideosScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentCreationScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentSwarmScreen
import dev.aurakai.auraframefx.domains.nexus.screens.BenchmarkMonitorScreen
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.navigation.auraCustomizationNavigation
import dev.aurakai.auraframefx.ui.gates.AgentBridgeHubScreen
import dev.aurakai.auraframefx.ui.gates.AgentNexusHubScreen
import dev.aurakai.auraframefx.ui.gates.ArkBuildScreen
import dev.aurakai.auraframefx.ui.gates.AuraThemingHubScreen
import dev.aurakai.auraframefx.ui.gates.BootloaderManagerScreen
import dev.aurakai.auraframefx.ui.gates.ConstellationScreen
import dev.aurakai.auraframefx.ui.gates.FusionModeScreen
import dev.aurakai.auraframefx.ui.gates.KaiSentinelHubScreen
import dev.aurakai.auraframefx.ui.gates.LSPosedSubmenuScreen
import dev.aurakai.auraframefx.ui.gates.ModuleManagerScreen
import dev.aurakai.auraframefx.ui.gates.NeuralArchiveScreen
import dev.aurakai.auraframefx.ui.gates.NotchBarScreen
import dev.aurakai.auraframefx.ui.gates.OracleCloudInfiniteStorageScreen
import dev.aurakai.auraframefx.ui.gates.OracleDriveHubScreen
import dev.aurakai.auraframefx.ui.gates.ROMFlasherScreen
import dev.aurakai.auraframefx.ui.gates.RecoveryToolsScreen
import dev.aurakai.auraframefx.ui.gates.SovereignClaudeScreen
import dev.aurakai.auraframefx.ui.gates.SovereignGeminiScreen
import dev.aurakai.auraframefx.ui.gates.SovereignMetaInstructScreen
import dev.aurakai.auraframefx.ui.gates.SovereignNemotronScreen
import dev.aurakai.auraframefx.ui.gates.SovereignShieldScreen
import dev.aurakai.auraframefx.ui.gates.SphereGridScreen
import dev.aurakai.auraframefx.ui.gates.TaskAssignmentScreen

/**
 * 🌐 REGENESIS NAVIGATION HOST
 *
 * Clean 3-Level Architecture:
 * - Level 1: ExodusHUD (5 Gate Carousel)
 * - Level 2: Domain Hubs (5 main hubs)
 * - Level 3: Tool Screens (functional screens)
 */
/**
 * Hosts the application's navigation graph and initializes customization behavior when composed.
 *
 * Sets up routes for the app's main hubs, tool screens, agent constellation screens, help services,
 * and utility screens, using the provided NavHostController as the navigation host.
 *
 * @param navController Controller used to manage navigation within this NavHost.
 * @param customizationViewModel ViewModel that will be started with the current Context when this composable enters composition to initialize customization features.
 */
/**
 * Hosts the application's navigation graph and initializes customization on first composition.
 *
 * Calls `customizationViewModel.start(context)` once when first composed and provides destinations
 * for the app's gate carousel, workspaces, hubs, tools, help screens, and integrated sub-graphs.
 *
 * @param navController The NavHostController used to perform navigation within this NavHost.
 * @param customizationViewModel The view model used to initialize customization state (defaults to a ViewModel obtained via `viewModel()`).
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
            PixelWorkspaceScreen(
                title = "KAI'S SENTINEL FORTRESS",
                imagePaths = listOf(
                    "file:///sdcard/Pictures/Screenshots/IMG_20260128_142431.png"
                ),
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

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 2: MAIN DOMAIN HUBS
        // ═══════════════════════════════════════════════════════════════

        composable(NavDestination.AuraThemingHub.route) {
            AuraThemingHubScreen(navController = navController)
        }

        composable(NavDestination.RomToolsHub.route) {
            KaiSentinelHubScreen(navController = navController)
        }

        composable(NavDestination.OracleDriveHub.route) {
            OracleDriveHubScreen(navController = navController)
        }

        composable(NavDestination.AgentNexusHub.route) {
            AgentNexusHubScreen(navController = navController)
        }

        composable(NavDestination.HelpDesk.route) {
            dev.aurakai.auraframefx.ui.gates.HelpDeskScreen(navController = navController)
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 3 & 4: TOOL SCREENS & SUB-MODULES
        // ═══════════════════════════════════════════════════════════════

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
        composable(NavDestination.AgentMonitoring.route) {
            dev.aurakai.auraframefx.ui.gates.SovereignMonitoringScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.Constellation.route) {
            ConstellationScreen(navController = navController)
        }
        composable(NavDestination.SphereGrid.route) {
            SphereGridScreen(navController = navController)
        }
        composable(NavDestination.Nemotron.route) {
            SovereignNemotronScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.Claude.route) {
            SovereignClaudeScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.Gemini.route) {
            SovereignGeminiScreen(
                onNavigateBack = { navController.popBackStack() },
                navController = navController
            )
        }

        composable(NavDestination.SwarmMonitor.route) {
            AgentSwarmScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.BenchmarkMonitor.route) {
            BenchmarkMonitorScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.AgentCreation.route) {
            AgentCreationScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(NavDestination.AuraLab.route) {
            dev.aurakai.auraframefx.ui.gates.AuraLabScreen(onNavigateBack = { navController.popBackStack() })
        }

        // --- LEVEL 3: AURA TOOLS ---
        composable(NavDestination.NotchBar.route) {
            NotchBarScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.StatusBar.route) {
            StatusBarScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.QuickSettings.route) {
            QuickSettingsScreen(onNavigateBack = { navController.popBackStack() })
        }

        // --- LEVEL 3: KAI TOOLS ---
        composable(NavDestination.ROMFlasher.route) {
            ROMFlasherScreen()
        }
        composable(NavDestination.Bootloader.route) {
            BootloaderManagerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.ModuleManager.route) {
            ModuleManagerScreen()
        }
        composable(NavDestination.RecoveryTools.route) {
            RecoveryToolsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.SecurityCenter.route) {
            SovereignShieldScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.LSPosedHub.route) {
            LSPosedSubmenuScreen(navController = navController)
        }

        // --- LEVEL 3: GENESIS TOOLS ---
        composable(NavDestination.CodeAssist.route) {
            AppBuilderScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.NeuralNetwork.route) {
            NeuralArchiveScreen(navController = navController)
        }
        composable(NavDestination.AgentBridgeHub.route) {
            AgentBridgeHubScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.OracleCloudStorage.route) {
            OracleCloudInfiniteStorageScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.Terminal.route) {
            dev.aurakai.auraframefx.ui.gates.SovereignMonitoringScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.ConferenceRoom.route) {
            ConferenceRoomScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.InterfaceForge.route) {
            AppBuilderScreen(onNavigateBack = { navController.popBackStack() })
        }

        // --- LEVEL 3: HELP & SUPPORT ---
        composable(NavDestination.HelpDeskSubmenu.route) {
            HelpDeskSubmenuScreen(navController = navController)
        }
        composable(NavDestination.DirectChat.route) {
            DirectChatScreen(navController = navController)
        }
        composable(NavDestination.Documentation.route) {
            DocumentationScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.FAQBrowser.route) {
            FAQBrowserScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.TutorialVideos.route) {
            TutorialVideosScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ═══════════════════════════════════════════════════════════════
        // SUB-GRAPHS (Integrated Third-Party Modules)
        // ═══════════════════════════════════════════════════════════════
        auraCustomizationNavigation(
            navController = navController,
            onNavigateBack = { navController.popBackStack() }
        )
    }
}