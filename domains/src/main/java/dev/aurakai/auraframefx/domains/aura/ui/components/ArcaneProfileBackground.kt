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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import dev.aurakai.auraframefx.domains.aura.ui.theme.GhostCyan
import kotlin.math.cos
import kotlin.math.sin

/**
 * 🏺 ARCANE PROFILE BACKGROUND
 * Features 4D Parallax, Digital Arcane wireframes, and high-fidelity background image support.
 */
@Composable
fun ArcaneProfileBackground(
    backgroundImage: Any?, // Resource ID or File path
    parallaxOffset: Offset,
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

        // ⚡ Bolt Optimization: Hoist alpha-modified colors and gradient brush to avoid per-recomposition allocations
        val wireframeColor = remember(accentColor) { accentColor.copy(alpha = 0.15f) }
        val depthGradient = remember {
            Brush.verticalGradient(
                colors = listOf(
                    Color.Black.copy(alpha = 0.8f),
                    Color.Transparent,
                    Color.Black.copy(alpha = 0.9f)
                )
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
            color = wireframeColor
        )

        // LAYER 2: DEPTH GRADIENT
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationX = parallaxOffset.x
                    translationY = parallaxOffset.y
                }
                .background(depthGradient)
        )
    }
}

@Composable
fun ArcaneWireframeOverlay(modifier: Modifier = Modifier, color: Color) {
    val transition = rememberInfiniteTransition(label = "arcane_wireframe")
    val timeState = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    // ⚡ Bolt Optimization: Hoist Path and Stroke allocations out of the render loop
    val path = remember { Path() }
    val hexPath = remember { Path() }
    val mainStroke = remember { Stroke(width = 1f) }
    val hexStroke = remember { Stroke(width = 2f) }
    val degToRad = 0.017453292f

    Canvas(modifier = modifier) {
        // ⚡ Bolt Optimization: Defer animation state read to the draw phase to avoid recompositions
        val time = timeState.value
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = size.minDimension / 2.5f

        // Draw Fibonacci-inspired geometric spirals
        path.reset()
        for (i in 0..360 step 15) {
            // ⚡ Bolt Optimization: Use float math and constant for degree-to-radian conversion
            val angle = (i.toFloat() + (time * 360f)) * degToRad
            val x = centerX + radius * cos(angle)
            val y = centerY + radius * sin(angle)

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
        drawPath(path, color, style = mainStroke)

        // Outer Hexagon
        hexPath.reset()
        for (i in 0..5) {
            // ⚡ Bolt Optimization: Use float math and constant for degree-to-radian conversion
            val angle = (i.toFloat() * 60f + (time * -180f)) * degToRad
            val x = centerX + (radius * 1.2f) * cos(angle)
            val y = centerY + (radius * 1.2f) * sin(angle)
            if (i == 0) hexPath.moveTo(x, y) else hexPath.lineTo(x, y)
        }
        hexPath.close()
        drawPath(hexPath, color, style = hexStroke)
    }
}
