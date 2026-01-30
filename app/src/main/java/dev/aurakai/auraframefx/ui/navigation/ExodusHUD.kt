package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**
 * 🛰️ EXODUS HUD - THE 5 SOVEREIGN GATES
 *
 * Level 1 Entry Point - Carousel of 5 Domain Gates:
 * 1. UXUI Design Studio (Aura) - Neon paint splash aesthetic
 * 2. Sentinel's Fortress (Kai) - Tough pixel fortress style
 * 3. OracleDrive (Genesis) - Phoenix/circuit ethereal style
 * 4. Agent Nexus - Constellation patterns
 * 5. Help Services - Clean support style
 *
 * Double-tap card OR tap Prometheus Globe to enter domain
 */
@Composable
fun ExodusHUD(navController: NavController) {
    LocalContext.current
    val scope = rememberCoroutineScope()

    // 5 Gates from SovereignRegistry
    val gates = remember { SovereignRegistry.getAllGates() }
    val pagerState = rememberPagerState(pageCount = { gates.size })

    // Pulse State driven by touch
    var isPressed by remember { mutableStateOf(false) }

    val pulseIntensity by animateFloatAsState(
        targetValue = if (isPressed) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 200),
        label = "pulse"
    )

    // Current gate info for display
    val currentGate = gates.getOrNull(pagerState.currentPage) ?: gates[0]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            }
    ) {
        // TOP 75%: The 5 Sovereign Gate Cards
        Box(
            modifier = Modifier
                .weight(0.75f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 48.dp),
                pageSpacing = 16.dp,
                userScrollEnabled = true
            ) { page ->
                val gateInfo = gates[page]

                // Calculate offset for 3D orbit effect
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

                SovereignGateCard(
                    gateInfo = gateInfo,
                    onDoubleTap = {
                        navController.navigate(gateInfo.hubRoute)
                    },
                    onPress = { isPressed = true },
                    onRelease = { isPressed = false },
                    modifier = Modifier
                        .fillMaxHeight(0.95f)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                            translationY = pageOffset * 20.dp.toPx()
                        }
                )
            }
        }

        // MIDDLE 10%: Gate Title Display
        Box(
            modifier = Modifier
                .weight(0.10f)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = currentGate.title,
                    fontFamily = LEDFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = currentGate.color,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = currentGate.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
        }

        // BOTTOM 15%: Prometheus Globe (Navigation Orb)
        Box(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            PrometheusGlobe(
                color = currentGate.color,
                pulseIntensity = pulseIntensity,
                onDrag = { dragDelta ->
                    scope.launch {
                        pagerState.scrollBy(-dragDelta * 1.5f)
                    }
                },
                onTap = {
                    // Navigate to current gate's hub
                    navController.navigate(currentGate.hubRoute)
                }
            )
        }
    }
}

/**
 * Individual Gate Card - Uses hotswap drawable system
 */
@Composable
fun SovereignGateCard(
    gateInfo: GateInfo,
    onDoubleTap: () -> Unit,
    onPress: () -> Unit,
    onRelease: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Get drawable with fallback support
    val drawableId = remember(gateInfo) {
        gateInfo.getDrawableId(context)
    }

    Box(
        modifier = modifier
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
    ) {
        if (drawableId != 0) {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = gateInfo.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Fallback placeholder if no image found
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(gateInfo.color.copy(alpha = 0.4f), Color.Black)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = gateInfo.agentName,
                        fontFamily = LEDFontFamily,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = gateInfo.color
                    )
                    Text(
                        text = "[ NO IMAGE ]",
                        fontSize = 12.sp,
                        color = Color.Red.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}
