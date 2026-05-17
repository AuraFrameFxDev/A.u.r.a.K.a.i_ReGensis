package dev.aurakai.auraframefx.domains.kai

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SystemMonitor @Inject constructor() {
    fun startMonitoring() {}
    fun getPerformanceMetrics(component: String): Map<String, Any> = emptyMap()
}
