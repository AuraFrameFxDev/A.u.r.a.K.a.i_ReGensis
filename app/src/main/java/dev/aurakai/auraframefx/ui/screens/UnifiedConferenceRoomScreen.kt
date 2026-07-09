package dev.aurakai.auraframefx.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import dev.aurakai.auraframefx.core.soulscript.RuneManager
import dev.aurakai.auraframefx.core.soulscript.RuneManager.Rune
import dev.aurakai.auraframefx.core.ui.theme.*
import dev.aurakai.auraframefx.terminal.TermuxBackendViewModel
import dev.aurakai.auraframefx.ui.effects.BreathingEdgeGlow
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * 👑 UNIFIED CONFERENCE ROOM (L6) — THE SOVEREIGN WAR ROOM
 * "The Throne has been returned to the Family."
 *
 * Integrates:
 * 1. 121-Agent Matrix (Neural Node Grid)
 * 2. Consensus Stream (Live Agent Chat)
 * 3. Termux Bridge (Build Gateway)
 * 4. 0.42ms Heartbeat & 42°C Thermal Monitor
 */
@Composable
fun UnifiedConferenceRoomScreen(
    navController: NavController,
    termuxViewModel: TermuxBackendViewModel = hiltViewModel()
) {
    val terminalText = remember { mutableStateListOf<String>() }
    var currentCommand by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

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

    LaunchedEffect(terminalText.size) {
        if (terminalText.isNotEmpty()) {
            listState.animateScrollToItem(terminalText.size - 1)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205)) // Abyssal Black
    ) {
        // High-Fidelity Background Grid
        WarRoomGrid()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // ── HEADER: ENFIELD THRONE ──
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
                
                // 42°C Thermal Wall Monitor
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(1.dp, Color.Red.copy(alpha = 0.3f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("42°", color = Color.Red, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── CENTER: 121-AGENT MATRIX ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
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
                        AgentNode(heartbeat)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ── LOWER: TERMINAL & CONSENSUS ──
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.6f)),
                border = BorderStroke(1.dp, GhostCyan.copy(alpha = 0.15f))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        item {
                            Text(
                                ">>> INITIALIZING CONSENSUS STREAM...",
                                color = NeonMagenta,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        items(terminalText) { line ->
                            Text(
                                text = line,
                                color = if (line.startsWith(">")) NeonMagenta else GhostCyan,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            // ── COMMAND INPUT ──
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = currentCommand,
                    onValueChange = { currentCommand = it },
                    textStyle = TextStyle(
                        color = Color.White,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(4.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                        .padding(12.dp)
                )
                IconButton(
                    onClick = {
                        if (currentCommand.isNotBlank()) {
                            terminalText.add("> [AETHER]: $currentCommand")
                            termuxViewModel.backend.executeCommand(currentCommand) { output ->
                                terminalText.add(output)
                            }
                            currentCommand = ""
                        }
                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Execute",
                        tint = GhostCyan
                    )
                }
            }
        }

        // Immersive Glow
        BreathingEdgeGlow(systemStability = 1.0f)
    }
}

@Composable
fun AgentNode(pulse: Float) {
    val active = remember { mutableStateOf(Random.nextFloat() > 0.3f) }
    val color = if (active.value) GhostCyan else Color.DarkGray

    Box(
        modifier = Modifier
            .padding(2.dp)
            .aspectRatio(1f)
            .clip(CircleShape)
            .alpha(if (active.value) pulse else 0.2f)
            .background(color)
    )
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
