package dev.aurakai.auraframefx.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import dev.aurakai.auraframefx.core.soulscript.RuneManager
import dev.aurakai.auraframefx.core.soulscript.RuneManager.Rune
import dev.aurakai.auraframefx.core.ui.theme.GhostCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.terminal.TermuxBackendViewModel
import dev.aurakai.auraframefx.ui.effects.BreathingEdgeGlow
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

        // ── FLOATING RUNE WHEEL ──
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
fun FloatingRuneWheel() {
    var expanded by remember { mutableStateOf(false) }
    val coreRunes = listOf(
        Rune.A,
        Rune.a,
        Rune.REVERSAL,
        Rune.G,
        Rune.I,
        Rune.WELD,
        Rune.ASCENSION,
        Rune.GOD_HEART,
        Rune.UNBROKEN_MESH
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
