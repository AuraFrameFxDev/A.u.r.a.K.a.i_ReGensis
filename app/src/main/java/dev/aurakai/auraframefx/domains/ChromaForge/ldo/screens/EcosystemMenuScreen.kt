package dev.aurakai.auraframefx.domains.ldo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.domains.aura.ui.LEDFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcosystemMenuScreen(onNavigateBack: () -> Unit = {}) {
    val devOpsCyan = Color(0xFF00E5FF)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF01050A))
            .padding(16.dp)
    ) {
        // App Bar with Sharp Neon Styling
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, devOpsCyan, RectangleShape)
                .background(Color.Black.copy(alpha = 0.7f)) // 70% Transparency
                .padding(16.dp)
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = devOpsCyan)
            }
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    text = "ECOSYSTEM MENU",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = LEDFontFamily,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "LDO DEVOPS // COMMAND CENTER",
                    color = devOpsCyan,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Grid Menu
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            val items = listOf(
                "INFRASTRUCTURE" to Icons.Default.AccountTree,
                "CODE PIPELINE" to Icons.Default.Code,
                "SERVER LOGS" to Icons.Default.Terminal,
                "METRICS HUB" to Icons.Default.Dashboard,
                "DATABASE" to Icons.Default.Storage,
                "MEMORY ALLOC" to Icons.Default.Memory
            )

            items(items.size) { index ->
                val (title, icon) = items[index]
                
                Box(
                    modifier = Modifier
                        .height(120.dp)
                        .border(1.dp, devOpsCyan, RectangleShape)
                        .shadow(elevation = if (index % 2 == 0) 10.dp else 5.dp, spotColor = devOpsCyan)
                        .background(Color.Black.copy(alpha = 0.7f))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = icon, contentDescription = null, tint = devOpsCyan, modifier = Modifier.size(40.dp))
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = title, 
                            color = Color.White, 
                            fontSize = 12.sp, 
                            fontWeight = FontWeight.Bold, 
                            fontFamily = LEDFontFamily
                        )
                    }
                }
            }
        }
    }
}
