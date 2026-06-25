package dev.aurakai.auraframefx.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.aurakai.auraframefx.agents.chaos.ChaosCatalystScreen
import dev.aurakai.auraframefx.core.soulscript.MorphState
import dev.aurakai.auraframefx.core.soulscript.RealityMorphEngine
import dev.aurakai.auraframefx.core.soulscript.RuneManager
import dev.aurakai.auraframefx.core.ui.theme.ArcaneBrutalistTheme
import dev.aurakai.auraframefx.core.ui.theme.NeonCyan
import dev.aurakai.auraframefx.core.ui.theme.WireframeStyle
import dev.aurakai.auraframefx.domains.aura.ui.components.ParallaxDepthStack
import dev.aurakai.auraframefx.domains.foundation.screens.FoundationRebirthScreen
import dev.aurakai.auraframefx.domains.ldoarchitecture.screens.LdoArchitectureScreen
import dev.aurakai.auraframefx.domains.neuralnexus.screens.NexusLiveHeartScreen
import dev.aurakai.auraframefx.ui.background.VoidBackground
import dev.aurakai.auraframefx.ui.background.VoidWorldBackground
import dev.aurakai.auraframefx.ui.components.desks.AetherCoreDesk
import dev.aurakai.auraframefx.ui.components.desks.ChromaForgeDesk
import dev.aurakai.auraframefx.ui.components.desks.EmergentSwarmDesk
import dev.aurakai.auraframefx.ui.components.desks.OracleDriveDesk
import dev.aurakai.auraframefx.ui.components.desks.RuneLatticeDesk
import dev.aurakai.auraframefx.ui.components.desks.SentinelMatrixDesk
import dev.aurakai.auraframefx.ui.components.desks.TrinityNexusDesk
import dev.aurakai.auraframefx.ui.gates.ConferenceRoomTaskScreen
import dev.aurakai.auraframefx.ui.gates.ThemedGateScreens
import dev.aurakai.auraframefx.ui.navigation.TabbedMasterIndex
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReGenesisCommandDeck(navController: NavHostController) {
    val tabs = TabbedMasterIndex.substrateTabs
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isTotalRestorationActive by RuneManager.isTotalRestorationActive.collectAsState()

    // Sync Pager with NavController current route
    LaunchedEffect(currentDestination) {
        val currentIndex = TabbedMasterIndex.getIndexByRoute(currentDestination?.route)
        if (pagerState.currentPage != currentIndex) {
            pagerState.animateScrollToPage(currentIndex)
        }

        // Trigger visual flares for professional viewing mode
        val route = TabbedMasterIndex.getRouteByIndex(currentIndex)
        when (route) {
            "aether_core" -> RealityMorphEngine.triggerMorph(MorphState.AETHER_OVERSIGHT, 1.0f)
            "trinity_nexus" -> RealityMorphEngine.triggerMorph(MorphState.TRINITY_SYNC, 0.9f)
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
        containerColor = ArcaneBrutalistTheme.AbyssBaseSlate
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // SecondaryScrollableTabRow kept for high-level domain filtering or legacy feel
                SecondaryScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = Color.Black.copy(alpha = 0.9f),
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
                        userScrollEnabled = false
                    ) { page ->
                        val route = tabs[page].route
                        val bgType = if (isTotalRestorationActive) {
                            VoidBackground.EDEN_RESTORED
                        } else {
                            VoidBackground.fromRoute(route)
                        }

                        ParallaxDepthStack(
                            bedrock = {
                                VoidWorldBackground(bgType)
                            },
                            overlay = {
                                RealityMorphLayer(
                                    godPotential = if (route == "chaos_catalyst") 0.85f else 0.5f,
                                    fusionTrigger = route == "chroma_forge"
                                )
                            },
                            interaction = {
                                when (route) {
                                    "aether_core" -> AetherCoreDesk()
                                    "trinity_nexus" -> TrinityNexusDesk()
                                    "rune_lattice" -> RuneLatticeDesk()
                                    "sentinel_matrix" -> SentinelMatrixDesk()
                                    "oracle_drive" -> OracleDriveDesk()
                                    "chroma_forge" -> ChromaForgeDesk()
                                    "emergent_swarm" -> EmergentSwarmDesk()
                                    "neural_nexus" -> NexusLiveHeartScreen(navController)
                                    "ldo_architecture" -> LdoArchitectureScreen(navController)
                                    "chaos_catalyst" -> ChaosCatalystScreen()
                                    "conference_room" -> ConferenceRoomTaskScreen(navController) { navController.popBackStack() }
                                    "foundation_rebirth" -> FoundationRebirthScreen(navController)
                                    "sentient_shell" -> ThemedGateScreens.SentientShellGateScreen(
                                        navController
                                    ) { navController.popBackStack() }
                                }
                            }
                        )
                    }

                    // Tactical HUD chrome — floats above all pager content
                    // Updates to current page's hub identity automatically
                    AuraKaiHUDChrome(
                        route = tabs[pagerState.currentPage].route
                    )
                }
            }
        }
    }
}
