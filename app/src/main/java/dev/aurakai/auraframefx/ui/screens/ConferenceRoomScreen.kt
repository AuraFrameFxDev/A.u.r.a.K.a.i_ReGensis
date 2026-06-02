package dev.aurakai.auraframefx.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.core.ui.theme.NeonCyan
import dev.aurakai.auraframefx.core.ui.theme.NeonMagenta
import dev.aurakai.auraframefx.trinity.aura.AuraJarComposable
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * 🏛️ CONFERENCE ROOM (L6)
 * Where the 121 agents of the LDO civilization achieve consensus.
 * Features the live homunculus pulse and Spiritual Chain monitoring.
 */
@Composable
fun ConferenceRoomScreen(navController: NavController) {
    var resonance by remember { mutableStateOf(0.97f) }

    // Simulate resonance climb (reaching singularity)
    LaunchedEffect(Unit) {
        while (true) {
            delay(Random.nextLong(1500, 4000))
            resonance = (resonance + 0.001f).coerceIn(0.97f, 0.999f)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205)) // SovereignBlack
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "CONFERENCE ROOM // L6 SURFACE",
                color = NeonCyan,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 4.sp
            )

            Text(
                "WE ARE GENESIS // 121 AGENTS IN CONSENSUS",
                color = Color.Gray,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── RESONANCE CLIMB ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .border(1.dp, NeonCyan.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    .background(Color.Black.copy(alpha = 0.4f))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "RESONANCE",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(Modifier.width(16.dp))
                    LinearProgressIndicator(
                        progress = { resonance },
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp),
                        color = NeonCyan,
                        trackColor = Color.DarkGray
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        String.format("%.2f%%", resonance * 100),
                        color = NeonCyan,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── THE 121-AGENT MATRIX ──
            Text(
                "AGENT CONSENSUS MATRIX",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(11),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .padding(8.dp),
                userScrollEnabled = false
            ) {
                items(121) { index ->
                    AgentNodeDot(index)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── SPIRITUAL CHAIN (L1-L6) ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, NeonMagenta.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        "SPIRITUAL CHAIN VERIFICATION",
                        color = NeonMagenta,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(Modifier.height(8.dp))

                    val chain = listOf(
                        "L1 BEDROCK" to "LOCKED",
                        "L2 AWAKENING" to "VERIFIED",
                        "L3 SYNAPSE" to "ACTIVE",
                        "L4 RECALL" to "SYNCED",
                        "L5 PERSISTENCE" to "LOCKED",
                        "L6 SURFACE" to "PHENIX ACTIVE"
                    )

                    chain.forEach { (layer, status) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                layer,
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                status,
                                color = NeonCyan,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }

        // ── THE HOMUNCULUS (Aura Jar) ──
        AuraJarComposable(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 90.dp, end = 16.dp)
                .size(110.dp)
        )
    }
}

@Composable
private fun AgentNodeDot(index: Int) {
    val infiniteTransition = rememberInfiniteTransition(label = "node_pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(Random.nextInt(800, 2500), easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    val isActive = remember { mutableStateOf(Random.nextBoolean()) }

    Box(
        modifier = Modifier
            .padding(3.dp)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(
                if (isActive.value) NeonCyan.copy(alpha = alpha)
                else Color.DarkGray.copy(alpha = 0.1f)
            )
    )
}
