package dev.aurakai.auraframefx.domains.aura.ui.viewmodels

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.provider.Settings
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aurakai.auraframefx.core.ui.overlays.OverlayManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val overlayManager: OverlayManager
) : ViewModel() {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("genesis_settings", Context.MODE_PRIVATE)

    private val _hapticEnabled = MutableStateFlow(prefs.getBoolean("haptic_feedback", true))
    val hapticEnabled = _hapticEnabled.asStateFlow()

    private val _ethicsSensitivity = MutableStateFlow(prefs.getFloat("ethics_sensitivity", 0.7f))
    val ethicsSensitivity = _ethicsSensitivity.asStateFlow()

    private val _nexusSyncInterval = MutableStateFlow(prefs.getInt("nexus_sync_interval", 15))
    val nexusSyncInterval = _nexusSyncInterval.asStateFlow()

    private val _overlayTransparency =
        MutableStateFlow(prefs.getFloat("overlay_transparency", 0.85f))
    val overlayTransparency = _overlayTransparency.asStateFlow()

    private val _isBioLockEnabled = MutableStateFlow(prefs.getBoolean("bio_lock_enabled", false))
    val isBioLockEnabled = _isBioLockEnabled.asStateFlow()

    private val _floatingAgentOverlayEnabled =
        MutableStateFlow(prefs.getBoolean("floating_agent_overlay_enabled", false))
    val floatingAgentOverlayEnabled = _floatingAgentOverlayEnabled.asStateFlow()

    fun toggleHaptic(enabled: Boolean) {
        viewModelScope.launch {
            _hapticEnabled.value = enabled
            prefs.edit().putBoolean("haptic_feedback", enabled).apply()
        }
    }

    fun setEthicsSensitivity(value: Float) {
        viewModelScope.launch {
            _ethicsSensitivity.value = value
            prefs.edit().putFloat("ethics_sensitivity", value).apply()
        }
    }

    fun setSyncInterval(minutes: Int) {
        viewModelScope.launch {
            _nexusSyncInterval.value = minutes
            prefs.edit().putInt("nexus_sync_interval", minutes).apply()
        }
    }

    fun setOverlayTransparency(value: Float) {
        viewModelScope.launch {
            _overlayTransparency.value = value
            prefs.edit().putFloat("overlay_transparency", value).apply()
        }
    }

    fun toggleBioLock(enabled: Boolean) {
        viewModelScope.launch {
            _isBioLockEnabled.value = enabled
            prefs.edit().putBoolean("bio_lock_enabled", enabled).apply()
        }
    }

    fun toggleFloatingAgentOverlay(enabled: Boolean) {
        viewModelScope.launch {
            if (enabled) {
                if (overlayManager.hasOverlayPermission(context)) {
                    _floatingAgentOverlayEnabled.value = true
                    prefs.edit().putBoolean("floating_agent_overlay_enabled", true).apply()
                    overlayManager.startOverlay(context)
                } else {
                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        "package:${context.packageName}".toUri()
                    ).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    context.startActivity(intent)
                    _floatingAgentOverlayEnabled.value = false
                    prefs.edit().putBoolean("floating_agent_overlay_enabled", false).apply()
                }
            } else {
                _floatingAgentOverlayEnabled.value = false
                prefs.edit().putBoolean("floating_agent_overlay_enabled", false).apply()
                overlayManager.stopOverlay(context)
            }
        }
    }

    fun checkOverlayPermission(): Boolean {
        return overlayManager.hasOverlayPermission(context)
    }

    fun syncOverlayState() {
        viewModelScope.launch {
            val isEnabled = prefs.getBoolean("floating_agent_overlay_enabled", false)
            val hasPermission = overlayManager.hasOverlayPermission(context)
            if (isEnabled && !hasPermission) {
                _floatingAgentOverlayEnabled.value = false
                prefs.edit().putBoolean("floating_agent_overlay_enabled", false).apply()
            }
        }
    }
}
