package dev.aurakai.auraframefx.domains.aura.ui.components

import androidx.compose.ui.platform.LocalContext
import dev.aurakai.auraframefx.domains.aura.lab.CustomizationPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.domains.aura.lab.SpacingConfig
import dev.aurakai.auraframefx.domains.aura.lab.Presets
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpacingControlPanel(
    modifier: Modifier = Modifier,
    onSpacingConfigChange: ((SpacingConfig) -> Unit)? = null
) {
    val context = LocalContext.current
    var spacing by remember { mutableStateOf(CustomizationPreferences.getSpacingConfig(context)) }

    val saveSpacing: (SpacingConfig) -> Unit = { newConfig ->
        spacing = newConfig
        CustomizationPreferences.saveSpacingConfig(context, newConfig)
        onSpacingConfigChange?.invoke(newConfig)
    }

    Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        Text("Spacing Controls", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        // Presets
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            PresetButton("Compact") { saveSpacing(Presets.compact) }
            PresetButton("Default") { saveSpacing(Presets.default) }
            PresetButton("Comfortable") { saveSpacing(Presets.comfortable) }
            PresetButton("Spacious") { saveSpacing(Presets.spacious) }
        }
        Spacer(Modifier.height(16.dp))

        // Individual Spacing Controls
        SpacerControl("Default Margin H", spacing.defaultMarginHorizontal) { newValue ->
            saveSpacing(spacing.copy(defaultMarginHorizontal = newValue))
        }
        SpacerControl("Default Margin V", spacing.defaultMarginVertical) { newValue ->
            saveSpacing(spacing.copy(defaultMarginVertical = newValue))
        }
        SpacerControl("Default Padding H", spacing.defaultPaddingHorizontal) { newValue ->
            saveSpacing(spacing.copy(defaultPaddingHorizontal = newValue))
        }
        SpacerControl("Default Padding V", spacing.defaultPaddingVertical) { newValue ->
            saveSpacing(spacing.copy(defaultPaddingVertical = newValue))
        }

        SpacerControl("Gate Margin H", spacing.gateMarginHorizontal) { newValue ->
            saveSpacing(spacing.copy(gateMarginHorizontal = newValue))
        }
        SpacerControl("Gate Margin V", spacing.gateMarginVertical) { newValue ->
            saveSpacing(spacing.copy(gateMarginVertical = newValue))
        }
        SpacerControl("Card Padding H", spacing.cardPaddingHorizontal) { newValue ->
            saveSpacing(spacing.copy(cardPaddingHorizontal = newValue))
        }
        SpacerControl("Header Padding V", spacing.headerPaddingVertical) { newValue ->
            saveSpacing(spacing.copy(headerPaddingVertical = newValue))
        }
    }
}

@Composable
private fun SpacerControl(label: String, currentValue: Dp, onValueChange: (Dp) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, modifier = Modifier.weight(0.4f))

        Slider(
            value = currentValue.value,
            onValueChange = { newValue -> onValueChange(newValue.roundToInt().dp) },
            valueRange = 0f..32f,
            steps = 31,
            modifier = Modifier.weight(0.4f)
        )

        OutlinedTextField(
            value = currentValue.value.roundToInt().toString(),
            onValueChange = { text ->
                val dpValue = text.toIntOrNull()?.dp ?: 0.dp
                onValueChange(dpValue.coerceIn(0.dp, 32.dp))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(0.2f).padding(start = 8.dp),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodySmall,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
private fun PresetButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Text(text)
    }
}
