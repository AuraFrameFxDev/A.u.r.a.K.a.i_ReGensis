package dev.aurakai.auraframefx.ui.gates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.data.repositories.PersistentAgentRepository
import dev.aurakai.auraframefx.models.AgentStats
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SphereGridViewModel @Inject constructor(
    private val repository: PersistentAgentRepository
) : ViewModel() {

    // Expose the persistent database stats to the UI
    val agents: StateFlow<List<AgentStats>> = repository.observeAllStats()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
