package dev.aurakai.auraframefx.domains.aura.models

import dev.aurakai.auraframefx.domains.aura.LauncherConfiguration
import dev.aurakai.auraframefx.domains.aura.MonetConfiguration
import dev.aurakai.auraframefx.domains.aura.SystemUIConfiguration

/**
 * ⚙️ REGENESIS CUSTOMIZATION CONFIG
 *
 * Top-level state model for the ReGenesis customization suite.
 * Tracks which integration modules (Iconify, ColorBlendr, PixelLauncherEnhanced) are active.
 */
data class ReGenesisCustomizationConfig(
    val iconifyEnabled: Boolean = false,
    val colorBlendrEnabled: Boolean = false,
    val pixelLauncherEnhancedEnabled: Boolean = false,
    val activeThemePackage: String = "",
    val customAccentColor: String = "#00E5FF",
    val lastModified: Long = System.currentTimeMillis(),
    val monetConfig: MonetConfiguration = MonetConfiguration(),
    val launcherConfig: LauncherConfiguration = LauncherConfiguration(),
    val systemUIConfig: SystemUIConfiguration = SystemUIConfiguration()
)
