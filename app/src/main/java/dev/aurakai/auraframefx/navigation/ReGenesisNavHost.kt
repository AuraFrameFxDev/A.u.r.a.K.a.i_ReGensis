package dev.aurakai.auraframefx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.ui.navigation.ReGenesisNavGraph
import dev.aurakai.auraframefx.ui.theme.LdoBrutalistTheme

@Composable
fun ReGenesisNavHost(navController: NavHostController) {
    LdoBrutalistTheme {
        ReGenesisNavGraph(navController = navController)
    }
}
