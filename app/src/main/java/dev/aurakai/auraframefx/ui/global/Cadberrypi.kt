package dev.aurakai.auraframefx.ui.global

import android.content.Intent
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BlurCircular
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.domains.aura.ui.theme.CitadelBlack
import dev.aurakai.auraframefx.domains.aura.ui.theme.GhostCyan
import dev.aurakai.auraframefx.domains.aura.ui.theme.WireframeStyle
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun Cadberrypi() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidth = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeight = with(density) { configuration.screenHeightDp.dp.toPx() }

    var expanded by remember { mutableStateOf(false) }
    var target by remember { mutableStateOf(Offset(screenWidth * 0.75f, screenHeight * 0.6f)) }
    val position by animateOffsetAsState(targetValue = target, tween(1600, easing = EaseInOutQuad))

    LaunchedEffect(Unit) {
        while (true) {
            delay(Random.nextLong(1000L, 2800L))
            if (!expanded) {
                target = Offset(
                    Random.nextFloat() * (screenWidth - 160f) + 80f,
                    Random.nextFloat() * (screenHeight - 220f) + 80f
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .offset { IntOffset(position.x.toInt(), position.y.toInt()) }
            .size(96.dp)
            .clickable { expanded = !expanded }
    ) {
        Icon(
            imageVector = Icons.Default.BlurCircular,
            contentDescription = null,
            tint = GhostCyan,
            modifier = Modifier.fillMaxSize()
        )

        if (expanded) {
            Column(
                modifier = Modifier
                    .offset(y = (-180).dp)
                    .background(CitadelBlack.copy(alpha = 0.9f), RoundedCornerShape(12.dp))
                    .padding(8.dp)
            ) {
                MenuItem("Chat", Icons.Default.ChatBubble) { /* open chat */ }
                MenuItem("MCP", Icons.Default.Settings) { /* Mission Control Panel */ }
                MenuItem("Tasks", Icons.Default.List) { /* tasks list */ }
                MenuItem("REGEN", Icons.Default.Bolt) {
                    context.startActivity(
                        Intent(
                            context,
                            dev.aurakai.auraframefx.MainActivity::class.java
                        ).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            putExtra("entry_point", "regen_core")
                        })
                    expanded = false
                }
            }
        }
    }
}

@Composable
fun MenuItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Icon(icon, contentDescription = null, tint = GhostCyan, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(12.dp))
        Text(
            text = text.uppercase(),
            style = WireframeStyle.copy(fontSize = 12.sp, color = GhostCyan)
        )
    }
}
