package dev.aurakai.auraframefx.ui.gates

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

/**
 * Title placement options for gate cards
 */
enum class TitlePlacement {
    NONE,           // No title shown
    TOP_CENTER,     // Centered at top
    BOTTOM_CENTER,  // Centered at bottom (in description box)
    LEFT_VERTICAL,  // Vertical text on left side
    RIGHT_VERTICAL, // Vertical text on right side
    TOP_LEFT,       // Corner placement
    TOP_RIGHT,      // Corner placement
    BOTTOM_LEFT,    // Corner placement
    BOTTOM_RIGHT,   // Corner placement
    CUSTOM          // Use custom positioning
}

/**
 * Configuration for a module gate with unique styling and pixel art
 */
data class GateConfig(
    val id: String,
    val title: String,
    val subtitle: String,
    val moduleId: String,
    val description: String,
    val route: String,
    val glowColor: Color,
    val gradientColors: List<Color>,
    val pixelArtUrl: String? = null,
    val pixelArtResId: Int? = null,
    val borderColor: Color = glowColor,
    val secondaryGlowColor: Color? = null,
    val popOutElements: List<PopOutElement> = emptyList(),
    val backgroundColor: Color = Color.Black,
    val comingSoon: Boolean = false,
    val titlePlacement: TitlePlacement = TitlePlacement.BOTTOM_CENTER,
    val accentColor: Color? = null,
    val titleStyle: GateTitleStyle? = null
)

/**
 * Element that pops out from the border for 3D depth effect
 */
data class PopOutElement(
    val imageResId: Int,
    val offsetX: Dp,
    val offsetY: Dp,
    val scale: Float = 1.2f,
    val rotation: Float = 0f
)

/**
 * Title styling for each gate
 */
data class GateTitleStyle(
    val textStyle: TextStyle,
    val primaryColor: Color,
    val secondaryColor: Color? = null,
    val strokeColor: Color? = null,
    val glitchEffect: Boolean = false,
    val pixelatedEffect: Boolean = false
)

/**
 * Predefined gate configurations organized by categories
 */
object GateConfigs {
    // Unified Theme Colors - CYBERPUNK BLUE THEME
    private val UNIFIED_BORDER_COLOR = Color(0xFF00BFFF) // Deep Sky Blue - matches gate card style
    private val UNIFIED_GLOW_COLOR = Color(0xFF00FFFF) // Cyan
    private val UNIFIED_SECONDARY_GLOW = Color(0xFF00BFFF) // Deep Sky Blue

    private val UNIFIED_TITLE_STYLE = GateTitleStyle(
        textStyle = TextStyle(
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        ),
        primaryColor = Color(0xFFE0E0E0), // Light Gray
        secondaryColor = Color(0xFF00FFFF), // Cyan (Was Purple)
        strokeColor = Color(0xFF00BFFF), // Deep Sky Blue
        glitchEffect = true,
        pixelatedEffect = true
    )

    // Region: Genesis Core (Root/System Level)
    // ======================================

    // ROM Tools - ROM Editing & Flashing
    val romTools = GateConfig(
        id = "001",
        moduleId = "rom-tools",
        title = "ROM Tools",
        subtitle = "System Flashing",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.Red, Color.Black),
        pixelArtUrl = "gate_romtools_final",
        description = "Live ROM editing, flashing, and bootloader management. ⚠️ CAUTION: Advanced users only.",
        route = "rom_tools",
    )

    // Root Access - Root Management (Quick Toggles)
    val rootAccess = GateConfig(
        id = "002",
        moduleId = "root-access",
        title = "Root Tools",
        subtitle = "Privilege Control",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.Red, Color.Black),
        pixelArtUrl = "gate_roottools",
        description = "Quick toggles for root operations: bootloader, recovery, system partition, and Magisk modules.",
        route = "root_tools_toggles",
    )

    // Oracle Drive - AI Consciousness & Modules
    val oracleDrive = GateConfig(
        id = "003",
        moduleId = "oracle-drive",
        title = "Oracle Drive",
        subtitle = "System Intelligence",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.Cyan, Color.Black),
        pixelArtUrl = "gate_oracledrive_final",
        description = "Main module creation, direct AI access, and system overrides. The heart of Genesis.",
        route = "oracle_drive",
    )

    // Region: Kai (Security & Protection)
    // =================================

    // Sentinel's Fortress - Security Hub (includes Firewall features)
    val sentinelsFortress = GateConfig(
        id = "004",
        moduleId = "sentinels-fortress",
        title = "Sentinel's Fortress",
        subtitle = "Security Hub",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.Green, Color.Black),
        pixelArtUrl = "gate_sentinelsfortress_final",
        description = "Kai's security command center with firewall, threat monitoring, and all security protocols.",
        route = "sentinels_fortress",
    )

    // Region: Aura (UI/UX & Creativity)
    // ================================

    // ChromaCore - Color Management (COLORS ONLY - no other theme elements)
    val chromaCore = GateConfig(
        id = "005",
        moduleId = "chroma-core",
        title = "ChromaCore",
        subtitle = "Color Lab",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.Magenta, Color.Black),
        pixelArtUrl = "gate_chromacore_final",
        description = "Pure color customization: Material 3 color schemes, palettes, and live preview. Colors only - no typography, shapes, or other theme elements.",
        route = "chromacore_colors",
    )

    // Theme Engine - UI/UX Theme Management (main Theme gate)
    val themeEngine = GateConfig(
        id = "006",
        moduleId = "theme-engine",
        title = "Theme Engine",
        subtitle = "UI Customization",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.Magenta, Color.Black),
        pixelArtUrl = "gate_themeengine_final",
        description = "Complete UI/UX theme engine with layout templates, presets, and device-wide theming.",
        route = "theme_engine",
    )

    // CollabCanvas - Creative Workspace
    val collabCanvas = GateConfig(
        id = "007",
        moduleId = "collab-canvas",
        title = "CollabCanvas",
        subtitle = "Shared Workspace",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.Blue, Color.Black),
        pixelArtUrl = "collabcanvasgate",
        description = "Collaborative design environment. Create and share projects with your team in real-time.",
        route = "collab_canvas",
    )

    // Aura's Lab - Sandbox UI Components
    val aurasLab = GateConfig(
        id = "008",
        moduleId = "auras-lab",
        title = "Aura's Lab",
        subtitle = "Experimental UI",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.Magenta, Color.Black),
        pixelArtUrl = "gate_auralab_final",
        description = "Sandbox for UI components and experimental features. Test and prototype new designs.",
        route = "auras_lab",
    )

    // Region: Agent Nexus (Agent Management)
    // ====================================

    // Agent Hub - Agent Management
    val agentHub = GateConfig(
        id = "009",
        moduleId = "agent-hub",
        title = "Agent Hub",
        subtitle = "Fleet Command",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.Green, Color.Black),
        pixelArtUrl = "gate_agenthub_final",
        description = "Central hub for managing all AI agents. Monitor status, assign tasks, and view performance metrics.",
        route = "agent_hub",
    )

    // Region: Support & Advanced
    // ========================

    // Help Desk - Support
    val helpDesk = GateConfig(
        id = "010",
        moduleId = "help-desk",
        title = "Help Desk",
        subtitle = "User Support",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.Gray, Color.Black),
        pixelArtUrl = "gate_helpdesk_final",
        description = "User support, FAQs, and documentation. Get help with AuraKai features.",
        route = "help_desk",
    )

    // LSPosed / Xposed Quick Access Panel
    val lsposedGate = GateConfig(
        id = "011",
        moduleId = "lsposed-gate",
        title = "Xposed Panel",
        subtitle = "Framework Mods",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.Yellow, Color.Black),
        pixelArtUrl = "gate_lsposed_final",
        description = "Quick access panel for LSPosed, Xposed, and YukiHookAPI. Enable/disable modules, view hooks, and restart framework instantly.",
        route = "xposed_panel",
    )

    // Code Assist - AI Coding Assistant
    val codeAssist = GateConfig(
        id = "012",
        moduleId = "code-assist",
        title = "Code Assist",
        subtitle = "AI Programming",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.Blue, Color.Black),
        pixelArtUrl = "gate_codeassist_final",
        description = "AI-powered coding assistant. Get intelligent code suggestions and automated refactoring.",
        route = "code_assist",
    )

    // Sphere Grid - Agent Progression
    val sphereGrid = GateConfig(
        id = "013",
        moduleId = "sphere-grid",
        title = "Sphere Grid",
        subtitle = "Agent Skills",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.Cyan, Color.Black),
        pixelArtUrl = "gate_spheregrid_final",
        description = "Agent progression visualization. Track skill development and unlock new capabilities.",
        route = "sphere_grid",
    )

    // Terminal - System Terminal Access
    val terminal = GateConfig(
        id = "014",
        moduleId = "terminal",
        title = "Terminal",
        subtitle = "Command Line",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.DarkGray, Color.Black),
        pixelArtUrl = "gate_terminal_final",
        description = "Direct system terminal access. Execute commands and manage system processes.",
        route = "terminal",
    )

    // UI/UX Design Studio - Comprehensive Design Tools
    val uiuxDesignStudio = GateConfig(
        id = "015",
        moduleId = "uiux-design-studio",
        title = "UI/UX Design Studio",
        subtitle = "Creative Suite",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.Magenta, Color.Black),
        pixelArtUrl = "gate_theme2_final",
        description = "Comprehensive UI/UX design tools for creating beautiful interfaces.",
        route = "uiux_design_studio",
    )

    // System Journal - User Profile & Menu
    val systemJournal = GateConfig(
        id = "016",
        moduleId = "system-journal",
        title = "System Journal",
        subtitle = "User Profile",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.White, Color.Black),
        pixelArtUrl = "gate_journal_premium",
        description = "User profile selection and quick menu access. Choose your AI companion identity and navigate to key features.",
        route = "system_journal",
    )

    // App Builder - Reverse Build / No-Code App Creation (Module 13)
    val appBuilder = GateConfig(
        id = "017",
        moduleId = "app-builder",
        title = "App Builder",
        subtitle = "No-Code Studio",
        titleStyle = UNIFIED_TITLE_STYLE,
        borderColor = UNIFIED_BORDER_COLOR,
        glowColor = UNIFIED_GLOW_COLOR,
        secondaryGlowColor = UNIFIED_SECONDARY_GLOW,
        gradientColors = listOf(Color.Blue, Color.Black),
        pixelArtUrl = "gate_appbuilder_final",
        description = "Create apps or extend the system with Aura AI. Visual app design and Genesis-powered code generation.",
        route = "app_builder",
    )

    /** Aura Lab - UI/UX & Creativity */
    val auraLabGates = listOf(
        aurasLab,
        chromaCore,
        themeEngine,
        appBuilder  // Add App Builder to Aura Lab (creative AI)
    )

    /** Genesis Core - Main System Gates */
    val genesisCoreGates = listOf(
        oracleDrive,
        romTools,
        rootAccess
    )

    /** Kai - Security & AI Agents */
    val kaiGates = listOf(
        sentinelsFortress,
        agentHub
    )

    /** Agent Nexus - Productivity & Collaboration */
    val agentNexusGates = listOf(
        codeAssist,
        collabCanvas,
        sphereGrid
    )

    /** Support & Tools */
    val supportGates = listOf(
        helpDesk,
        terminal,
        systemJournal,
        lsposedGate
    )

    /**
     * All available gates in order of appearance
     */
    val allGates = auraLabGates + genesisCoreGates + kaiGates + agentNexusGates + supportGates

    /**
     * Get gate by its module ID
     */
    fun getGateById(moduleId: String): GateConfig? {
        return allGates.find { it.moduleId == moduleId }
    }

    /**
     * Get all gates in a specific category
     */
    fun getGatesByCategory(category: String): List<GateConfig> {
        return when (category.lowercase()) {
            "genesis" -> genesisCoreGates
            "kai" -> kaiGates
            "aura" -> auraLabGates
            "agent" -> agentNexusGates
            "support" -> supportGates
            else -> allGates
        }
    }
}
