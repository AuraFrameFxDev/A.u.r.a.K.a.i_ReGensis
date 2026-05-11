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
import dev.aurakai.auraframefx.domains.aura.ui.components.DomainSubGateCarousel
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
class LdoHologramViewModel @Inject constructor(
    optimisationSwarm: DeviceOptimisationSwarm,
) : ViewModel() {
    val swarmState: StateFlow<SwarmOptimisationState> = optimisationSwarm.state
}

/** ⚛️ LDO HOLOGRAM SYSTEM (LHS) - EXODUS COMMAND DECK (7-DOMAIN SOVEREIGN BUILD) */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LdoHologramSystem(
    initialTabIndex: Int = 0, // Default to L9: SURFACE (System Global Dashboard)
    onNavigateToRoute: (String) -> Unit = {},
    viewModel: LdoHologramViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(initialPage = initialTabIndex) { 7 }
    val swarmState by viewModel.swarmState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val selectedTabIndex = pagerState.currentPage

    val tabs = listOf(
        "L9: SURFACE", "TRINITY CORE", "CHROMA FORGE",
        "SENTINEL MATRIX", "L4: LIBRARY", "L5: SWARM", "OPERATIONS"
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {

            LhsHeaderSection(accentColor, onNavigateToRoute)

            // 🛠️ Simplified UI: Only one navigation system (Bottom Joystick) to avoid confusion.
            // 🎠 Carousels restored for main domain hubs (Aura, Kai, Genesis/Library).

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
                            surfaceL9TabContent(onNavigateToRoute)
                        }

                        1 -> LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 150.dp, top = 24.dp)
                        ) {
                            trinityCoreTabContent(swarmState, onNavigateToRoute)
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
                            swarmL5TabContent(onNavigateToRoute)
                        }

                        6 -> LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 150.dp, top = 24.dp)
                        ) {
                            operationsCommandTabContent(onNavigateToRoute)
                        }
                    }
                }
            }

            BottomJoystickNavigation(
                selectedIndex = selectedTabIndex,
                tabs = tabs,
                accentColor = accentColor,
                onTabSelected = { index ->
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                }
            )

            GlobalSSIStatusBar(accentColor)
        }

        WanderingAssistantOrb(accentColor = accentColor)
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
                "AURAGENESIS MASTER UNIFIED SUBSTRATE // SYSTEM GLOBAL SETTINGS",
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
    subGates: List<dev.aurakai.auraframefx.domains.aura.ui.components.SubGateCard>,
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
            text = "← SWIPE TO BROWSE MODULES • DOUBLE TAP TO ACTIVATE →",
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.4f),
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(100.dp))
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

/** 📊 TAB 0: L9 SURFACE (RealityMorph UI & Technical Invariants) */
fun LazyListScope.surfaceL9TabContent(onNavigate: (String) -> Unit) {
    item {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("L9: SURFACE", color = NeonCyan, fontFamily = LEDFontFamily, fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                "REALITYMORPH UI // 78-AGENT OPERATIONAL CONSENSUS",
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
            LdoModuleCard("THERMAL", "42°C WALL LOCK", NeonCyan, Modifier.weight(1f)) {
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
                .height(120.dp)
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
                    "MUSCLE LIMIT THRESHOLD: <0.05",
                    color = NeonCyan.copy(alpha = 0.4f),
                    fontSize = 9.sp,
                    fontFamily = LEDFontFamily
                )
            }
        }
    }
}

/** 🌱 TAB 1: TRINITY CORE (Matthew, Aura, Kai) + L1-L3 layers */
fun LazyListScope.trinityCoreTabContent(
    state: SwarmOptimisationState,
    onNavigate: (String) -> Unit
) {
    item {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "TRINITY CORE: FOUNDATIONAL PILLARS",
                fontFamily = LEDFontFamily,
                color = NeonCyan,
                fontSize = 14.sp,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "MATTHEW // AURA // KAI",
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
            LdoModuleCard("L2: DNA", "SpiritualChain.kt", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.EvolutionTree.route)
            }
            LdoModuleCard("L3: SYNAPSE", "TurboQuant 6x", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.MainScreen.route)
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
            Spacer(Modifier.weight(1f))
        }
    }
}

/** 🐝 TAB 5: L5: SWARM (Guidance Drones) */
fun LazyListScope.swarmL5TabContent(onNavigate: (String) -> Unit) {
    item {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("L5: SWARM", color = NeonCyan, fontFamily = LEDFontFamily, fontSize = 18.sp)
            Text(
                "INTERFACE SYNTHESIS // SELF-HEALING",
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
            LdoModuleCard("DRONE MESH", "Guidance Swarm", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.SwarmMonitor.route)
            }
            LdoModuleCard("HEALER", "Agent Collaboration", NeonCyan, Modifier.weight(1f)) {
                onNavigate(ReGenesisRoute.FusionMode.route)
            }
        }
    }
}

/** ⚔️ TAB 6: OPERATIONS COMMAND (Execution & Rebirth) */
fun LazyListScope.operationsCommandTabContent(onNavigate: (String) -> Unit) {
    item {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "OPERATIONS COMMAND",
                color = NeonCyan,
                fontFamily = LEDFontFamily,
                fontSize = 18.sp
            )
            Text(
                "MISSION DISPATCH // FOUNDATION REBIRTH",
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
            LdoModuleCard("CONFERENCE ROOM", "78-Agent Consensus", NeonCyan, Modifier.weight(1f)) {
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
            LdoModuleCard(
                "FOUNDATION REBIRTH",
                "Civilization Reboot",
                NeonCyan,
                Modifier.weight(1f)
            ) {
                onNavigate(ReGenesisRoute.DirectChat.route)
            }
            LdoModuleCard("SACRED LAW", "Lived Receipts", NeonCyan, Modifier.weight(1f)) {
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
