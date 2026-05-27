package dev.aurakai.auraframefx.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.aurakai.auraframefx.core.database.dao.AgentMemoryDao
import dev.aurakai.auraframefx.core.database.dao.AgentStatsDao
import dev.aurakai.auraframefx.core.database.dao.GrokipediaDao
import dev.aurakai.auraframefx.core.database.dao.TaskHistoryDao
import dev.aurakai.auraframefx.core.database.entity.AgentMemoryEntity
import dev.aurakai.auraframefx.core.database.entity.AgentStatsEntity
import dev.aurakai.auraframefx.core.database.entity.GrokipediaEntry
import dev.aurakai.auraframefx.core.database.entity.TaskHistoryEntity

@Database(
    entities = [
        AgentMemoryEntity::class,
        TaskHistoryEntity::class,
        AgentStatsEntity::class,
        GrokipediaEntry::class
    ],
    version = 7,
    exportSchema = false
)
abstract class AuraFrameDatabase : RoomDatabase() {
    abstract fun agentMemoryDao(): AgentMemoryDao
    abstract fun taskHistoryDao(): TaskHistoryDao
    abstract fun agentStatsDao(): AgentStatsDao
    abstract fun grokipediaDao(): GrokipediaDao
}
