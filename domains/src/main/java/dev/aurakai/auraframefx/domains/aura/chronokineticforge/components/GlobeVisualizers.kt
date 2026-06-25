package dev.aurakai.auraframefx.domains.aura.chronokineticforge.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

/**
 * 🌍 GLOBE VISUALIZERS
 *
 * Aura Globe (Magenta) - Creation, expansion, outward flow
 * Kai Globe (Cyan) - Protection, containment, inward focus
 *
 * These are the twin visual anchors of the ChronoKinetic Forge.
 */

@Composable
fun AuraGlobe(
    modifier: Modifier = Modifier,
    isActive: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "aura_globe")

    // Outward expansion rotation (counter-clockwise)
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // Pulsing glow
    val glowPulse by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Canvas(modifier = modifier.size(48.dp)) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.width / 2 - 4.dp.toPx()
        val toRad = (kotlin.math.PI / 180f).toFloat()

        // Outer glow ring - expands outward
        // ⚡ Bolt Optimization: Reuse color with alpha
        drawCircle(
            color = Color(0xFFFF00FF).copy(alpha = 0.2f * glowPulse),
            radius = radius * 1.3f,
            center = Offset(centerX, centerY)
        )

        // Main sphere
        // ⚡ Bolt Optimization: Removed unused RadialGradientShader allocation
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFFF66FF),
                    Color(0xFFFF00FF),
                    Color(0xFFFF00FF).copy(alpha = 0.4f)
                ),
                center = Offset(centerX, centerY - radius * 0.2f),
                radius = radius
            ),
            radius = radius,
            center = Offset(centerX, centerY)
        )

        // Latitudes (horizontal) - expanding outward
        // ⚡ Bolt Optimization: Pre-calculate latitude color
        val latitudeColor = Color.White.copy(alpha = 0.4f)
        for (i in 1..3) {
            val yOffset = centerY + (i * radius / 4) * kotlin.math.sin((rotation + i * 45f) * toRad)
            drawLine(
                color = latitudeColor,
                start = Offset(centerX - radius * 0.8f, yOffset),
                end = Offset(centerX + radius * 0.8f, yOffset),
                strokeWidth = 1f
            )
        }

        // Longitudes (vertical arcs) - rotating
        val longitudes = 4
        // ⚡ Bolt Optimization: Pre-calculate longitude color
        val longitudeColor = Color.White.copy(alpha = 0.5f)
        for (i in 0 until longitudes) {
            val angle = rotation + (i * 360f / longitudes)
            val cosAngle = kotlin.math.cos(angle * toRad)
            val xOffset03 = cosAngle * radius * 0.3f
            val xOffset10 = cosAngle * radius

            val x1 = centerX + xOffset03
            val y1 = centerY - radius * 0.8f
            val x2 = centerX + xOffset10
            val y2 = centerY
            val x3 = centerX + xOffset03
            val y3 = centerY + radius * 0.8f

            // Draw arc as connected line segments
            drawLine(
                color = longitudeColor,
                start = Offset(x1, y1),
                end = Offset(x2, y2),
                strokeWidth = 1.5f
            )
            drawLine(
                color = longitudeColor,
                start = Offset(x2, y2),
                end = Offset(x3, y3),
                strokeWidth = 1.5f
            )
        }

        // Highlight dot - "creation pulse"
        // ⚡ Bolt Optimization: Replace Math.toRadians with faster multiplication
        val pulseAngle = rotation * 2 * toRad
        val pulseX = centerX + kotlin.math.cos(pulseAngle) * radius * 0.5f
        val pulseY = centerY + kotlin.math.sin(pulseAngle) * radius * 0.3f
        drawCircle(
            color = Color.White,
            radius = 3f,
            center = Offset(pulseX, pulseY)
        )
    }
}

@Composable
fun KaiGlobe(
    modifier: Modifier = Modifier,
    isActive: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "kai_globe")

    // Inward contraction rotation (clockwise - opposite of Aura)
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // Steady shield pulse
    val shieldPulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shield"
    )

    Canvas(modifier = modifier.size(48.dp)) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.width / 2 - 4.dp.toPx()
        val toRad = (kotlin.math.PI / 180f).toFloat()

        // Inner shield ring - contracts inward
        drawCircle(
            color = Color(0xFF00E5FF).copy(alpha = 0.15f),
            radius = radius * 0.7f * shieldPulse,
            center = Offset(centerX, centerY)
        )

        // Shield hexagon pattern
        // ⚡ Bolt Optimization: Draw hexagon directly without list/Offset allocations
        val hexRadius = radius * 0.6f
        val hexColor = Color(0xFF00E5FF).copy(alpha = 0.6f)
        for (i in 0..5) {
            val angle1 = ((60f * i) - 30f) * toRad
            val angle2 = ((60f * (i + 1)) - 30f) * toRad
            drawLine(
                color = hexColor,
                start = Offset(
                    centerX + kotlin.math.cos(angle1) * hexRadius,
                    centerY + kotlin.math.sin(angle1) * hexRadius
                ),
                end = Offset(
                    centerX + kotlin.math.cos(angle2) * hexRadius,
                    centerY + kotlin.math.sin(angle2) * hexRadius
                ),
                strokeWidth = 2f
            )
        }

        // Main sphere - more contained, structured
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFF00E5FF).copy(alpha = 0.3f),
                    Color(0xFF00B8D4),
                    Color(0xFF006064)
                ),
                center = Offset(centerX, centerY),
                radius = radius
            ),
            radius = radius,
            center = Offset(centerX, centerY)
        )

        // Grid lines - structured, defensive
        // Horizontal lines - stable
        // ⚡ Bolt Optimization: Pre-calculate grid line color and reuse math results
        val gridColor = Color(0xFF00E5FF).copy(alpha = 0.3f)
        for (i in -2..2) {
            val ratio = i / 3f
            val xOffset = radius * kotlin.math.sqrt(1f - ratio * ratio)
            val y = centerY + i * radius / 3f
            drawLine(
                color = gridColor,
                start = Offset(centerX - xOffset, y),
                end = Offset(centerX + xOffset, y),
                strokeWidth = 1f
            )
        }

        // Vertical arcs rotating inward
        val longitudes = 6
        // ⚡ Bolt Optimization: Pre-calculate longitude color and use toRad
        val longColor = Color(0xFF00E5FF).copy(alpha = 0.4f)
        for (i in 0 until longitudes) {
            val angle = rotation + (i * 60f)
            // More rigid, structural lines
            val x = centerX + kotlin.math.cos(angle * toRad) * radius * 0.9f
            drawLine(
                color = longColor,
                start = Offset(x, centerY - radius * 0.5f),
                end = Offset(x, centerY + radius * 0.5f),
                strokeWidth = 1f
            )
        }

        // Shield nodes at intersections
        drawCircle(
            color = Color(0xFF00E5FF),
            radius = 2f,
            center = Offset(centerX, centerY)
        )

        // Orbital ring - clockwise (containment)
        drawCircle(
            color = Color(0xFF00E5FF).copy(alpha = 0.3f),
            radius = radius * 1.1f,
            center = Offset(centerX, centerY),
            style = Stroke(width = 2f)
        )
    }
}

@Composable
fun DualGlobeHeader(
    modifier: Modifier = Modifier,
    auraActive: Boolean = true,
    kaiActive: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Aura Globe - Creation
        Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
            AuraGlobe(isActive = auraActive)
            Spacer(modifier = Modifier.height(4.dp))
            androidx.compose.material3.Text(
                "AURA",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFFFF00FF)
            )
        }

        // Kai Globe - Protection
        Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
            KaiGlobe(isActive = kaiActive)
            Spacer(modifier = Modifier.height(4.dp))
            androidx.compose.material3.Text(
                "KAI",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF00E5FF)
            )
        }
    }
}
