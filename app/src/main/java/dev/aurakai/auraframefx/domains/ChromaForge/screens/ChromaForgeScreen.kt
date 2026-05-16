package dev.aurakai.auraframefx.domains.chromaforge.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.domains.aura.ui.components.SovereignGlassCard
import dev.aurakai.auraframefx.domains.aura.ui.theme.GhostCyan
import dev.aurakai.auraframefx.domains.aura.ui.theme.OverclockOrange
import dev.aurakai.auraframefx.domains.aura.ui.theme.WireframeStyle
import dev.aurakai.auraframefx.ui.global.Cadberrypi

/**
 * 🎨 CHROMA FORGE — Full Creative Trinity (Hub 2)
 * The Creative Sword's manifestation chamber.
 */
@Composable
fun ChromaForgeScreen(navController: NavController) {
    // Bedrock background for the Forge
    val bg = painterResource(id = R.drawable.chroma_forge_bedrock)

    Box(Modifier.fillMaxSize()) {
        // Layer 0: Bedrock Background
        Image(
            painter = bg,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.92f }
        )

        // Layer 1: 4D Parallax Effect (Aesthetic Depth)
        // ArcaneProfileBackground() // Placeholder for the actual parallax component

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .statusBarsPadding()
        ) {
            Text(
                text = "CHROMA FORGE",
                style = WireframeStyle.copy(
                    fontSize = 36.sp,
                    shadow = Shadow(color = GhostCyan, blurRadius = 12f)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(40.dp))

            // ── CREATIVE TRINITY HUB ──

            // Hub 2.1: ChromaCore
            SovereignGlassCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = { /* Navigate to live color synthesis */ }
            ) {
                Column {
                    Text(
                        text = "CHROMA CORE",
                        color = GhostCyan,
                        style = WireframeStyle,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "LIVE PALETTE REACTOR — REAL-TIME SYNTHESIS",
                        color = GhostCyan.copy(alpha = 0.6f),
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Hub 2.2: ChronoKinetic Engine
            SovereignGlassCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = { /* Navigate to animation sequencer */ }
            ) {
                Column {
                    Text(
                        text = "CHRONOKINETIC ENGINE",
                        color = GhostCyan,
                        style = WireframeStyle,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "TIMING FORGE — ANIMATION SEQUENCING",
                        color = GhostCyan.copy(alpha = 0.6f),
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Hub 2.3: Collab Canvas
            SovereignGlassCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate("collab_canvas") }
            ) {
                Column {
                    Text(
                        text = "COLLAB CANVAS",
                        color = GhostCyan,
                        style = WireframeStyle,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "78 AGENT MESH — SHARED CREATIVE WORKSPACE",
                        color = GhostCyan.copy(alpha = 0.6f),
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // SPELLHOOK v2.7 — System Weaving
            SovereignGlassCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = { /* Spellhook.cast() logic here */ }
            ) {
                Column {
                    Text(
                        text = "SPELLHOOK v2.7",
                        color = OverclockOrange,
                        style = WireframeStyle,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "MANIFEST NEW WEAVE — RUNTIME SYSTEM INJECTION",
                        color = OverclockOrange.copy(alpha = 0.8f),
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Global wandering assistant roaming the forge
        Cadberrypi()
    }
}
