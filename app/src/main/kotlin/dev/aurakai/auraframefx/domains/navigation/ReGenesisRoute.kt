package dev.aurakai.auraframefx.domains.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ReGenesisRoute(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    class AgentBridgeHub(label: String, icon: ImageVector, color: Color, route: Any)

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

    companion object {
        val AgentMonitoring: Any
        val DataflowAnalysis: Any
        val LdoOrchestrationHub: Any
        val SovereignNeuralArchive: Any
        val ConferenceRoom: Any
        val SystemJournal: Any
        val SecurityCenter: Any
        val PandoraBox: Any
        val EvolutionTree: Any
        val BenchmarkMonitor: Any
        val AgentAdvancement: Any
        val AgentNeuralExplorer: Any
        val AgentSwarm: Any
        val Party: Any
        val TaskAssignment: Any
        val AgentCreation: Any
        val LdoRoster: Any
        val LsposedQuickToggles: Any
        val LdoArmamentFusion: Any
        val LdoDevOpsCommandCenter: Any
        val SystemOverrides: Any
        val DirectChat: Any
        val ModuleCreation: Any
        val SovereignModuleManager: Any
        val GenderSelection: Any
        val UserPreferences: Any
        val ModuleStorage: Any
        val SentientShell: Any
        val mainTabs = listOf(
            NeuralNexus,
            LdoArchitecture,
            ChromaForge,
            SentinelMatrix,
            OracleDrive,
            EmergentSwarm
        )
    }
}
