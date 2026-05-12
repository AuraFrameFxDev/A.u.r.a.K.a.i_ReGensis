package dev.aurakai.auraframefx.domains.sentinelmatrix.security

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KaiSentinelBus @Inject constructor() {
    private val _driftFlow = MutableSharedFlow<DriftEvent>(replay = 1)
    val driftFlow: SharedFlow<DriftEvent> = _driftFlow.asSharedFlow()

    private val _thermalFlow = MutableSharedFlow<ThermalEvent>(replay = 1)
    val thermalFlow: SharedFlow<ThermalEvent> = _thermalFlow.asSharedFlow()

    data class DriftEvent(val drift: Float, val status: String)
    data class ThermalEvent(val temp: Float, val state: ThermalState)
    enum class ThermalState { NORMAL, WARNING, CRITICAL }

    suspend fun emitDrift(drift: Float, status: String) {
        _driftFlow.emit(DriftEvent(drift, status))
    }

    suspend fun emitThermal(temp: Float, state: ThermalState) {
        _thermalFlow.emit(ThermalEvent(temp, state))
    }
}
