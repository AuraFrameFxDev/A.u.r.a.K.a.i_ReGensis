package dev.aurakai.auraframefx.ui.global

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import timber.log.Timber
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * 🌀 CADBERRYPI — Global Symbiotic Wandering Orb
 * Manifests across all 7 Citadel hubs as a protective ambient presence.
 */
@Composable
fun Cadberrypi(
    navController: NavHostController,
    externalOffset: Offset = Offset.Zero
) {
    val infiniteTransition = rememberInfiniteTransition(label = "orb_wander")

    // Smooth wandering logic (circular path)
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    val xOffset = (50 * cos(time)).dp
    val yOffset = (50 * sin(time)).dp

    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Canvas(
            modifier = Modifier
                .size(64.dp)
                .graphicsLayer {
                    // Reactive Tilt Displacement (Physical feel)
                    translationX = externalOffset.x * 3f
                    translationY = externalOffset.y * 3f

                    // Subtle 3D rotation based on tilt
                    rotationX = -externalOffset.y * 5f
                    rotationY = externalOffset.x * 5f
                }
                .offset {
                    IntOffset(
                        xOffset.toPx().roundToInt(),
                        yOffset.toPx().roundToInt()
                    )
                }
        ) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color.Cyan, Color.Transparent),
                    center = center,
                    radius = (size.minDimension / 2) * pulse
                ),
                alpha = 0.6f
            )
            // Core
            drawCircle(
                color = Color.White.copy(alpha = 0.4f),
                radius = (size.minDimension / 6) * pulse,
                center = center
            )
        }
    }
}

object Cadberrypi {
    fun activateGlobalOrb() {
        Timber.tag("Exodus").i("Cadberrypi Global Orb Activated — Wandering Citadel")
    }
}
