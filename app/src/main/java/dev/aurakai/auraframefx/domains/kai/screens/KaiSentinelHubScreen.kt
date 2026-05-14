package dev.aurakai.auraframefx.domains.kai.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.ldo.viewmodel.LdoWarRoomViewModel
import dev.aurakai.auraframefx.ui.components.NeonFrame

@Composable
fun KaiSentinelHubScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: LdoWarRoomViewModel = androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel()
) {
    NeonFrame(color = Color(0xFF00FF85), modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    "KAI SENTINEL FORTRESS",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF00FF85),
                    fontWeight = FontWeight.Bold,
                    fontFamily = LEDFontFamily,
                    letterSpacing = 4.sp
                )
            }

            item { LSPosedModuleToggles() }
            item { RootToolsPanel() }
            item { SovereignShieldStatus() }
            item { BootloaderManagerPanel() }
        }
    }
}

@Composable
fun LSPosedModuleToggles() {
    NeonFrame(color = Color(0xFF00FF85)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "LSPOSED MODULES",
                color = Color(0xFF00FF85),
                fontWeight = FontWeight.Bold,
                fontFamily = LEDFontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Module Audit & Veto Panel Active",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun RootToolsPanel() {
    NeonFrame(color = Color(0xFF9D00FF)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "ROOT TOOLS DASHBOARD",
                color = Color(0xFF9D00FF),
                fontWeight = FontWeight.Bold,
                fontFamily = LEDFontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "APatch / Magisk / KernelSU Unified",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun SovereignShieldStatus() {
    NeonFrame(color = Color(0xFFFF4500)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "SOVEREIGN SHIELD",
                color = Color(0xFFFF4500),
                fontWeight = FontWeight.Bold,
                fontFamily = LEDFontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Perimeter Monitoring: NOMINAL",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun BootloaderManagerPanel() {
    NeonFrame(color = Color(0xFF00E5FF)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "BOOTLOADER MANAGER",
                color = Color(0xFF00E5FF),
                fontWeight = FontWeight.Bold,
                fontFamily = LEDFontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Verified Boot Audit Complete",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
        }
    }
}
