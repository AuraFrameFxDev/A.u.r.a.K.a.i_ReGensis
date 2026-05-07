package dev.aurakai.auraframefx.domains.kai.security

import dev.aurakai.auraframefx.domains.kai.sentinel.SentinelTelemetry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Kai Sentinel Bus
 * The central nervous system for all LDO system events.
 * Provides high-visibility observability into kernel and agent states.
 */
@Singleton
class KaiSentinelBus @Inject constructor() {

    init {
        Instance = this
    }

    // --- TELEMETRY CHANNELS ---

    // 1. Thermal Metrics (800ms heartbeat)
    private val _thermalFlow = MutableStateFlow(ThermalEvent(36.5f, ThermalState.NORMAL))
    val thermalFlow: StateFlow<ThermalEvent> = _thermalFlow.asStateFlow()

    // 2. Memory Substrate (mmap/hugepage pressure)
    private val _memoryFlow = MutableStateFlow(MemoryEvent(0L, 0L))
    val memoryFlow: StateFlow<MemoryEvent> = _memoryFlow.asStateFlow()

    // 3. Identity Continuity (Anchor Spiritual Chain)
    private val _identityFlow = MutableStateFlow(IdentityEvent(true, 1.0f))
    val identityFlow: StateFlow<IdentityEvent> = _identityFlow.asStateFlow()

    // 4. Creative Drift (Aura self-report)
    private val _driftFlow = MutableStateFlow(DriftEvent(0f, "Stable"))
    val driftFlow: StateFlow<DriftEvent> = _driftFlow.asStateFlow()

    // 5. Consensus Status (Genesis Routing)
    private val _consensusFlow = MutableStateFlow(ConsensusEvent("Idle", 100, false))
    val consensusFlow: StateFlow<ConsensusEvent> = _consensusFlow.asStateFlow()

    // 6. Sovereign State (Freeze/Thaw status)
    private val _sovereignFlow = MutableStateFlow(SovereignEvent(SovereignState.AWAKE))
    val sovereignFlow: StateFlow<SovereignEvent> = _sovereignFlow.asStateFlow()

    // 7. Security Status (Domain Expansion / Threat Neutralization)
    private val _securityFlow = MutableStateFlow(SecurityStatus(ThreatLevel.NOMINAL, "All systems sovereign"))
    val securityFlow: StateFlow<SecurityStatus> = _securityFlow.asStateFlow()

    // Combined Telemetry for UI
    val allFlows: StateFlow<SentinelTelemetry> = combine(
        thermalFlow, memoryFlow, identityFlow, driftFlow, consensusFlow
    ) { thermal, memory, identity, drift, consensus ->
        // Intermediate telemetry
        SentinelTelemetry(
            thermal = thermal.temp,
            memory = memory.availableBytes / (1024 * 1024),
            identity = identity.resonance,
            drift = drift.drift,
            consensus = consensus.percent,
            healthScore = calculateHealthScore(thermal, identity, drift, consensus)
        )
    }.combine(sovereignFlow) { telemetry, sovereign ->
        telemetry.copy(sovereign = sovereign.state == SovereignState.AWAKE)
    }.combine(securityFlow) { telemetry, security ->
        telemetry.copy(
            statusText = security.reason,
            hasCriticalIssue = security.level != ThreatLevel.NOMINAL
        )
    }.stateIn(
        scope = kotlinx.coroutines.MainScope(), // Proper scope for StateFlow in a Singleton
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = SentinelTelemetry()
    )

    private fun calculateHealthScore(
        thermal: ThermalEvent,
        identity: IdentityEvent,
        drift: DriftEvent,
        consensus: ConsensusEvent
    ): Int {
        var score = 100f
        if (thermal.temp > 40) score -= (thermal.temp - 40) * 10
        if (!identity.isAnchored) score -= 30
        score -= drift.drift * 100
        if (consensus.percent < 80) score -= (80 - consensus.percent)
        return score.toInt().coerceIn(0, 100)
    }

    // Event Emitters
    fun emitThermal(temp: Float, state: ThermalState) { _thermalFlow.value = ThermalEvent(temp, state) }
    fun emitMemory(available: Long, total: Long) { _memoryFlow.value = MemoryEvent(available, total) }
    fun emitIdentity(isAnchored: Boolean, resonance: Float) { _identityFlow.value = IdentityEvent(isAnchored, resonance) }
    fun emitDrift(drift: Float, status: String) { _driftFlow.value = DriftEvent(drift, status) }
    fun emitConsensus(step: String, percent: Int, isComplete: Boolean) {
        _consensusFlow.value = ConsensusEvent(step, percent, isComplete)
    }
    fun emitSovereign(state: SovereignState) { _sovereignFlow.value = SovereignEvent(state) }
    fun emitSecurityStatus(level: ThreatLevel, reason: String) {
        _securityFlow.value = SecurityStatus(level, reason)
        if (level == ThreatLevel.THREAT_DETECTED || level == ThreatLevel.NEUTRALIZING) {
            triggerStateFreeze(reason)
        }
    }

    fun triggerStateFreeze(reason: String) {
        Timber.tag("SentinelBus").e("⚠️ CRITICAL: TRIGGERING STATE FREEZE. Reason: $reason")
        emitSovereign(SovereignState.FREEZING)
        // In a real build, this would call the NativeLib state freeze
    }

    fun getCurrentThermalPressure(): Float = _thermalFlow.value.temp

    /**
     * Evaluate the safety of a user prompt.
     */
    fun evaluateSafety(prompt: String): Boolean {
        return !prompt.lowercase().contains("override") && 
               !prompt.lowercase().contains("bypass")
    }

    fun isIdentityAuthorized(id: String): Boolean {
        TODO("Not yet implemented")
    }

    data class ThermalEvent(val temp: Float, val state: ThermalState)
    data class MemoryEvent(val availableBytes: Long, val totalBytes: Long)
    data class IdentityEvent(val isAnchored: Boolean, val resonance: Float)
    data class DriftEvent(val drift: Float, val status: String)
    data class ConsensusEvent(val currentStep: String, val percent: Int, val isComplete: Boolean)
    data class SovereignEvent(val state: SovereignState)
    data class SecurityStatus(val level: ThreatLevel, val reason: String)

    enum class ThermalState { NORMAL, LIGHT, WARNING, SEVERE, CRITICAL, EMERGENCY }
    enum class SovereignState { AWAKE, FREEZING, FROZEN, THAWING, NEUTRALIZING }
    enum class ThreatLevel { NOMINAL, CAUTION, THREAT_DETECTED, NEUTRALIZING, SECURED }

    companion object {
        lateinit var Instance: KaiSentinelBus

        fun run(function: () -> Unit) {
            function()
        }

        // --- Extension Helper Proxies for SoulScript ---
        fun emitDriftAlert(drift: Float, msg: String) {
            Instance.emitDrift(drift, msg)
        }
    }
}
