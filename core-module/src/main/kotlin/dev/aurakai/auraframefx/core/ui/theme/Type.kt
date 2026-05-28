package dev.aurakai.auraframefx.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.core.module.R

val corptaFont = FontFamily(
    Font(R.font.corpta, FontWeight.Normal)
)

val CruiserFontFamily = FontFamily(
    Font(R.font.cruiser_hollow_italic, FontWeight.Normal)
)

val LEDFontFamily = FontFamily(
    Font(R.font.enhanced_led_board_7, FontWeight.Normal)
)

val SpaceGrotesk = corptaFont // Fallback to corpta if space_grotesk is missing

val WireframeStyle = TextStyle(
    fontFamily = CruiserFontFamily,
    color = GhostCyan,
    fontSize = 18.sp,
    letterSpacing = 2.sp
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = corptaFont,
        fontWeight = FontWeight.Black,
        fontSize = 46.sp, // Capped at 46 as requested
        letterSpacing = 2.sp
    ),
    displayMedium = TextStyle(
        fontFamily = corptaFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        letterSpacing = 1.sp
    ),
    displaySmall = TextStyle(
        fontFamily = corptaFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = corptaFont,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = corptaFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = corptaFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = corptaFont,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    titleMedium = TextStyle(
        fontFamily = corptaFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    titleSmall = TextStyle(
        fontFamily = corptaFont,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = corptaFont,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = corptaFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    bodySmall = TextStyle(
        fontFamily = corptaFont,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp
    ),
    labelLarge = TextStyle(
        fontFamily = corptaFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelMedium = TextStyle(
        fontFamily = corptaFont,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp
    ),
    labelSmall = TextStyle(
        fontFamily = corptaFont,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    )
)
