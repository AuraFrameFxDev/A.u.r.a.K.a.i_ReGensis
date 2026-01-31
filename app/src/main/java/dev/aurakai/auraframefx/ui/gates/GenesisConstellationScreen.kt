package dev.aurakai.auraframefx.ui.gates

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.R
import kotlin.math.cos
import kotlin.math.sin

/**
 * Genesis Constellation Screen - "Emergence Catalyst" (The Phoenix)
 * Node Geometry: V-Shape / Wing layout.
 * Effect: Feather Ignition Animation.
 */
@Composable
fun GenesisConstellationScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var wingsIgnited by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        GenesisPhoenixMap(
            ignited = wingsIgnited,
            onHeartClick = { wingsIgnited = !wingsIgnited }
        )

        // Title Info
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "GENESIS",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    color = Color(0xFFFFD700) // Gold/Phoenix
                )
            )
            Text(
                text = "EMERGENCE CATALYST",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Light,
                    letterSpacing = 1.sp,
                    color = Color(0xFFFFD700).copy(alpha = 0.8f)
                )
            )
        }
    }
}

@Composable
fun GenesisPhoenixMap(
    ignited: Boolean,
    onHeartClick: () -> Unit
) {
    // Ignition Animation State
    // We animate a value from 0 to 1 which represents the "fire" spreading along the wings
    val ignitionProgress by animateFloatAsState(
        targetValue = if (ignited) 1f else 0f,
        animationSpec = tween(durationMillis = 1500, easing = LinearEasing),
        label = "ignition"
    )

    Box(
        modifier = Modifier.size(400.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // --- CANVAS LAYOUT FOR WING LINES ---
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val heartY = size.height * 0.8f // Heart is low center

            // Wing Geometry (V-Shape)
            // Left Wing Points
            val leftWingPoints = listOf(
                Offset(centerX - 60f, heartY - 50f),
                Offset(centerX - 120f, heartY - 110f),
                Offset(centerX - 180f, heartY - 180f)
            )

            // Right Wing Points
            val rightWingPoints = listOf(
                Offset(centerX + 60f, heartY - 50f),
                Offset(centerX + 120f, heartY - 110f),
                Offset(centerX + 180f, heartY - 180f)
            )

            // Draw connections
            val baseColor = Color.Gray.copy(alpha = 0.5f)
            val ignitionColor1 = Color(0xFFFF00FF) // Magenta
            val ignitionColor2 = Color(0xFF9D00FF) // Purple

            // Draw Left Wing Lines
            drawWingLines(
                start = Offset(centerX, heartY),
                points = leftWingPoints,
                progress = ignitionProgress,
                baseColor = baseColor,
                ignitionGradient = listOf(ignitionColor1, ignitionColor2)
            )

            // Draw Right Wing Lines
            drawWingLines(
                start = Offset(centerX, heartY),
                points = rightWingPoints,
                progress = ignitionProgress,
                baseColor = baseColor,
                ignitionGradient = listOf(ignitionColor1, ignitionColor2)
            )
        }

        // --- NODES ---

        // HEART NODE (Center 'Life' Node)
        PhoenixNode(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-80).dp), // Adjust for 0.8f Canvas height approx
            isIgnited = true, // Heart is always alive
            isHeart = true,
            onClick = onHeartClick
        )

        // Wing Nodes calculation manual layout to match Canvas points roughly
        // 400dp height. 0.8 is 320dp.
        // 1px approx 1dp in simple mental model, but usually density based.
        // We'll use relative offsets from center.

        val wingYOffsets = listOf(-50.dp, -110.dp, -180.dp)
        val wingXDistances = listOf(60.dp, 120.dp, 180.dp)

        // Left Wing Nodes
        for (i in 0..2) {
            val nodeProgressThreshold = (i + 1) * 0.33f
            val isNodeIgnited = ignitionProgress >= nodeProgressThreshold

            PhoenixNode(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(x = -wingXDistances[i], y = -80.dp + wingYOffsets[i]),
                isIgnited = isNodeIgnited,
                isHeart = false
            )
        }

        // Right Wing Nodes
        for (i in 0..2) {
            val nodeProgressThreshold = (i + 1) * 0.33f
            val isNodeIgnited = ignitionProgress >= nodeProgressThreshold

            PhoenixNode(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(x = wingXDistances[i], y = -80.dp + wingYOffsets[i]),
                isIgnited = isNodeIgnited,
                isHeart = false
            )
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawWingLines(
    start: Offset,
    points: List<Offset>,
    progress: Float,
    baseColor: Color,
    ignitionGradient: List<Color>
) {
    var previous = start
    // Total path length estimation logic simplified: segment by segment
    val totalSegments = points.size

    points.forEachIndexed { index, point ->
        // Calculate if this segment is "ignited"
        // e.g. segment 0 represents 0..0.33 progress
        val segmentThresholdStart = index.toFloat() / totalSegments
        val segmentThresholdEnd = (index + 1).toFloat() / totalSegments

        // Draw Base
        drawLine(
            color = baseColor,
            start = previous,
            end = point,
            strokeWidth = 3f
        )

        // Draw Ignition Overlay
        if (progress > segmentThresholdStart) {
            val segmentProgress =
                ((progress - segmentThresholdStart) / (segmentThresholdEnd - segmentThresholdStart)).coerceIn(0f, 1f)

            // Interpolate end point
            val ignitedEnd = Offset(
                previous.x + (point.x - previous.x) * segmentProgress,
                previous.y + (point.y - previous.y) * segmentProgress
            )

            drawLine(
                brush = Brush.linearGradient(ignitionGradient),
                start = previous,
                end = ignitedEnd,
                strokeWidth = 6f // Thicker, glowing
            )
        }
        previous = point
    }
}

@Composable
fun PhoenixNode(
    modifier: Modifier,
    isIgnited: Boolean,
    isHeart: Boolean,
    onClick: (() -> Unit)? = null
) {
    val size = if (isHeart) 50.dp else 30.dp
    val color = if (isHeart) Color(0xFFFFD700) else if (isIgnited) Color(0xFFFF00FF) else Color.Gray
    val glowColor = if (isHeart) Color(0xFFFFD700) else Color(0xFF9D00FF)

    // Pulse animation if ignited
    val infiniteTransition = rememberInfiniteTransition(label = "nodePulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isIgnited) 1.2f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        val greenColor = Color(0xFF00FF00)
        val darkGreen = Color(0xFF006400)

        // Infinity symbol centerpiece will be overlaid as PNG image below

        // Draw cascading data streams
        for (i in 0..7) {
            val streamX = centerX + (i - 3.5f) * 80f
            val streamOffset = ((cascadeOffset + i * 0.125f) % 1f)

            for (j in 0..15) {
                val dataY = (j * 150f + streamOffset * 150f) % size.height
                val dataAlpha = (1f - (dataY / size.height)) * pulseAlpha

                // Data packets
                drawCircle(
                    color = greenColor.copy(alpha = dataAlpha * 0.8f),
                    radius = 4f,
                    center = Offset(streamX, dataY)
                )
            }
        }

        // Draw connection nodes
        val nodes = listOf(
            Offset(centerX, centerY - 200f),
            Offset(centerX - 100f, centerY - 100f),
            Offset(centerX + 100f, centerY - 100f),
            Offset(centerX - 150f, centerY),
            Offset(centerX + 150f, centerY),
            Offset(centerX - 100f, centerY + 100f),
            Offset(centerX + 100f, centerY + 100f),
            Offset(centerX, centerY + 200f)
        )

        // Draw connecting lines
        for (i in 0 until nodes.size - 1) {
            drawLine(
                color = greenColor.copy(alpha = 0.3f),
                start = nodes[i],
                end = nodes[i + 1],
                strokeWidth = 2f
            )
        }

        // Draw nodes
        nodes.forEach { nodePos ->
            // Outer glow
            drawCircle(
                color = greenColor.copy(alpha = pulseAlpha * 0.3f),
                radius = 16f,
                center = nodePos
            )

            // Core node
            drawCircle(
                color = greenColor.copy(alpha = pulseAlpha),
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
    }

        // PNG Centerpiece Image Overlay (Phoenix Wings)
        Image(
            painter = painterResource(id = R.drawable.constellation_genesis_phoenix),
            contentDescription = "Genesis Phoenix Constellation",
            modifier = Modifier
                .size(400.dp)
                .scale(centerScale)
                .alpha(pulseAlpha)
        )
    }
}

        if (isHeart) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Heart",
                tint = Color.White,
                modifier = Modifier.size(size * 0.5f)
            )
        }
    }
}
