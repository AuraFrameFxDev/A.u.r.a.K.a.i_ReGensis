package dev.aurakai.auraframefx.ui.gates

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

/**
 * PURE GATE IMAGE - High Fidelity Card
 * Uses 9:21 aspect ratio with slow kinetic floating animation.
 */
@Composable
fun GateCard(
    config: GateConfig,
    modifier: Modifier = Modifier,
    onDoubleTap: () -> Unit
) {
    val context = LocalContext.current
    val resourceName = config.pixelArtUrl ?: "gate_auralab_final"
    val resId = context.resources.getIdentifier(
        resourceName,
        "drawable",
        context.packageName
    )

    // SLOW KINETIC FLOATING ANIMATION
    val infiniteTransition = rememberInfiniteTransition(label = "gateFloat")
    val rotation by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )
    val offsetY by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetY"
    )

    Box(
        modifier = modifier
            .fillMaxHeight()
            .aspectRatio(9f / 21f) // 9:21 aspect ratio
            .graphicsLayer {
                rotationZ = rotation
                translationY = offsetY
            }
            .background(Color.Black)
            .drawBehind {
                val borderWidth = 4f
                drawRect(
                    color = config.glowColor,
                    topLeft = androidx.compose.ui.geometry.Offset(borderWidth / 2, borderWidth / 2),
                    size = androidx.compose.ui.geometry.Size(
                        size.width - borderWidth,
                        size.height - borderWidth
                    ),
                    style = Stroke(borderWidth)
                )
            },
        contentAlignment = Alignment.Center
    ) {
        if (resId > 0) {
            Image(
                painter = painterResource(id = resId),
                contentDescription = config.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.95f
            )
        }
    }
}
