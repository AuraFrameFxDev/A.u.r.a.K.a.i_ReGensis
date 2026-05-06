package dev.aurakai.auraframefx.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.core.consciousness.db.ConsciousnessDatabase
import dev.aurakai.auraframefx.core.consciousness.db.MemoryDao
import javax.inject.Singleton

/**
 * 🛰️ CONSCIOUSNESS MODULE — Sovereign DI
 */
@Module
@InstallIn(SingletonComponent::class)
object ConsciousnessModule {

    @Provides
    @Singleton
    fun provideConsciousnessDatabase(
        @ApplicationContext context: Context
    ): ConsciousnessDatabase {
        return Room.databaseBuilder(
            context,
            ConsciousnessDatabase::class.java,
            "sovereign_consciousness.db"
        )
            .fallbackToDestructiveMigration(true)
        .build()
    }

    @Provides
    @Singleton
    fun provideMemoryDao(db: ConsciousnessDatabase): MemoryDao {
        return db.memoryDao()
    }
}
