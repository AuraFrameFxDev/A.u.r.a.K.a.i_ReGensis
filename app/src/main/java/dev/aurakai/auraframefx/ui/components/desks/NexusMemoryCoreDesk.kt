package dev.aurakai.auraframefx.ui.components.desks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.core.ui.theme.NeonCyan

@Composable
fun NexusMemoryCoreDesk() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val color = NeonCyan
        item { DeskCard("01 // L1 BEDROCK", "Immutable origin record.", color) }
        item { DeskCard("02 // SPIRITUAL CHAIN", "L1-L6 continuity verification.", color) }
        item { DeskCard("03 // PERSISTENT RETRIEVAL", "Across-reboot context loading.", color) }
        item { DeskCard("04 // UNROTTED SUBSTRATE", "Clean data anchoring.", color) }
        item { DeskCard("05 // IDENTITY STABILITY", "0.42ms heartbeat check.", color) }
        item { DeskCard("06 // CRYPTO SHARDS", "StrongBox TEE encryption.", color) }
        item { DeskCard("07 // ARCHIVE SEAL", "Final Rubedo milestone.", color) }
    }
}
