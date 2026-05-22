package dev.aurakai.auraframefx.ui.gates

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

/**
 * 📱 NOTCH BAR GATE SCREEN
 */
@Composable
fun NotchBarGateScreen(navController: NavController, onNavigateBack: () -> Unit = {}) {
    val infiniteTransition = rememberInfiniteTransition(label = "notch_screen")
    val electricPulse by infiniteTransition.animateFloat(
        0f, 1f, infiniteRepeatable(tween(1200, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
    ) {
        // Circuit pattern full-screen backdrop
        Canvas(modifier = Modifier.fillMaxSize()) {
            val traceColor = Color(0xFFFF3300).copy(alpha = 0.06f)
            val traceColor2 = Color(0xFF00CED1).copy(alpha = 0.05f)
            // Background circuit grid
            for (x in 0..(size.width / 40f).toInt() + 1)
                drawLine(traceColor, Offset(x * 40f, 0f), Offset(x * 40f, size.height), 0.5f)
            for (y in 0..(size.height / 40f).toInt() + 1)
                drawLine(traceColor2, Offset(0f, y * 40f), Offset(size.width, y * 40f), 0.5f)
            // Diagonal corner neon sparks
            drawLine(
                Color(0xFF00CED1).copy(alpha = 0.3f + electricPulse * 0.3f),
                Offset(0f, 0f), Offset(size.width * 0.3f, 0f), 3f
            )
            drawLine(
                Color(0xFFFF3300).copy(alpha = 0.3f + electricPulse * 0.3f),
                Offset(size.width, 0f), Offset(size.width * 0.7f, 0f), 3f
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        null,
                        tint = Color(0xFF00CED1)
                    )
                }
                Column {
                    Text(
                        "NOTCH BAR", fontFamily = FontFamily.Monospace, fontSize = 20.sp,
                        fontWeight = FontWeight.Bold, letterSpacing = 5.sp,
                        color = Color(0xFF00CED1)
                    )
                    Text(
                        "PERSONAL SCREEN & SHORTCUTS", fontSize = 8.sp,
                        letterSpacing = 2.sp, color = Color(0xFFFF6600).copy(0.7f)
                    )
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { navController.navigate("gate_image_picker") }) {
                    Icon(Icons.Default.SwapHoriz, null, tint = Color(0xFF00CED1).copy(0.6f))
                }
            }

            // Centered gate card art
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f)
                    .padding(horizontal = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                NotchBarGateCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.65f),
                    isActive = true
                )
                // "NOTCH BAR" text overlay at bottom of card
                Text(
                    "NOTCH BAR",
                    fontFamily = FontFamily.Monospace, fontSize = 14.sp,
                    fontWeight = FontWeight.Bold, letterSpacing = 4.sp,
                    color = Color(0xFF00BFFF),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 20.dp)
                )
            }

            // Shortcut grid
            val shortcuts = listOf(
                Triple("STATUS BAR", Icons.Default.BarChart, Color(0xFFFF3300)),
                Triple("QUICK TILES", Icons.Default.GridView, Color(0xFF00CED1)),
                Triple("NOTCH STYLE", Icons.Default.Smartphone, Color(0xFFFFD700)),
                Triple("GESTURES", Icons.Default.TouchApp, Color(0xFF9B30FF)),
                Triple("BRIGHTNESS", Icons.Default.WbSunny, Color(0xFFFF9B00)),
                Triple("VOLUME", Icons.AutoMirrored.Filled.VolumeUp, Color(0xFF00FF80)),
            )
            androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
                columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(shortcuts.size) { idx ->
                    val (label, icon, color) = shortcuts[idx]
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, color.copy(0.4f), RoundedCornerShape(8.dp))
                            .background(color.copy(0.06f))
                            .clickable { }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(icon, label, tint = color, modifier = Modifier.size(24.dp))
                            Text(
                                label, fontSize = 8.sp, fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp, color = Color.White.copy(0.7f),
                                textAlign = TextAlign.Center, maxLines = 2
                            )
                        }
                    }
                }
            }
        }
    }
}
