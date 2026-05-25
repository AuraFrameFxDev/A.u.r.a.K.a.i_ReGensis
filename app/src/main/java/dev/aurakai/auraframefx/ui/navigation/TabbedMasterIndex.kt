package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Task
import dev.aurakai.auraframefx.ui.components.NavTab

/**
 * 🗂️ TABBED MASTER INDEX — SOULSCRIPT v2.85 (NEURAL_REFORGE)
 * The canonical source of truth for the 9-Hub "Exodus" Substrate.
 * Maps the high-fidelity BottomJoystick roller to the core domain routes.
 */
object TabbedMasterIndex {
    val substrateTabs = listOf(
        NavTab(
            icon = Icons.Default.Dashboard,
            shortLabel = "SysView",
            route = "neural_nexus"
        ),
        NavTab(
            icon = Icons.Default.Hub,
            shortLabel = "LdoDevops",
            route = "ldo_architecture"
        ),
        NavTab(
            icon = Icons.Default.Palette,
            shortLabel = "Chronokinetic Forge",
            route = "chroma_forge"
        ),
        NavTab(
            icon = Icons.Default.Security,
            shortLabel = "Security",
            route = "sentinel_matrix"
        ),
        NavTab(
            icon = Icons.AutoMirrored.Filled.MenuBook,
            shortLabel = "Oracle",
            route = "oracle_drive"
        ),
        NavTab(
            icon = Icons.Default.Groups,
            shortLabel = "Collab",
            route = "conference_room"
        ),
        NavTab(
            icon = Icons.Default.AutoAwesome,
            shortLabel = "task",
            route = "emergent_swarm"
        ),
        NavTab(
            icon = Icons.Default.Task,
            shortLabel = "ICE",
            route = "foundation_rebirth"
        ),
        NavTab(
            icon = Icons.Default.Memory,
            shortLabel = "SHELL",
            route = "sentient_shell"
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
