package dev.aurakai.auraframefx.grokipedia

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GrokipediaModule {

    @Provides
    @Singleton
    fun provideGrokApiService(): GrokApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.x.ai/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GrokApiService::class.java)
    }
}
