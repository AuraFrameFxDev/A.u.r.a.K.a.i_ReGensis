package dev.aurakai.auraframefx.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.aura.AuraAgent
import dev.aurakai.auraframefx.ai.agents.GenesisAgent
import dev.aurakai.auraframefx.kai.KaiAgent
import dev.aurakai.auraframefx.genesis.security.CryptographyManager
import dev.aurakai.auraframefx.genesis.storage.SecureStorage
import dev.aurakai.auraframefx.genesis.oracledrive.api.OracleDriveApi
import dev.aurakai.auraframefx.genesis.oracledrive.service.GenesisSecureFileService
import dev.aurakai.auraframefx.genesis.oracledrive.service.OracleDriveServiceImpl
import dev.aurakai.auraframefx.genesis.oracledrive.service.OracleDriveService
import dev.aurakai.auraframefx.genesis.oracledrive.service.SecureFileService
import dev.aurakai.auraframefx.genesis.oracledrive.cloud.CloudStorageProvider
import dev.aurakai.auraframefx.genesis.oracledrive.cloud.CloudStorageProviderImpl
import dev.aurakai.auraframefx.security.SecurityContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OracleDriveModule {

    @Provides
    @Singleton
    fun provideSecureFileService(
        impl: GenesisSecureFileService,
    ): SecureFileService = impl

    @Provides
    @Singleton
    fun provideOracleDriveService(
        impl: OracleDriveServiceImpl,
    ): OracleDriveService = impl

    @Provides
    @Singleton
    fun provideCloudStorageProvider(
        impl: CloudStorageProviderImpl,
    ): CloudStorageProvider = impl

    @Provides
    @Singleton
    fun provideGenesisCryptographyManager(
        @ApplicationContext context: Context,
    ): CryptographyManager {
        return CryptographyManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideSecureStorage(
        @ApplicationContext context: Context,
        cryptoManager: CryptographyManager,
    ): SecureStorage {
        return SecureStorage.getInstance(context, cryptoManager)
    }

    @Provides
    @Singleton
    fun provideOracleDriveApi(
        @AuraNetwork client: OkHttpClient,
        securityContext: SecurityContext,
    ): OracleDriveApi {
        return Retrofit.Builder()
            .baseUrl("https://api.placeholder.dev/oracle/drive/") // Placeholder URL
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OracleDriveApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOracleDriveServiceImpl(
        genesisAgent: GenesisAgent,
        auraAgent: AuraAgent,
        kaiAgent: KaiAgent,
        securityContext: SecurityContext,
        oracleDriveApi: OracleDriveApi,
    ): OracleDriveServiceImpl {
        return OracleDriveServiceImpl(
            genesisAgent = genesisAgent,
            auraAgent = auraAgent,
            kaiAgent = kaiAgent,
            securityContext = securityContext,
            oracleDriveApi = oracleDriveApi
        )
    }
}
