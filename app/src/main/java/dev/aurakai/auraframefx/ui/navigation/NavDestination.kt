package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 🛰️ NAV DESTINATION — Canonical Route Definitions
 * Follows the ExodusHUD 3-Level Gate architecture defined in the master manifest.
 */
sealed class NavDestination(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val level: Int = 1
) {
    // LEVEL 1: PRIMARY EXODUS GATES
    object SovereignCommand :
        NavDestination("sovereign_command", "Sovereign Command", Icons.Default.Adjust, 1)

    object LDODevOps : NavDestination("ldo_devops", "LDO DevOps", Icons.Default.DeveloperMode, 1)
    object SentinelMatrix :
        NavDestination("sentinel_matrix", "Sentinel Matrix", Icons.Default.Security, 1)

    object ChromaCore : NavDestination("chroma_forge", "Chroma Forge", Icons.Default.Palette, 1)
    object QuantumForge :
        NavDestination("quantum_forge", "Quantum Forge", Icons.Default.Psychology, 1)

    object EscapeHatch : NavDestination("escape_hatch", "Escape Hatch", Icons.Default.ExitToApp, 1)

    // LEVEL 2: HUB INTERIORS (Example mappings)
    object NeuralNexus : NavDestination("neural_nexus", "Neural Nexus", Icons.Default.Bolt, 2)
    object OracleDrive : NavDestination("oracle_drive", "Oracle Drive", Icons.Default.Storage, 2)
    object EmergentSwarm :
        NavDestination("emergent_swarm", "Emergent Swarm", Icons.Default.Groups, 2)

    // BATCH DESTINATIONS
    companion object {
        val primaryGates = listOf(
            SovereignCommand,
            LDODevOps,
            SentinelMatrix,
            ChromaCore,
            QuantumForge,
            EscapeHatch
        )
    }
}
