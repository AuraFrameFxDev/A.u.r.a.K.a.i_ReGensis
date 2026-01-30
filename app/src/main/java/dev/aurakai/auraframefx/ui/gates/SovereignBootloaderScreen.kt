package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.ui.components.GridMenuItem
import dev.aurakai.auraframefx.ui.components.Level3GridMenu
import dev.aurakai.auraframefx.ui.components.ShieldGridBackground

/**
 * 🔐 BOOTLOADER - LVL 3 GRID MENU
 *
 * Kai's Bootloader Tools - Full menu
 *
 * ANIMATION: ShieldGridBackground
 * - Orange hex shield grid
 * - Green scanning line
 * - Protective, security vibes
 *
 * Features:
 * - Lock/Unlock bootloader
 * - Fastboot commands
 * - OEM unlock status
 * - AVB verification
 * - Critical warnings system
 */
@Composable
fun SovereignBootloaderScreen(
    onNavigateBack: () -> Unit
) {
    val menuItems = listOf(
        GridMenuItem(
            id = "unlock_bootloader",
            title = "Unlock Bootloader",
            subtitle = "⚠️ DANGEROUS - Wipes Data",
            icon = Icons.Default.LockOpen,
            route = "unlock_bootloader",
            accentColor = Color(0xFFFF1111)
        ),
        GridMenuItem(
            id = "lock_bootloader",
            title = "Lock Bootloader",
            subtitle = "Re-lock for Security",
            icon = Icons.Default.Lock,
            route = "lock_bootloader",
            accentColor = Color(0xFF00FF85)
        ),
        GridMenuItem(
            id = "fastboot_tools",
            title = "Fastboot Tools",
            subtitle = "Command Interface",
            icon = Icons.Default.Terminal,
            route = "fastboot_tools",
            accentColor = Color(0xFF00E5FF)
        ),
        GridMenuItem(
            id = "oem_status",
            title = "OEM Unlock Status",
            subtitle = "Check Current State",
            icon = Icons.Default.Info,
            route = "oem_status",
            accentColor = Color(0xFFFFCC00)
        ),
        GridMenuItem(
            id = "avb_verify",
            title = "AVB Verification",
            subtitle = "Android Verified Boot",
            icon = Icons.Default.VerifiedUser,
            route = "avb_verify",
            accentColor = Color(0xFF00FF85)
        ),
        GridMenuItem(
            id = "flash_boot",
            title = "Flash Boot Image",
            subtitle = "Custom Boot/Recovery",
            icon = Icons.Default.SystemUpdate,
            route = "flash_boot",
            accentColor = Color(0xFFFF3366)
        ),
        GridMenuItem(
            id = "backup_boot",
            title = "Backup Boot",
            subtitle = "Save Current Boot Image",
            icon = Icons.Default.Backup,
            route = "backup_boot",
            accentColor = Color(0xFF00E5FF)
        ),
        GridMenuItem(
            id = "device_info",
            title = "Device Info",
            subtitle = "Hardware & Firmware",
            icon = Icons.Default.PhoneAndroid,
            route = "device_info",
            accentColor = Color(0xFFB026FF)
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // 🛡️ ANIMATED BACKGROUND - Shield grid for Bootloader!
        ShieldGridBackground(
            primaryColor = Color(0xFFFF6B00),  // Orange shields
            secondaryColor = Color(0xFF00FF85) // Green scan line
        )

        Level3GridMenu(
            title = "BOOTLOADER",
            subtitle = "KAI'S SECURITY PROTOCOLS",
            menuItems = menuItems,
            onItemClick = { item ->
                // Handle bootloader operations
                // These need root/fastboot access
            },
            onBackClick = onNavigateBack,
            backgroundDrawable = null, // Using animated background
            fallbackGradient = listOf(Color.Transparent),
            accentColor = Color(0xFFFF1111)
        )
    }
}
