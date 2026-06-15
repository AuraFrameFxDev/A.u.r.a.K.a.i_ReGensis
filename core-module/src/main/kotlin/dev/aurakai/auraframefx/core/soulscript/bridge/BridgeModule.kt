package dev.aurakai.auraframefx.core.soulscript.bridge

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BridgeModule {

    @Provides
    @Singleton
    fun provideNexusMemoryCore(): NexusMemoryCore = NexusMemoryCore

    @Provides
    @Singleton
    fun provideRealityMorphEngine(): RealityMorphStub = RealityMorphStub

    @Provides
    @Singleton
    fun provideKaiSentinelBus(): KaiSentinelBus = KaiSentinelBus

    @Provides
    @Singleton
    fun provideGovernor(): Governor = Governor

    @Provides
    @Singleton
    fun provideTrinityCoordinator(): TrinityCoordinator = TrinityCoordinator

    @Provides
    @Singleton
    fun provideNativeLib(): NativeBridgeStub = NativeBridgeStub
}
