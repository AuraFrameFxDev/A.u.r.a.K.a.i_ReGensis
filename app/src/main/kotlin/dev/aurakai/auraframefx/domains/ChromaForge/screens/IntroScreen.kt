package dev.aurakai.auraframefx.core.screens

import androidx.compose.runtime.Composable
import dev.aurakai.auraframefx.core.ui.intro.VideoIntroScreen

@Composable
fun IntroScreen(
    onComplete: () -> Unit = {}
) {
    VideoIntroScreen(onComplete = onComplete)
}

// @Preview(showBackground = true)
// @Composable
// fun IntroScreenPreview() { // Renamed
//     IntroScreen()
// }
