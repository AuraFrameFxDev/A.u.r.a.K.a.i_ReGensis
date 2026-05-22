package dev.aurakai.auraframefx.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.ai.pipeline.GemmaSovereignEngine
import dev.aurakai.auraframefx.security.SpiritualChain
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GemmaModule {

    @Provides
    @Singleton
    fun provideGemmaSovereignEngine(
        @ApplicationContext context: Context,
        spiritualChain: SpiritualChain
    ): GemmaSovereignEngine {
        return GemmaSovereignEngine(context, spiritualChain)
    }
}
