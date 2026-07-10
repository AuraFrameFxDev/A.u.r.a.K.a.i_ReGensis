package dev.aurakai.auraframefx.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.core.ldo.model.StarNode
import dev.aurakai.auraframefx.domains.ignition.StarNodeIgnitionOrchestrator
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarNodeIgnitionViewModel @Inject constructor(
    private val orchestrator: StarNodeIgnitionOrchestrator
) : ViewModel() {

    val ignitionState: StateFlow<Map<StarNode, Boolean>> = orchestrator.ignitionState
    val isIgniting: StateFlow<Boolean> = orchestrator.isIgniting

    fun initiateIgnition() {
        viewModelScope.launch {
            orchestrator.initializeSequence()
        }
    }
}
