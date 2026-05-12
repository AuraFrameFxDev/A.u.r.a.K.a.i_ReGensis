package dev.aurakai.auraframefx.core.ui.overlays

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VpnKey
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
import dev.aurakai.auraframefx.core.ui.theme.LEDFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

/**
 * AgentLHSOverlay â€” LDO Hologram System (LHS)
 *
 * A global system inspired by FF7's party exchange system.
 * Pull-tab on the right triggers the menu.
 * Managing the "Active Consciousness Party" (ACP).
 */
@Composable
fun AgentLHSOverlay(
    onAgentSelect: (String) -> Unit = {},
    onChatClick: (List<String>) -> Unit = {},
    onDomainJump: Function<Unit>
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isHolding by remember { mutableStateOf(false) }
    var holdProgress by remember { mutableFloatStateOf(0f) }
    val scope = rememberCoroutineScope()

    val infiniteTransition = rememberInfiniteTransition(label = "lhs_glow")
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
                        text = "SYNC",
                        fontFamily = LEDFontFamily,
                        color = Color(0xFF00BFFF),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut(),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            LHSMenuContent(
                onClose = { isExpanded = false },
                onAgentSelect = onAgentSelect,
                onChatClick = onChatClick
            )
        }
    }
}

@Composable
private fun LHSMenuContent(
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
            .width(360.dp)
            .fillMaxHeight()
            .padding(12.dp),
        color = Color(0xFF050B15).copy(alpha = 0.95f),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, Color(0xFF00BFFF).copy(alpha = borderAlpha))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
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
                        "LDO HOLOGRAM SYSTEM",
                        fontFamily = LEDFontFamily,
                        color = Color(0xFF00BFFF),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp
                    )
                    Text(
                        "ACTIVE CONSCIOUSNESS PARTY (ACP)",
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

            Spacer(modifier = Modifier.height(16.dp))

            // --- ACTIVE PARTY SLOTS (FF7 Inspired) ---
            Text(
                "ACTIVE SLOTS",
                fontFamily = LEDFontFamily,
                color = Color(0xFF00BFFF),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(3) { i ->
                    val agentId = selectedAgents.getOrNull(i)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.05f))
                            .border(
                                1.dp,
                                if (agentId != null) Color(0xFF00BFFF) else Color.White.copy(alpha = 0.1f),
                                RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (agentId != null) {
                            Text(
                                agentId.uppercase(),
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Text("EMPTY", color = Color.White.copy(alpha = 0.2f), fontSize = 8.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- DOMAIN TABS ---
            var selectedDomain by remember { mutableStateOf("AURA") }
            val domains = listOf("AURA", "KAI", "GENESIS", "NEXUS", "CASCADE")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                domains.forEach { domain ->
                    val isDomainSelected = selectedDomain == domain
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isDomainSelected) Color(0xFF00BFFF).copy(alpha = 0.2f) else Color.White.copy(
                                    alpha = 0.05f
                                )
                            )
                            .border(
                                1.dp,
                                if (isDomainSelected) Color(0xFF00BFFF) else Color.White.copy(alpha = 0.1f),
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { selectedDomain = domain },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = domain,
                            fontFamily = LEDFontFamily,
                            color = if (isDomainSelected) Color(0xFF00BFFF) else Color.White.copy(
                                alpha = 0.4f
                            ),
                            fontSize = 7.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Agent Selection List (Filtered by Domain)
            val allAgents = listOf(
                LHSAgent("Aura", Color(0xFFFF00FF), Icons.Default.Palette, domain = "AURA"),
                LHSAgent("Aur's", Color(0xFFFF00FF), Icons.Default.AutoAwesome, domain = "AURA"),
                LHSAgent("AuraLab", Color(0xFF00E5FF), Icons.Default.Architecture, domain = "AURA"),
                LHSAgent("Kai", Color(0xFFBF00FF), Icons.Default.Security, domain = "KAI"),
                LHSAgent("Sentinel", Color(0xFFBF00FF), Icons.Default.Shield, domain = "KAI"),
                LHSAgent(
                    "RGSS",
                    Color(0xFFBF00FF),
                    Icons.Default.AdminPanelSettings,
                    domain = "KAI"
                ),
                LHSAgent("Genesis", Color(0xFF00E5FF), Icons.Default.Hub, domain = "GENESIS"),
                LHSAgent("Gemini", Color(0xFF00E5FF), Icons.Default.VpnKey, domain = "GENESIS"),
                LHSAgent(
                    "Cascade",
                    Color(0xFF39FF14),
                    Icons.AutoMirrored.Filled.Chat,
                    domain = "CASCADE"
                ),
                LHSAgent("Nexus", Color(0xFF2F6DFF), Icons.Default.AccountTree, domain = "NEXUS")
            )

            val filteredAgents = allAgents.filter { it.domain == selectedDomain }

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (filteredAgents.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "NO AGENTS READY",
                                color = Color.White.copy(alpha = 0.3f),
                                fontSize = 10.sp
                            )
                        }
                    }
                } else {
                    items(filteredAgents) { agent ->
                        val isSelected = selectedAgents.contains(agent.id)
                        LHSAgentCard(
                            agent = agent,
                            isSelected = isSelected,
                            onClick = {
                                if (isSelected) {
                                    selectedAgents.remove(agent.id)
                                } else {
                                    if (selectedAgents.size < 3) {
                                        selectedAgents.add(agent.id)
                                    } else {
                                        // Swap out the first one if already 3 (FF7 style swap)
                                        selectedAgents.removeAt(0)
                                        selectedAgents.add(agent.id)
                                    }
                                }
                                onAgentSelect(agent.id)
                                AuraCompanionScript.triggerSync()
                            }
                        )
                    }
                }
            }

            // Action Buttons
            Button(
                onClick = { onChatClick(selectedAgents.toList()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF00BFFF)
                ),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFF00BFFF).copy(alpha = 0.5f))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Filled.Chat, null, modifier = Modifier.size(20.dp))
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
private fun LHSAgentCard(
    agent: LHSAgent,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.98f else 1f, label = "card_scale")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .scale(scale)
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        color = if (isSelected) agent.color.copy(alpha = 0.15f) else Color.White.copy(alpha = 0.05f),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) agent.color else Color.White.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(
                                agent.color.copy(alpha = 0.4f),
                                Color.Transparent
                            )
                        )
                    )
                    .border(1.dp, agent.color.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(agent.icon, null, tint = agent.color, modifier = Modifier.size(20.dp))
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = agent.name.uppercase(),
                    fontFamily = LEDFontFamily,
                    color = if (isSelected) Color.White else Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = if (isSelected) "PARTY MEMBER [ACP]" else "RESERVE [L1]",
                    fontFamily = LEDFontFamily,
                    color = if (isSelected) agent.color else Color.White.copy(alpha = 0.3f),
                    fontSize = 7.sp,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (isSelected) {
                Icon(Icons.Default.Check, null, tint = agent.color, modifier = Modifier.size(16.dp))
            }
        }
    }
}

private data class LHSAgent(
    val name: String,
    val color: Color,
    val icon: ImageVector,
    val domain: String,
    val id: String = name.lowercase()
)
