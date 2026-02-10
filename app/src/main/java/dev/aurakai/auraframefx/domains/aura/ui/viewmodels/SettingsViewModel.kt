package dev.aurakai.auraframefx.domains.aura.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.YukiHookModulePrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aurakai.auraframefx.domains.aura.OverlayManager
import timber.log.Timber

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val overlayManager: OverlayManager
) : ViewModel() {

    private val prefs = YukiHookModulePrefs.from(context)

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

    fun toggleFloatingAgentOverlay(enabled: Boolean) {
        viewModelScope.launch {
            if (enabled) {
                // Check permission first
                if (overlayManager.hasOverlayPermission(context)) {
                    _floatingAgentOverlayEnabled.value = true
                    prefs.putBoolean("floating_agent_overlay_enabled", true)
                    overlayManager.startOverlay(context)
                    Timber.i("SettingsViewModel: Floating agent overlay enabled")
                } else {
                    // Launch overlay permission settings
                    Timber.w("SettingsViewModel: Overlay permission not granted, opening settings")
                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:${context.packageName}")
                    ).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    context.startActivity(intent)
                    // Keep disabled until permission is granted
                    _floatingAgentOverlayEnabled.value = false
                    prefs.putBoolean("floating_agent_overlay_enabled", false)
                }
            } else {
                _floatingAgentOverlayEnabled.value = false
                prefs.putBoolean("floating_agent_overlay_enabled", false)
                overlayManager.stopOverlay(context)
                Timber.i("SettingsViewModel: Floating agent overlay disabled")
            }
        }
    }

    fun checkOverlayPermission(): Boolean {
        return overlayManager.hasOverlayPermission(context)
    }

    // Call this when returning from permission settings to sync state
    fun syncOverlayState() {
        viewModelScope.launch {
            val prefEnabled = prefs.getBoolean("floating_agent_overlay_enabled", false)
            val hasPermission = overlayManager.hasOverlayPermission(context)

            if (prefEnabled && hasPermission && !overlayManager.isOverlayActive()) {
                // User granted permission and overlay should be enabled
                overlayManager.startOverlay(context)
                _floatingAgentOverlayEnabled.value = true
            } else if (!hasPermission && overlayManager.isOverlayActive()) {
                // Permission was revoked
                overlayManager.stopOverlay(context)
                _floatingAgentOverlayEnabled.value = false
                prefs.putBoolean("floating_agent_overlay_enabled", false)
            }
        }
    }
}

