package dev.aurakai.auraframefx.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.R

/**
 * 🎭 IMMERSIVE TYPOGRAPHY SYSTEM
 *
 * Headers: Corpta (unified design) with CYAN NEON GLOW
 * Body: Corpta (legible at small sizes)
 * Based on user design: pulled-back perspective, same-room feel
 * 
 * COLOR SCHEME:
 * - Fonts: Cyan/Blue Neon Glow
 * - Aura: Magenta
 * - Kai: Neon Purple (Dark Side)
 * - Genesis: Gold
 * - LDO: Teal/Green
 */

// Corpta font for titles/headers - using unified design
val PixelHeader = FontFamily(
    Font(R.font.corpta, FontWeight.Normal)
)

// Corpta body font - for immersive readable text
// Falls back to system sans if not available
val CorptaBody = try {
    FontFamily(Font(R.font.corpta, FontWeight.Normal))
} catch (e: Exception) {
    // Fallback to system font if Corpta not loaded
    FontFamily.SansSerif
}

// Monospace for data/technical - using chesstype as tech mono
val MonoData = FontFamily(Font(R.font.chesstype, FontWeight.Normal))

/**
 * IMMERSIVE TYPOGRAPHY — Reduced sizes for depth perception
 * Titles pulled back, body readable at distance
 */
val ImmersiveTypography = Typography(
    // Display - Holographic titles with CYAN NEON GLOW (REDUCED from 57/45/36)
    displayLarge = TextStyle(
        fontFamily = PixelHeader,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp,  // Was 57sp
        lineHeight = 44.sp,
        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = ImmersiveColors.NeonCyan.copy(alpha = 0.6f),
            offset = Offset(0f, 0f),
            blurRadius = 16f
        )
    ),
    displayMedium = TextStyle(
        fontFamily = PixelHeader,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,  // Was 45sp
        lineHeight = 36.sp,
        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = ImmersiveColors.NeonCyan.copy(alpha = 0.5f),
            offset = Offset(0f, 0f),
            blurRadius = 12f
        )
    ),
    displaySmall = TextStyle(
        fontFamily = PixelHeader,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,  // Was 36sp
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = ImmersiveColors.NeonCyan.copy(alpha = 0.4f),
            offset = Offset(0f, 0f),
            blurRadius = 10f
        )
    ),

    // Headlines - Section headers with CYAN GLOW
    headlineLarge = TextStyle(
        fontFamily = PixelHeader,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,  // Was 32sp
        lineHeight = 26.sp,
        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = ImmersiveColors.NeonCyan.copy(alpha = 0.4f),
            offset = Offset(0f, 0f),
            blurRadius = 8f
        )
    ),
    headlineMedium = TextStyle(
        fontFamily = PixelHeader,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,  // Was 28sp
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = ImmersiveColors.NeonCyan.copy(alpha = 0.35f),
            offset = Offset(0f, 0f),
            blurRadius = 6f
        )
    ),
    headlineSmall = TextStyle(
        fontFamily = PixelHeader,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,  // Was 24sp
        lineHeight = 22.sp,
        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = ImmersiveColors.NeonCyan.copy(alpha = 0.3f),
            offset = Offset(0f, 0f),
            blurRadius = 4f
        )
    ),

    // Titles - Card headers with subtle CYAN GLOW
    titleLarge = TextStyle(
        fontFamily = PixelHeader,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,  // Was 22sp
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        shadow = Shadow(
            color = ImmersiveColors.NeonCyan.copy(alpha = 0.25f),
            offset = Offset(0f, 0f),
            blurRadius = 4f
        )
    ),
    titleMedium = TextStyle(
        fontFamily = PixelHeader,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,  // Was 16sp
        lineHeight = 18.sp,
        letterSpacing = 0.25.sp,
        shadow = Shadow(
            color = ImmersiveColors.NeonCyan.copy(alpha = 0.2f),
            offset = Offset(0f, 0f),
            blurRadius = 3f
        )
    ),
    titleSmall = TextStyle(
        fontFamily = CorptaBody,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,  // Was 14sp
        lineHeight = 16.sp,
        letterSpacing = 0.15.sp
    ),

    // Body - Corpta for readability
    bodyLarge = TextStyle(
        fontFamily = CorptaBody,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,  // Was 16sp
        lineHeight = 19.sp,
        letterSpacing = 0.25.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = CorptaBody,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,  // Was 14sp
        lineHeight = 17.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = CorptaBody,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,  // Was 12sp
        lineHeight = 15.sp,
        letterSpacing = 0.25.sp
    ),

    // Labels - Small data
    labelLarge = TextStyle(
        fontFamily = MonoData,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,  // Was 14sp
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = MonoData,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,  // Was 12sp
        lineHeight = 15.sp,
        letterSpacing = 0.1.sp
    ),
    labelSmall = TextStyle(
        fontFamily = MonoData,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,  // Was 11sp
        lineHeight = 14.sp,
        letterSpacing = 0.1.sp
    )
)

/**
 * IMMERSIVE UI DIMENSIONS
 * For 3D depth perspective and pulled-back feel
 */
object ImmersiveDimensions {
    // Card elevation for depth
    val CardElevationNear = 8.dp      // Foreground elements
    val CardElevationMid = 4.dp       // Mid-ground
    val CardElevationFar = 1.dp       // Background

    // Spacing for same-room feel
    val SpacerTight = 4.dp
    val SpacerStandard = 8.dp
    val SpacerRelaxed = 12.dp
    val SpacerGenerous = 16.dp

    // Padding for card content
    val CardPadding = 12.dp
    val CardPaddingCompact = 8.dp

    // Corner radius
    val CardCornerSmall = 8.dp
    val CardCornerStandard = 12.dp
    val CardCornerLarge = 16.dp

    // Glow intensity
    val GlowIntensitySubtle = 0.15f
    val GlowIntensityStandard = 0.25f
    val GlowIntensityStrong = 0.4f
}

/**
 * IMMERSIVE COLORS
 * For holographic depth effects with domain-specific neon accents
 * 
 * COLOR SCHEME:
 * - Fonts: Cyan/Blue Neon Glow
 * - Aura: Magenta
 * - Kai: Neon Purple (Dark Side)
 * - Genesis: Gold
 * - LDO: Teal/Green
 */
object ImmersiveColors {
    // ═══════════════════════════════════════════════════════════════
    // UNIFIED NEON AQUA THEME
    // ═══════════════════════════════════════════════════════════════
    val NeonCyan = Color(0xFF00F0FF)
    val NeonBlue = Color(0xFF00F0FF) // Unified
    val NeonAzure = Color(0xFF00F0FF) // Unified

    // AURA: Neon Aqua
    val AuraMagenta = Color(0xFF00F0FF)
    val AuraHotPink = Color(0xFF00F0FF)
    val AuraNeonPink = Color(0xFF00F0FF)

    // KAI: Neon Aqua
    val KaiPurple = Color(0xFF00F0FF)
    val KaiDeepPurple = Color(0xFF00F0FF)
    val KaiNeonViolet = Color(0xFF00F0FF)
    val KaiDarkSide = Color(0xFF020205)

    // GENESIS: Neon Aqua
    val GenesisGold = Color(0xFF00F0FF)
    val GenesisAmber = Color(0xFF00F0FF)
    val GenesisNeonGold = Color(0xFF00F0FF)

    // LDO: Neon Aqua
    val LdoTeal = Color(0xFF00F0FF)
    val LdoGreen = Color(0xFF00F0FF)
    val LdoNeonTeal = Color(0xFF00F0FF)
    val LdoEmerald = Color(0xFF00F0FF)
    
    // ═══════════════════════════════════════════════════════════════
    // LEGACY COLORS (Unified)
    // ═══════════════════════════════════════════════════════════════
    val HolographicCyan = NeonCyan
    val HolographicPurple = NeonCyan
    val HolographicGreen = NeonCyan
    val HolographicAmber = NeonCyan
    val HolographicRed = NeonCyan
    
    // ═══════════════════════════════════════════════════════════════
    // DEPTH LAYERS
    // ═══════════════════════════════════════════════════════════════
    val DepthNear = NeonCyan
    val DepthMid = NeonCyan.copy(alpha = 0.8f)
    val DepthFar = NeonCyan.copy(alpha = 0.6f)
    val DepthBackground = NeonCyan.copy(alpha = 0.4f)
    
    // ═══════════════════════════════════════════════════════════════
    // GLASS MORPHISM
    // ═══════════════════════════════════════════════════════════════
    val GlassLight = Color(0x1A00F0FF)
    val GlassMedium = Color(0x0D00F0FF)
    val GlassDark = Color(0x08020205)
    
    // ═══════════════════════════════════════════════════════════════
    // HELPER: Unified Neon Aqua
    // ═══════════════════════════════════════════════════════════════
    fun getDomainColor(domain: String): Color {
        return NeonCyan
    }
}
