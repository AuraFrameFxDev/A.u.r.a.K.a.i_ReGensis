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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotchBarCustomizationScreen(navController: NavController) {
    val context = LocalContext.current

    var notchBarBgEnabled by remember { mutableStateOf(false) }
    var notchBarBgUri by remember { mutableStateOf<Uri?>(null) }
    var notchBarBgOpacity by remember { mutableStateOf(1.0f) }
    var notchBarBgBlendMode by remember { mutableStateOf("SrcOver") }

    var expandedBlendMode by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        notchBarBgEnabled = CustomizationPreferences.getNotchBarBackgroundEnabled(context)
        notchBarBgUri = CustomizationPreferences.getNotchBarBackgroundUri(context)
        notchBarBgOpacity = CustomizationPreferences.getNotchBarBackgroundOpacity(context)
        notchBarBgBlendMode = CustomizationPreferences.getNotchBarBackgroundBlendMode(context)
    }

    val onSavePreferences: () -> Unit = {
        CustomizationPreferences.saveNotchBarBackgroundSettings(
            context,
            notchBarBgEnabled,
            notchBarBgUri,
            notchBarBgOpacity,
            notchBarBgBlendMode
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("NotchBar Customization") })
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
                Text(
                    "Enable Custom NotchBar Background",
                    style = MaterialTheme.typography.titleMedium
                )
                Switch(
                    checked = notchBarBgEnabled,
                    onCheckedChange = {
                        notchBarBgEnabled = it
                        onSavePreferences()
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Image Selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = { /* TODO: Launch system image picker */ },
                    enabled = notchBarBgEnabled
                ) {
                    Text("Select Image")
                }
                if (notchBarBgUri != null) {
                    Button(onClick = {
                        notchBarBgUri = null
                        onSavePreferences()
                    }, enabled = notchBarBgEnabled) {
                        Text("Remove")
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Image Preview
            if (notchBarBgUri != null && notchBarBgEnabled) {
                Text("Selected Image Preview:")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(vertical = 8.dp)
                ) {
                    AsyncImage(
                        model = notchBarBgUri,
                        contentDescription = "NotchBar Background Preview",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Opacity Slider
            Text(
                "Background Opacity: ${(notchBarBgOpacity * 100).toInt()}%",
                style = MaterialTheme.typography.titleMedium
            )
            Slider(
                value = notchBarBgOpacity,
                onValueChange = { newValue ->
                    notchBarBgOpacity = newValue
                    onSavePreferences()
                },
                valueRange = 0f..1f,
                steps = 99,
                enabled = notchBarBgEnabled
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Blend Mode Selection
            Text("Blend Mode", style = MaterialTheme.typography.titleMedium)
            ExposedDropdownMenuBox(
                expanded = expandedBlendMode,
                onExpandedChange = { expandedBlendMode = !expandedBlendMode },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                TextField(
                    value = notchBarBgBlendMode,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Blend Mode") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedBlendMode) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedBlendMode,
                    onDismissRequest = { expandedBlendMode = false }
                ) {
                    val blendModes = listOf(
                        "SrcOver", "SrcIn", "SrcOut", "SrcAtop",
                        "DstOver", "DstIn", "DstOut", "DstAtop",
                        "Xor", "Multiply", "Screen", "Darken", "Lighten",
                        "Overlay", "Clear", "ColorDodge", "ColorBurn",
                        "Hardlight", "Softlight", "Difference",
                        "Saturation", "Color", "Luminosity"
                    )
                    blendModes.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                notchBarBgBlendMode = selectionOption
                                expandedBlendMode = false
                                onSavePreferences()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Note: Image transformation and cropping features have been discontinued.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
