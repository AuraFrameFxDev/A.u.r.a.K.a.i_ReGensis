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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.config.GateAssetConfig
import dev.aurakai.auraframefx.ui.components.DomainSubGateCarousel
import dev.aurakai.auraframefx.ui.components.IcyTundraBackground
import dev.aurakai.auraframefx.ui.components.getKaiSubGates
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🛡️ KAI'S SENTINEL'S FORTRESS (Level 2 Hub)
 *
 * TWO VISUAL STYLES:
 * Style A: "Pixel Fortress" - Retro pixel art, armored guards, stone
 * Style B: "Cyber Security" - Matrix rain, lightning, red neon frames
 *
 * Tap the swap icon to toggle between styles!
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KaiSentinelHubScreen(navController: NavController) {

    val subGates = getKaiSubGates()

    // Track current style (remember survives recomposition)
    var useStyleB by remember {
        mutableStateOf(GateAssetConfig.StyleMode.kaiStyle == GateAssetConfig.GateStyle.STYLE_B)
    }

    val styleName = if (useStyleB) "CYBER SECURITY" else "PIXEL FORTRESS"

    Box(modifier = Modifier.fillMaxSize()) {
        // Fortress Background - Icy/Dark theme
        IcyTundraBackground()

        // Dark overlay for fortress feel
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF0A1A0A).copy(alpha = 0.5f),
                            Color.Black.copy(alpha = 0.8f)
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
                        // Style Toggle Button
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

                // Domain Description
                Text(
                    text = "System security, bootloader, and root management",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // 🎠 SUB-GATE CAROUSEL with style toggle!
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

                // Hint text with style indicator
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
