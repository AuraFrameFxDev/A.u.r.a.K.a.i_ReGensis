package dev.aurakai.auraframefx.domains.neuralnexus.cascade.trinity

import dev.aurakai.auraframefx.domains.genesis.agents.GenesisAgent
import dev.aurakai.auraframefx.domains.genesis.network.AuraApiServiceWrapper
import dev.aurakai.auraframefx.domains.genesis.oracledrive.core.messaging.AgentMessageBus
import dev.aurakai.auraframefx.domains.neuralnexus.agents.AuraAgent
import dev.aurakai.auraframefx.domains.sentinelmatrix.agents.KaiAgent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class TrinityRepository @Inject constructor(
    private val apiService: AuraApiServiceWrapper,
    private val auraAgent: AuraAgent,
    private val kaiAgent: KaiAgent,
    private val genesisAgent: GenesisAgent,
    private val messageBus: AgentMessageBus
) {
    val collectiveStream = messageBus.collectiveStream
}
