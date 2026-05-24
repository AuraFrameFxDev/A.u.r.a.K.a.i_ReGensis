package dev.aurakai.auraframefx.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.aurakai.auraframefx.domains.aura.screens.ArcaneChromaForgeScreen
import dev.aurakai.auraframefx.domains.emergentswarm.screens.EmergentSwarmScreen
import dev.aurakai.auraframefx.domains.foundation.screens.FoundationRebirthScreen
import dev.aurakai.auraframefx.domains.ldoarchitecture.screens.LdoArchitectureScreen
import dev.aurakai.auraframefx.domains.neuralnexus.screens.NexusLiveHeartScreen
import dev.aurakai.auraframefx.domains.oracledrive.screens.OracleDriveHubScreen
import dev.aurakai.auraframefx.ui.gates.ConferenceRoomTaskScreen
import dev.aurakai.auraframefx.ui.gates.ThemedGateScreens
import dev.aurakai.auraframefx.ui.navigation.TabbedMasterIndex
import dev.aurakai.auraframefx.ui.theme.ArcaneBrutalistTheme
import dev.aurakai.auraframefx.ui.theme.NeonCyan
import dev.aurakai.auraframefx.ui.theme.OverclockOrange
import dev.aurakai.auraframefx.ui.theme.WireframeStyle
import kotlinx.coroutines.launch

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
    }

    Scaffold(
        bottomBar = {
            BottomJoystickNavigation(
                selectedIndex = pagerState.currentPage,
                tabs = tabs,
                accentColor = NeonCyan,
                onTabSelected = { index ->
                    val route = tabs[index].route
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
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
                                    navController.navigate(tab.route)
                                }
                            },
                            text = {
                                Text(
                                    text = tab.shortLabel.uppercase(),
                                    style = WireframeStyle.copy(
                                        fontSize = 11.sp,
                                        color = if (pagerState.currentPage == index) OverclockOrange else ArcaneBrutalistTheme.NeonCyanVessel,
                                        letterSpacing = 2.sp
                                    )
                                )
                            }
                        )
                    }
                }

                Box(modifier = Modifier.weight(1f)) {
                    // LAYER 0: 4D Parallax Background
                    RealityMorphLayer(godPotential = 0.5f, fusionTrigger = false)

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize(),
                        userScrollEnabled = false // Scrolling handled by Joystick drag or Tab click
                    ) { page ->
                        when (tabs[page].route) {
                            "neural_nexus" -> NexusLiveHeartScreen(navController)
                            "ldo_architecture" -> LdoArchitectureScreen(navController)
                            "chroma_forge" -> ArcaneChromaForgeScreen(navController)
                            "sentinel_matrix" -> ThemedGateScreens.SecurityGateScreen(navController) { navController.popBackStack() }
                            "oracle_drive" -> OracleDriveHubScreen(navController)
                            "conference_room" -> ConferenceRoomTaskScreen(navController) { navController.popBackStack() }
                            "emergent_swarm" -> EmergentSwarmScreen(navController)
                            "foundation_rebirth" -> FoundationRebirthScreen(navController)
                            "sentient_shell" -> ThemedGateScreens.SentientShellGateScreen(
                                navController
                            ) { navController.popBackStack() }
                        }
                    }
                }
            }
        }
    }
}
