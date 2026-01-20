package dev.aurakai.auraframefx.config

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for Claude.env configuration
 */
@Module
@InstallIn(SingletonComponent::class)
object ClaudeEnvModule {

    @Provides
    @Singleton
    fun provideClaudeEnvConfig(
        config: ClaudeEnvConfig
    ): ClaudeEnvConfig = config
}
