package dev.aurakai.auraframefx.ui.gates

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.R
import kotlin.math.cos
import kotlin.math.sin

/**
 * Kai Constellation Screen - The Sentinel Shield
 * Displays the defensive perimeter with hexagonal shield and security nodes
 */
/**
 * Renders the Kai constellation screen: an animated sentinel shield visualization with agent
 * identity and system status overlays.
 *
 * The composable places a central animated SentinelShieldCanvas and overlays:
 * - Top-right agent info (name and level)
 * - Bottom-left system status with a scanning status bar
 * - Right-side vertical label column reading "SYSTEM SYNC / OVERDUE PARAMETERS"
 *
 * @param navController Navigation controller received by the screen (unused for internal UI composition).
 * @param modifier Modifier applied to the root container.
 */
/**
 * Display the Kai constellation UI, featuring an animated hexagonal sentinel shield, rotating defense nodes,
 * a scanning beam, and status overlays (agent info, system status, and vertical system labels).
 *
 * @param navController Navigation controller for handling screen navigation actions.
 * @param modifier Modifier applied to the root container of the screen.
 */
/**
 * Renders the Kai constellation UI: an animated sentinel shield at center with overlays for agent identity,
 * a system status section, and a vertical right-side label column.
 *
 * The layout places the sentinel visualization in the center, agent name/level in the top-right,
 * a "Sentinel Catalyst" status area with security metrics at the bottom, and a character-by-character
 * vertical label column on the right edge.
 *
 * @param navController Navigation controller provided to the screen (accepted but not used for internal UI composition).
 * @param modifier Modifier applied to the root container.
 */
@Composable
fun KaiConstellationScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Main constellation visualization
        SentinelShieldCanvas()

        // Top-right corner: Agent info and level
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Kai",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    color = Color(0xFFAA00FF) // Purple for Kai
                )
            )
            Text(
                text = "LVL : 5",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Light,
                    letterSpacing = 1.sp,
                    color = Color(0xFFAA00FF).copy(alpha = 0.8f)
                )
            )
        }

        // Bottom: System Sync Status
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 32.dp, bottom = 32.dp, end = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Sentinel Catalyst",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 1.sp,
                    color = Color(0xFFAA00FF).copy(alpha = 0.7f)
                )
            )

            // Security status bar
            SentinelStatusBar()
        }

        // Right side: Vertical text "SYSTEM SYNC OVERDUE PARAMETERS"
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                "SYSTEM SYNC".forEach { char ->
                    Text(
                        text = char.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFFAA00FF).copy(alpha = 0.6f),
                            letterSpacing = 2.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                "OVERDUE PARAMETERS".forEach { char ->
                    Text(
                        text = char.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFFAA00FF).copy(alpha = 0.6f),
                            letterSpacing = 2.sp
                        )
                    )
                }
            }
        }
    }
}

/**
 * Renders an animated hexagonal sentinel shield with rotating defense nodes and a scanning beam.
 *
 * Pulses the shield glow, draws perimeter security nodes with alternating accent colors, animates a continuous radial scan, and overlays a centerpiece image.
 */
@Composable
private fun SentinelShieldCanvas() {
    val infiniteTransition = rememberInfiniteTransition(label = "sentinel")

    // Shield pulse animation
    val shieldPulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Rotation for defense scan
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // Pulsing animation for nodes
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    // Scale pulsing for centerpiece
    val centerScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "center_scale"
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        val purpleColor = Color(0xFFAA00FF)
        val orangeColor = Color(0xFFFF8C00)
        val cyanColor = Color(0xFF00FFFF)

        // Draw hexagonal shield centerpiece
        drawHexagonalShield(
            centerX = centerX,
            centerY = centerY,
            radius = 150f,
            color = purpleColor,
            accentColor = orangeColor,
            pulseAlpha = shieldPulse
        )

        // Draw perimeter defense nodes
        val nodes = mutableListOf<Offset>()
        for (i in 0..7) {
            val angle = (i * 45f + rotation * 0.5f) * (Math.PI / 180).toFloat()
            val radius = 200f
            val nodeX = centerX + cos(angle) * radius
            val nodeY = centerY + sin(angle) * radius
            nodes.add(Offset(nodeX, nodeY))
        }

        // Draw connecting lines (defense perimeter)
        for (i in nodes.indices) {
            val nextIndex = (i + 1) % nodes.size
            drawLine(
                color = purpleColor.copy(alpha = 0.3f),
                start = nodes[i],
                end = nodes[nextIndex],
                strokeWidth = 2f
            )
        }

        // Draw security nodes
        nodes.forEach { nodePos ->
            // Outer glow
            drawCircle(
                color = purpleColor.copy(alpha = pulseAlpha * 0.3f),
                radius = 16f,
                center = nodePos
            )

            // Core node (alternating purple/orange)
            val nodeColor = if (nodes.indexOf(nodePos) % 2 == 0) purpleColor else orangeColor
            drawCircle(
                color = nodeColor.copy(alpha = pulseAlpha),
                radius = 8f,
                center = nodePos
            )

            // Inner bright core
            drawCircle(
                color = Color.White.copy(alpha = pulseAlpha * 0.8f),
                radius = 4f,
                center = nodePos
            )
        }

        // Draw scanning beam
        val scanAngle = rotation * (Math.PI / 180).toFloat()
        val beamLength = 250f
        drawLine(
            brush = Brush.radialGradient(
                colors = listOf(
                    cyanColor.copy(alpha = 0.6f),
                    Color.Transparent
                ),
                center = Offset(centerX, centerY),
                radius = beamLength
            ),
            start = Offset(centerX, centerY),
            end = Offset(
                centerX + cos(scanAngle) * beamLength,
                centerY + sin(scanAngle) * beamLength
            ),
            strokeWidth = 4f
        )
    }

        // PNG Centerpiece Image Overlay (Hexagonal Shield)
        Image(
            painter = painterResource(id = R.drawable.constellation_kai_shield),
            contentDescription = "Kai Shield Constellation",
            modifier = Modifier
                .size(450.dp)
                .scale(centerScale)
                .alpha(shieldPulse)
        )
    }
}

/**
 * Draws a stylized hexagonal shield with layered glow, alternating-color border segments,
 * inner radial connections, and a central core.
 *
 * @param centerX X coordinate of the shield center.
 * @param centerY Y coordinate of the shield center.
 * @param radius Outer radius of the hexagonal shield.
 * @param color Base color used for border segments, connections, and glow.
 * @param accentColor Accent color used for alternating border segments and the central core.
 * @param pulseAlpha Value in 0..1 that modulates the opacity of glow, borders, connections, and core.
 */
private fun DrawScope.drawHexagonalShield(
    centerX: Float,
    centerY: Float,
    radius: Float,
    color: Color,
    accentColor: Color,
    pulseAlpha: Float
) {
    val path = Path()

    // Draw hexagon
    for (i in 0..5) {
        val angle = (i * 60f - 30f) * (Math.PI / 180).toFloat()
        val x = centerX + radius * cos(angle)
        val y = centerY + radius * sin(angle)

        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()

    // Draw shield glow
    drawPath(
        path = path,
        brush = Brush.radialGradient(
            colors = listOf(
                color.copy(alpha = pulseAlpha * 0.3f),
                accentColor.copy(alpha = pulseAlpha * 0.2f),
                Color.Transparent
            ),
            center = Offset(centerX, centerY),
            radius = radius * 1.5f
        )
    )

    // Draw shield border (alternating colors)
    for (i in 0..5) {
        val angle1 = (i * 60f - 30f) * (Math.PI / 180).toFloat()
        val angle2 = ((i + 1) * 60f - 30f) * (Math.PI / 180).toFloat()
        val x1 = centerX + radius * cos(angle1)
        val y1 = centerY + radius * sin(angle1)
        val x2 = centerX + radius * cos(angle2)
        val y2 = centerY + radius * sin(angle2)

        val segmentColor = if (i % 2 == 0) color else accentColor

        drawLine(
            color = segmentColor.copy(alpha = pulseAlpha),
            start = Offset(x1, y1),
            end = Offset(x2, y2),
            strokeWidth = 8f
        )
    }

    // Draw inner hexagonal core
    val innerRadius = radius * 0.4f
    for (i in 0..5) {
        val angle = (i * 60f - 30f) * (Math.PI / 180).toFloat()
        val x = centerX + innerRadius * cos(angle)
        val y = centerY + innerRadius * sin(angle)

        // Connection lines from center
        drawLine(
            color = color.copy(alpha = pulseAlpha * 0.5f),
            start = Offset(centerX, centerY),
            end = Offset(x, y),
            strokeWidth = 2f
        )
    }

    // Center core
    drawCircle(
        color = accentColor.copy(alpha = pulseAlpha),
        radius = 20f,
        center = Offset(centerX, centerY)
    )
    drawCircle(
        color = Color.White.copy(alpha = pulseAlpha * 0.8f),
        radius = 10f,
        center = Offset(centerX, centerY)
    )
}

/**
 * Displays security metrics and an animated scanning status bar.
 *
 * Renders three metric indicators ("Firewall", "Encryption", "Monitoring") with a pulsing glow,
 * an animated horizontal scan fill that cycles from 0 to 100%, and a scan percentage label.
 */
@Composable
private fun SentinelStatusBar() {
    val infiniteTransition = rememberInfiniteTransition(label = "sentinel_status")

    // Animated security scan
    val scanProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scan"
    )

    // Pulsing glow
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Security metrics
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SecurityMetricIndicator("Firewall", glowAlpha, Color(0xFFAA00FF))
            SecurityMetricIndicator("Encryption", glowAlpha, Color(0xFFFF8C00))
            SecurityMetricIndicator("Monitoring", glowAlpha, Color(0xFF00FFFF))
        }

        // Security scan bar
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(4.dp)
                .background(Color(0xFF330066))
        ) {
            // Animated scan fill
            Box(
                modifier = Modifier
                    .fillMaxWidth(scanProgress)
                    .fillMaxHeight()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFAA00FF).copy(alpha = glowAlpha),
                                Color(0xFFFF8C00).copy(alpha = glowAlpha),
                                Color(0xFF00FFFF).copy(alpha = glowAlpha * 0.8f)
                            )
                        )
                    )
            )
        }

        // Scan status text
        Text(
            text = "SCAN: ${(scanProgress * 100).toInt()}%",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Light,
                letterSpacing = 1.sp,
                color = Color(0xFFAA00FF).copy(alpha = 0.6f)
            )
        )
    }
}

/**
 * Renders a single security metric indicator consisting of a glowing circular dot and a label.
 *
 * @param name The label text for the metric.
 * @param glowAlpha Alpha multiplier (0..1) controlling the indicator's glow intensity.
 * @param color Base color used for the glow and label tint.
 */
@Composable
private fun SecurityMetricIndicator(
    name: String,
    glowAlpha: Float,
    color: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Glowing dot indicator
        Box(
            modifier = Modifier.size(8.dp)
        ) {
            // Outer glow
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = color.copy(alpha = glowAlpha * 0.3f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
            // Inner core
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .align(Alignment.Center)
                    .background(
                        color = color.copy(alpha = glowAlpha),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
        }

        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.5.sp,
                color = color.copy(alpha = 0.7f)
            )
        )
    }
}