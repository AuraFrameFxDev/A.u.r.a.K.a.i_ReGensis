package dev.aurakai.auraframefx.system.quicksettings

import dev.aurakai.auraframefx.ui.QuickSettingsBackground

data class QuickSettingsConfig(
    val tiles: List<QuickSettingsTileConfig> = emptyList()
) {
    companion object {
        val DEFAULT = QuickSettingsConfig(
            tiles = listOf(
                QuickSettingsTileConfig("wifi", "Wi-Fi", true),
                QuickSettingsTileConfig("bluetooth", "Bluetooth", true),
                QuickSettingsTileConfig("flashlight", "Flashlight", true),
                QuickSettingsTileConfig("rotation", "Auto-rotate", true),
                QuickSettingsTileConfig("battery", "Battery Saver", true),
                QuickSettingsTileConfig("dnd", "Do Not Disturb", true),
                QuickSettingsTileConfig("aura_overlay", "Aura Overlay", true)
            )
        )
    }
}

data class QuickSettingsTileConfig(
    val id: String,
    val label: String,
    val visible: Boolean,
    val enabled: Boolean = true,
    val enableClicks: Boolean = true,
    val rippleEffect: Boolean = true,
    val background: QuickSettingsBackground? = null
)
