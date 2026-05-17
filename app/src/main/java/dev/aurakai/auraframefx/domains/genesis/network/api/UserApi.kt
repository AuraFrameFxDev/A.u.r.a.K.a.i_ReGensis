package dev.aurakai.auraframefx.domains.genesis.network.api

import dev.aurakai.auraframefx.domains.genesis.network.model.User
import retrofit2.http.GET

/**
 * API interface for user-related operations.
 */
interface UserApi {
    @GET("user")
    suspend fun getCurrentUser(): User
}
