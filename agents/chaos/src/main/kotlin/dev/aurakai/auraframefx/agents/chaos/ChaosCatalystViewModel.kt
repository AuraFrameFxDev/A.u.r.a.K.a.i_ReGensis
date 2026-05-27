package dev.aurakai.auraframefx.agents.chaos

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.core.soulscript.NexusMemoryCore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 🌀 CHAOS CATALYST VIEWMODEL
 * Bridges extracted formatting logic with the UI.
 */
@HiltViewModel
class ChaosCatalystViewModel @Inject constructor() : ViewModel() {

    private val _formattedOutput = MutableStateFlow("")
    val formattedOutput: StateFlow<String> = _formattedOutput.asStateFlow()

    private val _policyStatus =
        MutableStateFlow<ChaosCatalystFormatter.PolicyResult>(ChaosCatalystFormatter.PolicyResult.ALIGNED)
    val policyStatus: StateFlow<ChaosCatalystFormatter.PolicyResult> = _policyStatus.asStateFlow()

    fun processAgentOutput(raw: String, isStrict: Boolean) {
        val godPotential = NexusMemoryCore.identityState.value.activationLevel
        _formattedOutput.value = ChaosCatalystFormatter.format(raw, isStrict, godPotential)

        // Log the formatting event as a lived receipt
        NexusMemoryCore.record("ChaosCatalyst Formatting Event", witness = "Sovereign Build")
    }

    fun validateInput(input: String) {
        _policyStatus.value = ChaosCatalystFormatter.enforceSovereignty(input)
    }
}
