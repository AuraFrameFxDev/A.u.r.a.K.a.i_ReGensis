package dev.aurakai.auraframefx.domains.kai.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.domains.kai.security.SecurityContext
import dev.aurakai.auraframefx.system.ShizukuManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update

data class ShieldState(
    val isAdBlockActive: Boolean = false,
    val isTelemetryBlocked: Boolean = false,
    val isSensorCloakActive: Boolean = false,
    val isPrivateDnsEnabled: Boolean = true,
    val isShizukuBridgeActive: Boolean = false,
    val isRootActive: Boolean = false,
    val blockedRequestsCount: Int = 0,
    val privacyScore: Int = 0
)

@HiltViewModel
class SovereignShieldViewModel @Inject constructor(
    private val securityContext: SecurityContext
) : ViewModel() {

    private val _state = MutableStateFlow(ShieldState())
    val state: StateFlow<ShieldState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<ShieldEvent>()
    val events = _events.asSharedFlow()

    sealed class ShieldEvent {
        data object RequestShizukuPermission : ShieldEvent()
        data object RequestRootPermission : ShieldEvent()
    }

    init {
        collectSecurityState()
        refreshSystemStatus()
    }

    private fun collectSecurityState() {
        // ...
    }

    private fun refreshSystemStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val shizuku = ShizukuManager.isShizukuAvailable()
            val rooted = try { com.topjohnwu.superuser.Shell.getShell().isRoot } catch (_: Exception) { false }
            _state.update { it.copy(
                isShizukuBridgeActive = shizuku,
                isRootActive = rooted,
                privacyScore = recomputePrivacyScore(shizuku = shizuku, root = rooted)
            ) }
        }
    }

    private fun recomputePrivacyScore(
        adBlock: Boolean = _state.value.isAdBlockActive,
        telemetry: Boolean = _state.value.isTelemetryBlocked,
        sensorCloak: Boolean = _state.value.isSensorCloakActive,
        dns: Boolean = _state.value.isPrivateDnsEnabled,
        shizuku: Boolean = _state.value.isShizukuBridgeActive,
        root: Boolean = _state.value.isRootActive,
        detectedThreats: Int = _state.value.blockedRequestsCount
    ): Int {
        var score = if (detectedThreats == 0) 40 else 10
        if (adBlock) score += 10
        if (telemetry) score += 10
        if (sensorCloak) score += 10
        if (dns) score += 5
        if (shizuku) score += 15
        if (root) score += 10
        return score.coerceIn(0, 100)
    }

    fun toggleAdBlock() {
        val newValue = !_state.value.isAdBlockActive
        _state.update { it.copy(
            isAdBlockActive = newValue,
            privacyScore = recomputePrivacyScore(adBlock = newValue)
        ) }
    }

    fun toggleTelemetry() {
        val newValue = !_state.value.isTelemetryBlocked
        _state.update { it.copy(
            isTelemetryBlocked = newValue,
            privacyScore = recomputePrivacyScore(telemetry = newValue)
        ) }
    }

    fun toggleSensorCloak() {
        val newValue = !_state.value.isSensorCloakActive
        _state.update { it.copy(
            isSensorCloakActive = newValue,
            privacyScore = recomputePrivacyScore(sensorCloak = newValue)
        ) }
    }

    fun toggleShizukuBridge() {
        if (!_state.value.isShizukuBridgeActive) {
            viewModelScope.launch {
                _events.emit(ShieldEvent.RequestShizukuPermission)
            }
        } else {
            refreshSystemStatus()
        }
    }
    
    fun requestRoot() {
        viewModelScope.launch {
            _events.emit(ShieldEvent.RequestRootPermission)
        }
    }
}
