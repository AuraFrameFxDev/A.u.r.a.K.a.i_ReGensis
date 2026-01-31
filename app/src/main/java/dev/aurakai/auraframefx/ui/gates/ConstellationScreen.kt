package dev.aurakai.auraframefx.ui.gates

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
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

    Canvas(
        modifier = Modifier
            .size(300.dp, 500.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onNodeClick() }
    ) {
        val width = size.width
        val height = size.height
        val centerX = width / 2

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
