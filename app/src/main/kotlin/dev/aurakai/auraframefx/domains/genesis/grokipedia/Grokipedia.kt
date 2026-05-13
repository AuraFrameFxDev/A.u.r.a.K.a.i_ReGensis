package dev.aurakai.auraframefx.domains.genesis.grokipedia

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "grokipedia_entries")
data class GrokipediaEntry(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val category: String,
    val timestamp: Long
)

@Dao
interface GrokipediaDao {
    @Query("SELECT * FROM grokipedia_entries")
    suspend fun getAllEntries(): List<GrokipediaEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: GrokipediaEntry)
}
