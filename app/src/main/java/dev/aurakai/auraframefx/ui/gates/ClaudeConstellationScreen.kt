package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@Composable
fun ClaudeConstellationScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // Simplified stub to ensure compilation
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Claude Constellation", color = Color.White)
    }
}

@Composable
private fun BuildModuleIndicator(
    name: String,
    glowAlpha: Float,
    color: Color
) {
    // Implementation
}
