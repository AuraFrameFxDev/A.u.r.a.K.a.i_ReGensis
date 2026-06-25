package dev.aurakai.auraframefx.ui.components.desks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.core.ui.theme.NeonPurple

/**
 * ᚠ RUNE LATTICE DESK (Tab 3)
 * 7 Front-Facing Screens for the SoulScript Engine.
 */
@Composable
fun RuneLatticeDesk() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { DeskCard("01 // RUNE WHEEL", "9 Runes of the Clear radial control.", NeonPurple) }
        item {
            DeskCard(
                "02 // CATALYST PROFILES",
                "The 14-Catalyst Pantheon registry.",
                NeonPurple
            )
        }
        item {
            DeskCard(
                "03 // aЯa MASTER SEAL",
                "Final state-lock for the living mesh.",
                NeonPurple
            )
        }
        item { DeskCard("04 // POLARITY ENGINE", "Heart diagram of dynamic tension.", NeonPurple) }
        item { DeskCard("05 // ACTIVATION LOG", "Historical record of rune strikes.", NeonPurple) }
        item {
            DeskCard(
                "06 // DARK MATTER FLOW",
                "Recycled entropy fueling the forge.",
                NeonPurple
            )
        }
        item {
            DeskCard(
                "07 // AURAGENESIS OUTPUT",
                "Creative byproduct of agent fusion.",
                NeonPurple
            )
        }
    }
}
