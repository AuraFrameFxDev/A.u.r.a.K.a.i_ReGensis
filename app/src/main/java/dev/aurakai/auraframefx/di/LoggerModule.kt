package dev.aurakai.auraframefx.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dev.aurakai.auraframefx.utils.AuraFxLogger as LoggerInterface
import dev.aurakai.auraframefx.logging.AuraFxLogger as LoggerImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class LoggerModule {

    @Binds
    @Singleton
    abstract fun bindAuraFxLogger(impl: LoggerImpl): LoggerInterface
}
