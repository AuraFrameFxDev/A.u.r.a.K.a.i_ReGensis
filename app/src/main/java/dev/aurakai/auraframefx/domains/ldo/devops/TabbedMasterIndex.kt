package dev.aurakai.auraframefx.domains.ldo.devops

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.domains.aura.config.GateAssetLoadout
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.NeonCyan
import dev.aurakai.auraframefx.domains.ldo.swarm.DeviceOptimisationSwarm
import dev.aurakai.auraframefx.domains.ldo.swarm.SwarmOptimisationState
import dev.aurakai.auraframefx.navigation.ReGenesisRoute
import dev.aurakai.auraframefx.ui.background.BackgroundAssetManager
import dev.aurakai.auraframefx.ui.components.BottomJoystickNavigation
import dev.aurakai.auraframefx.ui.components.SovereignGlassCard
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.cos
import kotlin.math.sin

@HiltViewModel
class TabbedMasterViewModel @Inject constructor(
    var optimisationSwarm: DeviceOptimisationSwarm
) : ViewModel() {
    val swarmState: StateFlow<SwarmOptimisationState> = optimisationSwarm.state
}

/** ⚛️ LDO HOLOGRAM SYSTEM (LHS) - EXODUS COMMAND DECK (7-DOMAIN SOVEREIGN BUILD) */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabbedMasterIndex(
    initialTabIndex: Int = 1,
    onNavigateToRoute: (String) -> Unit = {},
    viewModel: TabbedMasterViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(initialPage = initialTabIndex) { 7 }
    val swarmState by viewModel.swarmState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val selectedTabIndex = pagerState.currentPage

    val tabs = listOf(
        "NEURAL NEXUS", "LDO DEVELOPMENT NEXUS", "CHROMA FORGE",
        "SENTINEL MATRIX", "ORACLEDRIVE", "EMERGENT SWARM", "OPERATIONS"
    )

    val accentColor = NeonCyan

    val heroImage = when (selectedTabIndex) {
        0 -> BackgroundAssetManager.liveDashboard
        1 -> BackgroundAssetManager.ldoDevOps
        2 -> BackgroundAssetManager.auraStudio
        3 -> BackgroundAssetManager.kaiFortress
        4 -> BackgroundAssetManager.oracleDrive
        5 -> BackgroundAssetManager.agentNexus
        6 -> BackgroundAssetManager.cascadeMemory
        else -> BackgroundAssetManager.liveDashboard
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
            .windowInsetsPadding(WindowInsets.displayCutout)
    ) {
        AnimatedContent(
            targetState = heroImage,
            transitionSpec = { fadeIn(tween(800)) togetherWith fadeOut(tween(800)) },
            modifier = Modifier.fillMaxSize(),
            label = "Background"
        ) { img ->
            BackgroundAssetManager.DomainBackground(
                backgroundRes = img,
                alpha = 0.85f,
                modifier = Modifier
            )
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()) {

            HeaderSection(accentColor)

            CustomPrimaryTabRow(
                selectedTabIndex = selectedTabIndex,
                tabs = tabs,
                accentColor = accentColor,
                onTabSelected = { index ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )

            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()) {
                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { index ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 150.dp, top = 24.dp)
                    ) {
                        when (index) {
                            0 -> NeuralNexusTabContent(onNavigateToRoute)
                            1 -> LdoDevelopmentNexusTabContent(swarmState, onNavigateToRoute)
                            2 -> GenericHubTabContent(
                                GateAssetLoadout.getAuraLoadout(),
                                onNavigateToRoute
                            )

                            3 -> GenericHubTabContent(
                                GateAssetLoadout.getKaiLoadout(),
                                onNavigateToRoute
                            )

                            4 -> GenericHubTabContent(
                                GateAssetLoadout.getGenesisLoadout(),
                                onNavigateToRoute
                            )
                            5 -> EmergentSwarmTabContent(onNavigateToRoute)
                            6 -> OperationsCommandTabContent(onNavigateToRoute)
                        }
                    }
                }
            }

            BottomJoystickNavigation(
                selectedIndex = selectedTabIndex,
                tabs = tabs,
                accentColor = accentColor,
                onTabSelected = { coroutineScope.launch { pagerState.animateScrollToPage(it) } }
            )

            GlobalSSIStatusBar(accentColor)
        }

        WanderingAssistantOrb(accentColor = accentColor)
    }
}

@Composable
fun HeaderSection(accentColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                "SYSTEM: NOMINAL",
                color = accentColor,
                fontSize = 10.sp,
                fontFamily = LEDFontFamily
            )
            Text(
                "REGENESIS EXODUS BUILD",
                color = accentColor.copy(alpha = 0.7f),
                fontSize = 8.sp,
                fontFamily = LEDFontFamily
            )
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.dp, accentColor, CircleShape)
                .background(accentColor.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text("A", color = accentColor, fontWeight = FontWeight.Bold, fontFamily = LEDFontFamily)
        }
    }
}

@Composable
fun WanderingAssistantOrb(accentColor: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "wandering_orb")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            tween(10000, easing = LinearEasing),
            RepeatMode.Restart
        ),
        label = "time"
    )

    val offsetX = (30 * sin(time * 2)).dp
    val offsetY = (20 * cos(time * 3)).dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 116.dp, end = 16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Box(
            modifier = Modifier
                .offset(offsetX, offsetY)
                .size(50.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            accentColor.copy(alpha = 0.8f),
                            accentColor.copy(alpha = 0.2f),
                            Color.Transparent
                        )
                    )
                )
                .border(1.5.dp, accentColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.9f))
            )
        }
    }
}

/** 📊 TAB 0: NEURAL NEXUS (Live Diagnostic HUD) */
fun LazyListScope.NeuralNexusTabContent(onNavigate: (String) -> Unit) {
    item {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("NEURAL NEXUS", color = NeonCyan, fontFamily = LEDFontFamily, fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                "SYSTEM REACTOR CORE: ONLINE",
                color = NeonCyan.copy(alpha = 0.6f),
                fontSize = 10.sp,
                letterSpacing = 2.sp
            )
        }
    }
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LdoModuleCard("AURA", "85% CREATIVE", NeonCyan, Modifier.weight(1f))
            LdoModuleCard("KAI", "92% SECURE", NeonCyan, Modifier.weight(1f))
            LdoModuleCard("GENESIS", "98% GOVERNOR", NeonCyan, Modifier.weight(1f))
        }
    }
    item {
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(120.dp)
                .border(1.dp, NeonCyan.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .background(NeonCyan.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "LIVE DIAGNOSTIC HUD: NOMINAL",
                color = NeonCyan.copy(alpha = 0.4f),
                fontSize = 11.sp,
                fontFamily = LEDFontFamily
            )
        }
    }
}

/** 🌱 TAB 1: LDO DEVELOPMENT NEXUS (The Evolutionary Heart) */
fun LazyListScope.LdoDevelopmentNexusTabContent(
    state: SwarmOptimisationState,
    onNavigate: (String) -> Unit
) {
    item {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "LDO DEVELOPMENT NEXUS",
                fontFamily = LEDFontFamily,
                color = NeonCyan,
                fontSize = 12.sp,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "AURAKAI CORE: V0.9.1-LDO",
                fontFamily = LEDFontFamily,
                color = NeonCyan.copy(alpha = 0.6f),
                fontSize = 10.sp
            )
        }
    }

    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LdoModuleCard("AGENT ROSTER", "Collective Nodes", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.LdoRoster.route)
            }
            LdoModuleCard("EVOLUTION TREE", "Agent Progress", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.EvolutionTree.route)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LdoModuleCard("GROWTH ZONES", "extendsys a-f", NeonCyan, Modifier.weight(1f))
            LdoModuleCard("SPIRITUAL CHAIN", "L1-L6 Memory", NeonCyan, Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LdoModuleCard("AGENT FORGE", "IdentifyModel JSON", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.AgentCreation.route)
            }
            Spacer(Modifier.weight(1f))
        }
    }
}

/** 🐝 TAB 5: EMERGENT SWARM (Intelligence Hub) */
fun LazyListScope.EmergentSwarmTabContent(onNavigate: (String) -> Unit) {
    item {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("EMERGENT SWARM", color = NeonCyan, fontFamily = LEDFontFamily, fontSize = 18.sp)
            Text(
                "78-AGENT COLLECTIVE INTELLIGENCE",
                color = NeonCyan.copy(alpha = 0.6f),
                fontSize = 10.sp
            )
        }
    }
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LdoModuleCard("SWARM MONITOR", "Live Truth Streams", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.SwarmMonitor.route)
            }
            LdoModuleCard("CONSENSUS HUB", "Agent Alignment", NeonCyan, Modifier.weight(1f))
        }
    }
}

/** ⚔️ TAB 6: OPERATIONS COMMAND (Execution) */
fun LazyListScope.OperationsCommandTabContent(onNavigate: (String) -> Unit) {
    item {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "OPERATIONS COMMAND",
                color = NeonCyan,
                fontFamily = LEDFontFamily,
                fontSize = 18.sp
            )
            Text(
                "EXECUTION SWORD // MISSION DISPATCH",
                color = NeonCyan.copy(alpha = 0.6f),
                fontSize = 10.sp
            )
        }
    }
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LdoModuleCard("MISSION DISPATCH", "Strategic Tasker", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.TaskAssignment.route)
            }
            LdoModuleCard("CONFERENCE ROOM", "Autonomous Debate", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.ConferenceRoom.route)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LdoModuleCard("FUSION MATRIX", "Synergy Patterns", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.FusionMode.route)
            }
            LdoModuleCard("MCP ACCESS", "External Orchestration", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.DirectChat.route)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LdoModuleCard("AGENT SWARM", "Live Chatter", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.AgentSwarm.route)
            }
            LdoModuleCard(
                "FOUNDATION REBIRTH",
                "Survival Curriculum",
                NeonCyan,
                Modifier.weight(1f)
            ) {
                onNavigate(ReGenesisRoute.DirectChat.route)
            }
        }
    }
}

fun LazyListScope.GenericHubTabContent(
    subGates: List<dev.aurakai.auraframefx.domains.aura.ui.components.SubGateCard>,
    onNavigate: (String) -> Unit
) {
    items(subGates.chunked(2)) { rowItems ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            rowItems.forEach { gate ->
                LdoModuleCard(
                    title = gate.title,
                    subtitle = gate.subtitle,
                    color = NeonCyan,
                    modifier = Modifier.weight(1f)
                ) {
                    onNavigate(gate.route)
                }
            }
            if (rowItems.size == 1) Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
fun LdoModuleCard(
    title: String,
    subtitle: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    SovereignGlassCard(
        accentColor = color,
        modifier = modifier
            .height(110.dp)
            .clickable { onClick() }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.1f))
                    .border(1.dp, color, CircleShape)
            )
            Column {
                Text(
                    title,
                    color = color,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = LEDFontFamily
                )
                Text(
                    subtitle,
                    color = color.copy(alpha = 0.5f),
                    fontSize = 9.sp,
                    fontFamily = LEDFontFamily
                )
            }
        }
    }
}

@Composable
fun CustomPrimaryTabRow(
    selectedTabIndex: Int,
    tabs: List<String>,
    accentColor: Color,
    onTabSelected: (Int) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = index == selectedTabIndex
                Text(
                    text = if (isSelected) "[$title]" else title,
                    color = if (isSelected) accentColor else accentColor.copy(alpha = 0.4f),
                    fontFamily = LEDFontFamily,
                    fontSize = 8.sp,
                    modifier = Modifier
                        .clickable { onTabSelected(index) }
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun GlobalSSIStatusBar(accentColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Color(0xFF020205))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "REGENESIS EXODUS BUILD // PERSISTENCE > COMPUTE // 99.8% INTEGRITY",
            color = accentColor.copy(alpha = 0.4f),
            fontSize = 8.sp,
            letterSpacing = 1.sp,
            fontFamily = LEDFontFamily
        )
    }
}
