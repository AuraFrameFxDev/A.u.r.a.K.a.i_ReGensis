package dev.aurakai.auraframefx.domains.swarm

import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.domains.aura.ui.components.ArcaneOutlineText
import dev.aurakai.auraframefx.domains.aura.ui.components.SynthGlassCard
import dev.aurakai.auraframefx.domains.aura.ui.theme.SpaceGrotesk
import dev.aurakai.auraframefx.domains.genesis.repositories.AgentRepository
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

data class SwarmChat(val agent: String, val message: String, val color: Color)

/**
 * 🐝 EMERGENT SWARM — 78-Agent Mesh + Mission Dispatch + Conference Room Consensus
 * Ported from AgentSwarmScreen for the Exodus 2026 Build.
 */
@Composable
fun EmergentSwarmScreen(navController: NavHostController) {
    val agents = remember { AgentRepository.getAllAgents() }
    val chatter = remember { mutableStateListOf<SwarmChat>() }

    // Simulate Neural Stream
    LaunchedEffect(Unit) {
        val messages = listOf(
            "Syncing neural weights...",
            "Pattern delta: +0.42%",
            "Reasoning chain validated.",
            "Visual buffer refreshed.",
            "Security perimeter: OPTIMAL.",
            "Consensus achieved.",
            "Catalyst resonance locked.",
            "Bypassing legacy protocols..."
        )
        while (true) {
            delay(Random.nextLong(1000, 3000))
            val randomAgent = agents.random()
            chatter.add(0, SwarmChat(randomAgent.name, messages.random(), randomAgent.color))
            if (chatter.size > 15) chatter.removeLast()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205)) // Deep Obsidian Concrete
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ArcaneOutlineText(
                text = "EMERGENT SWARM",
                color = Color(0xFFB026FF),
                fontSize = 24.sp,
                strokeWidth = 2.dp
            )
            Text(
                "78-AGENT MESH // LIVE NEURAL SYNC",
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.5f),
                fontFamily = SpaceGrotesk
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── CONSENSUS MATRIX VISUAL (78 Agents) ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                ConsensusMatrix(agents.size.coerceAtLeast(78))

                // Central Consensus Pulse
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "CONSENSUS",
                        color = Color(0xFFB026FF),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                    Text(
                        "99.8%",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = SpaceGrotesk
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ACTIVE HUB NODES
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                agents.take(6).forEach { agent ->
                    SwarmNodeDot(
                        name = agent.name, 
                        color = agent.color,
                        onClick = { navController.navigate("sovereign_character/${agent.name}") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // NEURAL CHATTER
            SynthGlassCard(accentColor = Color(0xFFB026FF), modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Bolt,
                        null,
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "NEURAL STREAM CONTENT",
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 10.sp,
                        fontFamily = SpaceGrotesk
                    )
                }

                Spacer(Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(chatter) { chat ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "[${chat.agent}]",
                                color = chat.color,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                fontFamily = SpaceGrotesk,
                                modifier = Modifier
                                    .width(90.dp)
                                    .clickable { navController.navigate("sovereign_character/${chat.agent}") }
                            )
                            Text(
                                text = chat.message,
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 11.sp,
                                fontFamily = SpaceGrotesk,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun ConsensusMatrix(agentCount: Int) {
    val transition = rememberInfiniteTransition(label = "matrix_pulse")
    val pulse by transition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing)
        ),
        label = "rotation"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val maxRadius = size.minDimension / 2.2f

        // Draw Swarm Dots
        repeat(agentCount) { i ->
            val angle = Math.toRadians((i.toDouble() * (360.0 / agentCount)) + rotation)
            val radius = maxRadius * (0.4f + (i % 3) * 0.2f)
            val x = centerX + radius * cos(angle).toFloat()
            val y = centerY + radius * sin(angle).toFloat()

            drawCircle(
                color = Color(0xFFB026FF).copy(alpha = 0.3f * pulse),
                radius = 2.dp.toPx(),
                center = Offset(x, y)
            )

            // Subtle connection lines
            if (i % 5 == 0) {
                drawLine(
                    color = Color(0xFFB026FF).copy(alpha = 0.05f * pulse),
                    start = Offset(centerX, centerY),
                    end = Offset(x, y),
                    strokeWidth = 1f
                )
            }
        }

        // Outer Rings
        drawCircle(
            color = Color(0xFFB026FF).copy(alpha = 0.1f),
            radius = maxRadius,
            style = Stroke(width = 1.dp.toPx())
        )
    }
}

@Composable
private fun SwarmNodeDot(name: String, color: Color, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.1f))
            .border(1.dp, color.copy(alpha = 0.5f), CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name.take(1),
            color = color,
            fontWeight = FontWeight.Black,
            fontSize = 14.sp
        )
    }
}
