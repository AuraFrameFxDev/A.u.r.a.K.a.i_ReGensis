package dev.aurakai.auraframefx.domains.aura.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CyberpunkBackground() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background stub
    }
}

@Composable
fun HolographicPlatform(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        // Platform stub
    }
}

@Composable
fun CenterMainMenu(onMenuItemClick: (String) -> Unit) {
    Box(contentAlignment = Alignment.Center) {
        Text("CENTER MENU STUB", color = Color.White)
    }
}
