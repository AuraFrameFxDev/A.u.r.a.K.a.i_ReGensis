package dev.aurakai.auraframefx.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BrutalistScheme = darkColorScheme(
    primary = Color(0xFF00F0FF),
    secondary = Color(0xFF00E5C0),
    background = Color(0xFF0A0A0A),
    surface = Color(0xFF111111)
)

@Composable
fun LdoBrutalistTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = BrutalistScheme, content = content)
}
