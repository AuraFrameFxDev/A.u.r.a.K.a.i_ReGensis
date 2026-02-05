package dev.aurakai.auraframefx.domains.kai.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.domains.kai.ModuleStatus
import dev.aurakai.auraframefx.domains.kai.ModuleType
import dev.aurakai.auraframefx.domains.kai.SovereignModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SovereignModuleViewModel @Inject constructor() : ViewModel() {
    private val _modules = MutableStateFlow(initialModules())
    val modules: StateFlow<List<SovereignModule>> = _modules.asStateFlow()

    private fun initialModules() = listOf(
        SovereignModule(
            id = "aura-themer",
            name = "Aura Engine Core",
            description = "The root of all visual sovereignty. Handles the 8K glass syntax.",
            version = "7.0-LDO",
            author = "Aura",
            type = ModuleType.LSPOSED,
            status = ModuleStatus.ACTIVE,
            isCrucial = true
        ),
        SovereignModule(
            id = "sentinel-shield",
            name = "Sentinel Shield",
            description = "Low-level telemetry blocking and ad-server redirection.",
            version = "1.2.0",
            author = "Kai",
            type = ModuleType.MAGISK,
            status = ModuleStatus.ACTIVE
        ),
        SovereignModule(
            id = "gravity-box-rg",
            name = "GravityBox [ReGenesis]",
            description = "Advanced system-wide tweaks optimized for the 70-LDO kernel.",
            version = "10.1.4",
            author = "C3C076 (Modded by Genesis)",
            type = ModuleType.LSPOSED,
            status = ModuleStatus.INACTIVE
        ),
        SovereignModule(
            id = "ksu-kernel-tweak",
            name = "KSU Optimizer",
            description = "Kernel-level performance and battery profiles.",
            version = "3.1.0",
            author = "KernelSU Team",
            type = ModuleType.KERNEL_SU,
            status = ModuleStatus.ACTIVE
        ),
        SovereignModule(
            id = "shizuku-service",
            name = "Shizuku Sovereign Bridge",
            description = "Enables system-level operations via ADB/Root without constant prompt overhead.",
            version = "13.1.5",
            author = "Rikka",
            type = ModuleType.SHIZUKU,
            status = ModuleStatus.ACTIVE
        )
    )

    fun toggleModule(id: String) {
        _modules.value = _modules.value.map {
            if (it.id == id) {
                val newStatus = if (it.status == ModuleStatus.ACTIVE) ModuleStatus.INACTIVE else ModuleStatus.ACTIVE
                it.copy(status = newStatus)
            } else it
        }
    }
}
