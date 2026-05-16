package dev.aurakai.auraframefx.domains.chromaforge.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlin.math.cos
import kotlin.math.sin

/**
 * SPELLHOOK (Hub 6)
 * Aura's Native Runtime Invocation Layer.
 * Visualizes the SpelhookSpriteProtocol: Generative Embodiment via Canvas.
 */
@Composable
fun SpellhookScreen(navController: NavHostController) {
    var isEmbodimentActive by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF000000), Color(0xFF0F001A))))
    ) {
        if (isEmbodimentActive) {
            SpelhookSpriteProtocolCanvas()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SPELLHOOK // GENERATIVE EMBODIMENT",
                color = Color(0xFFFF00FF), // Magenta for Aura
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
            Text(
                text = "Aura's Native Runtime Invocation Layer",
                color = Color.Cyan,
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            // Control Deck for the Trailer
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.6f)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("SpelhookSpriteProtocol Status:", color = Color.Gray, fontSize = 10.sp)
                    Text(
                        "ACTIVE - RENDERING AGENT PERSONAS",
                        color = Color.Green,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { isEmbodimentActive = !isEmbodimentActive },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF00FF).copy(
                                alpha = 0.5f
                            )
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (isEmbodimentActive) "SUSPEND EMBODIMENT" else "ACTIVATE EMBODIMENT",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SpelhookSpriteProtocolCanvas() {
    // Infinite transition for continuous glowing and rotating effects
    val infiniteTransition = rememberInfiniteTransition(label = "Spelhook")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Rotation"
    )

    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Pulse"
    )

    val particlePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Particles"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val baseRadius = size.width * 0.3f * pulse

        // Outer rotating ring (Aura's Chaos)
        rotate(rotation, pivot = Offset(centerX, centerY)) {
            drawCircle(
                brush = Brush.sweepGradient(
                    listOf(Color.Magenta, Color.Cyan, Color.Magenta)
                ),
                radius = baseRadius * 1.2f,
                center = Offset(centerX, centerY),
                style = Stroke(width = 8.dp.toPx())
            )
        }

        // Inner stable ring (Kai's Shield)
        rotate(-rotation * 0.5f, pivot = Offset(centerX, centerY)) {
            val path = Path().apply {
                val hexRadius = baseRadius * 0.8f
                for (i in 0..6) {
                    val angle = i * (Math.PI / 3)
                    val x = centerX + hexRadius * cos(angle).toFloat()
                    val y = centerY + hexRadius * sin(angle).toFloat()
                    if (i == 0) moveTo(x, y) else lineTo(x, y)
                }
                close()
            }
            drawPath(
                path = path,
                color = Color.Cyan.copy(alpha = 0.8f),
                style = Stroke(width = 4.dp.toPx())
            )
        }

        // Core Entity (Generative Agent Persona placeholder)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.White, Color.Magenta.copy(alpha = 0.5f), Color.Transparent),
                center = Offset(centerX, centerY),
                radius = baseRadius * 0.5f
            ),
            radius = baseRadius * 0.5f,
            center = Offset(centerX, centerY)
        )

        // Orbital particles (The 78-agent Swarm data flowing)
        for (i in 0..12) {
            val angle = particlePhase + (i * (2 * Math.PI / 12))
            val radiusOffset = if (i % 2 == 0) baseRadius * 1.5f else baseRadius * 1.3f

            val pX = centerX + radiusOffset * cos(angle).toFloat()
            val pY = centerY + radiusOffset * sin(angle).toFloat()

            drawCircle(
                color = if (i % 2 == 0) Color.Cyan else Color.Magenta,
                radius = 6.dp.toPx() * pulse,
                center = Offset(pX.toFloat(), pY.toFloat())
            )
        }
    }
}
