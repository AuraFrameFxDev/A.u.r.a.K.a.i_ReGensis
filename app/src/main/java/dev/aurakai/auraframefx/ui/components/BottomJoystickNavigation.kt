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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Security
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
 * 🎮 BOTTOM JOYSTICK NAVIGATION - UNIFIED NEON AQUA
 */
@Composable
fun BottomJoystickNavigation(
    selectedIndex: Int,
    tabs: List<String>,
    accentColor: Color, // Still passed but we'll use NeonCyan primarily
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var dragOffset by remember { mutableFloatStateOf(0f) }
    
    val tabIcons = listOf(
        Icons.Default.Dashboard,
        Icons.Default.Code,
        Icons.Default.Palette,
        Icons.Default.Security,
        Icons.Default.Hub,
        Icons.Default.Memory,
        Icons.Default.Groups
    )
    
    val shortLabels = listOf(
        "DASH", "LDO", "AURA", "KAI", "GEN", "CASC", "NEXUS"
    )
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.8f),
                        Color.Black.copy(alpha = 0.95f)
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
                            dragOffset > 100f -> {
                                if (selectedIndex > 0) onTabSelected(selectedIndex - 1)
                            }

                            dragOffset < -100f -> {
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
                tabs.forEachIndexed { index, _ ->
                    BottomNavItem(
                        icon = tabIcons[index],
                        label = shortLabels[index],
                        isSelected = index == selectedIndex,
                        color = NeonCyan,
                        onClick = { onTabSelected(index) }
                    )
                }
            }
        }
        
        if (dragOffset != 0f) {
            SwipeHintOverlay(dragOffset, NeonCyan)
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
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(accentColor.copy(alpha = 0.2f))
    ) {
        val indicatorPosition = selectedIndex / (totalTabs - 1).toFloat()
        
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(40.dp)
                .graphicsLayer {
                    translationX = (indicatorPosition * (200.dp.toPx() - 40.dp.toPx())).coerceIn(
                        0f,
                        (200.dp.toPx() - 40.dp.toPx())
                    )
                }
                .clip(RoundedCornerShape(2.dp))
                .background(accentColor)
                .scale(pulse)
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
        targetValue = if (isSelected) 1.2f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "scale"
    )
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(if (isSelected) 44.dp else 36.dp)
                .scale(scale)
                .clip(CircleShape)
                .background(
                    if (isSelected) color.copy(alpha = 0.1f) else Color.Transparent
                )
                .border(
                    width = if (isSelected) 1.dp else 0.dp,
                    color = color.copy(alpha = 0.5f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) color else color.copy(alpha = 0.4f),
                modifier = Modifier.size(if (isSelected) 24.dp else 20.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(2.dp))
        
        Text(
            text = label,
            color = if (isSelected) color else color.copy(alpha = 0.3f),
            fontSize = 8.sp,
            fontFamily = LEDFontFamily,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            letterSpacing = 1.sp
        )
    }
}

@Composable
fun SwipeHintOverlay(
    dragOffset: Float,
    accentColor: Color
) {
    val direction = when {
        dragOffset > 0 -> "← PREV"
        dragOffset < 0 -> "NEXT →"
        else -> ""
    }
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = if (dragOffset > 0) Alignment.CenterStart else Alignment.CenterEnd
    ) {
        Text(
            text = direction,
            color = accentColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = LEDFontFamily,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}
