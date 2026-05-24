package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Storage
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 🛰️ NAV DESTINATION — Canonical Route Definitions
 * Focuses on non-tabbed sub-routes and utility destinations.
 */
sealed class NavDestination(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object CommandDeck : NavDestination("command_deck", "Command Deck", Icons.Default.Adjust)
    object EscapeHatch :
        NavDestination("escape_hatch", "Escape Hatch", Icons.AutoMirrored.Filled.ExitToApp)
    object NexusMemoryCore :
        NavDestination("nexus_memory_core", "Nexus Memory Core", Icons.Default.Storage)
}
