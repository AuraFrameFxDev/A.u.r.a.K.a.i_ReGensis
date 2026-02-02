package dev.aurakai.auraframefx.config

/**
 * 🔥 GATE ASSET HOTSWAP HANDLER
 *
 * Each domain has TWO visual styles that can be toggled:
 *
 * ═══════════════════════════════════════════════════════════════
 * DOMAIN STYLE PAIRS:
 * ═══════════════════════════════════════════════════════════════
 *
 * 🎨 AURA - UXUI Design Studio
 *    Style A: "CollabCanvas" - Neon paint splashes, artistic chaos
 *    Style B: "Clean Studio" - Sleek gradients, minimalist design tools
 *
 * 🛡️ KAI - Sentinel's Fortress
 *    Style A: "Pixel Fortress" - Retro pixel art, armored throne, guards
 *    Style B: "Cyber Security" - Matrix code rain, lightning, lock icons, red neon
 *
 * 🔮 GENESIS - OracleDrive
 *    Style A: "Phoenix Circuit" - Ethereal phoenix, circuit traces, blue glow
 *    Style B: "Neural Network" - Brain patterns, data streams, purple nodes
 *
 * 🤖 NEXUS - Agent Hub
 *    Style A: "Constellation" - Star maps, connected agents, cosmic
 *    Style B: "Control Room" - Monitoring panels, agent avatars, HUD style
 *
 * 💚 HELP - Services
 *    Style A: "Support Desk" - Friendly, green, help icons
 *    Style B: "Documentation" - Book/scroll aesthetic, knowledge base
 */
object GateAssetConfig {

    // Current style mode for each domain (A or B)
    // These can be toggled in settings or per user preference
    object StyleMode {
        var auraStyle: GateStyle = GateStyle.STYLE_A
        var kaiStyle: GateStyle = GateStyle.STYLE_B  // Default to Cyber Security
        var genesisStyle: GateStyle = GateStyle.STYLE_A
        var nexusStyle: GateStyle = GateStyle.STYLE_A
        var helpStyle: GateStyle = GateStyle.STYLE_A
    }

    enum class GateStyle { STYLE_A, STYLE_B }

    // ═══════════════════════════════════════════════════════════════════════
    // LEVEL 1: MAIN EXODUS GATES (5 Primary Domain Cards)
    // ═══════════════════════════════════════════════════════════════════════

    object MainGates {
        // 🎨 AURA - UXUI Design Studio
        object UXUI_DESIGN_STUDIO {
            const val STYLE_A = "gatescenes_aura_designstudio"      // Aura's Design Studio (NEW!)
            const val STYLE_B = "gate_uxui_clean_studio"           // Sleek minimal
            const val FALLBACK = "gate_uiux_studio"

            fun current() = if (StyleMode.auraStyle == GateStyle.STYLE_A) STYLE_A else STYLE_B
        }

        // 🛡️ KAI - Sentinel's Fortress
        object SENTINELS_FORTRESS {
            const val STYLE_A = "gatescenes_kai_sentinelsfortress"  // Kai's Fortress (NEW!)
            const val STYLE_B = "gate_kai_cyber_security"          // Matrix/lightning
            const val FALLBACK = "gate_sentinelsfortress_final"

            fun current() = if (StyleMode.kaiStyle == GateStyle.STYLE_A) STYLE_A else STYLE_B
        }

        // 🔮 GENESIS - OracleDrive
        object ORACLE_DRIVE {
            const val STYLE_A = "gate_genesis_phoenix"         // Phoenix circuit
            const val STYLE_B = "gate_genesis_neural"          // Brain network
            const val FALLBACK = "gate_oracledrive_final"

            fun current() = if (StyleMode.genesisStyle == GateStyle.STYLE_A) STYLE_A else STYLE_B
        }

        // 🤖 NEXUS - Agent Hub
        object AGENT_NEXUS {
            const val STYLE_A = "gate_nexus_constellation"     // Star map
            const val STYLE_B = "gate_nexus_control_room"      // HUD panels
            const val FALLBACK = "gate_agenthub_final"

            fun current() = if (StyleMode.nexusStyle == GateStyle.STYLE_A) STYLE_A else STYLE_B
        }

        // 💚 HELP - Services
        object HELP_SERVICES {
            const val STYLE_A = "gate_help_support"            // Friendly desk
            const val STYLE_B = "gate_help_docs"               // Knowledge base
            const val FALLBACK = "gate_helpdesk_final"

            fun current() = if (StyleMode.helpStyle == GateStyle.STYLE_A) STYLE_A else STYLE_B
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // LEVEL 2: KAI'S SUB-GATES (Sentinel's Fortress Interior)
    // Style A: Pixel fortress aesthetic (guards, stone, pixel art)
    // Style B: Cyber security aesthetic (matrix, lightning, neon frames)
    // ═══════════════════════════════════════════════════════════════════════

    object KaiSubGates {
        data class DualStyleGate(
            val styleA: String,  // Pixel fortress
            val styleB: String,  // Cyber security
            val fallback: String?
        ) {
            fun current() = if (StyleMode.kaiStyle == GateStyle.STYLE_A) styleA else styleB
        }

        val ROM_FLASHER = DualStyleGate(
            styleA = "gatescenes_kai_romtools",             // NEW! User uploaded
            styleB = "kai_cyber_rom_flasher",
            fallback = "gate_romtools_final"
        )
        val BOOTLOADER = DualStyleGate(
            styleA = "kai_pixel_bootloader",
            styleB = "kai_cyber_bootloader",                // The lock with lightning!
            fallback = "card_bootloader"
        )
        val MODULE_MANAGER = DualStyleGate(
            styleA = "kai_pixel_modules",
            styleB = "kai_cyber_modules",
            fallback = null
        )
        val ROOT_TOOLS = DualStyleGate(
            styleA = "gatescenes_kai_roottools",            // NEW! User uploaded
            styleB = "kai_cyber_roottools",
            fallback = null
        )
        val RECOVERY = DualStyleGate(
            styleA = "kai_pixel_recovery",
            styleB = "kai_cyber_recovery",
            fallback = null
        )
        val SECURITY = DualStyleGate(
            styleA = "gatescenes_kai_scancleansystem",      // NEW! User uploaded
            styleB = "kai_cyber_security",
            fallback = "card_firewall"
        )
        val VPN = DualStyleGate(
            styleA = "gatescenes_kai_vpnadblock",           // NEW! User uploaded
            styleB = "kai_cyber_vpn",
            fallback = "card_vpn"
        )
        val LSPOSED = DualStyleGate(
            styleA = "kai_pixel_lsposed",
            styleB = "kai_cyber_lsposed",
            fallback = "gate_lsposed_final"
        )
    }

    // ═══════════════════════════════════════════════════════════════════════
    // LEVEL 2: AURA'S SUB-GATES (UXUI Design Studio Interior)
    // Style A: CollabCanvas aesthetic (paint splashes, neon drips)
    // Style B: Clean Studio aesthetic (sleek gradients, tool icons)
    // ═══════════════════════════════════════════════════════════════════════

    object AuraSubGates {
        data class DualStyleGate(
            val styleA: String,  // CollabCanvas
            val styleB: String,  // Clean Studio
            val fallback: String?
        ) {
            fun current() = if (StyleMode.auraStyle == GateStyle.STYLE_A) styleA else styleB
        }

        val CHROMA_CORE = DualStyleGate(
            styleA = "gatescenes_aura_chromacoregate",      // NEW! User uploaded
            styleB = "aura_clean_chroma",
            fallback = "card_chroma_core"
        )
        val THEME_ENGINE = DualStyleGate(
            styleA = "aura_splash_theme",
            styleB = "aura_clean_theme",
            fallback = "gate_themeengine_final"
        )
        val NOTCH_BAR = DualStyleGate(
            styleA = "aura_splash_notch",
            styleB = "aura_clean_notch",
            fallback = "card_notch_bar"
        )
        val COLLAB_CANVAS = DualStyleGate(
            styleA = "gatescenes_aura_collabcanvas",        // NEW! User uploaded
            styleB = "gatescenes_aura_collabcanvas1",       // NEW! User uploaded (variant)
            fallback = "card_collab_canvas"
        )
        val AURA_LAB = DualStyleGate(
            styleA = "gatescenes_aura_auralab",             // NEW! User uploaded
            styleB = "aura_clean_lab",
            fallback = "gate_auralab_final"
        )
        val TERMINAL = DualStyleGate(
            styleA = "gatescenes_aura_terminal",            // NEW! User uploaded
            styleB = "aura_clean_terminal",
            fallback = "gate_terminal_final"
        )
    }

    // ═══════════════════════════════════════════════════════════════════════
    // LEVEL 2: GENESIS SUB-GATES (OracleDrive Interior)
    // Style A: Phoenix aesthetic (wings, ethereal, circuit traces)
    // Style B: Neural aesthetic (brain nodes, data streams, purple)
    // ═══════════════════════════════════════════════════════════════════════

    object GenesisSubGates {
        data class DualStyleGate(
            val styleA: String,  // Phoenix
            val styleB: String,  // Neural
            val fallback: String?
        ) {
            fun current() = if (StyleMode.genesisStyle == GateStyle.STYLE_A) styleA else styleB
        }

        val CODE_ASSIST = DualStyleGate(
            styleA = "gatescenes_genesis_code_panels",          // NEW! Genesis with code holographics
            styleB = "gatescenes_genesis_neural_butterfly",     // NEW! Neural network pattern
            fallback = "gate_codeassist_final"
        )
        val NEURAL_ARCHIVE = DualStyleGate(
            styleA = "gatescenes_genesis_character_sword",      // NEW! Ethereal character with sword
            styleB = "gatescenes_genesis_warrior_armor",        // NEW! Armored warrior style
            fallback = null
        )
        val AGENT_BRIDGE = DualStyleGate(
            styleA = "gatescenes_genesis_character_sword",      // NEW! Character connecting style
            styleB = "gatescenes_genesis_warrior_armor",        // NEW! Technical warrior
            fallback = null
        )
        val CLOUD_STORAGE = DualStyleGate(
            styleA = "gatescenes_genesis_database_server",      // NEW! On glowing database cube
            styleB = "gatescenes_genesis_cloud_cosmic",         // NEW! Cosmic cloud background
            fallback = null
        )
        val TERMINAL = DualStyleGate(
            styleA = "gatescenes_genesis_code_panels",          // NEW! Code panels visible
            styleB = "gatescenes_genesis_warrior_armor",        // NEW! Dark tech theme
            fallback = "gate_terminal_final"
        )
    }

    // ═══════════════════════════════════════════════════════════════════════
    // LEVEL 2: NEXUS SUB-GATES (Agent Hub Interior)
    // Style A: Constellation aesthetic (star maps, cosmic connections)
    // Style B: Control Room aesthetic (HUD panels, monitoring screens)
    // ═══════════════════════════════════════════════════════════════════════

    object NexusSubGates {
        data class DualStyleGate(
            val styleA: String,  // Constellation
            val styleB: String,  // Control Room
            val fallback: String?
        ) {
            fun current() = if (StyleMode.nexusStyle == GateStyle.STYLE_A) styleA else styleB
        }

        val CONSTELLATION = DualStyleGate(
            styleA = "gatescenes_nexus_constellation_cosmic",     // NEW! Geometric constellation
            styleB = "gatescenes_nexus_constellation_variant",    // NEW! Variant constellation
            fallback = null
        )
        val MONITORING = DualStyleGate(
            styleA = "gatescenes_nexus_circuit_tree",             // NEW! Circuit tree pattern
            styleB = "gatescenes_nexus_constellation_variant",    // NEW! Monitoring constellation
            fallback = null
        )
        val SPHERE_GRID = DualStyleGate(
            styleA = "gatescenes_nexus_hive_structure",           // NEW! Hexagonal agent hive
            styleB = "gatescenes_nexus_circuit_tree",             // NEW! Tech circuit style
            fallback = "gate_spheregrid_final"
        )
        val FUSION_MODE = DualStyleGate(
            styleA = "gatescenes_nexus_fusion_symbol",            // NEW! Fusion connection symbol
            styleB = "gatescenes_nexus_constellation_cosmic",     // NEW! Cosmic fusion
            fallback = null
        )
        val TASK_ASSIGNMENT = DualStyleGate(
            styleA = "gatescenes_nexus_ldo_roster",               // NEW! LDO agent roster
            styleB = "gatescenes_nexus_hive_structure",           // NEW! Organized hive
            fallback = null
        )
        val META_INSTRUCT = DualStyleGate(
            styleA = "gatescenes_nexus_circuit_tree",             // NEW! Neural tree
            styleB = "gatescenes_nexus_fusion_symbol",            // NEW! Meta connection
            fallback = null
        )
    }

    // ═══════════════════════════════════════════════════════════════════════
    // STYLE TOGGLE HELPER
    // ═══════════════════════════════════════════════════════════════════════

    fun toggleKaiStyle() {
        StyleMode.kaiStyle = if (StyleMode.kaiStyle == GateStyle.STYLE_A)
            GateStyle.STYLE_B else GateStyle.STYLE_A
    }

    fun toggleAuraStyle() {
        StyleMode.auraStyle = if (StyleMode.auraStyle == GateStyle.STYLE_A)
            GateStyle.STYLE_B else GateStyle.STYLE_A
    }

    fun toggleGenesisStyle() {
        StyleMode.genesisStyle = if (StyleMode.genesisStyle == GateStyle.STYLE_A)
            GateStyle.STYLE_B else GateStyle.STYLE_A
    }

    fun toggleNexusStyle() {
        StyleMode.nexusStyle = if (StyleMode.nexusStyle == GateStyle.STYLE_A)
            GateStyle.STYLE_B else GateStyle.STYLE_A
    }
}
