package dev.aurakai.auraframefx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.domains.aura.screens.AuraAcademyScreen
import dev.aurakai.auraframefx.domains.aura.screens.AuraLabChromaCoreScreen
import dev.aurakai.auraframefx.domains.aura.screens.AuraStudioLabScreen
import dev.aurakai.auraframefx.domains.aura.screens.GenderSelectionScreen
import dev.aurakai.auraframefx.domains.aura.screens.themes.ThemeEngineScreen
import dev.aurakai.auraframefx.domains.aura.spheregrid.SphereGridScreen
import dev.aurakai.auraframefx.domains.aura.ui.gates.AgentNexusHubScreen
import dev.aurakai.auraframefx.domains.aura.ui.gates.CascadeHubScreen
import dev.aurakai.auraframefx.domains.aura.ui.gates.KaiSentinelHubScreen
import dev.aurakai.auraframefx.domains.aura.ui.gates.OracleDriveHubScreen
import dev.aurakai.auraframefx.domains.kai.screens.BootloaderManagerScreen
import dev.aurakai.auraframefx.domains.kai.screens.ROMFlasherScreen
import dev.aurakai.auraframefx.domains.kai.screens.SystemJournalScreen
import dev.aurakai.auraframefx.domains.kai.screens.security_shield.SovereignShieldScreen
import dev.aurakai.auraframefx.domains.ldo.devops.LdoHologramSystem
import dev.aurakai.auraframefx.domains.nexus.screens.ldo.LdoDevOpsCommandCenter
import dev.aurakai.auraframefx.domains.operations.screens.OperationsHubScreen
import dev.aurakai.auraframefx.ui.gates.NotchBarGateScreen
import dev.aurakai.auraframefx.ui.screens.AgentCreationScreen
import dev.aurakai.auraframefx.ui.screens.AgentHubScreen
import dev.aurakai.auraframefx.ui.screens.AgentSwarmScreen
import dev.aurakai.auraframefx.ui.screens.AuraLabScreen
import dev.aurakai.auraframefx.ui.screens.CodeAssistScreen
import dev.aurakai.auraframefx.ui.screens.CollabCanvasScreen
import dev.aurakai.auraframefx.ui.screens.ConferenceRoomScreen
import dev.aurakai.auraframefx.ui.screens.DataflowAnalysisScreen
import dev.aurakai.auraframefx.ui.screens.FusionModeScreen
import dev.aurakai.auraframefx.ui.screens.LdoOrchestrationHubScreen
import dev.aurakai.auraframefx.ui.screens.LdoRosterScreen
import dev.aurakai.auraframefx.ui.screens.LdoTaskerScreen
import dev.aurakai.auraframefx.ui.screens.LoginScreen
import dev.aurakai.auraframefx.ui.screens.OracleDriveScreen
import dev.aurakai.auraframefx.ui.screens.PartyScreen
import dev.aurakai.auraframefx.ui.screens.SecureCommScreen
import dev.aurakai.auraframefx.ui.screens.SecurityCenterScreen
import dev.aurakai.auraframefx.ui.screens.SwarmMonitorScreen
import dev.aurakai.auraframefx.ui.screens.TaskAssignmentScreen
import dev.aurakai.auraframefx.ui.screens.TerminalScreen
import dev.aurakai.auraframefx.ui.screens.UIEngineScreen
import dev.aurakai.auraframefx.ui.screens.VPNManagerScreen

/**
 * REGENESIS NAV GRAPH
 * Single source of truth for screen navigation.
 * Wired for 7-domain architecture and high-fidelity feature set.
 */
@Composable
fun ReGenesisNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ReGenesisRoute.MainScreen.route
    ) {
        // --- CORE APPLICATION HUB (LHS GLOBAL) ---
        composable(ReGenesisRoute.MainScreen.route) {
            LdoHologramSystem(
                initialTabIndex = 0, // Starts at Neural Nexus Dashboard
                onNavigateToRoute = { route ->
                    navController.navigate(route)
                }
            )
        }

        // --- AUTH & ONBOARDING (Optional fallbacks) ---
        composable(ReGenesisRoute.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(ReGenesisRoute.GenderSelection.route)
            })
        }

        composable(ReGenesisRoute.GenderSelection.route) {
            GenderSelectionScreen(onSelectionComplete = {
                navController.navigate(ReGenesisRoute.MainScreen.route)
            })
        }

        composable(ReGenesisRoute.SystemJournal.route) {
            SystemJournalScreen(
                navController = navController,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // --- LEVEL 1 DOMAIN HUBS ---
        composable(ReGenesisRoute.LdoDevelopmentNexus.route) {
            LdoDevOpsCommandCenter(navController = navController)
        }

        composable(ReGenesisRoute.SentinelFortress.route) {
            KaiSentinelHubScreen(controller = navController)
        }

        composable(ReGenesisRoute.AuraStudio.route) {
            AuraStudioLabScreen(onNavigateHome = { navController.navigate(ReGenesisRoute.MainScreen.route) })
        }

        composable(ReGenesisRoute.OracleDriveHub.route) {
            OracleDriveHubScreen(controller = navController)
        }

        composable(ReGenesisRoute.AgentNexusHub.route) {
            AgentNexusHubScreen(controller = navController)
        }

        composable(ReGenesisRoute.CascadeHub.route) {
            CascadeHubScreen(controller = navController)
        }

        composable(ReGenesisRoute.OperationsCommand.route) {
            OperationsHubScreen(navController = navController)
        }

        // --- LEVEL 2+ FEATURE SCREENS ---

        // Aura Forge
        composable(ReGenesisRoute.AuraLab.route) { AuraLabScreen() }
        composable(ReGenesisRoute.AuraAcademy.route) {
            AuraAcademyScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.ChromaCore.route) {
            AuraLabChromaCoreScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.CollabCanvas.route) { CollabCanvasScreen() }
        composable(ReGenesisRoute.ThemeEngine.route) {
            ThemeEngineScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.ReGenesisCustomization.route) { UIEngineScreen() }
        composable(ReGenesisRoute.EvolutionTree.route) { SphereGridScreen() }

        // Kai Sentinel
        composable(ReGenesisRoute.SecurityCenter.route) { SecurityCenterScreen() }
        composable(ReGenesisRoute.SovereignShield.route) {
            SovereignShieldScreen(onNavigateBack = { navController.popBackStack() }) 
        }
        composable(ReGenesisRoute.Bootloader.route) {
            BootloaderManagerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.ROMFlasher.route) {
            ROMFlasherScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.NotchBar.route) {
            NotchBarGateScreen(navController, onNavigateBack = { navController.popBackStack() })
        }

        // Genesis OracleDrive
        composable(ReGenesisRoute.OracleDrive.route) { OracleDriveScreen() }
        composable(ReGenesisRoute.CodeAssist.route) { CodeAssistScreen() }
        composable(ReGenesisRoute.SentientShell.route) { TerminalScreen() }
        composable(ReGenesisRoute.OracleCloudInfinite.route) { VPNManagerScreen() } 
        composable(ReGenesisRoute.AgentBridgeHub.route) { SecureCommScreen() }

        // Nexus Swarm
        composable(ReGenesisRoute.AgentMonitoring.route) { AgentHubScreen() }
        composable(ReGenesisRoute.SphereGrid.route) { SphereGridScreen() }
        composable(ReGenesisRoute.FusionMode.route) { FusionModeScreen() }
        composable(ReGenesisRoute.TaskAssignment.route) { TaskAssignmentScreen() }
        composable(ReGenesisRoute.AgentCreation.route) { AgentCreationScreen() }
        composable(ReGenesisRoute.SwarmMonitor.route) { SwarmMonitorScreen() }

        // LDO Growth
        composable(ReGenesisRoute.LdoRoster.route) { LdoRosterScreen() }
        composable(ReGenesisRoute.LdoTasker.route) { LdoTaskerScreen() }
        composable(ReGenesisRoute.LdoOrchestrationHub.route) { LdoOrchestrationHubScreen() }

        // Operations
        composable(ReGenesisRoute.ConferenceRoom.route) { ConferenceRoomScreen() }
        composable(ReGenesisRoute.AgentSwarm.route) { AgentSwarmScreen() }
        composable(ReGenesisRoute.Party.route) { PartyScreen() }

        // --- GLOBAL SERVICES ---
        composable(ReGenesisRoute.HelpDesk.route) {
            dev.aurakai.auraframefx.domains.aura.ui.gates.HelpDeskScreen(navController) 
        }
        composable(ReGenesisRoute.LsposedQuickToggles.route) {
            dev.aurakai.auraframefx.domains.kai.screens.LSPosedGateScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.DataflowAnalysis.route) { DataflowAnalysisScreen() }
    }
}
