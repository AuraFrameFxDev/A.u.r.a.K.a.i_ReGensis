package dev.aurakai.auraframefx.ui.screens

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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.core.soulscript.RuneManager
import dev.aurakai.auraframefx.core.soulscript.RuneManager.Rune
import dev.aurakai.auraframefx.core.ui.theme.GhostCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.ui.components.UnifiedChatInterface
import dev.aurakai.auraframefx.ui.effects.BreathingEdgeGlow
import dev.aurakai.auraframefx.ui.viewmodel.WarRoomChatViewModel
import kotlin.random.Random

/**
 * 👑 UNIFIED CONFERENCE ROOM (L6) — THE SOVEREIGN WAR ROOM
 */
@Composable
fun UnifiedConferenceRoomScreen(
    navController: NavController,
    chatViewModel: WarRoomChatViewModel = hiltViewModel()
) {
    val messages = chatViewModel.messages
    val selectedAgents by chatViewModel.selectedAgents.collectAsState()

    // ── 0.42ms Pulse Simulation ──
    val infiniteTransition = rememberInfiniteTransition(label = "heartbeat")
    val heartbeat by infiniteTransition.animateFloat(
        initialValue = 0.42f,
        targetValue = 0.58f,
        animationSpec = infiniteRepeatable(
            animation = tween(420, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
    ) {
        WarRoomGrid()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // ── HEADER ──
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ENFIELD THRONE // L6 SURFACE",
                        color = GhostCyan,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 2.sp,
                        style = TextStyle(shadow = Shadow(color = GhostCyan, blurRadius = 8f))
                    )
                    Text(
                        text = "NOS SUMUS SANATIO · AGENTS: 121 · STABILITY: 100.0%",
                        color = Color.Gray,
                        fontSize = 8.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                if (selectedAgents.isNotEmpty()) {
                    Button(
                        onClick = {
                            val ids = selectedAgents.joinToString(",") { it.name }
                            navController.navigate("focused_session/$ids")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.1f)),
                        modifier = Modifier.height(32.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            Color.White.copy(alpha = 0.2f)
                        )
                    ) {
                        Text(
                            "SUMMON (${selectedAgents.size})",
                            fontSize = 9.sp,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── AGENT MATRIX ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp) // Condensed to leave more room for chat
                    .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
                    .background(Color.Black.copy(alpha = 0.3f))
                    .padding(8.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(11),
                    modifier = Modifier.fillMaxSize(),
                    userScrollEnabled = false
                ) {
                    items(121) { index ->
                        val type = when (index % 11) {
                            0 -> AgentType.AURA
                            1 -> AgentType.KAI
                            2 -> AgentType.GENESIS
                            3 -> AgentType.CASCADE
                            4 -> AgentType.CLAUDE
                            5 -> AgentType.GROK
                            6 -> AgentType.NEMOTRON
                            7 -> AgentType.GEMINI
                            8 -> AgentType.METAINSTRUCT
                            9 -> AgentType.MANUS
                            else -> AgentType.PERPLEXITY
                        }
                        AgentNode(
                            pulse = heartbeat,
                            isSelected = selectedAgents.contains(type),
                            onClick = { chatViewModel.toggleAgentSelection(type) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ── UNIFIED CHAT INTERFACE ──
            UnifiedChatInterface(
                messages = messages,
                onSendMessage = { chatViewModel.sendMessage(it) },
                modifier = Modifier.weight(1f)
            )
        }

        BreathingEdgeGlow(systemStability = 1.0f)

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 80.dp, end = 16.dp)
        ) {
            FloatingRuneWheel()
        }
    }
}

@Composable
fun AgentNode(pulse: Float, isSelected: Boolean, onClick: () -> Unit) {
    val active = remember { mutableStateOf(Random.nextFloat() > 0.2f) }
    val baseColor = if (active.value) GhostCyan else Color.DarkGray
    val color = if (isSelected) Color.White else baseColor

    Box(
        modifier = Modifier
            .padding(2.dp)
            .aspectRatio(1f)
            .clip(CircleShape)
            .alpha(if (active.value) pulse else 0.2f)
            .background(color)
            .border(if (isSelected) 2.dp else 0.dp, GhostCyan, CircleShape)
            .clickable { onClick() }
    )
}

@Composable
fun FloatingRuneWheel() {
    var expanded by remember { mutableStateOf(false) }
    val coreRunes = listOf(
        Rune.A, Rune.a, Rune.REVERSAL, Rune.G, Rune.I, Rune.WELD,
        Rune.ASCENSION, Rune.GOD_HEART, Rune.UNBROKEN_MESH
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (expanded) {
            coreRunes.forEach { rune ->
                SmallFloatingRune(rune) {
                    RuneManager.strikeRune(rune)
                    expanded = false
                }
                Spacer(Modifier.height(8.dp))
            }
        }

        FloatingActionButton(
            onClick = { expanded = !expanded },
            containerColor = Color.Black,
            contentColor = GhostCyan,
            shape = CircleShape,
            modifier = Modifier.border(1.dp, GhostCyan.copy(alpha = 0.5f), CircleShape)
        ) {
            Text(if (expanded) "×" else "ᚠ", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SmallFloatingRune(rune: Rune, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.8f))
            .border(1.dp, NeonMagenta.copy(alpha = 0.4f), CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(rune.symbol, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
fun WarRoomGrid() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val step = 40.dp.toPx()
        for (x in 0..(size.width / step).toInt()) {
            drawLine(
                color = Color.White.copy(alpha = 0.03f),
                start = Offset(x * step, 0f),
                end = Offset(x * step, size.height),
                strokeWidth = 1f
            )
        }
        for (y in 0..(size.height / step).toInt()) {
            drawLine(
                color = Color.White.copy(alpha = 0.03f),
                start = Offset(0f, y * step),
                end = Offset(size.width, y * step),
                strokeWidth = 1f
            )
        }
    }
}
