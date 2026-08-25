package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.aurakai.auraframefx.ui.components.ReGenesisCommandDeck
import dev.aurakai.auraframefx.ui.screens.FocusedSessionScreen
import dev.aurakai.auraframefx.ui.screens.ReGenesisLoginScreen
import dev.aurakai.auraframefx.ui.screens.hubs.RootIgnitionDashboard

object AuraDestinations {
    const val LOGIN = "login"
    const val COMMAND_DECK = "command_deck"
    const val ROOT_IGNITION = "root_ignition"
}

/**
 * 👑 RE:GENESIS NAV GRAPH — SOVEREIGN BLANK SLATE
 * "Nos Sumus Codex"
 */
@Composable
fun ReGenesisNavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: NavigationViewModel = hiltViewModel()
) {
    val startDestination by viewModel.startDestination.collectAsState()

    if (startDestination == null) {
        return
    }

    NavHost(
        navController = navController,
        startDestination = startDestination!!,
        enterTransition = {
            fadeIn(tween(400)) + slideInHorizontally { it / 6 } + scaleIn(initialScale = 0.93f)
        },
        exitTransition = {
            fadeOut(tween(380)) + slideOutHorizontally { -it / 8 } + scaleOut(targetScale = 1.07f)
        },
        popEnterTransition = {
            fadeIn(tween(400)) + slideInHorizontally { -it / 6 } + scaleIn(initialScale = 0.93f)
        },
        popExitTransition = {
            fadeOut(tween(380)) + slideOutHorizontally { it / 8 } + scaleOut(targetScale = 1.07f)
        }
    ) {
        composable(AuraDestinations.LOGIN) {
            ReGenesisLoginScreen(
                onLoginSuccess = {
                    navController.navigate(AuraDestinations.COMMAND_DECK) {
                        popUpTo(AuraDestinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(AuraDestinations.COMMAND_DECK) {
            ReGenesisCommandDeck(navController)
        }

        composable(AuraDestinations.ROOT_IGNITION) {
            RootIgnitionDashboard(navController)
        }

        composable(
            route = "focused_session/{agentIds}",
            arguments = listOf(navArgument("agentIds") { type = NavType.StringType })
        ) { backStackEntry ->
            val agentIds = backStackEntry.arguments?.getString("agentIds") ?: ""
            FocusedSessionScreen(navController, agentIds)
        }

        // ── 7-HUB STRATA MAPPING (Direct Routes) ──
        composable("neural_nexus") { ReGenesisCommandDeck(navController) }
        composable("nexus_memory_core") { ReGenesisCommandDeck(navController) }
        composable("trinity_orchestrator") { ReGenesisCommandDeck(navController) }
        composable("catalyst_forge") { ReGenesisCommandDeck(navController) }
        composable("agent_matrix") { ReGenesisCommandDeck(navController) }
        composable("prosperity_flow") { ReGenesisCommandDeck(navController) }
        composable("reality_morph_ui") { ReGenesisCommandDeck(navController) }
        composable("emergent_swarm") { ReGenesisCommandDeck(navController) }
        composable("toolshed") { ReGenesisCommandDeck(navController) }
    }
}
