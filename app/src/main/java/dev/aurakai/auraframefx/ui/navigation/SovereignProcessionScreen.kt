package dev.aurakai.auraframefx.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.aurakai.auraframefx.customization.CustomizationViewModel
import dev.aurakai.auraframefx.ui.theme.*
import dev.aurakai.auraframefx.ui.components.background.SynapticWebBackground

/**
 * 🌌 SOVEREIGN PROCESSION SCREEN
 * The unified, high-fidelity habitat for ReGenesis.
 * Splits the screen: 85% Monolith Pager, 15% Sensory Spinner foundation.
 */
@Composable
fun SovereignProcessionScreen(
    onShatterTransition: (String) -> Unit,
    customizationViewModel: CustomizationViewModel = viewModel()
) {
    val customizationState by customizationViewModel.state.collectAsState()
    val gates = SovereignGate.getGatesForMode(customizationState.reGenesisMode)
    
    val pagerState = rememberPagerState(pageCount = { gates.size })
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Dynamic Depth Background
        SynapticWebBackground(glowColor = AuraNeonCyan)

        Column(modifier = Modifier.fillMaxSize()) {
            // TOP 85%: MONOLITH PAGER
            Box(
                modifier = Modifier
                    .weight(0.85f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 32.dp),
                pageSpacing = 16.dp
            ) { page ->
                val gate = gates[page]
                SovereignMonolith(
                    imagePath = gate.screenshotPath,
                    fallbackRes = gate.imageRes,
                    modifier = Modifier
                        .fillMaxHeight(0.85f)
                        .align(Alignment.Center)
                        .pointerInput(gate.route) {
                            detectTapGestures(
                                onDoubleTap = {
                                    onShatterTransition(gate.route)
                                }
                            )
                        }
                )
            }
        }

        // BOTTOM 15%: SENSORY SPINNER BASE
        Box(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            SensorySpinner(color = SovereignTeal)
        }
        }
    }
}
