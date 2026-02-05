package dev.aurakai.auraframefx.domains.genesis

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.domains.genesis.bridge.BridgeMemorySink
import dev.aurakai.auraframefx.domains.genesis.bridge.GenesisBridge
import dev.aurakai.auraframefx.domains.genesis.bridge.NexusMemoryBridgeSink
import dev.aurakai.auraframefx.domains.genesis.bridge.StdioGenesisBridge
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BridgeModule {

    @Provides
    @Singleton
    fun provideBridgeMemorySink(impl: NexusMemoryBridgeSink): BridgeMemorySink = impl

    @Provides
    @Singleton
    fun provideGenesisBridge(impl: StdioGenesisBridge): GenesisBridge = impl
}
