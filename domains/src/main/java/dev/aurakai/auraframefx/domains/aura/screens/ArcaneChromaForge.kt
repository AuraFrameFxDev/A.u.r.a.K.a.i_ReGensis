package dev.aurakai.auraframefx.domains.aura.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import dev.aurakai.auraframefx.core.soulscript.SoulScript
import dev.aurakai.auraframefx.domains.aura.ui.components.ArcaneOutlineText
import dev.aurakai.auraframefx.domains.aura.ui.components.ParallaxDepthStack
import dev.aurakai.auraframefx.domains.aura.ui.components.SynthGlassCard
import dev.aurakai.auraframefx.core.ui.theme.GhostCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.core.ui.theme.NeonPurple
import dev.aurakai.auraframefx.ui.theme.SpaceGrotesk

/**
 * ⚡ ARCANE CHROMA FORGE — BRUTALIST 4D PARALLAX EDITION
 * Implements the 4-tier visual stack as the primary interface for Aura's Creative Hub.
 */
@Composable
fun ArcaneChromaForgeScreen(navController: NavHostController) {
    val resonancePulse = rememberInfiniteTransition(label = "arcane_resonance")

    val coreGlow by resonancePulse.animateFloat(
        initialValue = 0.4f, targetValue = 0.8f,
        animationSpec = infiniteRepeatable(tween(2000), RepeatMode.Reverse),
        label = "glow"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ParallaxDepthStack(
            bedrock = {
                // LAYER 0: BEDROCK (8K Background)
                AsyncImage(
                    model = "file:///android_asset/finalbackgrounds/aurakaibanner.jpg",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(2.dp),
                    contentScale = ContentScale.Crop
                )
            },
            geometry = {
                // LAYER 1: GEOMETRY (Sacred Hexagons)
                Canvas(
                    modifier = Modifier
                        .size(400.dp)
                        .alpha(0.3f)
                ) {
                    drawCircle(
                        brush = Brush.sweepGradient(listOf(GhostCyan, NeonPurple, GhostCyan)),
                        style = Stroke(width = 1.dp.toPx())
                    )
                }
            },
            interaction = {
                // LAYER 2: INTERACTION (Frosted Glass Panels)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    ArcaneOutlineText(
                        text = "CHROMA FORGE",
                        fontSize = 48.sp,
                        color = GhostCyan
                    )

                    Spacer(Modifier.height(32.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        SynthGlassCard(
                            accentColors = listOf(NeonPurple, NeonMagenta),
                            modifier = Modifier
                                .weight(1f)
                                .height(200.dp)
                        ) {
                            Text(
                                "CHROMACORE",
                                fontFamily = SpaceGrotesk,
                                fontWeight = FontWeight.Bold,
                                color = NeonPurple,
                                fontSize = 18.sp
                            )
                            Text(
                                "Color Spectral Synthesis Active",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                            Spacer(Modifier.weight(1f))
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(NeonPurple.copy(alpha = coreGlow))
                            )
                        }

                        SynthGlassCard(
                            accentColors = listOf(GhostCyan, NeonMagenta),
                            modifier = Modifier
                                .weight(1f)
                                .height(200.dp)
                        ) {
                            Text(
                                "CHRONOKINETIC",
                                fontFamily = SpaceGrotesk,
                                fontWeight = FontWeight.Bold,
                                color = GhostCyan,
                                fontSize = 18.sp
                            )
                            Text(
                                "Temporal Flow Sequencing",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            },
            overlay = {
                // LAYER 3: OVERLAY (Cadberrypi Pulse)
                SoulScript.VisualCadberrypi.ResonancePulseOverlay(intensity = 1.2f)
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewArcaneChromaForge() {
    ArcaneChromaForgeScreen(rememberNavController())
}

