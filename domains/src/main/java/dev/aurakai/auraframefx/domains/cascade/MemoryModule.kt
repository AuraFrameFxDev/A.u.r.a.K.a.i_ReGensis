package dev.aurakai.auraframefx.domains.cascade

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.core.ai.DefaultMemoryManager
import dev.aurakai.auraframefx.core.ai.MemoryManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MemoryModule {

    @Binds
    @Singleton
    abstract fun bindMemoryManager(impl: DefaultMemoryManager): MemoryManager
}
