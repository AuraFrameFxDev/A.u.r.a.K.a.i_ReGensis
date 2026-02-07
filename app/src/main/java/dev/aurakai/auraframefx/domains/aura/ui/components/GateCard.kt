package dev.aurakai.auraframefx.domains.aura.ui.components

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.aurakai.auraframefx.domains.aura.lab.ImageTransformation
import dev.aurakai.auraframefx.domains.aura.lab.SpacingConfig
import dev.aurakai.auraframefx.domains.aura.lab.CustomizationPreferences
import dev.aurakai.auraframefx.ui.theme.AuraFrameFXTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect

@Composable
fun GateCard(
    modifier: Modifier = Modifier,
    title: String = "Default Gate Title",
    description: String = "This is a description for the gate. It provides context and information.",
    currentSpacingConfig: SpacingConfig = SpacingConfig(), // Default or provided spacing
    imageUsageKey: String = "gate_card_background" // Unique key for this image
) {
    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageTransformation by remember { mutableStateOf(ImageTransformation()) }

    LaunchedEffect(imageUsageKey) {
        val (uri, transformation) = CustomizationPreferences.getImageWithTransformation(context, imageUsageKey)
        imageUri = uri
        imageTransformation = transformation ?: ImageTransformation()
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = currentSpacingConfig.gateMarginHorizontal,
                vertical = currentSpacingConfig.gateMarginVertical
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(120.dp) // Example image height
        ) {
            if (imageUri != null) {
                TransformedImage(
                    uri = imageUri,
                    transformation = imageTransformation,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "Gate Background Image"
                )
            } else {
                // Fallback or solid color background if no image
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = currentSpacingConfig.gatePaddingHorizontal,
                        vertical = currentSpacingConfig.gatePaddingVertical
                    )
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GateCardPreview() {
    AuraFrameFXTheme {
        GateCard()
    }
}
