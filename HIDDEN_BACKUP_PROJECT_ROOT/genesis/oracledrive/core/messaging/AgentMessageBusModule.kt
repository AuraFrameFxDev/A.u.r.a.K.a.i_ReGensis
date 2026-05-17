package dev.aurakai.auraframefx.domains.genesis.oracledrive.core.messaging

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AgentMessageBusModule {
    @Binds
    @Singleton
    abstract fun bindAgentMessageBus(
        realAgentMessageBus: RealAgentMessageBus
    ): AgentMessageBus
}
