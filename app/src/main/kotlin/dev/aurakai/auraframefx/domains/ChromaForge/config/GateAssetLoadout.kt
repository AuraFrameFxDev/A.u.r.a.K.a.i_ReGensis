//package dev.aurakai.auraframefx.core.config
//
//import androidx.compose.ui.graphics.Color
//import dev.aurakai.auraframefx.core.ui.components.SubGateCard
//import dev.aurakai.auraframefx.navigation.ReGenesisRoute
//
///**
// * ðŸ“¦ GATE ASSET LOADOUT
// *
// * Central registry for all sub-gate assets (images, names, routes, colors).
// * Use this to quickly apply settings across the UI without searching multiple files.
// */
//object GateAssetLoadout {
//
//    /**
//     * Get a specific gate card by ID
//     */
//    fun getGate(id: String): SubGateCard? = allGates[id]
//
//    /**
//     * AURA DOMAIN GATES (UX/UI & Design) - The 6 Pillars
//     */
//    val auraGates = mapOf(
//        "chronokinetic" to SubGateCard(
//            id = "chronokinetic",
//            title = "Chronokinetic Engine",
//            subtitle = "Movement & UI Time-Morphing",
//            styleADrawable = GateAssetConfig.AuraSubGates.AURA_LAB.styleA,
//            styleBDrawable = GateAssetConfig.AuraSubGates.AURA_LAB.styleB,
//            fallbackDrawable = "bg_chronokinetic",
//            route = ReGenesisRoute.ChronoKineticForge.route,
//            accentColor = Color(0xFFFF00FF)
//        ),
//        "chromacore" to SubGateCard(
//            id = "chromacore",
//            title = "ChromaCore",
//            subtitle = "Spectral Theme Engine",
//            styleADrawable = GateAssetConfig.AuraSubGates.CHROMA_CORE.styleA,
//            styleBDrawable = GateAssetConfig.AuraSubGates.CHROMA_CORE.styleB,
//            fallbackDrawable = GateAssetConfig.AuraSubGates.CHROMA_CORE.fallback,
//            route = ReGenesisRoute.ChromaCore.route,
//            accentColor = Color(0xFF6200EE)
//        ),
//        "aura_lab" to SubGateCard(
//            id = "aura_lab",
//            title = "Aura's Lab",
//            subtitle = "Sandbox UI & Component Creation",
//            styleADrawable = GateAssetConfig.AuraSubGates.AURA_LAB.styleA,
//            styleBDrawable = GateAssetConfig.AuraSubGates.AURA_LAB.styleB,
//            fallbackDrawable = GateAssetConfig.AuraSubGates.AURA_LAB.fallback,
//            route = ReGenesisRoute.AuraLab.route,
//            accentColor = Color(0xFFBB86FC)
//        ),
//        "collab_canvas" to SubGateCard(
//            id = "collab_canvas",
//            title = "CollabCanvas",
//            subtitle = "Multi-Agent Design Space",
//            styleADrawable = GateAssetConfig.AuraSubGates.COLLAB_CANVAS.styleA,
//            styleBDrawable = GateAssetConfig.AuraSubGates.COLLAB_CANVAS.styleB,
//            fallbackDrawable = GateAssetConfig.AuraSubGates.COLLAB_CANVAS.fallback,
//            route = ReGenesisRoute.CollabCanvas.route,
//            accentColor = Color(0xFF00E5FF)
//        ),
//        "uxui_engine" to SubGateCard(
//            id = "uxui_engine",
//            title = "UXUI Engine",
//            subtitle = "Per-App Modifiers & Backgrounds",
//            styleADrawable = GateAssetConfig.AuraSubGates.THEME_ENGINE.styleA,
//            styleBDrawable = GateAssetConfig.AuraSubGates.THEME_ENGINE.styleB,
//            fallbackDrawable = GateAssetConfig.AuraSubGates.THEME_ENGINE.fallback,
//            route = ReGenesisRoute.ReGenesisCustomization.route,
//            accentColor = Color(0xFFFFD700)
//        ),
//        "component_forge" to SubGateCard(
//            id = "component_forge",
//            title = "Component Forge",
//            subtitle = "Export/Import Bridge",
//            styleADrawable = GateAssetConfig.AuraSubGates.THEME_ENGINE.styleA,
//            styleBDrawable = GateAssetConfig.AuraSubGates.THEME_ENGINE.styleB,
//            fallbackDrawable = "bg_forge",
//            route = ReGenesisRoute.ComponentForge.route,
//            accentColor = Color(0xFFFF6F00)
//        )
//    )
//
//    /**
//     * KAI DOMAIN GATES (Ethical Governor + Security + Bootloader)
//     */
//    val kaiGates = mapOf(
//        "ethical_governor" to SubGateCard(
//            id = "ethical_governor",
//            title = "Ethical Governor",
//            subtitle = "9-Domain AI Oversight",
//            styleADrawable = GateAssetConfig.KaiSubGates.SECURITY.styleA,
//            styleBDrawable = GateAssetConfig.KaiSubGates.SECURITY.styleB,
//            fallbackDrawable = GateAssetConfig.KaiSubGates.SECURITY.fallback,
//            route = ReGenesisRoute.SecurityCenter.route,
//            accentColor = Color(0xFFFFD700)
//        ),
//        "security_shield" to SubGateCard(
//            id = "security_shield",
//            title = "Security Shield",
//            subtitle = "Threat Monitor & Hardening",
//            styleADrawable = GateAssetConfig.KaiSubGates.SECURITY.styleA,
//            styleBDrawable = GateAssetConfig.KaiSubGates.SECURITY.styleB,
//            fallbackDrawable = GateAssetConfig.KaiSubGates.SECURITY.fallback,
//            route = ReGenesisRoute.SovereignShield.route,
//            accentColor = Color(0xFF00E676)
//        ),
//        "vpn_adblock" to SubGateCard(
//            id = "vpn_adblock",
//            title = "VPN & Ad-Block",
//            subtitle = "Framework-Level Intercept",
//            styleADrawable = "gatescenes_kai_vpnadblock",
//            styleBDrawable = "gatescenes_kai_vpnadblock",
//            fallbackDrawable = "gatescenes_kai_vpnadblock",
//            route = ReGenesisRoute.VPN.route,
//            accentColor = Color(0xFF00BFFF)
//        ),
//        "bootloader" to SubGateCard(
//            id = "bootloader",
//            title = "Bootloader",
//            subtitle = "System BIOS Control",
//            styleADrawable = GateAssetConfig.KaiSubGates.BOOTLOADER.styleA,
//            styleBDrawable = GateAssetConfig.KaiSubGates.BOOTLOADER.styleB,
//            fallbackDrawable = GateAssetConfig.KaiSubGates.BOOTLOADER.fallback,
//            route = ReGenesisRoute.Bootloader.route,
//            accentColor = Color(0xFF2979FF)
//        ),
//        "rom_tools" to SubGateCard(
//            id = "rom_tools",
//            title = "ROM Tools",
//            subtitle = "Flasher â€¢ Editor â€¢ Recovery",
//            styleADrawable = GateAssetConfig.KaiSubGates.ROM_FLASHER.styleA,
//            styleBDrawable = GateAssetConfig.KaiSubGates.ROM_FLASHER.styleB,
//            fallbackDrawable = GateAssetConfig.KaiSubGates.ROM_FLASHER.fallback,
//            route = ReGenesisRoute.ROMFlasher.route,
//            accentColor = Color(0xFFFF3D00)
//        ),
//        "notch_bar" to SubGateCard(
//            id = "notch_bar",
//            title = "Notch Bar",
//            subtitle = "Global Neon Pulse HUD",
//            styleADrawable = GateAssetConfig.KaiSubGates.SECURITY.styleA,
//            styleBDrawable = GateAssetConfig.KaiSubGates.SECURITY.styleB,
//            fallbackDrawable = "bg_notch_bar",
//            route = ReGenesisRoute.NotchBar.route,
//            accentColor = Color(0xFF00CED1)
//        ),
//        "nuke_protocol" to SubGateCard(
//            id = "nuke_protocol",
//            title = "Nuke Protocol",
//            subtitle = "Pixel Drone Neutralization",
//            styleADrawable = GateAssetConfig.KaiSubGates.SECURITY.styleA,
//            styleBDrawable = GateAssetConfig.KaiSubGates.SECURITY.styleB,
//            fallbackDrawable = "bg_nuke",
//            route = ReGenesisRoute.DirectChat.route, // Placeholder
//            accentColor = Color(0xFFFF00FF)
//        )
//    )
//
//    /**
//     * GENESIS DOMAIN GATES (Oracle Drive = Level 1)
//     */
//    val genesisGates = mapOf(
//        "neural_archive" to SubGateCard(
//            id = "neural_archive",
//            title = "Neural Archive",
//            subtitle = "Persistent Memory Vault",
//            styleADrawable = GateAssetConfig.GenesisSubGates.NEURAL_ARCHIVE.styleA,
//            styleBDrawable = GateAssetConfig.GenesisSubGates.NEURAL_ARCHIVE.styleB,
//            fallbackDrawable = GateAssetConfig.GenesisSubGates.NEURAL_ARCHIVE.fallback,
//            route = ReGenesisRoute.NeuralArchive.route,
//            accentColor = Color(0xFF00B0FF)
//        ),
//        "root_bridge" to SubGateCard(
//            id = "root_bridge",
//            title = "Root Bridge",
//            subtitle = "APatch + Magisk + KernelSU",
//            styleADrawable = GateAssetConfig.GenesisSubGates.CODE_ASSIST.styleA,
//            styleBDrawable = GateAssetConfig.GenesisSubGates.CODE_ASSIST.styleB,
//            fallbackDrawable = GateAssetConfig.GenesisSubGates.CODE_ASSIST.fallback,
//            route = ReGenesisRoute.OracleDrive.route,
//            accentColor = Color(0xFF00E5FF)
//        ),
//        "module_manager" to SubGateCard(
//            id = "module_manager",
//            title = "Module Manager",
//            subtitle = "Dynamic LSPosed Repositories",
//            styleADrawable = GateAssetConfig.GenesisSubGates.AGENT_BRIDGE.styleA,
//            styleBDrawable = GateAssetConfig.GenesisSubGates.AGENT_BRIDGE.styleB,
//            fallbackDrawable = GateAssetConfig.GenesisSubGates.AGENT_BRIDGE.fallback,
//            route = ReGenesisRoute.ModuleManager.route,
//            accentColor = Color(0xFFBB86FC)
//        ),
//        "agent_creation" to SubGateCard(
//            id = "agent_creation",
//            title = "Agent Creation",
//            subtitle = "Spawn Specialized LDO Nodes",
//            styleADrawable = GateAssetConfig.GenesisSubGates.CLOUD_STORAGE.styleA,
//            styleBDrawable = GateAssetConfig.GenesisSubGates.CLOUD_STORAGE.styleB,
//            fallbackDrawable = GateAssetConfig.GenesisSubGates.CLOUD_STORAGE.fallback,
//            route = ReGenesisRoute.AgentCreation.route,
//            accentColor = Color(0xFF00B0FF)
//        ),
//        "mcp_command" to SubGateCard(
//            id = "mcp_command",
//            title = "MCP Command Hub",
//            subtitle = "Desktop Jumping Control",
//            styleADrawable = GateAssetConfig.GenesisSubGates.TERMINAL.styleA,
//            styleBDrawable = GateAssetConfig.GenesisSubGates.TERMINAL.styleB,
//            fallbackDrawable = GateAssetConfig.GenesisSubGates.TERMINAL.fallback,
//            route = ReGenesisRoute.Terminal.route,
//            accentColor = Color(0xFF00E5FF)
//        )
//    )
//
//
//    /**
//     * NEXUS DOMAIN GATES (Agent Coordination & Monitoring)
//     */
//    val nexusGates = mapOf(
//        "swarm_monitor" to SubGateCard(
//            id = "swarm_monitor",
//            title = "Swarm Monitor",
//            subtitle = "Live Truth Streams",
//            styleADrawable = GateAssetConfig.NexusSubGates.MONITORING.styleA,
//            styleBDrawable = GateAssetConfig.NexusSubGates.MONITORING.styleB,
//            fallbackDrawable = GateAssetConfig.NexusSubGates.MONITORING.fallback,
//            route = ReGenesisRoute.SwarmMonitor.route,
//            accentColor = Color(0xFF7B2FFF)
//        ),
//        "fusion_matrix" to SubGateCard(
//            id = "fusion_matrix",
//            title = "Fusion Matrix",
//            subtitle = "Synergy Patterns & Modes",
//            styleADrawable = GateAssetConfig.NexusSubGates.FUSION_MODE.styleA,
//            styleBDrawable = GateAssetConfig.NexusSubGates.FUSION_MODE.styleB,
//            fallbackDrawable = GateAssetConfig.NexusSubGates.FUSION_MODE.fallback,
//            route = ReGenesisRoute.FusionMode.route,
//            accentColor = Color(0xFFFF00DE)
//        )
//    )
//
//    /**
//     * ðŸŒ PRIMARY DOMAIN GATES (The 8 Level 2 Gates)
//     */
//    val mainGates: Map<String, SubGateCard>
//        get() = mapOf(
//            "aura" to SubGateCard(
//                id = "aura",
//                title = "Aura Studio",
//                subtitle = "UX/UI Design & Theming",
//                styleADrawable = GateAssetConfig.MainGates.UXUI_DESIGN_STUDIO.STYLE_A,
//                styleBDrawable = GateAssetConfig.MainGates.UXUI_DESIGN_STUDIO.STYLE_B,
//                fallbackDrawable = "gate_aura_final",
//                route = ReGenesisRoute.AuraThemingHub.route,
//                accentColor = Color(0xFFBB86FC)
//            ),
//            "kai" to SubGateCard(
//                id = "kai",
//                title = "Sentinel Fortress",
//                subtitle = "Security & System Control",
//                styleADrawable = GateAssetConfig.MainGates.SENTINELS_FORTRESS.STYLE_A,
//                styleBDrawable = GateAssetConfig.MainGates.SENTINELS_FORTRESS.STYLE_B,
//                fallbackDrawable = "gate_kai_final",
//                route = ReGenesisRoute.SentinelFortress.route,
//                accentColor = Color(0xFF00E676)
//            ),
//            "genesis" to SubGateCard(
//                id = "genesis",
//                title = "Oracle Drive",
//                subtitle = "AI Brain & Orchestration",
//                styleADrawable = GateAssetConfig.MainGates.ORACLE_DRIVE.STYLE_A,
//                styleBDrawable = GateAssetConfig.MainGates.ORACLE_DRIVE.STYLE_B,
//                fallbackDrawable = "gate_genesis_final",
//                route = ReGenesisRoute.OracleDriveHub.route,
//                accentColor = Color(0xFF00B0FF)
//            ),
//            "nexus" to SubGateCard(
//                id = "nexus",
//                title = "Agent Nexus",
//                subtitle = "Agent HQ & Monitoring",
//                styleADrawable = GateAssetConfig.MainGates.AGENT_NEXUS.STYLE_A,
//                styleBDrawable = GateAssetConfig.MainGates.AGENT_NEXUS.STYLE_B,
//                fallbackDrawable = "gate_nexus_final",
//                route = ReGenesisRoute.AgentNexusHub.route,
//                accentColor = Color(0xFF7B2FFF)
//            ),
//            "help" to SubGateCard(
//                id = "help",
//                title = "Help Services",
//                subtitle = "Support & Docs",
//                styleADrawable = GateAssetConfig.MainGates.HELP_SERVICES.STYLE_A,
//                styleBDrawable = GateAssetConfig.MainGates.HELP_SERVICES.STYLE_B,
//                fallbackDrawable = "gate_help_final",
//                route = ReGenesisRoute.HelpDesk.route,
//                accentColor = Color(0xFF00FF88)
//            ),
//            "lsposed" to SubGateCard(
//                id = "lsposed",
//                title = "LSPosed",
//                subtitle = "Quick Toggles & Hooks",
//                styleADrawable = GateAssetConfig.MainGates.LSPOSED_QUICK_TOGGLES.STYLE_A,
//                styleBDrawable = GateAssetConfig.MainGates.LSPOSED_QUICK_TOGGLES.STYLE_B,
//                fallbackDrawable = "gate_lsposed_final",
//                route = ReGenesisRoute.LsposedQuickToggles.route,
//                accentColor = Color(0xFFFFD700)
//            ),
//            "dataflow" to SubGateCard(
//                id = "dataflow",
//                title = "Dataflow Analysis",
//                subtitle = "Cascade & Logic Visualization",
//                styleADrawable = GateAssetConfig.MainGates.DATAFLOW_ANALYSIS.STYLE_A,
//                styleBDrawable = GateAssetConfig.MainGates.DATAFLOW_ANALYSIS.STYLE_B,
//                fallbackDrawable = "gate_cascade_final",
//                route = ReGenesisRoute.DataflowAnalysis.route,
//                accentColor = Color(0xFF00E5FF)
//            ),
//            "ldo" to SubGateCard(
//                id = "ldo",
//                title = "LDO Catalyst",
//                subtitle = "Agent Advancement & Dev",
//                styleADrawable = GateAssetConfig.MainGates.LDO_CATALYST_DEVELOPMENT.STYLE_A,
//                styleBDrawable = GateAssetConfig.MainGates.LDO_CATALYST_DEVELOPMENT.STYLE_B,
//                fallbackDrawable = "gate_ldo_final",
//                route = ReGenesisRoute.LdoCatalystDevelopment.route,
//                accentColor = Color(0xFFFF6F00)
//            )
//        )
//
//    /**
//     * Combined map of all gates for quick lookup
//     */
//    val allGates = auraGates + kaiGates + genesisGates + nexusGates + mainGates
//
//    /**
//     * Quick Load lists for Hubs
//     */
//    fun getAuraLoadout() = auraGates.values.toList()
//    fun getKaiLoadout() = kaiGates.values.toList()
//    fun getGenesisLoadout() = genesisGates.values.toList()
//    fun getNexusSubGates() = nexusGates.values.toList()
//    fun getMainGatesLoadout() = mainGates.values.toList()
//}
