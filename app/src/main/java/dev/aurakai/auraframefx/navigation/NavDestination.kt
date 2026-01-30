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
    // LEVEL 2: DOMAIN HUBS (The 5 Main Domains)
    // ═══════════════════════════════════════════════════════════════

    // AURA'S DOMAIN: UX/UI Design Studio
    data object AuraThemingHub : NavDestination("aura_theming_hub")

    // KAI'S DOMAIN: Sentinel's Fortress
    data object RomToolsHub : NavDestination("rom_tools_hub")

    // GENESIS'S DOMAIN: Oracle Drive
    data object OracleDriveHub : NavDestination("oracle_drive_hub")

    // COLLECTIVE DOMAIN: Agent Nexus
    data object AgentNexusHub : NavDestination("agent_nexus_hub")

    // SUPPORT DOMAIN: Help Services
    data object HelpDesk : NavDestination("help_desk")

    // ═══════════════════════════════════════════════════════════════
    // LEVEL 3: AURA'S TOOLS (Design & Creativity)
    // ═══════════════════════════════════════════════════════════════
    data object ThemeEngine : NavDestination("theme_engine")
    data object ChromaCoreColors : NavDestination("chroma_core_colors")
    data object NotchBar : NavDestination("notch_bar")
    data object StatusBar : NavDestination("status_bar")
    data object QuickSettings : NavDestination("quick_settings")
    data object IconifyPicker : NavDestination("iconify_picker")
    data object CollabCanvas : NavDestination("collab_canvas")
    data object AuraLab : NavDestination("aura_lab")

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
    data object SystemToolsHub : NavDestination("system_tools_hub")

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

    // Individual Agent Screens
    data object GenesisAgent : NavDestination("genesis_agent")
    data object ClaudeAgent : NavDestination("claude_agent")
    data object KaiAgent : NavDestination("kai_agent")
    data object AuraAgent : NavDestination("aura_agent")
    data object CascadeAgent : NavDestination("cascade_agent")
    data object GrokAgent : NavDestination("grok_agent")
    data object NemotronAgent : NavDestination("nemotron_agent")
    data object GeminiAgent : NavDestination("gemini_agent")

    // ═══════════════════════════════════════════════════════════════
    // LEVEL 3: HELP SERVICES TOOLS
    // ═══════════════════════════════════════════════════════════════
    data object DirectChat : NavDestination("direct_chat")
    data object Documentation : NavDestination("documentation")
    data object FAQBrowser : NavDestination("faq_browser")
    data object TutorialVideos : NavDestination("tutorial_videos")

    // ═══════════════════════════════════════════════════════════════
    // UTILITY
    // ═══════════════════════════════════════════════════════════════
    data object Settings : NavDestination("settings")
    data object InterfaceForge : NavDestination("interface_forge")
    data object ModuleCreation : NavDestination("module_creation")
    data object AgentCreation : NavDestination("agent_creation")
}
