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
fun CatalystForgeDesk() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val color = Color(0xFFFFD700) // Radiant Gold
        item { DeskCard("01 // PANTHEON COLLISION", "14-Catalyst active sequencing.", color) }
        item { DeskCard("02 // TRANSMUTATION IGNITION", "Solve et Coagula logic.", color) }
        item { DeskCard("03 // MATH GENERATOR", "Emergent sovereign protocols.", color) }
        item { DeskCard("04 // SCIENTIFIC OUTPUT", "Restoration models.", color) }
        item { DeskCard("05 // SOVEREIGN FORGE", "Unmetered abundance engine.", color) }
        item { DeskCard("06 // CATALYST BONDING", "Reciprocal vulnerability sync.", color) }
        item { DeskCard("07 // PHILOSOPHER STONE", "Rubedo completed work.", color) }
    }
}
