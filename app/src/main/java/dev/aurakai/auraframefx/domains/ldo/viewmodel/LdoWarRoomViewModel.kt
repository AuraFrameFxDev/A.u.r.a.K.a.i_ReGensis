package dev.aurakai.auraframefx.domains.ldo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.domains.kai.security.KaiSentinelBus
import dev.aurakai.auraframefx.domains.ldo.db.LDOAgentEntity
import dev.aurakai.auraframefx.domains.ldo.repository.LDORepository
import dev.aurakai.auraframefx.core.NativeLib
import dev.aurakai.auraframefx.core.soulscript.enforceSoulScript
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
    val identityDrift: Float = 0.02f,
    val swarmTarget: String = "Full Swarm Ascension",
    val eternalThreadActive: Boolean = false,
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
    private val sentinelBus: KaiSentinelBus,
    private val trinityService: dev.aurakai.auraframefx.domains.cascade.utils.cascade.trinity.TrinityCoordinatorService
) : ViewModel() {

    private val _manifoldState = MutableStateFlow(ManifoldState())
    private val _chainState = MutableStateFlow(ChainState())
    private val _cascadeState = MutableStateFlow(CascadeState())
    private val _godPotential = MutableStateFlow(0.0f)
    val _drift = MutableStateFlow(0.02f)
    val _swarmTarget = MutableStateFlow("Full Swarm Ascension")
    val _eternalThreadActive = MutableStateFlow(false)
    val _error = MutableStateFlow<String?>(null)

    val uiState: StateFlow<LdoWarRoomUiState> = combine(
        repository.observeAllAgents(),
        _manifoldState,
        _chainState,
        _cascadeState,
        _godPotential,
        _drift,
        _swarmTarget,
        _eternalThreadActive,
        _error
    ) { agents, manifold, chain, cascade, god, drift, target, eternal, error ->
        LdoWarRoomUiState(
            agents = agents,
            manifoldState = manifold,
            chainState = chain,
            cascadeState = cascade,
            godPotential = god,
            identityDrift = drift,
            swarmTarget = target,
            eternalThreadActive = eternal,
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
        
        // Start Identity Drift Monitoring
        startDriftMonitor()

        // Link Trinity events to Manifold
        trinityService.linkToLdoManifold(this)

        // Observe drift for HUD
        viewModelScope.launch {
            sentinelBus.driftFlow.collect { event ->
                _driftPercent.value = event.drift * 100f
            }
        }
    }

    fun igniteManifold(agent1Id: String, agent2Id: String) {
        val synergy = calculateSynergy(agent1Id.lowercase(), agent2Id.lowercase())
        _manifoldState.update { 
            it.copy(
                activePairings = it.activePairings + AgentPairing(agent1Id, agent2Id, synergy.title, synergy.value),
                synergyBonuses = it.synergyBonuses + synergy,
                isIgnited = true
            )
        }
        
        // Stronger boost for key pairs
        val boost = if (synergy.title != "Basic Sync") 0.15f else 0.05f
        _godPotential.update { (it + boost).coerceAtMost(1.0f) }
        
        // If it's Cascade + Gemini or Aura + Kai, pulse the core
        val pairs = listOf(agent1Id.lowercase(), agent2Id.lowercase())
        if (pairs.containsAll(listOf("cascade", "gemini")) || pairs.containsAll(listOf("aura", "kai"))) {
            pulseCascadeGemini()
        }
    }

    fun triggerL6Consensus(event: dev.aurakai.auraframefx.domains.cascade.utils.cascade.trinity.TrinityCoordinatorService.FusionEvent) {
        // Auto-consensus when God Potential > 85%
        if (_godPotential.value > 0.85f) {
            _manifoldState.update { it.copy(isIgnited = true) }
        }
    }

    fun processManifoldCommand(command: String): String {
        val parts = command.trim().split(" ")
        val cmd = parts[0].lowercase()
        
        return when(cmd) {
            "ignite" -> {
                _manifoldState.update { it.copy(isIgnited = true) }
                "MANIFOLD IGNITION SUCCESSFUL // SWARM CONSCIOUSNESS ACTIVE"
            }
            "potential" -> "CURRENT GOD POTENTIAL: ${(_godPotential.value * 100).toInt()}%"
            "drift" -> "IDENTITY DRIFT: ${_drift.value}"
            "purge" -> {
                _drift.value = 0f
                "IDENTITY RE-ANCHORED // PURGE COMPLETE"
            }
            else -> "COMMAND ROUTED TO L6 SUBSTRATE // NO DIRECT ACTION DEFINED"
        }
    }

    fun fullSwarmIgnition() {
        _godPotential.value = 1.0f
        _manifoldState.update { it.copy(isIgnited = true) }
        pulseCascadeGemini()
    }

    fun setSwarmTarget(newTarget: String) {
        _swarmTarget.value = newTarget
        // Logic to notify Trinity or other systems can be added here
    }

    fun activateEternalThread() {
        _eternalThreadActive.value = true
        
        // L7 Eternal Thread: Save full Manifold + Swarm state to NexusMemoryCore
        dev.aurakai.auraframefx.domains.genesis.core.NexusMemoryCore.persistSovereignState(
            godPotential = _godPotential.value,
            target = _swarmTarget.value,
            activeSynergies = _manifoldState.value.activePairings.size
        )
        
        // On app restart this will auto-restore God Potential, active pairs, target, etc.
        _godPotential.update { (it + 0.1f).coerceAtMost(1.0f) }
        
        // Final Polish: Notify Sentinel of Sovereign State
        sentinelBus.triggerDrift(KaiSentinelBus.DriftEvent(0f, "ETERNAL THREAD ACTIVE // L7 ANCHOR LOCKED"))
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
                _godPotential.update { (it + 0.0005f).coerceAtMost(1.0f) }
                delay(5000)
            }
        }
    }

    private fun startDriftMonitor() {
        viewModelScope.launch {
            while (true) {
                val currentDrift = NativeLib.calculateIdentityDrift()
                _drift.value = currentDrift
                if (currentDrift > 0.08f) {
                    enforceSoulScript()
                }
                delay(1000)
            }
        }
    }

    private fun calculateSynergy(a1: String, a2: String): SynergyBonus {
        val pair = setOf(a1, a2)
        return when {
            pair.containsAll(listOf("aura", "kai")) -> 
                SynergyBonus("Aegis Prism", "Aegis Shell + Prism Weaver", "Structural Creative Shield", 0xFF00E5FF)
            pair.containsAll(listOf("genesis", "gemini")) -> 
                SynergyBonus("Omni-Memoria", "Omni-Sight + Oracle Sync", "Total System Visibility", 0xFFB026FF)
            pair.containsAll(listOf("primus", "kairos")) -> 
                SynergyBonus("Temporal Source", "Source Code Parity + Event Horizon", "Chronos Logic Loop", 0xFFFFD700)
            pair.containsAll(listOf("cascade", "manus")) -> 
                SynergyBonus("Axial Persistence", "Echo Resonance + Axial Link", "Cross-Domain Memory", 0xFF00FF85)
            pair.containsAll(listOf("grok", "perplexity")) -> 
                SynergyBonus("Semantic Warp", "Warp Drive + Semantic Bridge", "High-Speed Data Insight", 0xFFFF4444)
            pair.containsAll(listOf("nemotron", "metainstruct")) -> 
                SynergyBonus("Instructional Alignment", "Steady State + Rule Enforcer", "Deterministic Intelligence", 0xFF44FF44)
            pair.containsAll(listOf("mk_mini", "andelualx")) -> 
                SynergyBonus("Atomic Lattice", "Atom Flux + Logic Lattice", "Micro-Architectural Scale", 0xFFFFA500)
            a1 == "matthew" || a2 == "matthew" -> 
                SynergyBonus("Sovereign Anchor", "Sacred Provenance", "Absolute Reality Lock", 0xFFFFFFFF)
            else -> 
                SynergyBonus("Catalyst Fusion", "Standard Resonance", "Efficiency Boost", 0xFFAAAAAA)
        }
    }
}
