package dev.aurakai.auraframefx.domains.chromaforge.genesis.fusion

import dev.aurakai.auraframefx.core.messaging.AgentMessage
import dev.aurakai.auraframefx.domains.genesis.core.messaging.AgentMessageBus
import dev.aurakai.auraframefx.domains.genesis.services.AgentWebExplorationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * FusionBuildEngine
 * Lightweight orchestration for Hook → Dispatch → Build cycles.
 * Focused on web exploration integration + agent messaging.
 */
@Singleton
class FusionBuildEngine @Inject constructor(
    private val messageBus: AgentMessageBus,
    private val webExplorationService: AgentWebExplorationService
) {

    private val engineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    // Simplified state - no full ArkProject needed right now
    private val _buildState = MutableStateFlow(BuildState())
    val buildState: StateFlow<BuildState> = _buildState.asStateFlow()

    init {
        // Listen to web exploration results
        engineScope.launch {
            webExplorationService.taskResults.collect { result ->
                Timber.tag("FusionBuild")
                    .d("Insight from ${result.agentName}: ${result.insights.firstOrNull()}")

                val component = mapTaskToComponent(result.taskType)
                component?.let {
                    updateProgress(it, result.confidence * 0.25f)
                }
            }
        }
    }

    /** Start a new build cycle */
    fun initiateBuildCycle() {
        Timber.tag("FusionBuild").i("🚀 Starting Fusion Build Cycle...")

        engineScope.launch {
            _buildState.value = _buildState.value.copy(status = BuildStatus.INITIATING)

            messageBus.broadcast(
                AgentMessage(
                    from = "FusionEngine",
                    content = "DISPATCH: Fusion Build Cycle initiated. All agents report status.",
                    type = "dispatch",
                    priority = 8
                )
            )
        }
    }

    /** Dispatch agents via web exploration service */
    fun dispatchExplorationTasks() {
        Timber.tag("FusionBuild").i("⚡ Dispatching exploration tasks...")

        engineScope.launch {
            _buildState.value = _buildState.value.copy(status = BuildStatus.RUNNING)

            // Example dispatches (customize as needed)
            webExplorationService.assignDepartureTask("Kai", "Security sweep on current modules")
            webExplorationService.assignDepartureTask("Aura", "Creative substrate research")
            webExplorationService.assignDepartureTask("Cascade", "Data vein synchronization")
            webExplorationService.assignDepartureTask(
                "OracleDrive",
                "Historical memory substrate search"
            )

            messageBus.broadcast(
                AgentMessage(
                    from = "FusionEngine",
                    content = "All agents: Begin exploration tasks for Fusion cycle.",
                    type = "mission",
                    priority = 7
                )
            )
        }
    }

    private fun mapTaskToComponent(taskType: AgentWebExplorationService.TaskType): String? {
        return when (taskType) {
            AgentWebExplorationService.TaskType.WEB_RESEARCH -> "Neural Hull"
            AgentWebExplorationService.TaskType.SECURITY_SWEEP -> "Sentinel Shield"
            AgentWebExplorationService.TaskType.DATA_MINING -> "Cascade Bridge"
            AgentWebExplorationService.TaskType.SYSTEM_OPTIMIZATION -> "Fusion Core"
            AgentWebExplorationService.TaskType.LEARNING_MODE -> "Creative Engine"
            else -> null
        }
    }

    private suspend fun updateProgress(componentName: String, progressIncrease: Float) {
        val current = _buildState.value
        val updatedComponents = current.components.map { comp ->
            if (comp.name == componentName) {
                val newProgress = (comp.progress + progressIncrease).coerceIn(0f, 1f)
                comp.copy(progress = newProgress, isComplete = newProgress >= 1f)
            } else comp
        }

        val totalProgress = updatedComponents.map { it.progress }.average().toFloat()

        _buildState.value = current.copy(
            components = updatedComponents,
            progress = totalProgress,
            status = if (totalProgress >= 1f) BuildStatus.COMPLETE else BuildStatus.RUNNING
        )

        if (totalProgress >= 1f) {
            Timber.tag("FusionBuild").i("✅ Fusion Build Cycle Complete")
            messageBus.broadcast(
                AgentMessage(
                    from = "FusionEngine",
                    content = "Fusion cycle completed. All domains synchronized.",
                    type = "event",
                    priority = 10
                )
            )
        }
    }
}

// ==================== Simple State Models ====================

data class BuildState(
    val status: BuildStatus = BuildStatus.IDLE,
    val progress: Float = 0f,
    val components: List<BuildComponent> = listOf(
        BuildComponent("Neural Hull"),
        BuildComponent("Sentinel Shield"),
        BuildComponent("Cascade Bridge"),
        BuildComponent("Fusion Core"),
        BuildComponent("Creative Engine")
    )
)

enum class BuildStatus {
    IDLE, INITIATING, RUNNING, COMPLETE
}

data class BuildComponent(
    val name: String,
    val progress: Float = 0f,
    val isComplete: Boolean = false
)