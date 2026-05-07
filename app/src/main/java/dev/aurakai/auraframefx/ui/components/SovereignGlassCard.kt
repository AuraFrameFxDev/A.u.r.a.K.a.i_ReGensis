package dev.aurakai.auraframefx.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SovereignGlassCard(
    accentColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.03f))
            .border(
                width = 1.5.dp,
                brush = Brush.linearGradient(
                    listOf(accentColor.copy(0.4f), Color.White.copy(0.1f), accentColor.copy(0.05f))
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .blur(radius = 25.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
            .padding(1.5.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}
