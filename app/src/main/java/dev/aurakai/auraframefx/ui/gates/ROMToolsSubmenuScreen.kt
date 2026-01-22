package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.navigation.NavDestination
import dev.aurakai.auraframefx.ui.components.unified.*

/**
 * 🔥 ROM TOOLS SUBMENU - Redesigned
 *
 * Fluid, immersive interface for ROM management
 * Features: Live editing, flashing, recovery tools
 */
@Composable
fun ROMToolsSubmenuScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AuraColors.BackgroundDeepest,
                        AuraColors.BackgroundDeep,
                        AuraColors.BackgroundMid
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(AuraSpacing.md),
            verticalArrangement = Arrangement.spacedBy(AuraSpacing.lg)
        ) {
            // Hero Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AuraSpacing.sm),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AuraSpacing.lg)
            ) {
                Text(
                    text = "ROM TOOLS",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 40.sp,
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                AuraColors.NeonOrange,
                                AuraColors.NeonPink,
                                AuraColors.NeonOrange
                            )
                        )
                    )
                )

                Text(
                    text = "Live ROM Editing & Flashing Suite",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = AuraColors.TextSecondary
                )
            }

            // Critical Warning Banner
            WarningBanner(
                message = "These tools can brick your device. Backup your data first!",
                severity = BannerSeverity.DANGER
            )

            // Core ROM Operations
            SectionHeader(
                title = "Core Operations",
                subtitle = "Essential ROM management tools",
                glowColor = AuraColors.NeonOrange
            )

            FluidMenuItem(
                title = "ROM Flasher",
                subtitle = "Flash custom ROMs with Genesis retention",
                icon = {
                    Icon(
                        Icons.Default.Build,
                        contentDescription = null,
                        tint = AuraColors.NeonOrange,
                        modifier = Modifier.size(24.dp)
                    )
                },
                glowColor = AuraColors.NeonOrange,
                showWarning = true,
                onClick = { navController.navigate(NavDestination.ROMFlasher.route) }
            )

            FluidMenuItem(
                title = "Live ROM Editor",
                subtitle = "Real-time system file editing",
                icon = {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = null,
                        tint = AuraColors.NeonCyan,
                        modifier = Modifier.size(24.dp)
                    )
                },
                glowColor = AuraColors.NeonCyan,
                showWarning = true,
                onClick = { navController.navigate(NavDestination.LiveROMEditor.route) }
            )

            FluidMenuItem(
                title = "Recovery Tools",
                subtitle = "TWRP integration & emergency recovery",
                icon = {
                    Icon(
                        Icons.Default.HealthAndSafety,
                        contentDescription = null,
                        tint = AuraColors.NeonGreen,
                        modifier = Modifier.size(24.dp)
                    )
                },
                glowColor = AuraColors.NeonGreen,
                onClick = { navController.navigate(NavDestination.RecoveryTools.route) }
            )

            // Bootloader & System
            SectionHeader(
                title = "Bootloader & System",
                subtitle = "Low-level device management",
                glowColor = AuraColors.NeonPurple
            )

            FluidMenuItem(
                title = "Bootloader Manager",
                subtitle = "Safe bootloader diagnostics & unlock (requires manual steps)",
                icon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                        tint = AuraColors.NeonPurple,
                        modifier = Modifier.size(24.dp)
                    )
                },
                glowColor = AuraColors.NeonPurple,
                showWarning = true,
                onClick = { navController.navigate(NavDestination.BootloaderManager.route) }
            )

            FluidMenuItem(
                title = "Root Tools & Toggles",
                subtitle = "Manage root access and system permissions",
                icon = {
                    Icon(
                        Icons.Default.AdminPanelSettings,
                        contentDescription = null,
                        tint = AuraColors.NeonMagenta,
                        modifier = Modifier.size(24.dp)
                    )
                },
                glowColor = AuraColors.NeonMagenta,
                onClick = { navController.navigate(NavDestination.RootToolsToggles.route) }
            )

            FluidMenuItem(
                title = "System Overrides",
                subtitle = "Build.prop tweaks & Genesis optimizations",
                icon = {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = null,
                        tint = AuraColors.NeonBlue,
                        modifier = Modifier.size(24.dp)
                    )
                },
                glowColor = AuraColors.NeonBlue,
                onClick = { navController.navigate(NavDestination.SystemOverrides.route) }
            )

            // Info Banner
            WarningBanner(
                message = "All operations are logged in System Journal for audit trail",
                severity = BannerSeverity.INFO
            )

            // Back Button
            Spacer(modifier = Modifier.height(AuraSpacing.md))

            FluidGlassCard(
                glowColor = AuraColors.NeonCyan,
                glowIntensity = 0.2f,
                onClick = { navController.popBackStack() }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = AuraColors.NeonCyan,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(AuraSpacing.xs))
                    Text(
                        text = "Back to Gate Navigation",
                        style = MaterialTheme.typography.titleSmall,
                        color = AuraColors.TextPrimary
                    )
                }
            }

            // Bottom padding
            Spacer(modifier = Modifier.height(AuraSpacing.xl))
        }
    }
}
