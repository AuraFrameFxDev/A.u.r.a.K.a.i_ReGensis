package dev.aurakai.auraframefx.kai.ui

import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

/**
 * Calculates hexagonal grid coordinates for Kai's shield visualization.
 * Implements a "Fortress" density algorithm where nodes condense under load.
 */
object KaiShieldMap {
    fun calculateHexPoints(center: Offset, radius: Float): List<Offset> {
        return (0 until 6).map { i ->
            val angle = i * PI / 3 - PI / 2 // Offset to have flat top
            Offset(
                x = center.x + radius * cos(angle).toFloat(),
                y = center.y + radius * sin(angle).toFloat()
            )
        }
    }

    fun generateGrid(width: Float, height: Float, spacing: Float): List<Offset> {
        val points = mutableListOf<Offset>()
        val rowSpacing = spacing * 0.866f // sqrt(3)/2
        
        var row = 0
        while (row * rowSpacing < height + spacing) {
            val offset = if (row % 2 == 1) spacing / 2 else 0f
            var col = 0
            while (col * spacing < width + spacing) {
                points.add(Offset(col * spacing + offset, row * rowSpacing))
                col++
            }
            row++
        }
        return points
    }
}
