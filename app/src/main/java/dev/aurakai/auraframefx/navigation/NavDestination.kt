package dev.aurakai.auraframefx.navigation

/**
 * 🌐 AURAKAI NAVIGATION DESTINATIONS
 *
 * CLEAN HIERARCHY:
 *
 * LEVEL 1: ExodusHUD (5 Gates)
 * ├── Aura's Studio → AuraThemingHub
 * ├── Sentinel's Fortress → RomToolsHub
 * ├── Oracle Drive → OracleDriveHub
 * ├── Agent Nexus → AgentNexusHub
 * └── Help Services → HelpDesk
 *
 * LEVEL 2: Domain Hubs (5 main hubs)
 * └── Each hub contains tool screens
 *
 * LEVEL 3: Tool Screens (functional screens within each domain)
 */
sealed class NavDestination(val route: String) {

    // ═══════════════════════════════════════════════════════════════
    // LEVEL 1: ROOT - ExodusHUD (The 5 Gate Carousel)
    // ═══════════════════════════════════════════════════════════════
    data object HomeGateCarousel : NavDestination("home_gate_carousel")

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

    // ═══════════════════════════════════════════════════════════════
    // LEVEL 3: KAI'S TOOLS (Security & System)
    // ═══════════════════════════════════════════════════════════════
    data object Bootloader : NavDestination("bootloader")
    data object RootTools : NavDestination("root_tools")
    data object ROMFlasher : NavDestination("rom_flasher")
    data object LiveROMEditor : NavDestination("live_rom_editor")
    data object RecoveryTools : NavDestination("recovery_tools")
    data object LSPosedHub : NavDestination("lsposed_hub")
    data object LSPosedModules : NavDestination("lsposed_modules")
    data object HookManager : NavDestination("hook_manager")
    data object SystemOverrides : NavDestination("system_overrides")
    data object ModuleManager : NavDestination("module_manager")
    data object SecurityCenter : NavDestination("security_center")
    data object VPN : NavDestination("vpn")

    // ═══════════════════════════════════════════════════════════════
    // LEVEL 3: GENESIS'S TOOLS (AI & Orchestration)
    // ═══════════════════════════════════════════════════════════════
    data object CodeAssist : NavDestination("code_assist")
    data object NeuralNetwork : NavDestination("neural_network")
    data object Terminal : NavDestination("terminal")
    data object OracleCloudStorage : NavDestination("oracle_cloud_storage")
    data object AgentBridgeHub : NavDestination("agent_bridge_hub")
    data object ConferenceRoom : NavDestination("conference_room")

    // ═══════════════════════════════════════════════════════════════
    // LEVEL 3: AGENT NEXUS TOOLS (Multi-Agent Coordination)
    // ═══════════════════════════════════════════════════════════════
    data object Constellation : NavDestination("constellation")
    data object AgentMonitoring : NavDestination("agent_monitoring")
    data object SphereGrid : NavDestination("sphere_grid")
    data object EvolutionTree : NavDestination("evolution_tree")
    data object FusionMode : NavDestination("fusion_mode")
    data object TaskAssignment : NavDestination("task_assignment")
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
    data object InterfaceForge : NavDestination("interface_forge")
    data object ModuleCreation : NavDestination("module_creation")
    data object AgentCreation : NavDestination("agent_creation")
}
