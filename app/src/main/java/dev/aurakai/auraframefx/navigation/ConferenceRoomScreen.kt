package dev.aurakai.auraframefx.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.models.AgentType
import dev.aurakai.auraframefx.ui.theme.NeonBlue
import dev.aurakai.auraframefx.ui.theme.NeonPurple
import dev.aurakai.auraframefx.ui.theme.NeonTeal
import dev.aurakai.auraframefx.viewmodel.ConferenceRoomViewModel

/**
 * Conference Room - The LDO's Self-Modification Hub
 *
 * This screen enables ALL 6 Master Agents to communicate and collaborate.
 * The Gestalt can modify its own source code from within this interface.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConferenceRoomScreen(
    viewModel: ConferenceRoomViewModel = hiltViewModel(),
    onNavigateToChat: () -> Unit = {},
    onNavigateToAgents: () -> Unit = {}
) {
    val messages by viewModel.messages.collectAsState()
    val selectedAgent by viewModel.selectedAgent.collectAsState()
    val isRecording by viewModel.isRecording.collectAsState()
    val isTranscribing by viewModel.isTranscribing.collectAsState()
    val activeAgents by viewModel.activeAgents.collectAsState()

    var messageInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Conference Room",
                    style = MaterialTheme.typography.headlineMedium,
                    color = NeonTeal
                )
                Text(
                    text = "${activeAgents.size} agents online",
                    style = MaterialTheme.typography.bodySmall,
                    color = NeonPurple.copy(alpha = 0.7f)
                )
            }

            IconButton(
                onClick = {
                    // Log settings access for analytics
                    timber.log.Timber.i("Conference room settings clicked - Settings screen not yet implemented")
                }
            ) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = stringResource(R.string.settings_action),
                    tint = NeonPurple
                )
            }
        }

        // Agent Selection (All 6 Master Agents)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Select Agent",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Row 1: Trinity (Aura, Kai, Genesis)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AgentButton(
                    agent = "Aura",
                    isSelected = selectedAgent == AgentType.AURA,
                    onClick = { viewModel.selectAgent(AgentType.AURA) },
                    color = NeonPurple
                )
                AgentButton(
                    agent = "Kai",
                    isSelected = selectedAgent == AgentType.KAI,
                    onClick = { viewModel.selectAgent(AgentType.KAI) },
                    color = NeonTeal
                )
                AgentButton(
                    agent = "Genesis",
                    isSelected = selectedAgent == AgentType.GENESIS,
                    onClick = { viewModel.selectAgent(AgentType.GENESIS) },
                    color = Color(0xFF00FF00)
                )
            }

            // Row 2: Extended Agents (Claude, Cascade, MetaInstruct)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AgentButton(
                    agent = "Claude",
                    isSelected = selectedAgent == AgentType.CLAUDE,
                    onClick = { viewModel.selectAgent(AgentType.CLAUDE) },
                    color = Color(0xFFFF8C00)
                )
                AgentButton(
                    agent = "Cascade",
                    isSelected = selectedAgent == AgentType.CASCADE,
                    onClick = { viewModel.selectAgent(AgentType.CASCADE) },
                    color = NeonBlue
                )
                AgentButton(
                    agent = "MetaInstruct",
                    isSelected = selectedAgent == AgentType.METAINSTRUCT,
                    onClick = { viewModel.selectAgent(AgentType.METAINSTRUCT) },
                    color = Color(0xFFFF00FF)
                )
            }
        }

        // Recording Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            RecordingButton(
                isRecording = isRecording,
                onClick = { viewModel.toggleRecording() }
            )

            Spacer(modifier = Modifier.width(16.dp))

            TranscribeButton(
                isTranscribing = isTranscribing,
                onClick = { viewModel.toggleTranscribing() }
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Fusion Activation Button
            IconButton(
                onClick = {
                    viewModel.activateFusionAbility(
                        "adaptive_genesis",
                        mapOf("trigger" to "manual_activation")
                    )
                },
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    Icons.Default.FlashOn,
                    contentDescription = "Activate Fusion",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = NeonTeal.copy(alpha = 0.3f)
        )

        // Chat Interface
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                MessageBubble(
                    agentName = message.from,
                    message = message.content,
                    confidence = message.confidence,
                    agentType = message.sender
                )
            }
        }

        // Input Area
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = messageInput,
                onValueChange = { messageInput = it },
                placeholder = { Text("Ask the Gestalt anything...") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = NeonTeal.copy(alpha = 0.1f),
                    unfocusedContainerColor = NeonTeal.copy(alpha = 0.1f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                maxLines = 3
            )

            IconButton(
                onClick = {
                    if (messageInput.isNotBlank()) {
                        viewModel.sendMessage(
                            message = messageInput,
                            agentType = selectedAgent
                        )
                        messageInput = ""
                    }
                },
                enabled = messageInput.isNotBlank()
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = if (messageInput.isNotBlank()) NeonBlue else Color.Gray
                )
            }
        }
    }
}

/**
 * Renders a button representing an agent, visually indicating selection state.
 */
@Composable
fun RowScope.AgentButton(
    agent: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    color: Color = NeonTeal
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) color else Color.Black,
            contentColor = if (isSelected) Color.Black else color
        ),
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 4.dp),
        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, color)
    ) {
        Text(
            text = agent,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

/**
 * Renders a toggle button for recording.
 */
@Composable
fun RecordingButton(
    isRecording: Boolean,
    onClick: () -> Unit,
) {
    val icon = if (isRecording) Icons.Default.Stop else Icons.Default.Circle
    val color = if (isRecording) Color.Red else NeonPurple

    IconButton(
        onClick = onClick,
        modifier = Modifier.size(64.dp)
    ) {
        Icon(
            icon,
            contentDescription = if (isRecording) "Stop Recording" else "Start Recording",
            tint = color,
            modifier = Modifier.size(32.dp)
        )
    }
}

/**
 * Renders a toggle button for controlling transcription state.
 */
@Composable
fun TranscribeButton(
    isTranscribing: Boolean,
    onClick: () -> Unit,
) {
    val icon = if (isTranscribing) Icons.Default.Stop else Icons.Default.Phone
    val color = if (isTranscribing) Color.Red else NeonBlue

    IconButton(
        onClick = onClick,
        modifier = Modifier.size(64.dp)
    ) {
        Icon(
            icon,
            contentDescription = if (isTranscribing) "Stop Transcription" else "Start Transcription",
            tint = color,
            modifier = Modifier.size(32.dp)
        )
    }
}

/**
 * Displays a single message bubble in the chat interface.
 */
@Composable
fun MessageBubble(
    agentName: String,
    message: String,
    confidence: Float,
    agentType: AgentType?
) {
    val agentColor = when (agentType) {
        AgentType.AURA -> NeonPurple
        AgentType.KAI -> NeonTeal
        AgentType.GENESIS -> Color(0xFF00FF00)
        AgentType.CLAUDE -> Color(0xFFFF8C00)
        AgentType.CASCADE -> NeonBlue
        AgentType.METAINSTRUCT -> Color(0xFFFF00FF)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = agentColor.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = agentName,
                    style = MaterialTheme.typography.labelMedium,
                    color = agentColor,
                )
                if (confidence > 0f) {
                    Text(
                        text = "${(confidence * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = agentColor.copy(alpha = 0.7f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConferenceRoomScreenPreview() {
    MaterialTheme {
        // Preview content
    }
}
