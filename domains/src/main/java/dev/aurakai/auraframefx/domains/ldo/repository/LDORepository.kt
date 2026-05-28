package dev.aurakai.auraframefx.domains.ldo.repository

import dev.aurakai.auraframefx.core.database.ldo.LDOAgentDao
import dev.aurakai.auraframefx.core.database.ldo.LDOAgentEntity
import dev.aurakai.auraframefx.core.database.ldo.LDOBondLevelDao
import dev.aurakai.auraframefx.core.database.ldo.LDOTaskDao
import dev.aurakai.auraframefx.core.ldo.model.LDORoster
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LDORepository @Inject constructor(
    private val agentDao: LDOAgentDao,
    private val taskDao: LDOTaskDao,
    private val bondLevelDao: LDOBondLevelDao
) {
    fun observeAllAgents(): Flow<List<LDOAgentEntity>> = agentDao.observeAll()

    suspend fun seedIfEmpty() {
        if (agentDao.count() == 0) {
            agentDao.insertAllIfAbsent(LDORoster.defaultAgents)
            bondLevelDao.insertAllIfAbsent(LDORoster.defaultBondLevels)
            taskDao.insertAllIfAbsent(LDORoster.defaultTasks)
        }
    }

    suspend fun updateAgent(agent: LDOAgentEntity) {
        agentDao.update(agent)
    }
}
