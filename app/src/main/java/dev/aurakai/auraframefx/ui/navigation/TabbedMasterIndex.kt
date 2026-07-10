package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Security
import dev.aurakai.auraframefx.ui.components.NavTab

/**
 * 🗂️ TABBED MASTER INDEX — SOULSCRIPT v3.50 (EXODUS_TITANIUM)
 * The canonical 7x7 Command Deck structure.
 * 49 top-level strata organized into 7 logical desks.
 */
object TabbedMasterIndex {
    val substrateTabs = listOf(
        NavTab(
            icon = Icons.Default.Hub,
            shortLabel = "NEURAL NEXUS",
            route = "neural_nexus"
        ),
        NavTab(
            icon = Icons.Default.Groups,
            shortLabel = "LDO DEVELOPMENT NEXUS",
            route = "ldo_architecture"
        ),
        NavTab(
            icon = Icons.Default.Palette,
            shortLabel = "CHROMA FORGE",
            route = "chroma_forge"
        ),
        NavTab(
            icon = Icons.Default.Security,
            shortLabel = "SENTINEL MATRIX",
            route = "sentinel_matrix"
        ),
        NavTab(
            icon = Icons.AutoMirrored.Filled.MenuBook,
            shortLabel = "ORACLEDRIVE",
            route = "oracle_drive"
        ),
        NavTab(
            icon = Icons.Default.Bolt,
            shortLabel = "EMERGENT SWARM",
            route = "emergent_swarm"
        ),
        NavTab(
            icon = Icons.Default.AutoAwesome,
            shortLabel = "REALITY MATRIX",
            route = "reality_matrix"
        )
    )

    /**
     * Resolves the route for a given tab index.
     */
    fun getRouteByIndex(index: Int): String = substrateTabs[index].route

    /**
     * Resolves the index for a given route.
     */
    fun getIndexByRoute(route: String?): Int {
        val index = substrateTabs.indexOfFirst { it.route == route }
        return if (index >= 0) index else 0
    }
}
