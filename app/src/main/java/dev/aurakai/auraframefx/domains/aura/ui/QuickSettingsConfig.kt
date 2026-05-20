package dev.aurakai.auraframefx.domains.aura.ui

/**
 * Configuration for Quick Settings customization
 */
data class QuickSettingsConfig(
    val tiles: List<QuickSettingsTileConfig> = emptyList(),
    val columns: Int = 4,
    val rows: Int = 2,
    val showLabels: Boolean = true
) {
    companion object {
        val DEFAULT = QuickSettingsConfig()
    }
}

/**
 * Configuration for an individual Quick Settings tile
 */
data class QuickSettingsTileConfig(
    val id: String,
    val label: String? = null,
    val enabled: Boolean = true,
    val color: String? = null
)
