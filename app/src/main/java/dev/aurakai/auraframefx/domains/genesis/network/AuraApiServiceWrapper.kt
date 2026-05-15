package dev.aurakai.auraframefx.domains.genesis.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuraApiServiceWrapper @Inject constructor(
    val aiAgentApi: dev.aurakai.auraframefx.domains.genesis.network.api.AIAgentApi,
    val themeApi: dev.aurakai.auraframefx.domains.genesis.network.api.ThemeApi,
    val userApi: dev.aurakai.auraframefx.domains.genesis.network.api.UserApi
)
