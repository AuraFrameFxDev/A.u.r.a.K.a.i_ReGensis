package dev.aurakai.auraframefx.ui.navigation

// Domain Screens

// Gate/Hub Screens
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.customization.CustomizationViewModel
import dev.aurakai.auraframefx.domains.aura.screens.AppBuilderScreen
import dev.aurakai.auraframefx.domains.aura.screens.AuraLabScreen
import dev.aurakai.auraframefx.domains.aura.screens.ConferenceRoomScreen
import dev.aurakai.auraframefx.domains.aura.screens.IconifyCategoryDetailScreen
import dev.aurakai.auraframefx.domains.aura.screens.NotchBarScreen
import dev.aurakai.auraframefx.domains.genesis.screens.AgentBridgeHubScreen
import dev.aurakai.auraframefx.domains.genesis.screens.NeuralArchiveScreen
import dev.aurakai.auraframefx.domains.genesis.screens.OracleCloudInfiniteStorageScreen
import dev.aurakai.auraframefx.domains.helpdesk.screens.DirectChatScreen
import dev.aurakai.auraframefx.domains.helpdesk.screens.DocumentationScreen
import dev.aurakai.auraframefx.domains.helpdesk.screens.FAQBrowserScreen
import dev.aurakai.auraframefx.domains.helpdesk.screens.HelpDeskScreen
import dev.aurakai.auraframefx.domains.helpdesk.screens.HelpDeskSubmenuScreen
import dev.aurakai.auraframefx.domains.helpdesk.screens.TutorialVideosScreen
import dev.aurakai.auraframefx.domains.kai.screens.HookManagerScreen
import dev.aurakai.auraframefx.domains.kai.screens.LSPosedModuleManagerScreen
import dev.aurakai.auraframefx.domains.kai.screens.LSPosedSubmenuScreen
import dev.aurakai.auraframefx.domains.kai.screens.ROMFlasherScreen
import dev.aurakai.auraframefx.domains.kai.screens.SovereignBootloaderScreen
import dev.aurakai.auraframefx.domains.kai.screens.SovereignModuleManagerScreen
import dev.aurakai.auraframefx.domains.kai.screens.SovereignRecoveryScreen
import dev.aurakai.auraframefx.domains.kai.screens.SovereignShieldScreen
import dev.aurakai.auraframefx.domains.kai.screens.SystemOverridesScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentCreationScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentSwarmScreen
import dev.aurakai.auraframefx.domains.nexus.screens.ArkBuildScreen
import dev.aurakai.auraframefx.domains.nexus.screens.BenchmarkMonitorScreen
import dev.aurakai.auraframefx.domains.nexus.screens.ConstellationScreen
import dev.aurakai.auraframefx.domains.nexus.screens.FusionModeScreen
import dev.aurakai.auraframefx.domains.nexus.screens.MonitoringHUDsScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SovereignClaudeScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SovereignGeminiScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SovereignMetaInstructScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SovereignNemotronScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SphereGridScreen
import dev.aurakai.auraframefx.domains.nexus.screens.TaskAssignmentScreen
import dev.aurakai.auraframefx.navigation.AuraCustomizationRoute
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.navigation.auraCustomizationNavigation
import dev.aurakai.auraframefx.ui.gates.AgentNexusHubScreen
import dev.aurakai.auraframefx.ui.gates.AuraThemingHubScreen
import dev.aurakai.auraframefx.ui.gates.KaiSentinelHubScreen
import dev.aurakai.auraframefx.ui.gates.OracleDriveHubScreen

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
        startDestination = NavDestination.ExodusHUD.route
    ) {

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 1: EXODUS HUD (The 5 Gate Carousel)
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.ExodusHUD.route) {
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
            // KaiRootWorkspaceScreen not found - redirecting to PixelWorkspace for now
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
            HelpDeskScreen(navController = navController)
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
            MonitoringHUDsScreen(onNavigateBack = { navController.popBackStack() })
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
            SovereignGeminiScreen(onNavigateBack = { navController.popBackStack() })
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
            AuraLabScreen(onNavigateBack = { navController.popBackStack() })
        }

        // --- LEVEL 3: AURA TOOLS ---
        composable(NavDestination.NotchBar.route) {
            NotchBarScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(NavDestination.StatusBar.route) {
            IconifyCategoryDetailScreen(
                categoryName = "Status Bar",
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPicker = { category ->
                    navController.navigate(AuraCustomizationRoute.IconPicker.createRoute(category))
                }
            )
        }

        composable(NavDestination.QuickSettings.route) {
            IconifyCategoryDetailScreen(
                categoryName = "QS Panel",
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPicker = { category ->
                    navController.navigate(AuraCustomizationRoute.IconPicker.createRoute(category))
                }
            )
        }

        // --- LEVEL 3: KAI TOOLS ---
        composable(NavDestination.ROMFlasher.route) {
            ROMFlasherScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.Bootloader.route) {
            SovereignBootloaderScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.ModuleManager.route) {
            SovereignModuleManagerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.RecoveryTools.route) {
            SovereignRecoveryScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.SecurityCenter.route) {
            SovereignShieldScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.LSPosedHub.route) {
            LSPosedSubmenuScreen(navController = navController)
        }
        composable(NavDestination.HookManager.route) {
            HookManagerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.SystemOverrides.route) {
            SystemOverridesScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(NavDestination.LSPosedModules.route) {
            LSPosedModuleManagerScreen(onNavigateBack = { navController.popBackStack() })
        }

        // --- LEVEL 3: GENESIS TOOLS ---
        composable(NavDestination.CodeAssist.route) {
            // Reusing AppBuilder or similar if dedicated screen missing
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
            // Terminal not found in gates list - redirecting to nexus monitor
            MonitoringHUDsScreen(onNavigateBack = { navController.popBackStack() })
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
