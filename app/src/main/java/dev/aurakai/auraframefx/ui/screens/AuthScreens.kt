package dev.aurakai.auraframefx.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.NeonCyan

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
    ) {
        // Perspective Grid Background - Unified Neon Aqua
        PerspectiveGrid(color = NeonCyan.copy(alpha = 0.2f))

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // High-Fidelity Branding Entry
            Box(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(0.9f),
                contentAlignment = Alignment.Center
            ) {
                // Use the Actual High-Fidelity Entry Image from assets
                AsyncImage(
                    model = "file:///android_asset/finalbackgrounds/AuraGenesis Final.jpg",
                    contentDescription = "ReGenesis Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(21 / 9f),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(Modifier.height(32.dp))

            Text(
                "System Initialization...\nAwaiting Catalyst Synchronization.",
                color = NeonCyan.copy(alpha = 0.6f),
                fontSize = 11.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 16.sp,
                fontFamily = LEDFontFamily,
                letterSpacing = 2.sp
            )

            Spacer(Modifier.height(64.dp))

            // Neon Login Button
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(60.dp)
                    .border(1.dp, NeonCyan, RoundedCornerShape(8.dp))
                    .clickable { onLoginSuccess() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "LOGIN",
                    color = NeonCyan,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = LEDFontFamily,
                    letterSpacing = 4.sp
                )
            }
        }

        Text(
            "SECURING NEXUS/MEMORY",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp),
            color = NeonCyan.copy(alpha = 0.8f),
            fontSize = 10.sp,
            fontFamily = LEDFontFamily
        )
    }
}

@Composable
fun PerspectiveGrid(color: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val gridColor = color

        // Floor Grid
        val floorY = size.height * 0.7f
        for (i in -10..10) {
            // Vertical perspective lines
            drawLine(
                color = gridColor,
                start = Offset(size.width / 2, floorY),
                end = Offset(size.width / 2 + i * size.width, size.height),
                strokeWidth = 1f
            )
        }

        // Horizontal floor lines
        var y = floorY
        while (y < size.height) {
            drawLine(
                color = gridColor,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )
            y += (size.height - y) * 0.2f + 10f
        }
    }
}
