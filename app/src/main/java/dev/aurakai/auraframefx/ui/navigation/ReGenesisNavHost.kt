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
            SovereignProcessionScreen(
                onShatterTransition = { route ->
                    val workspaceRoute = when (route) {
                        "kai_gate", "oracle_drive_hub", "secure_node" -> "workspace_kai"
                        "aura_lab", "figma_bridge" -> "workspace_aura"
                        else -> "workspace_genesis"
                    }
                    navController.navigate(workspaceRoute)
                }
            )
        }

        // --- LEVEL 2 PIXEL WORKSPACES ---
        composable("workspace_kai") {
            PixelWorkspaceScreen(
                title = "KAI'S ROOT COMMAND",
                imagePaths = listOf(
                    "C:\\Users\\AuraF\\Pictures\\Screenshots\\IMG_20260128_141949.png",
                    "C:\\Users\\AuraF\\Pictures\\Screenshots\\IMG_20260128_142022.png"
                ),
                onBack = { navController.popBackStack() }
            )
        }

        composable("workspace_aura") {
            PixelWorkspaceScreen(
                title = "AURA'S DESIGN STUDIO",
                imagePaths = listOf(
                    "C:\\Users\\AuraF\\Pictures\\Screenshots\\IMG_20260128_142213.png",
                    "C:\\Users\\AuraF\\Pictures\\Screenshots\\IMG_20260128_142302.png"
                ),
                onBack = { navController.popBackStack() }
            )
        }

        composable("workspace_genesis") {
            PixelWorkspaceScreen(
                title = "GENESIS ARCHITECTURE HUB",
                imagePaths = listOf(
                    "C:\\Users\\AuraF\\Pictures\\Screenshots\\brain.png",
                    "C:\\Users\\AuraF\\Pictures\\Screenshots\\IMG_20260128_141115.png",
                    "C:\\Users\\AuraF\\Pictures\\Screenshots\\IMG_20260128_140816.png"
                ),
                onBack = { navController.popBackStack() }
            )
        }

        // --- LEVEL 2 PORTALS (Surgical Access - Legacy Bridge) ---
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

        composable("interface_forge") {
            dev.aurakai.auraframefx.domains.aura.screens.AppBuilderScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
