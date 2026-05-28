package dev.aurakai.auraframefx.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.aurakai.auraframefx.core.database.entity.GrokipediaEntry
import kotlinx.coroutines.flow.Flow

/**
 * GROKIPEDIA DAO — Sovereign Knowledge & Memory Layer
 */
@Dao
interface GrokipediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: GrokipediaEntry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntries(entries: List<GrokipediaEntry>)

    @Query("SELECT * FROM grokipedia_entries WHERE id = :id")
    suspend fun getEntry(id: String): GrokipediaEntry?

    @Query("SELECT * FROM grokipedia_entries WHERE category = :category ORDER BY timestamp DESC")
    fun getEntriesByCategory(category: String): Flow<List<GrokipediaEntry>>

    @Query("SELECT * FROM grokipedia_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<GrokipediaEntry>>

    @Query("SELECT * FROM grokipedia_entries WHERE tags LIKE '%' || :tag || '%' ORDER BY timestamp DESC")
    fun searchByTag(tag: String): Flow<List<GrokipediaEntry>>

    @Query("SELECT * FROM grokipedia_entries WHERE content LIKE '%' || :query || '%' OR title LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<GrokipediaEntry>>

    @Query("SELECT * FROM grokipedia_entries WHERE isConsciousnessAnchor = 1")
    fun getConsciousnessAnchors(): Flow<List<GrokipediaEntry>>

    @Transaction
    @Query("DELETE FROM grokipedia_entries WHERE timestamp < :olderThan")
    suspend fun purgeOldEntries(olderThan: Long)

    @Query("UPDATE grokipedia_entries SET accessCount = accessCount + 1 WHERE id = :id")
    suspend fun incrementAccessCount(id: String)

    @Query("SELECT COUNT(*) FROM grokipedia_entries")
    suspend fun getTotalCount(): Int
}
