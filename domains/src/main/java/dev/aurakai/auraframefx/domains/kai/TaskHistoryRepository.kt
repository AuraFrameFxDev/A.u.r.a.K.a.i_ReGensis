package dev.aurakai.auraframefx.domains.kai

import dev.aurakai.auraframefx.core.database.dao.TaskHistoryDao
import dev.aurakai.auraframefx.core.database.entity.TaskHistoryEntity
import kotlinx.coroutines.flow.Flow

class TaskHistoryRepository(private val dao: TaskHistoryDao) {
    suspend fun insertTask(task: TaskHistoryEntity) = dao.insertTask(task)
    fun getAllTasks(): Flow<List<TaskHistoryEntity>> = dao.getAllTasks()
}
