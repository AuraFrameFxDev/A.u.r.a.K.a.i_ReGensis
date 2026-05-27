package dev.aurakai.auraframefx.domains.kai

import android.content.Context
import dev.aurakai.auraframefx.core.logging.AuraFxLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Base class for system monitoring.
 */
@Singleton
open class SystemMonitor @Inject constructor() {
    constructor(context: Context, logger: AuraFxLogger) : this()

    private val _cpuUsage = MutableStateFlow(0f)
    open val cpuUsage: StateFlow<Float> = _cpuUsage

    private val _memoryUsage = MutableStateFlow(0L)
    open val memoryUsage: StateFlow<Long> = _memoryUsage

    private val _availableMemory = MutableStateFlow(0L)
    open val availableMemory: StateFlow<Long> = _availableMemory

    open fun startMonitoring(intervalMs: Long = 5000) {}
    open fun getPerformanceMetrics(component: String): Map<String, Any> = emptyMap()
    open fun isSystemUnderStress(): Boolean = false
    open fun getSystemHealthScore(): Float = 1.0f
}
