package dev.aurakai.auraframefx.ui.arena

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun TrainingArenaScreen(
    viewModel: TrainingArenaViewModel
) {
    val progress by viewModel.progress.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Training Arena", style = MaterialTheme.typography.headlineMedium)
        LinearProgressIndicator(progress = { progress })
        Button(onClick = { viewModel.startTraining() }) {
            Text("Begin Training")
        }
    }
}
