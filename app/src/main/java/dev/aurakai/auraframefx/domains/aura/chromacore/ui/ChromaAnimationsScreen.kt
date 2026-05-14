package dev.aurakai.auraframefx.domains.aura.chromacore.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Waves
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.domains.aura.ui.LEDFontFamily
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChromaAnimationsScreen(onNavigateBack: () -> Unit = {}) {
    val auraMagenta = Color(0xFFFF00FF)
    val auraNeonBlue = Color(0xFF00E5FF)

    // 4D Layering Animation
    val infiniteTransition = rememberInfiniteTransition(label = "4D")
    val depthZ by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Depth"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020205))
    ) {
        // Neon App Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(auraMagenta.copy(alpha = 0.2f), Color.Transparent)
                    )
                )
                .padding(16.dp)
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = auraMagenta
                )
            }
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    text = "CHROMA ANIMATIONS",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = LEDFontFamily,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "AURA DOMAIN // 4D VISUAL ENGINE",
                    color = auraMagenta.copy(alpha = 0.8f),
                    fontSize = 10.sp,
                    letterSpacing = 1.sp
                )
            }
        }

        // 4D Display Chamber
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)
                // Layer 1: Outer Neon Frame
                .border(
                    2.dp,
                    Brush.linearGradient(listOf(auraMagenta, auraNeonBlue)),
                    RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            // Background grid
            Canvas(modifier = Modifier.fillMaxSize()) {
                val step = 40f
                for (x in 0..size.width.toInt() step step.toInt()) {
                    drawLine(
                        color = auraMagenta.copy(alpha = 0.1f),
                        start = Offset(x.toFloat(), 0f),
                        end = Offset(x.toFloat(), size.height),
                        strokeWidth = 1f
                    )
                }
                for (y in 0..size.height.toInt() step step.toInt()) {
                    drawLine(
                        color = auraMagenta.copy(alpha = 0.1f),
                        start = Offset(0f, y.toFloat()),
                        end = Offset(size.width, y.toFloat()),
                        strokeWidth = 1f
                    )
                }
            }

            // Layer 2: Floating 4D Element
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .graphicsLayer {
                        rotationX = 45f * depthZ
                        rotationY = 45f * (1f - depthZ)
                        scaleX = 1f + (depthZ * 0.2f)
                        scaleY = 1f + (depthZ * 0.2f)
                    }
                    .border(2.dp, auraMagenta, RoundedCornerShape(24.dp))
                    .background(auraNeonBlue.copy(alpha = 0.2f))
            ) {
                Icon(
                    imageVector = Icons.Default.Animation,
                    contentDescription = null,
                    tint = auraMagenta,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(64.dp)
                )
            }

            // Layer 3: Glassmorphism Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f))
                        )
                    )
            )
        }

        Text(
            text = "ANIMATION PRESETS",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontFamily = LEDFontFamily
        )

        // 4D Control Cards
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(4) { index ->
                val title = when (index) {
                    0 -> "NEON PULSE"
                    1 -> "QUANTUM DRIFT"
                    2 -> "MATRIX SHATTER"
                    else -> "VOID WEAVE"
                }
                val icon = when (index) {
                    0 -> Icons.Default.Waves
                    1 -> Icons.Default.Layers
                    2 -> Icons.Default.Animation
                    else -> Icons.Default.Waves
                }

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    modifier = Modifier
                        .height(100.dp)
                        .border(1.dp, auraMagenta.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.03f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = auraNeonBlue,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = title,
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = LEDFontFamily
                        )
                    }
                }
            }
        }
    }
}
