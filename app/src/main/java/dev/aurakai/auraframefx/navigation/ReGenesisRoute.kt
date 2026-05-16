package dev.aurakai.auraframefx.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
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

    // MISSING ROUTES FOR COMPATIBILITY
    data object AgentNexusHub :
        ReGenesisRoute("agent_nexus_hub", "Agent Nexus Hub", Icons.Default.Hub)

    data object AuraThemingHub :
        ReGenesisRoute("aura_theming_hub", "Aura Theming Hub", Icons.Default.Palette)

    data object AuraChat : ReGenesisRoute("aura_chat", "Aura Chat", Icons.Default.AutoAwesome)
    data object IconifyHub : ReGenesisRoute("iconify_hub", "Iconify Hub", Icons.Default.Palette)
    data object ReGenesisCustomization :
        ReGenesisRoute("customization", "Customization", Icons.Default.Settings)

    data object UserPreferences :
        ReGenesisRoute("user_preferences", "User Preferences", Icons.Default.Settings)

    data object ModuleCreation :
        ReGenesisRoute("module_creation", "Module Creation", Icons.Default.Build)

    data object DirectChat : ReGenesisRoute("direct_chat", "Direct Chat", Icons.Default.AutoAwesome)
    data object SystemOverrides :
        ReGenesisRoute("system_overrides", "System Overrides", Icons.Default.Security)

    data object OracleCloudInfinite :
        ReGenesisRoute("oracle_cloud_infinite", "Oracle Cloud Infinite", Icons.Default.Storage)

    data object AgentBridgeHub :
        ReGenesisRoute("agent_bridge_hub", "Agent Bridge Hub", Icons.Default.Hub)

    data object SovereignModuleManager :
        ReGenesisRoute("module_manager", "Module Manager", Icons.Default.Build)

    data object MonitoringHUDs :
        ReGenesisRoute("monitoring_huds", "Monitoring HUDs", Icons.Default.Dashboard)

    data object SecurityCenter :
        ReGenesisRoute("security_center", "Security Center", Icons.Default.Security)

    data object SovereignRecovery :
        ReGenesisRoute("sovereign_recovery", "Sovereign Recovery", Icons.Default.Security)

    data object LsposedQuickToggles :
        ReGenesisRoute("lsposed_toggles", "Lsposed Toggles", Icons.Default.Settings)

    data object GenderSelection :
        ReGenesisRoute("gender_selection", "Gender Selection", Icons.Default.Person)

    data object SentientShell :
        ReGenesisRoute("sentient_shell", "Sentient Shell", Icons.Default.AutoAwesome)

    data object operations : ReGenesisRoute("operations", "Operations", Icons.Default.Build)
    data object SystemJournal :
        ReGenesisRoute("system_journal", "System Journal", Icons.Default.Dashboard)

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
