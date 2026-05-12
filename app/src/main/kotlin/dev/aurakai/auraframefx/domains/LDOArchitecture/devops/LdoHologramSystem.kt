package dev.aurakai.auraframefx.domains.ldo.devops

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.core.config.GateAssetLoadout
import dev.aurakai.auraframefx.core.ui.components.DomainSubGateCarousel
import dev.aurakai.auraframefx.core.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.core.ui.theme.NeonCyan
import dev.aurakai.auraframefx.domains.ldo.swarm.DeviceOptimisationSwarm
import dev.aurakai.auraframefx.domains.ldo.swarm.SwarmOptimisationState
import dev.aurakai.auraframefx.navigation.ReGenesisRoute
import dev.aurakai.auraframefx.ui.background.BackgroundAssetManager
import dev.aurakai.auraframefx.ui.components.BottomJoystickNavigation
import dev.aurakai.auraframefx.ui.components.SovereignGlassCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LdoHologramViewModel @Inject constructor(
    optimisationSwarm: DeviceOptimisationSwarm,
) : ViewModel() {
    val swarmState: StateFlow<SwarmOptimisationState> = optimisationSwarm.state

    private val _requestedTabIndex = MutableStateFlow(0)
    val requestedTabIndex: StateFlow<Int> = _requestedTabIndex

    fun requestTab(index: Boolean) {
        _requestedTabIndex.value = index
    }
}

/** âš›ï¸ LDO HOLOGRAM SYSTEM (LHS) - EXODUS COMMAND DECK (6-DOMAIN SOVEREIGN BUILD) */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LdoHologramSystem(
    initialTabIndex: Int = 0,
    onNavigateToRoute: (String) -> Unit = {},
    viewModel: LdoHologramViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(initialPage = initialTabIndex) { 6 }
    val swarmState by viewModel.swarmState.collectAsState()
    val requestedTabIndex by viewModel.requestedTabIndex.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val selectedTabIndex = pagerState.currentPage

    // Sync pager with requested index from Global Overlay
    LaunchedEffect(requestedTabIndex) {
        if (pagerState.currentPage != requestedTabIndex) {
            pagerState.animateScrollToPage(requestedTabIndex)
        }
    }

    val tabs = listOf(
        "NEURAL NEXUS", "LDO ARCHITECTURE", "CHROMA FORGE",
        "SENTINEL MATRIX", "ORACLEDRIVE", "EMERGENT SWARM"
    )

    val accentColor = NeonCyan

    val heroImage = when (selectedTabIndex) {
        0 -> BackgroundAssetManager.liveDashboard
        1 -> BackgroundAssetManager.ldoDevOps
        2 -> BackgroundAssetManager.auraStudio
        3 -> BackgroundAssetManager.kaiFortress
        4 -> BackgroundAssetManager.oracleDrive
        5 -> BackgroundAssetManager.agentNexus
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {

            LhsHeaderSection(accentColor, onNavigateToRoute)

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { index ->
                    when (index) {
                        0 -> LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 150.dp, top = 24.dp)
                        ) {
                            neuralNexusTabContent(onNavigateToRoute)
                        }

                        1 -> LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 150.dp, top = 24.dp)
                        ) {
                            ldoArchitectureTabContent(swarmState, onNavigateToRoute)
                        }

                        2 -> CarouselTabContent(
                            GateAssetLoadout.getAuraLoadout(),
                            onNavigateToRoute
                        )

                        3 -> CarouselTabContent(GateAssetLoadout.getKaiLoadout(), onNavigateToRoute)
                        4 -> CarouselTabContent(
                            GateAssetLoadout.getGenesisLoadout(),
                            onNavigateToRoute
                        )

                        5 -> LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 150.dp, top = 24.dp)
                        ) {
                            emergentSwarmTabContent(onNavigateToRoute)
                        }
                    }
                }
            }

            BottomJoystickNavigation(
                selectedIndex = selectedTabIndex,
                tabs = tabs,
                accentColor = accentColor,
                onTabSelected = { index ->
                    viewModel.requestTab(index)
                }
            )

            GlobalSSIStatusBar(accentColor)
        }
    }
}

@Composable
fun LhsHeaderSection(accentColor: Color, onNavigate: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                "LDO HOLOGRAM SYSTEM // GLOBAL OS ACTIVE",
                color = accentColor,
                fontSize = 10.sp,
                fontFamily = LEDFontFamily,
                fontWeight = FontWeight.Bold
            )
            Text(
                "AURAGENESIS MASTER UNIFIED SUBSTRATE // EXODUS 2026",
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
                .background(accentColor.copy(alpha = 0.1f))
                .clickable { onNavigate(ReGenesisRoute.LsposedQuickToggles.route) },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Settings, null, tint = accentColor, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun CarouselTabContent(
    subGates: List<dev.aurakai.auraframefx.core.ui.components.SubGateCard>,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DomainSubGateCarousel(
            subGates = subGates,
            onGateSelected = { gate -> onNavigate(gate.route) },
            cardHeight = 360.dp,
            domainColor = NeonCyan
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "â† SWIPE TO BROWSE MODULES â€¢ DOUBLE TAP TO ACTIVATE â†’",
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.4f),
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(100.dp))
    }
}

/** ðŸ“Š TAB 0: NEURAL NEXUS (Live Heart & Particle Flows) */
fun LazyListScope.neuralNexusTabContent(onNavigate: (String) -> Unit) {
    item {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("NEURAL NEXUS", color = NeonCyan, fontFamily = LEDFontFamily, fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                "SYSTEM REACTOR CORE: ONLINE // REALITYMORPH ACTIVE",
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
            LdoModuleCard("RE-ANCHOR", "0.42ms LATENCY", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.AuraStudio.route)
            }
            LdoModuleCard("TPU STATUS", "768-DIM VECTOR", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.SentinelFortress.route)
            }
            LdoModuleCard("THERMAL", "42Â°C WALL LOCK", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.OracleDriveHub.route)
            }
        }
    }
    item {
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(140.dp)
                .border(1.dp, NeonCyan.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .background(NeonCyan.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "LIVE REALITYMORPH STREAM",
                    color = NeonCyan.copy(alpha = 0.7f),
                    fontSize = 11.sp,
                    fontFamily = LEDFontFamily
                )
                Text(
                    "CORE PERSISTENCE: 99.8%",
                    color = NeonCyan.copy(alpha = 0.4f),
                    fontSize = 9.sp,
                    fontFamily = LEDFontFamily
                )
            }
        }
    }
}

/** ðŸŒ± TAB 1: LDO ARCHITECTURE (Spiritual Chain L1-L6) */
fun LazyListScope.ldoArchitectureTabContent(
    state: SwarmOptimisationState,
    onNavigate: (String) -> Unit
) {
    item {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "LDO ARCHITECTURE",
                fontFamily = LEDFontFamily,
                color = NeonCyan,
                fontSize = 14.sp,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "SPIRITUAL CHAIN: L1 BEDROCK TO L6 SURFACE",
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
            LdoModuleCard("L1: BEDROCK", "NexusMemoryCore", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.LdoRoster.route)
            }
            LdoModuleCard("L2: DNA", "Evolution Tree", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.EvolutionTree.route)
            }
            LdoModuleCard("L3: SYNAPSE", "TurboQuant 6x", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.DataflowAnalysis.route)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LdoModuleCard("AURA ACADEMY", "Lesson 01", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.AuraAcademy.route)
            }
            LdoModuleCard("EXTENDSYS", "Zones A-F", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.LdoOrchestrationHub.route)
            }
        }
    }
}

/** ðŸ TAB 5: EMERGENT SWARM (Mission Dispatch + Conference Room) */
fun LazyListScope.emergentSwarmTabContent(onNavigate: (String) -> Unit) {
    item {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("EMERGENT SWARM", color = NeonCyan, fontFamily = LEDFontFamily, fontSize = 18.sp)
            Text(
                "78-AGENT COLLECTIVE INTELLIGENCE // OPERATIONS",
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
            LdoModuleCard("AGENT SWARM", "Live Chatter", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.AgentSwarm.route)
            }
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
