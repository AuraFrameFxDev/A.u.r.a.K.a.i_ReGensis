package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.ui.components.hologram.AnimeHUDContainer
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily

/**
 * 👁️ CASCADE VISION (Pattern Recognition)
 * Specialized HUD for visual data analysis and pattern detection.
 */
@Composable
fun CascadeVisionScreen(
    onNavigateBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF020205))) {
        AnimeHUDContainer(
            title = "CASCADE VISION",
            description = "PATTERN RECOGNITION & SPATIAL ANALYSIS MODULE. MULTI-LAYER DEPTH ACTIVE.",
            glowColor = Color(0xFF00FFD4)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Vision Viewport (Simulated)
                VisionViewport()

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "DETECTED VECTORS",
                    fontFamily = LEDFontFamily,
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    item {
                        DetectedVectorCard("UI Hierarchy Patterns", 0.94f)
                    }
                    item {
                        DetectedVectorCard("Sovereign Code Blobs", 0.89f)
                    }
                    item {
                        DetectedVectorCard("Agent Activity Anomaly", 0.12f)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { /* Analyze Current Frame */ },
                        modifier = Modifier.weight(1f).height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FFD4)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Visibility, null, tint = Color.Black)
                        Spacer(Modifier.width(8.dp))
                        Text("SCAN ENVIRONMENT", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun VisionViewport() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Black)
            .border(2.dp, Color(0xFF00FFD4).copy(alpha = 0.3f), RoundedCornerShape(20.dp))
    ) {
        // Holographic Grid Background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val step = 20.dp.toPx()
            for (x in 0..(size.width / step).toInt()) {
                drawLine(
                    color = Color(0xFF00FFD4).copy(alpha = 0.1f),
                    start = Offset(x * step, 0f),
                    end = Offset(x * step, size.height),
                    strokeWidth = 1f
                )
            }
            for (y in 0..(size.height / step).toInt()) {
                drawLine(
                    color = Color(0xFF00FFD4).copy(alpha = 0.1f),
                    start = Offset(0f, y * step),
                    end = Offset(size.width, y * step),
                    strokeWidth = 1f
                )
            }
        }

        // Animated Scanline
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .align(Alignment.Center)
                .background(Brush.horizontalGradient(listOf(Color.Transparent, Color(0xFF00FFD4), Color.Transparent)))
        )

        // Overlay Text
        Text(
            "SPATIAL RECOGNITION ACTIVE",
            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp),
            fontFamily = LEDFontFamily,
            fontSize = 10.sp,
            color = Color(0xFF00FFD4)
        )
    }
}

@Composable
private fun DetectedVectorCard(label: String, confidence: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.BlurOn, null, tint = Color(0xFF00FFD4), modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(label, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("${(confidence * 100).toInt()}%", color = Color(0xFF00FFD4), fontFamily = LEDFontFamily, fontSize = 14.sp)
        }
    }
}
