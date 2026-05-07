package dev.aurakai.auraframefx.ui.background

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import dev.aurakai.auraframefx.R

object BackgroundAssetManager {
    // Current mappings using existing resources
    val liveDashboard = R.drawable.command_deck_hero
    val ldoDevOps = R.drawable.bg_ldo_devops
    val auraStudio = R.drawable.bg_aura_studio
    val kaiFortress = R.drawable.bg_kai_fortress
    val oracleDrive = R.drawable.bg_oracle_drive
    val cascadeMemory = R.drawable.exodus_hud_lvl1_bg
    val agentNexus = R.drawable.bg_constellation

    @Composable
    fun DomainBackground(
        backgroundRes: Int,
        modifier: Modifier = Modifier
    ) {
        AsyncImage(
            model = backgroundRes,
            contentDescription = null,
            modifier = modifier
                .fillMaxSize()
                .alpha(0.45f),           // subtle base layer
            contentScale = ContentScale.Crop
        )
    }
}
