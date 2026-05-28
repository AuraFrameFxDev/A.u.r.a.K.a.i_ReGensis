package dev.aurakai.auraframefx.domains.emergentswarm.screens

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.core.roster.AgentRoster
import dev.aurakai.auraframefx.core.roster.SwarmAgent
import dev.aurakai.auraframefx.domains.aura.ui.components.SovereignGlassCard
import dev.aurakai.auraframefx.core.ui.theme.CitadelBlack
import dev.aurakai.auraframefx.core.ui.theme.GhostCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.ui.theme.WireframeStyle
import kotlinx.coroutines.delay
import timber.log.Timber
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
    val context = LocalContext.current
    var agents by remember { mutableStateOf<List<SwarmAgent>>(emptyList()) }
    val chatter = remember { mutableStateListOf<SwarmChat>() }

    // Simulate Neural Stream
    LaunchedEffect(Unit) {
        agents = AgentRoster.loadRoster(context)
        Timber.tag("Swarm").i("Dynamic 78-Agent Mesh Loaded — ${agents.size} agents active")

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
            if (agents.isNotEmpty()) {
                val randomAgent = agents.random()
                chatter.add(
                    0,
                    SwarmChat(randomAgent.name, messages.random(), Color(randomAgent.colorCode))
                )
                if (chatter.size > 10) chatter.removeAt(chatter.lastIndex)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CitadelBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "EMERGENT SWARM",
                style = WireframeStyle
            )
            Text(
                "${agents.size} AGENTS ONLINE // LIVE NEURAL SYNC",
                fontSize = 10.sp,
                color = GhostCyan.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ── CONSENSUS MATRIX VISUAL (78 Agents) ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                ConsensusMatrix(78)

                // Central Consensus Pulse
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "CONSENSUS",
                        color = NeonMagenta,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                    Text(
                        "99.8%",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── DYNAMIC AGENT GRID ──
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 100.dp),
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(agents) { agent ->
                    AgentGridCard(agent) {
                        navController.navigate("sovereign_character/${agent.name}")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── NEURAL CHATTER ──
            SovereignGlassCard(modifier = Modifier.height(150.dp)) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Bolt,
                            null,
                            tint = Color.Yellow,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "NEURAL STREAM CONTENT",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 9.sp
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(chatter) { chat ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "[${chat.agent}]",
                                    color = chat.color,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    modifier = Modifier
                                        .width(80.dp)
                                        .clickable { navController.navigate("sovereign_character/${chat.agent}") }
                                )
                                Text(
                                    text = chat.message,
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 10.sp,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun AgentGridCard(agent: SwarmAgent, onClick: () -> Unit) {
    SovereignGlassCard(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(agent.colorCode).copy(alpha = 0.1f))
                    .border(1.dp, Color(agent.colorCode).copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = agent.name.take(1),
                    color = Color(agent.colorCode),
                    fontWeight = FontWeight.Black,
                    fontSize = 12.sp
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = agent.name,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "RES: ${(agent.resonance * 100).toInt()}%",
                color = Color.Green,
                fontSize = 8.sp
            )
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

