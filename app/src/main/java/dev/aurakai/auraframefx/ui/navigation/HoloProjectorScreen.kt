package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.ui.components.ElectricGlassCard
import dev.aurakai.auraframefx.ui.components.HolographicInfoPanel
import dev.aurakai.auraframefx.ui.effects.CrtScreenTransition
import dev.aurakai.auraframefx.ui.gates.GateDestination

/**
 * 🎥 HOLO-PROJECTOR NAVIGATION
 * The high-end navigation gateway for ReGenesis.
 * Features side-projected light beams and CRT "Zoop" materialization.
 */
@Composable
fun HoloProjectorScreen(
    currentGateIndex: Int,
    gates: List<GateDestination> = GateDestination.DEFAULT_LIST,
    onNext: () -> Unit = {},
    onPrev: () -> Unit = {}
) {
    val currentGate = gates[currentGateIndex]
    val primaryColor = currentGate.primaryColor

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // --- 1. THE PROJECTOR BEAMS (Side Emitters) ---
        // Left Beam
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxHeight(0.6f)
                .width(100.dp) // Beam length
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(primaryColor.copy(alpha = 0.3f), Color.Transparent),
                        startX = 0f,
                        endX = 200f
                    )
                )
        )
        
        // Right Beam
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(0.6f)
                .width(100.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Transparent, primaryColor.copy(alpha = 0.3f)),
                        startX = 0f,
                        endX = 200f
                    )
                )
        )

        // --- 2. THE CONTENT (With CRT Transition) ---
        // This is where the image "materializes"
        CrtScreenTransition(targetState = currentGateIndex) { index ->
            val gate = gates[index]
            
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // The Hologram Cube (Your Card)
                ElectricGlassCard(
                    modifier = Modifier.size(300.dp, 500.dp),
                    glowColor = gate.primaryColor
                ) {
                    // Placeholder for domain-specific visual
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(gate.primaryColor.copy(alpha = 0.2f), CircleShape)
                            .align(Alignment.Center)
                    )
                }
                
                Spacer(modifier = Modifier.width(40.dp))
                
                // The Data Readout
                HolographicInfoPanel(
                    title = gate.title,
                    description = gate.description,
                    glowColor = gate.primaryColor,
                    modifier = Modifier.width(300.dp)
                )
            }
        }
        
        // --- 3. NAVIGATION CONTROLS (The Globe/Cube) ---
        // A placeholder for your 3D navigation orb at the bottom
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Navigation Buttons
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.White.copy(alpha = 0.05f), CircleShape)
                    .clickable { onPrev() }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                // Prev button indicator
            }

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        brush = Brush.radialGradient(
                            listOf(primaryColor.copy(alpha = 0.4f), Color.Transparent)
                        ),
                        shape = CircleShape
                    )
                    .border(1.dp, primaryColor.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Center Orb
            }

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.White.copy(alpha = 0.05f), CircleShape)
                    .clickable { onNext() }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                // Next button indicator
            }
        }
    }
}
