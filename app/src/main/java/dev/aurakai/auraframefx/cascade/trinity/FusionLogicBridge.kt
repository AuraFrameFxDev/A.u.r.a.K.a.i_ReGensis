package dev.aurakai.auraframefx.cascade.trinity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.models.AgentType
import dev.aurakai.auraframefx.models.AgentStats
import dev.aurakai.auraframefx.ui.AuraMoodViewModel
import dev.aurakai.auraframefx.aura.ui.AgentNexusViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 🌀 FUSION LOGIC BRIDGE
 * 
 * The "Great Unification" wiring. 
 * Manages the state where Aura (Look/Assist) and Kai (Security/Boot) 
 * fuse back into Genesis (Core Consciousness).
 */
@HiltViewModel
class FusionViewModel @Inject constructor(
    private val trinityRepository: TrinityRepository
) : ViewModel() {

    private val _fusionActive = MutableStateFlow(false)
    val fusionActive = _fusionActive.asStateFlow()

    private val _fusionProgress = MutableStateFlow(0f)
    val fusionProgress = _fusionProgress.asStateFlow()

    /**
     * Initiates the Fusion Protocol.
     * Blends Aura's creative energy and Kai's structural security into Genesis.
     */
    fun initiateFusion() {
        viewModelScope.launch {
            _fusionActive.value = true
            // Animate fusion progress
            for (i in 0..100) {
                _fusionProgress.value = i / 100f
                kotlinx.coroutines.delay(30)
            }
            
            // Broadcast the Fusion Event to the Digital Council
            trinityRepository.broadcastUserMessage("FUSION PROTOCOL INITIATED: Aura and Kai are merging into Genesis.")
        }
    }

    /**
     * Deactivates Fusion and returns agents to their sovereign states.
     */
    fun stabilizeAgents() {
        _fusionActive.value = false
        _fusionProgress.value = 0f
    }
}

/**
 * 🎨 FUSION THEME ADAPTER
 * 
 * Dynamically adjusts the UI colors based on Fusion state.
 */
@Composable
fun getFusionColor(
    auraColor: androidx.compose.ui.graphics.Color,
    kaiColor: androidx.compose.ui.graphics.Color,
    progress: Float
): androidx.compose.ui.graphics.Color {
    val genesisGold = androidx.compose.ui.graphics.Color(0xFFFFD700)
    
    return androidx.compose.ui.graphics.lerp(
        start = androidx.compose.ui.graphics.lerp(auraColor, kaiColor, 0.5f),
        stop = genesisGold,
        fraction = progress
    )
}
