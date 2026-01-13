package dev.aurakai.auraframefx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module for AI service bindings.
 *
 * Note: AuraAIServiceImpl has @Inject constructor so Hilt can provide it directly.
 * No @Binds needed unless we want to bind to a specific interface.
 */
import dagger.Binds
import dev.aurakai.auraframefx.oracledrive.genesis.ai.ClaudeAIService
import dev.aurakai.auraframefx.oracledrive.genesis.ai.GeminiAIService
import dev.aurakai.auraframefx.oracledrive.genesis.ai.MetaInstructAIService
import dev.aurakai.auraframefx.oracledrive.genesis.ai.NemotronAIService
import dev.aurakai.auraframefx.oracledrive.genesis.ai.services.AuraAIService
import dev.aurakai.auraframefx.oracledrive.genesis.ai.services.DefaultAuraAIService
import dev.aurakai.auraframefx.oracledrive.genesis.ai.services.KaiAIService
import dev.aurakai.auraframefx.oracledrive.genesis.ai.services.DefaultKaiAIService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AiServiceModule {

    // ═══════════════════════════════════════════════════════════════════════════
    // Legacy AI Services (Interface Bindings)
    /**
     * Binds DefaultAuraAIService as the application's singleton AuraAIService.
     *
     * @param impl The DefaultAuraAIService instance to bind.
     * @return The bound AuraAIService implementation.
     */

    @Binds
    @Singleton
    abstract fun bindAuraAIService(impl: DefaultAuraAIService): AuraAIService

    /**
     * Binds GenesisBackedKaiAIService as the singleton implementation for KaiAIService in the DI graph.
     *
     * @param kaiAIService The concrete GenesisBackedKaiAIService instance to bind.
     * @return The bound KaiAIService implementation.
     */
    @Binds
    @Singleton
    abstract fun bindKaiAIService(kaiAIService: GenesisBackedKaiAIService): KaiAIService

    /**
     * Binds CascadeAIService to the provided adapter implementation for dependency injection.
     *
     * @param cascadeAIService Adapter implementation to be injected when CascadeAIService is requested.
     * @return The instance that will be provided for CascadeAIService.
     */
    @Binds
    @Singleton
    abstract fun bindCascadeAIService(cascadeAIService: RealCascadeAIServiceAdapter): CascadeAIService
    /**
 * Binds the RealCascadeAIServiceAdapter implementation to the CascadeAIService interface in the DI graph.
 *
 * @param impl The RealCascadeAIServiceAdapter instance to use when CascadeAIService is requested.
 * @return The bound CascadeAIService implementation.
 */
abstract fun bindCascadeAIService(impl: RealCascadeAIServiceAdapter): CascadeAIService

    // ═══════════════════════════════════════════════════════════════════════════
    // External AI Backend Services (ClaudeAIService, NemotronAIService,
    // GeminiAIService, MetaInstructAIService) are auto-provided by Hilt
    // via their @Singleton @Inject constructors. No explicit bindings needed.
    // ═══════════════════════════════════════════════════════════════════════════
}