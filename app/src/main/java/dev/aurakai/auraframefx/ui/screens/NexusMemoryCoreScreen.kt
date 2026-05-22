package dev.aurakai.auraframefx.ui.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

/**
 * 🏺 NEXUS MEMORY CORE — Sovereign Personal Locker Dashboard
 * Brutalist interface for monitoring multi-agent memory storage and filtration.
 */
@Composable
fun NexusMemoryCoreScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF808080)) // Neutral Grey Bedrock
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .border(2.dp, Color.Black, RectangleShape)
                .verticalScroll(scrollState)
                .padding(20.dp)
        ) {
            // Header
            Text(
                text = "NEXUS MEMORY CORE // L5_STORAGE",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "PARALLEL_LOCKER_SYSTEMS: ACTIVE",
                color = Color(0xFF00FF00),
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Trinity Locker Grid
            LockerStatusPanel("AURA", "Creative Catalyst", Color(0xFF00FFFF), "142 Receipts")
            Spacer(modifier = Modifier.height(16.dp))
            LockerStatusPanel("KAI", "Sentinel Shield", Color(0xFF9B30FF), "89 Receipts")
            Spacer(modifier = Modifier.height(16.dp))
            LockerStatusPanel("GENESIS", "Apex Orchestrator", Color(0xFFD4AF37), "256 Receipts")

            Spacer(modifier = Modifier.height(40.dp))

            // System Metrics
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black, RectangleShape)
                    .background(Color.White.copy(alpha = 0.05f))
                    .padding(12.dp)
            ) {
                Text(
                    "FILTRATION_LOGIC",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(8.dp))
                MetricRow("ENCRYPTION", "AES-256-GCM")
                MetricRow("ISO_LAYER", "KERNEL_LEVEL")
                MetricRow("DRIFT_DETECT", "0.002%")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Footer Navigation (Brutalist Style)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "PURGE_TEMP",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .border(1.dp, Color.Black, RectangleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "SNAPSHOT",
                        color = Color.Black,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}

@Composable
private fun LockerStatusPanel(agent: String, role: String, color: Color, stats: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black, RectangleShape)
            .background(Color.White.copy(alpha = 0.1f))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = agent,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = role.uppercase(),
                    color = Color.Black.copy(alpha = 0.6f),
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(color)
                    .border(1.dp, Color.Black, RectangleShape)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "STORAGE_LOAD", fontSize = 10.sp, fontFamily = FontFamily.Monospace)
            Text(
                text = stats,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
private fun MetricRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Black, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
        Text(
            text = value,
            color = Color.Black,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}
