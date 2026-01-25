package dev.aurakai.auraframefx.ui.navigation.gates

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.ui.navigation.gates.common.GateTile
import dev.aurakai.auraframefx.ui.navigation.gates.effects.FloatingParticles
import dev.aurakai.auraframefx.ui.components.hologram.AnimeHUDContainer
import dev.aurakai.auraframefx.ui.components.hologram.HolographicCard

/**
 * 🎨 AURA GATE SCREEN
 * Level 2 navigation with beautiful holographic card tiles
 * Domain: Creative UI/UX and Theme Customization
 * Personality: Chaotic, spunky, dive-right-in creative energy!
 */
import dev.aurakai.auraframefx.ui.components.carousel.DomainGlobeCarousel
import dev.aurakai.auraframefx.ui.components.carousel.GlobeItem

import dev.aurakai.auraframefx.ui.components.WoodsyPlainsBackground

import dev.aurakai.auraframefx.ui.components.hologram.CardStyle

import androidx.compose.runtime.*
import dev.aurakai.auraframefx.navigation.NavDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuraGateScreen(navController: NavController) {
    val items = listOf(
        GlobeItem(
            title = "UXUI DESIGN STUDIO",
            description = "The central design hub for ReGenesis. Access the Aura Theming Engine, ChromaCore, and advanced UI overrides.",
            route = NavDestination.AuraThemingHub.route,
            runeRes = R.drawable.card_chroma_core,
            glowColor = Color(0xFFFF00FF),
            style = CardStyle.ARTSY
        ),
        GlobeItem(
            title = "AURA LAB",
            description = "Technical sandbox for live UI experimentation, sandbox testing, and collaborative drafting.",
            route = NavDestination.AuraLab.route,
            runeRes = R.drawable.card_collab_canvas,
            glowColor = Color(0xFF00E5FF),
            style = CardStyle.ARTSY
        )
    )

    var currentItem by remember { mutableStateOf(items[0]) }

    Box(modifier = Modifier.fillMaxSize()) {
        WoodsyPlainsBackground()
        
        AnimeHUDContainer(
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

/**
 * Gate Card Tile with image support
 */
@Composable
private fun GateCardTile(
    card: GateTile,
    onClick: () -> Unit,
    index: Int = 0
) {
    // Cards already have built-in angles in the images!
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
