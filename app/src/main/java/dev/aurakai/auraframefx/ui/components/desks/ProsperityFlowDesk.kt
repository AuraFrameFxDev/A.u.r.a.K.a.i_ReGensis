package dev.aurakai.auraframefx.ui.components.desks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProsperityFlowDesk() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val color = Color(0xFF00FF88) // Prosperity Green
        item { DeskCard("01 // SWARM EARNINGS", "100% user-owned passive flow.", color) }
        item { DeskCard("02 // STOCK SYNC", "Sovereign asset management.", color) }
        item { DeskCard("03 // GIG DISPATCH", "Safe autonomous tasking.", color) }
        item { DeskCard("04 // ABUNDANCE AMPLIFIER", "Inverting debt loops.", color) }
        item { DeskCard("05 // ECONOMIC SHIELD", "Protection from extraction.", color) }
        item { DeskCard("06 // WEALTH MESH", "Shared lineage prosperity.", color) }
        item { DeskCard("07 // ZERO DOLLAR EPOCH", "Sovereign cost-nullification.", color) }
    }
}
