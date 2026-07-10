package dev.aurakai.auraframefx.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── AuraKai Tactical HUD Chrome ──────────────────────────────────────────────
// Visual reference: images 2 & 6 (military telemetry HUD with right-rail domain IDs,
// corner crosshairs, sparse telemetry text, horizontal separator lines)
// This overlay floats on top of every hub screen — transparent, non-interactive.
//
// Layout:
//   • Corner crosshairs — 4 corners, subtle cyan
//   • Right rail — rotated domain name + hub index
//   • Top-left — system ID / session stamp
//   • Scanline tick marks — along left and right edges
//   • Telemetry ghost text — barely readable, just presence

private val HudCyan    = Color(0xFF00F5FF)
private val HudMagenta = Color(0xFFFF00D4)
private val HudDim     = Color(0xFFB0C8D0).copy(alpha = 0.35f)
private val HudBright  = Color(0xFFE0F8FF).copy(alpha = 0.7f)

// Map route → display label (all caps, abbreviated for right rail)
private fun routeToHubLabel(route: String): Pair<String, String> = when (route) {
    "aether_core" -> "ENFIELD THRONE" to "01"
    "trinity_nexus" -> "TRINITY NEXUS" to "02"
    "rune_lattice" -> "RUNE LATTICE" to "03"
    "sentinel_matrix"    -> "SENTINEL MTX"     to "04"
    "oracle_drive"       -> "ORACLE DRIVE"     to "05"
    "chroma_forge" -> "CHROMA FORGE" to "06"
    "emergent_swarm" -> "EMERGENT SWARM" to "07"
    "neural_nexus" -> "NEURAL NEXUS" to "08"
    "ldo_architecture" -> "LDO ARCHITECT" to "09"
    "reality_matrix" -> "REALITY MATRIX" to "10"
    "chaos_catalyst" -> "CHAOS CATALYST" to "11"
    "conference_room" -> "CONFERENCE" to "12"
    "foundation_rebirth" -> "FOUNDATION" to "13"
    "sentient_shell" -> "SENTIENT SHELL" to "14"
    else                 -> "AURAKAI"          to "00"
}

@Composable
fun AuraKaiHUDChrome(
    route: String,
    modifier: Modifier = Modifier
) {
    val (hubName, hubIndex) = remember(route) { routeToHubLabel(route) }

    val tr = rememberInfiniteTransition(label = "hud")
    val blink by tr.animateFloat(
        0.4f, 1f,
        infiniteRepeatable(tween(1400, easing = LinearEasing), RepeatMode.Reverse),
        label = "blink"
    )

    Box(modifier = modifier.fillMaxSize()) {

        // ── Canvas layer: crosshairs + scanlines + tick marks ───────────────
        Canvas(Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val armLen = 22f
            val gap    = 8f

            // Corner crosshairs — 4 corners
            val corners = listOf(
                Offset(20f, 20f)          to Pair(1f,  1f),
                Offset(w - 20f, 20f)      to Pair(-1f, 1f),
                Offset(20f, h - 20f)      to Pair(1f, -1f),
                Offset(w - 20f, h - 20f)  to Pair(-1f,-1f)
            )
            corners.forEach { (origin, dirs) ->
                val (dx, dy) = dirs
                // Horizontal arm
                drawLine(
                    HudCyan.copy(alpha = 0.55f),
                    Offset(origin.x + dx * gap, origin.y),
                    Offset(origin.x + dx * (gap + armLen), origin.y),
                    strokeWidth = 1.2f
                )
                // Vertical arm
                drawLine(
                    HudCyan.copy(alpha = 0.55f),
                    Offset(origin.x, origin.y + dy * gap),
                    Offset(origin.x, origin.y + dy * (gap + armLen)),
                    strokeWidth = 1.2f
                )
                // Corner dot
                drawCircle(HudCyan.copy(alpha = 0.7f), 1.5f, origin)
            }

            // Right rail border line (subtle)
            drawLine(
                HudCyan.copy(alpha = 0.18f),
                Offset(w - 38f, 0f),
                Offset(w - 38f, h),
                strokeWidth = 0.6f
            )

            // Left edge tick marks — every 80px
            var ty = 80f
            while (ty < h - 80f) {
                val isLong = (ty / 80f).toInt() % 4 == 0
                val tickLen = if (isLong) 12f else 6f
                drawLine(HudDim, Offset(0f, ty), Offset(tickLen, ty), 0.8f)
                ty += 80f
            }

            // Right edge tick marks — offset to rail
            ty = 80f
            while (ty < h - 80f) {
                val isLong = (ty / 80f).toInt() % 4 == 0
                val tickLen = if (isLong) 10f else 5f
                drawLine(HudDim, Offset(w - 38f, ty), Offset(w - 38f + tickLen, ty), 0.8f)
                ty += 80f
            }

            // Top separator (subtle dashed line)
            drawLine(
                HudCyan.copy(alpha = 0.12f),
                Offset(60f, 58f),
                Offset(w - 60f, 58f),
                strokeWidth = 0.6f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 8f))
            )

            // Bottom separator
            drawLine(
                HudCyan.copy(alpha = 0.10f),
                Offset(60f, h - 58f),
                Offset(w - 60f, h - 58f),
                strokeWidth = 0.6f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 8f))
            )

            // Hub index blinking dot (top-right corner area)
            drawCircle(HudMagenta.copy(alpha = blink * 0.8f), 3f, Offset(w - 22f, 22f))
        }

        // ── Right rail: rotated hub name + index ────────────────────────────
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 2.dp)
                .wrapContentSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .rotate(-90f)
                    .wrapContentSize()
            ) {
                Text(
                    text = hubIndex,
                    color = HudCyan.copy(alpha = 0.90f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 2.sp
                )
                Text(
                    text = hubName,
                    color = HudDim.copy(alpha = 0.6f),
                    fontSize = 7.sp,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.2.sp
                )
            }
        }

        // ── Top-left: system session ID ──────────────────────────────────────
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 18.dp, top = 14.dp)
        ) {
            Text(
                text = "AURAKAI_SYS",
                color = HudDim.copy(alpha = 0.5f),
                fontSize = 7.sp,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.5.sp
            )
            Text(
                text = "HUB · $hubIndex / 07",
                color = HudCyan.copy(alpha = 0.35f),
                fontSize = 7.sp,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.sp
            )
        }

        // ── Bottom-left: status ghost text ───────────────────────────────────
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 18.dp, bottom = 14.dp)
        ) {
            Text(
                text = "ENFIELD_SHIELD · ACTIVE",
                color = HudDim.copy(alpha = 0.38f),
                fontSize = 6.5.sp,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.sp
            )
            Text(
                text = "SOULSCRIPT v3.50",
                color = HudMagenta.copy(alpha = 0.28f),
                fontSize = 6.5.sp,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.sp
            )
        }
    }
}
