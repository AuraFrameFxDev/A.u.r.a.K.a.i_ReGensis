package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.aurakai.auraframefx.customization.CustomizationViewModel
import dev.aurakai.auraframefx.ui.theme.*
import dev.aurakai.auraframefx.ui.components.background.SynapticWebBackground
import androidx.compose.animation.core.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

/**
 * 🌌 SOVEREIGN PROCESSION SCREEN
 * The unified, high-fidelity habitat for ReGenesis.
 * Splits the screen: 85% Monolith Pager, 15% Sensory Spinner foundation.
 */
@Composable
fun SovereignProcessionScreen(
    onShatterTransition: (String) -> Unit,
    customizationViewModel: CustomizationViewModel = viewModel()
) {
    val customizationState by customizationViewModel.state.collectAsState()
    val gates = SovereignGate.getGatesForMode(customizationState.reGenesisMode)
    
    val pagerState = rememberPagerState(pageCount = { gates.size })
    var isPressed by remember { mutableStateOf(false) }

    Box(
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
        // Dynamic Depth Background
        SynapticWebBackground(glowColor = SovereignTeal)

        Column(modifier = Modifier.fillMaxSize()) {
            // TOP 85%: MONOLITH PAGER
            Box(
                modifier = Modifier
                    .weight(0.85f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 32.dp),
                    pageSpacing = 16.dp
                ) { page ->
                    val gate = gates[page]
                    SovereignMonolith(
                        imagePath = gate.screenshotPath,
                        fallbackRes = gate.imageRes,
                        modifier = Modifier
                            .fillMaxHeight(0.85f)
                            .align(Alignment.Center)
                            .pointerInput(gate.route) {
                                detectTapGestures(
                                    onDoubleTap = {
                                        // Haptic surge + Shatter
                                        onShatterTransition(gate.route)
                                    }
                                )
                            }
                    )
                }
            }

            // BOTTOM 15%: SENSORY SPINNER BASE & 12th SENSE
            Box(
                modifier = Modifier
                    .weight(0.15f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // The Gear Foundation (Rotating Engine)
                SensorySpinner(
                    color = SovereignTeal,
                    modifier = Modifier.size(120.dp).offset(y = 20.dp)
                )

                // The 12th Sense (Pulse Bar)
                SensoryPulseBar(
                    isActive = isPressed,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(0.6f)
                        .height(30.dp)
                )
            }
        }
    }
}

@Composable
fun SensoryPulseBar(isActive: Boolean, modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseHeight by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(150, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "height"
    )

    androidx.compose.foundation.Canvas(modifier = modifier) {
        val count = 12
        val spacing = size.width / (count + 1)
        val centerY = size.height / 2

        for (i in 1..count) {
            val h = if (isActive) {
                // Dynamic oscillation
                (size.height * 0.8f) * (pulseHeight * (0.5f + kotlin.math.sin(i.toDouble() * 0.5).toFloat() * 0.5f))
            } else {
                // Flatline (minimal ghost pulse)
                2.dp.toPx()
            }
            
            drawRect(
                color = SovereignTeal,
                topLeft = Offset(i * spacing - 1.dp.toPx(), centerY - h / 2),
                size = Size(2.dp.toPx(), h)
            )
        }
    }
}
