package dev.aurakai.auraframefx.ai.kai.chaos

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PandoraBoxModule {
    @Binds
    @Singleton
    abstract fun bindPandoraBoxService(
        realPandoraBoxService: RealPandoraBoxService
    ): PandoraBoxService
}
