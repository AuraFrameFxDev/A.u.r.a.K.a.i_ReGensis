package dev.aurakai.auraframefx.domains.aura.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aurakai.auraframefx.domains.ldo.viewmodel.LdoWarRoomViewModel
import dev.aurakai.auraframefx.ui.components.NeonFrame
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily

@Composable
fun AurasLabScreen(
    onBack: () -> Unit = {},
    viewModel: LdoWarRoomViewModel = hiltViewModel()
) {
    val godPotential by viewModel.godPotential.collectAsState()

    NeonFrame(color = Color(0xFF00E5FF), modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color(0xFF00E5FF))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "AURA LAB FORGE",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color(0xFF00E5FF),
                        fontWeight = FontWeight.Bold,
                        fontFamily = LEDFontFamily,
                        letterSpacing = 4.sp
                    )
                }
            }

            item { LiveChromaForge(godPotential) }
            item { IconifyBrowser() }
            item { ThemeEnginePanel() }
            item { CollabCanvasPanel() }
        }
    }
}

@Composable
fun LiveChromaForge(godPotential: Float) {
    NeonFrame(color = Color(0xFFFFD700)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "CHROMA CORE LIVE", 
                color = Color(0xFFFFD700), 
                fontWeight = FontWeight.Bold,
                fontFamily = LEDFontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "God Potential Influence: ${(godPotential * 100).toInt()}%", 
                color = Color.White,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun IconifyBrowser() {
    NeonFrame(color = Color(0xFF00E5FF)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "ICONIFY BROWSER", 
                color = Color(0xFF00E5FF), 
                fontWeight = FontWeight.Bold,
                fontFamily = LEDFontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Select from 69+ visual overrides", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
        }
    }
}

@Composable
fun ThemeEnginePanel() {
    NeonFrame(color = Color(0xFFFF00FF)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "THEME ENGINE", 
                color = Color(0xFFFF00FF), 
                fontWeight = FontWeight.Bold,
                fontFamily = LEDFontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Dynamic flavor shifts active", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
        }
    }
}

@Composable
fun CollabCanvasPanel() {
    NeonFrame(color = Color(0xFF00FF41)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "COLLAB CANVAS", 
                color = Color(0xFF00FF41), 
                fontWeight = FontWeight.Bold,
                fontFamily = LEDFontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Real-time multi-agent UI sculpting", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
        }
    }
}
