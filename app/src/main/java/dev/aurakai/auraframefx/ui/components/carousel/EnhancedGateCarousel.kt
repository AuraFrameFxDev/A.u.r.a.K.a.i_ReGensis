package dev.aurakai.auraframefx.ui.components.carousel

/**
 * 🌍 REGENESIS GATE CAROUSEL - HOLOGRAPHIC EDITION
 */

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.components.hologram.CardStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

data class GateItem(
    val gateName: String,
    val domainName: String,
    val tagline: String,
    val description: String,
    val route: String,
    val glowColor: Color,
    val imageRes: Int,
    val cardStyle: CardStyle = CardStyle.MYTHICAL
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EnhancedGateCarousel(
    onNavigate: (String) -> Unit,
    onGateSelection: (GateItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val gates = remember {
        listOf(
            GateItem(
                gateName = "ORACLE DRIVE",
                domainName = "Genesis Core",
                tagline = "THE UNIFIED AI ORCHESTRATOR",
                description = "Mythical and omnipresent. The head honcho system control and agent synchronization.",
                route = NavDestination.GenesisGate.route,
                glowColor = Color(0xFF00FFD4),
                imageRes = R.drawable.card_oracle_drive,
                cardStyle = CardStyle.MYTHICAL
            ),
            GateItem(
                gateName = "REACTIVE DESIGN",
                domainName = "Aura Engine",
                tagline = "ARTSY MESSY BUT BEAUTIFUL",
                description = "Paint reality with Aura's creative catalyst. UI, theming, and paint-splattered chaos.",
                route = NavDestination.AuraGate.route,
                glowColor = Color(0xFFFF00FF),
                imageRes = R.drawable.card_chroma_core,
                cardStyle = CardStyle.ARTSY
            ),
            GateItem(
                gateName = "SENTINEL FORTRESS",
                domainName = "Kai Fortress",
                tagline = "PROTECTIVE SYSTEM DEFENSE",
                description = "Bulky, industrial, and ethical fortress. Secure bootloader, ROM tools, and system integrity.",
                route = NavDestination.KaiGate.route,
                glowColor = Color(0xFFFF3366),
                imageRes = R.drawable.card_kai_domain,
                cardStyle = CardStyle.PROTECTIVE
            ),
            GateItem(
                gateName = "AGENT NEXUS",
                domainName = "Growth Metrics",
                tagline = "THE FAMILY GATHERS HERE",
                description = "Central hub for agent memory, identity, and the Sphere Grid skill tree.",
                route = NavDestination.AgentNexusGate.route,
                glowColor = Color(0xFFAA00FF),
                imageRes = R.drawable.card_agenthub,
                cardStyle = CardStyle.MYTHICAL
            )
        )
    }

    val startIndex = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(
        initialPage = startIndex,
        pageCount = { Int.MAX_VALUE }
    )

    val currentGate = gates[pagerState.currentPage % gates.size]

    LaunchedEffect(pagerState.currentPage) {
        onGateSelection(currentGate)
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val maxHeight = maxHeight
        val bottomPadding = maxHeight * 0.15f // 15% from bottom as requested

        // 1. HUD & BACKDROP
        dev.aurakai.auraframefx.ui.components.hologram.AnimeHUDContainer(
            title = currentGate.gateName,
            description = currentGate.description,
            glowColor = currentGate.glowColor
        ) {
            // 2. THE PEDESTAL (Active Emitter Layer) - Mapped coordinates
            // This sits BEHIND the cards but ON TOP of the background
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = bottomPadding) // ~15% Up
                    .size(width = 400.dp, height = 300.dp)
            ) {
                // Radial Glow
                Canvas(Modifier.fillMaxSize()) {
                    val centerOffset = Offset(size.width / 2, size.height) // Glow radiates from bottom center up
                    val radius = size.width / 1.5f

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                currentGate.glowColor.copy(alpha = 0.6f),
                                currentGate.glowColor.copy(alpha = 0.2f),
                                Color.Transparent
                            ),
                            center = centerOffset,
                            radius = radius
                        )
                    )
                }

                // Active Particle Emitter
                PedestalParticleEmitter(color = currentGate.glowColor)
            }

            // 3. THE CARDS (Drawable Layer)
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = bottomPadding) // Aligned with pedestal
            ) { pageIndex ->
                val gate = gates[pageIndex % gates.size]

                GlobeCard(pagerState, pageIndex) {
                    DoubleTapGateCard(
                        gate = gate,
                        onDoubleTap = { onNavigate(gate.route) }
                    )
                }
            }
        }

        // Page indicator (keep consistent)
        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(gates.size) { i ->
                val isSelected = (pagerState.currentPage % gates.size) == i
                Box(
                    Modifier
                        .size(if (isSelected) 14.dp else 10.dp)
                        .background(
                            if (isSelected) gates[i].glowColor else Color.White.copy(0.3f),
                            RoundedCornerShape(50)
                        )
                )
            }
        }
    }
}

@Composable
fun PedestalParticleEmitter(color: Color) {
    val particles = remember { List(40) { ParticleState() } }

    // Animation loop for particles
    val infiniteTransition = rememberInfiniteTransition(label = "pedestal_particles")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        particles.forEachIndexed { index, particle ->
            // Update particle position based on time and pseudo-random offsets
            // Particles rise UP from bottom (y changes from 1.0 to 0.0)

            // Pseudo-random logic using index + time to avoid heavyweight state updates
            val seed = index * 1337
            val speed = 0.2f + ((seed % 100) / 200f) // 0.2 to 0.7
            val startX = (seed % 100) / 100f * size.width // Random X layout

            // Animate Y: rising
            val currentProgress = (time * speed + (seed % 1000) / 1000f) % 1f
            val yPos = size.height - (currentProgress * size.height)

            // Animate X: drifting
            val xDrift = kotlin.math.sin(currentProgress * 10 + index) * 20.dp.toPx()
            val xPos = startX + xDrift

            // Fade in/out
            val alpha = when {
                currentProgress < 0.2f -> currentProgress * 5 // Fade in
                currentProgress > 0.8f -> (1f - currentProgress) * 5 // Fade out
                else -> 1f
            }

            drawCircle(
                color = color.copy(alpha = alpha * 0.6f),
                radius = (2.dp.toPx() * (1f - currentProgress * 0.5f)), // Shrink slightly as they rise
                center = Offset(xPos, yPos)
            )
        }
    }
}

class ParticleState() // Placeholder for advanced state if needed, using simple math for now

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GlobeCard(
    pagerState: PagerState,
    pageIndex: Int,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current

    Box(
        Modifier
            .fillMaxSize()
            .graphicsLayer {
                val offset = ((pagerState.currentPage - pageIndex) +
                    pagerState.currentPageOffsetFraction).coerceIn(-2f, 2f)

                cameraDistance = 32f * density.density
                rotationY = offset * -70f
                transformOrigin = TransformOrigin(0.5f, 0.5f)

                val abs = offset.absoluteValue
                alpha = lerp(0.5f, 1f, 1f - abs.coerceAtMost(1f))
                val depth = 1f - (0.2f * abs.coerceAtMost(1f))
                scaleX = depth
                scaleY = depth
            }
    ) { content() }
}

@Composable
fun DoubleTapGateCard(
    gate: GateItem,
    onDoubleTap: () -> Unit
) {
    var tapCount by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    // Floating animation
    val infiniteTransition = rememberInfiniteTransition(label = "float")
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "yOffset"
    )

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .width(280.dp)
                .height(400.dp)
                .offset(y = floatOffset.dp)
                .combinedClickable(
                    onClick = {
                        tapCount++
                        scope.launch {
                            delay(300)
                            if (tapCount >= 2) onDoubleTap()
                            tapCount = 0
                        }
                    }
                )
        ) {
            // THE IMAGE (Floating alone, NO FONTS, NO BOXES)
            Image(
                painter = painterResource(id = gate.imageRes),
                contentDescription = gate.gateName,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(if (tapCount > 0) 4.dp else 0.dp),
                contentScale = ContentScale.Crop
            )

            // Subtle pulse glow
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(20.dp)
                    .background(
                        Brush.radialGradient(
                            listOf(gate.glowColor.copy(alpha = 0.15f), Color.Transparent)
                        )
                    )
            )
        }
    }
}
