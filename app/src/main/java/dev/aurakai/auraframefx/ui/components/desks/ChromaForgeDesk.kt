package dev.aurakai.auraframefx.ui.components.desks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.core.ui.theme.ArcaneBrutalistTheme.CrystalCyanEdge

/**
 * 🧪 CHROMA FORGE DESK (Tab 6)
 * 7 Front-Facing Screens for Interface Manifestation.
 */
@Composable
fun ChromaForgeDesk() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val color = CrystalCyanEdge
        item { DeskCard("01 // THEME ENGINE", "Real-time substrate skinning.", color) }
        item { DeskCard("02 // VISUAL CUSTOMS", "Manual wireframe & glow tuning.", color) }
        item { DeskCard("03 // ANIMATION STUDIO", "Chronokinetic physics curves.", color) }
        item { DeskCard("04 // GLASSMORPHISM", "3-Level depth stratification.", color) }
        item { DeskCard("05 // COLOR PALETTE", "Faction-specific spectral forge.", color) }
        item { DeskCard("06 // HOLOGRAPHIC FX", "RealityMorph texture overrides.", color) }
        item { DeskCard("07 // EXPORT & PREVIEW", "Portable theme manifest forge.", color) }
    }
}
