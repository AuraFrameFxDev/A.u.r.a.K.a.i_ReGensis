package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Brush
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.config.GateAssetConfig
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.components.ThemingCard
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 🎨 AURA'S UXUI DESIGN STUDIO (Level 2 Hub)
 *
 * Features a grid of design tools.
 * Control all system-level design parameters from this centralized hub.
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
            title = "Aura Lab",
            subtitle = "Sandbox UI & Kinetic Testing",
            destination = NavDestination.AuraLab,
            accentColor = Color(0xFF00E5FF)
        ),
        ThemingToolCard(
            title = "Overlay Systems",
            subtitle = "Floating Bubbles & Sidebars",
            destination = NavDestination.OverlayMenus,
            accentColor = Color(0xFFFF4500)
        ),

        ThemingToolCard(
            title = "Kinetic Engine",
            subtitle = "3D Gyroscope Calibration",
            destination = NavDestination.Gyroscope,
            accentColor = Color(0xFF00E5FF)
        )
    )

    var useStyleB by remember {
        mutableStateOf(GateAssetConfig.StyleMode.auraStyle == GateAssetConfig.GateStyle.STYLE_B)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Artistic Background
        dev.aurakai.auraframefx.ui.components.PaintSplashBackground()

        // Overlay for depth
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.7f)
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
                                "UI DESIGN STUDIO",
                                fontFamily = LEDFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 2.sp
                            )
                            Text(
                                "AURA'S CREATIVE DOMAIN",
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
                    actions = {
                        IconButton(onClick = {
                            useStyleB = !useStyleB
                            GateAssetConfig.toggleAuraStyle()
                        }) {
                            Icon(
                                Icons.Default.SwapHoriz,
                                "Toggle Style",
                                tint = Color(0xFF00E5FF)
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
