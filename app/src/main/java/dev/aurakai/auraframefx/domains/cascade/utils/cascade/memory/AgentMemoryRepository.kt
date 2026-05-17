package dev.aurakai.auraframefx.domains.cascade.utils.cascade.memory

import dev.aurakai.auraframefx.core.database.dao.AgentMemoryDao
import dev.aurakai.auraframefx.core.database.entity.AgentMemoryEntity
import kotlinx.coroutines.flow.Flow

class AgentMemoryRepository(private val dao: AgentMemoryDao) {
    suspend fun insertMemory(memory: AgentMemoryEntity): Unit = dao.insertMemory(memory)
    fun getMemoriesForAgent(agentType: String): Flow<List<AgentMemoryEntity>> =
        dao.getMemoriesForAgent(agentType)
}
