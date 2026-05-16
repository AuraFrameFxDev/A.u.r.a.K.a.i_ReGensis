package dev.aurakai.auraframefx.domains.kai.screens.rom_tools

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.domains.aura.ui.LEDFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootToolsTogglesScreen(onNavigateBack: () -> Unit = {}) {
    val kaiGreen = Color(0xFF00FF88)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF010A05))
            .padding(16.dp)
    ) {
        // App Bar with Sharp Neon Styling
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, kaiGreen, RectangleShape)
                .background(Color.Black.copy(alpha = 0.7f)) // 70% Transparency
                .padding(16.dp)
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = kaiGreen
                )
            }
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    text = "ROOT TOGGLES",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = LEDFontFamily,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "KAI SENTINEL // SYSTEM OVERRIDES",
                    color = kaiGreen,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Security Status Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, kaiGreen, RectangleShape)
                .shadow(elevation = 15.dp, spotColor = kaiGreen, ambientColor = kaiGreen)
                .background(Color.Black.copy(alpha = 0.7f))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Security,
                    contentDescription = null,
                    tint = kaiGreen,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        "KERNEL ACCESS GRANTED",
                        color = kaiGreen,
                        fontWeight = FontWeight.Bold,
                        fontFamily = LEDFontFamily
                    )
                    Text(
                        "LSPosed / Zygisk Active",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Toggle List
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(5) { index ->
                val title = when (index) {
                    0 -> "SELinux Permissive"
                    1 -> "Magisk Hide"
                    2 -> "ADB Root Access"
                    3 -> "Thermal Throttling Bypass"
                    else -> "System R/W Mount"
                }

                var isActive by remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, if (isActive) kaiGreen else Color.DarkGray, RectangleShape)
                        .background(Color.Black.copy(alpha = 0.7f))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                title,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontFamily = LEDFontFamily
                            )
                            Text(
                                if (isActive) "ACTIVE" else "DISABLED",
                                color = if (isActive) kaiGreen else Color.Gray,
                                fontSize = 10.sp
                            )
                        }
                        IconButton(onClick = { isActive = !isActive }) {
                            Icon(
                                imageVector = if (isActive) Icons.Default.ToggleOn else Icons.Default.ToggleOff,
                                contentDescription = null,
                                tint = if (isActive) kaiGreen else Color.Gray,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
