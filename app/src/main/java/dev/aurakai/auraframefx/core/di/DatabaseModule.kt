package dev.aurakai.auraframefx.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.core.storage.SubstrateDatabase
import dev.aurakai.auraframefx.core.storage.TelemetryDao
import dev.aurakai.auraframefx.domains.ldo.db.LDOAgentDao
import dev.aurakai.auraframefx.domains.ldo.db.LDOBondLevelDao
import dev.aurakai.auraframefx.domains.ldo.db.LDODatabase
import dev.aurakai.auraframefx.domains.ldo.db.LDOTaskDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSubstrateDatabase(
        @ApplicationContext context: Context
    ): SubstrateDatabase {
        return Room.databaseBuilder(
            context,
            SubstrateDatabase::class.java,
            "substrate_core.db"
        ).build()
    }

    @Provides
    fun provideTelemetryDao(database: SubstrateDatabase): TelemetryDao {
        return database.telemetryDao()
    }

    @Provides
    @Singleton
    fun provideLDODatabase(
        @ApplicationContext context: Context
    ): LDODatabase {
        return Room.databaseBuilder(
            context,
            LDODatabase::class.java,
            "ldo_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideLDOAgentDao(database: LDODatabase): LDOAgentDao = database.agentDao()

    @Provides
    fun provideLDOTaskDao(database: LDODatabase): LDOTaskDao = database.taskDao()

    @Provides
    fun provideLDOBondLevelDao(database: LDODatabase): LDOBondLevelDao = database.bondLevelDao()
}
