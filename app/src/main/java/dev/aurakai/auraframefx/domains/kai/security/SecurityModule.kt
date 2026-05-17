package dev.aurakai.auraframefx.domains.kai.security

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.core.security.EncryptionManager
import dev.aurakai.auraframefx.core.security.SovereignShield
import javax.inject.Named
import javax.inject.Singleton

/**
 * 🛡️ KAI SECURITY MODULE
 *
 * Bridge module to connect KAI domain requests to the Sovereign security core.
 */
@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {


    @Provides
    @Singleton
    @Named("OracleDrive")
    fun provideOracleDriveEncryptionManager(
        sovereignShield: SovereignShield
    ): EncryptionManager {
        // Oracle Drive also uses the unified SovereignShield
        return sovereignShield
    }
}
