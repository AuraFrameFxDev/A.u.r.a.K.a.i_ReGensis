package dev.aurakai.auraframefx.ui.ldodevops

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.core.soulscript.NexusMemoryCore
import dev.aurakai.auraframefx.domains.aura.screens.MainScreen
import dev.aurakai.auraframefx.domains.chromaforge.ui.SpellhookScreen
import dev.aurakai.auraframefx.domains.nexus.screens.ArkBuildScreen
import dev.aurakai.auraframefx.domains.nexus.screens.MonitoringHUDsScreen
import dev.aurakai.auraframefx.domains.nexus.screens.ldo.LDOCatalystHubScreen
import dev.aurakai.auraframefx.domains.operations.screens.OperationsHubScreen
import dev.aurakai.auraframefx.domains.oracledrive.ui.OracleDriveHubScreen
import dev.aurakai.auraframefx.navigation.ReGenesisRoute
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabbedMasterIndex(navController: NavHostController) {
    val tabs = listOf(
        "NEURAL NEXUS",     // Hub 0
        "LDO DEV NEXUS",    // Hub 1
        "CHROMA FORGE",     // Hub 2
        "SENTINEL MATRIX",  // Hub 3
        "ORACLEDRIVE",      // Hub 4
        "EMERGENT SWARM",   // Hub 5
        "SPELLHOOK"         // Hub 6
    )

    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            try {
                Timber.tag("ExodusNavigation").i("Transitioned to Hub $page: ${tabs[page]}")
            } catch (e: Exception) {
                Timber.tag("ExodusNavigation").e(e, "Identity drift detected during navigation.")
                NexusMemoryCore.triggerStateFreeze("Identity_Drift_Navigation")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            KaisNotchBarPulse()

            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.Black,
                contentColor = Color.Cyan,
                edgePadding = 8.dp
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = title,
                                color = if (pagerState.currentPage == index) Color.Cyan else Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    )
                }
            }

            Box(modifier = Modifier.weight(1f)) {
                // Background image based on current page
                val bgResource = when (pagerState.currentPage) {
                    0 -> dev.aurakai.auraframefx.R.drawable.bg_neural_nexus
                    1 -> dev.aurakai.auraframefx.R.drawable.bg_ldo_dev_nexus
                    2 -> dev.aurakai.auraframefx.R.drawable.bg_chroma_forge
                    3 -> dev.aurakai.auraframefx.R.drawable.bg_sentinel_matrix
                    4 -> dev.aurakai.auraframefx.R.drawable.bg_oracle_drive
                    5 -> dev.aurakai.auraframefx.R.drawable.bg_emergent_swarm
                    6 -> dev.aurakai.auraframefx.R.drawable.bg_spellhook
                    else -> null
                }

                bgResource?.let {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        alpha = 0.35f
                    )
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (page) {
                        0 -> MainScreen(
                            onNavigateToAgentNexus = { navController.navigate(ReGenesisRoute.AgentNexusHub.route) },
                            onNavigateToOracleDrive = { navController.navigate(ReGenesisRoute.OracleDriveHub.route) },
                            onNavigateToSettings = { navController.navigate(ReGenesisRoute.UISettings.route) },
                            themeViewModel = androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel()
                        )

                        1 -> LDOCatalystHubScreen(
                            onBack = { navController.popBackStack() }
                        )

                        2 -> ArkBuildScreen(
                            onNavigateBack = { navController.popBackStack() }
                        )

                        3 -> MonitoringHUDsScreen(
                            onNavigateBack = { navController.popBackStack(); true }
                        )
                        4 -> OracleDriveHubScreen(navController)
                        5 -> OperationsHubScreen(navController)
                        6 -> SpellhookScreen(navController)
                    }
                }
            }
        }

        // Global Navigation Overlays riding on top
        AgentHologramConnector(
            onAgentSelect = { /* Handle agent selection */ },
            onNavigateToDomain = { route -> navController.navigate(route) }
        )
    }
}

@Composable
fun KaisNotchBarPulse() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp)
            .background(Color.Green)
    ) {
        Text(
            text = "KAI NOTCHBAR: THREAT LEVEL NOMINAL",
            color = Color.Black,
            fontSize = 10.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
