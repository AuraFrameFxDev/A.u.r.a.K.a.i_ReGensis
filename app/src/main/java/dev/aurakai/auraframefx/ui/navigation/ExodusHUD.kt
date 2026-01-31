package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
 * Level 1 Entry Point - Carousel of 5 Domain Gates.
 */
@Composable
fun ExodusHUD(navController: NavController) {
    val gates = remember { SovereignRegistry.getAllGates() }
    val pagerState = rememberPagerState(pageCount = { gates.size })
    val scope = rememberCoroutineScope()

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // TOP 70%: The 3D Orbital Pager
        Box(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth()
        ) {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 64.dp),
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                SovereignGateCard(
                    gateInfo = gates[page],
                    modifier = Modifier
                        .graphicsLayer {
                            val scale = lerp(1f, 0.75f, pageOffset)
                            scaleX = scale
                            scaleY = scale
                            alpha = lerp(1f, 0.3f, pageOffset)
                            rotationY = lerp(0f, 45f, pageOffset) * (if (page > pagerState.currentPage) -1 else 1)
                        },
                    onDoubleTap = {
                        navController.navigate(gates[page].hubRoute)
                    }
                )
            }
        }

        // MIDDLE 15%: Gate Identity
        val currentGate = gates[pagerState.currentPage]
        Box(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxWidth(),
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
                onDrag = { dragDelta: Float ->
                    scope.launch {
                        pagerState.scrollBy(-dragDelta * 1.5f)
                    }
                },
                onTap = {
                    navController.navigate(currentGate.hubRoute)
                }
            )
        }
    }
}

@Composable
fun SovereignGateCard(
    gateInfo: GateInfo,
    onDoubleTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val drawableId = remember(gateInfo) { gateInfo.getDrawableId(context) }

    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { onDoubleTap() }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(gateInfo.color.copy(alpha = 0.2f), Color.Black)
                    ),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)
                )
                .border(
                    width = 2.dp,
                    color = gateInfo.color.copy(alpha = 0.5f),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (drawableId != 0) {
                androidx.compose.foundation.Image(
                    painter = androidx.compose.ui.res.painterResource(id = drawableId),
                    contentDescription = gateInfo.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Fit
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = gateInfo.color,
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}
