package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
                 val gateId = "0${page + 1}"
                 val gateInfo = SovereignRegistry.getGate(gateId)

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
                     GateCard( // Assuming GateCard can handle GateInfo or needs adaptation.
                         // Since GateCard expects GateConfig, and we have GateInfo which is similar...
                         // We might need to map it or use a different card.
                         // For now, let's assume we can map or use the GateCard adapted.
                         // Actually, I'll update GateCard to use GateInfo in a moment or map it here.
                         config = GateConfig(
                             id = gateInfo.id,
                             title = gateInfo.title,
                             subtitle = gateInfo.subtitle,
                             gradientColors = listOf(gateInfo.color, Color.Black),
                             glowColor = gateInfo.color,
                             pixelArtUrl = gateInfo.fallbackDrawable
                         ),
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
