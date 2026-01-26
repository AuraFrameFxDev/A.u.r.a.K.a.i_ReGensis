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
                description = "All Genesis related functions. The head honcho system control and agent synchronization.",
                route = NavDestination.GenesisGate.route,
                glowColor = Color(0xFF00FFD4),
                imageRes = R.drawable.gate_oracle_drive,
                cardStyle = CardStyle.MYTHICAL
            ),
            GateItem(
                gateName = "UX/UI DESIGN STUDIO",
                domainName = "Aura Engine",
                tagline = "ARTSY MESSY BUT BEAUTIFUL",
                description = "All UI functions go in here. Paint reality with Aura's creative catalyst.",
                route = NavDestination.AuraGate.route,
                glowColor = Color(0xFFFF00FF),
                imageRes = R.drawable.gate_uiux_studio,
                cardStyle = CardStyle.ARTSY
            ),
            GateItem(
                gateName = "SENTINEL FORTRESS",
                domainName = "Kai Fortress",
                tagline = "PROTECTIVE SYSTEM DEFENSE",
                description = "All system utilities go with Kai. Secure bootloader, ROM tools, and system integrity.",
                route = NavDestination.KaiGate.route,
                glowColor = Color(0xFFFF3366),
                imageRes = R.drawable.gate_sentinel_fortress,
                cardStyle = CardStyle.PROTECTIVE
            ),
            GateItem(
                gateName = "NOTCH BAR",
                domainName = "System UI",
                tagline = "DYNAMIC STATUS AREA",
                description = "Control headers, signal icons, and battery styles. Customize your status bar experience.",
                route = "notch_bar_gate",
                glowColor = Color(0xFFFF9100), // Orange
                imageRes = R.drawable.card_notch_bar,
                cardStyle = CardStyle.PROTECTIVE
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
            // 2. THE PEDESTAL (Volumetric Stage) - Mapped coordinates
            // aligned to match the bottom of the cards
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 0.dp) // Anchored to very bottom
                    .fillMaxWidth(0.7f) // Match wider beam to cover card movement
                    .height(300.dp)
            ) {
                // Procedural Volumetric Beam - Widen to match card width (approx 60-70%)
                VolumetricLightBeam(color = currentGate.glowColor)

                // Active Particle Emitter
                PedestalParticleEmitter(
                    color = currentGate.glowColor,
                    domainName = currentGate.domainName
                )
            }

            // 3. THE CARDS (Drawable Layer)
            // DROPPED DOWN: Reduced bottom padding to bring them closer to projection source
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = maxHeight * 0.1f), // Lowered significantly (was 0.15f + offset)
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 64.dp) // Partial peeking
            ) { pageIndex ->

                // Calculate offset for physics
                val pageOffset = (pagerState.currentPage - pageIndex) + pagerState.currentPageOffsetFraction
                val gate = gates[pageIndex % gates.size]

                GlobeCard(
                    offset = pageOffset,
                    isActive = pageIndex == pagerState.currentPage
                ) {
                    DoubleTapGateCard(
                        gate = gate,
                        onDoubleTap = { onNavigate(gate.route) }
                    )
                }
            }
        }

        // Page indicator
        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp), // Pushed lower
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
fun VolumetricLightBeam(color: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "beam_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    val widthScale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "width"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        // MATCH WIDTH OF PROJECTION: 
        // Card is approx 260dp wide. Top of beam should match this.
        val bottomWidth = size.width * 0.3f
        val topWidth = size.width * 0.8f * widthScale // Wide top to catch the card

        val beamPath = androidx.compose.ui.graphics.Path().apply {
            moveTo(size.width / 2 - bottomWidth / 2, size.height) // Bottom Left
            lineTo(size.width / 2 + bottomWidth / 2, size.height) // Bottom Right
            lineTo(size.width / 2 + topWidth / 2, 0f)             // Top Right
            lineTo(size.width / 2 - topWidth / 2, 0f)             // Top Left
            close()
        }

        drawPath(
            path = beamPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    color.copy(alpha = 0.0f),      // Top (Fade completely at touch point)
                    color.copy(alpha = 0.1f * pulseAlpha), // Mid
                    color.copy(alpha = 0.6f * pulseAlpha)   // Bottom (Intense Source)
                )
            )
        )
    }
}

@Composable
fun PedestalParticleEmitter(color: Color, domainName: String) {
    val isAuraDomain = domainName.contains("Aura Engine", ignoreCase = true)

    // Aura Chaos Palette
    val chaosColors = listOf(Color.Magenta, Color.Cyan, Color(0xFFBD00FF)) // Neon Purple

    val particles = remember { List(40) { ParticleState() } }

    val infiniteTransition = rememberInfiniteTransition(label = "particles")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween( if(isAuraDomain) 5000 else 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        particles.forEachIndexed { index, _ ->
            val seed = index * 1337

            if (isAuraDomain) {
                // --- AURA CHAOS LOGIC (Brownian-ish) ---
                val t = (time + (seed % 100) / 100f) % 1f
                val xBase = (seed % 100) / 100f * size.width
                val yBase = size.height * 0.6f 

                val xJitter = kotlin.math.sin(t * 20 + index) * 40.dp.toPx()
                val yJitter = kotlin.math.cos(t * 15 + index * 2) * 40.dp.toPx()
                val xPos = xBase + xJitter
                val yPos = yBase + yJitter
                val pColor = chaosColors[index % chaosColors.size]
                val pAlpha = 0.8f * kotlin.math.sin(t * 3.14f).absoluteValue 

                drawCircle(
                    color = pColor.copy(alpha = pAlpha),
                    radius = (3.dp.toPx() * (0.5f +  (seed % 50)/50f)),
                    center = Offset(xPos, yPos)
                )

            } else {
                // --- STANDARD LINEAR LOGIC ---
                val speed = 0.2f + ((seed % 100) / 200f)
                val startX = (seed % 100) / 100f * size.width
                val currentProgress = (time * speed + (seed % 1000) / 1000f) % 1f
                val yPos = size.height - (currentProgress * size.height)
                val xDrift = kotlin.math.sin(currentProgress * 10 + index) * 20.dp.toPx()
                val xPos = startX + xDrift
                
                val alpha = when {
                    currentProgress < 0.2f -> currentProgress * 5
                    currentProgress > 0.8f -> (1f - currentProgress) * 5
                    else -> 1f
                }

                drawCircle(
                    color = color.copy(alpha = alpha * 0.6f),
                    radius = (2.dp.toPx() * (1f - currentProgress * 0.5f)),
                    center = Offset(xPos, yPos)
                )
            }
        }
    }
}

class ParticleState()

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GlobeCard(
    offset: Float, 
    isActive: Boolean, 
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current

    // 3D Idle Physics - Enhanced Rotation
    val infiniteTransition = rememberInfiniteTransition(label = "idle_spin")
    val idleRotation by infiniteTransition.animateFloat(
        initialValue = -8f, // Increased range
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = EaseInOutSine), // Faster cycle (6s)
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    Box(
        Modifier
            .fillMaxSize()
            .graphicsLayer {
                val safeOffset = offset.coerceIn(-2f, 2f)
                val absOffset = safeOffset.absoluteValue

                // Perspective
                cameraDistance = 12f * density.density // More extreme perspective

                // Rotation: Page Scroll + Idle Hover
                val effectiveIdle = if (absOffset < 0.1f) idleRotation else 0f
                rotationY = (safeOffset * -25f) + effectiveIdle // Reduced scroll rotation, increased idle presence

                // Focus Dimming
                val scale = lerp(1.15f, 0.85f, absOffset.coerceAtMost(1f)) // Bigger Active
                scaleX = scale
                scaleY = scale

                alpha = lerp(1.0f, 0.5f, absOffset.coerceAtMost(1f)) // Brighter idle
            }
            .background(Color.Transparent)
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
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "yOffset"
    )

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // RESIZED CARD: Fixed dimensions to match projection width approx
        Box(
            Modifier
                .width(260.dp) // Adjusted width
                .height(380.dp) // Adjusted height
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
                contentScale = ContentScale.FillBounds // Fill the fixed box EXACTLY
            )

            // Subtle pulse glow (Removed text overlay as requested "CARDS ARE ENOUGH")
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(15.dp)
                    .background(
                        Brush.radialGradient(
                            listOf(gate.glowColor.copy(alpha = 0.2f), Color.Transparent)
                        )
                    )
            )
        }
    }
}
