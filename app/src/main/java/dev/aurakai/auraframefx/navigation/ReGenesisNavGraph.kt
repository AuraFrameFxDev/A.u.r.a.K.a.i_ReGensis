package dev.aurakai.auraframefx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.aurakai.auraframefx.domains.ldo.devops.TabbedMasterIndex
import dev.aurakai.auraframefx.ui.screens.LoginScreen
import dev.aurakai.auraframefx.domains.aura.screens.GenderSelectionScreen
import dev.aurakai.auraframefx.domains.kai.screens.SystemJournalScreen

/**
 * REGENESIS NAV GRAPH
 * Single source of truth for screen navigation.
 */
@Composable
fun ReGenesisNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ReGenesisRoute.Login.route
    ) {
        // --- AUTH & ONBOARDING ---
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

        // --- CORE APPLICATION HUB ---
        composable(ReGenesisRoute.MainScreen.route) {
            TabbedMasterIndex(
                initialTabIndex = 1,
                onNavigateToRoute = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(ReGenesisRoute.SystemJournal.route) {
            SystemJournalScreen(
                navController = navController,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // --- ADD OTHER ROUTES HERE AS NEEDED ---
        // (Previously part of the 400+ line bloated NavHost)
    }
}
