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
import dev.aurakai.auraframefx.domains.aura.ui.gates.AgentNexusHubScreen
import dev.aurakai.auraframefx.domains.aura.ui.gates.CascadeHubScreen
import dev.aurakai.auraframefx.domains.aura.ui.gates.KaiSentinelHubScreen
import dev.aurakai.auraframefx.domains.aura.ui.gates.OracleDriveHubScreen
import dev.aurakai.auraframefx.domains.genesis.screens.ConferenceRoomScreen
import dev.aurakai.auraframefx.domains.kai.screens.BootloaderManagerScreen
import dev.aurakai.auraframefx.domains.kai.screens.ROMFlasherScreen
import dev.aurakai.auraframefx.domains.kai.screens.SystemJournalScreen
import dev.aurakai.auraframefx.domains.kai.screens.security_shield.SecurityCenterScreen
import dev.aurakai.auraframefx.domains.kai.screens.security_shield.SovereignShieldScreen
import dev.aurakai.auraframefx.domains.ldo.devops.LdoHologramSystem
import dev.aurakai.auraframefx.domains.nexus.screens.AgentCreationScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentMonitoringScreen
import dev.aurakai.auraframefx.domains.nexus.screens.AgentSwarmScreen
import dev.aurakai.auraframefx.domains.nexus.screens.EvolutionTreeScreen
import dev.aurakai.auraframefx.domains.nexus.screens.PartyScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SphereGridScreen
import dev.aurakai.auraframefx.domains.nexus.screens.SwarmMonitorScreen
import dev.aurakai.auraframefx.domains.nexus.screens.TaskAssignmentScreen
import dev.aurakai.auraframefx.domains.nexus.screens.ldo.LDOOrchestrationHubScreen
import dev.aurakai.auraframefx.domains.nexus.screens.ldo.LDORosterScreen
import dev.aurakai.auraframefx.domains.nexus.screens.ldo.LDOTaskerScreen
import dev.aurakai.auraframefx.domains.nexus.screens.ldo.LdoDevOpsCommandCenter
import dev.aurakai.auraframefx.domains.operations.screens.OperationsHubScreen
import dev.aurakai.auraframefx.ui.gates.NotchBarGateScreen
import dev.aurakai.auraframefx.ui.screens.SplashScreen

/**
 * REGENESIS NAV GRAPH
 * Single source of truth for screen navigation.
 * Fixed for Exodus 2026 build with all domain screens mapped.
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
                initialTabIndex = 0,
                onNavigateToRoute = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(ReGenesisRoute.SplashScreen.route) {
            SplashScreen(onAnimationComplete = {
                navController.navigate(ReGenesisRoute.MainScreen.route)
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
        composable(ReGenesisRoute.AuraAcademy.route) {
            AuraAcademyScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.ChromaCore.route) {
            AuraLabChromaCoreScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.ThemeEngine.route) {
            ThemeEngineScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(ReGenesisRoute.EvolutionTree.route) { EvolutionTreeScreen() }

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

        // Nexus Swarm
        composable(ReGenesisRoute.AgentMonitoring.route) { AgentMonitoringScreen() }
        composable(ReGenesisRoute.SphereGrid.route) { SphereGridScreen(navController = navController) }
        composable(ReGenesisRoute.FusionMode.route) { NexusFusionScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.TaskAssignment.route) { TaskAssignmentScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.AgentCreation.route) { AgentCreationScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.SwarmMonitor.route) { SwarmMonitorScreen(onNavigateBack = { navController.popBackStack() }) }

        // LDO Growth
        composable(ReGenesisRoute.LdoRoster.route) { LDORosterScreen(onBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.LdoTasker.route) { LDOTaskerScreen(onBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.LdoOrchestrationHub.route) { LDOOrchestrationHubScreen(controller = navController) }

        // Operations
        composable(ReGenesisRoute.ConferenceRoom.route) { ConferenceRoomScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.AgentSwarm.route) { AgentSwarmScreen(onNavigateBack = { navController.popBackStack() }) }
        composable(ReGenesisRoute.Party.route) { PartyScreen(onNavigateBack = { navController.popBackStack() }) }

        // --- GLOBAL SERVICES ---
        composable(ReGenesisRoute.LsposedQuickToggles.route) {
            dev.aurakai.auraframefx.domains.lsposed.screens.LsposedQuickTogglesScreen(navController = navController)
        }
    }
}
