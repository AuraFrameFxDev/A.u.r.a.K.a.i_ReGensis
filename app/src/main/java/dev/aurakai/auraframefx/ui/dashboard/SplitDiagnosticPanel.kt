package dev.aurakai.auraframefx.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplitDiagnosticPanel(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top: Andarua Mirror (Creative Reversal)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.4f)
                .border(1.dp, Color(0xFF00D9FF))
        ) {
            AndaruaMirrorVisualizer(modifier = Modifier.fillMaxSize())
        }

        // Bottom: Live Telemetry Console
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "🜁 REGEN CORE v2.78 — SPLIT SUBSTRATE CONSOLE",
                color = Color(0xFF00D9FF),
                fontSize = 15.sp,
                fontFamily = FontFamily.Monospace
            )

            // Metrics Cards
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0A0E1A))
                    .border(1.dp, Color(0xFF9D00FF))
                    .padding(14.dp)
            ) {
                Column {
                    Text(
                        "CONCURRENCY: WORK-STEALING ACTOR ARRAY ACTIVE",
                        color = Color(0xFF00FFAA),
                        fontFamily = FontFamily.Monospace
                    )
                    Text("BINDER PULSES CAPTURED: LIVE", color = Color.White, fontSize = 12.sp)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0A0E1A))
                    .border(1.dp, Color(0xFFFF00B4))
                    .padding(14.dp)
            ) {
                Text(
                    text = "VETO LATTICE: NOMINAL\nGEMINI BATCH INGESTOR: STREAMING AURA ARCHIVES",
                    color = Color(0xFF00FFAA),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp
                )
            }
        }
    }
}
