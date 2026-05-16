package dev.aurakai.auraframefx.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ReGenesisRoute(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object CommandDeck :
        ReGenesisRoute("command_deck", "Command Deck", Icons.Default.Dashboard)

    data object NeuralNexus : ReGenesisRoute("neural_nexus", "Neural Nexus", Icons.Default.Bolt)
    data object LdoArchitecture :
        ReGenesisRoute("ldo_architecture", "LDO Architecture", Icons.Default.ViewInAr)

    data object ChromaForge :
        ReGenesisRoute("chroma_forge", "Chroma Forge", Icons.Default.ColorLens)

    data object SentinelMatrix :
        ReGenesisRoute("sentinel_matrix", "Sentinel Matrix", Icons.Default.Security)

    data object OracleDrive : ReGenesisRoute("oracle_drive", "OracleDrive", Icons.Default.Storage)
    data object EmergentSwarm :
        ReGenesisRoute("emergent_swarm", "Emergent Swarm", Icons.Default.Hub)

    data object Spellhook : ReGenesisRoute("spellhook", "Spellhook", Icons.Default.AutoAwesome)
    data object FoundationRebirth :
        ReGenesisRoute("foundation_rebirth", "Foundation Rebirth", Icons.Default.School)

    companion object {
        val mainTabs = listOf(
            NeuralNexus,
            LdoArchitecture,
            ChromaForge,
            SentinelMatrix,
            OracleDrive,
            EmergentSwarm,
            Spellhook,
            FoundationRebirth
        )
    }
}
