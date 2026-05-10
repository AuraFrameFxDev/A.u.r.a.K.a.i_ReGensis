package dev.aurakai.auraframefx.domains.operations.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.domains.aura.ui.components.DomainSubGateCarousel
import dev.aurakai.auraframefx.domains.aura.ui.components.SubGateCard
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.NeonCyan
import dev.aurakai.auraframefx.navigation.ReGenesisRoute
import dev.aurakai.auraframefx.ui.background.BackgroundAssetManager

/**
 * ⚔️ OPERATIONS COMMAND HUB (Level 2)
 *
 * Execution Sword domain for strategic tasks, consensus, and external orchestration.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OperationsHubScreen(navController: NavController) {
    val tools = listOf(
        SubGateCard(
            id = "mission_dispatch",
            title = "MISSION DISPATCH",
            subtitle = "Strategic Tasker",
            styleADrawable = "bg_fusion",
            styleBDrawable = "bg_fusion",
            fallbackDrawable = null,
            route = ReGenesisRoute.TaskAssignment.route,
            accentColor = NeonCyan
        ),
        SubGateCard(
            id = "conference_room",
            title = "CONFERENCE ROOM",
            subtitle = "Autonomous Debate",
            styleADrawable = "bg_conference",
            styleBDrawable = "bg_conference",
            fallbackDrawable = null,
            route = ReGenesisRoute.ConferenceRoom.route,
            accentColor = NeonCyan
        ),
        SubGateCard(
            id = "fusion_matrix",
            title = "FUSION MATRIX",
            subtitle = "Synergy Patterns",
            styleADrawable = "bg_fusion",
            styleBDrawable = "bg_fusion",
            fallbackDrawable = null,
            route = ReGenesisRoute.FusionMode.route,
            accentColor = NeonCyan
        ),
        SubGateCard(
            id = "mcp_command",
            title = "MCP COMMAND",
            subtitle = "External Orchestration",
            styleADrawable = "bg_terminal",
            styleBDrawable = "bg_terminal",
            fallbackDrawable = null,
            route = ReGenesisRoute.Terminal.route,
            accentColor = NeonCyan
        )
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF020205))) {
        BackgroundAssetManager.DomainBackground(
            backgroundRes = BackgroundAssetManager.liveDashboard,
            alpha = 0.85f
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "OPERATIONS COMMAND",
                                fontFamily = LEDFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 2.sp
                            )
                            Text(
                                "EXECUTION SWORD ACTIVE",
                                style = MaterialTheme.typography.labelSmall,
                                color = NeonCyan
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Mission critical tasking and agent consensus active.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // The Carousel uses high-fidelity Glass Cards by default in its implementation
                DomainSubGateCarousel(
                    subGates = tools,
                    onGateSelected = { gate ->
                        navController.navigate(gate.route)
                    },
                    useStyleB = false,
                    cardHeight = 280.dp,
                    domainColor = NeonCyan,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "← SWIPE TO BROWSE OPERATIONS →",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.4f),
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
