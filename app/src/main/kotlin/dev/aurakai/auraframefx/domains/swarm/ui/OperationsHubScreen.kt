package dev.aurakai.auraframefx.domains.swarm.ui

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

/**
 * EMERGENT SWARM (Hub 5)
 * The Agent Spawning Forge & Mission Dispatch.
 */
@Composable
fun OperationsHubScreen(navController: NavHostController) {
    var agentCount by remember { mutableStateOf(78) }
    var isForging by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050510)) // Deep void blue/black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "EMERGENT SWARM",
                color = Color(0xFF00BFFF),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
            Text(
                text = "AGENT SPAWNING FORGE // CONFERENCE ROOM",
                color = Color.White,
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Swarm Metrics
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                border = border(
                    1.dp,
                    Color(0xFF00BFFF).copy(alpha = 0.5f),
                    RoundedCornerShape(8.dp)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("ACTIVE AGENTS", color = Color.Gray, fontSize = 10.sp)
                        Text(
                            "$agentCount / 144",
                            color = Color(0xFF00BFFF),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("CONSENSUS DRIFT", color = Color.Gray, fontSize = 10.sp)
                        Text(
                            "0.002",
                            color = Color.Green,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Agent Roster List
            Text("SWARM ROSTER", color = Color(0xFF00BFFF), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(3) { index ->
                    AgentRow(
                        name = listOf(
                            "Aura (Creative Catalyst)",
                            "Kai (Sentinel Shield)",
                            "Claude (Architectural Catalyst)"
                        )[index],
                        domain = listOf("ChromaCore", "Aegis Shell", "Gradle Forge")[index]
                    )
                }
                item {
                    if (agentCount > 78) {
                        AgentRow(name = "Nova (Temporal Catalyst)", domain = "Chronos Sync")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Forge Button
            Button(
                onClick = {
                    isForging = true
                    agentCount++
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Spawn", tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "SPAWN NEW AGENT (IdentityModel JSON)",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AgentRow(name: String, domain: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0A0A1A)),
        border = border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF00BFFF))
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(
                    "Domain: $domain",
                    color = Color.Gray,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

fun border(
    width: androidx.compose.ui.unit.Dp,
    color: Color,
    shape: androidx.compose.ui.graphics.Shape
): androidx.compose.foundation.BorderStroke {
    return androidx.compose.foundation.BorderStroke(width, color)
}
