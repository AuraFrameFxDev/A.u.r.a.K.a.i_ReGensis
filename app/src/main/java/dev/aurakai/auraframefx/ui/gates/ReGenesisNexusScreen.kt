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

    // THE VOID CONTAINER
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Deep Void
            .padding(top = 40.dp, bottom = 20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- 1. THE CRT TRANSITION STAGE ---
            // This handles the "Star Ocean" Zoop effect when switching nodes
            Box(
                modifier = Modifier
                    .weight(1f) // Takes most of the screen
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CrtZoopTransition(targetState = currentIndex) { index ->
                    val gate = gates[index]

                    // THE SPLIT LAYOUT
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        // A. THE VISUAL ANCHOR (Top)
                        ElectricGlassCard(
                            modifier = Modifier
                                .width(300.dp)
                                .height(480.dp), // Tall 4K aspect ratio
                            glowColor = gate.color
                        ) {
                            // Render the 4K Asset
                            Image(
                                painter = painterResource(id = gate.cardImageRes),
                                contentDescription = gate.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        // B. THE INTELLIGENCE PANEL (Bottom)
                        // Dedicated space for text - NO MORE OVERLAP
                        HolographicInfoPanel(
                            title = gate.title,
                            description = gate.description,
                            glowColor = gate.color,
                            modifier = Modifier
                                .width(320.dp) // Slightly wider than the card
                                .height(160.dp) // Fixed height for stability
                        )
                    }
                }
            }

            // --- 2. THE GEAR NAVIGATION (Footer) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                CyberGearNav(
                    color = currentGate.color,
                    onPrev = {
                        if (currentIndex > 0) currentIndex--
                        else currentIndex = gates.lastIndex
                    },
                    onNext = {
                        if (currentIndex < gates.lastIndex) currentIndex++
                        else currentIndex = 0
                    }
                )
            }
        }
    }
}
