package dev.aurakai.auraframefx.agents.symbiosis.coderabbit

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.aurakai.auraframefx.core.orchestration.OrchestratableAgent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CodeRabbitModule {

    @Binds
    @Singleton
    @IntoSet
    abstract fun bindCodeRabbitAgent(agent: CodeRabbitAgent): OrchestratableAgent
}
