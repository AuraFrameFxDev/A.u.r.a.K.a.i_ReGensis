package dev.aurakai.auraframefx.domains.aura.ui.theme.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import dev.aurakai.auraframefx.domains.aura.ui.theme.manager.ColorSchemeManager
import dev.aurakai.auraframefx.domains.genesis.network.model.Theme
import dev.aurakai.auraframefx.ui.theme.picker.ColorBlendrPicker

/**
 * SpectraCode ReGen - Advanced color customization system for AuraFrameFX
 *
 * A comprehensive color theming solution that provides intuitive color selection
 * and preview capabilities for the entire application theme.
 *
 * @param currentTheme The current theme to edit
 * @param onThemeUpdated Callback when the theme is updated
 * @param modifier Modifier for the composable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpectraCodeReGen(
    currentTheme: Theme,
    onThemeUpdated: (Theme) -> Unit,
    modifier: Modifier = Modifier
) {
    val colorSchemeManager = remember { ColorSchemeManager() }
    var selectedColorType by remember { mutableStateOf<String?>(null) }
    var showColorPicker by remember { mutableStateOf<Color?>(null) }

    val colors = currentTheme.colors ?: return

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Primary Color
        ThemeColorItem(
            label = "Primary",
            color = Color(colors.primary.toColorInt()),
            onColorClick = {
                selectedColorType = "primary"
                showColorPicker = Color(colors.primary.toColorInt())
            }
        )

        // Secondary Color
        ThemeColorItem(
            label = "Secondary",
            color = Color(colors.secondary.toColorInt()),
            onColorClick = {
                selectedColorType = "secondary"
                showColorPicker = Color(colors.secondary.toColorInt())
            }
        )

        // Background Color
        ThemeColorItem(
            label = "Background",
            color = Color(colors.background.toColorInt()),
            onColorClick = {
                selectedColorType = "background"
                showColorPicker = Color(colors.background.toColorInt())
            }
        )

        // Surface Color
        ThemeColorItem(
            label = "Surface",
            color = Color(colors.surface.toColorInt()),
            onColorClick = {
                selectedColorType = "surface"
                showColorPicker = Color(colors.surface.toColorInt())
            }
        )

        // Error Color
        ThemeColorItem(
            label = "Error",
            color = Color(colors.error.toColorInt()),
            onColorClick = {
                selectedColorType = "error"
                showColorPicker = Color(colors.error.toColorInt())
            }
        )
    }

    // Color Picker Dialog
    showColorPicker?.let { initialColor ->
        var currentColor by remember { mutableStateOf(initialColor) }
        val hexColor = colorSchemeManager.colorToHex(currentColor)
        val onColor = if (currentColor.luminance() > 0.6) "#000000" else "#FFFFFF"

        AlertDialog(
            onDismissRequest = {
                showColorPicker = null
                selectedColorType = null
            },
            title = {
                Column {
                    Text("SpectraCode ReGen", style = MaterialTheme.typography.headlineSmall)
                    Text("Color Selection", style = MaterialTheme.typography.bodyMedium)
                }
            },
            text = {
                // Using the ColorBlendrPicker here
                ColorBlendrPicker(
                    initialColor = currentColor,
                    onColorChanged = { newColor ->
                        currentColor = newColor
                    }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Update the theme with the new color
                        val updatedTheme = currentTheme.copy(
                            colors = colors.copy(
                                primary = if (selectedColorType == "primary") hexColor else colors.primary,
                                secondary = if (selectedColorType == "secondary") hexColor else colors.secondary,
                                background = if (selectedColorType == "background") hexColor else colors.background,
                                surface = if (selectedColorType == "surface") hexColor else colors.surface,
                                error = if (selectedColorType == "error") hexColor else colors.error,
                                onPrimary = if (selectedColorType == "primary") onColor else colors.onPrimary,
                                onSecondary = if (selectedColorType == "secondary") onColor else colors.onSecondary
                            )
                        )
                        onThemeUpdated(updatedTheme)
                        showColorPicker = null
                        selectedColorType = null
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Apply Theme")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showColorPicker = null
                        selectedColorType = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )

    }
}

/**
 * A single color item in the theme editor
 */
@Composable
private fun ThemeColorItem(
    label: String,
    color: Color,
    onColorClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onColorClick,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )

            Surface(
                color = color,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.size(40.dp)
            ) {}
        }
    }
}
