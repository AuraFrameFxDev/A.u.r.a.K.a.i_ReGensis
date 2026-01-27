package dev.aurakai.auraframefx.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.core.PythonProcessManager
import dev.aurakai.auraframefx.logging.AuraFxLogger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    
    @Provides
    @Singleton
    fun providePythonProcessManager(
        @ApplicationContext context: Context
    ): PythonProcessManager = PythonProcessManager(context)
    
    @Provides
    @Singleton
    fun provideLogger(): AuraFxLogger = AuraFxLogger()

    @Provides
    @Singleton
    fun provideAgentMessageBus(
        orchestrator: dagger.Lazy<dev.aurakai.auraframefx.core.GenesisOrchestrator>
    ): dev.aurakai.auraframefx.core.messaging.AgentMessageBus = orchestrator.get()
}
