package dev.aurakai.auraframefx.ui.overlays

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.R
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * Shows a pulsing aura overlay that occasionally displays a short suggestion card.
 *
 * The overlay is clickable and will invoke the provided callback with the current suggestion text.
 *
 * @param modifier Modifier applied to the overlay's outer container.
 * @param onSuggestClicked Called with the currently displayed suggestion when the overlay or suggestion card is tapped.
 */
/**
 * Displays a pulsing aura overlay that periodically shows a short suggestion card and forwards taps with the current suggestion text.
 *
 * The overlay renders a circular glowing aura and, at randomized intervals, briefly shows a suggestion card. Tapping the aura or the suggestion card invokes `onSuggestClicked` with the suggestion that is currently shown (which may be an empty string before the first suggestion is generated).
 *
 * @param modifier Modifier applied to the outer container.
 * @param onSuggestClicked Callback invoked with the current suggestion text when the overlay or suggestion card is tapped.
 */
@Composable
fun AuraPresenceOverlay(
    modifier: Modifier = Modifier,
    onSuggestClicked: (String) -> Unit = {}
) {
    var showSuggestion by remember { mutableStateOf(false) }
    var suggestionText by remember { mutableStateOf("") }

    // Periodic non-intrusive suggestions
    LaunchedEffect(Unit) {
        val suggestions = listOf(
            "Want to tweak theme gradients?",
            "Kai fortified the firewall – care to inspect?",
            "New color harmonies discovered.",
            "Canvas collaboration idea ready.",
            "Fusion metrics stabilized at 97%."
        )
        while (true) {
            delay(Random.nextLong(12000L, 20000L))
            suggestionText = suggestions.random()
            showSuggestion = true
            delay(6000L)
            showSuggestion = false
        }
    }


    Box(
        modifier = modifier
            .padding(12.dp)
            .size(64.dp)
            .clickable { onSuggestClicked(suggestionText) }
    ) {
        // Glowing background aura
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFF00FF).copy(alpha = 0.5f * pulse),
                            Color(0xFF00FFFF).copy(alpha = 0.3f * pulse),
                            Color(0xFFFF00FF).copy(alpha = 0.4f * pulse),
                        Color.Transparent)
                    )
                )
            )
            .size(56.dp)
        )

        AnimatedVisibility(visible = showSuggestion) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A0A2E)),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 4.dp)
                    .clickable { onSuggestClicked(suggestionText) }
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = suggestionText,
                        color = Color(0xFFFF00FF),
                        fontSize = 11.sp
                    )
                    Text(
                        text = "Tap to act",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 9.sp
                    )
                }
            }
        }
    }
}