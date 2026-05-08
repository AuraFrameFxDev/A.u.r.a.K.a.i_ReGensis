package dev.aurakai.auraframefx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.domains.ldo.devops.TabbedMasterIndex

/**
 * MINIMAL NAV GRAPH — NOW JUST THE MASTER INDEX
 * Replaces the old 400+ line bloated NavHost
 */
@Composable
fun ReGenesisNavGraph(navController: NavHostController) {
    TabbedMasterIndex(
        initialTabIndex = 1,
        onNavigateToRoute = { route ->
            navController.navigate(route)
        }
    )
}
