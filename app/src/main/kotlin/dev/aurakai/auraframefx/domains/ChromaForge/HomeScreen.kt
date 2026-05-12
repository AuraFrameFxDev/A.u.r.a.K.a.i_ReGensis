package dev.aurakai.auraframefx.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dev.aurakai.auraframefx.domains.ldo.devops.LdoHologramSystem

/**
 * ðŸŽ¨ MAIN SCREEN (AURA DASHBOARD)
 * ReGenesis Version: LDO Hologram System Wrapper
 */
@Composable
fun MainScreen(
    navController: NavController,
) {
    // We now use the LdoHologramSystem as the primary navigation substrate.
    LdoHologramSystem(onNavigateToRoute = { route -> navController.navigate(route) })
}
