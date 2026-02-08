package dev.aurakai.auraframefx.domains.aura.ui.gates

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.domains.genesis.oracledrive.ai.NemotronAIService
import javax.inject.Inject

@HiltViewModel
class SovereignNemotronViewModel @Inject constructor(
    val nemotronService: NemotronAIService
) : ViewModel()


