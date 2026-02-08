package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.ui.components.PrometheusGlobe
import dev.aurakai.auraframefx.ui.gates.GateCard
import dev.aurakai.auraframefx.ui.gates.GateConfig
import kotlin.math.absoluteValue

/**
 * 🛰️ EXODUS HUD - THE 5 SOVEREIGN GATES
 * UI Navigation Carousel
 */
@Composable
fun ExodusHUD(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { SovereignRegistry.getCount() })
    var isPressed by remember { mutableStateOf(false) }

    // Pulse animation
    val infiniteTransition = rememberInfiniteTransition(label = "Pulse")
    val pulseIntensity by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseIntensity"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // TOP 85%: The 5 Sovereign Gates
        Box(
            modifier = Modifier
                .weight(0.85f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
             HorizontalPager(
                 state = pagerState,
                 modifier = Modifier.fillMaxSize(),
                 contentPadding = PaddingValues(horizontal = 64.dp),
                 pageSpacing = 16.dp
             ) { page ->
                 // Get Gate from registry
                 val gateInfo = SovereignRegistry.getGate("0${page + 1}")

                 // Prometheus Orbit Logic
                val pageOffset = (
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                ).absoluteValue

                val scale = lerp(
                    start = 0.85f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )

                val alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )

                 // Construct GateConfig from gateInfo
                 val config = GateConfig(
                     id = gateInfo.id,
                     moduleId = gateInfo.moduleId,
                     title = gateInfo.title,
                     subtitle = gateInfo.subtitle,
                     description = gateInfo.description,
                     route = gateInfo.hubRoute,
                     glowColor = gateInfo.color,
                     gradientColors = listOf(gateInfo.color, Color.Black),
                     pixelArtUrl = gateInfo.fallbackDrawable,
                     borderColor = gateInfo.color,
                     titlePlacement = dev.aurakai.auraframefx.ui.gates.TitlePlacement.TOP_CENTER  // Title ABOVE image, not on it
                 )

                 Box(
                    modifier = Modifier
                        .fillMaxHeight(0.9f)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                            translationY = pageOffset * 20.dp.toPx()
                        }
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isPressed = true
                                    tryAwaitRelease()
                                    isPressed = false
                                },
                                onDoubleTap = {
                                    navController.navigate(gateInfo.hubRoute)
                                }
                            )
                        }
                 ) {
                     GateCard(
                         config = config,
                         onDoubleTap = { navController.navigate(gateInfo.hubRoute) }
                     )
                 }
            }
        }

        // BOTTOM 15%: The Prometheus Globe
        Box(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            PrometheusGlobe(
                color = Color(0xFF00E5FF), // Sovereign Teal / Aura Cyan
                pulseIntensity = pulseIntensity
            )
        }
    }
}
