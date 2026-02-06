
package dev.aurakai.auraframefx.aura.ui.components
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
import dev.aurakai.auraframefx.aura.lab.SpacingConfig
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpacingControlPanel(
    modifier: Modifier = Modifier,
    onSpacingConfigChange: ((SpacingConfig) -> Unit)? = null // Make it optional for internal use
) {
    val context = LocalContext.current
    var spacing by remember { mutableStateOf(CustomizationPreferences.getSpacingConfig(context)) }

    // Save changes to preferences whenever spacing changes internally
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
        // Add more SpacerControl for other spacing properties (gates, cards, headers, etc.)
        // For brevity, only a few are shown here.

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
            steps = 31, // 0.dp to 32.dp with 1.dp steps
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

private object Presets {
    val compact = SpacingConfig(
        defaultMarginHorizontal = 8.dp, defaultMarginVertical = 4.dp,
        defaultPaddingHorizontal = 8.dp, defaultPaddingVertical = 4.dp,
        gateMarginHorizontal = 12.dp, gateMarginVertical = 6.dp,
        gatePaddingHorizontal = 8.dp, gatePaddingVertical = 4.dp,
        cardMarginHorizontal = 4.dp, cardMarginVertical = 4.dp,
        cardPaddingHorizontal = 6.dp, cardPaddingVertical = 6.dp,
        headerMarginHorizontal = 8.dp, headerMarginVertical = 4.dp,
        headerPaddingHorizontal = 8.dp, headerPaddingVertical = 8.dp,
        quickSettingsTileMarginHorizontal = 2.dp, quickSettingsTileMarginVertical = 2.dp,
        quickSettingsTilePaddingHorizontal = 4.dp, quickSettingsTilePaddingVertical = 4.dp,
        statusBarElementMarginHorizontal = 4.dp, statusBarElementMarginVertical = 2.dp,
        statusBarElementPaddingHorizontal = 2.dp, statusBarElementPaddingVertical = 1.dp
    )

    val default = SpacingConfig(
        defaultMarginHorizontal = 16.dp, defaultMarginVertical = 16.dp,
        defaultPaddingHorizontal = 16.dp, defaultPaddingVertical = 16.dp,
        gateMarginHorizontal = 24.dp, gateMarginVertical = 12.dp,
        gatePaddingHorizontal = 16.dp, gatePaddingVertical = 8.dp,
        cardMarginHorizontal = 8.dp, cardMarginVertical = 8.dp,
        cardPaddingHorizontal = 12.dp, cardPaddingVertical = 12.dp,
        headerMarginHorizontal = 16.dp, headerMarginVertical = 8.dp,
        headerPaddingHorizontal = 16.dp, headerPaddingVertical = 16.dp,
        quickSettingsTileMarginHorizontal = 4.dp, quickSettingsTileMarginVertical = 4.dp,
        quickSettingsTilePaddingHorizontal = 8.dp, quickSettingsTilePaddingVertical = 8.dp,
        statusBarElementMarginHorizontal = 8.dp, statusBarElementMarginVertical = 4.dp,
        statusBarElementPaddingHorizontal = 4.dp, statusBarElementPaddingVertical = 2.dp
    )

    val comfortable = SpacingConfig(
        defaultMarginHorizontal = 24.dp, defaultMarginVertical = 12.dp,
        defaultPaddingHorizontal = 20.dp, defaultPaddingVertical = 10.dp,
        gateMarginHorizontal = 32.dp, gateMarginVertical = 16.dp,
        gatePaddingHorizontal = 20.dp, gatePaddingVertical = 10.dp,
        cardMarginHorizontal = 12.dp, cardMarginVertical = 10.dp,
        cardPaddingHorizontal = 16.dp, cardPaddingVertical = 14.dp,
        headerMarginHorizontal = 20.dp, headerMarginVertical = 10.dp,
        headerPaddingHorizontal = 20.dp, headerPaddingVertical = 20.dp,
        quickSettingsTileMarginHorizontal = 6.dp, quickSettingsTileMarginVertical = 6.dp,
        quickSettingsTilePaddingHorizontal = 10.dp, quickSettingsTilePaddingVertical = 10.dp,
        statusBarElementMarginHorizontal = 10.dp, statusBarElementMarginVertical = 6.dp,
        statusBarElementPaddingHorizontal = 6.dp, statusBarElementPaddingVertical = 3.dp
    )

    val spacious = SpacingConfig(
        defaultMarginHorizontal = 32.dp, defaultMarginVertical = 16.dp,
        defaultPaddingHorizontal = 24.dp, defaultPaddingVertical = 12.dp,
        gateMarginHorizontal = 40.dp, gateMarginVertical = 20.dp,
        gatePaddingHorizontal = 24.dp, gatePaddingVertical = 12.dp,
        cardMarginHorizontal = 16.dp, cardMarginVertical = 12.dp,
        cardPaddingHorizontal = 20.dp, cardPaddingVertical = 16.dp,
        headerMarginHorizontal = 24.dp, headerMarginVertical = 12.dp,
        headerPaddingHorizontal = 24.dp, headerPaddingVertical = 24.dp,
        quickSettingsTileMarginHorizontal = 8.dp, quickSettingsTileMarginVertical = 8.dp,
        quickSettingsTilePaddingHorizontal = 12.dp, quickSettingsTilePaddingVertical = 12.dp,
        statusBarElementMarginHorizontal = 12.dp, statusBarElementMarginVertical = 8.dp,
        statusBarElementPaddingHorizontal = 8.dp, statusBarElementPaddingVertical = 4.dp
    )
}
