package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.ui.components.DomainSubGateCarousel
import dev.aurakai.auraframefx.ui.components.getNexusSubGates
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🤖 AGENT NEXUS HUB (Level 2)
 *
 * Features a carousel of sub-gates with constellation aesthetic.
 * Multi-agent coordination, monitoring, and fusion!
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentNexusHubScreen(navController: NavController) {

    val subGates = getNexusSubGates()

    Box(modifier = Modifier.fillMaxSize()) {
        // Neural constellation background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF0F0F1E), Color(0xFF1A1A32))
                    )
                )
        )

        // Radial glow effect
        Box(
            modifier = Modifier
                .size(400.dp)
                .align(Alignment.TopEnd)
                .offset(x = 100.dp, y = (-100).dp)
                .background(
                    Brush.radialGradient(
                        listOf(Color(0xFF7B2FFF).copy(alpha = 0.15f), Color.Transparent)
                    )
                )
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "AGENT NEXUS",
                                fontFamily = LEDFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 2.sp
                            )
                            Text(
                                "MULTI-AGENT CONVERGENCE",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF7B2FFF)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
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

                // Domain Description
                Text(
                    text = "Agent coordination, monitoring, and fusion protocols",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // 🎠 SUB-GATE CAROUSEL - Constellation Style!
                DomainSubGateCarousel(
                    subGates = subGates,
                    onGateSelected = { gate ->
                        navController.navigate(gate.route)
                    },
                    cardHeight = 280.dp,
                    domainColor = Color(0xFF7B2FFF),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Hint text
                Text(
                    text = "← SWIPE TO BROWSE • TAP TO ENTER →",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.4f),
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
