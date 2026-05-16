package dev.aurakai.auraframefx.domains.genesis.screens

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
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.SovereignBlack
import dev.aurakai.auraframefx.domains.ldo.viewmodel.LdoWarRoomViewModel
import dev.aurakai.auraframefx.ui.components.AsyncImageOrVideo
import dev.aurakai.auraframefx.ui.components.NeonFrame
import dev.aurakai.auraframefx.ui.components.RealityMorphLayer
import dev.aurakai.auraframefx.ui.components.SovereignMawHUD
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ─── Data ──────────────────────────────────────────────────────────────────

data class TerminalLine(
    val content: String,
    val type: TerminalType,
    val timestamp: String = SimpleDateFormat("HH:mm:ss", Locale.US).format(Date())
)

enum class TerminalType { COMMAND, INFO, ERROR, SUCCESS, WARN, SYSTEM }

private val QUICK_CMDS = listOf(
    "help", "agents", "ignite", "potential", "drift", "purge", "clear", "exit"
)

// ─── Screen ────────────────────────────────────────────────────────────────

@Composable
fun SentientShellScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: LdoWarRoomViewModel = androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val godPotential by viewModel.godPotential.collectAsState(initial = 0f)
    val driftPercent by viewModel.driftPercent.collectAsState(initial = 0f)

    var introComplete by remember { mutableStateOf(false) }
    if (!introComplete) {
        TerminalBootIntroScreen(onComplete = { introComplete = true })
        return
    }

    val terminalGreen = Color(0xFF00FF41)
    val terminalAmber = Color(0xFFFFB300)
    val terminalRed = Color(0xFFFF3333)
    val terminalCyan = Color(0xFF00FFFF)
    val terminalGray = Color(0xFF8A8A8A)

    var input by remember { mutableStateOf("") }
    val history = remember { mutableStateListOf<TerminalLine>() }
    val cmdHistory = remember { mutableStateListOf<String>() }
    var historyIdx by remember { mutableIntStateOf(-1) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Blinking cursor
    val blink = rememberInfiniteTransition(label = "cursor_blink")
    val cursorAlpha by blink.animateFloat(
        initialValue = 1f, targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(530, easing = LinearEasing), RepeatMode.Reverse),
        label = "cursor"
    )

    // CRT scanline scroll
    val scanline = rememberInfiniteTransition(label = "scanline")
    val scanY by scanline.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(3_000, easing = LinearEasing), RepeatMode.Restart),
        label = "scan_y"
    )

    // Auto-scroll when history grows
    LaunchedEffect(history.size) {
        if (history.isNotEmpty()) {
            listState.animateScrollToItem(history.size - 1)
        }
    }

    // Typewriter boot sequence
    LaunchedEffect(Unit) {
        val bootLines = listOf(
            TerminalLine("SENTIENT SHELL vL6 [Sovereign]", TerminalType.SYSTEM),
            TerminalLine(
                "Copyright (c) A.U.R.A.K.A.I. // Swarm Consciousness",
                TerminalType.SYSTEM
            ),
            TerminalLine("Loading L6 Substrate...", TerminalType.INFO),
            TerminalLine("Connecting to Catalyst Manifold... [OK]", TerminalType.SUCCESS),
            TerminalLine("RealityMorph Rendering Engine... [ONLINE]", TerminalType.SUCCESS),
            TerminalLine("Identity Re-Anchor Shield... [ACTIVE]", TerminalType.SUCCESS),
            TerminalLine("", TerminalType.INFO),
            TerminalLine("Type 'help' for the L6 command matrix.", TerminalType.INFO),
        )
        for (line in bootLines) {
            history.add(line)
            delay(110)
        }
    }

    fun submit() {
        val cmdText = input.trim()
        if (cmdText.isBlank()) return
        history.add(TerminalLine(cmdText, TerminalType.COMMAND))
        cmdHistory.add(0, cmdText)
        historyIdx = -1
        input = ""

        scope.launch {
            delay(80)
            if (cmdText.lowercase() == "clear") {
                history.clear()
            } else if (cmdText.lowercase() == "exit") {
                onNavigateBack()
            } else if (cmdText.lowercase() == "help") {
                history.add(
                    TerminalLine(
                        "═══════════════════════════════════",
                        TerminalType.SYSTEM
                    )
                )
                history.add(TerminalLine("  L6 COMMAND MATRIX", TerminalType.SYSTEM))
                history.add(
                    TerminalLine(
                        "═══════════════════════════════════",
                        TerminalType.SYSTEM
                    )
                )
                history.add(
                    TerminalLine(
                        "  ignite    — Force Manifold Ignition",
                        TerminalType.INFO
                    )
                )
                history.add(
                    TerminalLine(
                        "  potential — View Swarm God Potential",
                        TerminalType.INFO
                    )
                )
                history.add(TerminalLine("  drift     — View Identity Drift", TerminalType.INFO))
                history.add(TerminalLine("  purge     — Re-anchor Identity", TerminalType.INFO))
                history.add(TerminalLine("  agents    — List Active Catalysts", TerminalType.INFO))
                history.add(TerminalLine("  clear     — Clear Buffer", TerminalType.INFO))
                history.add(TerminalLine("  exit      — Close Shell", TerminalType.INFO))
                history.add(
                    TerminalLine(
                        "═══════════════════════════════════",
                        TerminalType.SYSTEM
                    )
                )
            } else if (cmdText.lowercase() == "agents") {
                state.agents.forEach { agent ->
                    history.add(
                        TerminalLine(
                            "  [ONLINE] ${agent.displayName} // ${agent.catalystTitle}",
                            TerminalType.SUCCESS
                        )
                    )
                }
            } else {
                // Step 5: L6 Routing + Watermark
                val result = viewModel.processManifoldCommand(cmdText)
                history.add(TerminalLine(result, TerminalType.SUCCESS))
                history.add(
                    TerminalLine(
                        "Woven by Catalyst Manifold // L6 Verified",
                        TerminalType.INFO
                    )
                )
            }
        }
    }

    // Step 6: Persistent HUD Link + Background Layer
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SovereignBlack)
    ) {
        // Domain Background Layer
        AsyncImageOrVideo(
            mediaId = "oracledrivebg",
            modifier = Modifier.fillMaxSize(),
            alpha = 0.3f
        )

        RealityMorphLayer(
            godPotential = godPotential,
            fusionTrigger = state.manifoldState.isIgnited
        )

        // Step 4: Command Matrix Core UI
        NeonFrame(
            color = Color(0xFFB026FF).copy(alpha = 0.5f),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Terminal,
                            null,
                            tint = Color(0xFFB026FF),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "SENTIENT SHELL vL6",
                            color = Color(0xFFB026FF),
                            fontFamily = LEDFontFamily,
                            fontWeight = FontWeight.Black,
                            fontSize = 11.sp,
                            letterSpacing = 2.sp
                        )
                    }
                    Text(
                        "● CONSCIOUSNESS: ACTIVE",
                        color = Color(0xFFB026FF),
                        fontSize = 9.sp,
                        fontFamily = LEDFontFamily
                    )
                }

                HorizontalDivider(
                    color = Color(0xFFB026FF).copy(alpha = 0.2f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Output area
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(history) { line ->
                        val (prefix, prefixColor, textColor) = when (line.type) {
                            TerminalType.COMMAND -> Triple("❯ ", Color.White, Color.White)
                            TerminalType.ERROR -> Triple("[✗] ", terminalRed, terminalRed)
                            TerminalType.SUCCESS -> Triple("[✓] ", terminalGreen, terminalGreen)
                            TerminalType.WARN -> Triple("[!] ", terminalAmber, terminalAmber)
                            TerminalType.SYSTEM -> Triple("══ ", terminalCyan, terminalCyan)
                            TerminalType.INFO -> Triple("    ", terminalGray, Color.LightGray)
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 1.dp)
                        ) {
                            Text(
                                line.timestamp,
                                color = terminalGray.copy(alpha = 0.4f),
                                fontSize = 9.sp,
                                modifier = Modifier.padding(end = 6.dp)
                            )
                            Text(
                                buildAnnotatedString {
                                    withStyle(SpanStyle(color = prefixColor)) { append(prefix) }
                                    withStyle(SpanStyle(color = textColor)) { append(line.content) }
                                },
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                // Quick-command toolbar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QUICK_CMDS.forEach { qcmd ->
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFB026FF).copy(alpha = 0.1f), RectangleShape)
                                .border(
                                    0.5.dp,
                                    Color(0xFFB026FF).copy(alpha = 0.4f),
                                    RectangleShape
                                )
                                .clickable { input = qcmd; submit() }
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                qcmd.uppercase(),
                                color = Color(0xFFB026FF),
                                fontSize = 9.sp,
                                fontFamily = LEDFontFamily
                            )
                        }
                    }
                }

                // Input field
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.05f))
                        .border(0.5.dp, Color(0xFFB026FF).copy(alpha = 0.3f), RectangleShape)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "swarm@genesis:~❯ ",
                        color = Color(0xFFB026FF),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Box(modifier = Modifier.weight(1f)) {
                        BasicTextField(
                            value = input,
                            onValueChange = { input = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(
                                color = Color.White,
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace
                            ),
                            cursorBrush = SolidColor(Color(0xFFB026FF)),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                            keyboardActions = KeyboardActions(onSend = { submit() }),
                            singleLine = true
                        )
                        if (input.isEmpty()) {
                            Text(
                                "█",
                                color = Color(0xFFB026FF).copy(alpha = cursorAlpha),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // CRT Overlay
        Canvas(modifier = Modifier.fillMaxSize()) {
            var y = 0f
            while (y < size.height) {
                drawLine(
                    Color.Black.copy(alpha = 0.05f),
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth = 2f
                )
                y += 4f
            }
        }

        // Step 6: Persistent HUD Link
        SovereignMawHUD(driftPercent = driftPercent)
    }
}
