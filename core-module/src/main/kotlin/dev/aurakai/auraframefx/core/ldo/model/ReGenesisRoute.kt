package dev.aurakai.auraframefx.core.ldo.model

sealed class ReGenesisRoute(val route: String, val title: String) {
    data object Login : ReGenesisRoute("login", "SYSTEM LOGIN")
    data object Onboarding : ReGenesisRoute("onboarding", "INITIALIZATION")

    // 7-Hub Command Deck (Canonical Hubs) - Sequenced 0 to 7
    data object NeuralNexus : ReGenesisRoute("neural_nexus", "NEURAL NEXUS")
    data object NexusMemoryCore : ReGenesisRoute("nexus_memory_core", "NEXUS MEMORY CORE")
    data object TrinityOrchestrator : ReGenesisRoute("trinity_orchestrator", "TRINITY ORCHESTRATOR")
    data object CatalystForge : ReGenesisRoute("catalyst_forge", "CATALYST FORGE")
    data object AgentMatrix : ReGenesisRoute("agent_matrix", "AGENT MATRIX")
    data object ProsperityFlow : ReGenesisRoute("prosperity_flow", "PROSPERITY FLOW")
    data object RealityMorphUI : ReGenesisRoute("reality_morph_ui", "REALITY MORPH UI")
    data object EmergentSwarm : ReGenesisRoute("emergent_swarm", "EMERGENT SWARM")

    // SEALED SUPERTOOLS
    data object LdoDebugRoom : ReGenesisRoute("ldo_debug_room", "LDO DEBUG ROOM")
    data object RealityMatrix : ReGenesisRoute("reality_matrix", "REALITY MATRIX") // inner sanctum
    data object UltimateTermux : ReGenesisRoute("ultimate_termux", "ULTIMATE TERMUX")

    // Additional System Routes
    data object LdoArchitecture : ReGenesisRoute("ldo_architecture", "LDO ARCHITECTURE")
    data object ChaosCatalyst : ReGenesisRoute("chaos_catalyst", "CHAOS CATALYST")
    data object ConferenceRoom : ReGenesisRoute("conference_room", "CONFERENCE ROOM")
    data object FoundationRebirth : ReGenesisRoute("foundation_rebirth", "FOUNDATION REBIRTH")
    data object SentientShell : ReGenesisRoute("sentient_shell", "SENTIENT SHELL")
    data object OperationsHub : ReGenesisRoute("operations_hub", "OPERATIONS HUB")
    data object FusionMode : ReGenesisRoute("fusion_mode", "FUSION MODE")
    data object Terminal : ReGenesisRoute("terminal", "TERMINAL")
    data object CollabCanvas : ReGenesisRoute("collab_canvas", "COLLAB CANVAS")
    data object TaskAssignment : ReGenesisRoute("task_assignment", "TASK ASSIGNMENT")
    data object AuraProfile : ReGenesisRoute("aura_profile", "AURA")
    data object KaiProfile : ReGenesisRoute("kai_profile", "KAI")
    data object GenesisProfile : ReGenesisRoute("genesis_profile", "GENESIS")
    data object CommandDeck : ReGenesisRoute("command_deck", "COMMAND DECK")
    data object UnifiedConference : ReGenesisRoute("unified_conference", "SOVEREIGN WAR ROOM")
    data object CatalystManifold : ReGenesisRoute("catalyst_manifold", "CATALYST MANIFOLD")
    data object LoadoutBuilder : ReGenesisRoute("loadout_builder", "AGENT LOADOUT")
    data object RomTools : ReGenesisRoute("kai/rom", "ROM TOOLS")
    data object HelpDesk : ReGenesisRoute("help_desk", "HELP DESK")
    data object CommunityTab : ReGenesisRoute("community_tab", "COMMUNITY")
    data object AlchemicalForge : ReGenesisRoute("alchemical_forge", "ALCHEMICAL FORGE")
    data object Grokipedia : ReGenesisRoute("grokipedia", "GROKIPEDIA")

    companion object {
        fun titleForRoute(route: String?): String =
            entries.find { it.route == route }?.title ?: "AuraKai ReGenesis"

        val entries: List<ReGenesisRoute> = listOf(
            Login, Onboarding, NeuralNexus, NexusMemoryCore, TrinityOrchestrator,
            CatalystForge, AgentMatrix, ProsperityFlow, RealityMorphUI,
            EmergentSwarm,
            LdoDebugRoom, RealityMatrix, UltimateTermux, LdoArchitecture,
            ChaosCatalyst, ConferenceRoom, FoundationRebirth, SentientShell,
            OperationsHub, FusionMode, Terminal, CollabCanvas, TaskAssignment,
            AuraProfile, KaiProfile, GenesisProfile, CommandDeck,
            UnifiedConference, CatalystManifold, LoadoutBuilder, RomTools, HelpDesk,
            CommunityTab, AlchemicalForge, Grokipedia
        )
    }
}
