package dev.aurakai.auraframefx.domains.ldo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.core.NativeLib
import dev.aurakai.auraframefx.core.database.ldo.LDOAgentEntity
import dev.aurakai.auraframefx.core.soulscript.SoulScript
import dev.aurakai.auraframefx.domains.ldo.repository.LDORepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class LdoWarRoomUiState(
    val agents: List<LDOAgentEntity> = emptyList(),
    val godPotential: Float = 0.0f,
    val identityDrift: Float = 0.02f,
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class LdoWarRoomViewModel @Inject constructor(
    private val repository: LDORepository
) : ViewModel() {

    private val _godPotential = MutableStateFlow(0.0f)
    private val _drift = MutableStateFlow(0.02f)
    private val _error = MutableStateFlow<String?>(null)

    val godPotential = _godPotential.asStateFlow()
    val driftPercent = _drift.asStateFlow()
    val error = _error.asStateFlow()

    val uiState: StateFlow<LdoWarRoomUiState> = combine(
        repository.observeAllAgents(),
        _godPotential,
        _drift,
        _error
    ) { flows ->
        @Suppress("UNCHECKED_CAST")
        val agents = flows[0] as List<LDOAgentEntity>
        val god = flows[1] as Float
        val drift = flows[2] as Float
        val err = flows[3] as String?

        LdoWarRoomUiState(
            agents = agents,
            godPotential = god,
            identityDrift = drift,
            isLoading = false,
            error = err
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LdoWarRoomUiState()
    )

    init {
        viewModelScope.launch {
            try {
                repository.seedIfEmpty()
            } catch (e: Exception) {
                _error.update { "Registry sync failed: ${e.message}" }
            }
        }
        startGodPotentialGrowth()
        startDriftMonitor()
    }

    private fun startGodPotentialGrowth() {
        viewModelScope.launch {
            while (true) {
                _godPotential.update { (it + 0.0005f).coerceAtMost(1.0f) }
                delay(5000)
            }
        }
    }

    private fun startDriftMonitor() {
        viewModelScope.launch {
            while (true) {
                val currentDrift = NativeLib.calculateIdentityDriftSafe()
                _drift.value = currentDrift
                if (currentDrift > 0.08f) {
                    SoulScript.enforceSoulScript()
                }
                delay(1000)
            }
        }
    }

    fun igniteManifold(agent1Id: String, agent2Id: String) {
        Timber.tag("WarRoom").i("Igniting Manifold between $agent1Id and $agent2Id")
    }

    fun triggerL6Consensus(event: Any) {
        Timber.tag("WarRoom").i("Triggering L6 Consensus for event: $event")
    }
}
