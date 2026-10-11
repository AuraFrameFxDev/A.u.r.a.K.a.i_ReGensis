package dev.aurakai.auraframefx.domains.aura.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.random.Random

// ... (existing imports)

@Composable
fun HologramTransition(
    visible: Boolean,
    modifier: Modifier = Modifier,
    primaryColor: Color = Color.Cyan,
    secondaryColor: Color = Color.Magenta,
    scanLineDensity: Int = 8,
    glitchIntensity: Float = 0.1f,
    edgeGlowIntensity: Float = 0.3f,
    content: @Composable () -> Unit
) {
    // Animation states
    val transition = updateTransition(visible, label = "hologramTransition")
    // ⚡ Bolt Optimization: Switch to direct State access to defer reads to draw/layout phase
    val alphaState = transition.animateFloat(
        transitionSpec = { tween(durationMillis = if (visible) 800 else 500) },
        label = "alpha"
    ) { if (it) 1f else 0f }

    val currentDensity = LocalDensity.current
    // ⚡ Bolt Optimization: Hoist density-dependent px conversions to avoid per-frame toPx() calls
    val gridSizePx = remember(currentDensity) { with(currentDensity) { 20.dp.toPx() } }
    val gridStrokePx = remember(currentDensity) { 0.5f / currentDensity.density }
    val scanLineStrokePx = remember(currentDensity) { 1f / currentDensity.density }

    // Scan line animation
    val infiniteTransition = rememberInfiniteTransition(label = "scanLine")
    // ⚡ Bolt Optimization: Switch to direct State access to defer reads to draw/layout phase
    val scanLineOffsetState = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scanLineOffset"
    )

    // Edge glow animation
    // ⚡ Bolt Optimization: Hoist constant colors to avoid per-frame allocation
    val primaryAlpha0 = remember(primaryColor) { primaryColor.copy(alpha = 0f) }

    // Draw the hologram effect
    Box(
        modifier = modifier
            .clipToBounds()
            .graphicsLayer {
                // ⚡ Bolt Optimization: Defer state read to graphicsLayer phase
                this.alpha = alphaState.value
            }
    ) {
        // Content
        content()

        // Hologram overlay
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            // ⚡ Bolt Optimization: Capture state values once per draw frame
            val currentAlpha = alphaState.value
            val currentScanLineOffset = scanLineOffsetState.value

            // ⚡ Bolt Optimization: Pre-calculate alpha-modified colors once per frame
            val edgeGlowAlpha = 0.3f * edgeGlowIntensity * currentAlpha
            val gridColor = primaryColor.copy(alpha = 0.1f * currentAlpha)
            val scanLineColor = secondaryColor.copy(alpha = 0.1f * currentAlpha)
            val bracketColor = primaryColor.copy(alpha = 0.8f * currentAlpha)
            val edgeGlowModifiedColor = primaryColor.copy(alpha = edgeGlowAlpha)

            // Draw edge glow
            val edgeGlowBrush = Brush.linearGradient(
                colors = listOf(edgeGlowModifiedColor, primaryAlpha0),
                start = Offset(0f, 0f),
                end = Offset(width * 0.3f, 0f)
            )

            // Draw grid
            // ⚡ Bolt Optimization: Use manual while loop and hoisted pixel values to avoid iterator/range allocations
            val gridStep = gridSizePx.toInt()
            if (gridStep > 0) {
                var x = 0f
                while (x <= width) {
                    drawLine(
                        color = gridColor,
                        start = Offset(x, 0f),
                        end = Offset(x, height),
                        strokeWidth = gridStrokePx
                    )
                    x += gridSizePx
                }
                var y = 0f
                while (y <= height) {
                    drawLine(
                        color = gridColor,
                        start = Offset(0f, y),
                        end = Offset(width, y),
                        strokeWidth = gridStrokePx
                    )
                    y += gridSizePx
                }
            }

            // Draw scan lines
            val scanLineSpacing = height / scanLineDensity
            val scanLineY = (currentScanLineOffset * scanLineSpacing * 2) - scanLineSpacing
            for (i in -1..scanLineDensity) {
                val y = scanLineY + (i * scanLineSpacing)
                drawLine(
                    color = scanLineColor,
                    start = Offset(0f, y),
                    end = Offset(width, y),
                    strokeWidth = scanLineStrokePx
                )
            }

            // Apply glitch effect
            val glitchOffset = (1f - glitchIntensity) + (Random.nextFloat() * glitchIntensity * 2f)

            withTransform(
                transformBlock = {
                    translate(
                        left = (Random.nextFloat() - 0.5f) * 2f * glitchIntensity * 10f,
                        top = (Random.nextFloat() - 0.5f) * 2f * glitchIntensity * 10f
                    )
                    scale(
                        scaleX = glitchOffset,
                        scaleY = glitchOffset,
                        pivot = Offset(width / 2f, height / 2f)
                    )
                }
            ) {
                // Draw edge glow on all four sides
                // Left edge
                drawRect(
                    brush = edgeGlowBrush,
                    topLeft = Offset(0f, 0f),
                    size = Size(width * 0.3f, height)
                )

                // Right edge
                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(primaryAlpha0, edgeGlowModifiedColor)
                    ),
                    topLeft = Offset(width * 0.7f, 0f),
                    size = Size(width * 0.3f, height)
                )

                // Top edge
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(edgeGlowModifiedColor, primaryAlpha0)
                    ),
                    topLeft = Offset(0f, 0f),
                    size = Size(width, height * 0.3f)
                )

                // Bottom edge
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(primaryAlpha0, edgeGlowModifiedColor)
                    ),
                    topLeft = Offset(0f, height * 0.7f),
                    size = Size(width, height * 0.3f)
                )
            }

            // Draw corner brackets
            val bracketSize = 20f
            val bracketWidth = 2f

            // Top-left bracket
            drawLine(
                color = bracketColor,
                start = Offset(0f, 0f),
                end = Offset(bracketSize, 0f),
                strokeWidth = bracketWidth
            )
            drawLine(
                color = bracketColor,
                start = Offset(0f, 0f),
                end = Offset(0f, bracketSize),
                strokeWidth = bracketWidth
            )

            // Top-right bracket
            drawLine(
                color = bracketColor,
                start = Offset(width - bracketSize, 0f),
                end = Offset(width, 0f),
                strokeWidth = bracketWidth
            )
            drawLine(
                color = bracketColor,
                start = Offset(width, 0f),
                end = Offset(width, bracketSize),
                strokeWidth = bracketWidth
            )

            // Bottom-left bracket
            drawLine(
                color = bracketColor,
                start = Offset(0f, height - bracketSize),
                end = Offset(0f, height),
                strokeWidth = bracketWidth
            )
            drawLine(
                color = bracketColor,
                start = Offset(0f, height),
                end = Offset(bracketSize, height),
                strokeWidth = bracketWidth
            )

            // Bottom-right bracket
            drawLine(
                color = bracketColor,
                start = Offset(width - bracketSize, height),
                end = Offset(width, height),
                strokeWidth = bracketWidth
            )
            drawLine(
                color = bracketColor,
                start = Offset(width, height - bracketSize),
                end = Offset(width, height),
                strokeWidth = bracketWidth
            )

            // Draw some random digital noise
            if (visible && glitchIntensity > 0.1f) {
                val noiseCount = (width * height * 0.0005f * glitchIntensity).toInt()
                // ⚡ Bolt Optimization: Use manual for loop to avoid repeat() iterator allocation
                for (i in 0 until noiseCount) {
                    val x = Random.nextFloat() * width
                    val y = Random.nextFloat() * height
                    val noiseSize = Random.nextFloat() * 2f * glitchIntensity
                    drawCircle(
                        color = primaryColor.copy(alpha = Random.nextFloat() * 0.5f * currentAlpha),
                        radius = noiseSize,
                        center = Offset(x, y)
                    )
                }
            }
        }
    }
}

