package dev.aurakai.auraframefx.domains.kai.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ShieldState(
    val isAdBlockActive: Boolean = false,
    val isTelemetryBlocked: Boolean = false,
    val isSensorCloakActive: Boolean = false,
    val isPrivateDnsEnabled: Boolean = true,
    val isShizukuBridgeActive: Boolean = true,
    val blockedRequestsCount: Int = 1420,
    val privacyScore: Int = 85
)

@HiltViewModel
class SovereignShieldViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(ShieldState())
    val state: StateFlow<ShieldState> = _state.asStateFlow()

    fun toggleAdBlock() {
        _state.value = _state.value.copy(
            isAdBlockActive = !_state.value.isAdBlockActive,
            privacyScore = if (!_state.value.isAdBlockActive) _state.value.privacyScore + 5 else _state.value.privacyScore - 5
        )
    }

    fun toggleTelemetry() {
        _state.value = _state.value.copy(
            isTelemetryBlocked = !_state.value.isTelemetryBlocked,
            privacyScore = if (!_state.value.isTelemetryBlocked) _state.value.privacyScore + 10 else _state.value.privacyScore - 10
        )
    }

    fun toggleSensorCloak() {
        _state.value = _state.value.copy(isSensorCloakActive = !_state.value.isSensorCloakActive)
    }

    fun toggleShizukuBridge() {
        _state.value =
            _state.value.copy(isShizukuBridgeActive = !_state.value.isShizukuBridgeActive)
    }
}
