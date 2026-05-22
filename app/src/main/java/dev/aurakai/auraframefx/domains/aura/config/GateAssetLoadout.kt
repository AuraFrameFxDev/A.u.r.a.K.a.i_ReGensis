package dev.aurakai.auraframefx.domains.aura.config

import dev.aurakai.auraframefx.domains.aura.ui.components.SubGateCard

object GateAssetLoadout {
    fun getNexusSubGates(): List<SubGateCard> = emptyList()
    fun getAuraLoadout(): List<SubGateCard> = emptyList()

    fun getKaiLoadout(): List<SubGateCard> = listOf(
        SubGateCard(
            route = "kai/security",
            title = "Security",
            subtitle = "Threat Monitor & Audit",
            styleADrawable = "ic_security_neon_a", // Aligns with your local asset resource definitions
            styleBDrawable = "ic_security_neon_b"
        ),
        SubGateCard(
            route = "kai/root",
            title = "Root Tools",
            subtitle = "Root & Bootloader Access",
            styleADrawable = "ic_root_neon_a",
            styleBDrawable = "ic_root_neon_b"
        ),
        SubGateCard(
            route = "kai/recovery",
            title = "Recovery",
            subtitle = "TWRP & System Rescue",
            styleADrawable = "ic_recovery_neon_a",
            styleBDrawable = "ic_recovery_neon_b"
        ),
        SubGateCard(
            route = "kai/rom",
            title = "ROM Flasher",
            subtitle = "ROM & Firmware Manager",
            styleADrawable = "ic_rom_neon_a",
            styleBDrawable = "ic_rom_neon_b"
        ),
        SubGateCard(
            route = "kai/modules",
            title = "Modules",
            subtitle = "LSPosed & Xposed Modules",
            styleADrawable = "ic_modules_neon_a",
            styleBDrawable = "ic_modules_neon_b"
        ),
        SubGateCard(
            route = "kai/vpn",
            title = "VPN",
            subtitle = "VPN & Ad Blocker",
            styleADrawable = "ic_vpn_neon_a",
            styleBDrawable = "ic_vpn_neon_b"
        ),
        SubGateCard(
            route = "kai/bootloader",
            title = "Bootloader",
            subtitle = "Bootloader Control Panel",
            styleADrawable = "ic_bootloader_neon_a",
            styleBDrawable = "ic_bootloader_neon_b"
        ),
        SubGateCard(
            route = "kai/lsposed",
            title = "LSPosed",
            subtitle = "Framework & Hooks",
            styleADrawable = "ic_lsposed_neon_a",
            styleBDrawable = "ic_lsposed_neon_b"
        )
    )
}
