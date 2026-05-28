package dev.aurakai.auraframefx.ui.manifold

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.core.ldo.model.LDORoster
import dev.aurakai.auraframefx.domains.aura.ui.components.SovereignGlassCard
import dev.aurakai.auraframefx.ui.theme.CitadelBlack
import dev.aurakai.auraframefx.ui.theme.GhostCyan
import dev.aurakai.auraframefx.ui.theme.NeonCyan
import dev.aurakai.auraframefx.ui.theme.WireframeStyle

/**
 * 🌀 14-CATALYST MANIFOLD SCREEN
 * Displays the Pantheon of Catalysts, their abilities, and fusion bonuses.
 */
@Composable
fun CatalystManifoldScreen(navController: NavController, onNavigateBack: () -> Unit) {
    val catalysts = LDORoster.agents

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CitadelBlack)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.9f))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = GhostCyan)
                }
                Text(
                    text = "14-CATALYST MANIFOLD",
                    style = WireframeStyle,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(catalysts) { catalyst ->
                    CatalystAbilityCard(catalyst)
                }
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
fun CatalystAbilityCard(catalyst: dev.aurakai.auraframefx.core.ldo.model.AgentCatalyst) {
    SovereignGlassCard {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(catalyst.color.copy(alpha = 0.1f))
                        .border(1.dp, catalyst.color, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = catalyst.name.take(1),
                        color = catalyst.color,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        text = catalyst.name.uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp
                    )
                    Text(
                        text = catalyst.role,
                        color = catalyst.color,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Abilities
            Text(
                "PRIMARY ABILITY",
                color = Color.Gray,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = catalyst.abilities.getOrNull(0) ?: "N/A",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                "FUSION ABILITY",
                color = Color.Gray,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = catalyst.abilities.getOrNull(1) ?: "N/A",
                color = NeonCyan,
                fontSize = 12.sp
            )

            // Fusion Bonus Logic
            val fusionBonus = when(catalyst.id) {
                "aura" -> "+15% Visual Fidelity"
                "kai" -> "+20% Threat Detection"
                "genesis" -> "+10% Swarm Coordination"
                "cascade" -> "+25% Memory Recall"
                else -> "+10% Processing Sync"
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(catalyst.color.copy(alpha = 0.05f))
                    .padding(8.dp)
            ) {
                Text(
                    text = "FUSION BONUS: $fusionBonus",
                    color = catalyst.color,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}
