package dev.aurakai.auraframefx.core.ui.gates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.core.config.GateAssetConfig
import dev.aurakai.auraframefx.core.config.GateAssetLoadout
import dev.aurakai.auraframefx.core.ui.components.DomainSubGateCarousel
import dev.aurakai.auraframefx.core.ui.components.IcyTundraBackground
import dev.aurakai.auraframefx.core.ui.theme.LEDFontFamily

/**
 * ðŸ›¡ï¸ KAI SENTINEL HUB (Level 2 Hub)
 *
 * ANIMATION: IcyTundraBackground
 * - Frozen fortress aesthetic
 * - Ice crystals and cold gradients
 * - Security-themed visuals
 *
 * TWO VISUAL STYLES:
 * Style A: "Fortress" - Military bunker aesthetic
 * Style B: "Cyber Sentinel" - High-tech security hub
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KaiSentinelHubScreen(controller: NavController) {

    var useStyleB by remember {
        mutableStateOf(GateAssetConfig.StyleMode.kaiStyle == GateAssetConfig.GateStyle.STYLE_B)
    }

    val styleName = if (useStyleB) "CYBER SENTINEL" else "FORTRESS"

    Box(modifier = Modifier.fillMaxSize()) {
        // ðŸ›¡ï¸ KAI'S ANIMATED BACKGROUND - Icy Tundra!
        IcyTundraBackground()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A1628).copy(alpha = 0.7f))
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "SENTINEL'S FORTRESS",
                                fontFamily = LEDFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 2.sp
                            )
                            Text(
                                "KAI'S SECURITY DOMAIN â€¢ $styleName",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF00FF85)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { controller.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            useStyleB = !useStyleB
                            GateAssetConfig.toggleKaiStyle()
                        }) {
                            Icon(
                                Icons.Default.SwapHoriz,
                                "Toggle Style",
                                tint = Color(0xFF00FF85)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Security, ROM, bootloader, and root protocols",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // ðŸŽ  SUB-GATE CAROUSEL
                val subGates = GateAssetLoadout.getKaiLoadout()
                DomainSubGateCarousel(
                    subGates = subGates,
                    onGateSelected = { gate ->
                        controller.navigate(gate.route)
                    },
                    useStyleB = useStyleB,
                    cardHeight = 280.dp,
                    domainColor = Color(0xFF00FF85),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "â† SWIPE TO BROWSE â€¢ TAP â‡† TO CHANGE STYLE â†’",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.4f),
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
