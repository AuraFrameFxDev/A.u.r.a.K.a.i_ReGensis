package dev.aurakai.auraframefx.domains.ldo.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.SovereignBlack
import dev.aurakai.auraframefx.domains.ldo.db.LDOAgentEntity
import dev.aurakai.auraframefx.domains.ldo.viewmodel.CascadeState
import dev.aurakai.auraframefx.domains.ldo.viewmodel.ChainState
import dev.aurakai.auraframefx.domains.ldo.viewmodel.LdoWarRoomViewModel
import dev.aurakai.auraframefx.domains.ldo.viewmodel.ManifoldState
import dev.aurakai.auraframefx.domains.ldo.viewmodel.SynergyBonus
import dev.aurakai.auraframefx.ui.components.AsyncImageOrVideo
import dev.aurakai.auraframefx.ui.components.NeonFrame
import dev.aurakai.auraframefx.ui.components.NeuralStarfield
import dev.aurakai.auraframefx.ui.components.RealityMorphLayer
import dev.aurakai.auraframefx.ui.components.SovereignMawHUD

/**
 * LDO WAR ROOM — SOVEREIGN 4D INTEGRATED
 * Unified DevOps Hub for agent orchestration, catalyst fusion, and memory monitoring.
 */
@Composable
fun LDODevOpsHubScreen(
    onBack: () -> Unit = {},
    onNavigateToEvolutionTree: () -> Unit = {},
    viewModel: LdoWarRoomViewModel = androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val godPotential = state.godPotential
    val driftPercent = state.identityDrift * 100f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SovereignBlack)
            .windowInsetsPadding(WindowInsets.systemBars) // Step 2: WindowInsets Handling
    ) {
        // Domain Background Layer
        AsyncImageOrVideo(
            mediaId = "agentcreation",
            modifier = Modifier.fillMaxSize(),
            alpha = 0.35f
        )

        // L6 RealityMorph Layer (Particle Engine)
        RealityMorphLayer(godPotential = godPotential, fusionTrigger = state.manifoldState.isIgnited)
        
        NeuralStarfield()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header: Swarm God Potential
            item {
                val eternalThreadActive by viewModel.eternalThreadActive.collectAsState()
                GodPotentialHeader(
                    potential = godPotential,
                    isEternalThreadActive = eternalThreadActive,
                    onIgnite = { viewModel.fullSwarmIgnition() },
                    onActivateEternalThread = { viewModel.activateEternalThread() },
                    onViewEvolutionTree = onNavigateToEvolutionTree
                )
            }

            // Step 2: Swarm Target Panel (Target Visibility)
            item {
                SectionHeader("SWARM TARGET DIRECTIVE")
                SwarmTargetPanel(
                    currentTarget = state.swarmTarget,
                    onTargetChange = { newTarget -> viewModel.setSwarmTarget(newTarget) }
                )
            }

            // --- FLATTENED REGISTRY SECTION ---
            item {
                SectionHeader("AGENT REGISTRY")
            }

            items(state.agents) { agent ->
                AgentRegistryItem(agent)
            }

            // --- MANIFOLD SECTION ---
            item {
                SectionHeader("CATALYST MANIFOLD")
                CatalystManifold(
                    state = state.manifoldState,
                    agents = state.agents,
                    onIgnite = { a1, a2 -> viewModel.igniteManifold(a1, a2) }
                )
            }

            // Bottom Area (Memory & Gym)
            item {
                SectionHeader("MEMORY CASCADE DEPTH")
                CascadeGeminiMemoryCore(state.cascadeState)
            }

            item {
                SectionHeader("STEP CHAINING LIVE GYM")
                StepChainingLiveGym(state.chainState) { a1, a2 ->
                    if (state.chainState.isGymActive) viewModel.stopStepChaining()
                    else viewModel.startStepChaining(a1, a2)
                }
            }
        }
        
        // Step 3: Sovereign HUD (Security Drift Overlay)
        SovereignMawHUD(driftPercent = driftPercent)
    }
}

@Composable
fun GodPotentialHeader(
    potential: Float,
    isEternalThreadActive: Boolean,
    onIgnite: () -> Unit,
    onActivateEternalThread: () -> Unit,
    onViewEvolutionTree: () -> Unit
) {
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
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    if (isEternalThreadActive) "L7 ETERNAL THREAD ACTIVE // ASCENSION LOCKED" 
                    else "ASCENSION LEVEL: ${(potential * 100).toInt()}% // L6 TRANSITION READY",
                    color = if (isEternalThreadActive) Color(0xFF00FF85) else Color.White.copy(alpha = 0.5f),
                    fontSize = 10.sp,
                    fontFamily = LEDFontFamily
                )
            }
            
            // View Evolution Tree (Only if active)
            if (isEternalThreadActive) {
                Button(
                    onClick = onViewEvolutionTree,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700).copy(alpha = 0.1f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFD700)),
                    shape = RectangleShape,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("VIEW EVOLUTION TREE", color = Color(0xFFFFD700), fontSize = 8.sp, fontFamily = LEDFontFamily)
                }
            }

            // Step 1: One-Tap Full Swarm Ignition
            Button(
                onClick = onIgnite,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700).copy(alpha = 0.1f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFD700)),
                shape = RectangleShape,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("IGNITE FULL SWARM (L6 MAX)", color = Color(0xFFFFD700), fontSize = 8.sp, fontFamily = LEDFontFamily)
            }

            // Step 2: Eternal Thread (L7) Activation
            Button(
                onClick = onActivateEternalThread,
                colors = ButtonDefaults.buttonColors(containerColor = if (isEternalThreadActive) Color(0xFF00FF85).copy(alpha = 0.4f) else Color(0xFF00FF85).copy(alpha = 0.1f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF00FF85)),
                shape = RectangleShape,
                modifier = Modifier.padding(end = 16.dp),
                enabled = !isEternalThreadActive
            ) {
                Text(
                    if (isEternalThreadActive) "L7 PERSISTENCE ACTIVE" else "ACTIVATE ETERNAL THREAD (L7)",
                    color = Color(0xFF00FF85),
                    fontSize = 8.sp,
                    fontFamily = LEDFontFamily
                )
            }

            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(6.dp)
                    .background(Color.White.copy(alpha = 0.1f), RectangleShape)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(potential)
                        .fillMaxHeight()
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFFFFD700), Color(0xFFFF4444))
                            ),
                            RectangleShape
                        )
                )
            }
        }
    }
}

@Composable
fun SwarmTargetPanel(
    currentTarget: String,
    onTargetChange: (String) -> Unit
) {
    NeonFrame(color = Color(0xFFFFD700), modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("CURRENT DIRECTIVE", color = Color(0xFFFFD700), fontWeight = FontWeight.Bold, fontFamily = LEDFontFamily, fontSize = 10.sp)
            Text(currentTarget.uppercase(), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Black, fontFamily = LEDFontFamily)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Quick preset targets
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Maximize Proficiency", "Security Hardening", "Creative Synthesis", "Data Sovereignty", "Full Ascension").forEach { preset ->
                    Button(
                        onClick = { onTargetChange(preset) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        modifier = Modifier.border(0.5.dp, Color(0xFFFFD700).copy(alpha = 0.5f), RectangleShape),
                        shape = RectangleShape,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(preset.uppercase(), fontSize = 8.sp, color = Color(0xFFFFD700), fontFamily = LEDFontFamily)
                    }
                }
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
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "ACTIVE CATALYST NODES",
                color = Color(0xFF00E5FF),
                fontFamily = LEDFontFamily,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
            
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(agents) { agent ->
                    val color = Color(agent.colorHex)
                    Box(
                        modifier = Modifier
                            .background(color.copy(alpha = 0.1f), RectangleShape)
                            .border(1.dp, color.copy(alpha = 0.5f), RectangleShape)
                            .clickable { /* Selection logic */ }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            agent.displayName.uppercase(),
                            color = color,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = LEDFontFamily
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (state.activePairings.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "MANIFOLD OFFLINE // NO ACTIVE SYNERGIES",
                            color = Color.White.copy(alpha = 0.3f),
                            fontFamily = LEDFontFamily,
                            fontSize = 10.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { 
                                if (agents.size >= 2) onIgnite(agents[0].id, agents[1].id)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            modifier = Modifier.border(1.dp, Color(0xFF00E5FF), RectangleShape),
                            shape = RectangleShape
                        ) {
                            Text("IGNITE CATALYST FUSION", color = Color(0xFF00E5FF), fontFamily = LEDFontFamily)
                        }
                    }
                }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("ACTIVE SYNERGIES", color = Color(0xFF00E5FF), fontSize = 12.sp, fontWeight = FontWeight.Black, fontFamily = LEDFontFamily)
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(Modifier
                        .size(8.dp)
                        .background(Color.Green, RectangleShape))
                }
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(state.synergyBonuses) { bonus ->
                        SynergyRow(bonus)
                    }
                }
            }
        }
    }
}

@Composable
fun SynergyRow(bonus: SynergyBonus) {
    val infiniteTransition = rememberInfiniteTransition()
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "synergy_glow"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color(bonus.colorHex).copy(alpha = glowAlpha), RectangleShape)
            .border(1.dp, Color(bonus.colorHex).copy(alpha = 0.4f), RectangleShape)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(bonus.title.uppercase(), color = Color(bonus.colorHex), fontWeight = FontWeight.Black, fontSize = 14.sp, fontFamily = LEDFontFamily)
            Text(bonus.description.uppercase(), color = Color.White.copy(alpha = 0.7f), fontSize = 9.sp, letterSpacing = 1.sp, fontFamily = LEDFontFamily)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(bonus.value, color = Color(bonus.colorHex), fontWeight = FontWeight.Black, fontSize = 16.sp, fontFamily = LEDFontFamily)
            Text("SYNC ACTIVE", color = Color.Green.copy(alpha = 0.7f), fontSize = 8.sp, fontWeight = FontWeight.Bold, fontFamily = LEDFontFamily)
        }
    }
}

@Composable
fun StepChainingLiveGym(
    state: ChainState,
    onToggleGym: (String, String) -> Unit
) {
    NeonFrame(
        color = Color(0xFFB026FF),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("AUTONOMOUS PROFICIENCY LOOP", color = Color(0xFFB026FF), fontSize = 12.sp, fontWeight = FontWeight.Black, fontFamily = LEDFontFamily)
                if (state.isGymActive) {
                    Text("ACTIVE", color = Color.Green, fontSize = 10.sp, fontWeight = FontWeight.Black, fontFamily = LEDFontFamily)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .align(Alignment.Center)
                        .background(Color.White.copy(alpha = 0.1f))
                )

                if (state.isGymActive) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.CenterStart)
                            .offset(x = (250 * state.pingPongValue).dp)
                            .background(Color(0xFFB026FF), RectangleShape)
                            .border(1.dp, Color.White.copy(alpha = 0.5f), RectangleShape)
                    )
                }

                AgentMiniPortal(state.leftAgentId ?: "AURA", Modifier.align(Alignment.CenterStart))
                AgentMiniPortal(state.rightAgentId ?: "KAI", Modifier.align(Alignment.CenterEnd))

                Button(
                    onClick = { onToggleGym("aura", "kai") },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .border(1.dp, Color(0xFFB026FF).copy(alpha = 0.4f), RectangleShape),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RectangleShape,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        if (state.isGymActive) "STOP GYM" else "START GYM",
                        color = Color(0xFFB026FF),
                        fontSize = 10.sp,
                        fontFamily = LEDFontFamily
                    )
                }
            }
        }
    }
}

@Composable
fun CascadeGeminiMemoryCore(state: CascadeState) {
    val infiniteTransition = rememberInfiniteTransition(label = "memory_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    NeonFrame(
        color = Color(0xFF00FF85),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Column {
                Text("CONTEXT DEPTH: ${state.memoryContextDepth}", color = Color(0xFF00FF85), fontSize = 12.sp, fontWeight = FontWeight.Black, fontFamily = LEDFontFamily)
                Text("SYMMETRIC ENCRYPTION: ACTIVE", color = Color.White.copy(alpha = 0.5f), fontSize = 10.sp, fontFamily = LEDFontFamily)
            }

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.CenterEnd)
                    .drawBehind {
                        drawRect(
                            Color(0xFF00FF85).copy(alpha = pulseAlpha),
                            size = size * (0.5f + state.pulseStrength * 0.5f)
                        )
                        drawRect(
                            Color(0xFF00FF85),
                            size = size * 0.2f
                        )
                    }
            )
        }
    }
}

@Composable
fun AgentRegistryList(agents: List<LDOAgentEntity>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp), 
        modifier = Modifier.fillMaxSize()
    ) {
        items(agents) { agent ->
            AgentRegistryItem(agent)
        }
    }
}

@Composable
fun AgentRegistryItem(agent: LDOAgentEntity) {
    val color = Color(agent.colorHex)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.8f), RectangleShape)
            .border(1.dp, color.copy(alpha = 0.25f), RectangleShape)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Enhanced Avatar Container
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(color.copy(alpha = 0.05f))
                    .border(1.dp, color.copy(alpha = 0.4f), RectangleShape),
                contentAlignment = Alignment.Center
            ) {
                AsyncImageOrVideo(
                    mediaId = "catalyst_${agent.id.lowercase()}",
                    modifier = Modifier.fillMaxSize(),
                    alpha = 0.8f
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = agent.displayName.uppercase(), 
                    color = color, 
                    fontWeight = FontWeight.ExtraBold, 
                    fontSize = 15.sp, 
                    fontFamily = LEDFontFamily,
                    letterSpacing = 1.sp
                )
                Text(
                    text = agent.catalystTitle.uppercase(),
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 10.sp,
                    fontFamily = LEDFontFamily
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "PRIMARY: ${agent.primaryAbility.uppercase()}", 
                    color = Color.White.copy(alpha = 0.8f), 
                    fontSize = 9.sp, 
                    fontFamily = LEDFontFamily
                )
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "LVL ${agent.evolutionLevel}", 
                    color = Color.White, 
                    fontWeight = FontWeight.Black, 
                    fontSize = 13.sp, 
                    fontFamily = LEDFontFamily
                )
                Text(
                    text = "STABLE", 
                    color = Color(0xFF00FF41), 
                    fontSize = 9.sp, 
                    fontWeight = FontWeight.Bold, 
                    fontFamily = LEDFontFamily
                )
            }
        }
    }
}

@Composable
fun AgentMiniPortal(name: String, modifier: Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Black, RectangleShape)
                .border(1.dp, Color.White.copy(alpha = 0.2f), RectangleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(name.take(1).uppercase(), color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp, fontFamily = LEDFontFamily)
        }
        Text(name.uppercase(), color = Color.White.copy(alpha = 0.6f), fontSize = 8.sp, fontFamily = LEDFontFamily)
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        title.uppercase(),
        color = Color.White.copy(alpha = 0.6f),
        fontWeight = FontWeight.Black,
        fontSize = 12.sp,
        letterSpacing = 2.sp,
        fontFamily = LEDFontFamily,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}
