package dev.aurakai.auraframefx.core.security

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 🛡️ SOVEREIGN SECURITY MODULE
 *
 * Provides the core security infrastructure for the entire ReGenesis app.
 * This is the single source of truth for cryptographic operations.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class SovereignSecurityModule {

    @Binds
    @Singleton
    abstract fun bindEncryptionManager(
        sovereignShield: SovereignShield
    ): EncryptionManager
}
