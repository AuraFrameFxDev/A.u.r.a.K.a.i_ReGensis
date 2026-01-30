package dev.aurakai.auraframefx.navigation

/**
 * 🌐 AURAKAI NAVIGATION DESTINATIONS
 *
 * Type-safe routing for the multi-gate architecture
 *
 * Gate Personalities:
 * - AURA: Artsy, colorful, wild creativity
 * - KAI: Structured, protective security
 * - GENESIS: Godly, mythical, ominous power
 * - NEXUS: Central hub, welcoming monitoring
 * - HELP: Clean, supportive, informative
 * - LSPOSED: Technical, matrix-style authority
 */
sealed class NavDestination(val route: String) {

    // ═══════════════════════════════════════════════════════════════
    // LEVEL 1: ROOT - 3D Carousel Gateway
    // ═══════════════════════════════════════════════════════════════
    data object HomeGateCarousel : NavDestination("home_gate_carousel")
    data object HoloProjector : NavDestination("holo_projector")

    // ═══════════════════════════════════════════════════════════════
    // LEVEL 2: MAIN GATES (The Card Selection Domain)
    // ═══════════════════════════════════════════════════════════════
    data object AuraGate : NavDestination("aura_gate")           // Reactive Design
    data object KaiGate : NavDestination("kai_gate")             // Sentinel Fortress
    data object GenesisGate : NavDestination("genesis_gate")     // Oracle Drive
    data object AgentNexusGate : NavDestination("agent_nexus_gate") // Nexus Hub
    data object HelpServicesGate : NavDestination("help_services_gate")

    // ═══════════════════════════════════════════════════════════════
    // LEVEL 3: DOMAIN HUBS (The Management Frameworks)
    // ═══════════════════════════════════════════════════════════════

    // AURA HUBS (Design & Creativity)
    data object AuraThemingHub : NavDestination("aura_theming_hub") // The Unified UI Engine
    data object AuraLab : NavDestination("aura_lab")                // Sandbox & Testing

    // KAI HUBS (Security & System Control)
    data object RomToolsHub : NavDestination("rom_tools_hub")      // Flashing & Partitions
    data object LSPosedHub : NavDestination("lsposed_hub")        // Module Management
    data object SystemToolsHub : NavDestination("system_tools_hub") // Logs & Journals

    // GENESIS HUBS (AI & Orchestration)
    data object OracleDriveHub : NavDestination("oracle_drive_hub") // AI & Storage
    data object OracleCloudStorage : NavDestination("oracle_cloud_storage") // Infinite Storage
    data object AgentBridgeHub : NavDestination("agent_bridge_hub") // Multi-Agent Datavein

    // AGENT NEXUS HUBS (Progression & Identity)
    data object ConstellationHub : NavDestination("constellation_hub") // Skill Trees
    data object MonitoringHub : NavDestination("monitoring_hub")       // Performance & Stats

    // ═══════════════════════════════════════════════════════════════
    // LEVEL 4: TOOL SCREENS (Individual Controllers)
    // ═══════════════════════════════════════════════════════════════

    // -- Aura Toolset --
    data object ThemeEngine : NavDestination("theme_engine")
    data object ChromaCoreColors : NavDestination("chroma_core_colors")
    data object NotchBar : NavDestination("notch_bar")
    data object StatusBar : NavDestination("status_bar")
    data object QuickSettings : NavDestination("quick_settings")
    data object IconifyPicker : NavDestination("iconify_picker")
    data object CollabCanvas : NavDestination("collab_canvas")
    data object ModuleCreation : NavDestination("module_creation")
    data object AgentCreation : NavDestination("agent_creation")

    // -- Kai Toolset --
    data object Bootloader : NavDestination("bootloader")
    data object RootTools : NavDestination("root_tools")
    data object ROMFlasher : NavDestination("rom_flasher")
    data object LiveROMEditor : NavDestination("live_rom_editor")
    data object RecoveryTools : NavDestination("recovery_tools")
    data object LSPosedModules : NavDestination("lsposed_modules")
    data object HookManager : NavDestination("hook_manager")
    data object SystemOverrides : NavDestination("system_overrides")
    data object ModuleManager : NavDestination("module_manager")
    data object SecurityCenter : NavDestination("security_center")
    data object VPN : NavDestination("vpn")

    // -- Genesis Toolset --
    data object CodeAssist : NavDestination("code_assist")
    data object NeuralNetwork : NavDestination("neural_network")
    data object Terminal : NavDestination("sentient_shell")

    // -- Nexus Toolset --
    data object GenesisConstellation : NavDestination("genesis_constellation")
    data object ClaudeConstellation : NavDestination("claude_constellation")
    data object KaiConstellation : NavDestination("kai_constellation")
    data object GrokConstellation : NavDestination("grok_constellation")
    data object CascadeConstellation : NavDestination("cascade_constellation")
    data object AgentMonitoring : NavDestination("monitoring_huds") // Renamed from "agent_monitoring"
    data object SphereGrid : NavDestination("sphere_grid")
    data object EvolutionTree : NavDestination("evolution_tree")
    data object FusionMode : NavDestination("fusion_mode")
    data object Constellation : NavDestination("constellation")
    data object TaskAssignment : NavDestination("task_assignment")
    data object PartyScreen : NavDestination("party_screen")
    data object ArkBuild : NavDestination("ark_build")
    data object MetaInstruct : NavDestination("meta_instruct")
    data object Nemotron : NavDestination("nemotron")
    data object CascadeVision : NavDestination("cascade_vision")
    data object Claude : NavDestination("claude")
    data object Gemini : NavDestination("gemini")
    data object SwarmMonitor : NavDestination("swarm_monitor")
    data object BenchmarkMonitor : NavDestination("benchmark_monitor")
    data object InterfaceForge : NavDestination("interface_forge")

    // -- Utility & Infrastructure --
    data object HelpDesk : NavDestination("help_desk")
    data object HelpDeskSubmenu : NavDestination("help_desk_submenu")
    data object DirectChat : NavDestination("direct_chat")
    data object Documentation : NavDestination("documentation")
    data object FAQBrowser : NavDestination("faq_browser")
    data object TutorialVideos : NavDestination("tutorial_videos")
    data object Settings : NavDestination("settings")
    data object ConferenceRoom : NavDestination("conference_room")

    // Legacy / To be deprecated
    data object ThemeEngineSubmenu : NavDestination("theme_engine_submenu")
    data object UIUXGateSubmenu : NavDestination("uiux_gate_submenu")
    data object UXUIDesignStudio : NavDestination("uiux_gate_submenu")
    data object ROMToolsSubmenu : NavDestination("rom_tools_submenu")
    data object LSPosedPanel : NavDestination("lsposed_gate")
    data object OracleDriveSubmenu : NavDestination("oracle_drive_submenu")
    data object LSPosedSubmenu : NavDestination("lsposed_submenu")
    data object AgentNexus : NavDestination("agent_nexus_gate")
    data object ROMTools : NavDestination("rom_tools_hub")
    data object AgentHub : NavDestination("agent_hub")
    companion object
}
