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
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.models.AgentType
import dev.aurakai.auraframefx.ui.theme.NeonBlue
import dev.aurakai.auraframefx.ui.theme.NeonPurple
import dev.aurakai.auraframefx.ui.theme.NeonTeal
import dev.aurakai.auraframefx.viewmodel.ConferenceRoomViewModel

/**
 * Displays the main conference room UI, including agent selection, recording and transcription controls, chat interface, and message input.
 *
 * This composable manages local state for the selected agent, recording, and transcription status. It provides interactive controls for switching agents, starting/stopping recording and transcription, and a placeholder chat interface with a message input area.
 */
/**
 * Renders the conference room screen with agent selection, recording and transcription controls, a scrollable message list, and a message input area.
 *
 * Manages local UI state for the selected agent, recording/transcribing toggles, and message input while loading agent labels from resources and displaying a list of `ConferenceMessage`s.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConferenceRoomScreen() {
    // Load string resources once at composition time
    val agentAura = stringResource(R.string.agent_aura)
    val agentKai = stringResource(R.string.agent_kai)
    val agentCascade = stringResource(R.string.agent_cascade)

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
            Text(
                text = stringResource(R.string.conference_room),
                style = MaterialTheme.typography.headlineMedium,
                color = NeonTeal
            )

                Row {
                // System State Button
                IconButton(
                    onClick = { viewModel.getSystemState() }
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "System Status",
                        tint = NeonBlue
                    )
                }

                IconButton(
                    onClick = {
                        timber.log.Timber.i("Conference room settings clicked - Settings screen not yet implemented")
                    }
                ) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Settings",
                        contentDescription = "Settings",tint = NeonPurple
                    )
                }
            }
        }

        // Agent Selection
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AgentButton(
                agent = agentAura,
                isSelected = selectedAgent == agentAura,
                onClick = { selectedAgent = agentAura }
            )
            AgentButton(
                agent = agentKai,
                isSelected = selectedAgent == agentKai,
                onClick = { selectedAgent = agentKai }
            )
            AgentButton(
                agent = agentCascade,
                isSelected = selectedAgent == agentCascade,
                onClick = { selectedAgent = agentCascade }
            )
        }

        // Recording Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            RecordingButton(
                isRecording = isRecording,
                onClick = { isRecording = !isRecording }
            )

            Spacer(modifier = Modifier.width(16.dp))

            TranscribeButton(
                isTranscribing = isTranscribing,
                onClick = { isTranscribing = !isTranscribing }
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Fusion Activation Button
            IconButton(
                onClick = {
                    viewModel.activateFusion(
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

        Divider(
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
            items(messages.size) { index ->
                val message = messages[messages.size - 1 - index]
                MessageBubble(message = message)
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
                placeholder = { Text("Type your message...") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = NeonTeal.copy(alpha = 0.1f),
                    unfocusedContainerColor = NeonTeal.copy(alpha = 0.1f)
                )
            )

            IconButton(
                onClick = {
                    if (messageInput.isNotBlank()) {
                        val newMessage = ConferenceMessage(
                            agent = selectedAgent,
                            text = messageInput,
                            timestamp = System.currentTimeMillis()
                        )
                        messageInput = ""
                    }
                }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = NeonBlue
                )
            }
        }
    }
}

/**
 * Renders a row-scoped, equally weighted button labeled with an agent name.
 *
 * The button fills available horizontal space within the Row (equal weight), applies horizontal
 * padding, and invokes the provided `onClick` composable when pressed.
 *
 * @param agent The label text to display on the button.
 * @param isSelected Indicates whether this agent is currently selected; the visual styling for
 *   selection is not applied by this function.
 * @param onClick Composable lambda invoked when the button is clicked.
 */
@Composable
fun RowScope.AgentButton(
    agent: String,
    isSelected: Boolean,
    onClick: @Composable () -> Unit,
) {
    Button(
        onClick = onClick, ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ), Modifier
            .weight(1f)
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = agent,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

/**
 * Shows a square recording control button whose icon and tint reflect the current recording state.
 *
 * @param isRecording True if recording is active; the button displays a stop icon and red tint when true, or a circle icon and neon purple tint when false.
 * @param onClick Callback invoked when the button is pressed.
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
            contentDescription = if (isRecording) stringResource(R.string.stop_recording) else stringResource(
                R.string.start_recording
            ),
            tint = color
        )
    }
}

/**
 * Displays a button that toggles transcription state and updates its icon and tint accordingly.
 *
 * @param isTranscribing When `true`, the button shows a Stop icon with a red tint; when `false`, it shows a Phone icon with a blue tint.
 * @param onClick Callback invoked when the user presses the button.
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
            contentDescription = if (isTranscribing) stringResource(R.string.stop_transcription) else stringResource(
                R.string.start_transcription
            ),
            tint = color
        )
    }
}

/**
 * Renders a chat message as a card showing the agent name and message text with an agent-specific accent.
 *
 * The card background is tinted using the agent's accent color at low alpha; the agent name is shown in that
 * accent color above the message text.
 *
 * @param message The conference message to display. `message.agent` is used as the label and to choose the
 * accent color (`"Aura"` → NeonPurple, `"Kai"` → NeonTeal, `"Cascade"` → NeonBlue, otherwise Gray); `message.text`
 * is shown as the message body.
 */
@Composable
fun MessageBubble(message: ConferenceMessage) {
    val agentColor = when (message.agent) {
        "Aura" -> NeonPurple
        "Kai" -> NeonTeal
        "Cascade" -> NeonBlue
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
            Text(
                text = message.agent,
                style = MaterialTheme.typography.labelSmall,
                color = agentColor,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

/**
 * Renders ConferenceRoomScreen inside the app MaterialTheme for IDE previews.
 *
 * This composable is intended for preview tooling and does not alter app runtime state.
 */
@Preview(showBackground = true)
@Composable
fun ConferenceRoomScreenPreview() {
    MaterialTheme {
        ConferenceRoomScreen()
    }
}