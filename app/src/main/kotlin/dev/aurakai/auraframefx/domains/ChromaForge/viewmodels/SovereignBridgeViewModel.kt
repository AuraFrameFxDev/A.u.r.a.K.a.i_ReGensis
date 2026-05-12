package dev.aurakai.auraframefx.core.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.core.repository.SovereignBridgeRepository
import dev.aurakai.auraframefx.core.MCPConnector
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SovereignBridgeViewModel @Inject constructor(
    private val repository: SovereignBridgeRepository
) : ViewModel() {
    val connectors: StateFlow<List<MCPConnector>> = repository.connectors

    fun toggleConnector(connector: MCPConnector) {
        repository.toggleConnector(connector.id)
    }
}
