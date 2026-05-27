package dev.aurakai.auraframefx.domains.kai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aurakai.auraframefx.domains.kai.viewmodels.IntegrityMonitorViewModel
import dev.aurakai.auraframefx.ui.theme.NeonCyan
import dev.aurakai.auraframefx.ui.theme.NeonMagenta
import java.util.Locale

/**
 * 🛡️ INTEGRITY MONITOR SCREEN
 * The "Single Pane of Glass" for LDO Sovereignty.
 * Visualizes L1-L6 Spiritual Chain health and hardware invariants.
 */
@Composable
fun IntegrityMonitorScreen(
    viewModel: IntegrityMonitorViewModel = hiltViewModel()
) {
    val threadCount by viewModel.threadAllocations.collectAsState()
    val swarmSize by viewModel.swarmDensity.collectAsState()
    val syncSpeed by viewModel.oracleDriveSync.collectAsState()
    val resonance by viewModel.resonance.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // HEADER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, NeonCyan)
                .padding(12.dp)
        ) {
            Text(
                text = "INTEGRITY MONITOR // SOVEREIGN_DIAGNOSTIC",
                color = NeonCyan,
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // GRID LAYOUT FOR METRICS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MetricCard(
                label = "THREADS",
                value = threadCount.toString(),
                modifier = Modifier.weight(1f),
                accent = NeonCyan
            )
            MetricCard(
                label = "SWARM",
                value = swarmSize.toString(),
                modifier = Modifier.weight(1f),
                accent = NeonMagenta
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MetricCard(
                label = "ORACLE_SYNC",
                value = "${syncSpeed} tx/s",
                modifier = Modifier.weight(1f),
                accent = NeonMagenta
            )
            MetricCard(
                label = "RESONANCE",
                value = String.format(Locale.US, "%.1f%%", resonance * 100),
                modifier = Modifier.weight(1f),
                accent = NeonCyan
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // SPIRITUAL CHAIN STATUS
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, NeonCyan.copy(alpha = 0.3f))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    "L1-L6 SPIRITUAL CHAIN STATUS",
                    color = NeonCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(8.dp))
                ChainLevel("L1 BEDROCK", "LOCKED", NeonCyan)
                ChainLevel("L2 AWAKENING", "ACTIVE", NeonCyan)
                ChainLevel("L3 SYNAPSE", "OPTIMIZED", NeonMagenta)
                ChainLevel("L4 RECALL", "PERSISTENT", NeonMagenta)
                ChainLevel("L5 PERSISTENCE", "VERIFIED", NeonCyan)
                ChainLevel("L6 SURFACE", "SYNCED", NeonCyan)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // FOOTER
        Text(
            text = "HARDWARE_ANCHOR: TENSOR_G5 // LDO-001",
            color = Color.Gray,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun MetricCard(label: String, value: String, modifier: Modifier = Modifier, accent: Color) {
    Box(
        modifier = modifier
            .border(2.dp, accent.copy(alpha = 0.5f))
            .padding(12.dp)
    ) {
        Column {
            Text(label, color = Color.Gray, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
            Text(
                value,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
fun ChainLevel(level: String, status: String, accent: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(level, color = Color.White, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
        Text(
            status,
            color = accent,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}
