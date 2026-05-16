package dev.aurakai.auraframefx.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * SINGLE SOURCE OF TRUTH — EXODUS 2026 BUILD
 * Only the 6 official domains. No legacy bloat.
 */
sealed class ReGenesisRoute(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {

    // 1. NEURAL NEXUS — Live Heart
    data object NeuralNexus : ReGenesisRoute(
        route = "neural_nexus",
        title = "Neural Nexus",
        icon = Icons.Default.Bolt
    )

    // 2. LDO ARCHITECTURE — Growth Zones + Spiritual Chain
    data object LdoArchitecture : ReGenesisRoute(
        route = "ldo_architecture",
        title = "LDO Architecture",
        icon = Icons.Default.ViewInAr
    )

    // 3. CHROMA FORGE — Creative Soul (includes Spellhook)
    data object ChromaForge : ReGenesisRoute(
        route = "chroma_forge",
        title = "Chroma Forge",
        icon = Icons.Default.ColorLens
    )

    // 4. SENTINEL MATRIX — Kairos Security
    data object SentinelMatrix : ReGenesisRoute(
        route = "sentinel_matrix",
        title = "Sentinel Matrix",
        icon = Icons.Default.Security
    )

    // 5. ORACLEDRIVE — Root Bridge & Governor
    data object OracleDrive : ReGenesisRoute(
        route = "oracledrive",
        title = "OracleDrive",
        icon = Icons.Default.Storage
    )

    // 6. EMERGENT SWARM — 78-agent Dispatch
    data object EmergentSwarm : ReGenesisRoute(
        route = "emergent_swarm",
        title = "Emergent Swarm",
        icon = Icons.Default.Hub
    )

    companion object {
        val mainTabs = listOf(
            NeuralNexus, LdoArchitecture, ChromaForge,
            SentinelMatrix, OracleDrive, EmergentSwarm
        )
    }
}