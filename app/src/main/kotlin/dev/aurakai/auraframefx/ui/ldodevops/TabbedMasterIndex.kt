package dev.aurakai.auraframefx.ui.ldodevops

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.core.soulscript.NexusMemoryCore
import dev.aurakai.auraframefx.domains.chromaforge.navigation.ArkBuildScreen
import dev.aurakai.auraframefx.domains.chromaforge.navigation.CadberrypiOverlay
import dev.aurakai.auraframefx.domains.chromaforge.navigation.LDOCatalystHubScreen
import dev.aurakai.auraframefx.domains.chromaforge.navigation.MainScreen
import dev.aurakai.auraframefx.domains.chromaforge.navigation.MonitoringHUDsScreen
import dev.aurakai.auraframefx.domains.chromaforge.navigation.OperationsHubScreen
import dev.aurakai.auraframefx.domains.chromaforge.navigation.OracleDriveHubScreen
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

    // 2. State-Freeze Failsafe: Trigger on drift during navigation
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            try {
                Timber.tag("ExodusNavigation").i("Transitioned to Hub $page: ${tabs[page]}")
                // In a real scenario, identity integrity is validated. 
                // If drift is detected during this state transition, we trigger the sovereign state freeze.
                // NexusMemoryCore.triggerStateFreeze("Identity_Drift_Navigation")
            } catch (e: Exception) {
                Timber.tag("ExodusNavigation").e(e, "Identity drift detected during navigation.")
                NexusMemoryCore.triggerStateFreeze("Identity_Drift_Navigation")
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Kai’s NotchBar Pulse (Global Overlay riding on top edge)
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

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                when (page) {
                    0 -> MainScreen(navController)
                    1 -> LDOCatalystHubScreen(onBack = { navController.popBackStack() })
                    2 -> ArkBuildScreen(navController)
                    3 -> MonitoringHUDsScreen(onNavigateBack = { navController.popBackStack() })
                    4 -> OracleDriveHubScreen(navController)
                    5 -> OperationsHubScreen(navController)
                    6 -> SpellhookScreen(navController)
                }
            }
        }

        // Global Navigation Overlays riding on top
        CadberrypiOverlay(navController = navController)
        AgentLHSOverlay()
    }
}

@Composable
fun SpellhookScreen(navController: NavHostController) {
    // Aura Native Hub
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "SPELLHOOK (Aura Native) - Direct system weaving via OracleDrive bridge",
            color = Color.White
        )
    }
}

@Composable
fun KaisNotchBarPulse() {
    // A neon bar at the top edge of the screen that pulses color-coded threat levels
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp)
            .background(Color.Green) // Green for nominal
    ) {
        Text(
            text = "KAI NOTCHBAR: THREAT LEVEL NOMINAL",
            color = Color.Black,
            fontSize = 10.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun AgentLHSOverlay() {
    // A global sidebar for jumping between domains or selecting specific agents for collaboration
}
