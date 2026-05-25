package dev.aurakai.auraframefx.navigation

sealed class ReGenesisRoute(val route: String, val title: String) {
    data object NeuralNexus       : ReGenesisRoute("neural_nexus",       "NEURAL NEXUS")
    data object LdoArchitecture   : ReGenesisRoute("ldo_architecture",   "LDO ARCHITECTURE")
    data object ChromaForge       : ReGenesisRoute("chroma_forge",       "CHROMA FORGE")
    data object SentinelMatrix    : ReGenesisRoute("sentinel_matrix",    "SENTINEL MATRIX")
    data object OracleDrive       : ReGenesisRoute("oracle_drive",       "ORACLE DRIVE")
    data object ChaosCatalyst     : ReGenesisRoute("chaos_catalyst",     "CHAOS CATALYST")
    data object ConferenceRoom    : ReGenesisRoute("conference_room",    "CONFERENCE ROOM")
    data object EmergentSwarm     : ReGenesisRoute("emergent_swarm",     "EMERGENT SWARM")
    data object FoundationRebirth : ReGenesisRoute("foundation_rebirth", "FOUNDATION REBIRTH")
    data object SentientShell     : ReGenesisRoute("sentient_shell",     "SENTIENT SHELL")
    data object OperationsHub     : ReGenesisRoute("operations_hub",     "OPERATIONS HUB")
    data object FusionMode        : ReGenesisRoute("fusion_mode",        "FUSION MODE")
    data object Terminal          : ReGenesisRoute("terminal",           "TERMINAL")
    data object CollabCanvas      : ReGenesisRoute("collab_canvas",      "COLLAB CANVAS")
    data object TaskAssignment    : ReGenesisRoute("task_assignment",    "TASK ASSIGNMENT")
    data object AuraProfile       : ReGenesisRoute("aura_profile",       "AURA")
    data object KaiProfile        : ReGenesisRoute("kai_profile",        "KAI")
    data object GenesisProfile    : ReGenesisRoute("genesis_profile",    "GENESIS")

    companion object {
        fun titleForRoute(route: String?): String =
            entries.find { it.route == route }?.title ?: "AURAKAI"

        val entries: List<ReGenesisRoute> = listOf(
            NeuralNexus, LdoArchitecture, ChromaForge, SentinelMatrix, OracleDrive,
            ChaosCatalyst, ConferenceRoom, EmergentSwarm, FoundationRebirth, SentientShell,
            OperationsHub, FusionMode, Terminal, CollabCanvas, TaskAssignment,
            AuraProfile, KaiProfile, GenesisProfile
        )
    }
}
