package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.SettingsInputComponent
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.components.BackgroundType
import dev.aurakai.auraframefx.ui.components.CyberpunkScreenScaffold
import dev.aurakai.auraframefx.ui.theme.AgentDomain

/**
 * UI/UX Design Gate Submenu (ChromaCore)
 * Central hub for all visual customization features
 */
@Composable
fun UIUXGateSubmenuScreen(
    navController: NavController
) {
    val menuItems = listOf(
        SubmenuItem(
            title = "Theme Engine",
            description = "Customize system colors, fonts, and styles",
            icon = Icons.Default.Palette,
            route = NavDestination.ThemeEngine.route,
            color = Color(0xFFFF00FF) // Magenta
        ),
        SubmenuItem(
            title = "Notch Bar",
            description = "Adjust notch height, style, and visibility",
            icon = Icons.Default.Smartphone,
            route = NavDestination.NotchBar.route,
            color = Color(0xFF00FFFF) // Cyan
        ),
        SubmenuItem(
            title = "Status Bar",
            description = "Configure icons, clock, and battery styles",
            icon = Icons.Default.Wifi,
            route = NavDestination.StatusBar.route,
            color = Color(0xFF00FF00) // Green
        ),
        SubmenuItem(
            title = "Quick Settings",
            description = "Modify quick settings tiles and layout",
            icon = Icons.Default.SettingsInputComponent,
            route = NavDestination.QuickSettings.route,
            color = Color(0xFF00FFFF) // Gold
        ),
        SubmenuItem(
            title = "Overlay Menus",
            description = "Manage floating bubbles and sidebars",
            icon = Icons.Default.Layers,
            route = NavDestination.OverlayMenus.route,
            color = Color(0xFFFF4500) // Orange Red
        ),
        SubmenuItem(
            title = "3D Customization Lab",
            description = "Gyroscope-controlled 3D component editor",
            icon = Icons.Default.ViewInAr,
            route = "gyroscope_customization", // Using route string directly
            color = Color(0xFF00B4D8) // Futuristic Blue
        )
    )

    // Using Level 3 "Wild" Aesthetic: CHAOS_LIGHTNING (or similar placeholder)
    CyberpunkScreenScaffold(
        title = "UI/UX Design Gate",
        subtitle = "ChromaCore System",
        agentDomain = AgentDomain.AURA,
        backgroundType = BackgroundType.CHAOS_LIGHTNING,
        showHudOverlay = true, // Added HUD for extra flair
        onNavigateBack = { navController.popBackStack() }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
        ) {
            items(menuItems) { item ->
                SubmenuCard(item = item, onClick = { navController.navigate(item.route) })
            }
        }
    }
}
