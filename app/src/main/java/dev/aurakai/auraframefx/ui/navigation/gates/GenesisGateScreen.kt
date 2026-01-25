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
 * 🧠 GENESIS GATE SCREEN
 * Level 2 navigation - AI Orchestration & Development
 * Domain: Oracle Drive, Collaborative AI, Code Intelligence
 * Personality: Godly, manager's office, command center aesthetic!
 */
import dev.aurakai.auraframefx.ui.components.carousel.DomainGlobeCarousel
import dev.aurakai.auraframefx.ui.components.carousel.GlobeItem

import dev.aurakai.auraframefx.ui.components.LavaApocalypseBackground

import dev.aurakai.auraframefx.ui.components.hologram.CardStyle

import androidx.compose.runtime.*
import dev.aurakai.auraframefx.navigation.NavDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenesisGateScreen(navController: NavController) {
    val items = listOf(
        GlobeItem(
            title = "ORACLE DRIVE",
            description = "The central intelligence drive. Manage core system patterns, AI model weights, and neural memory archives within the Boss system.",
            route = NavDestination.OracleDriveHub.route,
            runeRes = R.drawable.card_oracle_drive,
            glowColor = Color(0xFF00FF85),
            style = CardStyle.MYTHICAL
        ),
        GlobeItem(
            title = "AGENT BRIDGE",
            description = "Universal orchestrator and bridge interface. Synchronize multi-agent tasks and monitor neural progression metrics.",
            route = NavDestination.AgentBridgeHub.route,
            runeRes = R.drawable.gate_oracledrive_final,
            glowColor = Color(0xFF00FFD4),
            style = CardStyle.MYTHICAL
        )
    )

    var currentItem by remember { mutableStateOf(items[0]) }

    Box(modifier = Modifier.fillMaxSize()) {
        LavaApocalypseBackground()
        
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
        if (card.imageRes != null) {
            Image(
                painter = painterResource(id = card.imageRes),
                contentDescription = card.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(1.dp)
            )

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
