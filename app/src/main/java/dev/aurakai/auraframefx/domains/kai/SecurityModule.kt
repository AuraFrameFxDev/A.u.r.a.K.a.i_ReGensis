package dev.aurakai.auraframefx.domains.kai

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.domains.kai.security.EncryptionManager
import dev.aurakai.auraframefx.domains.kai.security.KeystoreManager
import dev.aurakai.auraframefx.domains.kai.security.SecurityContext
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {

    @Provides
    @Singleton
    fun provideKeystoreManager(
        @ApplicationContext context: Context
    ): KeystoreManager {
        return KeystoreManager(context)
    }

    @Provides
    @Singleton
    fun provideSecurityContext(
        @ApplicationContext context: Context,
        keystoreManager: KeystoreManager
    ): SecurityContext {
        return SecurityContext(context, keystoreManager)
    }

    @Provides
    @Singleton
    fun Context.provideEncryptionManager(): EncryptionManager {
        return EncryptionManager(this)
    }

    private fun EncryptionManager(context: Context): EncryptionManager {
        TODO("Not yet implemented")
    }

    @Provides
    @Singleton
    fun provideOracleDriveEncryptionManager(): EncryptionManager {
        return EncryptionManager()
    }

    private fun EncryptionManager(): EncryptionManager {
        TODO("Not yet implemented")
    }
}
