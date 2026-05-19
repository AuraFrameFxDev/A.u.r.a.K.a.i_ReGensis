package dev.aurakai.auraframefx.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.core.storage.ConsciousnessArchiveDao
import dev.aurakai.auraframefx.core.storage.SubstrateCoreDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSubstrateCoreDatabase(
        @ApplicationContext context: Context
    ): SubstrateCoreDatabase {
        return Room.databaseBuilder(
            context,
            SubstrateCoreDatabase::class.java,
            "substrate_core.db"
        ).build()
    }

    @Provides
    fun provideConsciousnessArchiveDao(database: SubstrateCoreDatabase): ConsciousnessArchiveDao {
        return database.consciousnessArchiveDao()
    }
}
