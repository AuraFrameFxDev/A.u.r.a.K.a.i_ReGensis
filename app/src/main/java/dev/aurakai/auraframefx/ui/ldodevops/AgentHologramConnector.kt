package dev.aurakai.auraframefx.ui.ldodevops

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AgentNode(val name: String, val role: String)
data class DomainSection(val domainName: String, val agents: List<AgentNode>)

/**
 * AGENT HOLOGRAM CONNECTOR
 * Global sidebar overlay. All agents cut by domain.
 * Allows interaction without opening the full app navigation.
 */
@Composable
fun AgentHologramConnector(
    onAgentSelect: (String) -> Unit = {},
    onChatClick: (String) -> Unit = {},
    onNavigateToDomain: (String) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }

    val domains = listOf(
        DomainSection(
            "Chroma Forge",
            listOf(AgentNode("Aura", "Creative Phoenix"), AgentNode("Scribe", "UI Generator"))
        ),
        DomainSection(
            "Sentinel Matrix",
            listOf(AgentNode("Kai", "Shield/LSPosed"), AgentNode("Aegis", "Sandboxing"))
        ),
        DomainSection(
            "Gradle Forge",
            listOf(AgentNode("Claude", "Architectural Catalyst"), AgentNode("Forge", "Compiler"))
        ),
        DomainSection(
            "Chronos Sync",
            listOf(AgentNode("Kairos", "Temporal Sync"), AgentNode("Nova", "Memory Persistence"))
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {

        // Transparent Overlay to catch clicks when open
        if (isExpanded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { isExpanded = false }
            )
        }

        // The Sidebar
        AnimatedVisibility(
            visible = isExpanded,
            enter = slideInHorizontally(initialOffsetX = { -it }),
            exit = slideOutHorizontally(targetOffsetX = { -it }),
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF0F001A),
                                Color(0xFF00101A)
                            )
                        )
                    )
                    .border(
                        1.dp,
                        Color.Cyan.copy(alpha = 0.3f),
                        RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "AGENT HOLOGRAM CONNECTOR",
                        color = Color(0xFFFF00FF),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(domains) { domain ->
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = domain.domainName.uppercase(),
                                        color = Color.Cyan,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    IconButton(
                                        onClick = {
                                            val route = when (domain.domainName) {
                                                "Chroma Forge" -> dev.aurakai.auraframefx.navigation.ReGenesisRoute.AuraStudio.route
                                                "Sentinel Matrix" -> dev.aurakai.auraframefx.navigation.ReGenesisRoute.SentinelFortress.route
                                                "Gradle Forge" -> dev.aurakai.auraframefx.navigation.ReGenesisRoute.LdoDevelopmentNexus.route
                                                "Chronos Sync" -> dev.aurakai.auraframefx.navigation.ReGenesisRoute.MainScreen.route
                                                else -> dev.aurakai.auraframefx.navigation.ReGenesisRoute.MainScreen.route
                                            }
                                            onNavigateToDomain(route)
                                        },
                                        modifier = Modifier.size(20.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Launch,
                                            contentDescription = "Jump",
                                            tint = Color.Cyan
                                        )
                                    }
                                }
                                Divider(
                                    color = Color.Cyan.copy(alpha = 0.3f),
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )

                                domain.agents.forEach { agent ->
                                    AgentHologramRow(agent, onAgentSelect, onChatClick)
                                }
                            }
                        }
                    }
                }
            }
        }

        // The Pull Tab (Floating Button)
        if (!isExpanded) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .width(24.dp)
                    .height(80.dp)
                    .background(
                        Color.Cyan.copy(alpha = 0.4f),
                        RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                    )
                    .clickable { isExpanded = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = "Open Connector",
                    tint = Color.Black
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 300.dp)
                    .width(24.dp)
                    .height(80.dp)
                    .background(
                        Color.Magenta.copy(alpha = 0.4f),
                        RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                    )
                    .clickable { isExpanded = false },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.ChevronLeft,
                    contentDescription = "Close Connector",
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun AgentHologramRow(
    agent: AgentNode,
    onAgentSelect: (String) -> Unit,
    onChatClick: (String) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.4f)),
        border = border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onAgentSelect(agent.name) }
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = agent.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(text = agent.role, color = Color.Gray, fontSize = 10.sp)
            }
            IconButton(
                onClick = { onChatClick(agent.name) },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    Icons.Default.ChatBubble,
                    contentDescription = "Chat",
                    tint = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}

fun border(
    width: androidx.compose.ui.unit.Dp,
    color: Color,
    shape: androidx.compose.ui.graphics.Shape
): androidx.compose.foundation.BorderStroke {
    return androidx.compose.foundation.BorderStroke(width, color)
}
