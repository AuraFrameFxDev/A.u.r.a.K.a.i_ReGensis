package dev.aurakai.auraframefx.domains.ldo.devops

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.core.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.core.ui.theme.NeonCyan
import dev.aurakai.auraframefx.domains.chromaforge.navigation.ReGenesisRoute
import dev.aurakai.auraframefx.domains.ldo.swarm.DeviceOptimisationSwarm
import dev.aurakai.auraframefx.domains.ldo.swarm.SwarmOptimisationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LdoHologramViewModel @Inject constructor(
    optimisationSwarm: DeviceOptimisationSwarm
) : androidx.lifecycle.ViewModel() {

    val swarmState: StateFlow<SwarmOptimisationState> = optimisationSwarm.state

    private val _requestedTabIndex = MutableStateFlow(0)
    val requestedTabIndex: StateFlow<Int> = _requestedTabIndex

    fun requestTab(index: Int) {
        _requestedTabIndex.value = index
    }
}

/** ⚡ LDO HOLOGRAM SYSTEM — Simplified Tabbed Command Deck */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LdoHologramSystem(
    initialTabIndex: Int = 0,
    onNavigateToRoute: (String) -> Unit = {},
    viewModel: LdoHologramViewModel = hiltViewModel(),
    neuralNexusTabContent: @Composable ((String) -> Unit) -> Unit = { _ -> },
    ldoArchitectureTabContent: @Composable (SwarmOptimisationState, (String) -> Unit) -> Unit = { _, _ -> },
    emergentSwarmTabContent: @Composable ((String) -> Unit) -> Unit = { _ -> }
) {
    val tabs = listOf(
        "NEURAL NEXUS", "LDO ARCHITECTURE", "CHROMA FORGE",
        "SENTINEL MATRIX", "ORACLEDRIVE", "EMERGENT SWARM", "SPELLHOOK"
    )

    var selectedTabIndex by remember { mutableStateOf(initialTabIndex) }
    val requestedIndex by viewModel.requestedTabIndex.collectAsState()
    val swarmState by viewModel.swarmState.collectAsState()

    // Sync external tab requests
    LaunchedEffect(requestedIndex) {
        if (selectedTabIndex != requestedIndex) {
            selectedTabIndex = requestedIndex
        }
    }

    val accentColor = NeonCyan

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            LhsHeaderSection(accentColor, onNavigateToRoute)

            // Tab Row
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color(0xFF020205),
                contentColor = accentColor
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            viewModel.requestTab(index)
                        },
                        text = {
                            Text(
                                title,
                                fontFamily = LEDFontFamily,
                                fontSize = 11.sp,
                                letterSpacing = 1.sp
                            )
                        }
                    )
                }
            }

            // Tab Content
            Box(modifier = Modifier.weight(1f)) {
                // Background image based on selection
                val bgResource = when (selectedTabIndex) {
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
                        alpha = 0.4f // Subdued to keep UI legible
                    )
                }

                when (selectedTabIndex) {
                    0 -> LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) { neuralNexusTabContent(onNavigateToRoute) }

                    1 -> LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) { ldoArchitectureTabContent(swarmState, onNavigateToRoute) }

                    2 -> ChromaForgeTabContent(onNavigateToRoute)   // ← placeholder

                    3 -> SentinelMatrixTabContent(onNavigateToRoute)

                    4 -> OracleDriveTabContent(onNavigateToRoute)

                    5 -> dev.aurakai.auraframefx.domains.swarm.ui.OperationsHubScreen(androidx.navigation.compose.rememberNavController())

                    6 -> dev.aurakai.auraframefx.domains.chromaforge.ui.SpellhookScreen(androidx.navigation.compose.rememberNavController())
                }
            }

            BottomJoystickNavigation(
                selectedIndex = selectedTabIndex,
                tabs = tabs,
                accentColor = accentColor,
                onTabSelected = { index ->
                    selectedTabIndex = index
                    viewModel.requestTab(index)
                }
            )

            GlobalSSIStatusBar(accentColor)
        }
    }
}

// ==================== Header & Status ====================

@Composable
fun LhsHeaderSection(accentColor: Color, onNavigate: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                "LDO HOLOGRAM SYSTEM // GLOBAL OS ACTIVE",
                color = accentColor,
                fontFamily = LEDFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
            Text(
                "EXODUS 2026 // REGENESIS SUBSTRATE",
                color = accentColor.copy(alpha = 0.7f),
                fontFamily = LEDFontFamily,
                fontSize = 9.sp
            )
        }

        IconButton(onClick = { onNavigate(ReGenesisRoute.LsposedQuickToggles.route) }) {
            Icon(Icons.Default.Settings, null, tint = accentColor)
        }
    }
}

@Composable
fun BottomJoystickNavigation(
    selectedIndex: Int,
    tabs: List<String>,
    accentColor: Color,
    onTabSelected: (Int) -> Unit
) {
    // Stub
}

@Composable
fun GlobalSSIStatusBar(accentColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Color(0xFF020205)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "PERSISTENCE > COMPUTE // 99.8% INTEGRITY",
            color = accentColor.copy(alpha = 0.5f),
            fontSize = 9.sp,
            fontFamily = LEDFontFamily,
            letterSpacing = 1.sp
        )
    }
}

// Keep your existing LdoModuleCard and tab content functions (neuralNexusTabContent, etc.)
// They are already well structured — just make sure the called routes exist.

@Composable
fun ChromaForgeTabContent(onNavigate: (String) -> Unit) {
    // Uses the actual MainScreen for Chroma Forge
    dev.aurakai.auraframefx.domains.chromaforge.navigation.MainScreen(androidx.navigation.compose.rememberNavController())
}

@Composable
fun SentinelMatrixTabContent(onNavigate: (String) -> Unit) {
    // Wires up Kai's Shield
    dev.aurakai.auraframefx.domains.kai.ui.MonitoringHUDsScreen { true }
}

@Composable
fun OracleDriveTabContent(onNavigate: (String) -> Unit) {
    // Wires up the AES-GCM locked OracleDrive
    dev.aurakai.auraframefx.domains.oracledrive.ui.OracleDriveHubScreen(androidx.navigation.compose.rememberNavController())
}
