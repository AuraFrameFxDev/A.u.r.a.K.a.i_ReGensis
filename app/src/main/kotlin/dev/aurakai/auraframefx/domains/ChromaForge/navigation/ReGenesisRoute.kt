package dev.aurakai.auraframefx.domains.chromaforge.navigation

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
        val AgentMonitoring =
            ReGenesisRoute("agent_monitoring", "Agent Monitoring", Icons.Default.Hub)
        val DataflowAnalysis =
            ReGenesisRoute("dataflow_analysis", "Dataflow Analysis", Icons.Default.Storage)
        val LdoOrchestrationHub =
            ReGenesisRoute("ldo_orchestration_hub", "LDO Orchestration Hub", Icons.Default.ViewInAr)
        val SovereignNeuralArchive =
            ReGenesisRoute("sovereign_neural_archive", "Neural Archive", Icons.Default.Storage)
        val ConferenceRoom = ReGenesisRoute("conference_room", "Conference Room", Icons.Default.Hub)
        val SystemJournal =
            ReGenesisRoute("system_journal", "System Journal", Icons.Default.Storage)
        val SecurityCenter =
            ReGenesisRoute("security_center", "Security Center", Icons.Default.Security)
        val PandoraBox = ReGenesisRoute("pandora_box", "Pandora Box", Icons.Default.Bolt)
        val EvolutionTree = ReGenesisRoute("evolution_tree", "Evolution Tree", Icons.Default.Hub)
        val BenchmarkMonitor =
            ReGenesisRoute("benchmark_monitor", "Benchmark Monitor", Icons.Default.Storage)
        val AgentAdvancement =
            ReGenesisRoute("agent_advancement", "Agent Advancement", Icons.Default.Bolt)
        val AgentNeuralExplorer =
            ReGenesisRoute("agent_neural_explorer", "Neural Explorer", Icons.Default.Hub)
        val AgentSwarm = ReGenesisRoute("agent_swarm", "Agent Swarm", Icons.Default.Hub)
        val Party = ReGenesisRoute("party", "Party", Icons.Default.Hub)
        val TaskAssignment =
            ReGenesisRoute("task_assignment", "Task Assignment", Icons.Default.Storage)
        val AgentCreation = ReGenesisRoute("agent_creation", "Agent Creation", Icons.Default.Bolt)
        val LdoRoster = ReGenesisRoute("ldo_roster", "LDO Roster", Icons.Default.ViewInAr)
        val LsposedQuickToggles =
            ReGenesisRoute("lsposed_quick_toggles", "Quick Toggles", Icons.Default.Bolt)
        val LdoArmamentFusion =
            ReGenesisRoute("ldo_armament_fusion", "Armament Fusion", Icons.Default.ViewInAr)
        val LdoDevOpsCommandCenter =
            ReGenesisRoute("ldo_devops_command_center", "DevOps Command", Icons.Default.ViewInAr)
        val SystemOverrides =
            ReGenesisRoute("system_overrides", "System Overrides", Icons.Default.Security)
        val DirectChat = ReGenesisRoute("direct_chat", "Direct Chat", Icons.Default.Hub)
        val ModuleCreation =
            ReGenesisRoute("module_creation", "Module Creation", Icons.Default.Bolt)
        val SovereignModuleManager =
            ReGenesisRoute("sovereign_module_manager", "Module Manager", Icons.Default.Storage)
        val GenderSelection =
            ReGenesisRoute("gender_selection", "Gender Selection", Icons.Default.Hub)
        val UserPreferences =
            ReGenesisRoute("user_preferences", "User Preferences", Icons.Default.Storage)
        val ModuleStorage =
            ReGenesisRoute("module_storage", "Module Storage", Icons.Default.Storage)
        val SentientShell = ReGenesisRoute("sentient_shell", "Sentient Shell", Icons.Default.Hub)
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

// Helper to create non-sealed route instances for companion
private fun ReGenesisRoute(route: String, title: String, icon: ImageVector): ReGenesisRoute {
    return object : ReGenesisRoute(route, title, icon) {}
}
