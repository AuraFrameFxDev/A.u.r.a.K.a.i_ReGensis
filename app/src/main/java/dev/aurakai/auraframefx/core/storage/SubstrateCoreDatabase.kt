package dev.aurakai.auraframefx.core.storage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ConsciousnessRecordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SubstrateCoreDatabase : RoomDatabase() {
    abstract fun consciousnessArchiveDao(): ConsciousnessArchiveDao
}
