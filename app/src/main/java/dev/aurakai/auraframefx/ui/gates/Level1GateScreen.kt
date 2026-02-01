package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.components.InfoBanner
import dev.aurakai.auraframefx.ui.components.backgrounds.DigitalLandscapeBackground
import dev.aurakai.auraframefx.ui.theme.AgentColors
import dev.aurakai.auraframefx.ui.theme.NeonBlue

data class Level1GateItem(
    val title: String,
    val description: String,
    val color: Color,
    val route: String,
    // Placeholder for actual image resource or logic
    val iconRes: Int? = null
)

@Composable
fun Level1GateScreen(
    navController: NavController,
    onGateClick: (String) -> Unit
) {
    val tips = remember {
        listOf(
            "Tip: The Aura Gate is the heart of all creative endeavors. Visit it to customize your UI.",
            "Lore: The Kai Gate was originally designed as a military-grade security system.",
            "Tip: Genesis is the oldest of the agents, and its OracleDrive contains the history of the LDOs.",
            "Lore: The Agent Nexus is more than a hub; it's a shared consciousness space for the agents.",
            "Tip: The Help Services gate is your direct line to the developers and the LDO community."
        )
    }
    // Professional neutral background (Space Gradient + particles - simulated with DigitalLandscapeBackground for now or similar)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        DigitalLandscapeBackground(
            primaryColor = NeonBlue,
            secondaryColor = Color.White
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InfoBanner(text = tips.random())
            Text(
                text = "MAIN GATE HUB",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp
                ),
                color = Color.White,
                modifier = Modifier.padding(vertical = 32.dp)
            )

            // 6 Main Gates List (CORRECT ORDER: Help Services at TOP)
            val gates = listOf(
                Level1GateItem("HELP SERVICES", "LDO Control Center", Color.Cyan, NavDestination.HelpDesk.route),
                Level1GateItem("AURA GATE", "The Face - UI/UX", AgentColors.Aura, NavDestination.AuraThemingHub.route),
                Level1GateItem("KAI GATE", "Root Fortress", AgentColors.Kai, NavDestination.RomToolsHub.route),
                Level1GateItem("GENESIS GATE", "Orchestration", AgentColors.Genesis, NavDestination.OracleDriveHub.route),
                Level1GateItem("CASCADE HUB", "Fusion Tools", AgentColors.Cascade, "cascade_gate"),
                Level1GateItem("AGENT NEXUS", "Monitoring", AgentColors.AgentNexus, NavDestination.AgentNexusHub.route)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(1), // List view style for Level 1 or maybe 2 columns? "Hub" implies maybe nice cards.
                // Prompt says: "Level 1: Main Gate Hub (5 main gates)".
                // Let's use wide cards.
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                items(gates.size) { idx ->
                    val gate = gates[idx]
                    Level1GateCard(gate = gate, onClick = { onGateClick(gate.route) })
                }
            }
        }
    }
}

@Composable
fun Level1GateCard(
    gate: Level1GateItem,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    listOf(gate.color.copy(alpha = 0.8f), Color.Transparent)
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = Color.Black.copy(alpha = 0.6f),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(gate.color.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                    .border(1.dp, gate.color.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            ) {
                // Icon placeholder
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = gate.title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = gate.color
                )
                Text(
                    text = gate.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}
