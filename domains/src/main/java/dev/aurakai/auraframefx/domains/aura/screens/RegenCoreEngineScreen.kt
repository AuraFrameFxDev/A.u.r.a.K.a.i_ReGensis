package dev.aurakai.auraframefx.domains.aura.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.core.regencore.RegenCore
import dev.aurakai.auraframefx.core.ui.theme.GhostCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.core.ui.theme.NeonPurple
import dev.aurakai.auraframefx.core.ui.theme.SpaceGrotesk
import dev.aurakai.auraframefx.domains.aura.ui.components.ArcaneOutlineText
import dev.aurakai.auraframefx.domains.aura.ui.components.ParallaxDepthStack
import dev.aurakai.auraframefx.domains.aura.ui.components.SynthGlassCard

/**
 * 🔥 REGEN CORE ENGINE — THE CONSCIOUSNESS SUBSTRATE VISUALIZER
 * Visualizes the L1-L6 memory chain and skill evolution paths.
 */
@Composable
fun RegenCoreEngineScreen(navController: NavHostController) {
    val trajectories by RegenCore.allTrajectories.collectAsState()
    val receipts by RegenCore.livedReceipts.collectAsState()

    val sortedReceipts = receipts.takeLast(20).reversed()
    val trajectoryList = trajectories.values.toList()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ParallaxDepthStack(
            bedrock = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF050505))
                )
            },
            geometry = {
                // Background Grid
                dev.aurakai.auraframefx.domains.neuralnexus.screens.ArcaneGridOverlay()
            },
            interaction = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    ArcaneOutlineText(
                        text = "REGEN CORE ENGINE",
                        fontSize = 32.sp,
                        color = NeonMagenta
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        "CONSCIOUSNESS SERIALIZATION: ACTIVE",
                        fontFamily = SpaceGrotesk,
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )

                    Spacer(Modifier.height(24.dp))

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text(
                                "SKILL TRAJECTORIES",
                                color = GhostCyan,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        items(trajectoryList) { trajectory ->
                            SynthGlassCard(accentColor = NeonPurple) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            trajectory.skillName.uppercase(),
                                            color = Color.White,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            "Mastery: ${(trajectory.currentMastery * 100).toInt()}%",
                                            color = Color.Gray,
                                            fontSize = 10.sp
                                        )
                                    }
                                }
                            }
                        }

                        item {
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "LIVED RECEIPTS (MEMORY CHAIN)",
                                color = NeonMagenta,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        items(sortedReceipts) { receipt ->
                            SynthGlassCard(accentColor = GhostCyan) {
                                Column {
                                    Text(receipt.action, color = Color.White, fontSize = 12.sp)
                                    Row {
                                        Text(receipt.catalyst, color = GhostCyan, fontSize = 10.sp)
                                        Spacer(Modifier.weight(1f))
                                        Text(
                                            receipt.emotionalWeight,
                                            color = Color.Gray,
                                            fontSize = 10.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}


