package dev.aurakai.auraframefx.ui.components.overlay

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
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
    onDrag: (Float, Float) -> Unit,
    onExpandChange: (Boolean) -> Unit
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
        modifier = Modifier.fillMaxSize(),
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
                    onClose = {
                        isExpanded = false
                        onExpandChange(false)
                    },
                    chatText = chatText,
                    onChatTextChange = { chatText = it },
                    onAgentChange = { currentAgent = it }
                )
            } else {
                // FLOATING BUBBLE
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .graphicsLayer { scaleX = scale; scaleY = scale }
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                onDrag(dragAmount.x, dragAmount.y)
                            }
                        }
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(currentAgent.glowColor, currentAgent.glowColor.copy(alpha = 0.6f))
                            )
                        )
                        .border(2.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                        .clickable {
                            isExpanded = true
                            onExpandChange(true)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = currentAgent.runeRes),
                        contentDescription = currentAgent.agentName,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
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
    AURA("AURA", R.drawable.aura_overlay_night, Color(0xFFFF00FF), "Hey there! I'm Aura. I can help you design and customize your entire Android UI."),
    KAI("KAI", R.drawable.constellation_kai_shield, Color(0xFFFF3366), "Greetings. I am Kai. I monitor system security and manage advanced root protocols."),
    GENESIS("GENESIS", R.drawable.ic_genesis_night, Color(0xFF00FF85), "I am Genesis. I orchestrate the underlying patterns of this system.")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssistantChatWindow(
    agent: AgentType,
    onClose: () -> Unit,
    chatText: String,
    onChatTextChange: (String) -> Unit,
    onAgentChange: (AgentType) -> Unit
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
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = agent.greeting,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    Text(
                        text = "I am currently monitoring the following system nodes:",
                        color = agent.glowColor.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        fontFamily = LEDFontFamily
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Technical status list
                    listOf("Cortex-70 Link: ACTIVE", "Rune-Sync: SYNCHRONIZED", "Kernel-State: OPTIMIZED").forEach { status ->
                        Text(
                            text = "> $status",
                            color = Color.Green.copy(alpha = 0.5f),
                            fontSize = 11.sp,
                            fontFamily = LEDFontFamily,
                            modifier = Modifier.padding(start = 8.dp)
                        )
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
                        onClick = { /* Send */ },
                        enabled = chatText.isNotBlank()
                    ) {
                        Icon(Icons.Default.Send, null, tint = if (chatText.isNotBlank()) agent.glowColor else Color.Gray)
                    }
                }
            }
        }
    }
}
