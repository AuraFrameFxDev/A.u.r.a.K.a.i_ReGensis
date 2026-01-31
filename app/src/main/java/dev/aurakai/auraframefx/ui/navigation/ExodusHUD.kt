package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.ui.components.PrometheusGlobe
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**
 * 🛰️ EXODUS HUD - THE 5 SOVEREIGN GATES
 *
 * The top region displays a HorizontalPager of monoliths with scale, alpha, and vertical translation driven
 * by each page's offset to create an orbit-like visual effect. Global touch and per-card press events drive
 * a pulse animation that is visualized by the Prometheus Globe. Double-tapping a monolith navigates to
 * "pixel_domain/{id}".
 *
 * @param navController NavController used to navigate to a monolith's pixel domain on double-tap.
 */
@Composable
fun ExodusHUD(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { SovereignRouter.getCount() })

    // Pulse animation for the Prometheus Globe
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

    // Current gate info for display
    val currentGate = gates.getOrNull(pagerState.currentPage) ?: gates[0]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            // Capture touches globally for the "12th Sense" pulse
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onDoubleTap = { /* Consumed here to prevent conflicts, handled in cards */ },
                    onTap = { /* Consumed here to prevent conflicts */ }
                )
            }
    ) {
        // TOP 85%: The 11 Sovereign Monoliths (8K High-Fi)
        Box(
            modifier = Modifier
                .weight(0.85f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
             HorizontalPager(
                 state = pagerState,
                 modifier = Modifier.fillMaxSize(),
                 contentPadding = PaddingValues(horizontal = 64.dp), // Increased padding for 3D effect space
                 pageSpacing = 16.dp
             ) { page ->
                val route = SovereignRouter.fromPage(page)

                // Prometheus Orbit Logic: Calculate scale and alpha based on distance from center
                val pageOffset = (
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                ).absoluteValue

                // We want the center item to be full size, and items to the side to "curve away" (scale down)
                val scale = lerp(
                    start = 0.85f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )

                // Optional: Alpha fade for distant items
                val alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )

                MonolithCard(
                    assetPath = route.highFiPath,
                    onDoubleTap = { navController.navigate("pixel_domain/${route.id}") },
                    onPress = {
                        isPressed = true
                    },
                    onRelease = {
                        isPressed = false
                    },
                    modifier = Modifier
                        .fillMaxHeight(0.9f)
                        .graphicsLayer {
                            val scale = lerp(1f, 0.75f, pageOffset)
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                            // Simple Y translation to simulate arc if needed, but scale often suffices for "orbit" feel in flat 2D
                            translationY = pageOffset * 20.dp.toPx() // Sinks down slightly as it moves away
                        }
                )
            }
        }

        // BOTTOM 15%: The Prometheus Globe (Celestial Navigation)
        Box(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            PrometheusGlobe(
                color = SovereignTeal,
                pulseIntensity = pulseIntensity
            )
        }
    }
}

/**
 * Wrapper for SovereignMonolith to match the "MonolithCard" specification
 * and handle touch events for navigation and pulse feedback.
 */
@Composable
fun SovereignGateCard(
    gateInfo: GateInfo,
    onDoubleTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    SovereignMonolith(
        imagePath = assetPath,
        modifier = modifier
            .fillMaxHeight()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { onDoubleTap() },
                    onPress = {
                        onPress()
                        tryAwaitRelease()
                        onRelease()
                    }
                )
            }
    )
}
