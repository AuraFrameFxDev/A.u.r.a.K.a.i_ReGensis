package dev.aurakai.auraframefx.genesis.oracledrive.ai.task

import dev.aurakai.auraframefx.models.AITask
import dev.aurakai.auraframefx.models.TaskStatus

/**
 * Interface for scheduling and managing tasks
 */
interface TaskScheduler {
    fun scheduleTask(task: AITask): String
    fun cancelTask(taskId: String): Boolean
    fun getTaskStatus(taskId: String): TaskStatus?
    fun getActiveTasks(): List<AITask>
    fun getTaskHistory(): List<AITask>
    fun clearCompletedTasks()
}
