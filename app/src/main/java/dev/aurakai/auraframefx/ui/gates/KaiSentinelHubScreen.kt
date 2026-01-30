package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
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
import dev.aurakai.auraframefx.config.GateAssetConfig
import dev.aurakai.auraframefx.ui.components.DomainSubGateCarousel
import dev.aurakai.auraframefx.ui.components.IcyTundraBackground
import dev.aurakai.auraframefx.ui.components.ShieldGridBackground
import dev.aurakai.auraframefx.ui.components.getKaiSubGates
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🛡️ KAI'S SENTINEL'S FORTRESS (Level 2 Hub)
 *
 * ANIMATIONS:
 * Style A (Pixel Fortress): IcyTundraBackground - Ice crystals, shimmer, cold fortress
 * Style B (Cyber Security): ShieldGridBackground - Hex grid, shield pulses, scanning
 *
 * TWO VISUAL STYLES:
 * Style A: "Pixel Fortress" - Retro pixel art, armored guards, stone
 * Style B: "Cyber Security" - Matrix rain, lightning, red neon frames
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KaiSentinelHubScreen(navController: NavController) {

    val subGates = getKaiSubGates()

    var useStyleB by remember {
        mutableStateOf(GateAssetConfig.StyleMode.kaiStyle == GateAssetConfig.GateStyle.STYLE_B)
    }

    val styleName = if (useStyleB) "CYBER SECURITY" else "PIXEL FORTRESS"

    Box(modifier = Modifier.fillMaxSize()) {
        // 🛡️ KAI'S ANIMATED BACKGROUND - Changes with style!
        if (useStyleB) {
            ShieldGridBackground(
                primaryColor = Color(0xFFFF6B00),  // Orange shields
                secondaryColor = Color(0xFF00FF85) // Green scan line
            )
        } else {
            IcyTundraBackground()
        }

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
                                "KAI'S SECURITY DOMAIN • $styleName",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF00FF85)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
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

                Text(
                    text = "System security, bootloader, and root management",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // 🎠 SUB-GATE CAROUSEL
                DomainSubGateCarousel(
                    subGates = subGates,
                    onGateSelected = { gate ->
                        navController.navigate(gate.route)
                    },
                    useStyleB = useStyleB,
                    cardHeight = 280.dp,
                    domainColor = Color(0xFF00FF85),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "← SWIPE TO BROWSE • TAP ⇆ TO CHANGE STYLE →",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.4f),
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
