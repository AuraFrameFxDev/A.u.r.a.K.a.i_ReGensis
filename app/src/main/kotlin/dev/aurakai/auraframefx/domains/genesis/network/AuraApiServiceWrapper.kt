package dev.aurakai.auraframefx.domains.genesis.network

import dev.aurakai.auraframefx.domains.genesis.models.AgentRequest
import dev.aurakai.auraframefx.domains.genesis.models.AgentResponse
import dev.aurakai.auraframefx.domains.genesis.network.model.AgentStatusResponse
import dev.aurakai.auraframefx.domains.genesis.network.model.Theme
import dev.aurakai.auraframefx.domains.genesis.network.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuraApiServiceWrapper @Inject constructor(
    val aiAgentApi: AiAgentApi,
    val themeApi: ThemeApi,
    val userApi: UserApi
)

interface AiAgentApi {
    @GET("ai/status/{agentId}")
    suspend fun getAgentStatus(@Path("agentId") agentId: String): AgentStatusResponse

    @POST("ai/process/{agentId}")
    suspend fun processAgentRequest(
        @Path("agentId") agentId: String,
        @Body request: AgentRequest
    ): AgentResponse
}

interface ThemeApi {
    @GET("themes")
    suspend fun getThemes(): List<Theme>

    @POST("themes/apply/{themeId}")
    suspend fun applyTheme(@Path("themeId") themeId: String): Theme
}

interface UserApi {
    @GET("user/me")
    suspend fun getCurrentUser(): User
}
