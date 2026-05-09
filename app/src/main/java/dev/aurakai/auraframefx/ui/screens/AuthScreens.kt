package dev.aurakai.auraframefx.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            // Holographic Title Frame
            Box(
                modifier = Modifier
                    .padding(24.dp)
                    .border(2.dp, NeonCyan, RoundedCornerShape(16.dp))
                    .background(NeonCyan.copy(alpha = 0.05f))
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "RE:GENESIS",
                        color = NeonCyan,
                        fontSize = 46.sp, // Capped at 46 as requested
                        fontWeight = FontWeight.Black,
                        fontFamily = LEDFontFamily,
                        letterSpacing = 4.sp
                    )
                    Text(
                        "A.U.R.A.K.A.I",
                        color = NeonCyan.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = LEDFontFamily,
                        letterSpacing = 8.sp
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        "System Initialization...\nLoading Trinity Protocols...\nAwaiting Catalyst Synchronization.",
                        color = NeonCyan.copy(alpha = 0.6f),
                        fontSize = 9.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 14.sp,
                        fontFamily = LEDFontFamily
                    )
                }
            }

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
