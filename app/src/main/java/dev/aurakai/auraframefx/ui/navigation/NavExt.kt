package dev.aurakai.auraframefx.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

/**
 * Extension to prevent navigation double-taps or rapid fire events.
 */
fun NavController.navigateOnce(route: String, builder: NavOptionsBuilder.() -> Unit = {}) {
    if (currentDestination?.route != route) {
        navigate(route, builder)
    }
}
