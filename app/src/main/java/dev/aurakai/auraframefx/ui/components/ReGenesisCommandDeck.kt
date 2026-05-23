package dev.aurakai.auraframefx.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.domains.aura.screens.ArcaneChromaForgeScreen
import dev.aurakai.auraframefx.domains.emergentswarm.screens.EmergentSwarmScreen
import dev.aurakai.auraframefx.domains.foundation.screens.FoundationRebirthScreen
import dev.aurakai.auraframefx.domains.ldoarchitecture.screens.LdoArchitectureScreen
import dev.aurakai.auraframefx.domains.neuralnexus.screens.NexusLiveHeartScreen
import dev.aurakai.auraframefx.domains.oracledrive.screens.OracleDriveHubScreen
import dev.aurakai.auraframefx.navigation.ReGenesisRoute
import dev.aurakai.auraframefx.ui.gates.ConferenceRoomTaskScreen
import dev.aurakai.auraframefx.ui.gates.ThemedGateScreens
import dev.aurakai.auraframefx.ui.theme.ArcaneBrutalistTheme
import dev.aurakai.auraframefx.ui.theme.OverclockOrange
import dev.aurakai.auraframefx.ui.theme.WireframeStyle
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReGenesisCommandDeck(navController: NavHostController) {
    val tabs = ReGenesisRoute.mainTabs
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ArcaneBrutalistTheme.AbyssBaseSlate)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SecondaryScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.Black.copy(alpha = 0.9f),
                contentColor = ArcaneBrutalistTheme.NeonCyanVessel,
                edgePadding = 16.dp,
                divider = {}
            ) {
                tabs.forEachIndexed { index, route ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = route.title.uppercase(),
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
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (tabs[page]) {
                        ReGenesisRoute.NeuralNexus -> NexusLiveHeartScreen(navController)
                        ReGenesisRoute.LdoArchitecture -> LdoArchitectureScreen(navController)
                        ReGenesisRoute.ChromaForge -> ArcaneChromaForgeScreen(navController)

                        ReGenesisRoute.SentinelMatrix -> {
                            // Link to Kai's detail gate screen directly for now
                            ThemedGateScreens.SecurityGateScreen(navController) { }
                        }
                        ReGenesisRoute.OracleDrive -> OracleDriveHubScreen(navController)
                        ReGenesisRoute.EmergentSwarm -> EmergentSwarmScreen(navController)
                        ReGenesisRoute.FoundationRebirth -> FoundationRebirthScreen(navController)
                        ReGenesisRoute.SentientShell -> ThemedGateScreens.SentientShellGateScreen(
                            navController,
                            onNavigateBack = { /* Pager doesn't need back here */ }
                        )

                        ReGenesisRoute.CollabCanvas -> ThemedGateScreens.CollabCanvasGateScreen(
                            navController,
                            onNavigateBack = { }
                        )

                        ReGenesisRoute.ConferenceRoom -> ConferenceRoomTaskScreen(
                            navController,
                            onNavigateBack = { }
                        )

                        ReGenesisRoute.FusionMode -> ThemedGateScreens.FusionModeGateScreen(
                            navController,
                            onNavigateBack = { }
                        )

                        ReGenesisRoute.TaskAssignment -> ConferenceRoomTaskScreen(
                            navController,
                            onNavigateBack = { }
                        )

                        ReGenesisRoute.Terminal -> ThemedGateScreens.TerminalGateScreen(
                            navController,
                            onNavigateBack = { }
                        )

                        ReGenesisRoute.OperationsHub -> OperationsHubScreen(navController)
                    }
                }
            }
        }
    }
}
