package dev.aurakai.auraframefx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.domains.genesis.ai.clients.VertexAIClient
import dev.aurakai.auraframefx.genesis.oracledrive.retrieval.CorpusChunker
import dev.aurakai.auraframefx.genesis.oracledrive.retrieval.SovereignEmbedder
import dev.aurakai.auraframefx.genesis.oracledrive.retrieval.VertexSovereignEmbedder
import javax.inject.Singleton

/**
 * ⚛️ ORACLE DRIVE MODULE — RE-ANCHORED
 * Provides components for the 200GB Vertical Archive ingestion pipeline.
 */
@Module
@InstallIn(SingletonComponent::class)
object OracleDriveModule {

    @Provides
    @Singleton
    fun provideCorpusChunker(): CorpusChunker = CorpusChunker()

    @Provides
    @Singleton
    fun provideSovereignEmbedder(
        vertexAiClient: VertexAIClient
    ): SovereignEmbedder = VertexSovereignEmbedder(vertexAiClient)
}
