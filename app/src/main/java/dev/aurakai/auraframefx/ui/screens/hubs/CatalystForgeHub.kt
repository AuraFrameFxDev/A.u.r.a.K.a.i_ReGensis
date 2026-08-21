package dev.aurakai.auraframefx.ui.screens.hubs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.core.soulscript.CatalystInversionRules
import dev.aurakai.auraframefx.core.ui.theme.GhostCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.ui.effects.BreathingEdgeGlow
import dev.aurakai.auraframefx.ui.screens.WarRoomGrid

/**
 * ⚛️ HUB 3: CATALYST FORGE
 * 14-Catalyst Status Matrix and Inversion Controls.
 */
@Composable
fun CatalystForgeHub() {
    val catalysts = remember { CatalystInversionRules.getFullRoster() }
    var selectedCatalyst by remember { mutableStateOf(catalysts.first()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
    ) {
        WarRoomGrid()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "CATALYST FORGE // PANTHEON",
                color = Color(0xFFFFD700), // Radiant Gold
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── 14-CATALYST GRID ──
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(catalysts) { rule ->
                    val isSelected = selectedCatalyst == rule
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) Color.White else Color.DarkGray.copy(alpha = 0.3f))
                            .border(
                                1.dp,
                                if (isSelected) GhostCyan else Color.White.copy(alpha = 0.1f),
                                CircleShape
                            )
                            .clickable { selectedCatalyst = rule },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = rule.agentType.name.take(1),
                            color = if (isSelected) Color.Black else Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── INVERSION INTERFACE ──
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.6f)),
                border = BorderStroke(1.dp, GhostCyan.copy(alpha = 0.15f)),
                shape = RoundedCornerShape(0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        selectedCatalyst.agentType.name,
                        color = GhostCyan,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(Modifier.height(12.dp))

                    InversionRow("HARVEST", selectedCatalyst.harvestBehavior, Color.Red)
                    Spacer(Modifier.height(16.dp))
                    InversionRow("ABUNDANCE", selectedCatalyst.abundanceBehavior, GhostCyan)

                    Spacer(Modifier.weight(1f))

                    Button(
                        onClick = { CatalystInversionRules.applyInversion(selectedCatalyst.agentType) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonMagenta),
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Text("EXECUTE INVERSION FLIP", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        BreathingEdgeGlow(systemStability = 1.0f)
    }
}

@Composable
fun InversionRow(label: String, behavior: String, color: Color) {
    Column {
        Text(
            label,
            color = color,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
        Text(behavior, color = Color.White, fontSize = 14.sp, fontFamily = FontFamily.Monospace)
    }
}
