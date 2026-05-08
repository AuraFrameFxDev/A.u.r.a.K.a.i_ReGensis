package dev.aurakai.auraframefx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

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

fun TabbedMasterIndex(initialTabIndex: Int, onNavigateToRoute: (String) -> Unit) {
    TODO("Not yet implemented")
}
