package dev.aurakai.auraframefx.ui.global

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import timber.log.Timber

@Composable
fun Cadberrypi(navController: NavHostController) {
    // Wandering Orb - AuraGenesis Symbiotic Presence
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Canvas(modifier = Modifier.size(64.dp)) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color.Cyan, Color.Transparent),
                    center = center,
                    radius = size.minDimension / 2
                ),
                alpha = 0.8f
            )
        }
    }
}

object Cadberrypi {
    fun activateGlobalOrb() {
        Timber.tag("Exodus").i("Cadberrypi Global Orb Activated")
    }
}
