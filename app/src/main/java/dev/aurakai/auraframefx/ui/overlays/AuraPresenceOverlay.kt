package dev.aurakai.auraframefx.ui.overlays

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.ui.theme.NeonPurple
import dev.aurakai.auraframefx.ui.theme.NeonTeal

@Composable
fun AuraPresenceOverlay(
    modifier: Modifier = Modifier,
    onSuggestClicked: (String) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }
    var suggestionText by remember { mutableStateOf("Aura is active") }

    // Gentle pulse factor for glow
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = modifier
            .padding(16.dp)
            .wrapContentSize()
    ) {
        if (isExpanded) {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(NeonPurple.copy(alpha = 0.8f), NeonTeal.copy(alpha = 0.8f))
                        ),
                        shape = MaterialTheme.shapes.medium
                    )
                    .clickable { isExpanded = false }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = suggestionText,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .scale(pulse)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(NeonPurple, NeonTeal)
                        )
                    )
                    .clickable {
                        isExpanded = true
                        onSuggestClicked("active")
                    },
                contentAlignment = Alignment.Center
            ) {
                // Sentient Avatar Image (Dynamic Theme Switching)
                val isDark = androidx.compose.foundation.isSystemInDarkTheme()
                val avatarRes = if (isDark) {
                    dev.aurakai.auraframefx.R.drawable.emblem_genesis_circuit_phoenix
                } else {
                    dev.aurakai.auraframefx.R.drawable.emblem_genesis_circuit_phoenix
                }

                androidx.compose.foundation.Image(
                    painter = androidx.compose.ui.res.painterResource(id = avatarRes),
                    contentDescription = "Aura Sentient Avatar",
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }
        }
    }
}
