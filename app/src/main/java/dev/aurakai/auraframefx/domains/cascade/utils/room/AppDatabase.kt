package dev.aurakai.auraframefx.domains.cascade.utils.room

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.aurakai.auraframefx.grokipedia.GrokipediaDao
import dev.aurakai.auraframefx.grokipedia.GrokipediaEntry


@Database(
    entities = [AgentMemoryEntity::class, TaskHistoryEntity::class, AgentStatsEntity::class, GrokipediaEntry::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun agentMemoryDao(): AgentMemoryDao
    abstract fun taskHistoryDao(): TaskHistoryDao
    abstract fun agentStatsDao(): AgentStatsDao
    abstract fun grokipediaDao(): GrokipediaDao
}
