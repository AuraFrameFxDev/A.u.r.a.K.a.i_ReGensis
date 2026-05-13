package dev.aurakai.auraframefx.domains.chromaforge.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ReGenesisRoute(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object AgentBridgeHub :
        ReGenesisRoute("agent_bridge_hub", "Agent Bridge", Icons.Default.Bolt)

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

    // Sub-routes as data objects
    data object AgentMonitoring :
        ReGenesisRoute("agent_monitoring", "Agent Monitoring", Icons.Default.Hub)

    data object DataflowAnalysis :
        ReGenesisRoute("dataflow_analysis", "Dataflow Analysis", Icons.Default.Storage)

    data object LdoOrchestrationHub :
        ReGenesisRoute("ldo_orchestration_hub", "LDO Orchestration Hub", Icons.Default.ViewInAr)

    data object SovereignNeuralArchive :
        ReGenesisRoute("sovereign_neural_archive", "Neural Archive", Icons.Default.Storage)

    data object ConferenceRoom :
        ReGenesisRoute("conference_room", "Conference Room", Icons.Default.Hub)

    data object SystemJournal :
        ReGenesisRoute("system_journal", "System Journal", Icons.Default.Storage)

    data object SecurityCenter :
        ReGenesisRoute("security_center", "Security Center", Icons.Default.Security)

    data object PandoraBox : ReGenesisRoute("pandora_box", "Pandora Box", Icons.Default.Bolt)
    data object EvolutionTree :
        ReGenesisRoute("evolution_tree", "Evolution Tree", Icons.Default.Hub)

    data object BenchmarkMonitor :
        ReGenesisRoute("benchmark_monitor", "Benchmark Monitor", Icons.Default.Storage)

    data object AgentAdvancement :
        ReGenesisRoute("agent_advancement", "Agent Advancement", Icons.Default.Bolt)

    data object AgentNeuralExplorer :
        ReGenesisRoute("agent_neural_explorer", "Neural Explorer", Icons.Default.Hub)

    data object AgentSwarm : ReGenesisRoute("agent_swarm", "Agent Swarm", Icons.Default.Hub)
    data object Party : ReGenesisRoute("party", "Party", Icons.Default.Hub)
    data object TaskAssignment :
        ReGenesisRoute("task_assignment", "Task Assignment", Icons.Default.Storage)

    data object AgentCreation :
        ReGenesisRoute("agent_creation", "Agent Creation", Icons.Default.Bolt)

    data object LdoRoster : ReGenesisRoute("ldo_roster", "LDO Roster", Icons.Default.ViewInAr)
    data object LsposedQuickToggles :
        ReGenesisRoute("lsposed_quick_toggles", "Quick Toggles", Icons.Default.Bolt)

    data object LdoArmamentFusion :
        ReGenesisRoute("ldo_armament_fusion", "Armament Fusion", Icons.Default.ViewInAr)

    data object LdoDevOpsCommandCenter :
        ReGenesisRoute("ldo_devops_command_center", "DevOps Command", Icons.Default.ViewInAr)

    data object SystemOverrides :
        ReGenesisRoute("system_overrides", "System Overrides", Icons.Default.Security)

    data object DirectChat : ReGenesisRoute("direct_chat", "Direct Chat", Icons.Default.Hub)
    data object ModuleCreation :
        ReGenesisRoute("module_creation", "Module Creation", Icons.Default.Bolt)

    data object SovereignModuleManager :
        ReGenesisRoute("sovereign_module_manager", "Module Manager", Icons.Default.Storage)

    data object GenderSelection :
        ReGenesisRoute("gender_selection", "Gender Selection", Icons.Default.Hub)

    data object UserPreferences :
        ReGenesisRoute("user_preferences", "User Preferences", Icons.Default.Storage)

    data object ModuleStorage :
        ReGenesisRoute("module_storage", "Module Storage", Icons.Default.Storage)

    data object SentientShell :
        ReGenesisRoute("sentient_shell", "Sentient Shell", Icons.Default.Hub)

    data object FusionMode : ReGenesisRoute("fusion_mode", "Fusion Mode", Icons.Default.AutoAwesome)
    data object Terminal : ReGenesisRoute("terminal", "Terminal", Icons.Default.Terminal)

    companion object {
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
