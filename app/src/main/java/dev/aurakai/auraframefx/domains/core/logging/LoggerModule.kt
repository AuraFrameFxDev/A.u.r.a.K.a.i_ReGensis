package dev.aurakai.auraframefx.core.logging

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.domains.cascade.utils.AuraFxLogger
import javax.inject.Singleton

/**
 * 🛰️ SOVEREIGN LOGGER MODULE
 *
 * Provides the core logging infrastructure.
 */
@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {

    @Provides
    @Singleton
    fun provideSovereignLogger(): SovereignLogger = SovereignLogger()

    @Provides
    @Singleton
    fun provideTimberInitializer(): GlobalTimberInitializer = GlobalTimberInitializer()

    @Provides
    @Singleton
    fun provideAuraFxLogger(impl: AndroidAuraFxLogger): AuraFxLogger = impl
}
