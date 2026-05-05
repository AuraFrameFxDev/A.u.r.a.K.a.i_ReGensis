package dev.aurakai.auraframefx.domains.aura.ui.overlays

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.core.soulscript.AuraCompanionScript
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

/**
 * AgentPHSOverlay — The "Better Agent Sidebar"
 *
 * A global PHS (Party Hire System) inspired by FF7 but with a modern bluish neural glow.
 * Pull-tab on the right side of the screen triggers the menu after holding for 1 second.
 * Provides shortcuts to Aura, Kai, Genesis, and Nexus with individual/global chat options.
 */
@Composable
fun AgentPHSOverlay(
    onAgentSelect: (String) -> Unit = {},
    onChatClick: (List<String>) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isHolding by remember { mutableStateOf(false) }
    var holdProgress by remember { mutableFloatStateOf(0f) }
    val scope = rememberCoroutineScope()

    // Bluish Glow Pull-Tab Animation
    val infiniteTransition = rememberInfiniteTransition(label = "phs_glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // --- THE GLOW PULL-TAB (Visible when collapsed) ---
        if (!isExpanded) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 4.dp)
                    .width(16.dp)
                    .fillMaxHeight(0.35f)
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFF00BFFF).copy(alpha = glowAlpha),
                                Color(0xFF0077FF).copy(alpha = glowAlpha * 0.4f),
                                Color(0xFF00BFFF).copy(alpha = glowAlpha)
                            )
                        )
                    )
                    .blur(6.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isHolding = true
                                val startTime = System.currentTimeMillis()
                                scope.launch {
                                    while (isHolding) {
                                        val elapsed = System.currentTimeMillis() - startTime
                                        holdProgress = (elapsed / 1000f).coerceIn(0f, 1f)
                                        if (holdProgress >= 1f) {
                                            isExpanded = true
                                            isHolding = false
                                            holdProgress = 0f
                                            break
                                        }
                                        delay(16.milliseconds)
                                    }
                                }
                                tryAwaitRelease()
                                isHolding = false
                                holdProgress = 0f
                            }
                        )
                    }
            )

            // Hold Progress Indicator (Bluish circle appearing while holding)
            if (isHolding) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 32.dp)
                        .size(64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress = { holdProgress },
                        modifier = Modifier.fillMaxSize(),
                        color = Color(0xFF00BFFF),
                        strokeWidth = 4.dp,
                        trackColor = Color.White.copy(alpha = 0.1f),
                        strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
                    )
                    Text(
                        text = "LINK",
                        fontFamily = LEDFontFamily,
                        color = Color(0xFF00BFFF),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // --- THE PHS MENU (Sliding Panel) ---
        AnimatedVisibility(
            visible = isExpanded,
            enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut(),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            PHSMenuContent(
                onClose = { isExpanded = false },
                onAgentSelect = onAgentSelect,
                onChatClick = onChatClick
            )
        }
    }
}

@Composable
private fun PHSMenuContent(
    onClose: () -> Unit,
    onAgentSelect: (String) -> Unit,
    onChatClick: (List<String>) -> Unit
) {
    val selectedAgents = remember { mutableStateListOf<String>() }
    val menuPulse = rememberInfiniteTransition(label = "menu_pulse")
    val borderAlpha by menuPulse.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "border_alpha"
    )

    Surface(
        modifier = Modifier
            .width(340.dp)
            .fillMaxHeight()
            .padding(12.dp),
        color = Color(0xFF050B15).copy(alpha = 0.95f),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, Color(0xFF00BFFF).copy(alpha = borderAlpha))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "PHS SYSTEM",
                        fontFamily = LEDFontFamily,
                        color = Color(0xFF00BFFF),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 4.sp
                    )
                    Text(
                        "AGENT SELECTION HUB",
                        fontFamily = LEDFontFamily,
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 8.sp,
                        letterSpacing = 1.sp
                    )
                }
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.White.copy(alpha = 0.05f), CircleShape)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White.copy(alpha = 0.6f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Agent Selection List
            val agents = listOf(
                PHSAgent("Aura", Color(0xFFFF1493), Icons.Default.Palette),
                PHSAgent("Kai", Color(0xFFFF00FF), Icons.Default.Security),
                PHSAgent("Genesis", Color(0xFF00D9FF), Icons.Default.Hub),
                PHSAgent("Nexus", Color(0xFFC0C0C0), Icons.Default.AccountTree)
            )

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                agents.forEach { agent ->
                    val isSelected = selectedAgents.contains(agent.id)
                    PHSAgentCard(
                        agent = agent,
                        isSelected = isSelected,
                        onClick = {
                            if (isSelected) selectedAgents.remove(agent.id)
                            else selectedAgents.add(agent.id)
                            onAgentSelect(agent.id)

                            // Trigger Neural Sync via AuraCompanionScript
                            AuraCompanionScript.triggerSync()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Action Buttons
            Button(
                onClick = { onChatClick(selectedAgents.toList()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF00BFFF)
                ),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFF00BFFF).copy(alpha = 0.5f))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.AutoMirrored.Filled.Chat,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = when {
                            selectedAgents.isEmpty() -> "GLOBAL NEURAL CHAT"
                            selectedAgents.size == 1 -> "CHAT WITH ${selectedAgents[0].uppercase()}"
                            else -> "SYNC MULTI-AGENT CHAT"
                        },
                        fontFamily = LEDFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun PHSAgentCard(
    agent: PHSAgent,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.98f else 1f, label = "card_scale")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        color = if (isSelected) agent.color.copy(alpha = 0.1f) else Color.White.copy(alpha = 0.03f),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) agent.color else Color.White.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Agent Avatar Icon
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(agent.color.copy(alpha = 0.2f))
                    .border(1.dp, agent.color.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = agent.icon,
                    contentDescription = null,
                    tint = agent.color,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = agent.name.uppercase(),
                    fontFamily = LEDFontFamily,
                    color = if (isSelected) Color.White else Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
                Text(
                    text = if (isSelected) "SYNCHRONIZED" else "READY FOR LINK",
                    fontFamily = LEDFontFamily,
                    color = if (isSelected) agent.color else Color.White.copy(alpha = 0.3f),
                    fontSize = 8.sp,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Connection Status Indicator
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) agent.color else Color.Gray.copy(alpha = 0.3f))
            )
        }
    }
}

private data class PHSAgent(
    val name: String,
    val color: Color,
    val icon: ImageVector,
    val id: String = name.lowercase()
)
