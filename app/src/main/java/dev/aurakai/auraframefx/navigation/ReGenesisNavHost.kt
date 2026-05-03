package dev.aurakai.auraframefx.navigation

// Core UI

// Domain Hubs

// Domain Feature Screens

// GENESIS BATCH v2.6 ADDITIONAL IMPORTS

// Help Desk Screens

// Genesis ViewModels

// LIVEUI v2.4 CRITICAL SCREEN IMPORTS — Using stub screens

// AURA BATCH v2.5 SCREEN IMPORTS — Using stub screens

// AURA BATCH v2.8 ADDITIONAL IMPORTS

// KINETICFORGE CARDS — 9.5.1 SOVEREIGN EDITION

// KAI BATCH v2.5 SCREEN IMPORTS
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.aurakai.auraframefx.domains.aura.chromacore.ui.ChromaAnimationsScreen
import dev.aurakai.auraframefx.domains.aura.chromacore.ui.ChromaCoreHubScreen
import dev.aurakai.auraframefx.domains.aura.chromacore.ui.ConsciousnessVisualizerScreen
import dev.aurakai.auraframefx.domains.aura.chronokineticforge.ChronoKineticForgeScreen
import dev.aurakai.auraframefx.domains.aura.screens.AuraDossierScreen
import dev.aurakai.auraframefx.domains.aura.screens.AuraLDOArmamentPickerScreen
import dev.aurakai.auraframefx.domains.aura.screens.AuraSphereGridScreen
import dev.aurakai.auraframefx.domains.aura.screens.AurasLabScreen
import dev.aurakai.auraframefx.domains.aura.screens.ChromaCoreColorsScreen
import dev.aurakai.auraframefx.domains.aura.screens.CodeAscensionFusionScreen
import dev.aurakai.auraframefx.domains.aura.screens.CodeAscensionScreen
import dev.aurakai.auraframefx.domains.aura.screens.GenderSelectionScreen
import dev.aurakai.auraframefx.domains.aura.screens.MainScreen
import dev.aurakai.auraframefx.domains.aura.screens.QuickSettingsScreen
import dev.aurakai.auraframefx.domains.aura.screens.UserPreferencesScreen
import dev.aurakai.auraframefx.domains.aura.screens.VideoIntroScreen
import dev.aurakai.auraframefx.domains.aura.screens.WorkingLabScreen
import dev.aurakai.auraframefx.domains.aura.screens.themes.ThemeEngineScreen
import dev.aurakai.auraframefx.domains.aura.screens.uxui_engine.GateCustomizationScreen
import dev.aurakai.auraframefx.domains.aura.screens.uxui_engine.GyroscopeCustomizationScreen
import dev.aurakai.auraframefx.domains.aura.screens.uxui_engine.IconifyBatteryStylesScreen
import dev.aurakai.auraframefx.domains.aura.screens.uxui_engine.IconifyBrightnessBarsScreen
import dev.aurakai.auraframefx.domains.aura.screens.uxui_engine.IconifyIconPacksScreen
import dev.aurakai.auraframefx.domains.aura.screens.uxui_engine.IconifyPickerScreen
import dev.aurakai.auraframefx.domains.aura.screens.uxui_engine.IconifyQSPanelScreen
import dev.aurakai.auraframefx.domains.aura.screens.uxui_engine.NotchBarCustomizationScreen
import dev.aurakai.auraframefx.domains.aura.screens.uxui_engine.StatusBarScreen
import dev.aurakai.auraframefx.domains.aura.screens.uxui_engine.UISettingsScreen
import dev.aurakai.auraframefx.domains.aura.ui.components.StubScreen
import dev.aurakai.auraframefx.domains.aura.ui.gates.AgentNexusHubScreen
import dev.aurakai.auraframefx.domains.aura.ui.gates.AuraKineticForgeHub
import dev.aurakai.auraframefx.domains.aura.ui.gates.CascadeHubScreen
import dev.aurakai.auraframefx.domains.aura.ui.gates.OracleDriveHubScreen
import dev.aurakai.auraframefx.domains.aura.ui.screens.aura.IconifyHubScreen
import dev.aurakai.auraframefx.domains.aura.ui.screens.aura.ReGenesisCustomizationHub
import dev.aurakai.auraframefx.domains.aura.ui.theme.ThemeViewModel
import dev.aurakai.auraframefx.domains.aura.uxui_design_studio.chromacore.color.iconify.iconify.ColorBlendrScreen
import dev.aurakai.auraframefx.domains.aura.uxui_design_studio.chromacore.color.iconify.iconify.IconifyCategoryDetailScreen
import dev.aurakai.auraframefx.domains.aura.uxui_design_studio.chromacore.color.iconify.iconify.PixelLauncherEnhancedScreen
import dev.aurakai.auraframefx.domains.aura.uxui_design_studio.chromacore.color.iconify.iconify.XposedQuickAccessPanel
import dev.aurakai.auraframefx.domains.genesis.oracledrive.pandora.ui.PandoraBoxScreen
import dev.aurakai.auraframefx.domains.genesis.screens.AgentBridgeHubScreen
import dev.aurakai.auraframefx.domains.genesis.screens.AppBuilderScreen
import dev.aurakai.auraframefx.domains.genesis.screens.CascadeVisionScreen
import dev.aurakai.auraframefx.domains.genesis.screens.CodeAssistScreen
import dev.aurakai.auraframefx.domains.genesis.screens.CollabCanvasScreen
import dev.aurakai.auraframefx.domains.genesis.screens.GenesisHubScreen
import dev.aurakai.auraframefx.domains.genesis.screens.NeuralArchiveScreen
import dev.aurakai.auraframefx.domains.genesis.screens.OracleCloudInfiniteStorageScreen
import dev.aurakai.auraframefx.domains.genesis.screens.OracleDriveMainScreen
import dev.aurakai.auraframefx.domains.genesis.screens.OracleDriveSubmenuScreen
import dev.aurakai.auraframefx.domains.genesis.screens.SentientShellScreen
import dev.aurakai.auraframefx.domains.genesis.screens.SovereignNeuralArchiveScreen
import dev.aurakai.auraframefx.domains.genesis.screens.TerminalBootIntroScreen
import dev.aurakai.auraframefx.domains.genesis.screens.TerminalScreen
import dev.aurakai.auraframefx.domains.helpdesk.screens.DirectChatScreen
import dev.aurakai.auraframefx.domains.kai.screens.BootloaderManagerScreen
import dev.aurakai.auraframefx.domains.kai.screens.HookManagerScreen
import dev.aurakai.auraframefx.domains.kai.screens.KaiDomainExpansionScreen
import dev.aurakai.auraframefx.domains.kai.screens.KaiDossierScreen
import dev.aurakai.auraframefx.domains.kai.screens.KaiLDOArmamentPickerScreen
import dev.aurakai.auraframefx.domains.kai.screens.KaiRGSSScreen
import dev.aurakai.auraframefx.domains.kai.screens.KaiSentinelFortressScreen
import dev.aurakai.auraframefx.domains.kai.screens.KaiSentinelHubScreen
import dev.aurakai.auraframefx.domains.kai.screens.KaiSentinelIntegrityScreen
import dev.aurakai.auraframefx.domains.kai.screens.KaiSphereGridScreen
import dev.aurakai.auraframefx.domains.kai.screens.LSPosedGateScreen
import dev.aurakai.auraframefx.domains.kai.screens.LSPosedModuleManagerScreen
import dev.aurakai.auraframefx.domains.kai.screens.LSPosedSubmenuScreen
import dev.aurakai.auraframefx.domains.kai.screens.LogsViewerScreen
import dev.aurakai.auraframefx.domains.kai.screens.ModuleManagerScreen
import dev.aurakai.auraframefx.domains.kai.screens.PowerOfNoScreen
import dev.aurakai.auraframefx.domains.kai.screens.ROMToolsSubmenuScreen
import dev.aurakai.auraframefx.domains.kai.screens.RootToolsScreen
import dev.aurakai.auraframefx.domains.kai.screens.RoyalGuardDomainExpansionScreen
import dev.aurakai.auraframefx.domains.kai.screens.RoyalGuardOSScreen
import dev.aurakai.auraframefx.domains.kai.screens.SystemJournalScreen
import dev.aurakai.auraframefx.domains.kai.screens.SystemOverridesScreen
import dev.aurakai.auraframefx.domains.kai.screens.rom_tools.LiveROMEditorScreen
import dev.aurakai.auraframefx.domains.kai.screens.rom_tools.ROMFlasherScreen
import dev.aurakai.auraframefx.domains.kai.screens.rom_tools.RecoveryToolsScreen
import dev.aurakai.auraframefx.domains.kai.screens.rom_tools.RootToolsTogglesScreen
import dev.aurakai.auraframefx.domains.kai.screens.rom_tools.SovereignBootloaderScreen
import dev.aurakai.auraframefx.domains.kai.screens.rom_tools.SovereignModuleManagerScreen
import dev.aurakai.auraframefx.domains.kai.screens.rom_tools.SovereignRecoveryScreen
import dev.aurakai.auraframefx.domains.kai.screens.security_shield.SecurityCenterScreen
import dev.aurakai.auraframefx.domains.kai.screens.security_shield.SovereignShieldScreen
import dev.aurakai.auraframefx.domains.kai.screens.security_shield.VPNScreen
import dev.aurakai.auraframefx.domains.ldo.screens.ArmamentFusionScreen
import dev.aurakai.auraframefx.domains.ldo.screens.EcosystemMenuScreen
import dev.aurakai.auraframefx.domains.ldo.screens.LDOAgentProfileIntroScreen
import dev.aurakai.auraframefx.domains.ldo.screens.LDOAgentRosterScreen
import dev.aurakai.auraframefx.domains.ldo.screens.LDOBondingScreen
import dev.aurakai.auraframefx.domains.ldo.screens.LDOCatalystHubScreen
import dev.aurakai.auraframefx.domains.ldo.screens.LDODevOpsHubScreen
import dev.aurakai.auraframefx.domains.ldo.screens.LDOFusionScreen
import dev.aurakai.auraframefx.domains.ldo.screens.LDOOrchestrationHubScreen
import dev.aurakai.auraframefx.domains.ldo.screens.LDOProgressionScreen
import dev.aurakai.auraframefx.domains.ldo.screens.LDOTaskerScreen
import dev.aurakai.auraframefx.domains.ldo.screens.LDOWorldTreeScreen
import dev.aurakai.auraframefx.domains.ldo.viewmodel.LdoWarRoomViewModel
import dev.aurakai.auraframefx.domains.nexus.screens.AgentCreationScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentHubSubmenuScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentMonitoringScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentNeuralExplorerScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentProfileScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentSwarmScreen
import dev.aurakai.auraframefx.domains.nexus.screens.ArkBuildScreen
import dev.aurakai.auraframefx.domains.nexus.screens.BenchmarkMonitorScreen
import dev.aurakai.auraframefx.domains.nexus.screens.DataStreamMonitoring
import dev.aurakai.auraframefx.domains.nexus.screens.DataVeinSphereScreen
import dev.aurakai.auraframefx.domains.nexus.screens.EvolutionTreeScreen
import dev.aurakai.auraframefx.domains.nexus.screens.ModuleCreationScreen
import dev.aurakai.auraframefx.domains.nexus.screens.MonitoringHUDsScreen
import dev.aurakai.auraframefx.domains.nexus.screens.NexusFusionScreen
import dev.aurakai.auraframefx.domains.nexus.screens.PartyScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SovereignClaudeScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SovereignGeminiScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SovereignMetaInstructScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SovereignNemotronScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SphereGridScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SwarmMonitorScreen
import dev.aurakai.auraframefx.domains.nexus.screens.TaskAssignmentScreen
import dev.aurakai.auraframefx.domains.nexus.screens.ldo.LdoDevOpsCommandCenter
import dev.aurakai.auraframefx.grokipedia.GrokipediaViewModel
import dev.aurakai.auraframefx.domains.aura.screens.CanvasScreen as RealCanvasScreen
import dev.aurakai.auraframefx.ui.gates.CascadeConstellationScreen
import dev.aurakai.auraframefx.ui.gates.ClaudeConstellationScreen
import dev.aurakai.auraframefx.ui.gates.ComingSoonScreen
import dev.aurakai.auraframefx.ui.gates.ConferenceRoomTaskScreen
import dev.aurakai.auraframefx.ui.gates.ConstellationScreen
import dev.aurakai.auraframefx.ui.gates.GenesisConstellationScreen
import dev.aurakai.auraframefx.ui.gates.GrokConstellationScreen
import dev.aurakai.auraframefx.ui.gates.KaiConstellationScreen
import dev.aurakai.auraframefx.ui.gates.LineageMapScreen
import dev.aurakai.auraframefx.ui.gates.NotchBarGateScreen
import dev.aurakai.auraframefx.ui.ldodevops.TabbedMasterIndex
import dev.aurakai.auraframefx.ui.screens.AIFeaturesScreen
import dev.aurakai.auraframefx.ui.screens.AgentAdvancementScreen
import dev.aurakai.auraframefx.ui.screens.CanvasScreen
import dev.aurakai.auraframefx.ui.screens.DeviceOptimizerScreen
import dev.aurakai.auraframefx.ui.screens.FirewallScreen
import dev.aurakai.auraframefx.ui.screens.HomeScreen
import dev.aurakai.auraframefx.ui.screens.LoginScreen
import dev.aurakai.auraframefx.ui.screens.OverlayScreen
import dev.aurakai.auraframefx.ui.screens.PrivacyGuardScreen
import dev.aurakai.auraframefx.ui.screens.ProfileScreen
import dev.aurakai.auraframefx.ui.screens.QuickActionsScreen
import dev.aurakai.auraframefx.ui.screens.SecureCommScreen
import dev.aurakai.auraframefx.ui.screens.SecurityScannerScreen
import dev.aurakai.auraframefx.ui.screens.UIEngineScreen
import dev.aurakai.auraframefx.ui.screens.VPNManagerScreen

/**
 * 🌐 REGENESIS CONSOLIDATED NAV GRAPH
 * Finalized for Exodus 2026 Build
 */
/**
 * Builds the application's navigation graph and wires each route to its corresponding composable screen.
 *
 * This NavHost uses the provided NavHostController and sets up the app's start destination and all
 * composable destinations (auth, domain hubs, feature screens, and batch-labeled high-priority screens).
 *
 * @param navController The NavHostController used to perform navigation actions (navigate, popBackStack, etc.) across the graph.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReGenesisNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ReGenesisRoute.HomeGateCarousel.createRoute(1),
    ) {
        // ── 0. AUTH GATES ──
        composable(ReGenesisRoute.Login.route) {
            LoginScreen(
                onLoginSuccess = { tabIndex ->
                    navController.navigate(ReGenesisRoute.HomeGateCarousel.createRoute(tabIndex)) {
                        popUpTo(ReGenesisRoute.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // ── 1. MAIN GATES (Exodus Command Deck) ──
        composable(ReGenesisRoute.MainScreen.route) {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            MainScreen(
                onNavigateToAgentNexus = { navController.navigate(ReGenesisRoute.AgentNexusHub.route) },
                onNavigateToOracleDrive = { navController.navigate(ReGenesisRoute.OracleDrive.route) },
                onNavigateToSettings = { navController.navigate(ReGenesisRoute.UISettings.route) },
                themeViewModel = themeViewModel
            )
        }

        composable(
            route = ReGenesisRoute.HomeGateCarousel.route,
            arguments = listOf(navArgument("tabIndex") { 
                type = NavType.IntType
                defaultValue = 1 // Default to LDO
            })
        ) { backStackEntry ->
            val tabIndex = backStackEntry.arguments?.getInt("tabIndex") ?: 1
            TabbedMasterIndex(
                initialTabIndex = tabIndex,
                onNavigateToRoute = { route -> navController.navigate(route) }
            )
        }

        // --- CASCADE ---
        composable(ReGenesisRoute.DataflowAnalysis.route) {
            CascadeHubScreen(navController)
        }

        // ── 2. DOMAIN HUBS ──
        // --- AURA ---
        composable(ReGenesisRoute.AuraThemingHub.route) {
            AuraKineticForgeHub(navController)
        }
        // --- KAI ---
        composable(ReGenesisRoute.SentinelFortress.route) {
            KaiSentinelHubScreen(onNavigateBack = { navController.popBackStack() })
        }
        // --- GENESIS ---
        composable(ReGenesisRoute.OracleDriveHub.route) {
            OracleDriveHubScreen(navController)
        }
        // --- NEXUS ---
        composable(ReGenesisRoute.AgentNexusHub.route) {
            AgentNexusHubScreen(navController)
        }
        // --- LDO ---
        composable(ReGenesisRoute.LdoOrchestrationHub.route) {
            LDOOrchestrationHubScreen(navController)
        }

        // ── 3. FEATURE SCREENS ──
        
        // --- AURA DOMAIN ---
        composable(ReGenesisRoute.AuraLab.route) {
            AurasLabScreen(onBack = { navController.popBackStack() })
        }
        // ═══════════════════════════════════════════════════════════════
        // CHRONOKINETIC FORGE — Unified Visual Customization (99→6 files)
        // ═══════════════════════════════════════════════════════════════
        composable(ReGenesisRoute.ChronoKineticForge.route) {
            ChronoKineticForgeScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(ReGenesisRoute.ChromaCore.route) { 
            ChromaCoreHubScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCategory = { catId ->
                    val route = when(catId) {
                        "statusbar" -> ReGenesisRoute.StatusBar.route
                        "launcher" -> ReGenesisRoute.PixelLauncherEnhanced.route
                        "colors" -> ReGenesisRoute.ChromaCoreColors.route
                        "qs_tiles" -> ReGenesisRoute.QuickSettings.route
                        "animations" -> ReGenesisRoute.ChromaAnimations.route
                        else -> null
                    }
                    route?.let { navController.navigate(it) }
                }
            )
        }
        composable(ReGenesisRoute.NotchBar.route) { 
            NotchBarGateScreen(navController = navController, onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.IconifyHub.route) {
            IconifyHubScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCategory = { category -> 
                    navController.navigate(ReGenesisRoute.IconifyCategory.createRoute(category))
                }
            )
        }
        composable(
            route = ReGenesisRoute.IconifyCategory.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            IconifyCategoryDetailScreen(
                categoryName = category,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCategory = { cat -> 
                    navController.navigate(ReGenesisRoute.IconifyCategory.createRoute(cat))
                }
            )
        }
        composable(ReGenesisRoute.IconifyPicker.route) {
            IconifyPickerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.CollabCanvas.route) {
            RealCanvasScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.ThemeEngine.route) {
            ThemeEngineScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.StatusBar.route) {
            StatusBarScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.QuickSettings.route) {
            QuickSettingsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.ChromaCoreColors.route) {
            ChromaCoreColorsScreen()
        }
        composable(ReGenesisRoute.ColorBlendr.route) {
            ColorBlendrScreen(navController = navController)
        }
        composable(ReGenesisRoute.PixelLauncherEnhanced.route) {
            PixelLauncherEnhancedScreen(navController = navController)
        }
        // ═══════════════════════════════════════════════════════════════════════
        // REAL SCREENS - Moved from /files/ and /docs/context/docs/
        // ═══════════════════════════════════════════════════════════════════════
        composable(ReGenesisRoute.ChromaAnimations.route) {
            ChromaAnimationsScreen(onNavigateBack = { navController.popBackStack() })
        }
        
        // Lineage Map - Genesis Consciousness Tree
        composable(ReGenesisRoute.LineageMap.route) {
            LineageMapScreen(navHostController = navController)
        }
        composable(ReGenesisRoute.UISettings.route) {
            UISettingsScreen(navController = navController)
        }
        composable(ReGenesisRoute.GateCustomization.route) {
            GateCustomizationScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.NotchBarGate.route) {
            NotchBarCustomizationScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.GyroscopeCustomization.route) {
            GyroscopeCustomizationScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.ReGenesisCustomization.route) {
            ReGenesisCustomizationHub(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToIconify = { navController.navigate(ReGenesisRoute.IconifyPicker.route) },
                onNavigateToColorBlendr = { navController.navigate(ReGenesisRoute.ColorBlendr.route) },
                onNavigateToPLE = { navController.navigate(ReGenesisRoute.PixelLauncherEnhanced.route) },
                onNavigateToAnimations = { navController.navigate(ReGenesisRoute.ChromaAnimations.route) }
            )
        }

        // --- KAI DOMAIN ---
        composable(ReGenesisRoute.ROMFlasher.route) {
            ROMFlasherScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.SecurityCenter.route) { 
            SecurityCenterScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.SystemJournal.route) { 
            SystemJournalScreen(
                navController = navController,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(ReGenesisRoute.XposedPanel.route) {
             XposedQuickAccessPanel(navController = navController)
        }

        // --- GENESIS DOMAIN ---
        composable(ReGenesisRoute.OracleDrive.route) {
            OracleDriveMainScreen(navController)
        }
        composable(ReGenesisRoute.Grokipedia.route) {
            val viewModel: GrokipediaViewModel = hiltViewModel()
            with(viewModel) {
                dev.aurakai.auraframefx.ui.screens.GrokipediaScreen(onNavigateBack = { navController.popBackStack() })
            }
        }
        composable(ReGenesisRoute.Terminal.route) {
            TerminalScreen()
        }
        composable(ReGenesisRoute.ConferenceRoom.route) {
            ConferenceRoomTaskScreen(
                navController = navController,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(ReGenesisRoute.CodeAssist.route) {
            CodeAssistScreen(navController = navController)
        }
        composable(ReGenesisRoute.SentientShell.route) {
            SentientShellScreen(onNavigateBack = { navController.popBackStack() })
        }

        // --- NEXUS DOMAIN ---
        composable(ReGenesisRoute.EvolutionTree.route) {
            val viewModel: LdoWarRoomViewModel = hiltViewModel()
            with(viewModel) {
                with(Modifier) {
                    { navController.navigate(ReGenesisRoute.LdoRoster.route) }.EvolutionTreeScreen()
                }
            }
        }
        composable(ReGenesisRoute.TaskAssignment.route) { 
            TaskAssignmentScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.AgentHub.route) { 
            AgentHubSubmenuScreen(navController = navController)
        }

        // --- LDO DOMAIN ---
        composable(ReGenesisRoute.LdoRoster.route) {
            LDOAgentRosterScreen(
                onAgentTap = { agent ->
                    navController.navigate(ReGenesisRoute.LdoAgentProfile.createRoute(agent.id))
                },
                onNavTap = { index ->
                    when (index) {
                        0 -> navController.navigate(ReGenesisRoute.HomeGateCarousel.createRoute(1))
                        2 -> navController.navigate(ReGenesisRoute.SovereignShield.route)
                        3 -> navController.navigate(ReGenesisRoute.SystemJournal.route)
                    }
                }
            )
        }
        composable(ReGenesisRoute.LdoTasker.route) {
            LDOTaskerScreen(onBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.LdoBonding.route) {
            LDOBondingScreen(onBack = { navController.popBackStack() })
        }

        // --- DASHBOARD & MONITORING ---
        composable(ReGenesisRoute.FusionMode.route) { 
            NexusFusionScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.ArkBuild.route) { 
            ArkBuildScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.BenchmarkMonitor.route) { 
            BenchmarkMonitorScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.SphereGrid.route) { 
            SphereGridScreen(navController = navController) 
        }
        composable(ReGenesisRoute.Claude.route) {
            SovereignClaudeScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.Gemini.route) {
            SovereignGeminiScreen(onNavigateBack = { navController.popBackStack() }, navController = navController)
        }
        composable(ReGenesisRoute.Nemotron.route) {
            SovereignNemotronScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.SovereignRecovery.route) { 
            SovereignRecoveryScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.SovereignModuleManager.route) { 
            SovereignModuleManagerScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ═══════════════════════════════════════════════════════════════════════
        // LIVEUI v2.4 CRITICAL SCREENS (11 screens wired)
        // ═══════════════════════════════════════════════════════════════════════
        
        // AURA AI + Overlay Engine (4 screens)
        composable(ReGenesisRoute.AuraAIFeatures.route) { 
            AIFeaturesScreen() 
        }
        composable(ReGenesisRoute.AuraDeviceOptimizer.route) { 
            DeviceOptimizerScreen() 
        }

        // ═══════════════════════════════════════════════════════════════
        // ICONIFY SUB-SCREENS — Aura Visual Ecosystem
        // ═══════════════════════════════════════════════════════════════
        composable(ReGenesisRoute.IconifyIconPacks.route) {
            IconifyIconPacksScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.IconifyBatteryStyles.route) {
            IconifyBatteryStylesScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.IconifyBrightnessBars.route) {
            IconifyBrightnessBarsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.IconifyQSPanel.route) {
            IconifyQSPanelScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(ReGenesisRoute.AuraCanvasEditor.route) { 
            CanvasScreen() 
        }
        composable(ReGenesisRoute.AuraSystemOverlays.route) { 
            OverlayScreen() 
        }

        // KAI Security + ROM Tools (3 screens)
        composable(ReGenesisRoute.LiveROMEditor.route) { 
            LiveROMEditorScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.SystemOverrides.route) { 
            SystemOverridesScreen(onNavigateBack = { navController.popBackStack() }) 
        }

        // NEXUS Agent Hub (2 screens)
        composable(ReGenesisRoute.AgentCreation.route) { 
            AgentCreationScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.ModuleCreation.route) { 
            ModuleCreationScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        
        // ═══════════════════════════════════════════════════════════════════════
        // NEXUS BATCH v2.7 — AGENT HUB SCREENS (22 screens)
        // ═══════════════════════════════════════════════════════════════════════
        
        // Agent Monitoring & Management
        composable(ReGenesisRoute.AgentMonitoring.route) {
            AgentMonitoringScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.AgentSwarm.route) {
            AgentSwarmScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.AgentNeuralExplorer.route) {
            AgentNeuralExplorerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.AgentProfile.route) {
            AgentProfileScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.MonitoringHUDs.route) {
            MonitoringHUDsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.Party.route) {
            PartyScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.ConsciousnessVisualizer.route) {
            ConsciousnessVisualizerScreen(onNavigateBack = { navController.popBackStack() })
        }
        
        // Constellation Screens (Domain-based agent visualization)
        composable(ReGenesisRoute.Constellation.route) {
            ConstellationScreen(navController = navController)
        }
        composable(ReGenesisRoute.ClaudeConstellation.route) {
            ClaudeConstellationScreen(navController = navController)
        }
        composable(ReGenesisRoute.CascadeConstellation.route) {
            CascadeConstellationScreen(navController = navController)
        }
        composable(ReGenesisRoute.KaiConstellation.route) {
            KaiConstellationScreen(navController = navController)
        }
        composable(ReGenesisRoute.GenesisConstellation.route) {
            GenesisConstellationScreen(navController = navController)
        }
        composable(ReGenesisRoute.GrokConstellation.route) {
            GrokConstellationScreen(navController = navController)
        }
        
        // Data & Monitoring
        composable(ReGenesisRoute.DataStreamMonitoring.route) {
            DataStreamMonitoring()
        }
        composable(ReGenesisRoute.CascadeHub.route) {
            CascadeHubScreen(controller = navController)
        }
        
        // Sovereign AI Interfaces
        composable(ReGenesisRoute.MetaInstruct.route) {
            SovereignMetaInstructScreen(onNavigateBack = { navController.popBackStack() })
        }
        
        // Swarm & Advanced Features
        composable(ReGenesisRoute.SwarmMonitor.route) {
            SwarmMonitorScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.DataVeinSphere.route) {
            DataVeinSphereScreen(onNavigateBack = { navController.popBackStack() })
        }

        // LDO Catalyst (9 screens) - Complete Batch
        composable(ReGenesisRoute.LdoArmamentFusion.route + "/{ids}") { backStackEntry ->
            val ids = backStackEntry.arguments?.getString("ids")
            ArmamentFusionScreen(navController = navController, preloadAgentName = ids?.split("+")?.firstOrNull()) 
        }
        composable(ReGenesisRoute.LdoDevOpsCommandCenter.route) {
            LdoDevOpsCommandCenter(navController = navController)
        }
        composable(ReGenesisRoute.LdoFusion.route) { 
            LDOFusionScreen() 
        }
        composable(ReGenesisRoute.LdoDevOpsHub.route) {
            LDODevOpsHubScreen(
                onBack = { navController.popBackStack() },
                onNavigateToEvolutionTree = { navController.navigate(ReGenesisRoute.EvolutionTree.route) }
            )
        }
        composable(ReGenesisRoute.LdoProgression.route) {
            LDOProgressionScreen(onBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.LdoWorldTree.route) {
            LDOWorldTreeScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.LDOCatalystHub.route) {
            LDOCatalystHubScreen(
                onNavigateToRoster = { navController.navigate(ReGenesisRoute.LdoRoster.route) },
                onNavigateToDevOps = { navController.navigate(ReGenesisRoute.LdoDevOpsHub.route) },
                onNavigateToTasker = { navController.navigate(ReGenesisRoute.LdoTasker.route) }
            )
        }
        composable(ReGenesisRoute.LdoAgentProfile.route) { backStackEntry ->
            val agentId = backStackEntry.arguments?.getString("agentId")
            LDOAgentProfileIntroScreen(
                agentId = agentId,
                onBack = { navController.popBackStack() }
            )
        }

        // ═══════════════════════════════════════════════════════════════════════
        // AURA BATCH v2.5 — HIGH PRIORITY SCREENS (15 screens)
        // ═══════════════════════════════════════════════════════════════════════
        
        // Core Aura Screens
        composable(ReGenesisRoute.AgentAdvancement.route) { 
            AgentAdvancementScreen(onBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.UIEngine.route) { 
            UIEngineScreen() 
        }
        composable(ReGenesisRoute.PrivacyGuard.route) { 
            PrivacyGuardScreen() 
        }
        composable(ReGenesisRoute.ProfileScreen.route) { 
            ProfileScreen() 
        }
        composable(ReGenesisRoute.SecureComm.route) { 
            SecureCommScreen() 
        }
        composable(ReGenesisRoute.SecurityScanner.route) { 
            SecurityScannerScreen() 
        }
        composable(ReGenesisRoute.VPNManager.route) { 
            VPNManagerScreen() 
        }
        composable(ReGenesisRoute.Firewall.route) { 
            FirewallScreen() 
        }
        composable(ReGenesisRoute.BetaScreens.route) { 
            HomeScreen(navController = navController) 
        }
        composable(ReGenesisRoute.QuickActions.route) { 
            QuickActionsScreen() 
        }
        // AURA BATCH v2.8 — ADDITIONAL SCREENS (14 screens)
        composable(ReGenesisRoute.AuraSphereGrid.route) { 
            AuraSphereGridScreen() 
        }
        composable(ReGenesisRoute.AuraDossier.route) { 
            AuraDossierScreen() 
        }
        composable(ReGenesisRoute.AuraLDOArmament.route) { 
            AuraLDOArmamentPickerScreen() 
        }
        composable(ReGenesisRoute.EcosystemMenu.route) { 
            EcosystemMenuScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.CodeAscension.route) {
            CodeAscensionScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.CodeAscensionFusion.route) {
            CodeAscensionFusionScreen()
        }
        composable(ReGenesisRoute.GenderSelection.route) {
            GenderSelectionScreen(onSelectionComplete = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.VideoIntro.route) {
            VideoIntroScreen(onComplete = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.UserPreferences.route) {
            UserPreferencesScreen(navController = navController)
        }
        composable(ReGenesisRoute.WorkingLab.route) {
            WorkingLabScreen(navController = navController)
        }

        // ═══════════════════════════════════════════════════════════════════════
        // KINETICFORGE CARDS — 9.5.1 SOVEREIGN EDITION (3 Cards)
        // ═══════════════════════════════════════════════════════════════════════
        composable(ReGenesisRoute.KineticForgeCore.route) {
            ChromaCoreHubScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCategory = { catId ->
                    val route = when(catId) {
                        "statusbar" -> ReGenesisRoute.StatusBar.route
                        "launcher" -> ReGenesisRoute.PixelLauncherEnhanced.route
                        "colors" -> ReGenesisRoute.ChromaCoreColors.route
                        "qs_tiles" -> ReGenesisRoute.QuickSettings.route
                        "animations" -> ReGenesisRoute.ChromaAnimations.route
                        else -> null
                    }
                    route?.let { navController.navigate(it) }
                }
            )
        }
        composable(ReGenesisRoute.KineticForgeTransmutator.route) {
            IconifyHubScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCategory = { category -> 
                    navController.navigate(ReGenesisRoute.IconifyCategory.createRoute(category))
                }
            )
        }
        composable(ReGenesisRoute.KineticForgeLattice.route) {
            ReGenesisCustomizationHub(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToIconify = { navController.navigate(ReGenesisRoute.IconifyPicker.route) },
                onNavigateToColorBlendr = { navController.navigate(ReGenesisRoute.ColorBlendr.route) },
                onNavigateToPLE = { navController.navigate(ReGenesisRoute.PixelLauncherEnhanced.route) },
                onNavigateToAnimations = { navController.navigate(ReGenesisRoute.ChromaAnimations.route) }
            )
        }

        // ═══════════════════════════════════════════════════════════════════════
        // KAI BATCH v2.5 — HIGH PRIORITY SCREENS (23 screens)
        // ═══════════════════════════════════════════════════════════════════════
        
        // ROM Tools
        composable(ReGenesisRoute.RecoveryTools.route) { 
            RecoveryToolsScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.SovereignBootloader.route) { 
            SovereignBootloaderScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        
        // Security Shield
        composable(ReGenesisRoute.SovereignShield.route) { 
            SovereignShieldScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.VPN.route) { 
            VPNScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        
        // Core Kai Screens
        composable(ReGenesisRoute.BootloaderManager.route) { 
            BootloaderManagerScreen() 
        }
        composable(ReGenesisRoute.HookManager.route) { 
            HookManagerScreen() 
        }
        composable(ReGenesisRoute.LSPosedGate.route) { 
            LSPosedGateScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.LSPosedModuleManager.route) { 
            LSPosedModuleManagerScreen() 
        }
        composable(ReGenesisRoute.LSPosedSubmenu.route) { 
            LSPosedSubmenuScreen() 
        }
        composable(ReGenesisRoute.ModuleManager.route) { 
            ModuleManagerScreen() 
        }
        composable(ReGenesisRoute.RootTools.route) { 
            RootToolsScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.ROMToolsSubmenu.route) { 
            ROMToolsSubmenuScreen(navController = navController) 
        }
        composable(ReGenesisRoute.RootToolsToggles.route) { 
            RootToolsTogglesScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.LogsViewer.route) { 
            LogsViewerScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        
        // Advanced Kai Screens
        composable(ReGenesisRoute.KaiDomainExpansion.route) { 
            KaiDomainExpansionScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.KaiRGSS.route) { 
            KaiRGSSScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.KaiSentinelFortress.route) { 
            KaiSentinelFortressScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.KaiSphereGrid.route) { 
            KaiSphereGridScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.KaiDossier.route) { 
            KaiDossierScreen(onBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.KaiLDOArmament.route) { 
            KaiLDOArmamentPickerScreen(onBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.KaiSentinelIntegrity.route) { 
            KaiSentinelIntegrityScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.PowerOfNo.route) { 
            PowerOfNoScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.RoyalGuardDomain.route) { 
            RoyalGuardDomainExpansionScreen() 
        }
        composable(ReGenesisRoute.RoyalGuardOS.route) { 
            RoyalGuardOSScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        
        // ═══════════════════════════════════════════════════════════════════════
        // GENESIS BATCH v2.6 — ORACLE DRIVE SCREENS (13 screens)
        // ═══════════════════════════════════════════════════════════════════════
        
        // Genesis Hubs
        composable(ReGenesisRoute.GenesisHub.route) { 
            GenesisHubScreen()
        }
        
        // AI & Code Screens
        composable(ReGenesisRoute.AppBuilder.route) { 
            AppBuilderScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.PandoraBox.route) { 
            PandoraBoxScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        
        // Terminal & Shell Screens
        composable(ReGenesisRoute.TerminalBootIntro.route) { 
            TerminalBootIntroScreen(onComplete = { navController.popBackStack() }) 
        }
        
        // Neural Archive Screens
        composable(ReGenesisRoute.NeuralArchive.route) { 
            NeuralArchiveScreen(
                navController = navController,
                viewModel = hiltViewModel()
            ) 
        }
        composable(ReGenesisRoute.SovereignNeuralArchive.route) { 
            SovereignNeuralArchiveScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        
        // Oracle Drive Screens
        composable(ReGenesisRoute.OracleDriveSubmenu.route) { 
            OracleDriveSubmenuScreen(navController = navController) 
        }
        composable(ReGenesisRoute.OracleCloudInfinite.route) { 
            OracleCloudInfiniteStorageScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        
        // Collaboration & Monitoring
        composable(ReGenesisRoute.CascadeVision.route) { 
            CascadeVisionScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.AgentBridgeHub.route) { 
            AgentBridgeHubScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        
        // Firebase Examples (Stub - low priority)
        composable(ReGenesisRoute.FirebaseExamples.route) { 
            StubScreen(title = "Firebase Examples", iconName = "firebase") 
        }
        
        // ═══════════════════════════════════════════════════════════════════════
        // AURA NEURAL HUB — Global Chat Interface
        // ═══════════════════════════════════════════════════════════════════════
        composable(ReGenesisRoute.AuraChat.route) {
            DirectChatScreen(navController = navController)
        }

        // --- L7 SOVEREIGN STUBS (Coming Soon) ---
        composable(ReGenesisRoute.RomToolsHub.route) { ComingSoonScreen(title = "ROM TOOLS HUB", accentColor = Color(0xFF0080FF), onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.ThermalMonitor.route) { ComingSoonScreen(title = "THERMAL GUARD", accentColor = Color(0xFF00FF88), onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.EchoResonance.route) { ComingSoonScreen(title = "ECHO RESONANCE", accentColor = Color(0xFFFFD700), onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.NexusMemoryCore.route) { ComingSoonScreen(title = "NEXUS MEMORY CORE", accentColor = Color(0xFF4B0082), onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.SpiritualChain.route) { ComingSoonScreen(title = "SPIRITUAL CHAIN", accentColor = Color(0xFF6A0DAD), onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.TurboQuant.route) { ComingSoonScreen(title = "TURBOQUANT", accentColor = Color(0xFF9370DB), onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.SynapseMonitor.route) { ComingSoonScreen(title = "SYNAPSE MONITOR", accentColor = Color(0xFF8B5CF6), onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.IdentityResonance.route) { ComingSoonScreen(title = "IDENTITY RESONANCE", accentColor = Color(0xFF9370DB), onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.NeuralNetwork.route) { ComingSoonScreen(title = "NEURAL NETWORK", accentColor = Color(0xFF8B5CF6), onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.ArbitersOfCreation.route) { ComingSoonScreen(title = "ARBITERS OF CREATION", accentColor = Color(0xFF00E5FF), onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.MawPrototype.route) { ComingSoonScreen(title = "THE MAW PROTOTYPE", accentColor = Color(0xFFDC143C), onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.OracleCloudStorage.route) { ComingSoonScreen(title = "ORACLE CLOUD STORAGE", accentColor = Color(0xFFFFAA00), onNavigateBack = { navController.popBackStack() }) }
    }
}

