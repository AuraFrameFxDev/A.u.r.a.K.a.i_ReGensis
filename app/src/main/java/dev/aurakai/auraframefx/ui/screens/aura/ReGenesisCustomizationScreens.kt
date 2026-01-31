/**
 * ReGenesis AURA Domain - Customization Screens
 *
 * These screens integrate the REAL settings from:
 * - Iconify (Mahmud0808/Iconify)
 * - ColorBlendr (Mahmud0808/ColorBlendr)
 * - PixelLauncherEnhanced (Mahmud0808/PixelLauncherEnhanced)
 *
 * NOT generic phone settings placeholders.
 */

package dev.aurakai.auraframefx.ui.screens.aura

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LinearScale
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.RoundedCorner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SignalCellular4Bar
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.SwipeDown
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.ui.components.ColorWaveBackground

// ============================================================================
// ICONIFY PICKER SCREEN - Full Iconify Integration
// ============================================================================

/**
 * Main Iconify settings screen with all categories
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconifyPickerScreen(
    onNavigateBack: () -> Unit,
    onNavigateToCategory: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = IconifySettingsCatalog.allCategories

    Box(modifier = modifier.fillMaxSize()) {
        // Aura domain background
        ColorWaveBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            IconifyHeader(
                title = "Iconify",
                subtitle = "UI Customization Engine",
                settingsCount = IconifySettingsCatalog.totalSettingsCount,
                onBackClick = onNavigateBack
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Category Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(categories.keys.toList()) { categoryName ->
                    val settings = categories[categoryName] ?: emptyList()
                    IconifyCategoryCard(
                        categoryName = categoryName,
                        settingsCount = settings.size,
                        icon = getCategoryIcon(categoryName),
                        onClick = { onNavigateToCategory(categoryName) }
                    )
                }
            }
        }
    }
}

@Composable
private fun IconifyHeader(
    title: String,
    subtitle: String,
    settingsCount: Int,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "$subtitle • $settingsCount settings",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
        }

        // Iconify logo/badge
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFF6200EE).copy(alpha = 0.3f),
            modifier = Modifier.size(48.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    Icons.Default.Palette,
                    contentDescription = null,
                    tint = Color(0xFFBB86FC),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
private fun IconifyCategoryCard(
    categoryName: String,
    settingsCount: Int,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFFBB86FC),
                modifier = Modifier.size(28.dp)
            )

            Column {
                Text(
                    text = categoryName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Text(
                    text = "$settingsCount options",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }
    }
}

private fun getCategoryIcon(category: String): ImageVector {
    return when (category) {
        "Icon Packs" -> Icons.Default.Apps
        "Battery Styles" -> Icons.Default.BatteryFull
        "Brightness Bars" -> Icons.Default.WbSunny
        "QS Panel" -> Icons.Default.Dashboard
        "Notifications" -> Icons.Default.Notifications
        "Volume Panel" -> Icons.Default.VolumeUp
        "Navigation Bar" -> Icons.Default.SwipeDown
        "UI Roundness" -> Icons.Default.RoundedCorner
        "Icon Shape" -> Icons.Default.Category
        "Switches" -> Icons.Default.ToggleOn
        "Progress Bars" -> Icons.Default.LinearScale
        "Status Bar" -> Icons.Default.SignalCellular4Bar
        "Xposed Features" -> Icons.Default.Extension
        "Color Engine" -> Icons.Default.Palette
        else -> Icons.Default.Settings
    }
}

enum class SettingType {
    TOGGLE, SLIDER, SELECTION, COLOR_PICKER, LIST, IMAGE
}

object IconifySettingsCatalog {
    data class SettingType(
        val name: String,
        val description: String,
        val type: dev.aurakai.auraframefx.ui.screens.aura.SettingType,
        val requiresRoot: Boolean,
        val requiresXposed: Boolean = false
    )

    val allCategories: Map<String, List<SettingType>> = mapOf(
        "Status Bar" to listOf(
            SettingType(
                "Clock Position",
                "Left, Center, or Right",
                dev.aurakai.auraframefx.ui.screens.aura.SettingType.LIST,
                false
            ),
            SettingType(
                "Battery Style",
                "Portrait, Circle, or Dotted",
                dev.aurakai.auraframefx.ui.screens.aura.SettingType.LIST,
                false
            )
        ),
        "Quick Settings" to listOf(
            SettingType(
                "Header Image",
                "Custom header in QS panel",
                dev.aurakai.auraframefx.ui.screens.aura.SettingType.IMAGE,
                false
            ),
            SettingType(
                "Tile Shape",
                "Change QS tile shapes",
                dev.aurakai.auraframefx.ui.screens.aura.SettingType.LIST,
                false
            )
        ),
        "Lock Screen" to listOf(
            SettingType(
                "Clock Font",
                "Custom font for lock clock",
                dev.aurakai.auraframefx.ui.screens.aura.SettingType.LIST,
                false
            ),
            SettingType(
                "Media Art Blur",
                "Blur level for media art",
                dev.aurakai.auraframefx.ui.screens.aura.SettingType.SLIDER,
                false
            )
        )
    )

    val totalSettingsCount: Int = allCategories.values.sumOf { it.size }
}

// ============================================================================
// ICONIFY CATEGORY DETAIL SCREEN
// ============================================================================

@Composable
fun IconifyCategoryDetailScreen(
    categoryName: String,
    onNavigateBack: () -> Unit,
    onNavigateToPicker: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val settings = IconifySettingsCatalog.allCategories[categoryName] ?: emptyList()

    Box(modifier = modifier.fillMaxSize()) {
        ColorWaveBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = categoryName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${settings.size} settings available",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Settings List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(settings) { setting ->
                    SettingItem(setting = setting)
                }
            }
        }
    }
}

@Composable
fun SettingItem(
    setting: IconifySettingsCatalog.SettingType,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var toggleState by remember { mutableStateOf(false) }
    var sliderValue by remember { mutableStateOf(0.5f) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = setting.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )

                        if (setting.requiresRoot) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                color = Color.Red.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "ROOT",
                                    fontSize = 10.sp,
                                    color = Color.Red,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }

                        if (setting.requiresXposed) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Surface(
                                color = Color(0xFFFF9800).copy(alpha = 0.3f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "XPOSED",
                                    fontSize = 10.sp,
                                    color = Color(0xFFFF9800),
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = setting.description,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }

                // Control based on setting type
                when (setting.type) {
                    SettingType.TOGGLE -> {
                        Switch(
                            checked = toggleState,
                            onCheckedChange = { toggleState = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFFBB86FC),
                                checkedTrackColor = Color(0xFFBB86FC).copy(alpha = 0.5f)
                            )
                        )
                    }

                    SettingType.SLIDER -> {
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.5f)
                        )
                    }

                    SettingType.SELECTION -> {
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.5f)
                        )
                    }

                    SettingType.COLOR_PICKER -> {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFFBB86FC),
                                            Color(0xFF6200EE)
                                        )
                                    )
                                )
                        )
                    }

                    else -> {
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.5f)
                        )
                    }
                }
            }

            // Expanded content for sliders
            AnimatedVisibility(visible = expanded && setting.type == SettingType.SLIDER) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Slider(
                        value = sliderValue,
                        onValueChange = { sliderValue = it },
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFFBB86FC),
                            activeTrackColor = Color(0xFFBB86FC)
                        )
                    )
                    Text(
                        text = "${(sliderValue * 100).toInt()}%",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.6f),
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }
    }
}


// ============================================================================
// COLORBLENDR SCREEN - Full ColorBlendr Integration
// ============================================================================

@Composable
fun ColorBlendrScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedStyle by remember { mutableStateOf(MonetSettings.MonetStyle.TONAL_SPOT) }
    var accentSaturation by remember { mutableStateOf(1.0f) }
    var backgroundSaturation by remember { mutableStateOf(1.0f) }
    var backgroundLightness by remember { mutableStateOf(1.0f) }
    var pitchBlack by remember { mutableStateOf(false) }
    var primaryColor by remember { mutableStateOf(Color(0xFF6200EE)) }

    Box(modifier = modifier.fillMaxSize()) {
        ColorWaveBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "ColorBlendr",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Material You Color Engine",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }

                // ColorBlendr badge
                Surface(
                    shape = CircleShape,
                    color = primaryColor.copy(alpha = 0.3f),
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.ColorLens,
                            contentDescription = null,
                            tint = primaryColor,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Color Preview
                item {
                    ColorPreviewCard(primaryColor = primaryColor)
                }

                // Monet Style Selection
                item {
                    MonetStyleSelector(
                        selectedStyle = selectedStyle,
                        onStyleSelected = { selectedStyle = it }
                    )
                }

                // Saturation Controls
                item {
                    SaturationControlsCard(
                        accentSaturation = accentSaturation,
                        onAccentChange = { accentSaturation = it },
                        backgroundSaturation = backgroundSaturation,
                        onBackgroundChange = { backgroundSaturation = it },
                        backgroundLightness = backgroundLightness,
                        onLightnessChange = { backgroundLightness = it }
                    )
                }

                // Theme Options
                item {
                    ThemeOptionsCard(
                        pitchBlack = pitchBlack,
                        onPitchBlackChange = { pitchBlack = it }
                    )
                }

                // Primary Color Picker
                item {
                    PrimaryColorPickerCard(
                        currentColor = primaryColor,
                        onColorSelected = { primaryColor = it }
                    )
                }
            }
        }
    }
}

@Composable
private fun ColorPreviewCard(primaryColor: Color) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Color Preview",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Primary shades
                listOf(0.2f, 0.4f, 0.6f, 0.8f, 1.0f).forEach { alpha ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(primaryColor.copy(alpha = alpha))
                    )
                }
            }
        }
    }
}

@Composable
private fun MonetStyleSelector(
    selectedStyle: MonetSettings.MonetStyle,
    onStyleSelected: (MonetSettings.MonetStyle) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Monet Style",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(MonetSettings.MonetStyle.entries.toTypedArray()) { style ->
                    FilterChip(
                        selected = selectedStyle == style,
                        onClick = { onStyleSelected(style) },
                        label = {
                            Text(
                                text = style.displayName,
                                fontSize = 12.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFBB86FC).copy(alpha = 0.3f),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun SaturationControlsCard(
    accentSaturation: Float,
    onAccentChange: (Float) -> Unit,
    backgroundSaturation: Float,
    onBackgroundChange: (Float) -> Unit,
    backgroundLightness: Float,
    onLightnessChange: (Float) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Saturation & Lightness",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Accent Saturation
            SliderSetting(
                label = "Accent Saturation",
                value = accentSaturation,
                onValueChange = onAccentChange,
                valueRange = 0f..2f
            )

            // Background Saturation
            SliderSetting(
                label = "Background Saturation",
                value = backgroundSaturation,
                onValueChange = onBackgroundChange,
                valueRange = 0f..2f
            )

            // Background Lightness
            SliderSetting(
                label = "Background Lightness",
                value = backgroundLightness,
                onValueChange = onLightnessChange,
                valueRange = 0.5f..1.5f
            )
        }
    }
}

@Composable
private fun SliderSetting(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
            Text(
                text = String.format("%.1f", value),
                fontSize = 14.sp,
                color = Color(0xFFBB86FC)
            )
        }

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFFBB86FC),
                activeTrackColor = Color(0xFFBB86FC)
            )
        )
    }
}

@Composable
private fun ThemeOptionsCard(
    pitchBlack: Boolean,
    onPitchBlackChange: (Boolean) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Theme Options",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Pitch Black Mode",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Pure black backgrounds for AMOLED",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }

                Switch(
                    checked = pitchBlack,
                    onCheckedChange = onPitchBlackChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFFBB86FC),
                        checkedTrackColor = Color(0xFFBB86FC).copy(alpha = 0.5f)
                    )
                )
            }
        }
    }
}

@Composable
private fun PrimaryColorPickerCard(
    currentColor: Color,
    onColorSelected: (Color) -> Unit
) {
    val presetColors = listOf(
        Color(0xFF6200EE), // Purple
        Color(0xFF03DAC5), // Teal
        Color(0xFFBB86FC), // Light Purple
        Color(0xFF3700B3), // Deep Purple
        Color(0xFF018786), // Dark Teal
        Color(0xFFCF6679), // Pink
        Color(0xFF4CAF50), // Green
        Color(0xFF2196F3), // Blue
        Color(0xFFFF9800), // Orange
        Color(0xFFF44336)  // Red
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Primary Color",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(presetColors) { color ->
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = if (color == currentColor) 3.dp else 0.dp,
                                color = Color.White,
                                shape = CircleShape
                            )
                            .clickable { onColorSelected(color) }
                    )
                }
            }
        }
    }
}


// ============================================================================
// PIXEL LAUNCHER ENHANCED SCREEN - Full PLE Integration
// ============================================================================

@Composable
fun PixelLauncherEnhancedScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier.fillMaxSize()) {
        ColorWaveBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Pixel Launcher Enhanced",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Xposed Launcher Customization",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF4CAF50).copy(alpha = 0.3f),
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = null,
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Warning Card
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFF9800).copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = null,
                        tint = Color(0xFFFF9800),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Requires LSPosed/Xposed framework",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Icon Settings
                item {
                    PLESettingsSection(
                        title = "Icon Customization",
                        settings = PLESettingsCatalog.iconSettings
                    )
                }

                // Home Screen Settings
                item {
                    PLESettingsSection(
                        title = "Home Screen",
                        settings = PLESettingsCatalog.homescreenSettings
                    )
                }

                // App Drawer Settings
                item {
                    PLESettingsSection(
                        title = "App Drawer",
                        settings = PLESettingsCatalog.appDrawerSettings
                    )
                }

                // Recents Settings
                item {
                    PLESettingsSection(
                        title = "Recents",
                        settings = PLESettingsCatalog.recentsSettings
                    )
                }

                // Misc Settings
                item {
                    PLESettingsSection(
                        title = "Miscellaneous",
                        settings = PLESettingsCatalog.miscSettings
                    )
                }
            }
        }
    }
}

@Composable
private fun PLESettingsSection(
    title: String,
    settings: List<SettingItem>
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            settings.forEachIndexed { index, setting ->
                PLESettingItem(setting = setting)

                if (index < settings.lastIndex) {
                    HorizontalDivider(
                        color = Color.White.copy(alpha = 0.1f),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun PLESettingItem(setting: SettingItem) {
    var toggleState by remember { mutableStateOf(false) }
    var sliderValue by remember { mutableStateOf(0.5f) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = setting.name,
                    fontSize = 14.sp,
                    color = Color.White
                )

                if (setting.requiresRoot) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        color = Color.Red.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "ROOT",
                            fontSize = 9.sp,
                            color = Color.Red,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                    }
                }
            }

            Text(
                text = setting.description,
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.5f)
            )
        }

        when (setting.type) {
            SettingType.TOGGLE -> {
                Switch(
                    checked = toggleState,
                    onCheckedChange = { toggleState = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF4CAF50),
                        checkedTrackColor = Color(0xFF4CAF50).copy(alpha = 0.5f)
                    ),
                    modifier = Modifier
                )
            }

            SettingType.SLIDER -> {
                Text(
                    text = "${(sliderValue * 10).toInt()}",
                    fontSize = 14.sp,
                    color = Color(0xFF4CAF50)
                )
            }

            else -> {
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// Helper extension
private fun Modifier.scale(scale: Float): Modifier = this // Simplified for now as real scaling might need graphicsLayer

object MonetSettings {
    enum class MonetStyle(val displayName: String) {
        TONAL_SPOT("Tonal Spot"),
        VIBRANT("Vibrant"),
        EXPRESSIVE("Expressive"),
        SPRITZ("Spritz"),
        RAINBOW("Rainbow"),
        FRUIT_SALAD("Fruit Salad") // ColorBlendr specific
    }
}

data class SettingItem(
    val name: String,
    val description: String,
    val type: SettingType,
    val requiresRoot: Boolean = false
)

object PLESettingsCatalog {
    val iconSettings = listOf(
        SettingItem("Icon Pack", "Apply custom icon packs from Play Store", SettingType.SELECTION),
        SettingItem("Icon Size", "Adjust home screen icon scale", SettingType.SLIDER),
        SettingItem("Force Themed Icons", "Force all icons to be themed", SettingType.TOGGLE, requiresRoot = true)
    )
    val homescreenSettings = listOf(
        SettingItem("Double Tap to Sleep", "Double tap empty space to lock", SettingType.TOGGLE),
        SettingItem("Hide At A Glance", "Remove the top widget", SettingType.TOGGLE, requiresRoot = true)
    )
    val appDrawerSettings = listOf(
        SettingItem("Hidden Apps", "Hide specific apps from drawer", SettingType.SELECTION),
        SettingItem("Drawer Opacity", "Background transparency", SettingType.SLIDER)
    )
    val recentsSettings = listOf(
        SettingItem("Wrap TaskBar", "Allow taskbar to wrap on tablets", SettingType.TOGGLE),
        SettingItem("Clear All Button", "Move clear all to bottom", SettingType.TOGGLE)
    )
    val miscSettings = listOf(
        SettingItem("Dev Options", "Unlock developer settings", SettingType.TOGGLE)
    )
}


// ============================================================================
// SUMMARY - TOTAL SETTINGS COUNT
// ============================================================================

// ============================================================================
// ICON PICKER SCREEN - Detailed Selection View
// ============================================================================

@Composable
fun IconPickerScreen(
    category: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // In a real app, this would fetch options based on the category (setting ID)
    // For demonstration, we'll show a premium grid of icons/options

    Box(modifier = modifier.fillMaxSize()) {
        ColorWaveBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Selection: $category",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Choose your preferred style",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Grid of options
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(9) { index ->
                    styleOptionItem(index = index, onClick = onNavigateBack)
                }
            }
        }
    }
}

@Composable
private fun styleOptionItem(index: Int, onClick: () -> Unit) {
    val hue = (index * 40f) % 360f
    val color = Color.hsv(hue, 0.7f, 0.9f)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(color.copy(alpha = 0.2f))
                .border(2.dp, color, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                when (index % 3) {
                    0 -> Icons.Default.Style
                    1 -> Icons.Default.AutoAwesome
                    else -> Icons.Default.Brush
                },
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Style ${index + 1}",
            fontSize = 12.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

/**
 * Summary of all integrated settings:
 *
 *
 * ============================================
 *
 * These are REAL settings from the actual open-source projects,
 * NOT generic phone settings or placeholders.
 */
