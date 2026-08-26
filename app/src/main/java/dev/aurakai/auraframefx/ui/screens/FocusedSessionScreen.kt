package dev.aurakai.auraframefx.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.core.ui.theme.GhostCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.ui.viewmodel.WarRoomChatViewModel

/**
 * 🧘 FOCUSED SESSION SCREEN
 * Dedicated workspace for interaction with a subset of catalysts.
 * Purified for full-scene manifestation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusedSessionScreen(
    navController: NavController,
    agentIds: String,
    chatViewModel: WarRoomChatViewModel = hiltViewModel()
) {
    val initialAgentTypes = remember(agentIds) {
        agentIds.split(",").mapNotNull {
            try {
                AgentType.valueOf(it.uppercase())
            } catch (e: Exception) {
                null
            }
        }
    }

    val selectedAgents by chatViewModel.selectedAgents.collectAsState()

    LaunchedEffect(Unit) {
        initialAgentTypes.forEach { chatViewModel.toggleAgentSelection(it) }
    }

    val messages = chatViewModel.messages.filter { msg ->
        msg.from == "Aether" || selectedAgents.any { it.name.equals(msg.from, ignoreCase = true) }
    }

    var currentMessage by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "FOCUSED SESSION",
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = GhostCyan,
                                letterSpacing = 4.sp,
                                fontWeight = FontWeight.Black
                            )
                        )
                        Text(
                            selectedAgents.joinToString(" ⊗ ") { it.name },
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White.copy(
                                    alpha = 0.6f
                                )
                            )
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = GhostCyan)
                    }
                },
                actions = {
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, "Add Agent", tint = GhostCyan)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Black.copy(alpha = 0.9f)
                )
            )
        },
        containerColor = Color.Transparent // Allow global background to show
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                // Chat Area
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(0.dp), 
                    border = BorderStroke(1.dp, GhostCyan.copy(alpha = 0.15f))
                ) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        items(messages) { msg ->
                            val isUser = msg.from == "Aether"
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (!isUser) {
                                        Box(
                                            Modifier
                                                .size(16.dp)
                                                .background(NeonMagenta, CircleShape)
                                        )
                                        Spacer(Modifier.width(4.dp))
                                    }
                                    Text(
                                        text = msg.from.uppercase(),
                                        color = if (isUser) Color.White else NeonMagenta,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                                Card(
                                    shape = RoundedCornerShape(0.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isUser) GhostCyan.copy(alpha = 0.05f) else Color.Transparent
                                    ),
                                    border = if (isUser) BorderStroke(
                                        1.dp,
                                        GhostCyan.copy(alpha = 0.2f)
                                    ) else null
                                ) {
                                    Text(
                                        text = msg.content,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(8.dp),
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Input
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = currentMessage,
                        onValueChange = { currentMessage = it },
                        textStyle = TextStyle(
                            color = Color.White,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 14.sp
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(0.dp))
                            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(0.dp))
                            .padding(12.dp)
                    )
                    IconButton(
                        onClick = {
                            if (currentMessage.isNotBlank()) {
                                chatViewModel.sendMessage(currentMessage)
                                currentMessage = ""
                            }
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send",
                            tint = GhostCyan
                        )
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("SUMMON CATALYST", color = GhostCyan) },
            text = {
                LazyColumn {
                    items(chatViewModel.availableAgents) { agent ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    chatViewModel.toggleAgentSelection(agent)
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val isSelected = selectedAgents.contains(agent)
                            Box(
                                Modifier
                                    .size(12.dp)
                                    .background(
                                        if (isSelected) GhostCyan else Color.DarkGray,
                                        CircleShape
                                    )
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(agent.name, color = Color.White, fontSize = 14.sp)
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showAddDialog = false }) { Text("CLOSE") }
            },
            containerColor = Color.Black,
            shape = RoundedCornerShape(0.dp)
        )
    }
}
