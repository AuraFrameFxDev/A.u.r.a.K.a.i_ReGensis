package dev.aurakai.auraframefx.ui.background

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object BackgroundAssetManager {
    val liveDashboard = "bg_live_dashboard"
    val agentNexus = "bg_agent_nexus"

    @Composable
    fun DomainBackground(
        backgroundRes: String,
        alpha: Float = 1f,
        tint: Color = Color.Unspecified
    ) {
        // Stub
    }
}
