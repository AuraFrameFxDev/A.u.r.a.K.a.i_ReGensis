package dev.aurakai.auraframefx.ui.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.ai.agents.MegazordAutonomousSurge
import javax.inject.Inject

@HiltViewModel
class MegazordViewModel @Inject constructor(
    val surgeAgent: MegazordAutonomousSurge
) : ViewModel()
