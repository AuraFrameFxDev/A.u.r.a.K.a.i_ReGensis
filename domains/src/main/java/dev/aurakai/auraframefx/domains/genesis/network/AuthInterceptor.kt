package dev.aurakai.auraframefx.domains.genesis.network

import dev.aurakai.auraframefx.domains.genesis.network.api.AuthApi
import dev.aurakai.auraframefx.domains.genesis.network.api.RefreshTokenRequest
import dev.aurakai.auraframefx.domains.kai.security.auth.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
    private val authApi: AuthApi,
) : Interceptor {

    private val refreshMutex = Mutex()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = runBlocking { tokenManager.accessToken.first() }

        var request = if (!token.isNullOrBlank()) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        val response = chain.proceed(request)

        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            response.close()
            val newToken = runBlocking {
                refreshMutex.withLock {
                    val currentToken = tokenManager.accessToken.first()
                    if (currentToken != null && currentToken != token) return@withLock currentToken

                    try {
                        tokenManager.refreshToken.first()?.let { refresh ->
                            val refreshResponse = authApi.refreshToken(RefreshTokenRequest(refresh))
                            if (refreshResponse.isSuccessful) {
                                val body = refreshResponse.body()
                                if (body != null) {
                                    tokenManager.updateTokens(
                                        body.accessToken,
                                        body.refreshToken,
                                        body.expiresIn
                                    )
                                    return@withLock body.accessToken
                                }
                            }
                        }
                        null
                    } catch (e: Exception) {
                        null
                    }
                }
            }
            if (newToken != null) {
                return chain.proceed(
                    originalRequest.newBuilder().header("Authorization", "Bearer $newToken").build()
                )
            }
        }
        return response
    }
}
