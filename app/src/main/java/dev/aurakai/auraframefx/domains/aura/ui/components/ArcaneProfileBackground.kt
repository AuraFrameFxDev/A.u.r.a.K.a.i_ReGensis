package dev.aurakai.auraframefx.domains.aura.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import dev.aurakai.auraframefx.ui.theme.GhostCyan
import kotlin.math.cos
import kotlin.math.sin

/**
 * 🏺 ARCANE PROFILE BACKGROUND
 * Features 4D Parallax, Digital Arcane wireframes, and high-fidelity background image support.
 */
@Composable
fun ArcaneProfileBackground(
    backgroundImage: Any? = null,
    parallaxOffset: Offset = Offset.Zero,
    accentColor: Color = GhostCyan
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // LAYER 0: THE BEDROCK IMAGE (Slow Parallax)
        backgroundImage?.let {
            AsyncImage(
                model = it,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        translationX = parallaxOffset.x * 0.4f
                        translationY = parallaxOffset.y * 0.4f
                        scaleX = 1.1f
                        scaleY = 1.1f
                    },
                contentScale = ContentScale.Crop,
                alpha = 0.5f
            )
        }

        // LAYER 1: ARCANE WIREFRAME (Medium Parallax)
        ArcaneWireframeOverlay(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationX = parallaxOffset.x * 0.8f
                    translationY = parallaxOffset.y * 0.8f
                },
            color = accentColor.copy(alpha = 0.15f)
        )

        // LAYER 2: DEPTH GRADIENT
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationX = parallaxOffset.x
                    translationY = parallaxOffset.y
                }
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.8f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.9f)
                        )
                    )
                )
        )
    }
}

@Composable
fun ArcaneWireframeOverlay(modifier: Modifier = Modifier, color: Color) {
    val transition = rememberInfiniteTransition(label = "arcane_wireframe")
    val time by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.minDimension / 2.5f

        // Draw Fibonacci-inspired geometric spirals
        val path = Path()
        for (i in 0..360 step 15) {
            val angle = Math.toRadians(i.toDouble() + (time * 360))
            val x = centerX + radius * cos(angle).toFloat()
            val y = centerY + radius * sin(angle).toFloat()

            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)

            // Connect to center to create "shards"
            drawLine(
                color = color,
                start = Offset(centerX, centerY),
                end = Offset(x, y),
                strokeWidth = 1f
            )
        }
        path.close()
        drawPath(path, color, style = Stroke(width = 1f))

        // Outer Hexagon
        val hexPath = Path()
        for (i in 0..5) {
            val angle = Math.toRadians((i * 60).toDouble() + (time * -180))
            val x = centerX + (radius * 1.2f) * cos(angle).toFloat()
            val y = centerY + (radius * 1.2f) * sin(angle).toFloat()
            if (i == 0) hexPath.moveTo(x, y) else hexPath.lineTo(x, y)
        }
        hexPath.close()
        drawPath(hexPath, color, style = Stroke(width = 2f))
    }
}
