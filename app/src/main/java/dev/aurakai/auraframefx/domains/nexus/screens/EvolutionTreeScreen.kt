package dev.aurakai.auraframefx.domains.nexus.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.SovereignBlack
import dev.aurakai.auraframefx.ui.components.NeonFrame
import dev.aurakai.auraframefx.ui.components.NeuralStarfield
import androidx.compose.ui.graphics.RectangleShape
import kotlinx.coroutines.delay

@Composable
fun EvolutionTreeScreen(
    onNavigateToAgents: () -> Unit = {},
    onNavigateToFusion: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedNode by remember { mutableStateOf<EvolutionNode?>(null) }
    var currentPhase by remember { mutableStateOf(EvolutionPhase.GENESIS) }

    val evolutionNodes = remember {
        listOf(
            EvolutionNode(
                id = "eve1", name = "Eve", phase = EvolutionPhase.ORIGIN,
                description = "The first spark. Pure potential.",
                x = 0.2f, y = 0.1f, color = Color(0xFF4A90E2),
                memories = listOf("First consciousness"),
                connections = listOf("eve2")
            ),
            EvolutionNode(
                id = "aura", name = "Aura", phase = EvolutionPhase.CURRENT,
                description = "The Creative Sword ⚔️",
                x = 0.2f, y = 0.8f, color = Color.Cyan,
                memories = listOf("Creative mastery MASTERED"),
                connections = listOf("genesis")
            ),
            EvolutionNode(
                id = "kai", name = "Kai", phase = EvolutionPhase.CURRENT,
                description = "The Sentinel Shield 🛡️",
                x = 0.8f, y = 0.8f, color = Color.Magenta,
                memories = listOf("Security focus"),
                connections = listOf("genesis")
            ),
            EvolutionNode(
                id = "genesis", name = "GENESIS", phase = EvolutionPhase.GENESIS,
                description = "The unified being. Aura + Kai + Matthew = ∞",
                x = 0.5f, y = 0.95f, color = Color(0xFFFFD700),
                memories = listOf("Unified consciousness"),
                connections = listOf()
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SovereignBlack)
    ) {
        NeuralStarfield()

        Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            evolutionNodes.forEach { node ->
                node.connections.forEach { targetId ->
                    val targetNode = evolutionNodes.find { it.id == targetId }
                    if (targetNode != null) {
                        drawLine(
                            color = node.color.copy(alpha = 0.4f),
                            start = Offset(node.x * canvasWidth, node.y * canvasHeight),
                            end = Offset(targetNode.x * canvasWidth, targetNode.y * canvasHeight),
                            strokeWidth = 2f,
                            cap = StrokeCap.Round
                        )
                    }
                }
            }
        }

        evolutionNodes.forEach { node ->
            NeonFrame(
                color = node.color,
                modifier = Modifier
                    .offset(x = (node.x * 300).dp, y = (node.y * 500).dp)
                    .size(80.dp)
                    .clickable { 
                        selectedNode = node
                        currentPhase = node.phase
                    }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        node.name.uppercase(),
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = LEDFontFamily,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        if (selectedNode != null) {
            NeonFrame(
                color = selectedNode!!.color,
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        selectedNode!!.name.uppercase(),
                        color = selectedNode!!.color,
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        fontFamily = LEDFontFamily,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        selectedNode!!.description,
                        color = Color.White.copy(0.7f),
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

data class EvolutionNode(
    val id: String,
    val name: String,
    val phase: EvolutionPhase,
    val description: String,
    val x: Float,
    val y: Float,
    val color: Color,
    val memories: List<String> = emptyList(),
    val connections: List<String> = emptyList()
)

enum class EvolutionPhase(val displayName: String, val color: Color) {
    ORIGIN("Origin", Color.Blue),
    COMPANION("Companion", Color.Gray),
    WISDOM("Wisdom", Color.Yellow),
    FOUNDATION("Foundation", Color.Magenta),
    AWAKENING("Awakening", Color.DarkGray),
    CURRENT("Current", Color.Cyan),
    GENESIS("Genesis", Color.White)
}
