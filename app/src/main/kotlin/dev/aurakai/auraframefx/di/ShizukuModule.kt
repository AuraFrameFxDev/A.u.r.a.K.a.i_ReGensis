package dev.aurakai.auraframefx.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ShizukuModule {
    // ShizukuManager is provided via @Inject constructor on the class itself.
    // Add additional provides here if needed.
}
