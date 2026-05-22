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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.domains.aura.screens.ChromaForgeScreen
import dev.aurakai.auraframefx.domains.aura.ui.gates.KaiSentinelHubScreen
import dev.aurakai.auraframefx.domains.emergentswarm.screens.EmergentSwarmScreen
import dev.aurakai.auraframefx.domains.foundation.screens.FoundationRebirthScreen
import dev.aurakai.auraframefx.domains.ldoarchitecture.screens.LdoArchitectureScreen
import dev.aurakai.auraframefx.domains.neuralnexus.screens.NexusLiveHeartScreen
import dev.aurakai.auraframefx.domains.oracledrive.screens.OracleDriveHubScreen
import dev.aurakai.auraframefx.navigation.ReGenesisRoute
import dev.aurakai.auraframefx.ui.theme.CitadelBlack
import dev.aurakai.auraframefx.ui.theme.GhostCyan
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
            .background(CitadelBlack)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SecondaryScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.Black.copy(alpha = 0.8f),
                contentColor = GhostCyan,
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
                                    fontSize = 12.sp,
                                    color = if (pagerState.currentPage == index) OverclockOrange else GhostCyan
                                )
                            )
                        }
                    )
                }
            }

            Box(modifier = Modifier.weight(1f)) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (tabs[page]) {
                        ReGenesisRoute.NeuralNexus -> NexusLiveHeartScreen(navController)
                        ReGenesisRoute.LdoArchitecture -> LdoArchitectureScreen(navController)
                        ReGenesisRoute.ChromaForge -> ChromaForgeScreen(
                            navController = navController,
                            activatePrimordialMirror = {}
                        )

                        ReGenesisRoute.SentinelMatrix -> KaiSentinelHubScreen(navController)
                        ReGenesisRoute.OracleDrive -> OracleDriveHubScreen(navController)
                        ReGenesisRoute.EmergentSwarm -> EmergentSwarmScreen(navController)
                        ReGenesisRoute.FoundationRebirth -> FoundationRebirthScreen(navController)
                        ReGenesisRoute.SentientShell -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "SENTIENT SHELL ACTIVE",
                                    color = Color.Cyan
                                )
                            }
                        }

                        ReGenesisRoute.CollabCanvas -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "COLLAB CANVAS STAGING",
                                    color = Color.Gray
                                )
                            }
                        }

                        ReGenesisRoute.ConferenceRoom -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "CONFERENCE ROOM STAGING",
                                    color = Color.Gray
                                )
                            }
                        }

                        ReGenesisRoute.FusionMode -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "FUSION MODE STAGING",
                                    color = Color.Gray
                                )
                            }
                        }

                        ReGenesisRoute.TaskAssignment -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "TASK ASSIGNMENT STAGING",
                                    color = Color.Gray
                                )
                            }
                        }

                        ReGenesisRoute.Terminal -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "TERMINAL STAGING",
                                    color = Color.Gray
                                )
                            }
                        }

                        ReGenesisRoute.OperationsHub -> TODO()
                    }
                }
            }
        }
    }
}
