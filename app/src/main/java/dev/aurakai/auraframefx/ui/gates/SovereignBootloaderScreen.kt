package dev.aurakai.auraframefx.ui.gates

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.aurakai.auraframefx.ui.components.GridMenuItem
import dev.aurakai.auraframefx.ui.components.Level3GridMenu

/**
 * 🔐 BOOTLOADER - LVL 3 GRID MENU
 *
 * Kai's Bootloader Tools - Full menu with shield/security themed background
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

    Level3GridMenu(
        title = "BOOTLOADER",
        subtitle = "KAI'S SECURITY PROTOCOLS",
        menuItems = menuItems,
        onItemClick = { item ->
            // Handle bootloader operations
            // These need root/fastboot access
        },
        onBackClick = onNavigateBack,
        // Shield/security themed background
        backgroundDrawable = "bg_bootloader_shield",
        fallbackGradient = listOf(
            Color(0xFF1A0A0A),
            Color(0xFF2D1010),
            Color(0xFF0A0A1A)
        ),
        accentColor = Color(0xFFFF1111)
    )
}
