package dev.aurakai.auraframefx.ui.global

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BlurCircular
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.domains.aura.ui.theme.GhostCyan
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun Cadberrypi() {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidth = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeight = with(density) { configuration.screenHeightDp.dp.toPx() }

    var target by remember { mutableStateOf(Offset(screenWidth * 0.75f, screenHeight * 0.6f)) }
    val position by animateOffsetAsState(targetValue = target, tween(1600))

    LaunchedEffect(Unit) {
        while (true) {
            delay(Random.nextLong(1200L, 2800L))
            target = Offset(
                Random.nextFloat() * (screenWidth - 160f) + 80f,
                Random.nextFloat() * (screenHeight - 220f) + 80f
            )
        }
    }

    Box(
        modifier = Modifier
            .offset { IntOffset(position.x.toInt(), position.y.toInt()) }
            .size(96.dp)
    ) {
        // Try PNG first, fallback to icon is implicit by layering or try/catch if resource exists
        // Since we can't easily try/catch a painterResource in Compose without checking R.drawable
        // we'll layer them for now, or just provide the Icon as a safe default.

        Icon(
            imageVector = Icons.Default.BlurCircular,
            contentDescription = null,
            tint = GhostCyan,
            modifier = Modifier.fillMaxSize()
        )

        // Uncomment if cadberrypi_orb exists in res/drawable
        /*
        Image(
            painter = painterResource(id = R.drawable.cadberrypi_orb),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        */
    }
}
