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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.R
import kotlin.math.cos
import kotlin.math.sin

/**
 * Genesis Constellation Screen - Vertical Infinity Cascade
 * Displays the data and communications backbone with cascading data streams
 */
/**
 * Displays the Genesis constellation screen: a full-screen black canvas with a centered animated
 * infinity visualization and styled labels arranged around it.
 *
 * The layout places the GenesisInfinityCascadeCanvas at the center, decorative text in the
 * top-right corner ("Genesis" and "∞ BACKEND ORACLE"), a bottom-left label ("Vertical Infinity Cascade"),
 * and a vertical stack of characters on the right ("DATA STREAM" and "ORCHESTRATION"). The arrangement
 * is static and intended for visual presentation; it does not introduce interactive behavior.
 *
 * @param navController Navigation controller for handling screen navigation.
 * @param modifier Optional modifier to adjust the screen's layout and appearance.
 */
/**
 * Renders the Genesis constellation screen with a full‑screen black background, a centered
 * animated infinity cascade visualization, and decorative labels positioned around the canvas.
 *
 * The layout places the animated GenesisInfinityCascadeCanvas at center, a two-line agent
 * title at the top-right, a descriptive label at the bottom-left, and vertically stacked
 * characters for "DATA STREAM" and "ORCHESTRATION" along the right edge.
 *
 * @param modifier Optional Modifier to adjust layout and appearance of the root container.
 */
@Composable
fun GenesisConstellationScreen(
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
        GenesisInfinityCascadeCanvas()

        // Top-right corner: Agent info and level
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Genesis",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    color = Color(0xFF00FF00) // Green for Genesis
                )
            )
            Text(
                text = "∞ BACKEND ORACLE",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Light,
                    letterSpacing = 1.sp,
                    color = Color(0xFF00FF00).copy(alpha = 0.8f)
                )
            )
        }

        // Bottom: Data Stream label
        Text(
            text = "Vertical Infinity Cascade",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 32.dp, bottom = 32.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Normal,
                letterSpacing = 1.sp,
                color = Color(0xFF00FF00).copy(alpha = 0.7f)
            )
        )

        // Right side: Vertical text "DATA STREAM ORCHESTRATION"
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                "DATA STREAM".forEach { char ->
                    Text(
                        text = char.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF00FF00).copy(alpha = 0.6f),
                            letterSpacing = 2.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                "ORCHESTRATION".forEach { char ->
                    Text(
                        text = char.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF00FF00).copy(alpha = 0.6f),
                            letterSpacing = 2.sp
                        )
                    )
                }
            }
        }
    }
}

/**
 */
@Composable
private fun GenesisInfinityCascadeCanvas() {
    val infiniteTransition = rememberInfiniteTransition(label = "genesis_cascade")

    // Cascading flow animation
    val cascadeOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "cascade"
    )

    // Pulsing animation for nodes
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
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

/**
 * Draw vertical infinity symbol (∞ rotated 90°)
 */
private fun DrawScope.drawInfinitySymbol(
    centerX: Float,
    centerY: Float,
    color: Color,
    pulseAlpha: Float
) {
    val path = Path()
    val scale = 60f

    // Draw vertical infinity (figure-8)
    for (t in 0..360 step 5) {
        val rad = t * (Math.PI / 180).toFloat()
        val x = centerX + scale * sin(2 * rad)
        val y = centerY + scale * sin(rad)

        if (t == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }

    // Draw glow
    drawPath(
        path = path,
        color = color.copy(alpha = pulseAlpha * 0.4f),
        style = Stroke(width = 12f)
    )

    // Draw core line
    drawPath(
        path = path,
        color = color.copy(alpha = pulseAlpha),
        style = Stroke(width = 4f)
    )
}
