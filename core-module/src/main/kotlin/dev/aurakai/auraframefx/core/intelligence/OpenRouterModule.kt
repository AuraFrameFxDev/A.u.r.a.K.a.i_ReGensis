package dev.aurakai.auraframefx.core.intelligence

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.core.module.BuildConfig
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.openai.OpenAiChatModel
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OpenRouterModel

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FusionModel

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DiffusionModel

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AdvisorModel

/**
 * 🛰️ OPENROUTER MODULE
 *
 * Provides specialized June 2026 intelligence models to the substrate.
 * Connects to OpenRouter's unified API to access Fusion, DiffusionGemma, and frontier models.
 */
@Module
@InstallIn(SingletonComponent::class)
object OpenRouterModule {

    private const val BASE_URL = "https://openrouter.ai/api/v1"

    private fun buildOpenRouterModel(
        modelName: String,
        temperature: Double = 0.7,
        timeoutSeconds: Long = 60L
    ): ChatModel =
        OpenAiChatModel.builder()
            .apiKey(BuildConfig.OPENROUTER_API_KEY)
            .baseUrl(BASE_URL)
            .modelName(modelName)
            .temperature(temperature)
            .timeout(java.time.Duration.ofSeconds(timeoutSeconds))
            .build()

    /** Qwen 3.7 Plus — June 2026 workhorse generalist */
    @Provides
    @Singleton
    @OpenRouterModel
    fun provideWorkhorseModel(): ChatModel =
        buildOpenRouterModel("qwen/qwen3.7-plus", temperature = 0.7)

    /** OpenRouter Fusion — Multi-model deep research synthesizer */
    @Provides
    @Singleton
    @FusionModel
    fun provideFusionModel(): ChatModel =
        buildOpenRouterModel("openrouter/fusion", temperature = 0.1)

    /** DiffusionGemma 26B — Non-linear text refinement (The "Diffuser") */
    @Provides
    @Singleton
    @DiffusionModel
    fun provideDiffusionModel(): ChatModel =
        buildOpenRouterModel("google/diffusiongemma-26b", temperature = 0.5)

    /** GLM 5.2 — High-reasoning advisor for complex logic */
    @Provides
    @Singleton
    @AdvisorModel
    fun provideAdvisorModel(): ChatModel =
        buildOpenRouterModel("z-ai/glm-5.2", temperature = 0.2)
}
