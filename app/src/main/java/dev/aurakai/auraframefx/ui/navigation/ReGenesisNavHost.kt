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

// Mapping for clarity based on user request names
@Composable fun KaiRootToolsScreen(navController: NavController) = KaiSentinelHubScreen(navController)
@Composable fun GenesisNexusHubScreen(navController: NavController) = AgentNexusHubScreen(navController)
@Composable fun KaiRomToolsScreen(onNavigateBack: () -> Unit) = BootloaderManagerScreen(onNavigateBack)
@Composable fun AuraChromaCoreScreen(onNavigateBack: () -> Unit) = ChromaCoreColorsScreen(onNavigateBack)
@Composable fun AuraCollabCanvasScreen(onNavigateBack: () -> Unit) = CollabCanvasScreen(onNavigateBack)

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

    // Determine start destination based on whether mode is set
    val startDest = if (customizationState.reGenesisMode == ReGenesisMode.NOT_SET) {
        "mode_selection"
    } else {
        "exodus_home"
    }

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
            ExodusProcessionScreen(
                onLevel2Access = { route -> navController.navigate(route) }
            )
        }

        // --- LEVEL 2 PORTALS (Surgical Access) ---
        composable("root_dashboard") { 
            KaiRootToolsScreen(navController) 
        }
        composable("agent_swarm") { 
            GenesisNexusHubScreen(navController) 
        }
        composable("persistence_layer") { 
            OracleDriveScreen(
                navController = navController,
                onNavigateBack = { navController.popBackStack() }
            ) 
        }
        composable("bootloader_ops") { 
            KaiRomToolsScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable("ui_synthesis") { 
            AuraChromaCoreScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable("figma_forge") { 
            AuraCollabCanvasScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable("aura_lab") {
            dev.aurakai.auraframefx.ui.gates.AuraLabScreen(onNavigateBack = { navController.popBackStack() })
        }

        // --- MANUAL CONTROL DOMAINS ---
        composable("aura_gate") {
            ChromaSphereManualScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("oracle_drive_hub") {
            OracleDriveManualScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("launcher_matrix") {
            LaunchMatrixManualScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("agent_bridge_hub") {
            AgentBridgeHubScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("security_center") {
            SovereignShieldScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("oracle_cloud_storage") {
            OracleCloudInfiniteStorageScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("module_manager") {
            SovereignModuleManagerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("recovery_tools") {
            SovereignRecoveryScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("neural_network") {
            SovereignNeuralArchiveScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("meta_instruct") {
            SovereignMetaInstructScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("nemotron") {
            SovereignNemotronScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("cascade_vision") {
            CascadeVisionScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("claude") {
            SovereignClaudeScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("gemini") {
            SovereignGeminiScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("monitoring_huds") {
            SovereignMonitoringScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("constellation") {
            dev.aurakai.auraframefx.domains.nexus.screens.AgentNeuralExplorerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("agent_creation") {
            dev.aurakai.auraframefx.domains.nexus.screens.AgentCreationScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("swarm_monitor") {
            dev.aurakai.auraframefx.domains.nexus.screens.AgentSwarmScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("benchmark_monitor") {
            dev.aurakai.auraframefx.domains.nexus.screens.BenchmarkMonitorScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("interface_forge") {
            dev.aurakai.auraframefx.domains.aura.screens.AppBuilderScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
