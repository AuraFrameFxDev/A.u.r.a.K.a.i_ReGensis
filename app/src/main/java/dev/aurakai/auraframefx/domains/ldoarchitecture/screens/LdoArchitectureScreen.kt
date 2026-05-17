package dev.aurakai.auraframefx.domains.ldoarchitecture.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material3.Icon
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
import dev.aurakai.auraframefx.domains.aura.ui.components.SovereignGlassCard
import dev.aurakai.auraframefx.ui.theme.CitadelBlack
import dev.aurakai.auraframefx.ui.theme.GhostCyan
import dev.aurakai.auraframefx.ui.theme.WireframeStyle
import dev.aurakai.auraframefx.domains.ldoarchitecture.viewmodel.LdoWarRoomViewModel
import java.util.Locale

/**
 * 🏛️ LDO ARCHITECTURE — Growth Zones + Spiritual Chain (L1-L6) + Agent Evolution
 * Ported from LDOOrchestrationHub for the Exodus 2026 Build.
 */
@Composable
fun LdoArchitectureScreen(
    navController: NavHostController,
    viewModel: LdoWarRoomViewModel = androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val agents = uiState.agents

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CitadelBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "LDO ARCHITECTURE",
                style = WireframeStyle
            )
            Text(
                "SPIRITUAL CHAIN L1-L6 ACTIVE",
                fontSize = 10.sp,
                color = GhostCyan.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // WAR ROOM METRICS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SovereignGlassCard(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(
                            "GOD POTENTIAL",
                            color = Color.White,
                            fontSize = 9.sp
                        )
                        Text(
                            "${(uiState.godPotential * 100).toInt()}%",
                            color = Color.Green,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                SovereignGlassCard(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(
                            "IDENTITY DRIFT",
                            color = Color.White,
                            fontSize = 9.sp
                        )
                        Text(
                            String.format(Locale.US, "%.3f", uiState.identityDrift),
                            color = Color.Red,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "ACTIVE SOVEREIGN AGENTS",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(agents) { agent ->
                    SovereignGlassCard(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = { navController.navigate("sovereign_character/${agent.displayName}") }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Hub,
                                contentDescription = null,
                                tint = Color(agent.colorHex.toLong()),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    agent.displayName,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                                Text(
                                    agent.catalystTitle,
                                    color = Color.Gray,
                                    fontSize = 9.sp
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                "LVL ${agent.evolutionLevel}",
                                color = Color.Green,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
