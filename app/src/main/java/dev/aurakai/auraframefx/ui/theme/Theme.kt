package dev.aurakai.auraframefx.ui.theme

import android.app.Activity
import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aurakai.auraframefx.domains.aura.models.Emotion
import dev.aurakai.auraframefx.domains.aura.ui.viewmodels.AuraMoodViewModel
import dev.aurakai.auraframefx.ui.theme.model.CyberpunkColorScheme
import dev.aurakai.auraframefx.ui.theme.model.SolarizedColorScheme

lateinit var emotion: Emotion
private val DarkColorScheme = darkColorScheme(
    primary = NeonTeal,
    onPrimary = OnPrimary,
    primaryContainer = NeonTeal.copy(alpha = 0.2f),
    onPrimaryContainer = OnPrimary,
    secondary = NeonPurple,
    onSecondary = OnSecondary,
    secondaryContainer = NeonPurple.copy(alpha = 0.2f),
    onSecondaryContainer = OnSecondary,
    tertiary = NeonBlue,
    onTertiary = OnTertiary,
    tertiaryContainer = NeonBlue.copy(alpha = 0.2f),
    onTertiaryContainer = OnTertiary,
    background = DarkBackground,
    onBackground = OnSurface,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    error = ErrorColor,
    onError = OnPrimary,
    errorContainer = ErrorColor.copy(alpha = 0.2f),
    onErrorContainer = OnPrimary,
    outline = OnSurfaceVariant,
    outlineVariant = SurfaceVariant
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimary.copy(alpha = 0.2f),
    onPrimaryContainer = LightOnPrimary,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSecondary.copy(alpha = 0.2f),
    onSecondaryContainer = LightOnSecondary,
    tertiary = LightTertiary,
    onTertiary = LightOnTertiary,
    tertiaryContainer = LightTertiary.copy(alpha = 0.2f),
    onTertiaryContainer = LightOnTertiary,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    error = ErrorColor,
    onError = LightOnError,
    errorContainer = ErrorColor.copy(alpha = 0.2f),
    onErrorContainer = LightOnError,
    outline = LightOnSurfaceVariant,
    outlineVariant = LightSurfaceVariant
)

val LocalMoodGlow = compositionLocalOf { Color.Transparent }
val LocalMoodState: ProvidableCompositionLocal<Emotion> = compositionLocalOf { Emotion.NEUTRAL }

@Composable
fun AuraFrameFXTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    moodViewModel: AuraMoodViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val currentEmotion: Emotion by moodViewModel.moodState.collectAsState()
    val themeState by themeViewModel.theme.collectAsState()
    val colorState by themeViewModel.color.collectAsState()

    val useDarkTheme = when (themeState) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK, ThemeMode.CYBERPUNK -> darkTheme
        ThemeMode.SOLARIZED -> false
    }

    val baseColorScheme = when (themeState) {
        ThemeMode.CYBERPUNK -> CyberpunkColorScheme
        ThemeMode.SOLARIZED -> SolarizedColorScheme
        else -> {
            if (dynamicColor) {
                val context = LocalContext.current
                if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
                    context = context
                )
            } else {
                if (useDarkTheme) DarkColorScheme else LightColorScheme
            }
        }
    }

    val finalColorScheme = baseColorScheme.copy(
        primary = when (colorState) {
            ThemeColor.RED -> NeonRed
            ThemeColor.GREEN -> NeonGreen
            ThemeColor.BLUE -> NeonBlue
        },
        surface = Color(0xFF020205), // Deep Obsidian Concrete
        background = Color(0xFF020205)
    )

    currentEmotion.also { emotion = it }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window: Any = (view.context as Activity).window
            baseColorScheme.primary.toArgb()
            (!useDarkTheme).also {
                WindowCompat.getInsetsController(
                    window as Window,
                    view
                ).isAppearanceLightStatusBars = it
            }
        }
    }

    MaterialTheme(
        colorScheme = finalColorScheme,
        typography = AppTypography, // Use AppTypography from Type.kt
        content = content
    )
}

@Composable
internal fun ExperimentalMaterial3ApiTheme(
    colorScheme: ColorScheme,
    typography: androidx.compose.material3.Typography,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}

private fun getMoodGlowColor(
    emotion: Emotion,
    intensity: Float,
    baseColorScheme: ColorScheme
): Color {
    val baseAlpha = (intensity * 0.5f).coerceIn(0.1f, 0.7f)

    val color = when (emotion) {
        Emotion.HAPPY -> Color(0xFFFFD700)
        Emotion.EXCITED -> Color(0xFFFF4500)
        Emotion.ANGRY -> Color(0xFFDC143C)
        Emotion.SERENE -> Color(0xFF00CED1)
        Emotion.CONTEMPLATIVE -> Color(0xFF9932CC)
        Emotion.MISCHIEVOUS -> Color(0xFFADFF2F)
        Emotion.FOCUSED -> Color(0xFF4682B4)
        Emotion.CONFIDENT -> Color(0xFFDB7093)
        Emotion.MYSTERIOUS -> Color(0xFF2F4F4F)
        Emotion.MELANCHOLIC -> Color(0xFF6A5ACD)
        else -> baseColorScheme.primary
    }
    return color.copy(alpha = baseAlpha)
}
