package dev.aurakai.auraframefx.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * SINGLE SOURCE OF TRUTH FOR ALL NAVIGATION IN RE:GENESIS
 * Merged from old NavDestination + new ReGenesisRoute + new Aura Studio
 */
sealed class ReGenesisRoute(
    open val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {

    // LEVEL 1: PRIMARY GATES
    data object MainScreen : ReGenesisRoute("main_screen", "Main Dashboard")
    data object HomeGateCarousel : ReGenesisRoute("home_gate_carousel/{tabIndex}") {
        fun createRoute(tabIndex: Int = 0) = "home_gate_carousel/$tabIndex"
    }
    data object Login : ReGenesisRoute("login", "Login")

    // AURA DOMAIN (ChronoKinetic Forge + Studio)
    data object AuraStudio : ReGenesisRoute("aura_studio", "Aura Studio", Icons.Default.Bolt)
    data object ChronoKineticForge :
        ReGenesisRoute("chrono_kinetic_forge", "ChronoKinetic Forge", Icons.Default.Bolt)

    // KAI DOMAIN
    data object SentinelFortress :
        ReGenesisRoute("sentinel_fortress", "Sentinel Fortress", Icons.Default.Security)

    data object KaiToolShed :
        ReGenesisRoute("kai_tool_shed", "Kai ToolShed", Icons.Default.Settings)

    // GENESIS / ORACLEDRIVE
    data object OracleDriveHub :
        ReGenesisRoute("oracle_drive_hub", "Oracle Drive", Icons.Default.Storage)
    data object AgentNexusHub : ReGenesisRoute("agent_nexus_hub", "Agent Nexus", Icons.Default.Hub)
    data object OracleDrive : ReGenesisRoute("oracle_drive", "Oracle Drive")

    // CASCADE / LDO
    data object LdoDevelopmentNexus :
        ReGenesisRoute("ldo_devops_hub", "LDO Development Nexus", Icons.Default.ViewInAr)
    data object EmergentSwarm : ReGenesisRoute("emergent_swarm", "Emergent Swarm")
    data object DataflowAnalysis : ReGenesisRoute("dataflow_analysis", "Dataflow")

    // QUICK ACCESS / LEGACY
    data object ChromaCore : ReGenesisRoute("chroma_core", "ChromaCore", Icons.Default.ColorLens)
    data object CollabCanvas : ReGenesisRoute("collab_canvas", "Collab Canvas")
    data object AuraLab : ReGenesisRoute("sandbox_ui", "Aura's Lab", Icons.Default.Science)
    data object UISettings : ReGenesisRoute("ui_settings", "UI Settings")
    data object UserPreferences : ReGenesisRoute("user_preferences", "User Preferences")

    // Additional common routes to satisfy unresolved references
    data object AgentCreation : ReGenesisRoute("agent_creation", "Agent Creation")
    data object SwarmMonitor : ReGenesisRoute("swarm_monitor", "Swarm Monitor")
    data object TaskAssignment : ReGenesisRoute("task_assignment", "Task Assignment")
    data object LdoRoster : ReGenesisRoute("ldo_roster", "LDO Roster")
    data object Party : ReGenesisRoute("party", "Party")
    data object GenderSelection : ReGenesisRoute("gender_selection", "Gender Selection")
    data object ThemeEngine : ReGenesisRoute("theme_engine", "Theme Engine")
    data object ReGenesisCustomization : ReGenesisRoute("regenesis_customization", "Customization")
    data object SovereignShield : ReGenesisRoute("sovereign_shield", "Sovereign Shield")
    data object Bootloader : ReGenesisRoute("bootloader", "Bootloader")
    data object ROMFlasher : ReGenesisRoute("rom_flasher", "ROM Flasher")
    data object NotchBar : ReGenesisRoute("notch_bar", "Notch Bar")
    data object CodeAssist : ReGenesisRoute("code_assist", "Code Assist")
    data object SentientShell : ReGenesisRoute("sentient_shell", "Sentient Shell")
    data object AgentMonitoring : ReGenesisRoute("agent_monitoring", "Agent Monitoring")
    data object SphereGrid : ReGenesisRoute("sphere_grid", "Sphere Grid")
    data object AgentNeuralExplorer :
        ReGenesisRoute("agent_neural_explorer", "Agent Neural Explorer")

    data object FusionMode : ReGenesisRoute("fusion_mode", "Fusion Mode")
    data object MetaInstruct : ReGenesisRoute("meta_instruct", "Meta Instruct")
    data object AuraThemingHub : ReGenesisRoute("aura_theming_hub", "Aura Studio Hub")
    data object HelpDesk : ReGenesisRoute("help_desk", "Help Desk")
    data object LsposedQuickToggles : ReGenesisRoute("lsposed_quick_toggles", "LSPosed Toggles")
    data object LdoCatalystDevelopment : ReGenesisRoute("ldo_catalyst_development", "LDO Catalyst")
    data object DirectChat : ReGenesisRoute("direct_chat", "Direct Chat")
    data object Documentation : ReGenesisRoute("documentation", "Documentation")
    data object FAQBrowser : ReGenesisRoute("faq_browser", "FAQ Browser")
    data object TutorialVideos : ReGenesisRoute("tutorial_videos", "Tutorial Videos")
    data object CascadeVision : ReGenesisRoute("cascade_vision", "Cascade Vision")
    data object DataStreamMonitoring : ReGenesisRoute("data_monitor", "Data Monitoring")
    data object NeuralNetwork : ReGenesisRoute("neural_network", "Neural Network")
    data object LdoDevOpsCommandCenter :
        ReGenesisRoute("ldo_devops_command_center", "DevOps Command Center")

    data object LdoArmamentFusion : ReGenesisRoute("ldo_armament_fusion", "Armament Fusion")
    data object RomToolsHub : ReGenesisRoute("rom_tools_hub", "ROM Tools Hub")
    data object XposedPanel : ReGenesisRoute("xposed_panel", "Xposed Panel")
    data object Terminal : ReGenesisRoute("terminal", "Terminal")
    data object SentientShellScreen : ReGenesisRoute("sentient_shell_screen")
    data object EvolutionTree : ReGenesisRoute("evolution_tree", "Evolution Tree")
    data object LdoTasker : ReGenesisRoute("ldo_tasker", "LDO Tasker")
    data object LdoBonding : ReGenesisRoute("ldo_bonding", "LDO Bonding")
    data object ArkBuild : ReGenesisRoute("ark_build", "Ark Build")
    data object BenchmarkMonitor : ReGenesisRoute("benchmark_monitor", "Benchmark Monitor")
    data object Claude : ReGenesisRoute("claude", "Claude")
    data object Gemini : ReGenesisRoute("gemini", "Gemini")
    data object Nemotron : ReGenesisRoute("nemotron", "Nemotron")
    data object Kairos : ReGenesisRoute("kairos", "Kairos")
    data object Primus : ReGenesisRoute("primus", "Primus")
    data object Andelualx : ReGenesisRoute("andelualx", "Andelualx")
    data object MkMini : ReGenesisRoute("mk_mini", "MK Mini")
    data object Manus : ReGenesisRoute("manus", "Manus")
    data object Grok : ReGenesisRoute("grok", "Grok")
    data object Perplexity : ReGenesisRoute("perplexity", "Perplexity")
    data object AuraAIFeatures : ReGenesisRoute("aura_ai_features", "AI Features")
    data object AuraDeviceOptimizer : ReGenesisRoute("aura_device_optimizer", "Device Optimizer")
    data object IconifyIconPacks : ReGenesisRoute("aura/iconify/icon_packs")
    data object IconifyBatteryStyles : ReGenesisRoute("aura/iconify/battery_styles")
    data object IconifyBrightnessBars : ReGenesisRoute("aura/iconify/brightness_bars")
    data object IconifyQSPanel : ReGenesisRoute("aura/iconify/qs_panel")
    data object AuraCanvasEditor : ReGenesisRoute("aura_canvas_editor", "Canvas Editor")
    data object AuraSystemOverlays : ReGenesisRoute("aura_system_overlays", "System Overlays")
    data object LiveROMEditor : ReGenesisRoute("live_rom_editor", "Live ROM Editor")
    data object SystemOverrides : ReGenesisRoute("system_overrides", "System Overrides")
    data object ModuleCreation : ReGenesisRoute("module_creation", "Module Creation")
    data object AgentSwarm : ReGenesisRoute("agent_swarm", "Agent Swarm")
    data object ConsciousnessVisualizer :
        ReGenesisRoute("consciousness_visualizer", "Consciousness Visualizer")
    data object Constellation : ReGenesisRoute("constellation", "Constellation")
    data object ClaudeConstellation : ReGenesisRoute("claude_constellation", "Claude Constellation")
    data object CascadeConstellation :
        ReGenesisRoute("cascade_constellation", "Cascade Constellation")
    data object KaiConstellation : ReGenesisRoute("kai_constellation", "Kai Constellation")
    data object GenesisConstellation :
        ReGenesisRoute("genesis_constellation", "Genesis Constellation")
    data object GrokConstellation : ReGenesisRoute("grok_constellation", "Grok Constellation")
    data object CascadeHub : ReGenesisRoute("cascade_hub", "Cascade Hub")
    data object DataVeinSphere : ReGenesisRoute("datavein_sphere", "DataVein Sphere")
    data object LdoFusion : ReGenesisRoute("ldo_fusion", "Fusion")
    data object LdoOrchestrationHub : ReGenesisRoute("ldo_orchestration_hub", "Orchestration Hub")
    data object LdoWorldTree : ReGenesisRoute("ldo_world_tree", "World Tree")
    data object LDOCatalystHub : ReGenesisRoute("ldo_catalyst_hub", "Catalyst Hub")
    data object LdoAgentProfile : ReGenesisRoute("ldo_agent_profile/{agentId}") {
        const val ARG = "agentId"
        fun createRoute(agentId: String) = "ldo_agent_profile/$agentId"
    }

    data object AgentAdvancement : ReGenesisRoute("agent_advancement", "Agent Advancement")
    data object UIEngine : ReGenesisRoute("ui_engine", "UI Engine")
    data object PrivacyGuard : ReGenesisRoute("privacy_guard", "Privacy Guard")
    data object ProfileScreen : ReGenesisRoute("profile_screen", "Profile")
    data object SecureComm : ReGenesisRoute("secure_comm", "Secure Communications")
    data object SecurityScanner : ReGenesisRoute("security_scanner", "Security Scanner")
    data object VPNManager : ReGenesisRoute("vpn_manager", "VPN Manager")
    data object Firewall : ReGenesisRoute("firewall", "Firewall")
    data object BetaScreens : ReGenesisRoute("beta_screens", "Beta Screens")
    data object QuickActions : ReGenesisRoute("quick_actions", "Quick Actions")
    data object AuraSphereGrid : ReGenesisRoute("aura_sphere_grid", "Aura Sphere Grid")
    data object AuraDossier : ReGenesisRoute("aura_dossier", "Aura Dossier")
    data object AuraLDOArmament : ReGenesisRoute("aura_ldo_armament", "LDO Armament")
    data object EcosystemMenu : ReGenesisRoute("ecosystem_menu", "Ecosystem Menu")
    data object WorkingLab : ReGenesisRoute("working_lab", "Working Lab")
    data object KineticForgeCore : ReGenesisRoute("kinetic_forge_core", "Kinetic Forge Core")
    data object KineticForgeTransmutator :
        ReGenesisRoute("kinetic_forge_transmutator", "Transmutator")
    data object KineticForgeLattice : ReGenesisRoute("kinetic_forge_lattice", "Lattice")
    data object RecoveryTools : ReGenesisRoute("recovery_tools", "Recovery Tools")
    data object SovereignBootloader : ReGenesisRoute("sovereign_bootloader", "Sovereign Bootloader")
    data object VPN : ReGenesisRoute("vpn", "VPN")
    data object BootloaderManager : ReGenesisRoute("bootloader_manager", "Bootloader Manager")
    data object HookManager : ReGenesisRoute("hook_manager", "Hook Manager")
    data object LSPosedGate : ReGenesisRoute("lsposed_gate", "LSPosed Gate")
    data object LSPosedModuleManager :
        ReGenesisRoute("lsposed_module_manager", "LSPosed Module Manager")
    data object LSPosedSubmenu : ReGenesisRoute("lsposed_submenu", "LSPosed Submenu")
    data object ModuleManager : ReGenesisRoute("module_manager", "Module Manager")
    data object RootTools : ReGenesisRoute("root_tools", "Root Tools")
    data object ROMToolsSubmenu : ReGenesisRoute("rom_tools_submenu", "ROM Tools")
    data object RootToolsToggles : ReGenesisRoute("root_tools_toggles", "Root Toggles")
    data object LogsViewer : ReGenesisRoute("logs_viewer", "Logs Viewer")
    data object KaiDomainExpansion : ReGenesisRoute("kai_domain_expansion", "Domain Expansion")
    data object KaiRGSS : ReGenesisRoute("kai_rgss", "Royal Guard System")
    data object KaiSentinelFortress : ReGenesisRoute("kai_sentinel_fortress", "Sentinel Fortress")
    data object KaiSphereGrid : ReGenesisRoute("kai_sphere_grid", "Kai Sphere Grid")
    data object KaiDossier : ReGenesisRoute("kai_dossier", "Kai Dossier")
    data object KaiLDOArmament : ReGenesisRoute("kai_ldo_armament", "LDO Armament")
    data object KaiSentinelIntegrity :
        ReGenesisRoute("kai_sentinel_integrity", "Sentinel Integrity")
    data object PowerOfNo : ReGenesisRoute("power_of_no", "Power of No")
    data object RoyalGuardDomain : ReGenesisRoute("royal_guard_domain", "Royal Guard Domain")
    data object RoyalGuardOS : ReGenesisRoute("royal_guard_os", "Royal Guard OS")
    data object GenesisHub : ReGenesisRoute("genesis_hub", "Genesis Hub")
    data object AppBuilder : ReGenesisRoute("app_builder", "App Builder")
    data object PandoraBox : ReGenesisRoute("pandora_box", "Pandora's Box")
    data object TerminalBootIntro : ReGenesisRoute("terminal_boot_intro", "Terminal Boot")
    data object NeuralArchive : ReGenesisRoute("neural_archive", "Neural Archive")
    data object SovereignNeuralArchive :
        ReGenesisRoute("sovereign_neural_archive", "Sovereign Archive")
    data object OracleDriveSubmenu : ReGenesisRoute("oracle_drive_submenu", "Oracle Drive Submenu")
    data object OracleCloudInfinite : ReGenesisRoute("oracle_cloud_infinite", "Infinite Storage")
    data object FirebaseExamples : ReGenesisRoute("firebase_examples", "Firebase Examples")
    data object AuraChat : ReGenesisRoute("aura/chat", "Aura Neural Hub")
    data object IconifyHub : ReGenesisRoute("aura/iconify_hub", "Iconify Hub")
    data object ColorBlendr : ReGenesisRoute("aura/colorblendr")
    data object ColorBlendrMonet : ReGenesisRoute("aura/colorblendr/monet")
    data object ColorBlendrPalette : ReGenesisRoute("aura/colorblendr/palette")
    data object ColorBlendrPerApp : ReGenesisRoute("aura/colorblendr/per_app")
    data object PixelLauncherEnhanced : ReGenesisRoute("aura/pixel_launcher_enhanced")
    data object PLEIcons : ReGenesisRoute("aura/ple/icons")
    data object PLEHomeScreen : ReGenesisRoute("aura/ple/home_screen")
    data object PLEAppDrawer : ReGenesisRoute("aura/ple/app_drawer")
    data object PLERecents : ReGenesisRoute("aura/ple/recents")
    data object SystemJournal : ReGenesisRoute("system_journal", "System Journal")
    data object XposedQuickAccessPanel : ReGenesisRoute("xposed_quick_access_panel")
    data object IconifyCategory : ReGenesisRoute("aura/iconify/{category}") {
        fun createRoute(category: String) = "aura/iconify/$category"
    }
    data object ConferenceRoom : ReGenesisRoute("conference_room", "Conference Room")
    data object SecurityCenter : ReGenesisRoute("security_center", "Security Center")
    data object SovereignRecovery : ReGenesisRoute("sovereign_recovery", "Sovereign Recovery")
    data object AgentBridgeHub : ReGenesisRoute("agent_bridge_hub", "Agent Bridge")
    data object SovereignModuleManager :
        ReGenesisRoute("sovereign_module_manager", "Module Manager")

    data object MonitoringHUDs : ReGenesisRoute("monitoring_huds", "Monitoring HUDs")

    companion object {
        // Helper to get all tabs for TabbedMasterIndex
        fun getAllTabs(): List<ReGenesisRoute> = listOf(
            AuraStudio,
            SentinelFortress,
            OracleDriveHub,
            AgentNexusHub,
            LdoDevelopmentNexus,
            EmergentSwarm
        )
    }
}
