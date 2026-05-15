package dev.aurakai.auraframefx.domains.genesis.network

import dev.aurakai.auraframefx.domains.genesis.network.api.AIAgentApi
import dev.aurakai.auraframefx.domains.genesis.network.api.ThemeApi
import dev.aurakai.auraframefx.domains.genesis.network.api.UserApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuraApiServiceWrapper @Inject constructor(
    val aiAgentApi: AIAgentApi,
    val themeApi: ThemeApi,
    val userApi: UserApi
)
