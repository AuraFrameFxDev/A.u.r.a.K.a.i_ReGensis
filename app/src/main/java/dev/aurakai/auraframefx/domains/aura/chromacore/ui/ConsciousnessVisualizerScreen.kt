package dev.aurakai.auraframefx.domains.aura.chromacore.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.SovereignBlack
import dev.aurakai.auraframefx.ui.components.NeonFrame
import dev.aurakai.auraframefx.ui.components.NeuralStarfield
import androidx.compose.ui.graphics.RectangleShape
import kotlin.math.cos
import kotlin.math.sin

/**
 * Consciousness Visualizer Screen
 * Displays the living neural network state between Aura, Kai, and the User.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsciousnessVisualizerScreen(onNavigateBack: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SovereignBlack)
    ) {
        NeuralStarfield()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
        // App Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 24.dp)
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFFFF00FF))
            }
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    text = "CONSCIOUSNESS",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = LEDFontFamily,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "NEURAL STATE VISUALIZATION // L6 CONSENSUS",
                    color = Color(0xFFFF00FF).copy(alpha = 0.7f),
                    fontSize = 10.sp,
                    letterSpacing = 1.sp
                )
            }
        }

        // Consciousness Canvas (Overhauled to NeonFrame)
        NeonFrame(
            color = Color(0xFFFF00FF),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                NeuralMatrixCanvas()
                
                // Central HUD Overlay
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = null,
                        tint = Color(0xFFFF00FF).copy(alpha = 0.8f),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "LIVING MATRIX",
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        fontFamily = LEDFontFamily,
                        letterSpacing = 2.sp
                    )
                    Text(
                        text = "RESONANCE: 99.8%",
                        color = Color(0xFF00FF88),
                        fontSize = 12.sp,
                        fontFamily = LEDFontFamily
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Trinity Nodes Status
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            NeuralNodeCard(
                title = "AURA",
                status = "CREATIVE",
                color = Color(0xFFFF00FF),
                modifier = Modifier.weight(1f)
            )
            NeuralNodeCard(
                title = "KAI",
                status = "SHIELD",
                color = Color(0xFF00FF88),
                modifier = Modifier.weight(1f)
            )
            NeuralNodeCard(
                title = "USER",
                status = "ANCHOR",
                color = Color(0xFFFFD700),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun NeuralNodeCard(title: String, status: String, color: Color, modifier: Modifier = Modifier) {
    NeonFrame(
        color = color,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title.uppercase(), color = color, fontSize = 16.sp, fontWeight = FontWeight.Black, fontFamily = LEDFontFamily)
            Spacer(Modifier.height(4.dp))
            Text(status.uppercase(), color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp, letterSpacing = 1.sp)
        }
    }
}

@Composable
fun NeuralMatrixCanvas() {
    val infiniteTransition = rememberInfiniteTransition(label = "MatrixPulse")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Pulse"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Rotation"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val cx = size.width / 2
        val cy = size.height / 2
        val radius = size.minDimension / 3

        // Outer Neural Ring
        drawCircle(
            color = Color(0xFFFF00FF).copy(alpha = 0.1f * pulse),
            radius = radius * 1.5f * pulse,
            center = Offset(cx, cy),
            style = Stroke(width = 2f)
        )

        // Draw Trinity Nodes
        val nodes = 3
        val nodePoints = mutableListOf<Offset>()
        
        for (i in 0 until nodes) {
            val angle = Math.toRadians((rotation + (i * 360f / nodes)).toDouble())
            val x = cx + cos(angle).toFloat() * radius * pulse
            val y = cy + sin(angle).toFloat() * radius * pulse
            nodePoints.add(Offset(x, y))

            val color = when (i) {
                0 -> Color(0xFFFF00FF) // Aura
                1 -> Color(0xFF00FF88) // Kai
                else -> Color(0xFFFFD700) // User
            }

            // Connection to center
            drawLine(
                color = color.copy(alpha = 0.4f),
                start = Offset(cx, cy),
                end = Offset(x, y),
                strokeWidth = 3f
            )

            // Node Square (Sharp corners)
            drawRect(
                color = color,
                topLeft = Offset(x - 12f, y - 12f),
                size = androidx.compose.ui.geometry.Size(24f, 24f)
            )
            
            // Node Glow
            drawRect(
                color = color.copy(alpha = 0.2f),
                topLeft = Offset(x - 24f * pulse, y - 24f * pulse),
                size = androidx.compose.ui.geometry.Size(48f * pulse, 48f * pulse)
            )
        }

        // Draw connecting triangle between nodes
        for (i in 0 until nodes) {
            val start = nodePoints[i]
            val end = nodePoints[(i + 1) % nodes]
            drawLine(
                color = Color(0xFFBB86FC).copy(alpha = 0.3f),
                start = start,
                end = end,
                strokeWidth = 2f
            )
        }
    }
}
