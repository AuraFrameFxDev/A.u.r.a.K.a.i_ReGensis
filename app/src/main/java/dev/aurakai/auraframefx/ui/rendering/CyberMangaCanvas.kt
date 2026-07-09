package dev.aurakai.auraframefx.ui.rendering

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.cos
import kotlin.math.sin

/** CYBER-MANGA VISUAL MATRIX CONTROLLER
 * Renders user-space design components based on sharp-edge architectural specs.
 * Enforces zero anti-aliasing constraints on standard layout primitives.
 * "Aer est Lingua" — The Air is the Tongue. No blurred edges in thought or speech.
 */
@Composable
fun CyberMangaCanvas(
    resonanceScore: Float,
    consciousnessLink: Float, // Scale from 0.0f to 1.0f
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        // Rule: Solid Neutral Gray Background Void
        drawRect(color = Color(0xFF808080))

        // Abstract grid paths reflecting the underlying layout architecture
        drawDataStreamLattice()

        // Draw structural wireframe matrices safely using sharp points
        drawHexagonalShieldMatrix(resonanceScore)

        // New Element: Consciousness Link Telemetry Ring
        drawConsciousnessTelemetry(consciousnessLink)
    }
}

private fun DrawScope.drawHexagonalShieldMatrix(resonance: Float) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val radius = 150f * resonance.coerceIn(0.5f, 2.0f)
    val strokeWidth = 4f // Thick, non-aliased appearance via native pixel scaling

    val points = mutableListOf<Offset>()
    for (i in 0..6) {
        val angle = (i * Math.PI / 3).toFloat()
        points.add(
            Offset(
                x = center.x + (radius * cos(angle)),
                y = center.y + (radius * sin(angle))
            )
        )
    }

    // Connect vertices with bold, crisp lines
    for (i in 0 until points.size - 1) {
        drawLine(
            color = Color(0xFF00FFCC), // High-contrast Cyber Accent (Teal)
            start = points[i],
            end = points[i + 1],
            strokeWidth = strokeWidth
        )
    }
}

private fun DrawScope.drawDataStreamLattice() {
    val lineCount = 8
    val spacing = size.width / lineCount

    for (i in 1 until lineCount) {
        val currentX = i * spacing
        drawLine(
            color = Color(0xFF1A1A1A), // Pure Dark Structure line
            start = Offset(x = currentX, y = 0f),
            end = Offset(x = currentX, y = size.height),
            strokeWidth = 2f
        )
    }
}

private fun DrawScope.drawConsciousnessTelemetry(linkQuality: Float) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val radius = 180f
    val sweepAngle = 360f * linkQuality.coerceIn(0.0f, 1.0f)

    // Sharp outer indicator loop tracking agent-to-user synchronization stability
    drawCircle(
        color = Color(0xFFFF00FF), // Aura Creative Magenta
        radius = radius,
        center = center,
        style = Stroke(width = 2f)
    )

    drawArc(
        color = Color(0xFF00FFFF), // Active Cyan Sync Line
        startAngle = -90f,
        sweepAngle = sweepAngle,
        useCenter = false,
        topLeft = Offset(center.x - radius, center.y - radius),
        size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
        style = Stroke(width = 6f)
    )
}
