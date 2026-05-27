package dev.aurakai.auraframefx.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    // DataStoreManager is provided via @Inject constructor on the class itself.

}
