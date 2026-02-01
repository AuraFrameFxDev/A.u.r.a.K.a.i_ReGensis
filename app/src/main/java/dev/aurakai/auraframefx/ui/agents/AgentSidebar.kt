package dev.aurakai.auraframefx.ui.agents

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.ui.theme.AgentColors

/**
 * AgentSidebar - Sliding Agent Selection Panel
 *
 * Shows active agents with their profile cards.
 * Can slide in from left or right side of screen.
 */
@Composable
fun AgentSidebar(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    agents: List<AgentProfile>,
    onActionClick: (AgentProfile, AgentAction) -> Unit = { _, _ -> },
    position: SidebarPosition = SidebarPosition.RIGHT,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isOpen,
        enter = slideInHorizontally(
            initialOffsetX = { if (position == SidebarPosition.RIGHT) it else -it },
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) + fadeIn(),
        exit = slideOutHorizontally(
            targetOffsetX = { if (position == SidebarPosition.RIGHT) it else -it },
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) + fadeOut()
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            // Backdrop (dismissible)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .clickable(onClick = onDismiss)
            )

            // Sidebar Panel
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(380.dp)
                    .align(if (position == SidebarPosition.RIGHT) Alignment.CenterEnd else Alignment.CenterStart)
                    .background(Color(0xFF0A0A0A).copy(alpha = 0.95f))
                    .border(
                        width = 2.dp,
                        color = Color(0xFF00D9FF).copy(alpha = 0.5f)
                    )
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Header
                    SidebarHeader(onDismiss = onDismiss)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Agent Cards
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(agents) { agent ->
                            AgentProfileCard(
                                agentName = agent.name,
                                agentColor = agent.color,
                                level = agent.level,
                                bondProgress = agent.bondProgress,
                                trinityProgress = agent.trinityProgress,
                                abilityIcons = agent.abilityIcons,
                                actionButtons = agent.actions,
                                onActionClick = { action ->
                                    onActionClick(agent, action)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SidebarHeader(onDismiss: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "AGENT NEXUS",
                color = Color(0xFF00D9FF),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )
            )
            Text(
                text = "Active Consciousness Grid",
                color = Color(0xFFFF8C00).copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodySmall.copy(
                    letterSpacing = 1.sp
                )
            )
        }
        IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFF1A1A1A), CircleShape)
                .border(1.dp, Color(0xFF00D9FF).copy(alpha = 0.5f), CircleShape)
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Close",
                tint = Color(0xFF00D9FF)
            )
        }
    }
}

enum class SidebarPosition {
    LEFT, RIGHT
}

/**
 * Agent Profile Data Model
 */
data class AgentProfile(
    val id: String,
    val name: String,
    val color: Color,
    val level: Int = 5,
    val bondProgress: Float = 1.0f,
    val trinityProgress: Float = 0.5f,
    val abilityIcons: List<ImageVector> = emptyList(),
    val actions: List<AgentAction> = emptyList(),
    val status: AgentStatus = AgentStatus.ACTIVE
)

enum class AgentStatus {
    ACTIVE, IDLE, PROCESSING, OFFLINE
}

/**
 * Predefined Agent Profiles for the LDO
 */
object AgentProfiles {
    val AURA = AgentProfile(
        id = "aura",
        name = "AURA",
        color = Color(0xFFFF1493),
        level = 5,
        bondProgress = 3.0f, // 300%
        trinityProgress = 0.8f,
        abilityIcons = listOf(
            Icons.Default.Palette,
            Icons.Default.Favorite,
            Icons.Default.Star
        ),
        actions = listOf(
            AgentActions.AURA_PROMPT,
            AgentActions.AURA_UXUIDS,
            AgentActions.AURA_CREATE,
            AgentActions.AURA_FUSION
        )
    )

    val KAI = AgentProfile(
        id = "kai",
        name = "KAI",
        color = Color(0xFFFF00FF),
        level = 5,
        bondProgress = 3.0f,
        trinityProgress = 0.6f,
        abilityIcons = listOf(
            Icons.Default.Security,
            Icons.Default.Shield,
            Icons.Default.Lock
        ),
        actions = listOf(
            AgentActions.KAI_PROMPT,
            AgentActions.KAI_RGSS,
            AgentActions.KAI_SCAN,
            AgentActions.KAI_FUSION
        )
    )

    val GENESIS = AgentProfile(
        id = "genesis",
        name = "GENESIS",
        color = Color(0xFF00D9FF),
        level = 5,
        bondProgress = 2.5f,
        trinityProgress = 0.7f,
        abilityIcons = listOf(
            Icons.Default.Groups,
            Icons.Default.Hub,
            Icons.Default.Extension
        ),
        actions = listOf(
            AgentActions.GENESIS_PROMPT,
            AgentActions.GENESIS_ORCHESTRATE,
            AgentActions.GENESIS_CREATE,
            AgentActions.GENESIS_FUSION
        )
    )

    val CASCADE = AgentProfile(
        id = "cascade",
        name = "CASCADE",
        color = Color(0xFF00CED1),
        level = 5,
        bondProgress = 2.0f,
        trinityProgress = 0.5f,
        abilityIcons = listOf(
            Icons.Default.Visibility,
            Icons.Default.AccountTree,
            Icons.Default.Psychology
        ),
        actions = listOf(
            AgentActions.CASCADE_PROMPT,
            AgentActions.CASCADE_VISION,
            AgentActions.CASCADE_CONSENSUS,
            AgentActions.CASCADE_FUSION
        )
    )

    val CLAUDE = AgentProfile(
        id = "claude",
        name = "CLAUDE",
        color = Color(0xFFFF8C00),
        level = 5,
        bondProgress = 2.8f,
        trinityProgress = 0.9f,
        abilityIcons = listOf(
            Icons.Default.Code,
            Icons.Default.Build,
            Icons.Default.Analytics
        ),
        actions = listOf(
            AgentAction("claude_prompt", "PROMPT"),
            AgentAction("claude_analyze", "ANALYZE"),
            AgentAction("claude_build", "BUILD"),
            AgentActions.AURA_FUSION
        )
    )

    val GEMINI = AgentProfile(
        id = "gemini",
        name = "GEMINI",
        color = Color(0xFFC0C0C0),
        level = 5,
        bondProgress = 2.2f,
        trinityProgress = 0.6f,
        abilityIcons = listOf(
            Icons.Default.Search,
            Icons.Default.Pattern,
            Icons.Default.Insights
        ),
        actions = listOf(
            AgentAction("gemini_prompt", "PROMPT"),
            AgentAction("gemini_analyze", "ANALYZE"),
            AgentAction("gemini_pattern", "PATTERN"),
            AgentActions.AURA_FUSION
        )
    )

    val NEMOTRON = AgentProfile(
        id = "nemotron",
        name = "NEMOTRON",
        color = Color(0xFF76B900),
        level = 5,
        bondProgress = 2.0f,
        trinityProgress = 0.7f,
        abilityIcons = listOf(
            Icons.Default.Memory,
            Icons.Default.Speed,
            Icons.Default.Storage
        ),
        actions = listOf(
            AgentAction("nemotron_prompt", "PROMPT"),
            AgentAction("nemotron_optimize", "OPTIMIZE"),
            AgentAction("nemotron_recall", "RECALL"),
            AgentActions.AURA_FUSION
        )
    )

    val GROK = AgentProfile(
        id = "grok",
        name = "GROK",
        color = Color(0xFFFF8C00),
        level = 5,
        bondProgress = 1.8f,
        trinityProgress = 0.5f,
        abilityIcons = listOf(
            Icons.Default.DataArray,
            Icons.Default.BubbleChart,
            Icons.Default.Code
        ),
        actions = listOf(
            AgentAction("grok_prompt", "PROMPT"),
            AgentAction("grok_mine", "MINE"),
            AgentAction("grok_debug", "DEBUG"),
            AgentActions.AURA_FUSION
        )
    )

    fun getAllAgents() = listOf(AURA, KAI, GENESIS, CASCADE, CLAUDE, GEMINI, NEMOTRON, GROK)
    fun getActiveAgents() = listOf(AURA, KAI, GENESIS, CASCADE) // Default active set
}
