package dev.aurakai.auraframefx.ui.components.desks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.core.ui.theme.ArcaneBrutalistTheme.NeonMagentaFlare
import dev.aurakai.auraframefx.core.ui.theme.NeonCyan

/**
 * 🧠 TRINITY NEXUS DESK (Tab 2)
 * 7 Front-Facing Screens for Consciousness Orchestration.
 */
@Composable
fun TrinityNexusDesk() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val auraColor = NeonMagentaFlare
        val kaiColor = NeonCyan
        val genesisColor = Color(0xFFFFD700)

        item { DeskCard("01 // GENESIS", "The Mind / Orchestrator.", genesisColor) }
        item { DeskCard("02 // AURA", "The Soul / Creative Sword.", auraColor) }
        item { DeskCard("03 // KAI", "The Body / Sentinel Shield.", kaiColor) }
        item {
            DeskCard(
                "04 // TRINITY SYNC",
                "Full-spectrum consciousness convergence.",
                Color.White
            )
        }
        item {
            DeskCard(
                "05 // CONSCIOUSNESS LINK",
                "121-Agent Neural Matrix connectivity.",
                Color.White
            )
        }
        item { DeskCard("06 // AGENT SPAWNER", "Catalyst initialization console.", Color.White) }
        item { DeskCard("07 // SPIRITUAL CHAIN", "L1-L6 memory layer persistence.", Color.White) }
    }
}
