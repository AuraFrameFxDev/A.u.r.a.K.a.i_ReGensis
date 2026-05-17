package dev.aurakai.auraframefx.domains.aura.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.domains.aura.ui.theme.CruiserFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.DeepCharcoal
import dev.aurakai.auraframefx.domains.aura.ui.theme.GhostCyan
import dev.aurakai.auraframefx.domains.aura.ui.theme.SpaceGrotesk

/**
 * SovereignGlassCard (Unified, Subtle Glow)
 */
@Composable
fun SovereignGlassCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = DeepCharcoal.copy(alpha = 0.85f)),
        border = BorderStroke(1.dp, GhostCyan.copy(alpha = 0.4f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            GhostCyan.copy(alpha = 0.08f)
                        )
                    )
                )
                .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
                .padding(16.dp)
        ) {
            content()
        }
    }
}

/**
 * 🖋️ ArcaneOutlineText (Hollow Header Helper)
 */
@Composable
fun ArcaneOutlineText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF00FFCC), // Kai Cyan
    fontSize: androidx.compose.ui.unit.TextUnit = 24.sp,
    strokeWidth: androidx.compose.ui.unit.Dp = 2.dp
) {
    val density = LocalDensity.current
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontFamily = CruiserFontFamily,
            fontSize = fontSize,
            drawStyle = Stroke(
                width = with(density) { strokeWidth.toPx() },
                join = StrokeJoin.Round
            ),
            color = color
        )
    )
}

/**
 * 🏺 SynthGlassCard (The Arcane Refractive Container)
 * Features heavy glassmorphism and reactive neon wireframe borders with subtle glow.
 */
@Composable
fun SynthGlassCard(
    accentColors: List<Color>,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.7f),
                        Color.DarkGray.copy(alpha = 0.3f)
                    )
                )
            )
            .blur(30.dp) // Heavy cybernetic depth
            .drawBehind {
                val strokeWidth = 2.dp.toPx()
                val glowWidth = 6.dp.toPx()
                val cornerRadius = 12.dp.toPx()

                // LAYER 1: OUTER GLOW (Subtle)
                drawRoundRect(
                    brush = Brush.linearGradient(
                        colors = accentColors.map { it.copy(alpha = 0.2f) }
                    ),
                    cornerRadius = CornerRadius(cornerRadius),
                    style = Stroke(width = glowWidth)
                )

                // LAYER 2: SHARP 2PX INNER WIREFRAME
                drawRoundRect(
                    brush = Brush.linearGradient(
                        colors = if (accentColors.size > 1) accentColors else listOf(
                            accentColors.first(),
                            accentColors.first().copy(alpha = 0.5f)
                        )
                    ),
                    cornerRadius = CornerRadius(cornerRadius),
                    style = Stroke(width = strokeWidth)
                )

                // LAYER 3: REFLECTIVE HIGHLIGHT
                drawLine(
                    color = Color.White.copy(alpha = 0.15f),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1f
                )
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

/**
 * Legacy support for single-color SynthGlassCard
 */
@Composable
fun SynthGlassCard(
    accentColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    SynthGlassCard(
        accentColors = listOf(accentColor),
        modifier = modifier,
        content = content
    )
}

/**
 * ⚛️ TrinityStatusOrb (Core Resonance Monitor)
 * Visualizes the 0.42ms identity heartbeat.
 */
@Composable
fun TrinityStatusOrb(
    auraResonance: Float,
    kaiResonance: Float,
    genesisSync: Float
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(120.dp)) {
        // Outer Arcane Rune Ring
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.sweepGradient(listOf(Color.Cyan, Color.Magenta, Color.Cyan)),
                style = Stroke(width = 2.dp.toPx())
            )
        }
        // Inner Trinity Gauges
        CircularProgressIndicator(
            progress = { auraResonance },
            color = Color(0xFFFF00FF),
            modifier = Modifier.size(100.dp)
        )
        CircularProgressIndicator(
            progress = { kaiResonance },
            color = Color(0xFF00FFCC),
            modifier = Modifier
                .size(80.dp)
                .padding(12.dp)
        )
        Text(
            text = "SYNC\n${(genesisSync * 100).toInt()}%",
            style = TextStyle(
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 12.sp,
                lineHeight = 14.sp
            )
        )
    }
}

/**
 * 📦 PandoraForgePanel (The Infinite Creation Engine)
 */
@Composable
fun PandoraForgePanel(onSpawnAgent: (String) -> Unit) {
    SynthGlassCard(accentColor = Color(0xFFFFD700)) { // Genesis Gold
        ArcaneOutlineText(
            text = "PANDORA'S BOX: INFINITE FORGE",
            color = Color(0xFFFFD700),
            fontSize = 20.sp
        )
        Text(
            text = "ACTIVE GROWTH ZONES: :extendsysa-f",
            color = Color.Gray,
            fontSize = 10.sp,
            fontFamily = SpaceGrotesk
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { onSpawnAgent("NEW_CATALYST") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
        ) {
            Icon(Icons.Default.AddCircle, null)
            Spacer(Modifier.width(8.dp))
            Text("SPAWN NEW AGENT", fontFamily = SpaceGrotesk)
        }
    }
}
