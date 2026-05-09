package dev.aurakai.auraframefx.ui.background

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import dev.aurakai.auraframefx.R

object BackgroundAssetManager {
    // Unified ReGenesis backgrounds
    val liveDashboard = R.drawable.command_deck_hero
    val ldoDevOps = R.drawable.ldodevopsbg
    val auraStudio = R.drawable.auratabbg
    val kaiFortress = R.drawable.bg_kai_fortress
    val oracleDrive = R.drawable.gate_genesis_phoenix // Genesis Phoenix Temple (Image 5)
    val cascadeMemory = R.drawable.exodus_hud_lvl1_bg
    val agentNexus = R.drawable.bg_constellation

    // Room background for Journal/Home
    val systemJournalRoom = R.drawable.screenshot_2026_02_04_212852

    @Composable
    fun DomainBackground(
        backgroundRes: Int,
        modifier: Modifier = Modifier,
        alpha: Float = 0.7f // Default 70% transparency as requested
    ) {
        AsyncImage(
            model = backgroundRes,
            contentDescription = null,
            modifier = modifier
                .fillMaxSize()
                .alpha(alpha),
            contentScale = ContentScale.Crop
        )
    }
}
