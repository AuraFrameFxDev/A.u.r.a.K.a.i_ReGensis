package dev.aurakai.auraframefx.domains.aura.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.domains.aura.lab.SpacingConfig
import dev.aurakai.auraframefx.ui.theme.AuraFrameFXTheme
import java.util.Locale

@Composable
fun DomainSubGateCarousel(
    modifier: Modifier = Modifier,
    title: String = "Sub-Gates",
    gates: List<String> = listOf("Sub-Gate 1", "Sub-Gate 2", "Sub-Gate 3", "Sub-Gate 4", "Sub-Gate 5"),
    currentSpacingConfig: SpacingConfig = SpacingConfig()
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(
                horizontal = currentSpacingConfig.headerPaddingHorizontal,
                vertical = currentSpacingConfig.headerPaddingVertical
            )
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                horizontal = currentSpacingConfig.defaultPaddingHorizontal,
                vertical = currentSpacingConfig.defaultPaddingVertical
            ),
            horizontalArrangement = Arrangement.spacedBy(currentSpacingConfig.cardMarginHorizontal)
        ) {
            items(gates) {
                val uniqueImageUsageKey = "gate_card_background_${it.replace(" ", "_").lowercase(Locale.ROOT)}"
                GateCard(
                    title = it,
                    description = "Description for $it",
                    currentSpacingConfig = currentSpacingConfig,
                    imageUsageKey = uniqueImageUsageKey
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DomainSubGateCarouselPreview() {
    AuraFrameFXTheme {
        DomainSubGateCarousel()
    }
}
