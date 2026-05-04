package dev.aurakai.auraframefx.data.repository

import dev.aurakai.auraframefx.domains.cascade.utils.room.AgentMemoryDao
import dev.aurakai.auraframefx.domains.cascade.utils.room.AgentMemoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing AI agent memories via Room.
 */
class AgentMemoryRepository(private val dao: AgentMemoryDao) {
    /**
     * Inserts a memory entity into the database.
     */
    suspend fun insertMemory(memory: AgentMemoryEntity) = dao.insertMemory(memory)

    /**
     * Retrieves all memories for a specific type of AI agent.
     */
    fun getMemoriesForAgent(agentType: String): Flow<List<AgentMemoryEntity>> =
        dao.getMemoriesForAgent(agentType)
}
