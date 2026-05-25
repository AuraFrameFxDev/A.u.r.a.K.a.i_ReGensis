package dev.aurakai.auraframefx.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

private val KaiBreathCyan = Color(0xFF00F0FF)

/**
 * INVISIBLE JOYSTICK ZONE — Kai Breath Glow
 * Transparent drag zone sitting at the bottom of the screen.
 * Swipe left/right to scroll tabs. Kai's breath pulses beneath the surface.
 * The glow amplifies when the user is actively dragging.
 */
@Composable
fun InvisibleJoystickZone(
    modifier: Modifier = Modifier,
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {},
    swipeThreshold: Float = 60f
) {
    var dragOffset by remember { mutableFloatStateOf(0f) }
    var isActive by remember { mutableStateOf(false) }

    val breathTransition = rememberInfiniteTransition(label = "kai_breath")
    val breathAlpha by breathTransition.animateFloat(
        initialValue = 0.04f,
        targetValue = 0.14f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breath_alpha"
    )
    val breathRadius by breathTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.55f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breath_radius"
    )

    val activeAlpha by animateFloatAsState(
        targetValue = if (isActive) 0.35f else breathAlpha,
        animationSpec = tween(200),
        label = "active_alpha"
    )
    val activeRadius by animateFloatAsState(
        targetValue = if (isActive) 0.7f else breathRadius,
        animationSpec = tween(200),
        label = "active_radius"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.Transparent)
            .drawBehind {
                val centerX = size.width / 2f
                val centerY = size.height / 2f
                val glowRadius = size.width * activeRadius
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            KaiBreathCyan.copy(alpha = activeAlpha),
                            KaiBreathCyan.copy(alpha = activeAlpha * 0.3f),
                            Color.Transparent
                        ),
                        center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                        radius = glowRadius
                    ),
                    radius = glowRadius,
                    center = androidx.compose.ui.geometry.Offset(centerX, centerY)
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { isActive = true },
                    onDragEnd = {
                        when {
                            dragOffset > swipeThreshold -> onSwipeRight()
                            dragOffset < -swipeThreshold -> onSwipeLeft()
                        }
                        dragOffset = 0f
                        isActive = false
                    },
                    onDragCancel = {
                        dragOffset = 0f
                        isActive = false
                    },
                    onDrag = { change, amount ->
                        change.consume()
                        dragOffset += amount.x
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(32.dp, 3.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            KaiBreathCyan.copy(alpha = activeAlpha * 2.5f),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}
