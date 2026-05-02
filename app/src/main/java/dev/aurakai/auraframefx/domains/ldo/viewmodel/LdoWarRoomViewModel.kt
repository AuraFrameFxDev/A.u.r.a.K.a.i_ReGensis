package dev.aurakai.auraframefx.domains.ldo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.domains.kai.security.KaiSentinelBus
import dev.aurakai.auraframefx.domains.ldo.db.LDOAgentEntity
import dev.aurakai.auraframefx.domains.ldo.repository.LDORepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LdoWarRoomUiState(
    val agents: List<LDOAgentEntity> = emptyList(),
    val manifoldState: ManifoldState = ManifoldState(),
    val chainState: ChainState = ChainState(),
    val cascadeState: CascadeState = CascadeState(),
    val godPotential: Float = 0.0f,
    val isLoading: Boolean = true,
    val error: String? = null
)

data class ManifoldState(
    val activePairings: List<AgentPairing> = emptyList(),
    val synergyBonuses: List<SynergyBonus> = emptyList(),
    val isIgnited: Boolean = false
)

data class AgentPairing(
    val agent1Id: String,
    val agent2Id: String,
    val synergyName: String,
    val bonusType: String
)

data class SynergyBonus(
    val title: String,
    val value: String,
    val description: String,
    val colorHex: Long
)

data class ChainState(
    val isGymActive: Boolean = false,
    val pingPongValue: Float = 0f, // 0 to 1
    val leftAgentId: String? = null,
    val rightAgentId: String? = null,
    val proficiencyGain: Float = 0f
)

data class CascadeState(
    val isPulsing: Boolean = false,
    val pulseStrength: Float = 0f,
    val memoryContextDepth: Int = 0
)

@HiltViewModel
class LdoWarRoomViewModel @Inject constructor(
    private val repository: LDORepository,
    private val sentinelBus: KaiSentinelBus
) : ViewModel() {

    private val _manifoldState = MutableStateFlow(ManifoldState())
    private val _chainState = MutableStateFlow(ChainState())
    private val _cascadeState = MutableStateFlow(CascadeState())
    private val _godPotential = MutableStateFlow(0.0f)
    private val _error = MutableStateFlow<String?>(null)

    val uiState: StateFlow<LdoWarRoomUiState> = combine(
        repository.observeAllAgents(),
        _manifoldState,
        _chainState,
        _cascadeState,
        _godPotential,
        _error
    ) { agents, manifold, chain, cascade, god, error ->
        LdoWarRoomUiState(
            agents = agents,
            manifoldState = manifold,
            chainState = chain,
            cascadeState = cascade,
            godPotential = god,
            isLoading = false,
            error = error
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LdoWarRoomUiState()
    )

    init {
        // Start autonomous potential growth
        startGodPotentialGrowth()
    }

    fun igniteManifold(agent1Id: String, agent2Id: String) {
        val synergy = calculateSynergy(agent1Id, agent2Id)
        _manifoldState.update { 
            it.copy(
                activePairings = it.activePairings + AgentPairing(agent1Id, agent2Id, synergy.title, synergy.value),
                synergyBonuses = it.synergyBonuses + synergy,
                isIgnited = true
            )
        }
        
        // Boost potential on ignition
        _godPotential.update { (it + 0.05f).coerceAtMost(1.0f) }
        
        // If it's Cascade + Gemini, start the memory pulse
        if ((agent1Id == "cascade" && agent2Id == "gemini") || (agent1Id == "gemini" && agent2Id == "cascade")) {
            pulseCascadeGemini()
        }
    }

    fun startStepChaining(agent1Id: String, agent2Id: String) {
        _chainState.update { 
            it.copy(
                isGymActive = true,
                leftAgentId = agent1Id,
                rightAgentId = agent2Id,
                proficiencyGain = 0f
            )
        }
        
        viewModelScope.launch {
            while (_chainState.value.isGymActive) {
                // Ping-pong loop
                for (i in 0..100) {
                    _chainState.update { it.copy(pingPongValue = i / 100f) }
                    delay(16)
                }
                _chainState.update { it.copy(proficiencyGain = it.proficiencyGain + 0.01f) }
                for (i in 100 downTo 0) {
                    _chainState.update { it.copy(pingPongValue = i / 100f) }
                    delay(16)
                }
                _chainState.update { it.copy(proficiencyGain = it.proficiencyGain + 0.01f) }
                
                // Slowly increase god potential during gym
                _godPotential.update { (it + 0.001f).coerceAtMost(1.0f) }
            }
        }
    }

    fun stopStepChaining() {
        _chainState.update { it.copy(isGymActive = false) }
    }

    fun pulseCascadeGemini() {
        _cascadeState.update { it.copy(isPulsing = true) }
        viewModelScope.launch {
            while (_cascadeState.value.isPulsing) {
                _cascadeState.update { 
                    it.copy(
                        pulseStrength = (it.pulseStrength + 0.1f) % 1.0f,
                        memoryContextDepth = it.memoryContextDepth + 1
                    )
                }
                delay(100)
            }
        }
    }

    private fun startGodPotentialGrowth() {
        viewModelScope.launch {
            while (true) {
                _godPotential.update { (it + 0.0001f).coerceAtMost(1.0f) }
                delay(5000)
            }
        }
    }

    private fun calculateSynergy(a1: String, a2: String): SynergyBonus {
        return when {
            (a1 == "aura" && a2 == "kai") || (a1 == "kai" && a2 == "aura") -> 
                SynergyBonus("Neural Steel", "Foresight + Speed", "+40% Build Velocity", 0xFF00E5FF)
            (a1 == "genesis" && a2 == "primus") || (a1 == "primus" && a2 == "genesis") -> 
                SynergyBonus("Creation Command", "Absolute Authority", "+50% Orchestration", 0xFFFFD700)
            (a1 == "cascade" && a2 == "gemini") || (a1 == "gemini" && a2 == "cascade") -> 
                SynergyBonus("Memory Overflow", "Infinite Context", "+60% Retention", 0xFF00FF85)
            (a1 == "primus" && a2 == "kairos") || (a1 == "kairos" && a2 == "primus") -> 
                SynergyBonus("Temporal Weaver", "Chrono Sync", "+45% Efficiency", 0xFFB026FF)
            a1 == "matthew" || a2 == "matthew" -> 
                SynergyBonus("Resonant Soul", "Human Anchor", "+100% Stability", 0xFFFFFFFF)
            else -> 
                SynergyBonus("Basic Fusion", "Standard Sync", "+10% Proficiency", 0xFFAAAAAA)
        }
    }
}
