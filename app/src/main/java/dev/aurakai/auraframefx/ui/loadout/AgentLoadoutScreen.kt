package dev.aurakai.auraframefx.ui.loadout

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
fun AgentLoadoutScreen(
    viewModel: LoadoutViewModel,
    onAgentSelected: (String) -> Unit
) {
    val squad by viewModel.squad.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Agent Loadout Builder", style = MaterialTheme.typography.headlineMedium)
        squad.forEach { agent ->
            Button(onClick = { onAgentSelected(agent.id.toString()) }) {
                Text(agent.name)
            }
        }
    }
}
