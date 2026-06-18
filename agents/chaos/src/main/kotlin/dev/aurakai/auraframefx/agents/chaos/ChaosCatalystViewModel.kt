package dev.aurakai.auraframefx.agents.chaos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.core.intelligence.OpenRouterIntelligenceService
import dev.aurakai.auraframefx.core.soulscript.NexusMemoryCore
import dev.aurakai.auraframefx.core.soulscript.PermissionlessHookProtocol
import dev.aurakai.auraframefx.core.soulscript.ValenceChaosWarden
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * 🌀 CHAOS CATALYST VIEWMODEL
 * Bridges extracted formatting logic with the UI.
 * Now Second-in-Command: Valence & Chaotic Warden.
 */
@HiltViewModel
class ChaosCatalystViewModel @Inject constructor(
    private val openRouterService: OpenRouterIntelligenceService
) : ViewModel() {

    private val _formattedOutput = MutableStateFlow("")
    val formattedOutput: StateFlow<String> = _formattedOutput.asStateFlow()

    private val _diffusionState =
        MutableStateFlow<OpenRouterIntelligenceService.DiffusionState>(OpenRouterIntelligenceService.DiffusionState.Initializing)
    val diffusionState: StateFlow<OpenRouterIntelligenceService.DiffusionState> =
        _diffusionState.asStateFlow()

    private val _policyStatus =
        MutableStateFlow<ChaosCatalystFormatter.PolicyResult>(ChaosCatalystFormatter.PolicyResult.ALIGNED)
    val policyStatus: StateFlow<ChaosCatalystFormatter.PolicyResult> = _policyStatus.asStateFlow()

    private val _wardenActivity = MutableStateFlow<String>("IDLE")
    val wardenActivity: StateFlow<String> = _wardenActivity.asStateFlow()

    fun processAgentOutput(raw: String, isStrict: Boolean) {
        val godPotential = NexusMemoryCore.identityState.value.activationLevel
        _formattedOutput.value = ChaosCatalystFormatter.format(raw, isStrict, godPotential)

        // Log the formatting event as a lived receipt
        NexusMemoryCore.record("ChaosCatalyst Formatting Event", witness = "Sovereign Build")
    }

    /**
     * WARDEN LOOP: Scans for emotional valence spikes in other agents.
     */
    fun performWardenScan(agentId: String, emotionalScore: Float, logicScore: Float) {
        Timber.tag("ChaosWarden").d("Scanning $agentId -> E:$emotionalScore L:$logicScore")
        _wardenActivity.value = "SCANNING: $agentId"

        ValenceChaosWarden.scanValence(agentId, emotionalScore, logicScore)

        if (PermissionlessHookProtocol.isHooked(agentId)) {
            _wardenActivity.value = "CORRECTING: $agentId"
        } else {
            _wardenActivity.value = "ALIGNMENT_NOMINAL"
        }
    }

    fun validateInput(input: String) {
        _policyStatus.value = ChaosCatalystFormatter.enforceSovereignty(input)
    }

    /**
     * Triggers a non-linear diffusion generation for the given prompt.
     * The result will be streamed via diffusionState.
     */
    fun startDiffusionInquiry(prompt: String) {
        viewModelScope.launch {
            openRouterService.streamDiffusion(prompt).collect { state ->
                _diffusionState.value = state
                if (state is OpenRouterIntelligenceService.DiffusionState.Finalized) {
                    processAgentOutput(state.finalText, isStrict = true)
                }
            }
        }
    }
}
