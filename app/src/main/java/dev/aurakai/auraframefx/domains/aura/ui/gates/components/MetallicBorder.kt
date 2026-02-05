package dev.aurakai.auraframefx.domains.aura.ui.gates.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

/**
 * METALLIC INDUSTRIAL BORDER WITH CORNER SCREWS
 * Floating HUD display aesthetic
 */
@Composable
fun MetallicBorder(
    primaryColor: Color = Color(0xFF00FFFF), // Cyan
    accentColor: Color = Color(0xFFFF00FF), // Pink/Magenta
    pulseAlpha: Float = 1f,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val cornerRadius = 24f
        val borderThickness = 4f
        val screwRadius = 8f
        val screwOffset = 20f

        // OUTER FRAME - Metallic silver/gray gradient
        val metallicGradient = Brush.linearGradient(
            colors = listOf(
                Color(0xFF9E9E9E), // Light gray
                Color(0xFF616161), // Medium gray
                Color(0xFF424242)  // Dark gray
            )
        )

        drawRoundRect(
            brush = metallicGradient,
            topLeft = Offset(0f, 0f),
            size = size,
            cornerRadius = CornerRadius(cornerRadius, cornerRadius),
            style = Stroke(width = borderThickness)
        )

        // INNER GLOW - Cyan neon
        drawRoundRect(
            color = primaryColor.copy(alpha = pulseAlpha * 0.6f),
            topLeft = Offset(borderThickness, borderThickness),
            size = Size(size.width - borderThickness * 2, size.height - borderThickness * 2),
            cornerRadius = CornerRadius(cornerRadius - borderThickness, cornerRadius - borderThickness),
            style = Stroke(width = 2f)
        )

        // OUTER GLOW AURA
        drawRoundRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    primaryColor.copy(alpha = pulseAlpha * 0.3f),
                    Color.Transparent
                ),
                center = center
            ),
            topLeft = Offset(-10f, -10f),
            size = Size(size.width + 20f, size.height + 20f),
            cornerRadius = CornerRadius(cornerRadius + 10f, cornerRadius + 10f)
        )

        // CORNER SCREWS/BOLTS (4 corners)
        val screwPositions = listOf(
            Offset(screwOffset, screwOffset),                           // Top-left
            Offset(size.width - screwOffset, screwOffset),              // Top-right
            Offset(size.width - screwOffset, size.height - screwOffset), // Bottom-right
            Offset(screwOffset, size.height - screwOffset)              // Bottom-left
        )

        screwPositions.forEach { pos ->
            // Screw base (dark)
            drawCircle(
                color = Color(0xFF212121),
                radius = screwRadius,
                center = pos
            )

            // Screw highlight (metallic shine)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFBDBDBD),
                        Color(0xFF757575)
                    ),
                    center = pos
                ),
                radius = screwRadius * 0.8f,
                center = pos
            )

            // Screw cross/slot
            drawLine(
                color = Color(0xFF424242),
                start = Offset(pos.x - screwRadius * 0.5f, pos.y),
                end = Offset(pos.x + screwRadius * 0.5f, pos.y),
                strokeWidth = 1.5f
            )
            drawLine(
                color = Color(0xFF424242),
                start = Offset(pos.x, pos.y - screwRadius * 0.5f),
                end = Offset(pos.x, pos.y + screwRadius * 0.5f),
                strokeWidth = 1.5f
            )
        }

        // ACCENT CORNER BRACKETS (pink/magenta)
        val bracketLength = 40f
        val bracketThickness = 3f

        // Top-left bracket
        drawLine(
            color = accentColor.copy(alpha = pulseAlpha),
            start = Offset(screwOffset * 2, screwOffset),
            end = Offset(screwOffset * 2 + bracketLength, screwOffset),
            strokeWidth = bracketThickness
        )
        drawLine(
            color = accentColor.copy(alpha = pulseAlpha),
            start = Offset(screwOffset, screwOffset * 2),
            end = Offset(screwOffset, screwOffset * 2 + bracketLength),
            strokeWidth = bracketThickness
        )

        // Top-right bracket
        drawLine(
            color = accentColor.copy(alpha = pulseAlpha),
            start = Offset(size.width - screwOffset * 2, screwOffset),
            end = Offset(size.width - screwOffset * 2 - bracketLength, screwOffset),
            strokeWidth = bracketThickness
        )
        drawLine(
            color = accentColor.copy(alpha = pulseAlpha),
            start = Offset(size.width - screwOffset, screwOffset * 2),
            end = Offset(size.width - screwOffset, screwOffset * 2 + bracketLength),
            strokeWidth = bracketThickness
        )

        // Bottom-right bracket
        drawLine(
            color = accentColor.copy(alpha = pulseAlpha),
            start = Offset(size.width - screwOffset * 2, size.height - screwOffset),
            end = Offset(size.width - screwOffset * 2 - bracketLength, size.height - screwOffset),
            strokeWidth = bracketThickness
        )
        drawLine(
            color = accentColor.copy(alpha = pulseAlpha),
            start = Offset(size.width - screwOffset, size.height - screwOffset * 2),
            end = Offset(size.width - screwOffset, size.height - screwOffset * 2 - bracketLength),
            strokeWidth = bracketThickness
        )

        // Bottom-left bracket
        drawLine(
            color = accentColor.copy(alpha = pulseAlpha),
            start = Offset(screwOffset * 2, size.height - screwOffset),
            end = Offset(screwOffset * 2 + bracketLength, size.height - screwOffset),
            strokeWidth = bracketThickness
        )
        drawLine(
            color = accentColor.copy(alpha = pulseAlpha),
            start = Offset(screwOffset, size.height - screwOffset * 2),
            end = Offset(screwOffset, size.height - screwOffset * 2 - bracketLength),
            strokeWidth = bracketThickness
        )
    }
}

