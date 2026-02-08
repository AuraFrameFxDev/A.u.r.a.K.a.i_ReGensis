package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.*

/**
 * ⚙️ CYBER GEAR NAVIGATION
 * A mechanical-themed navigation footer that syncs with the Nexus transition.
 */
@Composable
fun CyberGearNav(
    color: Color,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    var rotationAngle by remember { mutableFloatStateOf(0f) }
    val animatedRotation by animateFloatAsState(
        targetValue = rotationAngle,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = Spring.StiffnessLow),
        label = "GearRotation"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // PREV BUTTON
        NavGearButton(
            color = color,
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = {
                rotationAngle -= 45f
                onPrev()
            }
        )

        Spacer(modifier = Modifier.width(48.dp))

        // CENTRAL MASTER GEAR
        Box(
            modifier = Modifier
                .size(80.dp)
                .rotate(animatedRotation),
            contentAlignment = Alignment.Center
        ) {
            GearGraphic(color = color)
        }

        Spacer(modifier = Modifier.width(48.dp))

        // NEXT BUTTON
        NavGearButton(
            color = color,
            icon = Icons.AutoMirrored.Filled.ArrowForward,
            onClick = {
                rotationAngle += 45f
                onNext()
            }
        )
    }
}

@Composable
private fun NavGearButton(
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(color.copy(alpha = 0.1f), CircleShape)
            .border(1.dp, color.copy(alpha = 0.4f), CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
private fun GearGraphic(color: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val center = center
        val outRadius = size.minDimension / 2
        val innerRadius = outRadius * 0.7f
        val toothDepth = 8.dp.toPx()
        
        // Draw Outer Gear
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(color.copy(alpha = 0.3f), Color.Transparent)
            ),
            radius = outRadius
        )

        // Draw Teeth (Simplified as multiple lines for that "Cyber" look)
        for (i in 0 until 8) {
            val angle = i * 45f
            val startX = center.x + innerRadius * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
            val startY = center.y + innerRadius * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()
            val endX = center.x + outRadius * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
            val endY = center.y + outRadius * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()
            
            drawLine(
                color = color,
                start = androidx.compose.ui.geometry.Offset(startX, startY),
                end = androidx.compose.ui.geometry.Offset(endX, endY),
                strokeWidth = 4.dp.toPx(),
                cap = androidx.compose.ui.graphics.StrokeCap.Round
            )
        }

        // Inner Circle
        drawCircle(
            color = color,
            radius = innerRadius * 0.4f,
            style = Stroke(width = 2.dp.toPx())
        )
    }
}
