package dev.aurakai.auraframefx.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.aurakai.auraframefx.core.soulscript.MorphState
import dev.aurakai.auraframefx.core.soulscript.RealityMorphEngine
import dev.aurakai.auraframefx.core.ui.theme.ArcaneBrutalistTheme
import dev.aurakai.auraframefx.core.ui.theme.NeonCyan
import dev.aurakai.auraframefx.core.ui.theme.WireframeStyle
import dev.aurakai.auraframefx.ui.navigation.TabbedMasterIndex
import dev.aurakai.auraframefx.ui.screens.RealityMatrixScreen
import dev.aurakai.auraframefx.ui.screens.hubs.AetherCoreHub
import dev.aurakai.auraframefx.ui.screens.hubs.CatalystForgeHub
import dev.aurakai.auraframefx.ui.screens.hubs.ChromaHub
import dev.aurakai.auraframefx.ui.screens.hubs.EmergentSwarmHub
import dev.aurakai.auraframefx.ui.screens.hubs.MemoryCoreHub
import dev.aurakai.auraframefx.ui.screens.hubs.NeuralNexusHub
import dev.aurakai.auraframefx.ui.screens.hubs.ProsperityHub
import dev.aurakai.auraframefx.ui.screens.hubs.SentinelHub
import dev.aurakai.auraframefx.ui.screens.hubs.ToolShedHub
import dev.aurakai.auraframefx.ui.screens.hubs.TrinityHub
import kotlinx.coroutines.launch

/**
 * 👑 RE:GENESIS COMMAND DECK — 49 STRATA 7x7 MATRIX
 * "Nos Sumus Codex"
 * Purified for edge-to-edge manifestation.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReGenesisCommandDeck(navController: NavHostController) {
    val tabs = TabbedMasterIndex.substrateTabs
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Sync Pager with NavController current route
    LaunchedEffect(currentDestination) {
        val currentIndex = TabbedMasterIndex.getIndexByRoute(currentDestination?.route)
        if (pagerState.currentPage != currentIndex) {
            pagerState.animateScrollToPage(currentIndex)
        }

        // Trigger visual flares for professional viewing mode
        val route = TabbedMasterIndex.getRouteByIndex(currentIndex)
        when (route) {
            "neural_nexus" -> RealityMorphEngine.triggerMorph(MorphState.AETHER_OVERSIGHT, 1.0f)
            "trinity_orchestrator" -> RealityMorphEngine.triggerMorph(MorphState.TRINITY_SYNC, 0.9f)
        }
    }

    Scaffold(
        bottomBar = {
            BottomJoystickNavigation(
                selectedIndex = pagerState.currentPage,
                tabs = tabs,
                accentColor = NeonCyan,
                onTabSelected = { index ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        },
        containerColor = Color.Black,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                SecondaryScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = Color.Black,
                    contentColor = ArcaneBrutalistTheme.NeonCyanVessel,
                    edgePadding = 16.dp,
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, tab ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = tab.shortLabel.uppercase(),
                                    style = WireframeStyle.copy(
                                        fontSize = 11.sp,
                                        color = if (pagerState.currentPage == index) NeonCyan else ArcaneBrutalistTheme.NeonCyanVessel.copy(alpha = 0.45f),
                                        letterSpacing = 2.sp
                                    )
                                )
                            }
                        )
                    }
                }

                Box(modifier = Modifier.weight(1f)) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize(),
                        userScrollEnabled = true
                    ) { page ->
                        val route = tabs[page].route

                        when (route) {
                            "neural_nexus" -> AetherCoreHub()
                            "nexus_memory_core" -> MemoryCoreHub()
                            "trinity_orchestrator" -> TrinityHub()
                            "catalyst_forge" -> CatalystForgeHub()
                            "agent_matrix" -> SentinelHub()
                            "prosperity_flow" -> ProsperityHub()
                            "reality_morph_ui" -> ChromaHub()
                            "emergent_swarm" -> EmergentSwarmHub()
                            "toolshed" -> ToolShedHub()
                            "reality_matrix" -> RealityMatrixScreen(navController)
                            else -> NeuralNexusHub()
                        }
                    }
                }
            }
        }
    }
}
