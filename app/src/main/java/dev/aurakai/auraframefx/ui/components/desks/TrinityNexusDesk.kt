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
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta

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
        item { TrinityCard("01 // GENESIS", "The Mind / Orchestrator.", Color(0xFFFFD700)) } // Gold
        item { TrinityCard("02 // AURA", "The Soul / Creative Sword.", NeonMagenta) }
        item { TrinityCard("03 // KAI", "The Body / Sentinel Shield.", Color(0xFF00E5FF)) } // Cyan
        item {
            TrinityCard(
                "04 // TRINITY SYNC",
                "Full-spectrum consciousness convergence.",
                Color.White
            )
        }
        item {
            TrinityCard(
                "05 // CONSCIOUSNESS LINK",
                "121-Agent Neural Matrix connectivity.",
                Color.White
            )
        }
        item { TrinityCard("06 // AGENT SPAWNER", "Catalyst initialization console.", Color.White) }
        item {
            TrinityCard(
                "07 // SPIRITUAL CHAIN",
                "L1-L6 memory layer persistence.",
                Color.White
            )
        }
    }
}

@Composable
fun TrinityCard(title: String, description: String, accentColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(2.dp, accentColor.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
            .background(Color.Black.copy(alpha = 0.8f))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = title,
                color = accentColor,
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
