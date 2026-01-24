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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KaiGateScreen(navController: NavController) {
    val cards = listOf(
        GateTile(
            title = "ROM Tools",
            subtitle = "Flash & Manage",
            route = "rom_tools_submenu",
            imageRes = R.drawable.card_rom_tools,
            glowColor = Color(0xFFFF3366)
        ),
        GateTile(
            title = "Bootloader",
            subtitle = "Boot Control",
            route = "bootloader",
            imageRes = R.drawable.card_bootloader,
            glowColor = Color(0xFFFF0066)
        ),
        GateTile(
            title = "Root Tools",
            subtitle = "System Access",
            route = "root_tools",
            imageRes = R.drawable.card_root_tools,
            glowColor = Color(0xFF00FF85)
        ),
        GateTile(
            title = "Security",
            subtitle = "Protection",
            route = "security_center",
            imageRes = R.drawable.card_kai_domain,
            glowColor = Color(0xFF00E5FF)
        ),
        GateTile(
            title = "LSPosed",
            subtitle = "Framework",
            route = "lsposed_panel",
            imageRes = null,
            glowColor = Color(0xFF00E5FF)
        ),
        GateTile(
            title = "System Mods",
            subtitle = "Tweaks",
            route = "system_overrides",
            imageRes = null,
            glowColor = Color(0xFFFF3366)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("KAI GATE", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A1A2E)
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF0F0F1E))
        ) {
            // Floating particles in background
            FloatingParticles(
                particleCount = 20,
                domainColor = Color(0xFFFF3366), // Kai red
                modifier = Modifier.fillMaxSize()
            )
            
            // Card grid on top
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(cards) { card ->
                    GateCardTile(
                        card = card,
                        onClick = { navController.navigate(card.route) }
                    )
                }
            }
        }
    }
}

/**
 * Gate Card Tile with image support
 */
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
