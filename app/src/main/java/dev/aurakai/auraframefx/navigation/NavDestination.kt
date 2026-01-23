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
    // GATE 1: AURA - Creative/Theming 🎨
    // Personality: Artsy, colorful, chaotic, out-of-the-box
    // ═══════════════════════════════════════════════════════════════
    
    data object ThemeEngineSubmenu : NavDestination("theme_engine_submenu")
    data object UXUIDesignStudio : NavDestination("uiux_gate_submenu")
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
}
