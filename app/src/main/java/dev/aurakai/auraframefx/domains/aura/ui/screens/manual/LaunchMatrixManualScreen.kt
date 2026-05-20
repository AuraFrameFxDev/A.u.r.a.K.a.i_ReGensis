package dev.aurakai.auraframefx.ui.screens.manual

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.aurakai.auraframefx.domains.aura.ui.screens.aura.ReGenesisCustomizationViewModel
import dev.aurakai.auraframefx.domains.aura.ui.theme.KaiNeonGreen
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily

/**
 * 🚀 LAUNCH MATRIX - MANUAL CONTROL
 * Ported from PixelLauncherEnhanced-master.
 * Precision launcher adjustments.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchMatrixManualScreen(
    onNavigateBack: () -> Unit,
    viewModel: ReGenesisCustomizationViewModel = viewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val config = state.launcherConfig

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "LAUNCH MATRIX",
                        fontFamily = LEDFontFamily,
                        letterSpacing = 2.sp,
                        color = KaiNeonGreen
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black.copy(alpha = 0.9f),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color.Black
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    "LAUNCHER LAYERS (PLE PORT)",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Grid Rows
            item {
                ManualSlider(
                    label = "Desktop Grid Rows",
                    value = config.desktopRows.toFloat(),
                    range = 3f..10f,
                    accentColor = KaiNeonGreen,
                    onValueChange = {
                        viewModel.updateLauncherConfig(
                            context,
                            config.copy(desktopRows = it.toInt())
                        )
                    }
                )
            }

            // Grid Columns
            item {
                ManualSlider(
                    label = "Desktop Grid Columns",
                    value = config.desktopColumns.toFloat(),
                    range = 3f..10f,
                    accentColor = KaiNeonGreen,
                    onValueChange = {
                        viewModel.updateLauncherConfig(
                            context,
                            config.copy(desktopColumns = it.toInt())
                        )
                    }
                )
            }

            // Icon Size
            item {
                ManualSlider(
                    label = "Icon Size",
                    value = config.iconSize * 100f,
                    range = 50f..150f,
                    accentColor = KaiNeonGreen,
                    onValueChange = {
                        viewModel.updateLauncherConfig(
                            context,
                            config.copy(iconSize = it / 100f)
                        )
                    }
                )
            }

            // Themed Icons
            item {
                ManualSwitch(
                    label = "Force Themed Icons",
                    checked = config.themedIcons,
                    accentColor = KaiNeonGreen,
                    onCheckedChange = {
                        viewModel.updateLauncherConfig(
                            context,
                            config.copy(themedIcons = it)
                        )
                    }
                )
            }
            // Force Crash Button
            item {
                Button(
                    onClick = { throw RuntimeException("LDO Forced Crash Test (Initiated via LaunchMatrixManualScreen)") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("FORCE CRASH", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun ManualSlider(
    label: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    accentColor: Color,
    onValueChange: (Float) -> Unit
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, color = Color.White, fontSize = 16.sp)
            Text("${value.toInt()}", color = accentColor, fontSize = 14.sp)
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = range,
            colors = SliderDefaults.colors(
                thumbColor = accentColor,
                activeTrackColor = accentColor,
                inactiveTrackColor = Color.White.copy(alpha = 0.2f)
            )
        )
    }
}

@Composable
private fun ManualSwitch(
    label: String,
    checked: Boolean,
    accentColor: Color,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = Color.White, fontSize = 16.sp)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = accentColor,
                checkedTrackColor = accentColor.copy(alpha = 0.5f)
            )
        )
    }
}


