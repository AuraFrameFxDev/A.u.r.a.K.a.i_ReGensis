package dev.aurakai.auraframefx.domains.aura.ui.components.backgrounds

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
// Removed invalid import: androidx.compose.runtime.nodeCount
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * An animated data visualization background with flowing lines and nodes
 * @param primaryColor Primary color for the data lines (default: Cyan)
 * @param secondaryColor Secondary color for the data lines (default: Magenta)
 * @param backgroundColor Background color (default: Transparent)
 * @param lineCount Number of data lines (default: 8)
 * @param nodeCount Number of nodes per line (default: 12)
 * @param animationDuration Duration of one animation cycle in milliseconds (default: 10000)
 */
/**
 * Displays an animated background with flowing radial data lines and glowing nodes.
 *
 * Renders a customizable number of animated radial lines, each composed of nodes with dynamic positions and glowing effects, over a configurable background color. The animation creates a flowing, data-inspired visual effect suitable for dashboards or decorative backgrounds.
 *
 * @param modifier Modifier for layout and drawing constraints.
 * @param primaryColor Color used for alternating primary data lines and grid.
 * @param secondaryColor Color used for alternating secondary data lines.
 * @param backgroundColor Background fill color.
 * @param lineCount Number of radial data lines to draw.
 * @param nodeCount Number of nodes per data line.
 * @param animationDuration Duration of one animation cycle in milliseconds.
 */
/**
 * Displays an animated background with flowing radial lines and glowing nodes for data visualization effects.
 *
 * Renders a customizable number of animated radial lines, each with dynamically moving nodes and glowing highlights, over a configurable background. The animation creates a flowing, data-inspired visual suitable for dashboards or decorative backgrounds.
 *
 * @param modifier Modifier for layout and drawing constraints.
 * @param primaryColor Color used for primary data lines and grid circles.
 * @param secondaryColor Color used for alternating secondary data lines.
 * @param backgroundColor Fill color for the background area.
 * @param lineCount Number of radial data lines to display.
 * @param nodeCount Number of nodes per data line.
 * @param animationDuration Duration of one animation cycle in milliseconds.
 */
@Composable
fun DataVisualizationBackground(
    modifier: Modifier = Modifier,
    primaryColor: Color = Color.Cyan,
    secondaryColor: Color = Color.Magenta,
    backgroundColor: Color = Color.Transparent,
    lineCount: Int = 8,
    nodeCount: Int = 12,
    animationDuration: Int = 10000,
) {
    val density = LocalDensity.current
    val strokeWidth = with(density) { 1.5.dp.toPx() }
    val nodeRadius = with(density) { 2.dp.toPx() }

    // ⚡ Bolt Optimization: Hoist grid color to avoid per-frame allocation
    val gridColor = remember(primaryColor) { primaryColor.copy(alpha = 0.1f) }

    // ⚡ Bolt Optimization: Pre-calculate node and line factors to avoid redundant math in render loop
    val nodeProgress = remember(nodeCount) { FloatArray(nodeCount) { it.toFloat() / (nodeCount - 1) } }
    val nodeAlphas = remember(nodeCount) { FloatArray(nodeCount) { 0.5f + 0.5f * (it.toFloat() / (nodeCount - 1)) } }
    val nodeRadiusFactors = remember(nodeCount) { FloatArray(nodeCount) { 0.5f + 1.5f * (it.toFloat() / (nodeCount - 1)) } }
    val lineAlphaFactors = remember(nodeCount) { FloatArray(nodeCount - 1) { 0.3f + 0.7f * (it.toFloat() / (nodeCount - 1)) } }
    val lineStrokeWidthFactors = remember(nodeCount) { FloatArray(nodeCount - 1) { 0.5f + 0.5f * (it.toFloat() / (nodeCount - 1)) } }

    // ⚡ Bolt Optimization: Pre-calculate color variations to avoid Color.copy() in render loop
    val primaryAlphas = remember(primaryColor, nodeCount) {
        List(nodeCount) { i -> primaryColor.copy(alpha = nodeAlphas[i]) }
    }
    val primaryGlowColors = remember(primaryColor, nodeCount) {
        List(nodeCount) { i -> listOf(primaryColor.copy(alpha = nodeAlphas[i] * 0.3f), primaryColor.copy(alpha = 0f)) }
    }
    val secondaryAlphas = remember(secondaryColor, nodeCount) {
        List(nodeCount) { i -> secondaryColor.copy(alpha = nodeAlphas[i]) }
    }
    val secondaryGlowColors = remember(secondaryColor, nodeCount) {
        List(nodeCount) { i -> listOf(secondaryColor.copy(alpha = nodeAlphas[i] * 0.3f), secondaryColor.copy(alpha = 0f)) }
    }

    val primaryLineColors = remember(primaryColor, nodeCount) {
        List(nodeCount - 1) { i -> primaryColor.copy(alpha = lineAlphaFactors[i]) }
    }
    val secondaryLineColors = remember(secondaryColor, nodeCount) {
        List(nodeCount - 1) { i -> secondaryColor.copy(alpha = lineAlphaFactors[i]) }
    }

    // ⚡ Bolt Optimization: Use pre-allocated FloatArrays to avoid List<Offset> boxing
    val xCoords = remember(nodeCount) { FloatArray(nodeCount) }
    val yCoords = remember(nodeCount) { FloatArray(nodeCount) }

    // Animation values
    val infiniteTransition = rememberInfiniteTransition(label = "dataVizBackground")
    val phaseState = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDuration,
                easing = LinearEasing
            )
        ),
        label = "phase"
    )

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        // Draw background
        drawRect(color = backgroundColor, size = size)

        val center = Offset(size.width / 2, size.height / 2)
        val maxRadius = minOf(size.width, size.height) * 0.8f / 2
        val phase = phaseState.value

        // Draw grid lines
        val gridSteps = 5
        for (i in 0 until gridSteps) {
            val radius = maxRadius * (i + 1) / gridSteps
            drawCircle(
                color = gridColor,
                radius = radius,
                center = center,
                style = Stroke(width = 0.5f)
            )
        }

        // Draw data lines and nodes
        // ⚡ Bolt Optimization: Use manual indexed loops to eliminate Iterator allocations
        for (lineIndex in 0 until lineCount) {
            val angle = 2f * PI.toFloat() * lineIndex / lineCount
            val isPrimary = lineIndex % 2 == 0
            val alphas = if (isPrimary) primaryAlphas else secondaryAlphas
            val glowColors = if (isPrimary) primaryGlowColors else secondaryGlowColors

            // Calculate points along the line with some noise
            for (nodeIndex in 0 until nodeCount) {
                val progress = nodeProgress[nodeIndex]
                val noise = sin(phase * 2 + lineIndex * 0.5f + nodeIndex * 0.3f) * 0.1f
                val radius = (0.3f + 0.7f * progress) * maxRadius * (1 + noise * 0.2f)

                xCoords[nodeIndex] = center.x + radius * cos(angle + noise * 0.2f)
                yCoords[nodeIndex] = center.y + radius * sin(angle + noise * 0.2f)
            }

            // Draw connecting lines
            val lineColors = if (isPrimary) primaryLineColors else secondaryLineColors
            for (i in 0 until nodeCount - 1) {
                val lineColor = lineColors[i]

                drawLine(
                    color = lineColor,
                    start = Offset(xCoords[i], yCoords[i]),
                    end = Offset(xCoords[i + 1], yCoords[i + 1]),
                    strokeWidth = strokeWidth * lineStrokeWidthFactors[i]
                )
            }

            // Draw nodes
            for (index in 0 until nodeCount) {
                val point = Offset(xCoords[index], yCoords[index])
                val nodeRadiusFactor = nodeRadiusFactors[index]
                val radius = nodeRadius * nodeRadiusFactor

                // Outer glow
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = glowColors[index],
                        radius = radius * 3f,
                        center = point
                    ),
                    radius = radius * 3f,
                    center = point
                )

                // Node
                drawCircle(
                    color = alphas[index],
                    radius = radius,
                    center = point
                )
            }
        }
    }
}

/**
 * Displays a preview of the DataVisualizationBackground composable with preset colors and a dark background.
 */
@Composable
@Preview
fun DataVisualizationBackgroundPreview() {
    DataVisualizationBackground(
        primaryColor = Color.Cyan,
        secondaryColor = Color.Magenta,
        backgroundColor = Color(0xFF0A0A1A)
    )
}

