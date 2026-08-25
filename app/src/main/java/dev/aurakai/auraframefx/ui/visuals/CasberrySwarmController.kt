package dev.aurakai.auraframefx.ui.visuals

data class CasberrySwarmController(
    val transitionTo: (SwarmState) -> Unit,
    val currentState: () -> SwarmState
)