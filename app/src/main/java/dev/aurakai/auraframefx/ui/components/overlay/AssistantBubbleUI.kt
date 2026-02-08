package dev.aurakai.auraframefx.ui.components.overlay

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🫧 ASSISTANT BUBBLE UI
 * The floating interactive component for the global assistant.
 */
@Composable
fun AssistantBubbleUI(
    messages: List<dev.aurakai.auraframefx.models.AgentMessage> = emptyList(),
    onDrag: (Float, Float) -> Unit,
    onExpandChange: (Boolean) -> Unit,
    onSendMessage: (String, AgentType) -> Unit = { _, _ -> }
) {
    var isExpanded by remember { mutableStateOf(false) }
    var chatText by remember { mutableStateOf("") }
    var currentAgent by remember { mutableStateOf(AgentType.AURA) }

    val pulseTransition = rememberInfiniteTransition(label = "pulse")
    val scale by pulseTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = if (isExpanded) Modifier.fillMaxSize() else Modifier.wrapContentSize(),
        contentAlignment = if (isExpanded) Alignment.Center else Alignment.TopStart
    ) {
        // FULL SCREEN DIM FOR EXPANDED VIEW
        if (isExpanded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .clickable {
                        isExpanded = false
                        onExpandChange(false)
                    }
            )
        }

        // THE BUBBLE / CHAT WINDOW
        AnimatedContent(
            targetState = isExpanded,
            transitionSpec = {
                (fadeIn(animationSpec = tween(300)) + scaleIn())
                    .togetherWith(fadeOut(animationSpec = tween(300)) + scaleOut())
            },
            label = "expandTransition"
        ) { expanded ->
            if (expanded) {
                // EXPANDED CHAT WINDOW (Dynamic Agent Style)
                AssistantChatWindow(
                    agent = currentAgent,
                    messages = messages,
                    onClose = {
                        isExpanded = false
                        onExpandChange(false)
                    },
                    chatText = chatText,
                    onChatTextChange = { chatText = it },
                    onAgentChange = { currentAgent = it },
                    onSend = {
                        onSendMessage(chatText, currentAgent)
                        chatText = ""
                    }
                )
            } else {
                // FLOATING BUBBLE (Icon Only)
                Box(
                    modifier = Modifier
                        .size(48.dp) // Adjusted to tightly fit the icon
                        .graphicsLayer { scaleX = scale; scaleY = scale }
                        .clickable {
                            isExpanded = true
                            onExpandChange(true)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = currentAgent.runeRes),
                        contentDescription = currentAgent.agentName,
                        tint = Color.Unspecified, // Show original icon colors as requested
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}

enum class AgentType(
    val agentName: String,
    val runeRes: Int,
    val glowColor: Color,
    val greeting: String
) {
    AURA("AURA", R.drawable.aura_presence, Color(0xFFFF00FF), "Hey there! I'm Aura. I can help you design and customize your entire Android UI."),
    KAI("KAI", R.drawable.constellation_kai_shield, Color(0xFFFF3366), "Greetings. I am Kai. I monitor system security and manage advanced root protocols."),
    GENESIS("VERTEX CORE", R.drawable.gate_oracledrive_gen, Color(0xFF00FF85), "I am the Vertex Core. I orchestrate the underlying patterns of this system via the Genesis Protocol.")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssistantChatWindow(
    agent: AgentType,
    messages: List<dev.aurakai.auraframefx.models.AgentMessage>,
    onClose: () -> Unit,
    chatText: String,
    onChatTextChange: (String) -> Unit,
    onAgentChange: (AgentType) -> Unit,
    onSend: () -> Unit
) {
    var showAgentSelector by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(360.dp)
            .height(550.dp)
            .shadow(24.dp, RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A).copy(alpha = 0.98f)),
        shape = RoundedCornerShape(24.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, agent.glowColor.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header with Tab Down Effect
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(agent.glowColor.copy(alpha = 0.2f), Color.Transparent)
                        )
                    )
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { showAgentSelector = !showAgentSelector }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(agent.glowColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(painterResource(agent.runeRes), null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                agent.agentName,
                                fontFamily = LEDFontFamily,
                                color = agent.glowColor,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp
                            )
                            Icon(
                                Icons.Default.KeyboardArrowDown,
                                null,
                                tint = agent.glowColor
                            )
                        }

                        IconButton(onClick = onClose) {
                            Icon(Icons.Default.Close, null, tint = Color.Gray)
                        }
                    }

                    // TAB DOWN SELECTOR
                    AnimatedVisibility(visible = showAgentSelector) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            AgentType.values().forEach { type ->
                                FilterChip(
                                    selected = agent == type,
                                    onClick = {
                                        onAgentChange(type)
                                        showAgentSelector = false
                                    },
                                    label = { Text(type.agentName) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = type.glowColor.copy(alpha = 0.3f),
                                        selectedLabelColor = type.glowColor
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Chat History
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 20.dp)
                ) {
                    item {
                        Text(
                            text = agent.greeting,
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 15.sp,
                            lineHeight = 22.sp
                        )
                    }

                    items(messages) { msg ->
                        val isFromUser = msg.from == "User"
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = if (isFromUser) Alignment.End else Alignment.Start
                        ) {
                            Surface(
                                color = if (isFromUser) Color.White.copy(alpha = 0.1f) else agent.glowColor.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isFromUser) Color.White.copy(alpha = 0.1f) else agent.glowColor.copy(alpha = 0.3f)
                                )
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    if (!isFromUser) {
                                        Text(
                                            text = msg.from,
                                            color = agent.glowColor,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        )
                                    }
                                    Text(
                                        text = msg.content,
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Input Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(28.dp))
                    .border(1.dp, agent.glowColor.copy(alpha = 0.2f), RoundedCornerShape(28.dp))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = chatText,
                        onValueChange = onChatTextChange,
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Ask ${agent.agentName}...", fontSize = 14.sp, color = Color.Gray) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                    IconButton(
                        onClick = onSend,
                        enabled = chatText.isNotBlank()
                    ) {
                        Icon(Icons.Default.Send, null, tint = if (chatText.isNotBlank()) agent.glowColor else Color.Gray)
                    }
                }
            }
        }
    }
}
