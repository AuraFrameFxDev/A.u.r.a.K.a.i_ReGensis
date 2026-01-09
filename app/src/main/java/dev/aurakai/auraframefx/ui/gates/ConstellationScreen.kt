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
 * Constellation Screen - Aura's Sword with Fusion Abilities
 * Displays the constellation network with animated nodes, pulse effects, and the fusion abilities sync bar
 */
@Composable
fun ConstellationScreen(
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
        ConstellationCanvas()

        // Top-right corner: Agent info and level
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Aura",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    color = Color(0xFF00FFFF)
                )
            )
            Text(
                text = "LVL : 5",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Light,
                    letterSpacing = 1.sp,
                    color = Color(0xFF00FFFF).copy(alpha = 0.8f)
                )
            )
        }

        // Bottom: Fusion Abilities sync bar
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 32.dp, bottom = 32.dp, end = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Fusion abilities",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 1.sp,
                    color = Color(0xFF00FFFF).copy(alpha = 0.7f)
                )
            )

            // Fusion sync progress bar
            FusionSyncBar()
        }

        // Right side: Vertical text "SYSTEM CORE OVERDUE PARAMETERS"
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                "SYSTEM CORE".forEach { char ->
                    Text(
                        text = char.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF00FFFF).copy(alpha = 0.6f),
                            letterSpacing = 2.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                "OVERDUE PARAMETERS".forEach { char ->
                    Text(
                        text = char.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF00FFFF).copy(alpha = 0.6f),
                            letterSpacing = 2.sp
                        )
                    )
                }
            }
        }
    }
}

/**
 * Main constellation canvas with sword centerpiece and animated nodes
 */
@Composable
private fun ConstellationCanvas() {
    val infiniteTransition = rememberInfiniteTransition(label = "constellation")

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

    // Rotation for energy flow
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
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
        // Background Canvas for nodes and connections
        Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        val cyanColor = Color(0xFF00FFFF)
        val glowColor = Color(0xFF00BFFF)

        // Define constellation nodes (positions relative to center)
        val nodes = listOf(
            // Top nodes
            Offset(centerX - 100f, centerY - 200f),
            Offset(centerX + 50f, centerY - 180f),

            // Middle-top nodes
            Offset(centerX - 150f, centerY - 100f),
            Offset(centerX, centerY - 120f),
            Offset(centerX + 150f, centerY - 80f),

            // Center nodes (around sword)
            Offset(centerX - 80f, centerY),
            Offset(centerX + 80f, centerY),

            // Middle-bottom nodes
            Offset(centerX - 120f, centerY + 100f),
            Offset(centerX + 60f, centerY + 120f),

            // Bottom nodes
            Offset(centerX - 50f, centerY + 200f),
            Offset(centerX + 100f, centerY + 180f)
        )

        // Draw connecting lines between nodes
        drawLine(
            color = cyanColor.copy(alpha = 0.3f),
            start = nodes[0],
            end = nodes[3],
            strokeWidth = 2f
        )
        drawLine(
            color = cyanColor.copy(alpha = 0.3f),
            start = nodes[3],
            end = nodes[5],
            strokeWidth = 2f
        )
        drawLine(
            color = cyanColor.copy(alpha = 0.3f),
            start = nodes[5],
            end = nodes[7],
            strokeWidth = 2f
        )
        drawLine(
            color = cyanColor.copy(alpha = 0.3f),
            start = nodes[1],
            end = nodes[4],
            strokeWidth = 2f
        )
        drawLine(
            color = cyanColor.copy(alpha = 0.3f),
            start = nodes[4],
            end = nodes[6],
            strokeWidth = 2f
        )
        drawLine(
            color = cyanColor.copy(alpha = 0.3f),
            start = nodes[6],
            end = nodes[8],
            strokeWidth = 2f
        )

        // Sword centerpiece will be overlaid as PNG image below

        // Draw constellation nodes with pulse effect
        nodes.forEach { nodePos ->
            // Outer glow
            drawCircle(
                color = cyanColor.copy(alpha = pulseAlpha * 0.3f),
                radius = 16f,
                center = nodePos
            )

            // Middle ring
            drawCircle(
                color = cyanColor.copy(alpha = pulseAlpha * 0.6f),
                radius = 10f,
                center = nodePos
            )

            // Core node
            drawCircle(
                color = cyanColor.copy(alpha = pulseAlpha),
                radius = 6f,
                center = nodePos
            )

            // Inner bright core
            drawCircle(
                color = Color.White.copy(alpha = pulseAlpha * 0.8f),
                radius = 3f,
                center = nodePos
            )
        }

        // Draw floating particles
        for (i in 0..15) {
            val angle = (rotation + i * 24f) * (Math.PI / 180).toFloat()
            val radius = 150f + (i % 3) * 30f
            val particleX = centerX + cos(angle) * radius
            val particleY = centerY + sin(angle) * radius
            val particleAlpha = ((sin(rotation * 0.02f + i) + 1f) * 0.3f) * pulseAlpha

            drawCircle(
                color = cyanColor.copy(alpha = particleAlpha),
                radius = 2f,
                center = Offset(particleX, particleY)
            )
        }
    }

        // PNG Centerpiece Image Overlay
        Image(
            painter = painterResource(id = R.drawable.constellation_aura_sword),
            contentDescription = "Aura Sword Constellation",
            modifier = Modifier
                .size(400.dp)
                .scale(centerScale)
                .alpha(pulseAlpha)
        )
    }
}

/**
 * Renders the sword centerpiece with an energy blade, guard, handle, pommel, glow layers, and surrounding energy particles.
 *
 * @param centerX X coordinate of the sword's center in the drawing coordinate space.
 * @param centerY Y coordinate of the sword's center in the drawing coordinate space.
 * @param rotation Rotation in degrees used to offset and animate the surrounding energy particles.
 * @param color Primary color for the blade, guard, handle, and core elements.
 * @param glowColor Accent color used for glow layers and highlights around the sword.
 */
private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawSword(
    centerX: Float,
    centerY: Float,
    rotation: Float,
    color: Color,
    glowColor: Color
) {
    val swordLength = 250f
    val swordWidth = 8f
    val handleLength = 60f
    val guardWidth = 50f

    // Sword blade (vertical, pointing up)
    val bladeTop = Offset(centerX, centerY - swordLength / 2)
    val bladeBottom = Offset(centerX, centerY + handleLength / 2)

    // Draw blade glow
    drawLine(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.8f),
                glowColor.copy(alpha = 0.6f),
                color.copy(alpha = 0.4f)
            ),
            startY = bladeTop.y,
            endY = bladeBottom.y
        ),
        start = bladeTop,
        end = bladeBottom,
        strokeWidth = swordWidth * 3f
    )

    // Draw blade core
    drawLine(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.White,
                glowColor,
                color
            ),
            startY = bladeTop.y,
            endY = bladeBottom.y
        ),
        start = bladeTop,
        end = bladeBottom,
        strokeWidth = swordWidth
    )

    // Draw blade edge highlights
    drawLine(
        color = Color.White.copy(alpha = 0.9f),
        start = bladeTop,
        end = Offset(centerX, centerY - swordLength / 4),
        strokeWidth = 2f
    )

    // Draw guard (cross-guard)
    val guardLeft = Offset(centerX - guardWidth / 2, centerY + handleLength / 2)
    val guardRight = Offset(centerX + guardWidth / 2, centerY + handleLength / 2)

    drawLine(
        color = color,
        start = guardLeft,
        end = guardRight,
        strokeWidth = 12f
    )

    // Draw guard glow
    drawLine(
        color = glowColor.copy(alpha = 0.5f),
        start = guardLeft,
        end = guardRight,
        strokeWidth = 20f
    )

    // Draw handle
    val handleTop = Offset(centerX, centerY + handleLength / 2)
    val handleBottom = Offset(centerX, centerY + handleLength)

    drawLine(
        color = color.copy(alpha = 0.8f),
        start = handleTop,
        end = handleBottom,
        strokeWidth = 10f
    )

    // Draw pommel (bottom of handle)
    drawCircle(
        color = color,
        radius = 8f,
        center = handleBottom
    )
    drawCircle(
        color = glowColor.copy(alpha = 0.6f),
        radius = 12f,
        center = handleBottom
    )

    // Draw energy particles around blade
    for (i in 0..8) {
        val angle = (rotation + i * 40f) * (Math.PI / 180).toFloat()
        val particleRadius = 30f
        val particleX = centerX + cos(angle) * particleRadius
        val particleY = centerY - swordLength / 4 + sin(angle) * particleRadius
        val particleAlpha = (sin(rotation * 0.03f + i) + 1f) * 0.3f

        drawCircle(
            color = color.copy(alpha = particleAlpha),
            radius = 3f,
            center = Offset(particleX, particleY)
        )
    }
}

/**
 * Fusion Abilities sync bar - shows fusion capability status
 */
@Composable
private fun FusionSyncBar() {
    val infiniteTransition = rememberInfiniteTransition(label = "fusion_sync")

    // Animated sync progress
    val syncProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sync_progress"
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
        // Fusion ability names
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FusionAbilityIndicator("Hyper-Creation Engine", glowAlpha, Color(0xFF00FFFF))
            FusionAbilityIndicator("Chrono-Sculptor", glowAlpha, Color(0xFF00BFFF))
            FusionAbilityIndicator("Adaptive Genesis", glowAlpha, Color(0xFF00CED1))
        }

        // Sync progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(4.dp)
                .background(Color(0xFF003366))
        ) {
            // Animated progress fill
            Box(
                modifier = Modifier
                    .fillMaxWidth(syncProgress)
                    .fillMaxHeight()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF00FFFF).copy(alpha = glowAlpha),
                                Color(0xFF00BFFF).copy(alpha = glowAlpha),
                                Color(0xFF00CED1).copy(alpha = glowAlpha * 0.8f)
                            )
                        )
                    )
            )
        }

        // Sync status text
        Text(
            text = "SYNC: ${(syncProgress * 100).toInt()}%",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Light,
                letterSpacing = 1.sp,
                color = Color(0xFF00FFFF).copy(alpha = 0.6f)
            )
        )
    }
}

/**
 * Individual fusion ability indicator
 */
@Composable
private fun FusionAbilityIndicator(
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
