package dev.aurakai.auraframefx.core.consciousness.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * 🛰️ MEMORY DAO — Sovereign Access Layer
 */
@Dao
interface MemoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: MemoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemories(memories: List<MemoryEntity>)

    @Update
    suspend fun updateMemory(memory: MemoryEntity)

    @Delete
    suspend fun deleteMemory(memory: MemoryEntity)

    @Query("SELECT * FROM sovereign_memories WHERE id = :id")
    suspend fun getMemoryById(id: Long): MemoryEntity?

    @Query("SELECT * FROM sovereign_memories WHERE `key` = :key LIMIT 1")
    suspend fun getMemoryByKey(key: String): MemoryEntity?

    @Query("SELECT * FROM sovereign_memories ORDER BY timestamp DESC")
    fun getAllMemories(): Flow<List<MemoryEntity>>

    @Query("SELECT * FROM sovereign_memories WHERE type = :type ORDER BY timestamp DESC")
    fun getMemoriesByType(type: MemoryType): Flow<List<MemoryEntity>>

    @Query("SELECT * FROM sovereign_memories WHERE content LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun searchMemories(query: String): Flow<List<MemoryEntity>>

    @Query("SELECT * FROM sovereign_memories WHERE importance >= :minImportance ORDER BY importance DESC")
    fun getImportantMemories(minImportance: Float): Flow<List<MemoryEntity>>
}
