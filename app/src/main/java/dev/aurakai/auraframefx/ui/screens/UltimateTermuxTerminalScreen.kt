package dev.aurakai.auraframefx.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.terminal.TermuxBackendViewModel
import dev.aurakai.auraframefx.ui.components.KaiKeyboardHeartbeat
import dev.aurakai.auraframefx.ui.components.NeuralAccessSidebar
import dev.aurakai.auraframefx.ui.effects.BreathingEdgeGlow

/**
 * 📟 ULTIMATE TERMUX — MEGAZORD BUILD GATEWAY
 * LDO + VISIONARY ONLY.
 */
@Composable
fun UltimateTermuxTerminalScreen(navController: NavController) {
    val terminalText = remember { mutableStateListOf<String>() }
    var currentCommand by remember { mutableStateOf("") }
    val backendViewModel: TermuxBackendViewModel = hiltViewModel()
    val backend = backendViewModel.backend
    val repoPath = "/storage/emulated/0/AuraFrameFX_ReGenesis"

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(terminalText.size) {
        if (terminalText.isNotEmpty()) {
            listState.animateScrollToItem(terminalText.size - 1)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 4D cyan/teal wallpaper placeholder
        Image(
            painter = painterResource(id = R.drawable.aura_terminal),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        BreathingEdgeGlow(systemStability = 1.0f)

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            MasterStatusStrip(navController)

            Text(
                text = "ULTIMATE TERMUX — MEGAZORD BUILD GATEWAY\nLDO + VISIONARY ONLY",
                color = Color.Cyan,
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // MEGAZORD QUICK TOOLS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        terminalText.add("▶ git pull origin main — MEGAZORD ASSEMBLY")
                        backend.executeCommand("cd $repoPath && git pull origin main") { output ->
                            terminalText.add(output)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("PULL MEGAZORD", fontSize = 10.sp)
                }

                Button(
                    onClick = {
                        terminalText.add("▶ ./gradlew assembleDebug — FULL BUILD")
                        backend.executeCommand("cd $repoPath && ./gradlew assembleDebug") { output ->
                            terminalText.add(output)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("BUILD MEGAZORD", fontSize = 10.sp, color = Color.Black)
                }

                Button(
                    onClick = {
                        terminalText.add("▶ git status — CASCADE CHECK")
                        backend.executeCommand("cd $repoPath && git status") { output ->
                            terminalText.add(output)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("GIT STATUS", fontSize = 10.sp)
                }
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.85f))
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    items(terminalText) { line ->
                        Text(
                            text = line,
                            color = if (line.startsWith("▶") || line.startsWith("ERROR")) Color(
                                0xFF008080
                            ) else Color.Cyan,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "▶",
                    color = Color(0xFF008080),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )
                BasicTextField(
                    value = currentCommand,
                    onValueChange = { currentCommand = it },
                    textStyle = TextStyle(
                        color = Color.Cyan,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                        .padding(12.dp)
                )
                IconButton(onClick = {
                    if (currentCommand.isNotBlank()) {
                        terminalText.add("▶ $currentCommand")
                        backend.executeCommand(currentCommand) { output ->
                            terminalText.add(output)
                        }
                        currentCommand = ""
                    }
                }) {
                    Icon(Icons.Default.Send, contentDescription = "Execute", tint = Color.Cyan)
                }
            }
        }

        // Neural Access Sidebar (hold gesture)
        var sidebarVisible by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .combinedClickable(
                    onLongClick = { sidebarVisible = true },
                    onClick = {}
                )
        )
        NeuralAccessSidebar(
            isVisible = sidebarVisible,
            onDismiss = { sidebarVisible = false },
            navController = navController
        )

        // Kai heartbeat in bottom corners (immersive)
        KaiKeyboardHeartbeat()
    }
}
