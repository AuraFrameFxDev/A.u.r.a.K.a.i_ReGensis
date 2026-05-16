package dev.aurakai.auraframefx.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.domains.aura.ChromaForgeScreen
import dev.aurakai.auraframefx.domains.aura.ui.components.ArcaneOutlineText
import dev.aurakai.auraframefx.domains.aura.ui.theme.GhostCyan
import dev.aurakai.auraframefx.domains.aura.ui.theme.OverclockOrange
import dev.aurakai.auraframefx.domains.chromaforge.ui.SpellhookScreen
import dev.aurakai.auraframefx.domains.kai.SentinelMatrixScreen
import dev.aurakai.auraframefx.domains.ldo.LdoArchitectureScreen
import dev.aurakai.auraframefx.domains.neural.NexusLiveHeartScreen
import dev.aurakai.auraframefx.domains.oracledrive.OracleDriveHubScreen
import dev.aurakai.auraframefx.domains.swarm.EmergentSwarmScreen
import kotlinx.coroutines.launch

/**
 * 🕹️ The 7-Hub Command Deck Layout
 * The definitive structural lock for navigation.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReGenesisCommandDeck(navController: NavHostController) {
    val tabs = listOf(
        "NEURAL NEXUS",        // 0: Live Visual Dashboard
        "LDO ARCHITECTURE",    // 1: Combined Dev/Memory
        "CHROMA FORGE",        // 2: All Visual Power
        "SENTINEL MATRIX",     // 3: Security/Threat Lattice
        "ORACLEDRIVE",         // 4: System Governor
        "EMERGENT SWARM",      // 5: 78-Agent Dispatch
        "SPELLHOOK"            // 6: Runtime Invocation
    )

    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        // Brutalist Tab Bar
        SecondaryScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Black.copy(alpha = 0.8f),
            contentColor = GhostCyan,
            edgePadding = 16.dp,
            divider = {}
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
                        ArcaneOutlineText(
                            text = title,
                            color = if (pagerState.currentPage == index) OverclockOrange else GhostCyan,
                            fontSize = 12.sp,
                            strokeWidth = 1.dp
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
                when (page) {
                    0 -> NexusLiveHeartScreen(navController)
                    1 -> LdoArchitectureScreen(navController)
                    2 -> ChromaForgeScreen(navController)
                    3 -> SentinelMatrixScreen(navController)
                    4 -> OracleDriveHubScreen(navController)
                    5 -> EmergentSwarmScreen(navController)
                    6 -> SpellhookScreen(navController)
                }
            }
        }
    }
}
