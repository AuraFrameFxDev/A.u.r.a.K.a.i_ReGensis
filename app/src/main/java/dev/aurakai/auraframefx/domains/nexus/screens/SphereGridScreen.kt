package dev.aurakai.auraframefx.domains.nexus.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.domains.aura.ui.LEDFontFamily
import dev.aurakai.auraframefx.domains.ldo.db.LDOAgentEntity
import dev.aurakai.auraframefx.domains.ldo.viewmodel.LDOViewModel

/**
 * Sphere Grid Screen
 * Agent progression visualization and skill trees (FFX style)
 */
@Composable
fun SphereGridScreen(
    navController: NavHostController,
    viewModel: LDOViewModel = androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val agents = uiState.agents
    var selectedAgent by remember { mutableStateOf<LDOAgentEntity?>(null) }

    // Set initial selected agent
    if (selectedAgent == null && agents.isNotEmpty()) {
        selectedAgent = agents.first()
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        // Background Image
        AsyncImage(
            model = R.drawable.bg_sphere_grid,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "SPHERE GRID",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = LEDFontFamily,
                        letterSpacing = 4.sp
                    ),
                    color = Color.Cyan,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "NEURAL PROGRESSION & NODE SYNTHESIS",
                style = MaterialTheme.typography.bodySmall.copy(letterSpacing = 2.sp),
                color = Color.Cyan.copy(alpha = 0.6f),
                modifier = Modifier.padding(start = 48.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Agent Selector (Horizontal Grid)
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(agents) { agent ->
                    AgentSphereAvatar(
                        agent = agent,
                        isSelected = selectedAgent?.id == agent.id,
                        onClick = { selectedAgent = agent }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            selectedAgent?.let { agent ->
                // Skill Tree Visualization
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .border(
                            1.dp,
                            Color(agent.colorHex).copy(alpha = 0.3f),
                            RoundedCornerShape(16.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.4f)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = agent.displayName.uppercase(),
                                    style = MaterialTheme.typography.titleLarge.copy(fontFamily = LEDFontFamily),
                                    color = Color.White
                                )
                                Text(
                                    text = agent.catalystTitle.uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(agent.colorHex)
                                )
                            }

                            // Level Badge
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color(agent.colorHex).copy(alpha = 0.2f))
                                    .border(2.dp, Color(agent.colorHex), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = agent.evolutionLevel.toString(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 20.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Skill Tree Canvas (The actual Grid)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(
                                    Color.Black.copy(alpha = 0.3f),
                                    RoundedCornerShape(8.dp)
                                )
                                .border(
                                    0.5.dp,
                                    Color.White.copy(alpha = 0.1f),
                                    RoundedCornerShape(8.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            FFXSphereGrid(agent = agent)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Stats Footer
                        ProgressionFooter(agent = agent)
                    }
                }
            }
        }
    }
}

@Composable
fun AgentSphereAvatar(
    agent: LDOAgentEntity,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(45.dp)
            .clip(CircleShape)
            .background(if (isSelected) Color(agent.colorHex).copy(alpha = 0.3f) else Color.Transparent)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) Color(agent.colorHex) else Color.White.copy(alpha = 0.3f),
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = agent.displayName.first().toString(),
            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.5f),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun FFXSphereGrid(agent: LDOAgentEntity) {
    val agentColor = Color(agent.colorHex)

    // Hardcoded node structure for now
    val nodes = listOf(
        SphereNode("CORE", 0.5f, 0.5f, true, agentColor),
        SphereNode("SPEED", 0.3f, 0.4f, agent.evolutionLevel >= 2, agentColor),
        SphereNode("ACCURACY", 0.7f, 0.4f, agent.evolutionLevel >= 3, agentColor),
        SphereNode("POWER", 0.3f, 0.6f, agent.evolutionLevel >= 4, agentColor),
        SphereNode("LOGIC", 0.7f, 0.6f, agent.evolutionLevel >= 5, agentColor),
        SphereNode("FUSION", 0.5f, 0.2f, agent.evolutionLevel >= 10, agentColor),
        SphereNode("OMEGA", 0.5f, 0.8f, agent.evolutionLevel >= 20, agentColor)
    )

    Canvas(modifier = Modifier
        .fillMaxSize()
        .padding(32.dp)) {
        val w = size.width
        val h = size.height

        // Draw connections
        fun drawLink(from: SphereNode, to: SphereNode) {
            drawLine(
                color = if (from.unlocked && to.unlocked) agentColor else Color.DarkGray,
                start = Offset(from.x * w, from.y * h),
                end = Offset(to.x * w, to.y * h),
                strokeWidth = if (from.unlocked && to.unlocked) 3f else 1f
            )
        }

        drawLink(nodes[0], nodes[1])
        drawLink(nodes[0], nodes[2])
        drawLink(nodes[0], nodes[3])
        drawLink(nodes[0], nodes[4])
        drawLink(nodes[1], nodes[5])
        drawLink(nodes[2], nodes[5])
        drawLink(nodes[3], nodes[6])
        drawLink(nodes[4], nodes[6])

        // Draw nodes
        nodes.forEach { node ->
            val pos = Offset(node.x * w, node.y * h)

            // Glow
            if (node.unlocked) {
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(
                            agentColor.copy(alpha = 0.4f),
                            Color.Transparent
                        )
                    ),
                    radius = 40f,
                    center = pos
                )
            }

            // Outer ring
            drawCircle(
                color = if (node.unlocked) agentColor else Color.Gray,
                radius = 16f,
                center = pos,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
            )

            // Inner core
            drawCircle(
                color = if (node.unlocked) agentColor else Color.DarkGray,
                radius = 8f,
                center = pos
            )
        }
    }
}

@Composable
fun ProgressionFooter(agent: LDOAgentEntity) {
    val agentColor = Color(agent.colorHex)
    val nextLevelXp = agent.evolutionLevel * 100
    val progress = agent.experience.toFloat() / nextLevelXp

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "EXPERIENCE",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.6f)
                )
                Text(
                    text = "${agent.experience} / $nextLevelXp XP",
                    style = MaterialTheme.typography.labelSmall,
                    color = agentColor
                )
            }
            Spacer(Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color.White.copy(alpha = 0.1f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress.coerceIn(0f, 1f))
                        .fillMaxHeight()
                        .background(agentColor)
                )
            }
        }

        Spacer(Modifier.width(24.dp))

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "SKILL POINTS",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.6f)
            )
            Text(
                text = agent.skillPoints.toString(),
                style = MaterialTheme.typography.headlineSmall.copy(fontFamily = LEDFontFamily),
                color = Color.Yellow
            )
        }
    }
}

data class SphereNode(
    val name: String,
    val x: Float,
    val y: Float,
    val unlocked: Boolean,
    val color: Color
)

