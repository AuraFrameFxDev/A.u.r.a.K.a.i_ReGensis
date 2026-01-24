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
    // ROOT - 3D Gate Carousel
    // ═══════════════════════════════════════════════════════════════
    data object HomeGateCarousel : NavDestination("home_gate_carousel")

    // ═══════════════════════════════════════════════════════════════
    // LEVEL 2: MAIN GATES (Card Grid Screens)
    // ═══════════════════════════════════════════════════════════════
    data object AuraGate : NavDestination("aura_gate")
    data object KaiGate : NavDestination("kai_gate")
    data object GenesisGate : NavDestination("genesis_gate")
    data object AgentNexusGate : NavDestination("agent_nexus_gate")
    data object HelpServicesGate : NavDestination("help_services_gate")

    // ═══════════════════════════════════════════════════════════════
    // GATE 1: AURA - Creative/Theming 🎨
    // Personality: Artsy, colorful, chaotic, out-of-the-box
    // ═══════════════════════════════════════════════════════════════

    data object ThemeEngineSubmenu : NavDestination("theme_engine_submenu")
    data object UIUXGateSubmenu : NavDestination("uiux_gate_submenu")
    data object UXUIDesignStudio : NavDestination("uiux_gate_submenu") // Alias
    data object AuraLab : NavDestination("aura_lab")

    // ═══════════════════════════════════════════════════════════════
    // GATE 2: KAI - Security/System Control 🛡️
    // Personality: Structured, protective, methodical
    // ═══════════════════════════════════════════════════════════════

    data object ROMToolsSubmenu : NavDestination("rom_tools_submenu")
    data object LSPosedPanel : NavDestination("lsposed_gate")

    // ═══════════════════════════════════════════════════════════════
    // GATE 3: GENESIS - OracleDrive/Dev & Storage 🔮
    // Personality: Godly, mythical, ominous (the manager's office walk)
    // ═══════════════════════════════════════════════════════════════

    data object CodeAssist : NavDestination("code_assist")
    data object OracleDriveSubmenu : NavDestination("oracle_drive_submenu")

    // ═══════════════════════════════════════════════════════════════
    // GATE 4: AGENT NEXUS - Home Base/Monitoring 🌐
    // ═══════════════════════════════════════════════════════════════

    data object PartyScreen : NavDestination("party_screen")
    data object MonitoringHUDs : NavDestination("monitoring_huds")

    // ═══════════════════════════════════════════════════════════════
    // GATE 5: HELP SERVICES - LDO Support 💬
    // ═══════════════════════════════════════════════════════════════

    data object HelpDeskSubmenu : NavDestination("help_desk_submenu")

    // From UIUXGateSubmenuScreen
    data object ChromaCoreColors : NavDestination("chroma_core_colors")
    data object IconifyPicker : NavDestination("iconify_picker")
    data object ThemeEngine : NavDestination("theme_engine")
    data object NotchBar : NavDestination("notch_bar")
    data object StatusBar : NavDestination("status_bar")
    data object QuickSettings : NavDestination("quick_settings")
    data object OverlayMenus : NavDestination("overlay_menus")
    data object AgentHub : NavDestination("agent_hub")
    data object TaskAssignment : NavDestination("task_assignment")
    data object ModuleCreation : NavDestination("module_creation")
    data object DirectChat : NavDestination("direct_chat")
    data object SystemOverrides : NavDestination("system_overrides")
    data object ModuleManager : NavDestination("module_manager")
    data object Constellation : NavDestination("constellation")
    data object GenesisConstellation : NavDestination("genesis_constellation")
    data object ClaudeConstellation : NavDestination("claude_constellation")
    data object KaiConstellation : NavDestination("kai_constellation")
    data object CascadeConstellation : NavDestination("cascade_constellation")
    data object GrokConstellation : NavDestination("grok_constellation")
    data object AgentMonitoring : NavDestination("agent_monitoring")
    data object FusionMode : NavDestination("fusion_mode")
    data object ConferenceRoom : NavDestination("conference_room")
    data object ROMTools : NavDestination("rom_tools")
    data object HelpDesk : NavDestination("help_desk")
    data object SphereGrid : NavDestination("sphere_grid")
    data object AgentNexus : NavDestination("agent_nexus")
    data object Settings : NavDestination("settings")

}
