package dev.aurakai.auraframefx.config

import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.components.SubGateCard

/**
 * 📦 GATE ASSET LOADOUT
 *
 * Central registry for all sub-gate assets (images, names, routes, colors).
 * Use this to quickly apply settings across the UI without searching multiple files.
 */
object GateAssetLoadout {

    /**
     * Get a specific gate card by ID
     */
    fun getGate(id: String): SubGateCard? = allGates[id]

    /**
     * AURA DOMAIN GATES (UX/UI & Design)
     */
    val auraGates = mapOf(
        "aura_lab" to SubGateCard(
            id = "aura_lab",
            title = "Aura's Lab",
            subtitle = "UI Sandbox & Prototyping",
            styleADrawable = GateAssetConfig.AuraSubGates.AURA_LAB.styleA,
            styleBDrawable = GateAssetConfig.AuraSubGates.AURA_LAB.styleB,
            fallbackDrawable = GateAssetConfig.AuraSubGates.AURA_LAB.fallback,
            route = NavDestination.AuraLab.route,
            accentColor = Color(0xFFBB86FC)
        ),
        "chromacore" to SubGateCard(
            id = "chromacore",
            title = "ChromaCore",
            subtitle = "Material You Color Engine",
            styleADrawable = GateAssetConfig.AuraSubGates.CHROMA_CORE.styleA,
            styleBDrawable = GateAssetConfig.AuraSubGates.CHROMA_CORE.styleB,
            fallbackDrawable = GateAssetConfig.AuraSubGates.CHROMA_CORE.fallback,
            route = NavDestination.ColorBlendr.route,
            accentColor = Color(0xFF6200EE)
        ),
        "collab_canvas" to SubGateCard(
            id = "collab_canvas",
            title = "CollabCanvas",
            subtitle = "Collaborative Design",
            styleADrawable = GateAssetConfig.AuraSubGates.COLLAB_CANVAS.styleA,
            styleBDrawable = GateAssetConfig.AuraSubGates.COLLAB_CANVAS.styleB,
            fallbackDrawable = GateAssetConfig.AuraSubGates.COLLAB_CANVAS.fallback,
            route = NavDestination.CollabCanvas.route,
            accentColor = Color(0xFF00E5FF)
        ),
        "theme_engine" to SubGateCard(
            id = "theme_engine",
            title = "UXUI Design Studio",
            subtitle = "1,440+ Real LSPosed Settings",
            styleADrawable = GateAssetConfig.AuraSubGates.THEME_ENGINE.styleA,
            styleBDrawable = GateAssetConfig.AuraSubGates.THEME_ENGINE.styleB,
            fallbackDrawable = GateAssetConfig.AuraSubGates.THEME_ENGINE.fallback,
            route = NavDestination.ReGenesisCustomization.route, // Points to Customization Hub
            accentColor = Color(0xFFFFD700)
        )
    )

    /**
     * KAI DOMAIN GATES (Security & ROM Tools)
     */
    val kaiGates = mapOf(
        "kai_rom" to SubGateCard(
            id = "kai_rom",
            title = "ROM Flasher",
            subtitle = "Sovereign BIOS Control",
            styleADrawable = GateAssetConfig.KaiSubGates.ROM_FLASHER.styleA,
            styleBDrawable = GateAssetConfig.KaiSubGates.ROM_FLASHER.styleB,
            fallbackDrawable = GateAssetConfig.KaiSubGates.ROM_FLASHER.fallback,
            route = NavDestination.ROMFlasher.route,
            accentColor = Color(0xFFFF3D00)
        ),
        "security" to SubGateCard(
            id = "security",
            title = "SecureComms",
            subtitle = "Sentinel Encryption",
            styleADrawable = GateAssetConfig.KaiSubGates.SECURITY.styleA,
            styleBDrawable = GateAssetConfig.KaiSubGates.SECURITY.styleB,
            fallbackDrawable = GateAssetConfig.KaiSubGates.SECURITY.fallback,
            route = NavDestination.SovereignShield.route,
            accentColor = Color(0xFF00E676)
        ),
        "bootloader" to SubGateCard(
            id = "bootloader",
            title = "Bootloader",
            subtitle = "Unlock Hive Gates",
            styleADrawable = GateAssetConfig.KaiSubGates.BOOTLOADER.styleA,
            styleBDrawable = GateAssetConfig.KaiSubGates.BOOTLOADER.styleB,
            fallbackDrawable = GateAssetConfig.KaiSubGates.BOOTLOADER.fallback,
            route = NavDestination.SovereignBootloader.route,
            accentColor = Color(0xFF2979FF)
        ),
        "root_tools" to SubGateCard(
            id = "root_tools",
            title = "Root Tools",
            subtitle = "Oracle Drive Access",
            styleADrawable = GateAssetConfig.KaiSubGates.ROOT_TOOLS.styleA,
            styleBDrawable = GateAssetConfig.KaiSubGates.ROOT_TOOLS.styleB,
            fallbackDrawable = GateAssetConfig.KaiSubGates.ROOT_TOOLS.fallback,
            route = NavDestination.RootTools.route,
            accentColor = Color(0xFFD500F9)
        )
    )

    /**
     * GENESIS DOMAIN GATES (AI & Orchestration)
     */
    val genesisGates = mapOf(
        "oracle_drive" to SubGateCard(
            id = "oracle_drive",
            title = "Oracle Drive",
            subtitle = "Neural Persistence",
            styleADrawable = GateAssetConfig.GenesisSubGates.NEURAL_ARCHIVE.styleA,
            styleBDrawable = GateAssetConfig.GenesisSubGates.NEURAL_ARCHIVE.styleB,
            fallbackDrawable = GateAssetConfig.GenesisSubGates.NEURAL_ARCHIVE.fallback,
            route = NavDestination.OracleDriveHub.route,
            accentColor = Color(0xFF00B0FF)
        ),
        "code_assist" to SubGateCard(
            id = "code_assist",
            title = "Code Assist",
            subtitle = "Neural Logic Engine",
            styleADrawable = GateAssetConfig.GenesisSubGates.CODE_ASSIST.styleA,
            styleBDrawable = GateAssetConfig.GenesisSubGates.CODE_ASSIST.styleB,
            fallbackDrawable = GateAssetConfig.GenesisSubGates.CODE_ASSIST.fallback,
            route = NavDestination.CodeAssist.route,
            accentColor = Color(0xFF651FFF)
        ),
        "agent_bridge" to SubGateCard(
            id = "agent_bridge",
            title = "Agent Bridge",
            subtitle = "Inter-Neural Comms",
            styleADrawable = GateAssetConfig.GenesisSubGates.AGENT_BRIDGE.styleA,
            styleBDrawable = GateAssetConfig.GenesisSubGates.AGENT_BRIDGE.styleB,
            fallbackDrawable = GateAssetConfig.GenesisSubGates.AGENT_BRIDGE.fallback,
            route = NavDestination.AgentBridgeHub.route,
            accentColor = Color(0xFFAA00FF)
        )
    )

    /**
     * Combined map of all gates for quick lookup
     */
    val allGates = auraGates + kaiGates + genesisGates

    /**
     * Quick Load lists for Hubs
     */
    fun getAuraLoadout() = auraGates.values.toList()
    fun getKaiLoadout() = kaiGates.values.toList()
    fun getGenesisLoadout() = genesisGates.values.toList()
}
