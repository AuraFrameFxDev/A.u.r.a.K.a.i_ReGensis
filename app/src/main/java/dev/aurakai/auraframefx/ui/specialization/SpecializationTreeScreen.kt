package dev.aurakai.auraframefx.ui.specialization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun SpecializationTreeScreen(
    viewModel: SpecializationViewModel,
    onBackTriggered: () -> Unit
) {
    val nodes by viewModel.nodes.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Specialization Tree", style = MaterialTheme.typography.headlineMedium)
        nodes.forEach { node ->
            Text("- $node")
        }
        Button(onClick = onBackTriggered) {
            Text("Back")
        }
    }
}
