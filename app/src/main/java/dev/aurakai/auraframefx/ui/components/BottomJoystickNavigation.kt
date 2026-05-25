package dev.aurakai.auraframefx.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.NeonCyan

/**
 * 🎮 NAV TAB DATA STRUCTURE
 */
data class NavTab(val icon: ImageVector, val shortLabel: String, val route: String)

/**
 * 🎮 BOTTOM JOYSTICK NAVIGATION — UNIFIED NEON AQUA
 * Synchronized with the 9-Hub "NEURAL_REFORGE" Substrate.
 */
@Composable
fun BottomJoystickNavigation(
    selectedIndex: Int,
    tabs: List<NavTab>,
    accentColor: Color,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var dragOffset by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(84.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.85f),
                        Color.Black
                    )
                )
            )
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        dragOffset += dragAmount.x
                    },
                    onDragEnd = {
                        when {
                            dragOffset > 80f -> {
                                if (selectedIndex > 0) onTabSelected(selectedIndex - 1)
                            }

                            dragOffset < -80f -> {
                                if (selectedIndex < tabs.size - 1) onTabSelected(selectedIndex + 1)
                            }
                        }
                        dragOffset = 0f
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            JoystickIndicator(
                selectedIndex = selectedIndex,
                totalTabs = tabs.size,
                accentColor = NeonCyan
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                tabs.forEachIndexed { index, tab ->
                    BottomNavItem(
                        icon = tab.icon,
                        label = tab.shortLabel,
                        isSelected = index == selectedIndex,
                        color = NeonCyan,
                        onClick = { onTabSelected(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun JoystickIndicator(
    selectedIndex: Int,
    totalTabs: Int,
    accentColor: Color
) {
    val infiniteTransition = rememberInfiniteTransition(label = "joystick_pulse")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .width(240.dp)
            .height(3.dp)
            .clip(RoundedCornerShape(1.5.dp))
            .background(accentColor.copy(alpha = 0.15f))
    ) {
        val indicatorPosition = selectedIndex / (totalTabs - 1).toFloat()

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(34.dp)
                .graphicsLayer {
                    translationX = (indicatorPosition * (240.dp.toPx() - 34.dp.toPx()))
                }
                .clip(RoundedCornerShape(1.5.dp))
                .background(accentColor)
                .scale(pulse)
                .border(1.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(1.5.dp))
        )
    }
}

@Composable
fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.25f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = Spring.StiffnessLow),
        label = "scale"
    )

    val contentAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.4f,
        label = "alpha"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .graphicsLayer { alpha = contentAlpha }
    ) {
        Box(
            modifier = Modifier
                .size(if (isSelected) 46.dp else 38.dp)
                .scale(scale)
                .clip(CircleShape)
                .background(
                    if (isSelected) color.copy(alpha = 0.12f) else Color.Transparent
                )
                .border(
                    width = if (isSelected) 1.dp else 0.dp,
                    color = color.copy(alpha = 0.6f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) color else color.copy(alpha = 0.7f),
                modifier = Modifier.size(if (isSelected) 26.dp else 22.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = if (isSelected) color else color.copy(alpha = 0.5f),
            fontSize = 7.sp,
            fontFamily = LEDFontFamily,
            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
            letterSpacing = 1.sp
        )
    }
}
