package dev.aurakai.auraframefx.domains.aura.ui.components.background

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * 🕸️ SYNAPTIC WEB BACKGROUND (Level 1)
 * A GPU-accelerated canvas that draws a hexagonal neural grid.
 * It connects the 11 nodes of the ReGenesis system.
 */
@Composable
fun SynapticWebBackground(
    modifier: Modifier = Modifier,
    glowColor: Color = Color.Cyan
) {
    // ⚡ Bolt Optimization: Hoist density-dependent calculations to avoid per-frame toPx() calls
    val density = LocalDensity.current
    val hexSizePx = remember(density) { with(density) { 60.dp.toPx() } }
    val strokeWidthPx = remember(density) { with(density) { 1.dp.toPx() } }
    val nodeRadiusPx = remember(density) { with(density) { 2.dp.toPx() } }

    // ⚡ Bolt Optimization: Cache alpha-modified colors to avoid per-frame allocations
    val hexColor = remember(glowColor) { glowColor.copy(alpha = 0.1f) }
    val nodeColor = remember(glowColor) { glowColor.copy(alpha = 0.2f) }

    // ⚡ Bolt Optimization: Pre-calculate hexagon vertex offsets to avoid redundant trig math
    val vertexOffsets = remember(hexSizePx) {
        Array(6) { i ->
            val angle = (60.0 * i - 30.0) * (PI / 180.0)
            Offset(hexSizePx * cos(angle).toFloat(), hexSizePx * sin(angle).toFloat())
        }
    }

    // ⚡ Bolt Optimization: Reuse Path object and Stroke style
    val hexPath = remember { Path() }
    val hexStroke = remember(strokeWidthPx) { Stroke(width = strokeWidthPx) }

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Grid Calculation
        val cols = (width / (hexSizePx * 1.5f)).toInt() + 2
        val rows = (height / (hexSizePx * 1.732f)).toInt() + 2

        for (i in 0 until cols) {
            val colOffset = i * hexSizePx * 1.5f
            val isOddCol = i % 2 == 1
            val colYShift = if (isOddCol) hexSizePx * 0.866f else 0f

            for (j in 0 until rows) {
                val x = colOffset
                val y = j * hexSizePx * 1.732f + colYShift

                // ⚡ Bolt Optimization: Draw hexagon using pre-calculated offsets and reusable path
                hexPath.reset()
                for (v in 0 until 6) {
                    val offset = vertexOffsets[v]
                    if (v == 0) {
                        hexPath.moveTo(x + offset.x, y + offset.y)
                    } else {
                        hexPath.lineTo(x + offset.x, y + offset.y)
                    }
                }
                hexPath.close()
                drawPath(hexPath, hexColor, style = hexStroke)

                // Draw connection nodes (synapses)
                drawCircle(
                    color = nodeColor,
                    radius = nodeRadiusPx,
                    center = Offset(x, y)
                )
            }
        }
    }
}
