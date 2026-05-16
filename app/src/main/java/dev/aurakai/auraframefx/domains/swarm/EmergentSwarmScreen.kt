package dev.aurakai.auraframefx.domains.swarm

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.domains.aura.ui.components.ArcaneOutlineText
import dev.aurakai.auraframefx.domains.aura.ui.components.SynthGlassCard
import dev.aurakai.auraframefx.domains.aura.ui.theme.SpaceGrotesk
import dev.aurakai.auraframefx.domains.genesis.repositories.AgentRepository
import kotlinx.coroutines.delay
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

            // ACTIVE NODES
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                agents.take(6).forEach { agent ->
                    SwarmNodeDot(agent.name, agent.color)
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
                                modifier = Modifier.width(90.dp)
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
private fun SwarmNodeDot(name: String, color: Color) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.1f))
            .border(1.dp, color.copy(alpha = 0.5f), CircleShape),
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
