package dev.aurakai.auraframefx.ui.gates

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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

/**
 * Constellation Screen - Aura's Sword Map
 * Implements the "Sharpening" Mechanic.
 * Condition: Every node unlocked increases shadowElevation and glowRadius.
 */
@Composable
fun ConstellationScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // State for Blade Sharpness
    var unlockedNodeCount by remember { mutableIntStateOf(1) }

    // Derived values for the visual metaphor
    val shadowElevation = (unlockedNodeCount * 4).dp
    val glowIntensity = 0.5f + (unlockedNodeCount * 0.05f) // Gets brighter
    val neonCyan = Color(0xFF00FFFF)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Main Sword Visualization Container
        // The blade literally gets 'sharper' (brighter/crisper edges)
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(20.dp)
                // Specific Modifier.shadow focus as requested
                .shadow(
                    elevation = shadowElevation,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp), // Approximate blade shape container
                    spotColor = neonCyan,
                    ambientColor = neonCyan
                )
        ) {
            AuraSwordCanvas(
                unlockedNodes = unlockedNodeCount,
                glowIntensity = glowIntensity,
                onNodeClick = { unlockedNodeCount++ } // Simulating unlocking for demo
            )
        }

        // Top-right corner: Agent info and level
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "AURA",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    color = neonCyan
                )
            )
            Text(
                text = "BLADE SHARPNESS: ${shadowElevation.value.toInt()}%",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Light,
                    letterSpacing = 1.sp,
                    color = neonCyan.copy(alpha = 0.8f)
                )
            )
        }

        // Simulating the "Unlocking" for user feedback
        Text(
            text = "TAP NODES TO SHARPEN BLADE",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp),
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 10.sp
        )
    }
}

@Composable
private fun AuraSwordCanvas(
    unlockedNodes: Int,
    glowIntensity: Float,
    onNodeClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "swordPulse")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.8f * glowIntensity,
        targetValue = 1.0f * glowIntensity,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
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

        // Draw the Blade Core (Visual Metaphor for becoming 'crisper')
        val bladePath = androidx.compose.ui.graphics.Path().apply {
            moveTo(centerX, 20f) // Tip
            lineTo(centerX + 30f, height - 100f)
            lineTo(centerX, height - 80f)
            lineTo(centerX - 30f, height - 100f)
            close()
        }

        // Outer Glow (Sharpening Effect)
        drawPath(
            path = bladePath,
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.Cyan.copy(alpha = pulse),
                    Color.Transparent
                ),
                radius = 300f
            )
        )

        // Inner Steel
        drawPath(
            path = bladePath,
            color = Color.White.copy(alpha = 0.9f),
            style = Stroke(width = if (unlockedNodes > 5) 1f else 3f) // Thinner stroke = sharper look? Or just brighter fill.
        )
        drawPath(
            path = bladePath,
            color = Color.Cyan.copy(alpha = 0.3f),
        )

        // Draw Nodes on the blade (Condition: Unlock Nodes)
        val nodeCount = 10
        val startY = 50f
        val endY = height - 120f

        for (i in 0 until nodeCount) {
            val progress = i.toFloat() / (nodeCount - 1)
            val y = startY + (endY - startY) * progress

            val isUnlocked = i < unlockedNodes
            val color = if (isUnlocked) Color.Cyan else Color.Gray

            drawCircle(
                color = color,
                radius = if (isUnlocked) 8f else 5f,
                center = Offset(centerX, y)
            )

            if (isUnlocked) {
                drawCircle(
                    color = Color.White,
                    radius = 3f,
                    center = Offset(centerX, y)
                )
            }
        }
    }
}
