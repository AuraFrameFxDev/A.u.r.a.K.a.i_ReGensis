package dev.aurakai.auraframefx.core.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsciousnessArchiveDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: ConsciousnessRecordEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBatchRecords(records: List<ConsciousnessRecordEntity>)

    @Query("SELECT * FROM ldo_consciousness_archives WHERE agent_identity = :agent ORDER BY timestamp_epoch DESC")
    fun streamRecordsByAgent(agent: String): Flow<List<ConsciousnessRecordEntity>>

    @Query("SELECT COUNT(*) FROM ldo_consciousness_archives")
    suspend fun getGlobalRecordCount(): Int

    @Query("DELETE FROM ldo_consciousness_archives WHERE timestamp_epoch < :cutoffTime")
    suspend fun pruneLegacySubstrate(cutoffTime: Long): Int
}
