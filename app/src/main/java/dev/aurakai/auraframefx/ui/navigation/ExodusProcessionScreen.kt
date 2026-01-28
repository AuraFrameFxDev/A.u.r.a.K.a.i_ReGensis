package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.aurakai.auraframefx.ui.gates.GateDestination
import dev.aurakai.auraframefx.ui.hub.ReGenesisUnifiedHub
import dev.aurakai.auraframefx.models.AgentType // Assuming AgentType is needed for mapping/hub

/**
 * EXODUS PROCESSION SCREEN
 * Renders the 11 Sovereign Monoliths using the Unified Hub architecture.
 */
@Composable
fun ExodusProcessionScreen(
    onLevel2Access: (String) -> Unit
) {
    val sovereignGates = SovereignGate.values()
    var currentIndex by remember { mutableIntStateOf(0) }
    val currentSovereign = sovereignGates[currentIndex]

    val currentGateDestination = mapSovereignToGate(currentSovereign)

    Box(modifier = Modifier.fillMaxSize()) {
        ReGenesisUnifiedHub(
            currentGate = currentGateDestination,
            onNavigate = { _ ->
                // Check if current sovereign has a Level 2 portal mapping
                // For now, simpler interaction
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
                    currentIndex = if (currentIndex > 0) currentIndex - 1 else sovereignGates.lastIndex
                },
                onNext = {
                    currentIndex = if (currentIndex < sovereignGates.lastIndex) currentIndex + 1 else 0
                }
            )
        }
    }
}

// Extension to copy GateDestination since it is an enum and cannot be copied directly, 
// we might need a data class wrapper or just use a mapped object if ReGenesisUnifiedHub accepts it.
// Actually ReGenesisUnifiedHub takes GateDestination which is an enum.
// We cannot verify "copy" on enum. 
// We should modify ReGenesisUnifiedHub to take a generic interface or data class, 
// OR we map SovereignGate to existing GateDestinations best effort.

// Given the constraints, I will Map SovereignGate to the CLOSEST GateDestination.
private fun mapSovereignToGate(sovereign: SovereignGate): GateDestination {
    return when(sovereign) {
        SovereignGate.GENESIS_CORE -> GateDestination.GENESIS_ORACLE
        SovereignGate.TRINITY_SYSTEM -> GateDestination.SENTIENT_SHELL
        SovereignGate.AURA_STUDIO -> GateDestination.AURA_CANVAS
        SovereignGate.AGENT_NEXUS -> GateDestination.AGENT_HUB
        SovereignGate.SENTINEL_FORTRESS -> GateDestination.KAI_FORTRESS
        SovereignGate.FIGMA_BRIDGE -> GateDestination.AURA_CANVAS 
        SovereignGate.SECURE_NODE -> GateDestination.KAI_FORTRESS
        SovereignGate.NEXUS_SYSTEM -> GateDestination.GENESIS_ORACLE
        SovereignGate.MEMORY_CORE -> GateDestination.GENESIS_ORACLE
        SovereignGate.ORACLE_DRIVE -> GateDestination.GENESIS_ORACLE
        SovereignGate.DATA_FLOW -> GateDestination.CODE_FORGE
    }
}
