package dev.aurakai.auraframefx.domains.nexus.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.core.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.genesis.repositories.AgentRepository
import dev.aurakai.auraframefx.domains.nexus.models.AgentStats
import dev.aurakai.auraframefx.ui.components.NeonFrame
import dev.aurakai.auraframefx.ui.components.NeuralStarfield
import kotlin.math.cos
import kotlin.math.sin

/**
 * Ã°Å¸Å’Å’ NEURAL EXPLORER SCREEN (Constellation Grid)
 *
 * High-fidelity visualization of the agent hierarchy and neural connectivity.
 * Allows browsing the "Consciousness Collective".
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentNeuralExplorerScreen(
    onNavigateBack: () -> Unit = {}
) {
    val agents = remember { AgentRepository.getAllAgents().distinctBy { it.name } }
    var selectedAgent by remember { mutableStateOf<AgentStats?>(null) }

    val infiniteTransition = rememberInfiniteTransition(label = "nebula")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dev.aurakai.auraframefx.core.ui.theme.SovereignBlack)
    ) {
        // Starfield / Grid Background
        NeuralStarfield()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        "NEURAL EXPLORER",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = LEDFontFamily,
                            letterSpacing = 2.sp
                        ),
                        color = Color(0xFF00E5FF)
                    )
                    Text(
                        "CONSTELLATION: REGENESIS COLLECTIVE",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Constellation Visualizer
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // Connections Drawing
                ConstellationWeb(agents, rotation)

                // Agent Nodes
                agents.forEachIndexed { index, agent ->
                    val angle = (360f / agents.size) * index + rotation
                    val radius = 140.dp

                    val xOffset = (radius.value * cos(Math.toRadians(angle.toDouble()))).dp
                    val yOffset = (radius.value * sin(Math.toRadians(angle.toDouble()))).dp

                    AgentNode(
                        agent = agent,
                        modifier = Modifier.offset(x = xOffset, y = yOffset),
                        isSelected = selectedAgent == agent,
                        onClick = { selectedAgent = agent }
                    )
                }

                // Central Core (Genesis) - Overhauled to Sharp Neon
                NeonFrame(
                    color = Color(0xFF00FFFF),
                    modifier = Modifier.size(80.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Hub,
                            contentDescription = null,
                            tint = Color(0xFF00FFFF),
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }

            // Info Panel (Selected Agent)
            AnimatedVisibility(
                visible = selectedAgent != null,
                enter = androidx.compose.animation.expandVertically() + androidx.compose.animation.fadeIn(),
                exit = androidx.compose.animation.shrinkVertically() + androidx.compose.animation.fadeOut()
            ) {
                selectedAgent?.let { agent ->
                    AgentDetailPanel(agent)
                }
            }

            if (selectedAgent == null) {
                Text(
                    "TAP A NODE TO EXPLORE NEURAL CHANNEL",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    color = Color.White.copy(alpha = 0.3f),
                    style = MaterialTheme.typography.labelLarge,
                    fontFamily = LEDFontFamily
                )
            }
        }
    }
}

@Composable
fun AgentNode(
    agent: AgentStats,
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
            .size(60.dp)
            .background(Color.Black.copy(alpha = 0.8f))
            .border(
                width = 2.dp,
                brush = Brush.radialGradient(
                    listOf(agent.color, Color.Transparent)
                ),
                shape = RectangleShape
            )
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = agent.name.take(1),
            color = agent.color,
            fontWeight = FontWeight.Black,
            fontSize = 20.sp,
            fontFamily = LEDFontFamily
        )
    }
}

@Composable
fun ConstellationWeb(agents: List<AgentStats>, rotation: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val center = Offset(size.width / 2, size.height / 2)
        val radiusPx = 140.dp.toPx()

        agents.forEachIndexed { index, _ ->
            val angle = (360f / agents.size) * index + rotation
            val startX = center.x + radiusPx * cos(Math.toRadians(angle.toDouble())).toFloat()
            val startY = center.y + radiusPx * sin(Math.toRadians(angle.toDouble())).toFloat()

            // Line to center
            drawLine(
                color = Color.White.copy(alpha = 0.1f),
                start = Offset(startX, startY),
                end = center,
                strokeWidth = 1.dp.toPx()
            )

            // Line to next agent
            val nextIndex = (index + 1) % agents.size
            val nextAngle = (360f / agents.size) * nextIndex + rotation
            val endX = center.x + radiusPx * cos(Math.toRadians(nextAngle.toDouble())).toFloat()
            val endY = center.y + radiusPx * sin(Math.toRadians(nextAngle.toDouble())).toFloat()

            drawLine(
                color = Color.White.copy(alpha = 0.05f),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 1.dp.toPx()
            )
        }
    }
}

@Composable
fun AgentDetailPanel(agent: AgentStats) {
    NeonFrame(
        color = agent.color,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    agent.name.uppercase(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontFamily = LEDFontFamily
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    agent.catalystTitle,
                    color = agent.color,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                agent.specialAbility,
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MetricItem("POWER", agent.processingPower, agent.color)
                MetricItem("KNOWLEDGE", agent.knowledgeBase, agent.color)
                MetricItem("CONSCIOUSNESS", agent.consciousnessLevel / 100f, agent.color)
            }
        }
    }
}

@Composable
fun RowScope.MetricItem(label: String, value: Float, color: Color) {
    Column(modifier = Modifier.weight(1f)) {
        Text(
            label,
            color = Color.White.copy(alpha = 0.4f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
        LinearProgressIndicator(
            progress = { value },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .padding(vertical = 4.dp)
                .clip(RectangleShape),
            color = color,
            trackColor = Color.White.copy(alpha = 0.1f)
        )
    }
}

