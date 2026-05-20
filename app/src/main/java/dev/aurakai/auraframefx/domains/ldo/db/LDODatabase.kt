package dev.aurakai.auraframefx.domains.ldo.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        LDOAgentEntity::class,
        LDOTaskEntity::class,
        LDOBondLevelEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LDODatabase : RoomDatabase() {
    abstract fun agentDao(): LDOAgentDao
    abstract fun taskDao(): LDOTaskDao
    abstract fun bondLevelDao(): LDOBondLevelDao
}
