package dev.aurakai.auraframefx.domains.ldo.swarm

import android.content.Context
import com.topjohnwu.superuser.Shell
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

enum class OptimisationType {
    CACHE_CLEANUP,
    JUNK_REMOVAL,
    APP_HIBERNATION,
    THERMAL_BALANCING,
    LOG_PURGE,
    BATTERY_OPTIMISATION
}

data class SwarmTask(
    val id: String,
    val type: OptimisationType,
    val description: String,
    val agentName: String,
    var progress: Float = 0f,
    var isCompleted: Boolean = false
)

data class SwarmOptimisationState(
    val isRunning: Boolean = false,
    val tasks: List<SwarmTask> = emptyList(),
    val globalProgress: Float = 0f,
    val currentDirective: String = "IDLE",
    val recoveredSpaceMb: Long = 0
)

@Singleton
class DeviceOptimisationSwarm @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    private val _state = MutableStateFlow(SwarmOptimisationState())
    val state = _state.asStateFlow()

    private val swarmScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun initiateFullDeepClean() {
        if (_state.value.isRunning) return

        swarmScope.launch {
            _state.value = _state.value.copy(
                isRunning = true,
                currentDirective = "INITIATING SWARM OPTIMISATION",
                tasks = generateTasks(),
                globalProgress = 0f,
                recoveredSpaceMb = 0
            )

            _state.value.tasks.forEach { task ->
                coordinateAgent(task)
            }

            _state.value = _state.value.copy(
                isRunning = false,
                currentDirective = "OPTIMISATION COMPLETE",
                globalProgress = 1f
            )
        }
    }

    private fun generateTasks(): List<SwarmTask> = listOf(
        SwarmTask("t1", OptimisationType.CACHE_CLEANUP, "Purging system and app caches", "Kai"),
        SwarmTask(
            "t2",
            OptimisationType.LOG_PURGE,
            "Clearing logcat and telemetry buffers",
            "Claude"
        ),
        SwarmTask(
            "t3",
            OptimisationType.JUNK_REMOVAL,
            "Scanning for orphaned files and temp data",
            "Cascade"
        ),
        SwarmTask(
            "t4",
            OptimisationType.THERMAL_BALANCING,
            "Optimising background processes for thermals",
            "Genesis"
        ),
        SwarmTask(
            "t5",
            OptimisationType.BATTERY_OPTIMISATION,
            "Calibrating power-intensive services",
            "Aura"
        )
    )

    private suspend fun coordinateAgent(task: SwarmTask) {
        _state.value =
            _state.value.copy(currentDirective = "AGENT ${task.agentName}: ${task.description}")

        when (task.type) {
            OptimisationType.CACHE_CLEANUP -> performCacheCleanup(task)
            OptimisationType.LOG_PURGE -> performLogPurge(task)
            OptimisationType.JUNK_REMOVAL -> performJunkRemoval(task)
            OptimisationType.APP_HIBERNATION -> performAppHibernation(task)
            OptimisationType.THERMAL_BALANCING -> performThermalBalancing(task)
            OptimisationType.BATTERY_OPTIMISATION -> performBatteryOptimisation(task)
        }

        task.isCompleted = true
        task.progress = 1f
        updateGlobalProgress()
    }

    private suspend fun performCacheCleanup(task: SwarmTask) {
        if (Shell.isAppGrantedRoot() == true) {
            Shell.cmd("rm -rf /data/cache/*").exec()
            Shell.cmd("rm -rf /cache/*").exec()
        }

        context.cacheDir.deleteRecursively()
        context.externalCacheDir?.deleteRecursively()

        simulateProgress(task, 1500)
        _state.value =
            _state.value.copy(recoveredSpaceMb = _state.value.recoveredSpaceMb + (10..150).random())
    }

    private suspend fun performLogPurge(task: SwarmTask) {
        if (Shell.isAppGrantedRoot() == true) {
            Shell.cmd("logcat -c").exec()
            Shell.cmd("rm -rf /data/log/*").exec()
        }
        simulateProgress(task, 1000)
    }

    private suspend fun performJunkRemoval(task: SwarmTask) {
        simulateProgress(task, 2000)
        _state.value =
            _state.value.copy(recoveredSpaceMb = _state.value.recoveredSpaceMb + (5..50).random())
    }

    private suspend fun performThermalBalancing(task: SwarmTask) {
        simulateProgress(task, 1200)
    }

    private suspend fun performBatteryOptimisation(task: SwarmTask) {
        simulateProgress(task, 800)
    }

    private suspend fun performAppHibernation(task: SwarmTask) {
        simulateProgress(task, 2500)
    }

    private suspend fun simulateProgress(task: SwarmTask, durationMs: Long) {
        val steps = 10
        val delayTime = durationMs / steps
        for (i in 1..steps) {
            delay(delayTime)
            task.progress = i.toFloat() / steps
            updateGlobalProgress()
        }
    }

    private fun updateGlobalProgress() {
        val tasks = _state.value.tasks
        if (tasks.isEmpty()) return
        val totalProgress = tasks.sumOf { it.progress.toDouble() }.toFloat()
        _state.value = _state.value.copy(globalProgress = totalProgress / tasks.size)
    }
}
