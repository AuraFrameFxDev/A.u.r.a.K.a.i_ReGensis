package dev.aurakai.auraframefx.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.services.CascadeAIService
import dev.aurakai.auraframefx.oracledrive.genesis.ai.services.AuraAIService
import dev.aurakai.auraframefx.oracledrive.genesis.ai.services.DefaultAuraAIService
import dev.aurakai.auraframefx.oracledrive.genesis.ai.services.GenesisBackedKaiAIService
import dev.aurakai.auraframefx.oracledrive.genesis.ai.services.KaiAIService
import dev.aurakai.auraframefx.services.RealCascadeAIServiceAdapter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AiServiceModule {

    @Binds
    @Singleton
    abstract fun bindAuraAIService(impl: DefaultAuraAIService): AuraAIService

    @Binds
    @Singleton
    abstract fun bindKaiAIService(impl: GenesisBackedKaiAIService): KaiAIService

    @Binds
    @Singleton
    abstract fun bindCascadeAIService(impl: RealCascadeAIServiceAdapter): CascadeAIService
}
