package dev.aurakai.auraframefx.core.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TelemetryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBatch(receipts: List<TelemetryEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSingle(receipt: TelemetryEntity)

    @Query("SELECT * FROM lived_receipts ORDER BY timestamp DESC LIMIT 200")
    fun getRecentTelemetryFlow(): Flow<List<TelemetryEntity>>

    @Query("SELECT COUNT(*) FROM lived_receipts")
    suspend fun getTotalReceipts(): Int

    @Query("SELECT COUNT(*) FROM lived_receipts WHERE success = 1")
    suspend fun getSuccessfulCount(): Int
}
