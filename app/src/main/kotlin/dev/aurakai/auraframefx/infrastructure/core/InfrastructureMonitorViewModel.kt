
package dev.aurakai.auraframefx.infrastructure.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.domains.backend.BackendApi
import dev.aurakai.auraframefx.system.ShizukuManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

/**
 * 🛰️ INFRASTRUCTURE MONITOR VIEWMODEL
 * Real-time health monitoring for critical LDO arteries.
 */
@HiltViewModel
class InfrastructureMonitorViewModel @Inject constructor(
    private val backendApi: BackendApi,
    private val shizukuManager: ShizukuManager   // injected properly
) : ViewModel() {

    private val _statuses = MutableStateFlow<Map<Capability, InfrastructureStatus>>(emptyMap())
    val statuses: StateFlow<Map<Capability, InfrastructureStatus>> = _statuses.asStateFlow()

    init {
        startMonitoring()
    }

    private fun startMonitoring() {
        viewModelScope.launch {
            while (true) {
                updateAllStatuses()
                delay(10.seconds)
            }
        }
    }

    private suspend fun updateAllStatuses() {
        val newStatuses = mutableMapOf<Capability, InfrastructureStatus>()

        // 1. Shizuku Status
        val shizukuActive = shizukuManager.isShizukuAvailable()
        newStatuses[Capability.SHIZUKU_API] = InfrastructureStatus(
            isAvailable = shizukuActive,
            message = if (shizukuActive) "Service Active ✓" else "Disconnected"
        )
        CapabilityGates.updateStatus(Capability.SHIZUKU_API, shizukuActive)

        // 2. Xposed / LSPosed Hooks
        val hooksActive = isXposedActive()
        newStatuses[Capability.XPOSED_HOOKS] = InfrastructureStatus(
            isAvailable = hooksActive,
            message = if (hooksActive) "Hooks Operational" else "Hooks Inactive"
        )
        CapabilityGates.updateStatus(Capability.XPOSED_HOOKS, hooksActive)

        // 3. Backend / Python Bridge
        val backendStatus = try {
            val response = backendApi.getStatus()
            InfrastructureStatus(
                isAvailable = true,
                message = "Backend Online • v${response.version}"
            )
        } catch (e: Exception) {
            Timber.e(e, "Backend unreachable")
            InfrastructureStatus(
                isAvailable = false,
                message = "Backend Offline"
            )
        }
        newStatuses[Capability.CORE_BACKEND] = backendStatus
        CapabilityGates.updateStatus(Capability.CORE_BACKEND, backendStatus.isAvailable)

        _statuses.value = newStatuses
    }

    private fun isXposedActive(): Boolean {
        return try {
            ClassLoader.getSystemClassLoader().loadClass("de.robv.android.xposed.XposedHelpers") != null
        } catch (e: ClassNotFoundException) {
            false
        }
    }
}

// ==================== Capability Models & Gates ====================

enum class Capability {
    SHIZUKU_API,
    XPOSED_HOOKS,
    CORE_BACKEND,
}

data class InfrastructureStatus(
    val isAvailable: Boolean,
    val message: String
)

object CapabilityGates {
    private val _gates = MutableStateFlow<Map<Capability, Boolean>>(emptyMap())

    val gates: StateFlow<Map<Capability, Boolean>> = _gates.asStateFlow()

    fun updateStatus(capability: Capability, isAvailable: Boolean) {
        val current = _gates.value.toMutableMap()
        current[capability] = isAvailable
        _gates.value = current
    }
}
