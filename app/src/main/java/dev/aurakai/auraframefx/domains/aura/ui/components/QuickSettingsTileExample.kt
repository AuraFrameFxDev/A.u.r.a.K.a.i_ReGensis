package dev.aurakai.auraframefx.domains.aura.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.domains.aura.lab.SpacingConfig
import dev.aurakai.auraframefx.domains.aura.lab.Presets
import dev.aurakai.auraframefx.ui.theme.AuraFrameFXTheme
import dev.aurakai.auraframefx.ui.theme.LocalSpacing
import dev.aurakai.auraframefx.ui.theme.SpacingProvider

@Composable
fun QuickSettingsTileExample(
    modifier: Modifier = Modifier,
    title: String = "Tile Title",
    icon: @Composable () -> Unit = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
    currentSpacingConfig: SpacingConfig = LocalSpacing.current
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(
                horizontal = currentSpacingConfig.quickSettingsTilePaddingHorizontal,
                vertical = currentSpacingConfig.quickSettingsTilePaddingVertical
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .padding(currentSpacingConfig.statusBarElementPaddingHorizontal, currentSpacingConfig.statusBarElementPaddingVertical),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuickSettingsTileExamplePreview() {
    AuraFrameFXTheme {
        Column {
            Text("Default Spacing:")
            QuickSettingsTileExample()
            Spacer(modifier = Modifier.height(16.dp))

            Text("Compact Spacing:")
            SpacingProvider(spacing = Presets.compact) {
                QuickSettingsTileExample(currentSpacingConfig = LocalSpacing.current)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text("Spacious Spacing:")
            SpacingProvider(spacing = Presets.spacious) {
                QuickSettingsTileExample(currentSpacingConfig = LocalSpacing.current)
            }
        }
    }
}
