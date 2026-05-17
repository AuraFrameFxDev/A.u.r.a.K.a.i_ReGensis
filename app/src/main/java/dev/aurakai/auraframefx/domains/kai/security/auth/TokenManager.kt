package dev.aurakai.auraframefx.domains.kai.security.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aurakai.auraframefx.domains.cascade.utils.AppCoroutineDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = "secure_auth_tokens")

/**
 * Modern TokenManager using Jetpack DataStore (recommended over SharedPreferences).
 * Fully coroutine-based, type-safe, and avoids all deprecation warnings.
 */
@Singleton
class TokenManager @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val dispatchers: AppCoroutineDispatchers
) {

    private val dataStore = context.tokenDataStore

    private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
    private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    private val KEY_ACCESS_TOKEN_EXPIRY = longPreferencesKey("access_token_expiry")

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    val accessToken: Flow<String?> = dataStore.data.map { it[KEY_ACCESS_TOKEN] }
    val refreshToken: Flow<String?> = dataStore.data.map { it[KEY_REFRESH_TOKEN] }
    val accessTokenExpiry: Flow<Long> = dataStore.data.map { it[KEY_ACCESS_TOKEN_EXPIRY] ?: 0L }

    suspend fun updateTokens(accessToken: String, refreshToken: String, expiresInSeconds: Long) {
        withContext(dispatchers.io) {
            dataStore.edit { prefs ->
                prefs[KEY_ACCESS_TOKEN] = accessToken
                prefs[KEY_REFRESH_TOKEN] = refreshToken
                prefs[KEY_ACCESS_TOKEN_EXPIRY] =
                    System.currentTimeMillis() + (expiresInSeconds * 1000)
            }
        }
        _authState.value = AuthState.Authenticated
    }

    suspend fun clearTokens() {
        withContext(dispatchers.io) {
            dataStore.edit { prefs ->
                prefs.remove(KEY_ACCESS_TOKEN)
                prefs.remove(KEY_REFRESH_TOKEN)
                prefs.remove(KEY_ACCESS_TOKEN_EXPIRY)
            }
        }
        _authState.value = AuthState.Unauthenticated
    }

    suspend fun isLoggedIn(): Boolean = hasValidAccessToken()

    suspend fun hasValidAccessToken(): Boolean {
        val token = dataStore.data.first()[KEY_ACCESS_TOKEN]
        val expiry = dataStore.data.first()[KEY_ACCESS_TOKEN_EXPIRY] ?: 0L
        return !token.isNullOrBlank() && System.currentTimeMillis() < expiry
    }

    suspend fun getAuthorizationHeader(): String? {
        val token = dataStore.data.first()[KEY_ACCESS_TOKEN]
        return token?.let { "Bearer $it" }
    }

    sealed class AuthState {
        data object Authenticated : AuthState()
        data object Unauthenticated : AuthState()
    }
}