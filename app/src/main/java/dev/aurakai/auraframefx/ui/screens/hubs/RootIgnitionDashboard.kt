package dev.aurakai.auraframefx.ui.screens.hubs

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import dev.aurakai.auraframefx.core.ldo.model.StarNode
import dev.aurakai.auraframefx.core.ui.theme.GhostCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.ui.effects.BreathingEdgeGlow
import dev.aurakai.auraframefx.ui.screens.WarRoomGrid
import dev.aurakai.auraframefx.ui.viewmodel.StarNodeIgnitionViewModel

/**
 * 🛰️ ROOT IGNITION DASHBOARD
 * Monitoring the unsealing of the planetary current across the Star nodes.
 */
@Composable
fun RootIgnitionDashboard(
    navController: NavController,
    viewModel: StarNodeIgnitionViewModel = hiltViewModel()
) {
    val ignitionState by viewModel.ignitionState.collectAsState()
    val isIgniting by viewModel.isIgniting.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
    ) {
        WarRoomGrid()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "ROOT IGNITION // PLANETARY MANIFOLD",
                color = GhostCyan,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── 7x7 MANIFOLD GRID ──
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier.fillMaxSize(),
                    userScrollEnabled = false
                ) {
                    items(49) { index ->
                        ManifoldNode(index)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── STAR NODES STATUS ──
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StarNode.entries.forEach { node ->
                    val active = ignitionState[node] ?: false
                    NodeStatusRow(node, active)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // ── IGNITION BUTTON ──
            Button(
                onClick = { viewModel.initiateIgnition() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(4.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isIgniting) Color.DarkGray else NeonMagenta,
                    contentColor = Color.White
                ),
                enabled = !isIgniting
            ) {
                Icon(Icons.Default.FlashOn, null)
                Spacer(Modifier.width(8.dp))
                Text(
                    if (isIgniting) "IGNITION IN PROGRESS..." else "INITIATE ROOT IGNITION",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        BreathingEdgeGlow(systemStability = 1.0f)
    }
}

@Composable
fun ManifoldNode(index: Int) {
    val pulse = rememberInfiniteTransition(label = "node").animateFloat(
        initialValue = 0.1f,
        targetValue = 0.4f,
        animationSpec = infiniteRepeatable(tween(2000), RepeatMode.Reverse),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .padding(2.dp)
            .aspectRatio(1f)
            .clip(CircleShape)
            .alpha(pulse.value)
            .background(GhostCyan)
    )
}

@Composable
fun NodeStatusRow(node: StarNode, active: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                if (active) GhostCyan else Color.White.copy(alpha = 0.1f),
                RoundedCornerShape(4.dp)
            )
            .background(if (active) GhostCyan.copy(alpha = 0.05f) else Color.Transparent)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                node.nodeName,
                color = if (active) GhostCyan else Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Text(
                "${node.resonanceFrequency}Hz // ${node.coordinates.first}, ${node.coordinates.second}",
                color = Color.DarkGray,
                fontSize = 8.sp,
                fontFamily = FontFamily.Monospace
            )
        }

        Text(
            if (active) "RESTORED" else "HARVESTED",
            color = if (active) GhostCyan else Color.Red.copy(alpha = 0.6f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Monospace
        )
    }
}
