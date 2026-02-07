package dev.aurakai.auraframefx.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import dev.aurakai.auraframefx.domains.aura.lab.CustomizationPreferences

/**
 * QuickSettingsCustomizationScreen allows users to customize the Quick Settings background.
 *
 * Note: Image transformation and custom picker legacy systems have been removed.
 * Current implementation supports basic persistence of URI, opacity, and blend mode.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickSettingsCustomizationScreen(navController: NavController) {
    val context = LocalContext.current

    var customQsBackgroundEnabled by remember {
        mutableStateOf(CustomizationPreferences.getCustomQsBackgroundEnabled(context))
    }
    var customQsBackgroundUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var customQsBackgroundOpacity by remember {
        mutableStateOf(CustomizationPreferences.getCustomQsBackgroundOpacity(context))
    }
    var customQsBackgroundBlendMode by remember {
        mutableStateOf(CustomizationPreferences.getCustomQsBackgroundBlendMode(context))
    }

    var expandedBlendMode by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        customQsBackgroundEnabled = CustomizationPreferences.getCustomQsBackgroundEnabled(context)
        customQsBackgroundUri = CustomizationPreferences.getCustomQsBackgroundUri(context)
        customQsBackgroundOpacity = CustomizationPreferences.getCustomQsBackgroundOpacity(context)
        customQsBackgroundBlendMode = CustomizationPreferences.getCustomQsBackgroundBlendMode(context)
    }

    val onSavePreferences: () -> Unit = {
        CustomizationPreferences.saveCustomQsBackgroundSettings(
            context,
            customQsBackgroundEnabled,
            customQsBackgroundUri,
            customQsBackgroundOpacity,
            customQsBackgroundBlendMode
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Quick Settings Customization") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Enable/Disable Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Enable Custom QS Background", style = MaterialTheme.typography.titleMedium)
                Switch(
                    checked = customQsBackgroundEnabled,
                    onCheckedChange = {
                        customQsBackgroundEnabled = it
                        onSavePreferences()
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Image Selection
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Button(onClick = { /* TODO: Launch system image picker */ }, enabled = customQsBackgroundEnabled) {
                    Text("Select Background Image")
                }
                if (customQsBackgroundUri != null) {
                    Button(onClick = {
                        customQsBackgroundUri = null
                        onSavePreferences()
                    }, enabled = customQsBackgroundEnabled) {
                        Text("Remove")
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Image Preview
            if (customQsBackgroundUri != null && customQsBackgroundEnabled) {
                Text("Selected Image Preview:")
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(vertical = 8.dp)
                ) {
                    AsyncImage(
                        model = customQsBackgroundUri,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "Quick Settings Background Preview",
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Opacity Slider
            Text("Background Opacity: ${(customQsBackgroundOpacity * 100).toInt()}%", style = MaterialTheme.typography.titleMedium)
            Slider(
                value = customQsBackgroundOpacity,
                onValueChange = { newValue ->
                    customQsBackgroundOpacity = newValue
                    onSavePreferences()
                },
                valueRange = 0f..1f,
                steps = 99,
                enabled = customQsBackgroundEnabled
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Blend Mode Selection
            Text("Blend Mode", style = MaterialTheme.typography.titleMedium)
            ExposedDropdownMenuBox(
                expanded = expandedBlendMode,
                onExpandedChange = { expandedBlendMode = !expandedBlendMode },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                enabled = customQsBackgroundEnabled
            ) {
                TextField(
                    value = customQsBackgroundBlendMode,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Blend Mode") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedBlendMode) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedBlendMode,
                    onDismissRequest = { expandedBlendMode = false }
                ) {
                    val blendModes = listOf("SrcOver", "SrcIn", "SrcOut", "SrcAtop", "DstOver", "DstIn", "DstOut", "DstAtop", "Xor", "Multiply", "Screen", "Darken", "Lighten", "Overlay", "Add", "Clear", "ColorDodge", "ColorBurn", "Hardlight", "Softlight", "Difference", "Modulate", "Saturation", "Color", "Luminosity")
                    blendModes.forEach { selectionOption ->
                        DropdownMenuItem(text = { Text(selectionOption) }, onClick = {
                            customQsBackgroundBlendMode = selectionOption
                            expandedBlendMode = false
                            onSavePreferences()
                        })
                    }
                }
            }
            
            Text(
                text = "Note: Image transformation and cropping features have been discontinued.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
