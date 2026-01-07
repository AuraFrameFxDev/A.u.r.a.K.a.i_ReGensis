package dev.aurakai.auraframefx.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.core.PythonProcessManager as CorePythonProcessManager
import dev.aurakai.auraframefx.python.PythonProcessManager
import dev.aurakai.auraframefx.logging.AuraFxLogger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    
    @Provides
    @Singleton
    fun provideCorePythonProcessManager(
        @ApplicationContext context: Context
    ): CorePythonProcessManager = CorePythonProcessManager(context)
    
    @Provides
    @Singleton
    fun providePythonProcessManager(
        @ApplicationContext context: Context,
        coreManager: CorePythonProcessManager
    ): PythonProcessManager = PythonProcessManager(context, coreManager)
    
    @Provides
    @Singleton
    fun provideLogger(): AuraFxLogger = AuraFxLogger()
}
