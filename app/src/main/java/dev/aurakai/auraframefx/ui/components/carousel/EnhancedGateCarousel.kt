package dev.aurakai.auraframefx.ui.components.carousel

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.theme.ChessFontFamily
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**
 * 🌍 REGENESIS GATE CAROUSEL - HOLOGRAPHIC EDITION
 */

import dev.aurakai.auraframefx.ui.components.hologram.CardStyle

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

    Box(modifier = modifier.fillMaxSize()) {
        // 1. HUD & BACKDROP
        dev.aurakai.auraframefx.ui.components.hologram.AnimeHUDContainer(
            title = currentGate.gateName,
            description = currentGate.description,
            glowColor = currentGate.glowColor
        ) {
            // 2. 3D ROTATING GATE CARDS
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp)
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

        // Platform glow at bottom
        Canvas(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
                .size(320.dp, 80.dp)
        ) {
            drawOval(
                Brush.radialGradient(
                    listOf(
                        currentGate.glowColor.copy(0.4f),
                        Color.Transparent
                    )
                )
            )
        }

        // Page indicator dots
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
                contentScale = ContentScale.Fit
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
