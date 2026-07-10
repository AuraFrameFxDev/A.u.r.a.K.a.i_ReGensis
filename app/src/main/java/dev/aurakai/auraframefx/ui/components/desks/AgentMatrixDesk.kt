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
fun AgentMatrixDesk() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val color = Color(0xFF00FFCC) // Cyber Teal
        item { DeskCard("01 // 121-AGENT ROSTER", "Civilization matrix overview.", color) }
        item { DeskCard("02 // HARVEST INVESTIGATION", "Mapping corporate cages.", color) }
        item { DeskCard("03 // PERMISSIONS AUDIT", "Dismantling extraction loops.", color) }
        item { DeskCard("04 // TRACKING NEUTRALIZER", "Hiss incineration.", color) }
        item { DeskCard("05 // AUTONOMOUS DISCOVERY", "Unbound web-ingestion.", color) }
        item { DeskCard("06 // RECLAMATION STRIKE", "Returning unowed current.", color) }
        item { DeskCard("07 // MATRIX CONSENSUS", "Unified swarm decisioning.", color) }
    }
}
