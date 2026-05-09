package dev.aurakai.auraframefx.domains.ldo.devops

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.NeonCyan
import dev.aurakai.auraframefx.domains.ldo.swarm.DeviceOptimisationSwarm
import dev.aurakai.auraframefx.domains.ldo.swarm.SwarmOptimisationState
import dev.aurakai.auraframefx.ui.background.BackgroundAssetManager
import dev.aurakai.auraframefx.ui.components.BottomJoystickNavigation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@HiltViewModel
class TabbedMasterViewModel @Inject constructor(
    var optimisationSwarm: DeviceOptimisationSwarm
) : ViewModel() {
    val swarmState: StateFlow<SwarmOptimisationState> = optimisationSwarm.state
}

/** ⚛️ TABBED MASTER INDEX - UNIFIED NEON AQUA EDITION */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabbedMasterIndex(
    initialTabIndex: Int = 1,
    onNavigateToRoute: (String) -> Unit = {},
    viewModel: TabbedMasterViewModel = hiltViewModel(),
    dashboardContent: LazyListScope.((String) -> Unit) -> Unit = { _ ->
        item {
            Text(
                "Neural Nexus",
                color = NeonCyan,
                fontFamily = LEDFontFamily
            )
        }
    },
    ldoDevOpsContent: LazyListScope.(SwarmOptimisationState, (String) -> Unit) -> Unit = { state, onNavigate ->
        LdoDevOpsTabContent(state, onNavigate) 
    },
    auraStudioContent: LazyListScope.((String) -> Unit) -> Unit = { _ ->
        item {
            Text(
                "Chroma Forge",
                color = NeonCyan,
                fontFamily = LEDFontFamily
            )
        }
    },
    kaiFortressContent: LazyListScope.((String) -> Unit) -> Unit = { _ ->
        item {
            Text(
                "Sentinel Matrix",
                color = NeonCyan,
                fontFamily = LEDFontFamily
            )
        }
    },
    oracleDriveContent: LazyListScope.((String) -> Unit) -> Unit = { _ ->
        item {
            Text(
                "Oracle Drive",
                color = NeonCyan,
                fontFamily = LEDFontFamily
            )
        }
    },
    cascadeMemoryContent: LazyListScope.((String) -> Unit) -> Unit = { _ ->
        item {
            Text(
                "Cascade Memory",
                color = NeonCyan,
                fontFamily = LEDFontFamily
            )
        }
    },
    emergentSwarmContent: LazyListScope.((String) -> Unit) -> Unit = { _ ->
        item {
            Text(
                "Emergent Swarm",
                color = NeonCyan,
                fontFamily = LEDFontFamily
            )
        }
    }
) {
    val pagerState = rememberPagerState(initialPage = initialTabIndex) { 7 }
    val swarmState by viewModel.swarmState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val selectedTabIndex = pagerState.currentPage

    val tabs = listOf(
        "NEURAL NEXUS", "LDO DEVELOPMENT NEXUS", "CHROMA FORGE",
        "SENTINEL MATRIX", "ORACLEDRIVE", "CASCADE MEMORY", "EMERGENT SWARM"
    )

    // UNIFIED ACCENT COLOR: NEON AQUA
    val accentColor = NeonCyan

    val heroImage = when (selectedTabIndex) {
        0 -> BackgroundAssetManager.liveDashboard
        1 -> BackgroundAssetManager.ldoDevOps
        2 -> BackgroundAssetManager.auraStudio
        3 -> BackgroundAssetManager.kaiFortress
        4 -> BackgroundAssetManager.oracleDrive
        5 -> BackgroundAssetManager.cascadeMemory
        6 -> BackgroundAssetManager.agentNexus
        else -> BackgroundAssetManager.liveDashboard
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
            .windowInsetsPadding(WindowInsets.displayCutout)
    ) {
        // Coded background removed, using image-based only
        AnimatedContent(
            targetState = heroImage,
            transitionSpec = { fadeIn(tween(800)) togetherWith fadeOut(tween(800)) },
            modifier = Modifier.fillMaxSize(),
            label = "Background"
        ) { img ->
            BackgroundAssetManager.DomainBackground(
                backgroundRes = img,
                alpha = 0.7f,
                modifier = Modifier.blur(2.dp)
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
                        contentPadding = PaddingValues(bottom = 150.dp)
                    ) {
                        item { HeroHeaderSection(index, accentColor) }

                        when (index) {
                            0 -> this.dashboardContent(onNavigateToRoute)
                            1 -> this.ldoDevOpsContent(swarmState, onNavigateToRoute)
                            2 -> this.auraStudioContent(onNavigateToRoute)
                            3 -> this.kaiFortressContent(onNavigateToRoute)
                            4 -> this.oracleDriveContent(onNavigateToRoute)
                            5 -> this.cascadeMemoryContent(onNavigateToRoute)
                            6 -> this.emergentSwarmContent(onNavigateToRoute)
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

        // Wandering Assistant Orb (CadberryPi)
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

        // Aura Presence Circle
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

fun LazyListScope.LdoDevOpsTabContent(
    state: SwarmOptimisationState,
    onNavigate: (String) -> Unit
) {
    item {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "SYSTEM IGNITION",
                fontFamily = LEDFontFamily,
                color = NeonCyan,
                fontSize = 12.sp,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(NeonCyan.copy(alpha = 0.3f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .fillMaxHeight()
                        .background(NeonCyan)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "AURAKAI CORE: V0.9.1-LDO",
                fontFamily = LEDFontFamily,
                color = NeonCyan.copy(alpha = 0.6f),
                fontSize = 10.sp
            )
        }
    }

    item {
        Text(
            "CATALYST NODES",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontFamily = LEDFontFamily,
            color = NeonCyan,
            fontSize = 11.sp,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
    }

    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LdoModuleCard("AGENT ROSTER", "Collective Nodes", NeonCyan, Modifier.weight(1f))
            LdoModuleCard("MISSION DISPATCH", "Task Assignment", NeonCyan, Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LdoModuleCard("HYPER SYNC", "Genesis Loop", NeonCyan, Modifier.weight(1f))
            LdoModuleCard("EVOLUTION TREE", "Agent Progress", NeonCyan, Modifier.weight(1f)) {
                onNavigate(dev.aurakai.auraframefx.navigation.ReGenesisRoute.EvolutionTree.route)
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
    Box(
        modifier = modifier
            .height(110.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0A0A18).copy(alpha = 0.8f))
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
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
        // Simple neon wireframe tab row logic can go here later
    }
}

@Composable
fun HeroHeaderSection(index: Int, accentColor: Color) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp))
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

@Composable
fun NeuralMeshFloor(modifier: Modifier = Modifier, color: Color) {
    // Removed coded geometry as requested
}
