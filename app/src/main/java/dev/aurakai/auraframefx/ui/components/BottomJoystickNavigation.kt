package dev.aurakai.auraframefx.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
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
            .background(Color.Black),
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
    Box(
        modifier = Modifier
            .width(240.dp)
            .height(3.dp)
    ) {
        val indicatorPosition = selectedIndex / (totalTabs - 1).toFloat()

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(34.dp)
                .graphicsLayer {
                    translationX = (indicatorPosition * (240.dp.toPx() - 34.dp.toPx()))
                }
                .background(accentColor)
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .graphicsLayer { alpha = if (isSelected) 1f else 0.4f }
    ) {
        Box(
            modifier = Modifier
                .size(if (isSelected) 46.dp else 38.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) color else color.copy(alpha = 0.5f),
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
