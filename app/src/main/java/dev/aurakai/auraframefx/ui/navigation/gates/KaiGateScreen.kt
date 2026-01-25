package dev.aurakai.auraframefx.ui.navigation.gates

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.ui.navigation.gates.common.GateTile
import dev.aurakai.auraframefx.ui.navigation.gates.effects.FloatingParticles

/**
 * 🛡️ KAI GATE SCREEN
 * Level 2 navigation - Security & System Control
 * Domain: ROM Management, Bootloader, Root Access, System Security
 * Personality: Structured, protective, fortress aesthetic!
 */
import dev.aurakai.auraframefx.ui.components.hologram.AnimeHUDContainer
import dev.aurakai.auraframefx.ui.components.hologram.HolographicCard
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KaiGateScreen(navController: NavController) {
    val cards = listOf(
        GateTile(
            title = "ROM TOOLS",
            subtitle = "Flash & Manage",
            route = "rom_tools_submenu",
            imageRes = R.drawable.rune_sentinel,
            glowColor = Color(0xFFFF3366)
        ),
        GateTile(
            title = "BOOTLOADER",
            subtitle = "Boot Control",
            route = "bootloader",
            imageRes = R.drawable.rune_sentinel,
            glowColor = Color(0xFFFF0066)
        ),
        GateTile(
            title = "ROOT ACCESS",
            subtitle = "System Toggles",
            route = "root_tools",
            imageRes = R.drawable.rune_sentinel,
            glowColor = Color(0xFF00FF85)
        ),
        GateTile(
            title = "SECURITY CENTER",
            subtitle = "Fortress Control",
            route = "security_center",
            imageRes = R.drawable.rune_sentinel,
            glowColor = Color(0xFF00E5FF)
        ),
        GateTile(
            title = "LSPOSED PANEL",
            subtitle = "Hook Framework",
            route = "lsposed_panel",
            imageRes = R.drawable.rune_cortex,
            glowColor = Color(0xFF00E5FF)
        ),
        GateTile(
            title = "SYSTEM MODS",
            subtitle = "Override Engine",
            route = "system_overrides",
            imageRes = R.drawable.rune_surgeon,
            glowColor = Color(0xFFFF3366)
        )
    )

    AnimeHUDContainer(
        title = "KAI DOMAIN: SENTINEL CORE",
        description = "SYSTEM INTEGRITY VERIFIED. ACCESSING SECURITY LAYER. ALL MODIFICATIONS SUBJECT TO KAI PROTOCOLS.",
        glowColor = Color(0xFFFF3366) // Kai red
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(24.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(cards) { card ->
                Box(
                    modifier = Modifier
                        .clickable { navController.navigate(card.route) }
                        .aspectRatio(0.75f),
                    contentAlignment = Alignment.Center
                ) {
                    HolographicCard(
                        runeRes = card.imageRes ?: R.drawable.rune_sentinel,
                        glowColor = card.glowColor,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Overlay label in LED font
                    Text(
                        text = card.title,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 20.dp),
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 10.sp,
                        fontFamily = dev.aurakai.auraframefx.ui.theme.LEDFontFamily
                    )
                }
            }
        }
    }
}

@Composable
private fun GateCardTile(
    card: GateTile,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        card.glowColor.copy(alpha = 0.8f),
                        card.glowColor.copy(alpha = 0.3f)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color(0xFF1A1A2E))
            .clickable(onClick = onClick)
    ) {
        // Background image if available
        if (card.imageRes != null) {
            Image(
                painter = painterResource(id = card.imageRes),
                contentDescription = card.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(1.dp)
            )

            // Gradient scrim overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xFF1A1A2E).copy(alpha = 0.7f),
                                Color(0xFF1A1A2E)
                            )
                        )
                    )
            )
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = card.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = card.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = card.glowColor.copy(alpha = 0.8f)
            )
        }
    }
}
