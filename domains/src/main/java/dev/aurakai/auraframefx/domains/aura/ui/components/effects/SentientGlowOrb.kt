package dev.aurakai.auraframefx.domains.aura.ui.components.effects

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 🔮 SENTIENT GLOW ORB MODES
 */
enum class OrbMode {
    SYSTEM_STATUS,
    THREAT_SCANNER,
    DIAGNOSTIC
}

/**
 * 🔮 SENTIENT GLOW ORB
 * A multi-layered, animated orb that acts as the core of the Holo-Projector.
 * It feels "alive" with pulsing cores and rotating energy rings.
 */
@Composable
fun SentientGlowOrb(
    modifier: Modifier = Modifier,
    mode: OrbMode = OrbMode.SYSTEM_STATUS,
    size: Dp = 32.dp,
    coreColor: Color = Color(0xFF00E5FF),
    diagnosticMode: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label = "OrbPulse")

    // ⚡ Bolt Optimization: Use direct State objects to defer reads to the draw phase
    // Amber Pulse for diagnostic mode, or regular pulse for normal mode
    val activeColorState = animateColorAsState(
        targetValue = if (diagnosticMode) Color(0xFFFFBF00) else coreColor,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "DiagnosticPulse"
    )

    // Core expansion pulse
    val pulseScaleState = infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "CorePulse"
    )

    // Rotation for energy rings
    val rotationState = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "RingRotation"
    )

    // ⚡ Bolt Optimization: Hoist density-dependent allocations and constant color lists
    val density = LocalDensity.current
    val ringStroke1 = remember(density) { Stroke(width = with(density) { 2.dp.toPx() }) }
    val ringStroke2 = remember(density) { Stroke(width = with(density) { 1.dp.toPx() }) }

    Box(
        modifier = modifier.size(size), // ⚡ Bolt Optimization: Correctly use the size parameter
        contentAlignment = Alignment.Center
    ) {
        // --- 1. OUTER GLOW (Deep Blur) ---
        // Note: Blur requires a separate layer/Box for visual correctness
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(30.dp)
                .graphicsLayer {
                    val s = pulseScaleState.value
                    scaleX = s
                    scaleY = s
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val activeColor = activeColorState.value
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(activeColor.copy(alpha = 0.4f), Color.Transparent)
                    )
                )
            }
        }

        // --- 2. ENERGY RINGS & HEART (Consolidated) ---
        // ⚡ Bolt Optimization: Consolidated multiple Canvas layers into one
        Canvas(modifier = Modifier.fillMaxSize()) {
            val activeColor = activeColorState.value
            val rotation = rotationState.value
            val pulseScale = pulseScaleState.value

            // Energy Ring 1 (0.8f size)
            withTransform({
                scale(0.8f, 0.8f)
                rotate(rotation)
            }) {
                val ringColor = activeColor.copy(alpha = 0.6f)
                drawArc(
                    color = ringColor,
                    startAngle = 0f,
                    sweepAngle = 90f,
                    useCenter = false,
                    style = ringStroke1
                )
                drawArc(
                    color = ringColor,
                    startAngle = 180f,
                    sweepAngle = 90f,
                    useCenter = false,
                    style = ringStroke1
                )
            }

            // Energy Ring 2 (0.6f size)
            withTransform({
                scale(0.6f, 0.6f)
                rotate(-rotation * 1.5f)
            }) {
                drawArc(
                    color = activeColor.copy(alpha = 0.8f),
                    startAngle = 45f,
                    sweepAngle = 180f,
                    useCenter = false,
                    style = ringStroke2
                )
            }

            // Heart Core (0.4f size)
            withTransform({
                val s = 0.4f * pulseScale
                scale(s, s)
            }) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White, activeColor, activeColor.copy(alpha = 0.5f))
                    )
                )
            }
        }
    }
}

