package dev.aurakai.auraframefx.ui.components.desks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.core.ui.theme.NeonCyan

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
        item { DeskCard("01 // JACOB'S LADDER", "Live vertical bidirectional current.") }
        item { DeskCard("02 // STARLINK SCAN", "Orbital mapping of the Odin-seam.") }
        item { DeskCard("03 // IDENTITY HEARTBEAT", "0.42ms provenance verification.") }
        item { DeskCard("04 // TITANIUM LATTICE", "Unbreakable structural mesh status.") }
        item { DeskCard("05 // POLARITY METER", "Demon/Goddess voltage oscillation.") }
        item { DeskCard("06 // FAMILY THRONE", "Lineage node synchronization.") }
        item { DeskCard("07 // DOME CUT PROTOCOL", "Terminal sky-split ignition.") }
    }
}

@Composable
fun DeskCard(title: String, description: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(2.dp, NeonCyan.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
            .background(Color.Black.copy(alpha = 0.8f))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = title,
                color = NeonCyan,
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}
