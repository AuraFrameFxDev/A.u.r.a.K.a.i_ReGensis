package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.models.AgentType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import dev.aurakai.auraframefx.customization.CustomizationViewModel
import dev.aurakai.auraframefx.models.ReGenesisMode
import dev.aurakai.auraframefx.ui.hub.ReGenesisUnifiedHub

/**
 * EXODUS PROCESSION SCREEN
 * Renders the 11 Sovereign Monoliths using the Unified Hub architecture.
 */
@Composable
fun ExodusProcessionScreen(
    onLevel2Access: (String) -> Unit,
    customizationViewModel: CustomizationViewModel = viewModel()
) {
    val customizationState by customizationViewModel.state.collectAsState()
    val sovereignGates = SovereignGate.getGatesForMode(customizationState.reGenesisMode)
    
    var currentIndex by remember(sovereignGates) { mutableIntStateOf(0) }
    // Bounds check in case gates list changed
    val safeIndex = if (currentIndex >= sovereignGates.size) 0 else currentIndex
    val currentSovereign = if (sovereignGates.isNotEmpty()) sovereignGates[safeIndex] else SovereignGate.GENESIS_CORE

    Box(modifier = Modifier.fillMaxSize()) {
        ReGenesisUnifiedHub(
            currentGate = currentSovereign,
            onNavigate = { gate ->
                onLevel2Access(gate.route)
            }
        )
        
        // Navigation Controls (CyberGear)
        androidx.compose.foundation.layout.Box(
            modifier = androidx.compose.ui.Alignment.BottomCenter.let { align ->
                androidx.compose.ui.Modifier
                    .align(align)
                    .padding(bottom = 32.dp)
            }
        ) {
             CyberGearNav(
                color = currentSovereign.color,
                onPrev = {
                    currentIndex = if (safeIndex > 0) safeIndex - 1 else sovereignGates.lastIndex
                },
                onNext = {
                    currentIndex = if (safeIndex < sovereignGates.lastIndex) safeIndex + 1 else 0
                }
            )
        }
    }
}

