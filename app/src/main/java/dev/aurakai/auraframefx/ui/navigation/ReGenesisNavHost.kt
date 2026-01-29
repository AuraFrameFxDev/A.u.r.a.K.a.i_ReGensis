package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.aurakai.auraframefx.aura.ui.*
import dev.aurakai.auraframefx.customization.CustomizationViewModel
import dev.aurakai.auraframefx.domains.aura.screens.*
import dev.aurakai.auraframefx.domains.genesis.screens.*
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.gates.*
import dev.aurakai.auraframefx.ui.screens.EvolutionTreeScreen
import dev.aurakai.auraframefx.ui.screens.SettingsScreen

/**
 * 🌐 REGENESIS NAVIGATION HOST 2.0
 * The definitive neural backbone of the Sovereign Architecture.
 *
 * This host wires Level 1 (Exodus), Level 2 (Pixel Gallery), Level 3 (Domain Hubs),
 * and Level 4 (74+ specialized Tool Engines).
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

    val startDest = NavDestination.HomeGateCarousel.route

    NavHost(navController = navController, startDestination = startDest) {

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 1: THE ROOT (EXODUS MONOLITHS)
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.HomeGateCarousel.route) {
            ExodusHUD(navController = navController)
        }

        composable("exodus_home") { ExodusHUD(navController = navController) }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 2: PIXEL WORKSPACES (GALLERY VIEW)
        // ═══════════════════════════════════════════════════════════════
        composable(
            "pixel_domain/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "01"
            val gateInfo = SovereignRegistry.getGate(id)

            PixelWorkspaceScreen(
                title = gateInfo.title,
                imagePaths = listOf(gateInfo.pixelArtPath),
                onBack = { navController.popBackStack() },
                onEnter = {
                    val targetHub = when (id) {
                        "01" -> NavDestination.OracleDriveHub.route
                        "03" -> NavDestination.AuraThemingHub.route
                        "04" -> NavDestination.AgentNexusGate.route
                        "05" -> NavDestination.RomToolsHub.route
                        "10" -> NavDestination.OracleDriveHub.route
                        "11" -> NavDestination.HelpDesk.route
                        else -> NavDestination.AuraThemingHub.route
                    }
                    navController.navigate(targetHub) {
                        popUpTo(NavDestination.HomeGateCarousel.route)
                    }
                }
            )
        }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 3: DOMAIN HUBS (THE MANAGEMENT FRAMEWORKS)
        // ═══════════════════════════════════════════════════════════════

        // AURA: REACTIVE DESIGN HUB
        composable(NavDestination.AuraThemingHub.route) { AuraThemingHubScreen(navController = navController) }
        composable(NavDestination.AuraLab.route) { AuraLabScreen(onNavigateBack = { navController.popBackStack() }) }

        // KAI: SENTINEL FORTRESS HUBS
        composable(NavDestination.RomToolsHub.route) { KaiSentinelHubScreen(navController = navController) }
        composable(NavDestination.LSPosedHub.route) { LSPosedSubmenuScreen(navController = navController) }
        composable(NavDestination.SystemToolsHub.route) { LogsViewerScreen(onNavigateBack = { navController.popBackStack() }) } // Logging/Journal Hub

        // GENESIS: ORACLE DRIVE HUBS
        composable(NavDestination.OracleDriveHub.route) { OracleDriveHubScreen(navController = navController) }
        composable(NavDestination.OracleCloudStorage.route) { OracleCloudInfiniteStorageScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.AgentBridgeHub.route) { AgentBridgeHubScreen(onNavigateBack = { navController.popBackStack() }) }

        // NEXUS: AGENT COORDINATION HUBS
        composable(NavDestination.AgentNexusGate.route) { AgentNexusHubScreen(navController = navController) }
        composable(NavDestination.ConstellationHub.route) { ConstellationScreen(navController = navController) }
        composable(NavDestination.MonitoringHub.route) { SovereignMonitoringScreen(onNavigateBack = { navController.popBackStack() }) }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 4: SOVEREIGN TOOLS (FUNCTIONAL ENGINES)
        // ═══════════════════════════════════════════════════════════════

        // --- AURA CORE TOOLSET ---
        composable(NavDestination.ThemeEngine.route) { ThemeEngineScreen() }
        composable(NavDestination.ChromaCoreColors.route) { ChromaCoreColorsScreen() }
        composable(NavDestination.IconifyPicker.route) { IconifyPickerScreen() }
        composable(NavDestination.NotchBar.route) { NotchBarScreen() }
        composable(NavDestination.StatusBar.route) { StatusBarScreen() }
        composable(NavDestination.QuickSettings.route) { QuickActionsScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.CollabCanvas.route) { CollabCanvasScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.ModuleCreation.route) { ModuleCreationScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.InterfaceForge.route) { AppBuilderScreen(onNavigateBack = { navController.popBackStack() }) }

        // --- KAI SENTINEL TOOLSET ---
        composable(NavDestination.Bootloader.route) { SovereignBootloaderScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.RootTools.route) { RootToolsTogglesScreen(navController = navController) }
        composable(NavDestination.ROMFlasher.route) { ROMFlasherScreen() }
        composable(NavDestination.LiveROMEditor.route) { LiveROMEditorScreen() }
        composable(NavDestination.RecoveryTools.route) { RecoveryToolsScreen() }
        composable(NavDestination.LSPosedModules.route) { LSPosedModuleManagerScreen() }
        composable(NavDestination.HookManager.route) { HookManagerScreen() }
        composable(NavDestination.SecurityCenter.route) { SovereignShieldScreen() }
        composable(NavDestination.ModuleManager.route) { SovereignModuleManagerScreen() }
        composable(NavDestination.SystemOverrides.route) { SystemOverridesScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.VPN.route) { VPNManagerScreen() }

        // --- GENESIS ORACLE TOOLSET ---
        composable(NavDestination.CodeAssist.route) { CodeAssistScreen() }
        composable(NavDestination.NeuralNetwork.route) { NeuralArchiveScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.Terminal.route) { TerminalScreen() }

        // --- NEXUS AGENT TOOLSET ---
        composable(NavDestination.GenesisConstellation.route) { GenesisConstellationScreen() }
        composable(NavDestination.ClaudeConstellation.route) { ClaudeConstellationScreen() }
        composable(NavDestination.KaiConstellation.route) { KaiConstellationScreen() }
        composable(NavDestination.GrokConstellation.route) { GrokConstellationScreen() }
        composable(NavDestination.CascadeConstellation.route) { CascadeConstellationScreen() }
        composable(NavDestination.AgentMonitoring.route) { SovereignMonitoringScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.SphereGrid.route) { SphereGridScreen(navController = navController) }
        composable(NavDestination.EvolutionTree.route) { EvolutionTreeScreen(navController = navController) }
        composable(NavDestination.FusionMode.route) { FusionModeScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.TaskAssignment.route) { TaskAssignmentScreen() }
        composable(NavDestination.ArkBuild.route) { ArkBuildScreen() }
        composable(NavDestination.MetaInstruct.route) { SovereignMetaInstructScreen() }
        composable(NavDestination.Nemotron.route) { SovereignNemotronScreen() }
        composable(NavDestination.Claude.route) { SovereignClaudeScreen() }
        composable(NavDestination.Gemini.route) { SovereignGeminiScreen() }
        composable(NavDestination.Constellation.route) { ConstellationScreen(navController = navController) }

        // ═══════════════════════════════════════════════════════════════
        // LEVEL 5: UTILITY & INFRASTRUCTURE
        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.HelpDesk.route) { HelpDeskScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.HelpDeskSubmenu.route) { HelpDeskSubmenuScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.DirectChat.route) { DirectChatScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.Documentation.route) { DocumentationScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.FAQBrowser.route) { FAQBrowserScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.TutorialVideos.route) { TutorialVideosScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.Settings.route) { SettingsScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.ConferenceRoom.route) { ConferenceRoomScreen(onNavigateBack = { navController.popBackStack() }) }
    }
}
