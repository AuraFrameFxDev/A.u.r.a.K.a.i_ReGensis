package dev.aurakai.auraframefx.domains.cascade.utils.cascade.memory

import kotlinx.coroutines.flow.Flow
import dev.aurakai.auraframefx.domains.cascade.utils.room.AgentMemoryDao
import dev.aurakai.auraframefx.domains.cascade.utils.room.AgentMemoryEntity

class AgentMemoryRepository(private val dao: AgentMemoryDao) {
    suspend fun insertMemory(memory: AgentMemoryEntity): Unit = dao.insertMemory(memory)
    fun getMemoriesForAgent(agentType: String): Flow<List<AgentMemoryEntity>> =
        dao.getMemoriesForAgent(agentType)
}
