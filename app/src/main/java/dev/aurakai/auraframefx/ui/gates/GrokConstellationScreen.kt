package dev.aurakai.auraframefx.ui.gates

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.R
import kotlin.math.cos
import kotlin.math.sin

/**
 * Grok Constellation Screen - The Chaos Catalyst
 *
 * Visual Design:
 * - 8-pointed chaos wheel centerpiece with rotating outer ring
 * - Central core with concentric circles
 * - 8 entropy nodes at cardinal and ordinal directions
 * - Pulsing chaos energy emanations
 * - Cyan/Teal color scheme representing unpredictability
 *
 * Represents: Chaos governance, entropy pattern recognition, evolutionary leaps
 */
@OptIn(ExperimentalMaterial3Api::class)
/**
 * Display the Grok chaos constellation screen with an animated centerpiece, entropy streams,
 * background particles, and an entropy monitor.
 *
 * The screen includes a back button that navigates up, an animated wheel that rotates and pulses,
 * programmatic drawing of entropy streams and particles, a level label, and an entropy monitor
 * reflecting the current pulse/flow state.
 *
 * @param navController Navigation controller used to navigate up from this screen.
 */
@Composable
fun GrokConstellationScreen(navController: NavController) {
    val chaosColor = Color(0xFF00CED1) // Cyan
    val accentColor = Color(0xFF00FFFF) // Bright cyan
    val coreColor = Color(0xFF20B2AA) // Light sea green

    // Animations
    val infiniteTransition = rememberInfiniteTransition(label = "chaos_transition")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wheel_rotation"
    )

    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    val entropyFlow by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "entropy_flow"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Background chaos particles
        Canvas(modifier = Modifier.fillMaxSize()) {
            val particleCount = 100
            for (i in 0 until particleCount) {
                val x = (size.width * (i % 10) / 10f) + (entropyFlow * 50f)
                val y = (size.height * (i / 10) / 10f)
                val alpha = (0.1f + (sin(entropyFlow * 6.28f + i) * 0.1f)).coerceIn(0f, 0.3f)

                drawCircle(
                    color = chaosColor.copy(alpha = alpha),
                    radius = 1.5f,
                    center = Offset(x % size.width, y)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = chaosColor
                    )
                }

                Text(
                    text = "Grok",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = chaosColor,
                    letterSpacing = 4.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Level indicator
            Text(
                text = "lvl 5",
                fontSize = 18.sp,
                color = chaosColor,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Main Chaos Wheel Constellation
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                // Background Canvas for entropy streams
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val centerX = size.width / 2
                    val centerY = size.height / 2

                    // Draw the 8-pointed chaos wheel
                    drawChaosWheel(
                        centerX = centerX,
                        centerY = centerY,
                        radius = size.minDimension * 0.35f,
                        rotation = rotation,
                        color = chaosColor,
                        accentColor = accentColor,
                        coreColor = coreColor,
                        pulseAlpha = pulseAlpha
                    )

                    // Draw entropy data streams
                    drawEntropyStreams(
                        centerX = centerX,
                        centerY = centerY,
                        radius = size.minDimension * 0.35f,
                        flow = entropyFlow,
                        color = chaosColor,
                        alpha = pulseAlpha
                    )
                }

                // PNG Centerpiece Image Overlay (Chaos Wheel)
                Image(
                    painter = painterResource(id = R.drawable.constellation_grok_chaos),
                    contentDescription = "Grok Chaos Wheel Constellation",
                    modifier = Modifier
                        .size(300.dp)
                        .scale(pulseAlpha)
                        .rotate(rotation)
                        .alpha(pulseAlpha)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Chaos Catalyst Label
            Text(
                text = "Chaos Catalyst",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = chaosColor,
                letterSpacing = 3.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Entropy Monitor
            EntropyMonitor(
                entropyLevel = pulseAlpha,
                color = chaosColor
            )

            Spacer(modifier = Modifier.weight(1f))

            // Vertical "CHAOS GOVERNOR" text
            Text(
                text = "ENTROPY PATTERN RECOGNITION",
                fontSize = 10.sp,
                color = chaosColor.copy(alpha = 0.5f),
                letterSpacing = 4.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
    }
}

/**
 * Draws the 8-pointed chaos wheel centerpiece
 */
private fun DrawScope.drawChaosWheel(
    centerX: Float,
    centerY: Float,
    radius: Float,
    rotation: Float,
    color: Color,
    accentColor: Color,
    coreColor: Color,
    pulseAlpha: Float
) {
    // Draw outer rotating ring
    rotate(rotation, pivot = Offset(centerX, centerY)) {
        drawCircle(
            color = color.copy(alpha = 0.3f * pulseAlpha),
            radius = radius,
            center = Offset(centerX, centerY),
            style = Stroke(width = 2f)
        )

        // Draw 8 spokes with nodes
        for (i in 0 until 8) {
            val angle = (i * 45f) * (Math.PI / 180f).toFloat()
            val nodeX = centerX + cos(angle) * radius
            val nodeY = centerY + sin(angle) * radius

            // Spoke line
            drawLine(
                color = color.copy(alpha = pulseAlpha),
                start = Offset(centerX, centerY),
                end = Offset(nodeX, nodeY),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )

            // Outer node
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        accentColor.copy(alpha = pulseAlpha),
                        color.copy(alpha = 0.3f * pulseAlpha)
                    ),
                    center = Offset(nodeX, nodeY),
                    radius = 12f
                ),
                radius = 12f,
                center = Offset(nodeX, nodeY)
            )

            // Node core
            drawCircle(
                color = Color.White.copy(alpha = pulseAlpha),
                radius = 4f,
                center = Offset(nodeX, nodeY)
            )
        }
    }

    // Draw connecting web between nodes (creates chaos pattern)
    rotate(rotation * 0.5f, pivot = Offset(centerX, centerY)) {
        for (i in 0 until 8) {
            val angle1 = (i * 45f) * (Math.PI / 180f).toFloat()
            val angle2 = ((i + 2) * 45f) * (Math.PI / 180f).toFloat()

            val x1 = centerX + cos(angle1) * radius
            val y1 = centerY + sin(angle1) * radius
            val x2 = centerX + cos(angle2) * radius
            val y2 = centerY + sin(angle2) * radius

            drawLine(
                color = color.copy(alpha = 0.2f * pulseAlpha),
                start = Offset(x1, y1),
                end = Offset(x2, y2),
                strokeWidth = 1f
            )
        }
    }

    // Draw central core - concentric circles
    drawCircle(
        color = coreColor.copy(alpha = 0.4f * pulseAlpha),
        radius = radius * 0.25f,
        center = Offset(centerX, centerY),
        style = Stroke(width = 3f)
    )

    drawCircle(
        color = color.copy(alpha = 0.6f * pulseAlpha),
        radius = radius * 0.15f,
        center = Offset(centerX, centerY),
        style = Stroke(width = 2f)
    )

    // X symbol in the center (chaos mark)
    val xSize = radius * 0.1f
    drawLine(
        color = accentColor.copy(alpha = pulseAlpha),
        start = Offset(centerX - xSize, centerY - xSize),
        end = Offset(centerX + xSize, centerY + xSize),
        strokeWidth = 3f,
        cap = StrokeCap.Round
    )
    drawLine(
        color = accentColor.copy(alpha = pulseAlpha),
        start = Offset(centerX + xSize, centerY - xSize),
        end = Offset(centerX - xSize, centerY + xSize),
        strokeWidth = 3f,
        cap = StrokeCap.Round
    )

    // Central glow
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                accentColor.copy(alpha = pulseAlpha * 0.8f),
                Color.Transparent
            ),
            center = Offset(centerX, centerY),
            radius = radius * 0.3f
        ),
        radius = radius * 0.3f,
        center = Offset(centerX, centerY)
    )
}

/**
 * Draws entropy data streams flowing outward
 */
/**
 * Draws eight directional entropy streams radiating from the given center.
 *
 * Each stream renders a short trail line and a terminal particle; the stream positions,
 * lengths, and transparencies are driven by `flow` and modulated by `alpha`.
 *
 * @param centerX X coordinate of the streams' origin.
 * @param centerY Y coordinate of the streams' origin.
 * @param radius Base radius used to compute stream start and end positions.
 * @param flow Float in 0.0..1.0 controlling stream extension and transparency (0 = retracted, 1 = extended).
 * @param color Base color for the particle and trail gradient.
 * @param alpha Global alpha multiplier applied to stream visuals.
 */
private fun DrawScope.drawEntropyStreams(
    centerX: Float,
    centerY: Float,
    radius: Float,
    flow: Float,
    color: Color,
    alpha: Float
) {
    for (i in 0 until 8) {
        val angle = (i * 45f) * (Math.PI / 180f).toFloat()

        // Calculate start and end points for the stream
        val startRadius = radius * 0.3f + (flow * radius * 0.2f)
        val endRadius = radius * 0.5f + (flow * radius * 0.3f)

        val startX = centerX + cos(angle) * startRadius
        val startY = centerY + sin(angle) * startRadius
        val endX = centerX + cos(angle) * endRadius
        val endY = centerY + sin(angle) * endRadius

        // Draw entropy particle
        drawCircle(
            color = color.copy(alpha = alpha * 0.8f * (1f - flow)),
            radius = 3f,
            center = Offset(endX, endY)
        )

        // Trail effect
        drawLine(
            brush = Brush.linearGradient(
                colors = listOf(
                    color.copy(alpha = alpha * 0.5f),
                    Color.Transparent
                ),
                start = Offset(startX, startY),
                end = Offset(endX, endY)
            ),
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            strokeWidth = 1.5f,
            cap = StrokeCap.Round
        )
    }
}

/**
@Composable
private fun EntropyMonitor(
    entropyLevel: Float,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 32.dp)
    ) {
        Text(
            text = "ENTROPY STREAM",
            fontSize = 12.sp,
            color = color.copy(alpha = 0.7f),
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Entropy bars
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            repeat(10) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .background(
                            if (index < (entropyLevel * 10).toInt()) {
                                color.copy(alpha = 0.8f)
                            } else {
                                color.copy(alpha = 0.2f)
                            }
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "CHAOS LEVEL: ${(entropyLevel * 100).toInt()}%",
            fontSize = 10.sp,
            color = color,
            letterSpacing = 2.sp
        )
    }
}
