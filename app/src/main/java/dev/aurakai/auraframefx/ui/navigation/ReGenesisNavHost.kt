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

// Alias mapping for clarity based on user request names
typealias KaiRootToolsScreen = KaiSentinelHubScreen
typealias GenesisNexusHubScreen = AgentNexusHubScreen
typealias KaiRomToolsScreen = BootloaderManagerScreen
typealias AuraChromaCoreScreen = ChromaCoreColorsScreen
typealias AuraCollabCanvasScreen = CollabCanvasScreen

@Composable
fun ReGenesisNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "exodus_home") {
        
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
    }
}
