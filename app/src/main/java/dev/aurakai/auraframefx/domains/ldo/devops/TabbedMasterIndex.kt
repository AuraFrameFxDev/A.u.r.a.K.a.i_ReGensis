package dev.aurakai.auraframefx.domains.ldo.devops

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.domains.ldo.swarm.DeviceOptimisationSwarm
import dev.aurakai.auraframefx.domains.ldo.swarm.SwarmOptimisationState
import dev.aurakai.auraframefx.ui.background.BackgroundAssetManager
import dev.aurakai.auraframefx.ui.components.BottomJoystickNavigation
import dev.aurakai.auraframefx.ui.components.NeonWireframeBackground
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TabbedMasterViewModel @Inject constructor(
    var optimisationSwarm: DeviceOptimisationSwarm
) : ViewModel() {
    val swarmState: StateFlow<SwarmOptimisationState> = optimisationSwarm.state
}

/** ⚛️ TABBED MASTER INDEX - RE:GENESIS EXODUS EDITION */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabbedMasterIndex(
    initialTabIndex: Int = 1,
    onNavigateToRoute: (String) -> Unit = {},
    viewModel: TabbedMasterViewModel = hiltViewModel(),
    dashboardContent: LazyListScope.((String) -> Unit) -> Unit = { onNavigate ->
        item {
            Text(
                "Neural Nexus dashboardContent not implemented",
                color = Color.White
            )
        }
    },
    ldoDevOpsContent: LazyListScope.(SwarmOptimisationState, (String) -> Unit) -> Unit = { _, _ ->
        item {
            Text("LDO Development Nexus ldoDevOpsContent not implemented", color = Color.White)
        }
    },
    auraStudioContent: LazyListScope.((String) -> Unit) -> Unit = { onNavigate ->
        item {
            Text(
                "Chroma Forge auraStudioContent not implemented",
                color = Color.White
            )
        }
    },
    kaiFortressContent: LazyListScope.((String) -> Unit) -> Unit = { onNavigate ->
        item {
            Text(
                "Sentinel Matrix kaiFortressContent not implemented",
                color = Color.White
            )
        }
    },
    oracleDriveContent: LazyListScope.((String) -> Unit) -> Unit = { onNavigate ->
        item {
            Text(
                "Oracle Drive oracleDriveContent not implemented",
                color = Color.White
            )
        }
    },
    cascadeMemoryContent: LazyListScope.((String) -> Unit) -> Unit = { onNavigate ->
        item {
            Text(
                "Cascade Memory cascadeMemoryContent not implemented",
                color = Color.White
            )
        }
    },
    emergentSwarmContent: LazyListScope.((String) -> Unit) -> Unit = { onNavigate ->
        item {
            Text(
                "Emergent Swarm emergentSwarmContent not implemented",
                color = Color.White
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

    val accentColor = when (selectedTabIndex) {
        0 -> Color(0xFFFFD700)
        1 -> Color(0xFF00E5FF)
        2 -> Color(0xFFFF00FF)
        3 -> Color(0xFF00FF88)
        4 -> Color(0xFFFFAA00)
        5 -> Color(0xFF8B5CF6)
        6 -> Color(0xFF00D6FF)
        else -> Color(0xFFFFD700)
    }

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
        NeonWireframeBackground(accentColor = accentColor, modifier = Modifier.fillMaxSize())

        AnimatedContent(
            targetState = heroImage,
            transitionSpec = { fadeIn(tween(800)) togetherWith fadeOut(tween(800)) },
            modifier = Modifier.fillMaxSize(),
            label = "Background"
        ) { img ->
            BackgroundAssetManager.DomainBackground(
                backgroundRes = img,
                modifier = Modifier
                    .alpha(0.3f)
                    .blur(4.dp)
            )
        }

        NeuralMeshFloor(modifier = Modifier.align(Alignment.BottomCenter), color = accentColor)

        Column(modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()) {
            MasterStatusStrip(accentColor)

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

        AssistantOrb(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 116.dp, end = 16.dp),
            accentColor = accentColor
        )
    }
}

@Composable
fun NeuralMeshFloor(modifier: Modifier = Modifier, color: Color) {
    Box(modifier = modifier)
}

@Composable
fun MasterStatusStrip(accentColor: Color) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(40.dp))
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
        .height(48.dp))
}

@Composable
fun HeroHeaderSection(index: Int, accentColor: Color) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp))
}

@Composable
fun GlobalSSIStatusBar(accentColor: Color) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(20.dp))
}

@Composable
fun AssistantOrb(modifier: Modifier = Modifier, accentColor: Color) {
    Box(modifier = modifier.size(50.dp))
}
