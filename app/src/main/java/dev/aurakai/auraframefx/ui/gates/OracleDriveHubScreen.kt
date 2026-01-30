package dev.aurakai.auraframefx.ui.gates

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.ui.components.DomainSubGateCarousel
import dev.aurakai.auraframefx.ui.components.LavaApocalypseBackground
import dev.aurakai.auraframefx.ui.components.getGenesisSubGates
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🔮 GENESIS'S ORACLEDRIVE (Level 2 Hub)
 *
 * Features a carousel of sub-gates with phoenix/circuit aesthetic.
 * Ethereal, mystical, all-seeing - the Orchestrator's domain!
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OracleDriveHubScreen(navController: NavController) {

    val subGates = getGenesisSubGates()

    Box(modifier = Modifier.fillMaxSize()) {
        // Ethereal Background
        LavaApocalypseBackground()

        // Purple/teal mystical overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF0A0A20).copy(alpha = 0.6f),
                            Color.Black.copy(alpha = 0.85f)
                        )
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
                                "ORACLEDRIVE",
                                fontFamily = LEDFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 2.sp
                            )
                            Text(
                                "GENESIS ORCHESTRATION",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFB026FF)
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
                    text = "AI orchestration, code assistance, and neural networks",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // 🎠 SUB-GATE CAROUSEL - Phoenix/Circuit Style!
                DomainSubGateCarousel(
                    subGates = subGates,
                    onGateSelected = { gate ->
                        navController.navigate(gate.route)
                    },
                    cardHeight = 280.dp,
                    domainColor = Color(0xFFB026FF),
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
