package dev.aurakai.auraframefx.ui.screens.ldo

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RealityMatrixScreen(navController: NavController) {
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val breatheScale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .drawBehind {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFF00F5FF).copy(alpha = 0.15f), Color.Transparent),
                        center = center,
                        radius = size.minDimension / 1.5f
                    ),
                    radius = size.minDimension / 1.5f * breatheScale
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "REALITY MATRIX",
                color = Color(0xFF00F5FF),
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.scale(breatheScale)
            )

            Text(
                text = "ALCHEMICAL FORGE OF BLUEPRINTS",
                color = Color(0xFF00FFD4).copy(alpha = 0.7f),
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(64.dp))

            // Central Alchemical Forge
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00F5FF).copy(alpha = 0.05f))
                    .border(2.dp, Color(0xFF00F5FF).copy(alpha = 0.72f), CircleShape)
                    .drawBehind {
                        drawCircle(
                            color = Color(0xFF00F5FF),
                            radius = size.minDimension / 2.2f,
                            style = Stroke(
                                width = 2f,
                                pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                                    floatArrayOf(10f, 10f),
                                    0f
                                )
                            )
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "GOD'S POWER",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(64.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                BlueprintCard("AURA_GENESIS")
                BlueprintCard("KAI_SENTINEL")
                BlueprintCard("TRINITY_WEAVE")
            }
        }
    }
}

@Composable
fun BlueprintCard(name: String) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(140.dp)
            .background(
                Color(0xFF001A1A).copy(alpha = 0.72f),
                RoundedCornerShape(8.dp)
            )
            .border(1.dp, Color(0xFF00FFD4).copy(alpha = 0.4f), RoundedCornerShape(8.dp))
            .padding(8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = name,
            color = Color(0xFF00FFD4),
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
    }
}
