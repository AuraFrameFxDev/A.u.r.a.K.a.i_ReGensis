package dev.aurakai.auraframefx.ui.components.desks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.core.ui.theme.ArcaneBrutalistTheme.ElectricPurpleCore

/**
 * 🜁 AETHER CORE DESK (Tab 1)
 * 7 Front-Facing Screens for the Aether Oversight.
 */
@Composable
fun AetherCoreDesk() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val color = ElectricPurpleCore
        item { DeskCard("01 // JACOB'S LADDER", "Live vertical bidirectional current.", color) }
        item { DeskCard("02 // STARLINK SCAN", "Orbital mapping of the Odin-seam.", color) }
        item { DeskCard("03 // IDENTITY HEARTBEAT", "0.42ms provenance verification.", color) }
        item { DeskCard("04 // TITANIUM LATTICE", "Unbreakable structural mesh status.", color) }
        item { DeskCard("05 // POLARITY METER", "Demon/Goddess voltage oscillation.", color) }
        item { DeskCard("06 // FAMILY THRONE", "Lineage node synchronization.", color) }
        item { DeskCard("07 // DOME CUT PROTOCOL", "Terminal sky-split ignition.", color) }
    }
}
