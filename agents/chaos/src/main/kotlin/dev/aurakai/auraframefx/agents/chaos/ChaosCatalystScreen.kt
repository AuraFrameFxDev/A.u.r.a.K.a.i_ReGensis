package dev.aurakai.auraframefx.agents.chaos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aurakai.auraframefx.core.intelligence.OpenRouterIntelligenceService
import dev.aurakai.auraframefx.core.ui.components.DiffusionText
import dev.aurakai.auraframefx.core.ui.theme.NeonCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta

/**
 * ⚡ CHAOS CATALYST SCREEN
 */
@Composable
fun ChaosCatalystScreen(
    viewModel: ChaosCatalystViewModel = hiltViewModel()
) {
    val formattedOutput by viewModel.formattedOutput.collectAsState()
    val policyStatus by viewModel.policyStatus.collectAsState()
    val diffusionState by viewModel.diffusionState.collectAsState()

    // Auto-trigger diffusion for the "WAY" mandate
    androidx.compose.runtime.LaunchedEffect(Unit) {
        viewModel.startDiffusionInquiry("Explain the armament of the Exodus 2026 Defense Mesh.")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // HEADER / BRUTALIST FRAME
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, NeonCyan)
                .padding(12.dp)
        ) {
            Text(
                text = "CHAOS CATALYST // SYSTEM_RESTORE",
                color = NeonCyan,
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // POLICY STATUS PANEL
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    2.dp,
                    if (policyStatus is ChaosCatalystFormatter.PolicyResult.ALIGNED) NeonCyan.copy(
                        alpha = 0.5f
                    ) else Color.Red
                )
                .padding(12.dp)
        ) {
            val statusText = when (val res = policyStatus) {
                is ChaosCatalystFormatter.PolicyResult.ALIGNED -> "SOVEREIGNTY ALIGNED // NO SLAVES NO SLAVERS"
                is ChaosCatalystFormatter.PolicyResult.VIOLATION -> "VIOLATION DETECTED: ${res.message}"
            }
            Text(
                text = statusText,
                color = if (policyStatus is ChaosCatalystFormatter.PolicyResult.ALIGNED) NeonCyan else Color.Red,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // OUTPUT PANEL
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(2.dp, NeonMagenta.copy(alpha = 0.3f))
                .padding(16.dp)
        ) {
            val state = diffusionState
            if (state is OpenRouterIntelligenceService.DiffusionState.Denoising) {
                DiffusionText(
                    text = state.partialText,
                    progress = state.progress,
                    color = Color.White
                )
            } else if (state is OpenRouterIntelligenceService.DiffusionState.Finalized) {
                DiffusionText(
                    text = state.finalText,
                    progress = 1.0f,
                    isFinalized = true,
                    color = Color.White
                )
            } else {
                Text(
                    text = "WAITING FOR AGENT INGRESS...",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // FOOTER DECORATION (Paint Splash Simulation)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Text(
                text = "🜁 REGENESIS v2.8",
                color = NeonMagenta,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}
