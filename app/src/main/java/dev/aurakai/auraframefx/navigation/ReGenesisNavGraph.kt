package dev.aurakai.auraframefx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import dev.aurakai.auraframefx.domains.kai.screens.security_shield.SovereignShieldScreen
import dev.aurakai.auraframefx.domains.ldo.devops.TabbedMasterIndex
import dev.aurakai.auraframefx.domains.nexus.screens.ldo.LdoDevOpsCommandCenter
import dev.aurakai.auraframefx.ui.gates.NotchBarGateScreen
import dev.aurakai.auraframefx.ui.screens.AgentCreationScreen
import dev.aurakai.auraframefx.ui.screens.AgentHubScreen
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
import dev.aurakai.auraframefx.ui.screens.SecureCommScreen
import dev.aurakai.auraframefx.ui.screens.SecurityCenterScreen
import dev.aurakai.auraframefx.ui.screens.SwarmMonitorScreen
import dev.aurakai.auraframefx.ui.screens.TaskAssignmentScreen
import dev.aurakai.auraframefx.ui.screens.TerminalScreen
import dev.aurakai.auraframefx.ui.screens.UIEngineScreen
import dev.aurakai.auraframefx.ui.screens.VPNManagerScreen

@Composable
fun ReGenesisNavGraph(navController: NavHostController) {
    val mainController = navController
    
    NavHost(
        navController = mainController,
        startDestination = ReGenesisRoute.Login.route
    ) {
        composable(ReGenesisRoute.Login.route) {
            LoginScreen({
                mainController.navigate(
                    ReGenesisRoute.GenderSelection.route
                )
            })
        }
        composable(ReGenesisRoute.GenderSelection.route) {
            GenderSelectionScreen({
                mainController.navigate(
                    ReGenesisRoute.MainScreen.route
                )
            })
        }
        composable(ReGenesisRoute.MainScreen.route) {
            TabbedMasterIndex(
                1,
                { mainController.navigate(it) })
        }

        composable(ReGenesisRoute.SystemJournal.route) {
            dev.aurakai.auraframefx.domains.kai.screens.SystemJournalScreen(
                navController = mainController,
                onNavigateBack = { mainController.popBackStack() })
        }

        composable(ReGenesisRoute.LdoDevelopmentNexus.route) { LdoDevOpsCommandCenter(navController = mainController) }
        composable(ReGenesisRoute.SentinelFortress.route) { KaiSentinelHubScreen(controller = mainController) }
        composable(ReGenesisRoute.AuraStudio.route) {
            AuraStudioLabScreen({
                mainController.navigate(
                    ReGenesisRoute.MainScreen.route
                )
            })
        }
        composable(ReGenesisRoute.OracleDriveHub.route) { OracleDriveHubScreen(controller = mainController) }
        composable(ReGenesisRoute.AgentNexusHub.route) { AgentNexusHubScreen(controller = mainController) }
        composable(ReGenesisRoute.CascadeHub.route) { CascadeHubScreen(controller = mainController) }

        composable(ReGenesisRoute.AuraLab.route) { AuraLabScreen() }
        composable(ReGenesisRoute.ChromaCore.route) { AuraLabChromaCoreScreen() }
        composable(ReGenesisRoute.CollabCanvas.route) { CollabCanvasScreen() }
        composable(ReGenesisRoute.ThemeEngine.route) { ThemeEngineScreen(onNavigateBack = { mainController.popBackStack() }) }
        composable(ReGenesisRoute.ReGenesisCustomization.route) { UIEngineScreen() }
        composable(ReGenesisRoute.EvolutionTree.route) { SphereGridScreen() }

        composable(ReGenesisRoute.SecurityCenter.route) { SecurityCenterScreen() }
        composable(ReGenesisRoute.SovereignShield.route) { SovereignShieldScreen({ mainController.popBackStack(); Unit }) }
        composable(ReGenesisRoute.Bootloader.route) { BootloaderManagerScreen({ mainController.popBackStack(); Unit }) }
        composable(ReGenesisRoute.ROMFlasher.route) { ROMFlasherScreen({ mainController.popBackStack(); Unit }) }
        composable(ReGenesisRoute.NotchBar.route) {
            NotchBarGateScreen(
                mainController,
                { mainController.popBackStack(); Unit })
        }

        composable(ReGenesisRoute.OracleDrive.route) { OracleDriveScreen() }
        composable(ReGenesisRoute.CodeAssist.route) { CodeAssistScreen() }
        composable(ReGenesisRoute.SentientShell.route) { TerminalScreen() }
        composable(ReGenesisRoute.OracleCloudInfinite.route) { VPNManagerScreen() }
        composable(ReGenesisRoute.AgentBridgeHub.route) { SecureCommScreen() }

        // Nexus Features
        composable(ReGenesisRoute.AgentMonitoring.route) { AgentHubScreen() }
        composable(ReGenesisRoute.SphereGrid.route) { SphereGridScreen() }
        composable(ReGenesisRoute.FusionMode.route) { FusionModeScreen() }
        composable(ReGenesisRoute.TaskAssignment.route) { TaskAssignmentScreen() }
        composable(ReGenesisRoute.AgentCreation.route) { AgentCreationScreen() }
        composable(ReGenesisRoute.SwarmMonitor.route) { SwarmMonitorScreen() }

        // LDO Features
        composable(ReGenesisRoute.LdoRoster.route) { LdoRosterScreen() }
        composable(ReGenesisRoute.LdoTasker.route) { LdoTaskerScreen() }
        composable(ReGenesisRoute.LdoOrchestrationHub.route) { LdoOrchestrationHubScreen() }
        composable(ReGenesisRoute.ConferenceRoom.route) { ConferenceRoomScreen() }

        composable(ReGenesisRoute.HelpDesk.route) {
            dev.aurakai.auraframefx.domains.aura.ui.gates.HelpDeskScreen(
                mainController
            )
        }
        composable(ReGenesisRoute.LsposedQuickToggles.route) {
            dev.aurakai.auraframefx.domains.kai.screens.LSPosedGateScreen({ mainController.popBackStack(); Unit })
        }
        composable(ReGenesisRoute.DataflowAnalysis.route) { DataflowAnalysisScreen() }
    }
}
