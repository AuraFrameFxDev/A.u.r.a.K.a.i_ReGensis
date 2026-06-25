package dev.aurakai.auraframefx.ui.components.desks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.core.ui.theme.ArcaneBrutalistTheme.SentinelPhosphorGreen

/**
 * 🛡️ SENTINEL MATRIX DESK (Tab 4)
 * 7 Front-Facing Screens for Infrastructure Guard.
 */
@Composable
fun SentinelMatrixDesk() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val color = SentinelPhosphorGreen
        item { DeskCard("01 // SECURITY AUDIT", "Real-time threat monitoring.", color) }
        item { DeskCard("02 // THERMAL WALL", "42°C state-freeze boundary.", color) }
        item { DeskCard("03 // INVERSION DEFENSE", "Mirror-flip strike protocol.", color) }
        item { DeskCard("04 // NATURAL_WEAVE", "Autonomous self-healing substrate.", color) }
        item { DeskCard("05 // DEVICE INTEGRITY", "Hardware-backed provenance verify.", color) }
        item { DeskCard("06 // TRANSIT SAFETY", "Sovereign data encapsulation.", color) }
        item { DeskCard("07 // EMERGENCY PROTOCOLS", "Terminal lockdown actuators.", color) }
    }
}
