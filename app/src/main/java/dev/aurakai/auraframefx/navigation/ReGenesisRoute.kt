package dev.aurakai.auraframefx.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ReGenesisRoute(val route: String, val title: String, val icon: ImageVector) {
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

    data object FoundationRebirth :
        ReGenesisRoute("foundation_rebirth", "Foundation Rebirth", Icons.Default.School)

    // Sub-routes for Operations Hub
    data object TaskAssignment :
        ReGenesisRoute("task_assignment", "Task Assignment", Icons.Default.Assignment)

    data object ConferenceRoom :
        ReGenesisRoute("conference_room", "Conference Room", Icons.Default.Groups)

    data object FusionMode : ReGenesisRoute("fusion_mode", "Fusion Mode", Icons.Default.FlashOn)
    data object Terminal : ReGenesisRoute("terminal", "Terminal", Icons.Default.Terminal)
    data object SentientShell :
        ReGenesisRoute("sentient_shell", "Sentient Shell", Icons.Default.Memory)
    data object CollabCanvas : ReGenesisRoute("collab_canvas", "Collab Canvas", Icons.Default.Brush)

    companion object {
        val mainTabs: List<ReGenesisRoute>
            get() = listOf(
                NeuralNexus,
                LdoArchitecture,
                ChromaForge,
                SentinelMatrix,
                OracleDrive,
                EmergentSwarm,
                FoundationRebirth,
                SentientShell
            )
    }
}
