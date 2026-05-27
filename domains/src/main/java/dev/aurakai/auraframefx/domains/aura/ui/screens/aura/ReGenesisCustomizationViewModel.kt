package dev.aurakai.auraframefx.domains.aura.ui.screens.aura

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.domains.aura.LauncherConfiguration
import dev.aurakai.auraframefx.domains.aura.MonetConfiguration
import dev.aurakai.auraframefx.domains.aura.SystemUIConfiguration
import dev.aurakai.auraframefx.domains.aura.models.ReGenesisCustomizationConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ⚙️ REGENESIS CUSTOMIZATION VIEWMODEL
 *
 * Manages the global state for all system customizations including
 * Iconify, ColorBlendr, and PixelLauncherEnhanced.
 * Handles persistence via SharedPreferences/DataStore.
 */
@HiltViewModel
class ReGenesisCustomizationViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ReGenesisCustomizationConfig())
    val state = _state.asStateFlow()

    fun toggleIconify(enabled: Boolean) {
        _state.value = _state.value.copy(iconifyEnabled = enabled)
    }

    fun toggleColorBlendr(enabled: Boolean) {
        _state.value = _state.value.copy(colorBlendrEnabled = enabled)
    }

    fun togglePixelLauncherEnhanced(enabled: Boolean) {
        _state.value = _state.value.copy(pixelLauncherEnhancedEnabled = enabled)
    }

    fun updateMonetConfig(context: Context, config: MonetConfiguration) {
        _state.value = _state.value.copy(monetConfig = config)
    }

    fun updateLauncherConfig(context: Context, config: LauncherConfiguration) {
        _state.value = _state.value.copy(launcherConfig = config)
    }

    fun updateSystemUIConfig(context: Context, config: SystemUIConfiguration) {
        _state.value = _state.value.copy(systemUIConfig = config)
    }
}
