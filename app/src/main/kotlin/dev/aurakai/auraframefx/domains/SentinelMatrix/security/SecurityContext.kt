package dev.aurakai.auraframefx.domains.sentinelmatrix.security

import dev.aurakai.auraframefx.domains.sentinelmatrix.models.ThreatLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecurityContext @Inject constructor() {
    private val _threatLevel = MutableStateFlow(ThreatLevel.LOW)
    val threatLevel: StateFlow<ThreatLevel> = _threatLevel.asStateFlow()

    private val _shieldActive = MutableStateFlow(true)
    val shieldActive: StateFlow<Boolean> = _shieldActive.asStateFlow()

    fun evaluateThreat(content: String) {
        if (content.contains("critical", ignoreCase = true)) {
            _threatLevel.value = ThreatLevel.CRITICAL
        } else if (content.contains("warning", ignoreCase = true)) {
            _threatLevel.value = ThreatLevel.MEDIUM
        }
    }

    fun toggleShield() {
        _shieldActive.value = !_shieldActive.value
    }
}

class GuidanceDrone {
    enum class DroneType { RESTORATIVE, DEFENSIVE, SURVEILLANCE }
}

interface GuidanceDroneDispatcher {
    fun dispatchDrone(type: GuidanceDrone.DroneType, reason: String)
}
