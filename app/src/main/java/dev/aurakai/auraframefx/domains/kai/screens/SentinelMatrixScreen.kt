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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.core.soulscript.SoulScriptV27
import dev.aurakai.auraframefx.domains.aura.ui.components.SovereignGlassCard
import dev.aurakai.auraframefx.ui.theme.CitadelBlack
import dev.aurakai.auraframefx.ui.theme.GhostCyan
import dev.aurakai.auraframefx.ui.theme.WireframeStyle
import timber.log.Timber

/**
 * 🛡️ SENTINEL MATRIX — Kairos Security Shield + NotchBar Pulse + Ethical Hard-Veto
 * Hardened Exodus 2026 Build with Brutalist Digital Arcane aesthetic.
 */
@Composable
fun SentinelMatrixScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        SoulScriptV27.hardenPerimeter()
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CitadelBlack)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.9f))
                    .padding(16.dp)
            ) {
                Text(
                    text = "SENTINEL MATRIX",
                    style = WireframeStyle
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // THE KAIROS SHIELD ORB
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .background(GhostCyan.copy(alpha = 0.05f), CircleShape)
                        .border(1.dp, GhostCyan, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Shield",
                        tint = GhostCyan,
                        modifier = Modifier.size(80.dp)
                    )
                }

                Text(
                    "KAIROS SHIELD ACTIVE",
                    color = GhostCyan,
                    fontSize = 12.sp,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // THREAT LATTICE STATUS
                SovereignGlassCard {
                    Column {
                        Text(
                            "THREAT LATTICE MONITOR",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "NotchBar Pulse + Live Zygote Monitoring: SECURE",
                            color = Color.Green,
                            fontSize = 10.sp
                        )
                    }
                }

                // ETHICAL HARD-VETO
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SovereignGlassCard(
                        modifier = Modifier
                            .weight(1f),
                        onClick = {
                            val safe =
                                SoulScriptV27.SentinelMatrix.ethicalHardVeto("test intent")
                            Timber.tag("Sentinel").i("Hard-Veto Test Result: $safe")
                        }
                    ) {
                        Column {
                            Text(
                                "HARD-VETO",
                                color = Color.Red,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Tap to Test Protection",
                                color = Color.Gray,
                                fontSize = 9.sp
                            )
                        }
                    }

                    SovereignGlassCard(modifier = Modifier.weight(1f)) {
                        Column {
                            Text(
                                "ROOT BRIDGE",
                                color = Color.Yellow,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "APatch + LSPosed Locked",
                                color = Color.Gray,
                                fontSize = 9.sp
                            )
                        }
                    }
                }

                // THERMAL WALL
                SovereignGlassCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "THERMAL WALL ARMED",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Text(
                                "Veto trigger set at 42.0°C",
                                color = Color.Gray,
                                fontSize = 10.sp
                            )
                        }
                        Text(
                            "36.5°C",
                            color = Color.Green,
                            fontSize = 18.sp
                        )
                    }
                }

                Spacer(Modifier.height(80.dp))
            }
        }
    }
}
