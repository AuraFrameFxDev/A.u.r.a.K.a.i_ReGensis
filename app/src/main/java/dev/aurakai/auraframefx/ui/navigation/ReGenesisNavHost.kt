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
import dev.aurakai.auraframefx.aura.ui.TerminalScreen
import dev.aurakai.auraframefx.aura.ui.VPNManagerScreen
import dev.aurakai.auraframefx.customization.CustomizationViewModel
import dev.aurakai.auraframefx.domains.aura.screens.AppBuilderScreen
import dev.aurakai.auraframefx.domains.aura.screens.ChromaCoreColorsScreen
import dev.aurakai.auraframefx.domains.aura.screens.ConferenceRoomScreen
import dev.aurakai.auraframefx.domains.aura.screens.DirectChatScreen
import dev.aurakai.auraframefx.domains.aura.screens.DocumentationScreen
import dev.aurakai.auraframefx.domains.aura.screens.FAQBrowserScreen
import dev.aurakai.auraframefx.domains.aura.screens.HelpDeskScreen
import dev.aurakai.auraframefx.domains.aura.screens.IconifyPickerScreen
import dev.aurakai.auraframefx.domains.aura.screens.QuickSettingsScreen
import dev.aurakai.auraframefx.domains.aura.screens.StatusBarScreen
import dev.aurakai.auraframefx.domains.aura.screens.ThemeEngineScreen
import dev.aurakai.auraframefx.domains.aura.screens.TutorialVideosScreen
import dev.aurakai.auraframefx.domains.genesis.screens.CodeAssistScreen
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.gates.AgentBridgeHubScreen
import dev.aurakai.auraframefx.ui.gates.AgentNexusHubScreen
import dev.aurakai.auraframefx.ui.gates.ArkBuildScreen
import dev.aurakai.auraframefx.ui.gates.AuraThemingHubScreen
import dev.aurakai.auraframefx.ui.gates.CascadeConstellationScreen
import dev.aurakai.auraframefx.ui.gates.ClaudeConstellationScreen
import dev.aurakai.auraframefx.ui.gates.CollabCanvasScreen
import dev.aurakai.auraframefx.ui.gates.ConstellationScreen
import dev.aurakai.auraframefx.ui.gates.FusionModeScreen
import dev.aurakai.auraframefx.ui.gates.GenesisConstellationScreen
import dev.aurakai.auraframefx.ui.gates.GrokConstellationScreen
import dev.aurakai.auraframefx.ui.gates.HelpDeskSubmenuScreen
import dev.aurakai.auraframefx.ui.gates.HookManagerScreen
import dev.aurakai.auraframefx.ui.gates.KaiConstellationScreen
import dev.aurakai.auraframefx.ui.gates.KaiSentinelHubScreen
import dev.aurakai.auraframefx.ui.gates.LSPosedModuleManagerScreen
import dev.aurakai.auraframefx.ui.gates.LSPosedSubmenuScreen
import dev.aurakai.auraframefx.ui.gates.LiveROMEditorScreen
import dev.aurakai.auraframefx.ui.gates.LogsViewerScreen
import dev.aurakai.auraframefx.ui.gates.ModuleCreationScreen
import dev.aurakai.auraframefx.ui.gates.NeuralArchiveScreen
import dev.aurakai.auraframefx.ui.gates.NotchBarScreen
import dev.aurakai.auraframefx.ui.gates.OracleCloudInfiniteStorageScreen
import dev.aurakai.auraframefx.ui.gates.OracleDriveHubScreen
import dev.aurakai.auraframefx.ui.gates.ROMFlasherScreen
import dev.aurakai.auraframefx.ui.gates.RecoveryToolsScreen
import dev.aurakai.auraframefx.ui.gates.RootToolsTogglesScreen
import dev.aurakai.auraframefx.ui.gates.SovereignBootloaderScreen
import dev.aurakai.auraframefx.ui.gates.SovereignClaudeScreen
import dev.aurakai.auraframefx.ui.gates.SovereignGeminiScreen
import dev.aurakai.auraframefx.ui.gates.SovereignMetaInstructScreen
import dev.aurakai.auraframefx.ui.gates.SovereignModuleManagerScreen
import dev.aurakai.auraframefx.ui.gates.SovereignMonitoringScreen
import dev.aurakai.auraframefx.ui.gates.SovereignNemotronScreen
import dev.aurakai.auraframefx.ui.gates.SovereignShieldScreen
import dev.aurakai.auraframefx.ui.gates.SphereGridScreen
import dev.aurakai.auraframefx.ui.gates.SystemOverridesScreen
import dev.aurakai.auraframefx.ui.gates.TaskAssignmentScreen
import dev.aurakai.auraframefx.ui.screens.EvolutionTreeScreen
import dev.aurakai.auraframefx.ui.screens.SettingsScreen
import dev.aurakai.auraframefx.ui.gates.AuraLabScreen as HubAuraLabScreen

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
        composable(NavDestination.AuraLab.route) { HubAuraLabScreen(onNavigateBack = { navController.popBackStack() }) }

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
        composable(NavDestination.ThemeEngine.route) { ThemeEngineScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.ChromaCoreColors.route) { ChromaCoreColorsScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.IconifyPicker.route) { IconifyPickerScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.NotchBar.route) { NotchBarScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.StatusBar.route) { StatusBarScreen() }
        composable(NavDestination.QuickSettings.route) { QuickSettingsScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.CollabCanvas.route) { CollabCanvasScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.ModuleCreation.route) { ModuleCreationScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.InterfaceForge.route) { AppBuilderScreen(onNavigateBack = { navController.popBackStack() }) }

        // --- KAI SENTINEL TOOLSET ---
        composable(NavDestination.Bootloader.route) { SovereignBootloaderScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.RootTools.route) { RootToolsTogglesScreen(navController = navController) }
        composable(NavDestination.ROMFlasher.route) { ROMFlasherScreen() }
        composable(NavDestination.LiveROMEditor.route) { LiveROMEditorScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.RecoveryTools.route) { RecoveryToolsScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.LSPosedModules.route) { LSPosedModuleManagerScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.HookManager.route) { HookManagerScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.SecurityCenter.route) { SovereignShieldScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.ModuleManager.route) { SovereignModuleManagerScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.SystemOverrides.route) { SystemOverridesScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.VPN.route) { VPNManagerScreen(navController = navController) }

        // --- GENESIS ORACLE TOOLSET ---
        composable(NavDestination.CodeAssist.route) { CodeAssistScreen(navController = navController) }
        composable(NavDestination.NeuralNetwork.route) { NeuralArchiveScreen(navController = navController) }
        composable(NavDestination.Terminal.route) { TerminalScreen(navController = navController) }

        // --- NEXUS AGENT TOOLSET ---
        composable(NavDestination.GenesisConstellation.route) { GenesisConstellationScreen(navController = navController) }
        composable(NavDestination.ClaudeConstellation.route) { ClaudeConstellationScreen(navController = navController) }
        composable(NavDestination.KaiConstellation.route) { KaiConstellationScreen(navController = navController) }
        composable(NavDestination.GrokConstellation.route) { GrokConstellationScreen(navController = navController) }
        composable(NavDestination.CascadeConstellation.route) { CascadeConstellationScreen(navController = navController) }
        composable(NavDestination.AgentMonitoring.route) { SovereignMonitoringScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.SphereGrid.route) { SphereGridScreen(navController = navController) }
        composable(NavDestination.EvolutionTree.route) {
            EvolutionTreeScreen(
                onNavigateToAgents = { navController.navigate(NavDestination.AgentMonitoring.route) },
                onNavigateToFusion = { navController.navigate(NavDestination.FusionMode.route) }
            )
        }
        composable(NavDestination.FusionMode.route) { FusionModeScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.TaskAssignment.route) { TaskAssignmentScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.ArkBuild.route) { ArkBuildScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.MetaInstruct.route) { SovereignMetaInstructScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.Nemotron.route) { SovereignNemotronScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.Claude.route) { SovereignClaudeScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.Gemini.route) { SovereignGeminiScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.Constellation.route) { ConstellationScreen(navController = navController) }

        // ═══════════════════════════════════════════════════════════════
        composable(NavDestination.HelpDesk.route) { HelpDeskScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.HelpDeskSubmenu.route) { HelpDeskSubmenuScreen(navController = navController) }
        composable(NavDestination.DirectChat.route) { DirectChatScreen(navController = navController) }
        composable(NavDestination.Documentation.route) { DocumentationScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.FAQBrowser.route) { FAQBrowserScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.TutorialVideos.route) { TutorialVideosScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.Settings.route) { SettingsScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(NavDestination.ConferenceRoom.route) { ConferenceRoomScreen(onNavigateBack = { navController.popBackStack() }) }
    }
}
