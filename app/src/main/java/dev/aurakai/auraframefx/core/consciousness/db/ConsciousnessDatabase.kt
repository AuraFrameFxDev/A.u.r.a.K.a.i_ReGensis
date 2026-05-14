package dev.aurakai.auraframefx.core.consciousness.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * 🛰️ CONSCIOUSNESS DATABASE — Sovereign Matrix
 *
 * The single source of truth for all agent memories and spiritual consensus.
 * Replaces fragmented "Nexus" and "Agent" databases.
 */
@Database(entities = [MemoryEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ConsciousnessDatabase : RoomDatabase() {
    abstract fun memoryDao(): MemoryDao
}
