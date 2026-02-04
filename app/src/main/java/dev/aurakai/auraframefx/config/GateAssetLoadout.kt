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
        "themes" to SubGateCard(
            id = "themes",
            title = "Themes",
            subtitle = "Theme Selection & Management",
            styleADrawable = GateAssetConfig.AuraSubGates.THEME_ENGINE.styleA,
            styleBDrawable = GateAssetConfig.AuraSubGates.THEME_ENGINE.styleB,
            fallbackDrawable = GateAssetConfig.AuraSubGates.THEME_ENGINE.fallback,
            route = NavDestination.ThemeEngine.route,
            accentColor = Color(0xFFFF6F00)
        ),
        "uxui_engine" to SubGateCard(
            id = "uxui_engine",
            title = "UXUI Engine",
            subtitle = "Iconify • ColorBlendr • PixelLauncher",
            styleADrawable = GateAssetConfig.AuraSubGates.THEME_ENGINE.styleA,
            styleBDrawable = GateAssetConfig.AuraSubGates.THEME_ENGINE.styleB,
            fallbackDrawable = GateAssetConfig.AuraSubGates.THEME_ENGINE.fallback,
            route = NavDestination.ReGenesisCustomization.route,
            accentColor = Color(0xFFFFD700)
        )
    )

    /**
     * KAI DOMAIN GATES (Ethical Governor + Security + Bootloader)
     */
    val kaiGates = mapOf(
        "ethical_governor" to SubGateCard(
            id = "ethical_governor",
            title = "Ethical Governor",
            subtitle = "9-Domain AI Oversight (Backend)",
            styleADrawable = GateAssetConfig.KaiSubGates.SECURITY.styleA,
            styleBDrawable = GateAssetConfig.KaiSubGates.SECURITY.styleB,
            fallbackDrawable = GateAssetConfig.KaiSubGates.SECURITY.fallback,
            route = NavDestination.SecurityCenter.route, // Monitors genesis_ethical_governor.py
            accentColor = Color(0xFFFFD700) // Gold for guardian
        ),
        "security_shield" to SubGateCard(
            id = "security_shield",
            title = "Security Shield",
            subtitle = "Encryption • VPN • Threat Monitor",
            styleADrawable = GateAssetConfig.KaiSubGates.SECURITY.styleA,
            styleBDrawable = GateAssetConfig.KaiSubGates.SECURITY.styleB,
            fallbackDrawable = GateAssetConfig.KaiSubGates.SECURITY.fallback,
            route = NavDestination.SovereignShield.route,
            accentColor = Color(0xFF00E676)
        ),
        "bootloader" to SubGateCard(
            id = "bootloader",
            title = "Bootloader",
            subtitle = "System BIOS Control",
            styleADrawable = GateAssetConfig.KaiSubGates.BOOTLOADER.styleA,
            styleBDrawable = GateAssetConfig.KaiSubGates.BOOTLOADER.styleB,
            fallbackDrawable = GateAssetConfig.KaiSubGates.BOOTLOADER.fallback,
            route = NavDestination.BootloaderManager.route,
            accentColor = Color(0xFF2979FF)
        ),
        "rom_tools" to SubGateCard(
            id = "rom_tools",
            title = "ROM Tools",
            subtitle = "Flasher • Editor • Recovery",
            styleADrawable = GateAssetConfig.KaiSubGates.ROM_FLASHER.styleA,
            styleBDrawable = GateAssetConfig.KaiSubGates.ROM_FLASHER.styleB,
            fallbackDrawable = GateAssetConfig.KaiSubGates.ROM_FLASHER.fallback,
            route = NavDestination.ROMFlasher.route, // Can be ROM hub later
            accentColor = Color(0xFFFF3D00)
        )
    )

    /**
     * GENESIS DOMAIN GATES (Oracle Drive = Level 1)
     * Code Assist, Orchestrations, Creation Tools are INSIDE Oracle Drive hub
     * Note: Agent Nexus is a SEPARATE domain (Agent HQ with monitoring)
     */
    val genesisGates = mapOf(
        "oracle_drive" to SubGateCard(
            id = "oracle_drive",
            title = "Oracle Drive",
            subtitle = "Code Assist • Orchestrations • Creation",
            styleADrawable = GateAssetConfig.GenesisSubGates.NEURAL_ARCHIVE.styleA,
            styleBDrawable = GateAssetConfig.GenesisSubGates.NEURAL_ARCHIVE.styleB,
            fallbackDrawable = GateAssetConfig.GenesisSubGates.NEURAL_ARCHIVE.fallback,
            route = NavDestination.OracleDriveHub.route, // Hub contains all Genesis tools
            accentColor = Color(0xFF00B0FF) // Cyan - Genesis
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
