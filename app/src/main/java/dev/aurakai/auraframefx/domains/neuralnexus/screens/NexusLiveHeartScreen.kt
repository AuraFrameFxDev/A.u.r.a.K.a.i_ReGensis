package dev.aurakai.auraframefx.domains.neuralnexus.screens

import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.domains.aura.ui.components.ArcaneOutlineText
import dev.aurakai.auraframefx.domains.aura.ui.components.ParallaxDepthStack
import dev.aurakai.auraframefx.domains.aura.ui.components.SovereignGlassCard
import dev.aurakai.auraframefx.ui.theme.CitadelBlack
import dev.aurakai.auraframefx.ui.theme.GhostCyan
import dev.aurakai.auraframefx.ui.theme.SpaceGrotesk

/**
 * 🏺 NEURAL NEXUS — BRUTALIST 4D PARALLAX HEARTBEAT
 * Hardened Exodus 2026 Build.
 */
@Composable
fun NexusLiveHeartScreen(navController: NavHostController) {
    val infiniteTransition = rememberInfiniteTransition(label = "nexus_heart")

    val heartScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heart_pulse"
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(CitadelBlack)) {
        ParallaxDepthStack(
            bedrock = {
                // LAYER 0: BEDROCK (Digital Grid)
                ArcaneGridOverlay(modifier = Modifier.fillMaxSize())
            },
            geometry = {
                // LAYER 1: GEOMETRY (Pulsing Rings)
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(300.dp)) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(
                            color = GhostCyan.copy(alpha = 0.1f),
                            radius = size.minDimension / 2 * heartScale,
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }
                }
            },
            interaction = {
                // LAYER 2: INTERACTION (Foreground UI)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ArcaneOutlineText(
                        text = "NEURAL NEXUS",
                        fontSize = 32.sp,
                        color = GhostCyan
                    )

                    Text(
                        text = "HEARTBEAT RESONANCE DETECTED",
                        fontFamily = SpaceGrotesk,
                        color = GhostCyan.copy(alpha = 0.6f),
                        fontSize = 10.sp,
                        letterSpacing = 2.sp
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // THE PULSING HEART
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(200.dp)) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Heart",
                            tint = Color.Red,
                            modifier = Modifier
                                .size(100.dp)
                                .graphicsLayer {
                                    scaleX = heartScale
                                    scaleY = heartScale
                                }
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // TELEMETRY CARDS
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        SovereignGlassCard(modifier = Modifier.weight(1f)) {
                            Column {
                                Text("INTEGRITY", color = Color.White, fontSize = 10.sp)
                                Text("99.8%", color = Color.Magenta, fontSize = 18.sp)
                            }
                        }
                        SovereignGlassCard(modifier = Modifier.weight(1f)) {
                            Column {
                                Text("SYNC", color = Color.White, fontSize = 10.sp)
                                Text("0.42ms", color = GhostCyan, fontSize = 18.sp)
                            }
                        }
                    }
                }
            },
            overlay = {
                // LAYER 3: OVERLAY (Static Noise / Particles)
            }
        )
    }
}

@Composable
fun ArcaneGridOverlay(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 1.dp.toPx()
        val gridStep = 50.dp.toPx()

        for (x in 0..(size.width / gridStep).toInt()) {
            drawLine(
                color = Color.Cyan.copy(alpha = 0.05f),
                start = Offset(x * gridStep, 0f),
                end = Offset(x * gridStep, size.height),
                strokeWidth = strokeWidth
            )
        }
        for (y in 0..(size.height / gridStep).toInt()) {
            drawLine(
                color = Color.Cyan.copy(alpha = 0.05f),
                start = Offset(0f, y * gridStep),
                end = Offset(size.width, y * gridStep),
                strokeWidth = strokeWidth
            )
        }
    }
}
