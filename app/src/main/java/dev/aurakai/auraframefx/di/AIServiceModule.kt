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
     * Binds the CascadeAIService interface to its DefaultCascadeAIService implementation in the DI graph.
     *
     * @param impl The DefaultCascadeAIService instance to provide when CascadeAIService is requested.
     * @return The CascadeAIService instance backed by the provided implementation.
     */
    @Binds
    @Singleton
    abstract fun bindCascadeAIService(cascadeAIService: RealCascadeAIServiceAdapter): CascadeAIService
    abstract fun bindCascadeAIService(impl: RealCascadeAIServiceAdapter): CascadeAIService

    companion object {
        // ═══════════════════════════════════════════════════════════════════════════
        // NEW External AI Backend Services (Direct Providers)
        // ═══════════════════════════════════════════════════════════════════════════

        /**
         * Exposes the injected ClaudeAIService instance as a singleton DI binding.
         *
         * @param service The ClaudeAIService instance to register.
         * @return The `ClaudeAIService` instance bound in the singleton component.
         */
        @Provides
        @Singleton
        fun provideClaudeAIService(service: ClaudeAIService): ClaudeAIService = service

        /**
         * Exposes the NemotronAIService implementation as a singleton binding.
         *
         * @return The provided NemotronAIService instance.
         */
        @Provides
        @Singleton
        fun provideNemotronAIService(service: NemotronAIService): NemotronAIService = service

        /**
         * Exposes the GeminiAIService implementation as a singleton binding.
         *
         * @return The provided GeminiAIService instance.
         */
        @Provides
        @Singleton
        fun provideGeminiAIService(service: GeminiAIService): GeminiAIService = service

        /**
         * Exposes the injected MetaInstructAIService as a singleton binding.
         *
         * @return The provided MetaInstructAIService instance.
         */
        @Provides
        @Singleton
        fun provideMetaInstructAIService(service: MetaInstructAIService): MetaInstructAIService = service
    }
}
