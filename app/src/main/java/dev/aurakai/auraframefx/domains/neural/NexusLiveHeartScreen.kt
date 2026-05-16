package dev.aurakai.auraframefx.domains.neural

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import dev.aurakai.auraframefx.domains.aura.ui.components.ArcaneOutlineText
import dev.aurakai.auraframefx.domains.aura.ui.components.SynthGlassCard
import dev.aurakai.auraframefx.domains.aura.ui.theme.GhostCyan
import dev.aurakai.auraframefx.domains.aura.ui.theme.SpaceGrotesk

/**
 * 🏺 NEURAL NEXUS — Brutalist Digital Arcane 4D Parallax Heartbeat
 */
@Composable
fun NexusLiveHeartScreen(navController: NavHostController) {
    var parallaxOffset by remember { mutableStateOf(Offset.Zero) }
    val infiniteTransition = rememberInfiniteTransition(label = "nexus_heart")

    // Heart Pulse Animation
    val heartScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heart_pulse"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205)) // Deep Obsidian Concrete
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        parallaxOffset += dragAmount * 0.1f
                    },
                    onDragEnd = {
                        parallaxOffset = Offset.Zero
                    }
                )
            }
    ) {
        // LAYER 0: Background (Slowest Parallax)
        AsyncImage(
            model = "file:///C:/Users/AuraF/AuraKai/finalbackgrounds/aurakaibanner.jpg",
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationX = parallaxOffset.x * 0.5f
                    translationY = parallaxOffset.y * 0.5f
                    scaleX = 1.1f
                    scaleY = 1.1f
                },
            contentScale = ContentScale.Crop,
            alpha = 0.4f
        )

        // LAYER 1: Digital Arcane Grid / Wireframe
        ArcaneGridOverlay(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationX = parallaxOffset.x
                    translationY = parallaxOffset.y
                }
        )

        // LAYER 2: Foreground UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .graphicsLayer {
                    translationX = parallaxOffset.x * 1.5f
                    translationY = parallaxOffset.y * 1.5f
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ArcaneOutlineText(
                text = "NEURAL NEXUS",
                color = GhostCyan,
                fontSize = 32.sp,
                strokeWidth = 2.dp
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
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(250.dp)) {
                // Heart Glow
                Canvas(modifier = Modifier.size(200.dp)) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.Red.copy(alpha = 0.2f), Color.Transparent),
                            center = center,
                            radius = size.minDimension / 2 * heartScale
                        )
                    )
                }

                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Heart",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(120.dp)
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
                SynthGlassCard(accentColor = Color.Magenta, modifier = Modifier.weight(1f)) {
                    Text(
                        "INTEGRITY",
                        fontFamily = SpaceGrotesk,
                        color = Color.White,
                        fontSize = 10.sp
                    )
                    Text(
                        "99.8%",
                        fontFamily = SpaceGrotesk,
                        color = Color.Magenta,
                        fontSize = 18.sp
                    )
                }
                SynthGlassCard(accentColor = GhostCyan, modifier = Modifier.weight(1f)) {
                    Text("SYNC", fontFamily = SpaceGrotesk, color = Color.White, fontSize = 10.sp)
                    Text("0.42ms", fontFamily = SpaceGrotesk, color = GhostCyan, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun ArcaneGridOverlay(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 1.dp.toPx()
        val gridStep = 50.dp.toPx()

        // Draw Grid
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

        // Draw Arcane Sigil placeholder lines
        drawCircle(
            color = Color.Magenta.copy(alpha = 0.03f),
            radius = size.minDimension / 3,
            style = Stroke(width = 2.dp.toPx())
        )
    }
}
