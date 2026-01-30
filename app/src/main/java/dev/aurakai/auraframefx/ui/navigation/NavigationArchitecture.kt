package dev.aurakai.auraframefx.ui.navigation

/**
 * 🗺️ REGENESIS NAVIGATION ARCHITECTURE
 *
 * ════════════════════════════════════════════════════════════════════════════
 * LVL 1: CAROUSEL - Main Domain Gates (ExodusHUD)
 * ════════════════════════════════════════════════════════════════════════════
 * 5 swipeable domain gates, each themed uniquely:
 *
 * 1. SENTINEL'S FORTRESS (Kai)
 *    Theme: Shield protector, bulky, gridy, strong, protective
 *    Colors: Orange/Red shields, dark backgrounds
 *    Style A: Pixel Fortress (throne room)
 *    Style B: Cyber Security (matrix/lightning)
 *
 * 2. UXUI DESIGN STUDIO (Aura)
 *    Theme: Artsy, fun, messy, creative chaos
 *    Colors: Pink/Cyan/Magenta paint splashes
 *    Style A: CollabCanvas (paint splatter)
 *    Style B: Clean Studio (minimal)
 *
 * 3. ORACLEDRIVE (Genesis)
 *    Theme: Godly, ethereal, shiny, circuit-sprite
 *    Colors: Green circuits, glowing nodes
 *    Style A: Phoenix (ethereal wings)
 *    Style B: Sprite (pixel circuit creature)
 *
 * 4. AGENT NEXUS
 *    Theme: Constellation, connected agents
 *    Colors: Purple/violet, star patterns
 *
 * 5. HELP SERVICES
 *    Theme: Friendly, supportive
 *    Colors: Green accents
 *
 * ════════════════════════════════════════════════════════════════════════════
 * LVL 2: CAROUSEL - Sub-Gates (Inside each domain hub)
 * ════════════════════════════════════════════════════════════════════════════
 * Each domain has a carousel of sub-gates with domain-specific imagery.
 * Each sub-gate leads to a FULL MENU (LVL 3), not just a simple screen.
 *
 * KAI'S SUB-GATES:
 * - Bootloader (full menu with lock/unlock, fastboot, etc.)
 * - ROM Flasher (full menu with partition management)
 * - Root Tools (full menu with su, magisk, etc.)
 * - VPN/AdBlock (full menu with network tools)
 * - LSPosed Hub (full menu with modules, hooks)
 * - Security Center (full menu with firewall, privacy)
 *
 * AURA'S SUB-GATES:
 * - CollabCanvas (FULL SEPARATE MENU - design collaboration)
 * - ChromaCore (FULL SEPARATE MENU - color engine)
 * - Aura's Lab (FULL SEPARATE MENU - sandbox testing)
 * - Theme Engine (FULL SEPARATE MENU - visual styles)
 * - UXUI Mods (FULL SEPARATE MENU - system UI tweaks)
 * - Notch Bar, Status Bar, Quick Settings
 *
 * Note: CollabCanvas & ChromaCore can EXPORT into UXUI with imports!
 *
 * GENESIS SUB-GATES:
 * - Code Assist (AI logic injection)
 * - Conference Room (multi-agent orchestration)
 * - Neural Archive (memory & vectors)
 * - Agent Bridge (data vein hub)
 * - Terminal (sentient shell)
 * - Agent Creation (new agent synthesis)
 *
 * NEXUS SUB-GATES:
 * - Constellation (visual agent map)
 * - Sphere Grid (skill trees, XP)
 * - Monitoring HUD (real-time status)
 * - Fusion Mode (protocol blending)
 * - Task Assignment (action queues)
 *
 * ════════════════════════════════════════════════════════════════════════════
 * LVL 3: GRID MENUS - Full Functional Screens
 * ════════════════════════════════════════════════════════════════════════════
 * NOT a carousel! GRID style with:
 * - Menu name to click
 * - Themed background design (colorful for ChromaCore, abstract art, etc.)
 * - Each item is a clickable card that opens the actual tool
 *
 * Example: ChromaCore LVL 3 Grid
 * - Monet Colors
 * - Accent Picker
 * - Wallpaper Extract
 * - Custom Palette
 * - System Override
 * (all on a colorful, abstract background)
 *
 * Example: Bootloader LVL 3 Grid
 * - Lock Bootloader
 * - Unlock Bootloader
 * - Fastboot Tools
 * - OEM Unlock Status
 * - AVB Verify
 * (all on a shield/security themed background)
 */
object NavigationArchitecture {

    const val LEVEL_1 = "CAROUSEL"      // ExodusHUD - 5 domain gates
    const val LEVEL_2 = "CAROUSEL"      // Domain hub - sub-gate carousel
    const val LEVEL_3 = "GRID"          // Full menu - grid of tools

    // Domain identifiers
    const val DOMAIN_KAI = "sentinel_fortress"
    const val DOMAIN_AURA = "uxui_design_studio"
    const val DOMAIN_GENESIS = "oracle_drive"
    const val DOMAIN_NEXUS = "agent_nexus"
    const val DOMAIN_HELP = "help_services"
}
