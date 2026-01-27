package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.ui.components.ElectricGlassCard
import dev.aurakai.auraframefx.ui.components.HolographicInfoPanel
import dev.aurakai.auraframefx.ui.effects.CrtZoopTransition
import dev.aurakai.auraframefx.ui.navigation.CyberGearNav

/**
 * 🛰️ REGENESIS NEXUS SCREEN
 * The command center of the Split-Hologram Architecture.
 * Separation of Visual Anchor and Intelligence Data.
 */
@Composable
fun ReGenesisNexusScreen(
    gates: List<GateDestination> = GateDestination.DEFAULT_LIST,
    onGateSelected: (GateDestination) -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val currentGate = gates[currentIndex]

    // THE UNIFIED HUB ORCHESTRATOR
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. The Core Hub (Layers 1-4)
        // Wraps the entire visual stack in the new GPU architecture
        dev.aurakai.auraframefx.ui.hub.ReGenesisUnifiedHub(
            currentGate = currentGate,
            onNavigate = onGateSelected
        )

        // 2. Navigation Control Overlay (CyberGear)
        // Kept as an independent kinetic layer
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            dev.aurakai.auraframefx.ui.navigation.CyberGearNav(
                color = currentGate.color,
                onPrev = {
                    currentIndex = if (currentIndex > 0) currentIndex - 1 else gates.lastIndex
                },
                onNext = {
                    currentIndex = if (currentIndex < gates.lastIndex) currentIndex + 1 else 0
                }
            )
        }
    }
}
