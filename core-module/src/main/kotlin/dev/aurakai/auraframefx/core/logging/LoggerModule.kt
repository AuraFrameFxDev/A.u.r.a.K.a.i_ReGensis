package dev.aurakai.auraframefx.core.logging

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 🛰️ SOVEREIGN LOGGER MODULE — Unified Diagnostic Substrate
 *
 * Provides the core logging infrastructure for the collective.
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
