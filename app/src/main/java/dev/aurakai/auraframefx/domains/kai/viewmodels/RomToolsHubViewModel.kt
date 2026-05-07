package dev.aurakai.auraframefx.domains.kai.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.domains.ldo.swarm.DeviceOptimisationSwarm
import dev.aurakai.auraframefx.domains.ldo.swarm.SwarmOptimisationState
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RomToolsHubViewModel @Inject constructor(
    private val optimisationSwarm: DeviceOptimisationSwarm
) : ViewModel() {

    val swarmState: StateFlow<SwarmOptimisationState> = optimisationSwarm.state

    fun startDeepOptimisation() {
        optimisationSwarm.initiateFullDeepClean()
    }
}
