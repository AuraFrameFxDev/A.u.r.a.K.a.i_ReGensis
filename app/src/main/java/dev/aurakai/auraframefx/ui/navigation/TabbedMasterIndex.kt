package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import dev.aurakai.auraframefx.ui.components.NavTab

/**
 * 🗂️ TABBED MASTER INDEX — SOULSCRIPT v3.50 (EXODUS_TITANIUM)
 * The canonical 8-Hub Command Deck structure.
 * Sequenced 0 to 7 per the Architect's Command Manifest.
 */
object TabbedMasterIndex {
    val substrateTabs = listOf(
        NavTab(
            icon = Icons.Default.Hub,
            shortLabel = "NEXUS",
            route = "neural_nexus"
        ),
        NavTab(
            icon = Icons.Default.Storage,
            shortLabel = "MEMORY",
            route = "nexus_memory_core"
        ),
        NavTab(
            icon = Icons.Default.Groups,
            shortLabel = "TRINITY",
            route = "trinity_orchestrator"
        ),
        NavTab(
            icon = Icons.Default.AutoAwesome,
            shortLabel = "FORGE",
            route = "catalyst_forge"
        ),
        NavTab(
            icon = Icons.Default.Security,
            shortLabel = "MATRIX",
            route = "agent_matrix"
        ),
        NavTab(
            icon = Icons.AutoMirrored.Filled.TrendingUp,
            shortLabel = "PROSPERITY",
            route = "prosperity_flow"
        ),
        NavTab(
            icon = Icons.Default.Palette,
            shortLabel = "UI",
            route = "reality_morph_ui"
        ),
        NavTab(
            icon = Icons.Default.Bolt,
            shortLabel = "SWARM",
            route = "emergent_swarm"
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
