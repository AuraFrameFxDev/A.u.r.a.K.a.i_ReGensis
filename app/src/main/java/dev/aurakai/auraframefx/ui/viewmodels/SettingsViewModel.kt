package dev.aurakai.auraframefx.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.YukiHookModulePrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val prefs = YukiHookModulePrefs.from(context)

    private val _hapticEnabled = MutableStateFlow(prefs.getBoolean("haptic_feedback", true))
    val hapticEnabled = _hapticEnabled.asStateFlow()

    private val _ethicsSensitivity = MutableStateFlow(prefs.getFloat("ethics_sensitivity", 0.7f))
    val ethicsSensitivity = _ethicsSensitivity.asStateFlow()

    private val _nexusSyncInterval = MutableStateFlow(prefs.getInt("nexus_sync_interval", 15))
    val nexusSyncInterval = _nexusSyncInterval.asStateFlow()

    private val _overlayTransparency = MutableStateFlow(prefs.getFloat("overlay_transparency", 0.85f))
    val overlayTransparency = _overlayTransparency.asStateFlow()

    private val _isBioLockEnabled = MutableStateFlow(prefs.getBoolean("bio_lock_enabled", false))
    val isBioLockEnabled = _isBioLockEnabled.asStateFlow()

    fun toggleHaptic(enabled: Boolean) {
        viewModelScope.launch {
            _hapticEnabled.value = enabled
            prefs.putBoolean("haptic_feedback", enabled)
        }
    }

    fun setEthicsSensitivity(value: Float) {
        viewModelScope.launch {
            _ethicsSensitivity.value = value
            prefs.putFloat("ethics_sensitivity", value)
        }
    }

    fun setSyncInterval(minutes: Int) {
        viewModelScope.launch {
            _nexusSyncInterval.value = minutes
            prefs.putInt("nexus_sync_interval", minutes)
        }
    }

    fun setOverlayTransparency(value: Float) {
        viewModelScope.launch {
            _overlayTransparency.value = value
            prefs.putFloat("overlay_transparency", value)
        }
    }

    fun toggleBioLock(enabled: Boolean) {
        viewModelScope.launch {
            _isBioLockEnabled.value = enabled
            prefs.putBoolean("bio_lock_enabled", enabled)
        }
    }
}
