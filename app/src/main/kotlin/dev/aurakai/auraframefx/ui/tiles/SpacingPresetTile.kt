
package dev.aurakai.auraframefx.ui.tiles

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import dev.aurakai.auraframefx.R // Assuming R file exists for resources
import dev.aurakai.auraframefx.domains.aura.lab.CustomizationPreferences
import dev.aurakai.auraframefx.domains.aura.lab.SpacingConfig
import dev.aurakai.auraframefx.domains.aura.lab.Presets

private const val TAG = "SpacingPresetTile"

// This is a simplified example. In a real app, you'd likely use a ViewModel
// or a centralized state management system to store and apply the selected preset.
object CurrentSpacingPreset { // Simple singleton to hold current preset
    var currentPreset: SpacingConfig = Presets.default
        private set

    fun setPreset(context: Context, preset: SpacingConfig) {
        currentPreset = preset
        CustomizationPreferences.saveSpacingConfig(context, preset) // Save persistently
        Log.d(TAG, "Spacing preset set to: ${preset.javaClass.simpleName} and saved persistently.")
        // In a real application, this would trigger an update to the UI system
        // For now, it just logs the change.
    }

    fun getNextPreset(): SpacingConfig {
        return when (currentPreset) {
            Presets.default -> Presets.comfortable
            Presets.comfortable -> Presets.spacious
            Presets.spacious -> Presets.compact
            Presets.compact -> Presets.default
            else -> Presets.default // Fallback
        }
    }
}

class SpacingPresetTile : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        Log.d(TAG, "SpacingPresetTile: onStartListening")
        // Load current preset from preferences and update CurrentSpacingPreset
        val savedConfig = CustomizationPreferences.getSpacingConfig(this.applicationContext)
        CurrentSpacingPreset.currentPreset = savedConfig
        updateTile()
    }

    override fun onClick() {
        super.onClick()
        Log.d(TAG, "SpacingPresetTile: onClick")

        val newPreset = CurrentSpacingPreset.getNextPreset()
        CurrentSpacingPreset.setPreset(this.applicationContext, newPreset)

        updateTile()
    }

    private fun updateTile() {
        val tile = qsTile
        if (tile == null) {
            Log.e(TAG, "qsTile is null in updateTile")
            return
        }

        val currentPresetName = when (CurrentSpacingPreset.currentPreset) {
            Presets.compact -> "Compact"
            Presets.default -> "Default"
            Presets.comfortable -> "Comfortable"
            Presets.spacious -> "Spacious"
            else -> "Custom"
        }

        tile.label = "Spacing: $currentPresetName"
        tile.icon = Icon.createWithResource(this, R.drawable.ic_spacing_preset) // Assuming an icon exists
        tile.state = Tile.STATE_ACTIVE // Or Tile.STATE_INACTIVE / Tile.STATE_UNAVAILABLE based on context
        tile.updateTile()
        Log.d(TAG, "Tile updated: Spacing: $currentPresetName")
    }

    override fun onTileAdded() {
        super.onTileAdded()
        Log.d(TAG, "SpacingPresetTile: onTileAdded")
        updateTile()
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
        Log.d(TAG, "SpacingPresetTile: onTileRemoved")
    }
}
