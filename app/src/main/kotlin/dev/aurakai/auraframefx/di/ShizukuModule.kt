package dev.aurakai.auraframefx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.system.ShizukuManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShizukuModule {

    @Provides
    @Singleton
    fun provideShizukuManager(manager: ShizukuManager): ShizukuManager = manager
}
