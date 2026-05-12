package dev.aurakai.auraframefx.domains.chromaforge.ui.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.domains.chromaforge.genesis.fusion.FusionBuildEngine
import dev.aurakai.auraframefx.domains.oracledrive.services.AgentWebExplorationService
import kotlinx.coroutines.flow.StateFlow
import dev.aurakai.auraframefx.domains.chromaforge.genesis.fusion.BuildState
import javax.inject.Inject

@HiltViewModel
class ArkBuildViewModel @Inject constructor(
    private val fusionBuildEngine: FusionBuildEngine,
    val webExplorationService: AgentWebExplorationService
) : ViewModel() {

    val buildState: StateFlow<BuildState> = fusionBuildEngine.buildState

    fun initiateBuild() {
        fusionBuildEngine.initiateBuildCycle()
    }

    fun dispatchAgents() {
        fusionBuildEngine.dispatchExplorationTasks()
    }

    // Simulate progress updates for demo purposes
    fun simulateProgress(componentName: String, amount: Float) {
        // Note: Progress is now handled internally by FusionBuildEngine via webExplorationService results
    }
}

