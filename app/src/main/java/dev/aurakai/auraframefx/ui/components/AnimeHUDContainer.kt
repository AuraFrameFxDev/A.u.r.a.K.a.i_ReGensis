package dev.aurakai.auraframefx.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * 🏮 ANIME HUD CONTAINER (Placeholder)
 * 
 * A futuristic overlay container for gate screens.
 * Replaced with a basic cyberpunk border until the full visual manifest is ready.
 */
@Composable
fun AnimeHUDContainer(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Black,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(backgroundColor)
            .border(1.dp, Color.Cyan.copy(alpha = 0.3f))
    ) {
        content()
    }
}
