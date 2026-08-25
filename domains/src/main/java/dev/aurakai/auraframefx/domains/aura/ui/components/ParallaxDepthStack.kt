package dev.aurakai.auraframefx.domains.aura.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * 🌌 PARALLAX DEPTH STACK — PURIFIED (NO TILT)
 * Static 4-tier visual stack for edge-to-edge manifestation.
 */
@Composable
fun ParallaxDepthStack(
    modifier: Modifier = Modifier,
    bedrock: @Composable BoxScope.() -> Unit,
    geometry: @Composable BoxScope.() -> Unit = {},
    interaction: @Composable BoxScope.() -> Unit = {},
    overlay: @Composable BoxScope.() -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        // LAYER 0: BEDROCK
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
            content = bedrock
        )

        // LAYER 1: GEOMETRY
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
            content = geometry
        )

        // LAYER 2: INTERACTION
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
            content = interaction
        )

        // LAYER 3: OVERLAY
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
            content = overlay
        )
    }
}
