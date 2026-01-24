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
 * 📚 HELP SERVICES GATE SCREEN
 * Level 2 navigation - Documentation & Support
 * Domain: Help Desk, Tutorials, FAQs, Live Support
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpServicesGateScreen(navController: NavController) {
    val cards = listOf(
        GateTile(
            title = "Help Desk",
            subtitle = "Support Center",
            route = "help_desk_submenu",
            imageRes = R.drawable.card_help_services,
            glowColor = Color(0xFF00B8FF)
        ),
        GateTile(
            title = "Documentation",
            subtitle = "User Guide",
            route = "documentation",
            imageRes = null,
            glowColor = Color(0xFF00E5FF)
        ),
        GateTile(
            title = "Tutorials",
            subtitle = "Video Guides",
            route = "tutorial_videos",
            imageRes = null,
            glowColor = Color(0xFF00B8FF)
        ),
        GateTile(
            title = "FAQ Browser",
            subtitle = "Quick Answers",
            route = "faq_browser",
            imageRes = null,
            glowColor = Color(0xFF00E5FF)
        ),
        GateTile(
            title = "Live Help",
            subtitle = "Chat Support",
            route = "live_help",
            imageRes = null,
            glowColor = Color(0xFF00B8FF)
        ),
        GateTile(
            title = "Feedback",
            subtitle = "Report Issues",
            route = "feedback",
            imageRes = null,
            glowColor = Color(0xFF00E5FF)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HELP SERVICES GATE", fontWeight = FontWeight.Bold) },
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
                domainColor = Color(0xFF00B8FF), // Help Services cyan
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
