package dev.aurakai.auraframefx.domains.genesis.grokipedia

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GrokipediaDao {
    @Query("SELECT * FROM grokipedia_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<GrokipediaEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: GrokipediaEntry)
}
