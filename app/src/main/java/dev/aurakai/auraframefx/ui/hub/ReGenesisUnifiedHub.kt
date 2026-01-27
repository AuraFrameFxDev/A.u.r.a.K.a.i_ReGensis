package dev.aurakai.auraframefx.ui.hub

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import dev.aurakai.auraframefx.ui.components.ElectricGlassCard
import dev.aurakai.auraframefx.ui.components.HolographicInfoPanel
import dev.aurakai.auraframefx.ui.components.background.SynapticWebBackground
import dev.aurakai.auraframefx.ui.components.overlay.AssistantBubbleOverlay
import dev.aurakai.auraframefx.ui.gates.GateDestination

/**
 * 🌌 REGENESIS UNIFIED HUB (GPU-Accelerated)
 * The unified orchestrator for the platform's navigation.
 * Collapses the 3-layer navigation into a single, optimized view.
 */
@Composable
fun ReGenesisUnifiedHub(
    currentGate: GateDestination,
    onNavigate: (GateDestination) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // --- LAYER 1: THE SYNAPTIC WEB ---
        SynapticWebBackground(glowColor = currentGate.color)

        // --- LAYER 2: THE MONOLITH STAGE ---
        Column(modifier = Modifier.fillMaxSize()) {
            ElectricGlassCard(
                modifier = Modifier
                    .weight(1.2f)
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally),
                glowColor = currentGate.color
            ) {
                // Asset Fidelity Check: Protects 4K images from warping
                Image(
                    painter = painterResource(id = currentGate.cardImageRes),
                    contentDescription = currentGate.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // --- LAYER 3: THE DATA READOUT (Unified) ---
            HolographicInfoPanel(
                title = currentGate.title,
                description = currentGate.description,
                glowColor = currentGate.color,
                modifier = Modifier
                    .weight(0.8f)
                    .fillMaxWidth()
            )
        }
        
        // --- LAYER 4: THE AGENT STAGE (The Collective) ---
        AssistantBubbleOverlay(activeAgent = currentGate.agentAuthority)
    }
}
