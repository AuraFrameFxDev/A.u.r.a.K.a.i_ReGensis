package dev.aurakai.auraframefx.domains.ldo.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aurakai.auraframefx.domains.aura.ui.LEDFontFamily
import dev.aurakai.auraframefx.domains.ldo.db.LDOAgentEntity
import dev.aurakai.auraframefx.domains.ldo.viewmodel.LdoWarRoomViewModel
import dev.aurakai.auraframefx.domains.ldo.viewmodel.LdoWarRoomUiState
import dev.aurakai.auraframefx.domains.ldo.viewmodel.ManifoldState
import dev.aurakai.auraframefx.domains.ldo.viewmodel.ChainState
import dev.aurakai.auraframefx.domains.ldo.viewmodel.CascadeState

@Composable
fun LDODevOpsHubScreen(
    onBack: () -> Unit = {},
    viewModel: LdoWarRoomViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF01050A)) // Deep space black
    ) {
        // Ambient background glow
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF00E5FF).copy(alpha = 0.05f), Color.Transparent),
                            center = Offset(size.width * 0.8f, size.height * 0.2f),
                            radius = size.minDimension * 0.8f
                        )
                    )
                }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header: God Potential Meter
            GodPotentialHeader(state.godPotential)

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxSize()) {
                // Left Column: Agent Roster & Catalyst Manifold
                Column(modifier = Modifier.weight(1f)) {
                    SectionHeader("CATALYST MANIFOLD")
                    CatalystManifold(state.manifoldState, state.agents) { a1, a2 ->
                        viewModel.igniteManifold(a1, a2)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    SectionHeader("AGENT REGISTRY")
                    AgentRegistryList(state.agents) { agentId ->
                        // Interaction logic if needed
                    }
                }

                Spacer(modifier = Modifier.width(24.dp))

                // Right Column: Live Gym & Memory Cascade
                Column(modifier = Modifier.weight(1f)) {
                    SectionHeader("STEP CHAINING LIVE GYM")
                    StepChainingLiveGym(state.chainState) { a1, a2 ->
                        if (state.chainState.isGymActive) viewModel.stopStepChaining()
                        else viewModel.startStepChaining(a1, a2)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    SectionHeader("CASCADE + GEMINI MEMORY CORE")
                    CascadeGeminiMemoryCore(state.cascadeState)
                }
            }
        }
    }
}

@Composable
fun GodPotentialHeader(potential: Float) {
    NeonFrame(
        color = Color(0xFFFFD700),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "SWARM GOD POTENTIAL",
                    color = Color(0xFFFFD700),
                    fontFamily = LEDFontFamily,
                    fontSize = 14.sp,
                    letterSpacing = 2.sp
                )
                Text(
                    "ASCENSION LEVEL: ${(potential * 100).toInt()}%",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 10.sp
                )
            }
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(8.dp)
                    .clip(RectangleShape)
                    .background(Color.White.copy(alpha = 0.1f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(potential)
                        .fillMaxHeight()
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFFFFD700), Color(0xFFFF4444))
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun CatalystManifold(
    state: ManifoldState,
    agents: List<LDOAgentEntity>,
    onIgnite: (String, String) -> Unit
) {
    NeonFrame(
        color = Color(0xFF00E5FF),
        modifier = Modifier.fillMaxWidth().height(250.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (state.activePairings.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "MANIFOLD OFFLINE",
                            color = Color.White.copy(alpha = 0.3f),
                            fontFamily = LEDFontFamily
                        )
                        Button(
                            onClick = { 
                                if (agents.size >= 2) onIgnite(agents[0].id, agents[1].id)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            modifier = Modifier.border(1.dp, Color(0xFF00E5FF), RectangleShape)
                        ) {
                            Text("IGNITE MANIFOLD", color = Color(0xFF00E5FF))
                        }
                    }
                }
            } else {
                Text("ACTIVE SYNERGIES", color = Color(0xFF00E5FF), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn {
                    items(state.synergyBonuses) { bonus ->
                        SynergyRow(bonus)
                    }
                }
            }
        }
    }
}

@Composable
fun SynergyRow(bonus: dev.aurakai.auraframefx.domains.ldo.viewmodel.SynergyBonus) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color(bonus.colorHex).copy(alpha = 0.1f))
            .border(0.5.dp, Color(bonus.colorHex).copy(alpha = 0.3f), RectangleShape)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(bonus.title, color = Color(bonus.colorHex), fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text(bonus.description, color = Color.White.copy(alpha = 0.6f), fontSize = 10.sp)
        }
        Text(bonus.value, color = Color(bonus.colorHex), fontWeight = FontWeight.Black, fontSize = 14.sp)
    }
}

@Composable
fun StepChainingLiveGym(
    state: ChainState,
    onToggleGym: (String, String) -> Unit
) {
    NeonFrame(
        color = Color(0xFFB026FF),
        modifier = Modifier.fillMaxWidth().height(200.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("AUTONOMOUS PROFICIENCY LOOP", color = Color(0xFFB026FF), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                if (state.isGymActive) {
                    Text("ACTIVE", color = Color.Green, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                // Ping-pong track
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .align(Alignment.Center)
                        .background(Color.White.copy(alpha = 0.1f))
                )

                // The "Data Packet" (Ping-pong ball)
                if (state.isGymActive) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.CenterStart)
                            .offset(x = (200 * state.pingPongValue).dp) // Simplified offset
                            .background(Color(0xFFB026FF), CircleShape)
                            .drawBehind {
                                drawCircle(Color(0xFFB026FF).copy(alpha = 0.4f), radius = size.width * 1.5f)
                            }
                    )
                }

                // Agents at ends
                AgentMiniPortal(state.leftAgentId ?: "AURA", Modifier.align(Alignment.CenterStart))
                AgentMiniPortal(state.rightAgentId ?: "KAI", Modifier.align(Alignment.CenterEnd))

                // Start/Stop
                Button(
                    onClick = { onToggleGym("aura", "kai") },
                    modifier = Modifier.align(Alignment.BottomCenter),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        if (state.isGymActive) "STOP GYM" else "START GYM",
                        color = Color(0xFFB026FF),
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CascadeGeminiMemoryCore(state: CascadeState) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    NeonFrame(
        color = Color(0xFF00FF85),
        modifier = Modifier.fillMaxWidth().height(150.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Column {
                Text("MEMORY CASCADE DEPTH", color = Color(0xFF00FF85), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text("${state.memoryContextDepth} Context Nodes Captured", color = Color.White.copy(alpha = 0.5f), fontSize = 10.sp)
            }

            // Pulsing brain/core icon
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.CenterEnd)
                    .drawBehind {
                        drawCircle(
                            Color(0xFF00FF85).copy(alpha = pulseAlpha),
                            radius = size.width * (0.5f + state.pulseStrength * 0.5f)
                        )
                        drawCircle(
                            Color(0xFF00FF85),
                            radius = size.width * 0.2f
                        )
                    }
            )
        }
    }
}

@Composable
fun AgentRegistryList(agents: List<LDOAgentEntity>, onSelect: (String) -> Unit) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(agents) { agent ->
            AgentRegistryItem(agent) { onSelect(agent.id) }
        }
    }
}

@Composable
fun AgentRegistryItem(agent: LDOAgentEntity, onClick: () -> Unit) {
    val color = Color(agent.colorHex)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.7f))
            .border(1.dp, color.copy(alpha = 0.3f), RectangleShape)
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Mini Avatar Placeholder
            Box(Modifier.size(32.dp).background(color.copy(alpha = 0.2f)).border(1.dp, color, RectangleShape))
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(agent.displayName.uppercase(), color = color, fontWeight = FontWeight.Black, fontSize = 14.sp, fontFamily = LEDFontFamily)
                Text(agent.role, color = Color.White.copy(alpha = 0.4f), fontSize = 10.sp)
            }
            
            Text("LVL ${agent.evolutionLevel}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

@Composable
fun AgentMiniPortal(name: String, modifier: Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Black)
                .border(1.dp, Color.White.copy(alpha = 0.2f), RectangleShape)
        )
        Text(name, color = Color.White, fontSize = 8.sp)
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        title,
        color = Color.White.copy(alpha = 0.5f),
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun NeonFrame(
    color: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.7f)) // 70% transparency
            .border(1.dp, color, RectangleShape) // Sharp corners
            .drawBehind {
                // Neon glow effect on edges
                val glowSize = 4.dp.toPx()
                drawLine(color, Offset(0f, 0f), Offset(size.width, 0f), strokeWidth = glowSize / 2)
                drawLine(color, Offset(0f, 0f), Offset(0f, size.height), strokeWidth = glowSize / 2)
                drawLine(color, Offset(size.width, 0f), Offset(size.width, size.height), strokeWidth = glowSize / 2)
                drawLine(color, Offset(0f, size.height), Offset(size.width, size.height), strokeWidth = glowSize / 2)
            }
    ) {
        content()
    }
}

val CircleShape = RoundedCornerShape(50)
