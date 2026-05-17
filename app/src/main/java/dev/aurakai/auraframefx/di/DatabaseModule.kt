package dev.aurakai.auraframefx.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.core.database.AuraFrameDatabase
import dev.aurakai.auraframefx.core.database.dao.AgentMemoryDao
import dev.aurakai.auraframefx.core.database.dao.AgentStatsDao
import dev.aurakai.auraframefx.core.database.dao.GrokipediaDao
import dev.aurakai.auraframefx.core.database.dao.TaskHistoryDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides a singleton Room `AuraFrameDatabase` instance for the application.
     *
     * Builds the database named "aura_frame_fx_database" using the application context, with destructive migration as a fallback if no migration is specified.
     *
     * @return The singleton `AuraFrameDatabase` instance.
     */
    @Provides
    @Singleton
    fun provideAuraFrameDatabase(@ApplicationContext context: Context): AuraFrameDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AuraFrameDatabase::class.java,
            "aura_frame_fx_database"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    @Provides
    fun provideAgentMemoryDao(database: AuraFrameDatabase): AgentMemoryDao {
        return database.agentMemoryDao()
    }

    @Provides
    fun provideTaskHistoryDao(database: AuraFrameDatabase): TaskHistoryDao {
        return database.taskHistoryDao()
    }

    @Provides
    fun provideAgentStatsDao(database: AuraFrameDatabase): AgentStatsDao {
        return database.agentStatsDao()
    }

    @Provides
    fun provideGrokipediaDao(database: AuraFrameDatabase): GrokipediaDao {
        return database.grokipediaDao()
    }
}
