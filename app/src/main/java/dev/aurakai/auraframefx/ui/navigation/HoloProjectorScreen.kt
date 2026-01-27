package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import dev.aurakai.auraframefx.ui.components.ElectricGlassCard
import dev.aurakai.auraframefx.ui.components.HolographicInfoPanel
import dev.aurakai.auraframefx.ui.components.effects.SentientGlowOrb
import dev.aurakai.auraframefx.ui.effects.CrtScreenTransition
import dev.aurakai.auraframefx.ui.gates.GateDestination

/**
 * 🎥 HOLO-PROJECTOR NAVIGATION (Split Layout Edition)
 * Fixed for Portrait mode to prevent text/image collision.
 * Segregates the 4K Card (Top) and the Info Panel (Bottom).
 */
@Composable
fun HoloProjectorScreen(
    currentGateIndex: Int,
    gates: List<GateDestination> = GateDestination.DEFAULT_LIST,
    onNext: () -> Unit = {},
    onPrev: () -> Unit = {}
) {
    val currentGate = gates[currentGateIndex]
    val primaryColor = currentGate.color

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // --- 1. THE PROJECTOR BEAMS (Subtle Side Emitters) ---
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxHeight(0.6f)
                .width(40.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(primaryColor.copy(alpha = 0.15f), Color.Transparent),
                    )
                )
        )
        
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(0.6f)
                .width(40.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Transparent, primaryColor.copy(alpha = 0.15f)),
                    )
                )
        )

        // --- 2. THE MASTER SPLIT-LAYOUT ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            
            // materialization transition
            CrtScreenTransition(targetState = currentGateIndex) { index ->
                val gate = gates[index]
                
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // --- TOP: THE 4K ARTIFACT ---
                    ElectricGlassCard(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .aspectRatio(0.8f), // Force card-like ratio
                        glowColor = gate.color
                    ) {
                        Image(
                            painter = painterResource(id = gate.cardImageRes),
                            contentDescription = gate.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // --- BOTTOM: THE INFO PANEL ---
                    HolographicInfoPanel(
                        title = gate.title,
                        description = gate.description,
                        glowColor = gate.color,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        
        // --- 3. NAVIGATION CONTROLS (Footer) ---
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.spacedBy(30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Prev Button
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.White.copy(alpha = 0.05f), CircleShape)
                    .border(1.dp, primaryColor.copy(alpha = 0.3f), CircleShape)
                    .clickable { onPrev() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Previous Domain",
                    tint = primaryColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Central Orb (The Sentient Core)
            SentientGlowOrb(
                modifier = Modifier.size(80.dp),
                coreColor = primaryColor
            )

            // Next Button
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.White.copy(alpha = 0.05f), CircleShape)
                    .border(1.dp, primaryColor.copy(alpha = 0.3f), CircleShape)
                    .clickable { onNext() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next Domain",
                    tint = primaryColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
