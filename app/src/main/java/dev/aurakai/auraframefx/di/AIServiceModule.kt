package dev.aurakai.auraframefx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module for AI service bindings.
 *
 * Provides ALL Genesis AI Services:
 * - Legacy services (Aura, Kai, Cascade)
 * - NEW external AI backends (Claude, Nemotron, Gemini, MetaInstruct)
 *
 * All services are @Singleton with @Inject constructors, so Hilt auto-provides them.
 * This module explicitly declares them for clarity and future interface bindings.
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
     * Binds DefaultAuraAIService as the singleton implementation for AuraAIService.
     *
     * @param impl The DefaultAuraAIService instance to bind.
     * @return The bound AuraAIService implementation.
     */

    @Binds
    @Singleton
    abstract fun bindAuraAIService(impl: DefaultAuraAIService): AuraAIService

    @Binds
    @Singleton
    abstract fun bindKaiAIService(kaiAIService: GenesisBackedKaiAIService): KaiAIService

    /**
     * Binds the CascadeAIService interface to its DefaultCascadeAIService implementation in the DI graph.
     *
     * @param impl The DefaultCascadeAIService instance to provide when CascadeAIService is requested.
     * @return The CascadeAIService instance backed by the provided implementation.
     */
    @Binds
    @Singleton
    abstract fun bindCascadeAIService(cascadeAIService: RealCascadeAIServiceAdapter): CascadeAIService
    abstract fun bindCascadeAIService(impl: RealCascadeAIServiceAdapter): CascadeAIService

    // ═══════════════════════════════════════════════════════════════════════════
    // External AI Backend Services (ClaudeAIService, NemotronAIService,
    // GeminiAIService, MetaInstructAIService) are auto-provided by Hilt
    // via their @Singleton @Inject constructors. No explicit bindings needed.
    // ═══════════════════════════════════════════════════════════════════════════
}
