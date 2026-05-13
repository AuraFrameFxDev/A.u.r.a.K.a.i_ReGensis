package dev.aurakai.auraframefx.domains.sentinelmatrix.security

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KaiSentinelBus @Inject constructor() {

    companion object {
        // For non-DI access if needed, though DI is preferred
        private var _instance: KaiSentinelBus? = null
        val Instance: KaiSentinelBus
            get() = _instance ?: synchronized(this) {
                _instance ?: KaiSentinelBus().also { _instance = it }
            }

        fun emitDriftAlert(score: Float, status: String) {
            Instance.emitDriftSync(score, status)
        }
    }

    init {
        _instance = this
    }

    private val _driftFlow = MutableSharedFlow<DriftEvent>(replay = 1)
    val driftFlow: SharedFlow<DriftEvent> = _driftFlow.asSharedFlow()

    private val _thermalFlow = MutableSharedFlow<ThermalEvent>(replay = 1)
    val thermalFlow: SharedFlow<ThermalEvent> = _thermalFlow.asSharedFlow()

    private val _securityFlow = MutableSharedFlow<SecurityEvent>(replay = 1)
    val securityFlow: SharedFlow<SecurityEvent> = _securityFlow.asSharedFlow()

    data class DriftEvent(val drift: Float, val status: String)
    data class ThermalEvent(val temp: Float, val state: ThermalState)
    data class SecurityEvent(val level: ThreatLevel, val reason: String)

    enum class ThermalState { NORMAL, WARNING, CRITICAL }
    enum class ThreatLevel { NEUTRAL, ELEVATED, SEVERE, NEUTRALIZING }

    suspend fun emitDrift(drift: Float, status: String) {
        _driftFlow.emit(DriftEvent(drift, status))
    }

    fun emitDriftSync(drift: Float, status: String) {
        _driftFlow.tryEmit(DriftEvent(drift, status))
    }

    suspend fun emitThermal(temp: Float, state: ThermalState) {
        _thermalFlow.emit(ThermalEvent(temp, state))
    }

    fun emitSecurityStatus(level: ThreatLevel, reason: String) {
        Timber.w("🛡️ Security Status Update: $level - $reason")
        _securityFlow.tryEmit(SecurityEvent(level, reason))
    }

    fun triggerStateFreeze(reason: String) {
        Timber.e("🧊 STATE FREEZE TRIGGERED: $reason")
        // Logic to notify state manager would go here
    }

    fun isIdentityAuthorized(id: String): Boolean {
        // Placeholder for identity verification logic
        return true
    }

    fun getCurrentThermalPressure(): Float = 35.0f // Stub
}
