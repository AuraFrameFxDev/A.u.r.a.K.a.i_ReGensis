package dev.aurakai.auraframefx.domains.aura.config

import dev.aurakai.auraframefx.core.ui.theme.GhostCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonGreen
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.core.ui.theme.NeonPurple
import dev.aurakai.auraframefx.domains.aura.ui.components.SubGateCard
import timber.log.Timber

/**
 * 🚀 GATE ASSET LOADOUT — Primary data provider for Hub Carousels
 */
object GateAssetLoadout {

    fun getNexusSubGates(): List<SubGateCard> = listOf(
        SubGateCard(
            id = "nexus_constellation",
            title = "Lineage",
            subtitle = "Ancestral Agent Tree",
            styleADrawable = GateAssetConfig.NexusSubGates.CONSTELLATION.styleA,
            styleBDrawable = GateAssetConfig.NexusSubGates.CONSTELLATION.styleB,
            fallbackDrawable = GateAssetConfig.NexusSubGates.CONSTELLATION.fallback,
            route = "lineage_map",
            accentColor = NeonGreen
        ),
        SubGateCard(
            id = "nexus_monitoring",
            title = "Monitoring",
            subtitle = "Live Dataflow Pulse",
            styleADrawable = GateAssetConfig.NexusSubGates.MONITORING.styleA,
            styleBDrawable = GateAssetConfig.NexusSubGates.MONITORING.styleB,
            fallbackDrawable = GateAssetConfig.NexusSubGates.MONITORING.fallback,
            route = "dataflow_analysis",
            accentColor = NeonGreen
        ),
        SubGateCard(
            id = "nexus_sphere_grid",
            title = "Sphere Grid",
            subtitle = "Agent Mastery Dashboard",
            styleADrawable = GateAssetConfig.NexusSubGates.SPHERE_GRID.styleA,
            styleBDrawable = GateAssetConfig.NexusSubGates.SPHERE_GRID.styleB,
            fallbackDrawable = GateAssetConfig.NexusSubGates.SPHERE_GRID.fallback,
            route = "sphere_grid",
            accentColor = NeonGreen
        ),
        SubGateCard(
            id = "nexus_fusion",
            title = "Fusion Mode",
            subtitle = "Agent Synergy Patterns",
            styleADrawable = GateAssetConfig.NexusSubGates.FUSION_MODE.styleA,
            styleBDrawable = GateAssetConfig.NexusSubGates.FUSION_MODE.styleB,
            fallbackDrawable = GateAssetConfig.NexusSubGates.FUSION_MODE.fallback,
            route = "fusion_mode",
            accentColor = NeonGreen
        ),
        SubGateCard(
            id = "nexus_tasks",
            title = "Tasks",
            subtitle = "Mission Dispatch Hub",
            styleADrawable = GateAssetConfig.NexusSubGates.TASK_ASSIGNMENT.styleA,
            styleBDrawable = GateAssetConfig.NexusSubGates.TASK_ASSIGNMENT.styleB,
            fallbackDrawable = GateAssetConfig.NexusSubGates.TASK_ASSIGNMENT.fallback,
            route = "task_assignment",
            accentColor = NeonGreen
        )
    )

    fun getAuraLoadout(): List<SubGateCard> = listOf(
        SubGateCard(
            id = "aura_chroma",
            title = "ChromaCore",
            subtitle = "Theme & Color Engine",
            styleADrawable = GateAssetConfig.AuraSubGates.CHROMA_CORE.styleA,
            styleBDrawable = GateAssetConfig.AuraSubGates.CHROMA_CORE.styleB,
            fallbackDrawable = GateAssetConfig.AuraSubGates.CHROMA_CORE.fallback,
            route = "chroma_forge",
            accentColor = NeonMagenta
        ),
        SubGateCard(
            id = "aura_canvas",
            title = "CollabCanvas",
            subtitle = "Collaborative Design",
            styleADrawable = GateAssetConfig.AuraSubGates.COLLAB_CANVAS.styleA,
            styleBDrawable = GateAssetConfig.AuraSubGates.COLLAB_CANVAS.styleB,
            fallbackDrawable = GateAssetConfig.AuraSubGates.COLLAB_CANVAS.fallback,
            route = "collab_canvas",
            accentColor = NeonMagenta
        ),
        SubGateCard(
            id = "aura_notch",
            title = "Notch Bar",
            subtitle = "Status Pulse Config",
            styleADrawable = GateAssetConfig.AuraSubGates.NOTCH_BAR.styleA,
            styleBDrawable = GateAssetConfig.AuraSubGates.NOTCH_BAR.styleB,
            fallbackDrawable = GateAssetConfig.AuraSubGates.NOTCH_BAR.fallback,
            route = "notch_bar",
            accentColor = NeonMagenta
        ),
        SubGateCard(
            id = "aura_terminal",
            title = "Terminal",
            subtitle = "Script & Code Forge",
            styleADrawable = GateAssetConfig.AuraSubGates.TERMINAL.styleA,
            styleBDrawable = GateAssetConfig.AuraSubGates.TERMINAL.styleB,
            fallbackDrawable = GateAssetConfig.AuraSubGates.TERMINAL.fallback,
            route = "terminal",
            accentColor = NeonMagenta
        )
    )

    fun getKaiLoadout(): List<SubGateCard> {
        val loadout = listOf(
            SubGateCard(
                id = "kai_security",
                title = "Security",
                subtitle = "Threat Monitor & Audit",
                styleADrawable = GateAssetConfig.KaiSubGates.SECURITY.styleA,
                styleBDrawable = GateAssetConfig.KaiSubGates.SECURITY.styleB,
                fallbackDrawable = GateAssetConfig.KaiSubGates.SECURITY.fallback,
                route = "kai/security",
                accentColor = GhostCyan
            ),
            SubGateCard(
                id = "kai_root",
                title = "Root Tools",
                subtitle = "Root & Bootloader Access",
                styleADrawable = GateAssetConfig.KaiSubGates.ROOT_TOOLS.styleA,
                styleBDrawable = GateAssetConfig.KaiSubGates.ROOT_TOOLS.styleB,
                fallbackDrawable = GateAssetConfig.KaiSubGates.ROOT_TOOLS.fallback,
                route = "kai/root",
                accentColor = GhostCyan
            ),
            SubGateCard(
                id = "kai_recovery",
                title = "Recovery",
                subtitle = "TWRP & System Rescue",
                styleADrawable = GateAssetConfig.KaiSubGates.RECOVERY.styleA,
                styleBDrawable = GateAssetConfig.KaiSubGates.RECOVERY.styleB,
                fallbackDrawable = GateAssetConfig.KaiSubGates.RECOVERY.fallback,
                route = "kai/recovery",
                accentColor = GhostCyan
            ),
            SubGateCard(
                id = "kai_rom",
                title = "ROM Flasher",
                subtitle = "ROM & Firmware Manager",
                styleADrawable = GateAssetConfig.KaiSubGates.ROM_FLASHER.styleA,
                styleBDrawable = GateAssetConfig.KaiSubGates.ROM_FLASHER.styleB,
                fallbackDrawable = GateAssetConfig.KaiSubGates.ROM_FLASHER.fallback,
                route = "kai/rom",
                accentColor = GhostCyan
            ),
            SubGateCard(
                id = "kai_modules",
                title = "Modules",
                subtitle = "LSPosed & Xposed Modules",
                styleADrawable = GateAssetConfig.KaiSubGates.MODULE_MANAGER.styleA,
                styleBDrawable = GateAssetConfig.KaiSubGates.MODULE_MANAGER.styleB,
                fallbackDrawable = GateAssetConfig.KaiSubGates.MODULE_MANAGER.fallback,
                route = "kai/modules",
                accentColor = GhostCyan
            ),
            SubGateCard(
                id = "kai_vpn",
                title = "VPN",
                subtitle = "VPN & Ad Blocker",
                styleADrawable = GateAssetConfig.KaiSubGates.VPN.styleA,
                styleBDrawable = GateAssetConfig.KaiSubGates.VPN.styleB,
                fallbackDrawable = GateAssetConfig.KaiSubGates.VPN.fallback,
                route = "kai/vpn",
                accentColor = GhostCyan
            ),
            SubGateCard(
                id = "kai_bootloader",
                title = "Bootloader",
                subtitle = "Bootloader Control Panel",
                styleADrawable = GateAssetConfig.KaiSubGates.BOOTLOADER.styleA,
                styleBDrawable = GateAssetConfig.KaiSubGates.BOOTLOADER.styleB,
                fallbackDrawable = GateAssetConfig.KaiSubGates.BOOTLOADER.fallback,
                route = "kai/bootloader",
                accentColor = GhostCyan
            ),
            SubGateCard(
                id = "kai_lsposed",
                title = "LSPosed",
                subtitle = "Framework & Hooks",
                styleADrawable = GateAssetConfig.KaiSubGates.LSPOSED.styleA,
                styleBDrawable = GateAssetConfig.KaiSubGates.LSPOSED.styleB,
                fallbackDrawable = GateAssetConfig.KaiSubGates.LSPOSED.fallback,
                route = "kai/lsposed",
                accentColor = GhostCyan
            )
        )

        if (loadout.isEmpty()) {
            Timber.tag("GateAsset").e("❌ CRITICAL: getKaiLoadout() is returning an empty list!")
        } else {
            Timber.tag("GateAsset")
                .i("🛡️ getKaiLoadout() initialized with ${loadout.size} sub-gates.")
        }

        return loadout
    }

    fun getGenesisLoadout(): List<SubGateCard> = listOf(
        SubGateCard(
            id = "genesis_code",
            title = "Code Assist",
            subtitle = "AI-Powered Development",
            styleADrawable = GateAssetConfig.GenesisSubGates.CODE_ASSIST.styleA,
            styleBDrawable = GateAssetConfig.GenesisSubGates.CODE_ASSIST.styleB,
            fallbackDrawable = GateAssetConfig.GenesisSubGates.CODE_ASSIST.fallback,
            route = "code_assist",
            accentColor = NeonPurple
        ),
        SubGateCard(
            id = "genesis_neural",
            title = "Neural Archive",
            subtitle = "Context & Memory Vault",
            styleADrawable = GateAssetConfig.GenesisSubGates.NEURAL_ARCHIVE.styleA,
            styleBDrawable = GateAssetConfig.GenesisSubGates.NEURAL_ARCHIVE.styleB,
            fallbackDrawable = GateAssetConfig.GenesisSubGates.NEURAL_ARCHIVE.fallback,
            route = "neural_archive",
            accentColor = NeonPurple
        )
    )
}

