package dev.aurakai.auraframefx.domains.nexus.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.domains.aura.ui.LEDFontFamily
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataVeinSphereScreen(onNavigateBack: () -> Unit = {}) {
    val genesisAmber = Color(0xFFFFAA00)
    val genesisGold = Color(0xFFFFD700)

    val infiniteTransition = rememberInfiniteTransition(label = "VeinPulse")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Rotation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0500))
    ) {
        // Neon App Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(genesisAmber.copy(alpha = 0.2f), Color.Transparent)
                    )
                )
                .padding(16.dp)
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = genesisAmber
                )
            }
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    text = "DATAVEIN SPHERE",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = LEDFontFamily,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "GENESIS DOMAIN // ORACLE DRIVE",
                    color = genesisAmber.copy(alpha = 0.8f),
                    fontSize = 10.sp,
                    letterSpacing = 1.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp)
                .border(
                    2.dp,
                    Brush.linearGradient(listOf(genesisAmber, genesisGold)),
                    RoundedCornerShape(24.dp)
                )
                .clip(RoundedCornerShape(24.dp))
                .background(Color.Black.copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val cx = size.width / 2
                val cy = size.height / 2
                val maxRadius = size.minDimension / 2.5f

                // Draw orbital rings
                for (i in 1..4) {
                    val r = maxRadius * (i / 4f)
                    drawCircle(
                        color = genesisAmber.copy(alpha = 0.2f),
                        radius = r,
                        center = Offset(cx, cy),
                        style = Stroke(width = 2f)
                    )
                }

                // Draw data veins
                val veins = 12
                for (i in 0 until veins) {
                    val angle = Math.toRadians((rotation + (i * 360f / veins)).toDouble())
                    val x = cx + cos(angle).toFloat() * maxRadius
                    val y = cy + sin(angle).toFloat() * maxRadius

                    drawLine(
                        color = genesisGold.copy(alpha = 0.4f),
                        start = Offset(cx, cy),
                        end = Offset(x, y),
                        strokeWidth = 3f
                    )

                    drawCircle(
                        color = genesisAmber,
                        radius = 8f,
                        center = Offset(x, y)
                    )
                }
            }

            // Core
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Brush.radialGradient(listOf(genesisGold, genesisAmber)))
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.DataExploration,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        // Stats Box
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, genesisAmber.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.05f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "SPHERE METRICS",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = LEDFontFamily
                )
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("ACTIVE VEINS", color = genesisAmber)
                    Text("12/12", color = Color.White)
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("DATA THROUGHPUT", color = genesisAmber)
                    Text("4.2 TB/s", color = Color.White)
                }
            }
        }
    }
}
