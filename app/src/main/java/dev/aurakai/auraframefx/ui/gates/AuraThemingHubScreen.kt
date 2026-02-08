package dev.aurakai.auraframefx.ui.gates

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
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.components.WoodsyPlainsBackground
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🎨 AURA THEME ENGINE HUB (Level 3)
 * The main control panel for the entire theming and UI customization suite.
 * High-fidelity, framework-style consolidated entry point.
 */

data class ThemingToolCard(
    val title: String,
    val subtitle: String,
    val destination: NavDestination,
    val isWired: Boolean = true,
    val accentColor: Color = Color(0xFFFF00FF)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuraThemingHubScreen(navController: NavController) {
    
    val tools = listOf(
        ThemingToolCard(
            title = "ChromaCore Colors",
            subtitle = "System Color Engine & Monet",
            destination = NavDestination.ChromaCoreColors,
            accentColor = Color(0xFFB026FF)
        ),
        ThemingToolCard(
            title = "Theme Engine",
            subtitle = "Visual Style & Theming Control",
            destination = NavDestination.ThemeEngine,
            accentColor = Color(0xFFFF00FF)
        ),
        ThemingToolCard(
            title = "Notch Bar Manager",
            subtitle = "Dynamic Edge/Notch Customization",
            destination = NavDestination.NotchBar,
            accentColor = Color(0xFFFF1493)
        ),
        ThemingToolCard(
            title = "Status Bar Tweaks",
            subtitle = "Icon & Layout Customization",
            destination = NavDestination.StatusBar,
            accentColor = Color(0xFF00E5FF)
        ),
        ThemingToolCard(
            title = "Quick Settings Panel",
            subtitle = "Tile Management & Overrides",
            destination = NavDestination.QuickSettings,
            accentColor = Color(0xFF00FF85)
        ),
        ThemingToolCard(
            title = "Collab Canvas",
            subtitle = "Design with Aura & Kai",
            destination = NavDestination.CollabCanvas,
            accentColor = Color(0xFFB026FF)
        ),
        ThemingToolCard(
            title = "Aura Lab",
            subtitle = "Sandbox UI & Kinetic Testing",
            destination = NavDestination.AuraLab,
            accentColor = Color(0xFF00E5FF)
        ),
        ThemingToolCard(
            title = "Iconify Picker",
            subtitle = "Service Injection (TODO)",
            destination = NavDestination.IconifyPicker,
            isWired = false,
            accentColor = Color.Gray
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // High-Fidelity Background
        WoodsyPlainsBackground()
        
        // Semi-transparent Overlay for "Studio" feel
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)))

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { 
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "UI DESIGN STUDIO", 
                                fontFamily = LEDFontFamily, 
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 2.sp
                            )
                            Text(
                                "THEMING ENGINE SUITE", 
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFFF00FF)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
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
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Header Info
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black.copy(alpha = 0.3f))
                        .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Brush, null, tint = Color(0xFFFF00FF), modifier = Modifier.size(32.dp))
                        Spacer(Modifier.width(16.dp))
                        Text(
                            "Welcome to the Unified Framework. Control all system-level design parameters from this centralized hub.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(tools) { tool ->
                        ThemingCard(tool = tool) {
                            if (tool.isWired) {
                                navController.navigate(tool.destination.route)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThemingCard(tool: ThemingToolCard, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    listOf(tool.accentColor.copy(alpha = 0.5f), Color.Transparent)
                ),
                shape = RoundedCornerShape(20.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.6f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(tool.accentColor.copy(alpha = 0.2f))
                    .border(1.dp, tool.accentColor.copy(alpha = 0.4f), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Brush, // Generic icon for now
                    null,
                    tint = tool.accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Column {
                Text(
                    text = tool.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = tool.subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (tool.isWired) Color.White.copy(alpha = 0.6f) else Color.Red.copy(alpha = 0.6f)
                )
            }
        }
    }
}
