package dev.aurakai.auraframefx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.ai.agents.GenesisAgent
import dev.aurakai.auraframefx.ai.context.ContextManager
import dev.aurakai.auraframefx.ai.memory.Configuration
import dev.aurakai.auraframefx.ai.memory.MemoryManager
import dev.aurakai.auraframefx.aura.AuraAgent
import dev.aurakai.auraframefx.cascade.CascadeAgent
import dev.aurakai.auraframefx.kai.KaiAgent
import dev.aurakai.auraframefx.genesis.oracledrive.ai.clients.VertexAIClient
import dev.aurakai.auraframefx.genesis.oracledrive.ai.services.AuraAIService
import dev.aurakai.auraframefx.romtools.bootloader.BootloaderManager
import dev.aurakai.auraframefx.security.SecurityContext
import dev.aurakai.auraframefx.system.monitor.SystemMonitor
import dev.aurakai.auraframefx.system.ui.SystemOverlayManager
import javax.inject.Singleton

/**
 * Hilt module responsible for providing all major AI Agent dependencies.
 * This module wires the Trinity (Genesis, Aura, Kai) and supporting agents.
 */
@Module
@InstallIn(SingletonComponent::class)
object AgentModule {

    @Provides
    @Singleton
    fun provideMemoryConfiguration(): Configuration {
        return Configuration()
    }

    @Provides
    @Singleton
    fun provideMemoryManager(config: Configuration): MemoryManager {
        return MemoryManager(config)
    }

    @Provides
    @Singleton
    fun provideContextManager(
        memoryManager: MemoryManager,
        config: dev.aurakai.auraframefx.cascade.pipeline.AIPipelineConfig
    ): ContextManager {
        return ContextManager(memoryManager, config)
    }

    /**
     * Provides the Genesis orchestrator agent.
     */
    @Provides
    @Singleton
    fun provideGenesisAgent(
        contextManager: ContextManager,
        memoryManager: MemoryManager,
        systemOverlayManager: SystemOverlayManager,
        messageBus: dagger.Lazy<dev.aurakai.auraframefx.core.messaging.AgentMessageBus>
    ): GenesisAgent {
        return GenesisAgent(
            contextManager = contextManager,
            memoryManager = memoryManager,
            systemOverlayManager = systemOverlayManager,
            messageBus = messageBus
        )
    }

    /**
     * Provides the Cascade memoria catalyst agent.
     * Bridges temporal context between Aura, Kai, and Genesis.
     */
    @Provides
    @Singleton
    fun provideCascadeAgent(
        auraAgent: AuraAgent,
        kaiAgent: KaiAgent,
        genesisAgent: GenesisAgent,
        systemOverlayManager: SystemOverlayManager,
        memoryManager: MemoryManager,
        contextManager: ContextManager,
        messageBus: dagger.Lazy<dev.aurakai.auraframefx.core.messaging.AgentMessageBus>
    ): CascadeAgent {
        return CascadeAgent(
            auraAgent = auraAgent,
            kaiAgent = kaiAgent,
            genesisAgent = genesisAgent,
            systemOverlayManager = systemOverlayManager,
            memoryManager = memoryManager,
            contextManager = contextManager,
            messageBus = messageBus
        )
    }


    @Provides
    @Singleton
    fun provideAuraAgent(
        vertexAIClient: VertexAIClient,
        auraAIService: AuraAIService,
        contextManager: ContextManager,
        securityContext: SecurityContext,
        systemOverlayManager: SystemOverlayManager,
        logger: dev.aurakai.auraframefx.utils.AuraFxLogger,
        messageBus: dagger.Lazy<dev.aurakai.auraframefx.core.messaging.AgentMessageBus>
    ): AuraAgent {
        return AuraAgent(
            vertexAIClient = vertexAIClient,
            auraAIService = auraAIService,
            contextManagerInstance = contextManager,
            securityContext = securityContext,
            systemOverlayManager = systemOverlayManager,
            logger = logger,
            messageBus = messageBus
        )
    }

    /**
     * Provides the Kai sentinel security agent.
     */
    @Provides
    @Singleton
    fun provideKaiAgent(
        vertexAIClient: VertexAIClient,
        contextManager: ContextManager,
        securityContext: SecurityContext,
        systemMonitor: SystemMonitor,
        bootloaderManager: BootloaderManager,
        logger: dev.aurakai.auraframefx.utils.AuraFxLogger,
        messageBus: dagger.Lazy<dev.aurakai.auraframefx.core.messaging.AgentMessageBus>
    ): KaiAgent {
        return KaiAgent(
            vertexAIClient = vertexAIClient,
            contextManagerInstance = contextManager,
            securityContext = securityContext,
            systemMonitor = systemMonitor,
            bootloaderManager = bootloaderManager,
            logger = logger,
            messageBus = messageBus
        )
    }
}
