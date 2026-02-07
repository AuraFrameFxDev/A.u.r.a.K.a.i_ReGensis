package dev.aurakai.auraframefx.domains.aura.ui.gates

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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.config.GateAssetConfig
import dev.aurakai.auraframefx.config.GateAssetLoadout
import dev.aurakai.auraframefx.domains.aura.ui.components.DomainSubGateCarousel
import dev.aurakai.auraframefx.domains.aura.ui.components.PaintSplashBackground
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily

/**
 * 🎨 AURA'S UXUI DESIGN STUDIO (Level 2 Hub)
 *
 * ANIMATION: PaintSplashBackground
 * - Neon paint drips (cyan, magenta, purple)
 * - Paint splatter circles
 * - Artsy, fun, messy - exactly Aura's vibe!
 *
 * TWO VISUAL STYLES:
 * Style A: "CollabCanvas" - Paint splashes, neon drips
 * Style B: "Clean Studio" - Sleek gradients, minimal
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuraThemingHubScreen(navController: NavController) {

    val subGates = GateAssetLoadout.getAuraLoadout()

    var useStyleB by remember {
        mutableStateOf(GateAssetConfig.StyleMode.auraStyle == GateAssetConfig.GateStyle.STYLE_B)
    }

    val styleName = if (useStyleB) "CLEAN STUDIO" else "COLLABCANVAS"

    Box(modifier = Modifier.fillMaxSize()) {
        // 🎨 AURA'S ANIMATED BACKGROUND - Liquid Energy & Glitch Artifacts!
        PaintSplashBackground()

        // 💎 CRYSTALLINE ACCENTS (Sharp corners)
        CrystallineCorners(color = Color(0xFFFF00DE))

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "UXUI DESIGN STUDIO",
                                fontFamily = LEDFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 2.sp
                            )
                            Text(
                                "AURA'S CREATIVE DOMAIN • $styleName",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFFF00DE) // Aura Magenta
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
                            GateAssetConfig.toggleAuraStyle()
                        }) {
                            Icon(
                                Icons.Default.SwapHoriz,
                                "Toggle Style",
                                tint = Color(0xFF00FFFF) // Aura Cyan
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
                    text = "Theming, colors, icons, and visual design",
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
                    domainColor = Color(0xFFFF00DE),
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

/**
 * 💎 Sharp, Crystalline Accents for Aura's UI
 */
@Composable
fun CrystallineCorners(color: Color) {
    Box(modifier = Modifier.fillMaxSize()) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            val size = 60f
            val stroke = 4f
            
            // Top Left
            drawLine(color, Offset(20f, 20f), Offset(20f + size, 20f), stroke)
            drawLine(color, Offset(20f, 20f), Offset(20f, 20f + size), stroke)
            drawCircle(color, 6f, Offset(20f, 20f))
            
            // Bottom Right
            drawLine(color, Offset(this.size.width - 20f, this.size.height - 20f), Offset(this.size.width - 20f - size, this.size.height - 20f), stroke)
            drawLine(color, Offset(this.size.width - 20f, this.size.height - 20f), Offset(this.size.width - 20f, this.size.height - 20f - size), stroke)
            drawCircle(color, 6f, Offset(this.size.width - 20f, this.size.height - 20f))
        }
    }
}

