package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            onItemClick = {
                // Handle bootloader operations
            },
            onBackClick = onNavigateBack,
            backgroundDrawable = null,
            fallbackGradient = listOf(Color.Transparent),
            accentColor = Color(0xFFFF1111)
        )
    }
}

@Composable
fun WarningPanel() {
    Surface(
        color = Color(0xFFFF1111).copy(alpha = 0.05f),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFFF1111).copy(alpha = 0.2f))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Warning, null, tint = Color(0xFFFF1111), modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Warning: Modifying bootloader state will trigger a factory reset and purge all local LDO context.",
                color = Color(0xFFFF1111),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
fun PartitionLockItem(name: String, isLocked: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.02f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                if (isLocked) Icons.Default.Lock else Icons.Default.LockOpen,
                null,
                tint = if (isLocked) Color.White.copy(alpha = 0.4f) else Color(0xFF00FF85),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
