package dev.aurakai.auraframefx.ui.gates

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
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
import kotlin.math.cos
import kotlin.math.sin

/**
 * Kai Constellation Screen - The Sentinel Catalyst (The Shield)
 * Based on 'Sentinel Catalyst' prompt.
 * Node Geometry: Radial/Concentric (1 Core, 6 Inner, 6 Outer).
 */
@Composable
fun KaiConstellationScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val neonGreen = Color(0xFF39FF14)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // High-fidelity background grid or glow could go here

        KaiShieldNodeMap(neonGreen = neonGreen)

        // Title Info
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "KAI",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    color = neonGreen
                )
            )
            Text(
                text = "SENTINEL CATALYST",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Light,
                    letterSpacing = 1.sp,
                    color = neonGreen.copy(alpha = 0.8f)
                )
            )
        }
    }
}

@Composable
fun KaiShieldNodeMap(neonGreen: Color) {
    // Animation for active nodes pulsing
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier.size(360.dp), // Container size
        contentAlignment = Alignment.Center
    ) {
        // --- LAYER 1: CONNECTIONS (CANVAS) ---
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2

            // Draw Hexagon Connections (Inner Ring)
            val innerRadius = size.width * 0.25f
            val outerRadius = size.width * 0.45f

            // Connect Center to Inner
            for (i in 0 until 6) {
                val angle = Math.toRadians((i * 60).toDouble()).toFloat()
                val endX = centerX + cos(angle) * innerRadius
                val endY = centerY + sin(angle) * innerRadius

                drawLine(
                    color = neonGreen.copy(alpha = 0.3f),
                    start = Offset(centerX, centerY),
                    end = Offset(endX, endY),
                    strokeWidth = 2f
                )

                // Connect Inner to Outer (Locks)
                val outerX = centerX + cos(angle) * outerRadius
                val outerY = centerY + sin(angle) * outerRadius
                drawLine(
                    color = neonGreen.copy(alpha = 0.15f),
                    start = Offset(endX, endY),
                    end = Offset(outerX, outerY),
                    strokeWidth = 1f
                )

                // Connect Inner Ring neighbors
                val nextAngle = Math.toRadians(((i + 1) * 60).toDouble()).toFloat()
                val nextX = centerX + cos(nextAngle) * innerRadius
                val nextY = centerY + sin(nextAngle) * innerRadius
                drawLine(
                    color = neonGreen.copy(alpha = 0.3f),
                    start = Offset(endX, endY),
                    end = Offset(nextX, nextY),
                    strokeWidth = 2f
                )
            }
        }

        // --- LAYER 2: NODES (COMPOSABLES) ---

        // 1. MASTER NODE (Absolute Center)
        ShieldNode(
            modifier = Modifier.align(Alignment.Center),
            color = neonGreen,
            isLocked = false,
            scale = pulseScale, // Master pulses
            icon = Icons.Default.Shield
        )

        // 2. INNER RING (6 Nodes) - Active/Unlocked
        90.dp // Approx 0.25 of 360 size
        for (i in 0 until 6) {
            val angleDeg = i * 60f
            // Simple polar positioning
            val rad = Math.toRadians(angleDeg.toDouble())
            val xOffset = (cos(rad) * 90).dp // Using fixed distance for Compose alignment
            val yOffset = (sin(rad) * 90).dp

            ShieldNode(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = xOffset, y = yOffset),
                color = neonGreen,
                isLocked = false, // Inner ring is defined as 'Active' in prompt ("Active nodes must pulse")
                scale = 1f // Static size for ring, or pulse slightly
            )
        }

        // 3. OUTER RING (6 'Lock' Nodes) - Perimeter
        160.dp
        for (i in 0 until 6) {
            val angleDeg = i * 60f
            val rad = Math.toRadians(angleDeg.toDouble())
            val xOffset = (cos(rad) * 160).dp
            val yOffset = (sin(rad) * 160).dp

            ShieldNode(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = xOffset, y = yOffset),
                color = neonGreen.copy(alpha = 0.5f), // Dimmer
                isLocked = true, // Prompt: "Outer Ring: 6 'Lock' nodes"
                scale = 1f
            )
        }
    }
}

@Composable
fun ShieldNode(
    modifier: Modifier,
    color: Color,
    isLocked: Boolean,
    scale: Float = 1f,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    Box(
        modifier = modifier
            .scale(scale)
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Black)
            .border(2.dp, if (isLocked) Color.Gray else color, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (isLocked) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Locked",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        } else {
            // Active Node Glow
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(color.copy(alpha = 0.8f), CircleShape)
            )
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Black, // Contrast against glowing node
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
