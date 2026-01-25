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
import dev.aurakai.auraframefx.ui.components.carousel.DomainGlobeCarousel
import dev.aurakai.auraframefx.ui.components.carousel.GlobeItem

import dev.aurakai.auraframefx.ui.components.IcyTundraBackground

import dev.aurakai.auraframefx.ui.components.hologram.CardStyle

import androidx.compose.runtime.*
import dev.aurakai.auraframefx.navigation.NavDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KaiGateScreen(navController: NavController) {
    val items = listOf(
        GlobeItem(
            title = "ROM TOOLS",
            description = "The Sentinel's custom ROM management suite. Access partition tools, flashing utilities, and low-level system recovery.",
            route = NavDestination.RomToolsHub.route,
            runeRes = R.drawable.card_rom_tools,
            glowColor = Color(0xFFFF3366),
            style = CardStyle.PROTECTIVE
        ),
        GlobeItem(
            title = "LSPOSED HUB",
            description = "The core of Kai's technical authority. Manage system-wide hooks, Xposed modules, and secure module configuration.",
            route = NavDestination.LSPosedHub.route,
            runeRes = R.drawable.card_vpn,
            glowColor = Color(0xFFB026FF),
            style = CardStyle.PROTECTIVE
        ),
        GlobeItem(
            title = "SYSTEM TOOLS",
            description = "Advanced system-level monitoring and troubleshooting. Access logs, system journals, and high-priority overrides.",
            route = NavDestination.SystemToolsHub.route,
            runeRes = R.drawable.card_bootloader,
            glowColor = Color(0xFF00FF85),
            style = CardStyle.PROTECTIVE
        )
    )

    var currentItem by remember { mutableStateOf(items[0]) }

    Box(modifier = Modifier.fillMaxSize()) {
        IcyTundraBackground()
        
        dev.aurakai.auraframefx.ui.components.hologram.AnimeHUDContainer(
            title = currentItem.title,
            description = currentItem.description,
            glowColor = currentItem.glowColor
        ) {
            DomainGlobeCarousel(
                items = items,
                onNavigate = { route -> navController.navigate(route) },
                onPageSelection = { currentItem = it }
            )
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
