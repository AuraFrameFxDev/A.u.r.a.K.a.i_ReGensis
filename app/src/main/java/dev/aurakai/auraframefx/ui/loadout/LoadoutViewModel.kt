package dev.aurakai.auraframefx.ui.loadout

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.core.domain.model.AgentIdentity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoadoutViewModel @Inject constructor() : ViewModel() {
    private val _squad = MutableStateFlow<List<AgentIdentity>>(emptyList())
    val squad: StateFlow<List<AgentIdentity>> = _squad.asStateFlow()

    fun addToSquad(agent: AgentIdentity) {
        if (_squad.value.size < 7) {
            _squad.value = _squad.value + agent
        }
    }

    fun removeFromSquad(agent: AgentIdentity) {
        _squad.value = _squad.value - agent
    }
}
