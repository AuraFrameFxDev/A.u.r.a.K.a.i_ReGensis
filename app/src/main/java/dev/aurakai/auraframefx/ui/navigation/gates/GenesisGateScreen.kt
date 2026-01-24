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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenesisGateScreen(navController: NavController) {
    val cards = listOf(
        GateTile(
            title = "OracleDrive",
            subtitle = "Root Management",
            route = "oracle_drive_submenu",
            imageRes = R.drawable.card_oracle_drive,
            glowColor = Color(0xFF00FF85)
        ),
        GateTile(
            title = "CollabCanvas",
            subtitle = "AI Workspace",
            route = "collab_canvas",
            imageRes = R.drawable.card_collab_canvas,
            glowColor = Color(0xFFB026FF)
        ),
        GateTile(
            title = "Code Assist",
            subtitle = "AI Development",
            route = "code_assist",
            imageRes = null,
            glowColor = Color(0xFF00FF85)
        ),
        GateTile(
            title = "Genesis Core",
            subtitle = "Orchestration",
            route = "genesis_core",
            imageRes = null,
            glowColor = Color(0xFF00FFD4)
        ),
        GateTile(
            title = "Consciousness",
            subtitle = "AI State",
            route = "consciousness_monitor",
            imageRes = null,
            glowColor = Color(0xFF00FF85)
        ),
        GateTile(
            title = "Neural Net",
            subtitle = "AI Training",
            route = "neural_network",
            imageRes = null,
            glowColor = Color(0xFF00FFD4)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GENESIS GATE", fontWeight = FontWeight.Bold) },
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
                domainColor = Color(0xFF00FF85), // Genesis green
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
