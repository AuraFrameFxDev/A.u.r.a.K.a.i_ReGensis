package dev.aurakai.auraframefx.core.logging

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 🛰️ SOVEREIGN LOGGING MODULE — Base Substrate
 *
 * Provides the foundational logging infrastructure.
 */
@Module
@InstallIn(SingletonComponent::class)
object SovereignLoggingModule {

    @Provides
    @Singleton
    fun provideSovereignLogger(): SovereignLogger = SovereignLogger()

    @Provides
    @Singleton
    fun provideTimberInitializer(): GlobalTimberInitializer = GlobalTimberInitializer()
}
