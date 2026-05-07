package dev.aurakai.auraframefx.data

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
import androidx.security.crypto.EncryptedSharedPreferences.create
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKey.Builder
import androidx.security.crypto.MasterKey.KeyScheme
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages secure storage of sensitive data like OAuth tokens or API keys.
 */
@Singleton
class SecurePreferences @Inject constructor(@field:ApplicationContext private val context: Context) {

    // Use applicationContext to prevent activity/fragment context leaks
    val appContext = context.applicationContext!!

    // Get or create master key for encryption
    private val masterKey: MasterKey by lazy {
        return@lazy Builder(/* context = */ appContext)
            .setKeyScheme(KeyScheme.AES256_GCM)
            .build()
    }

    // Create encrypted shared preferences
    val securePrefs by lazy {
        create(
            appContext,
            "secure_prefs",
            masterKey,
            AES256_SIV,
            AES256_GCM
        )
    }

    /**
     * Save the provided API key in encrypted preferences.
     *
     * The key is stored under the "api_key" preference entry.
     *
     * @param key The API key to store.
     */
    fun saveApiKey(key: String) {
        securePrefs.edit { putString("api_key", key) }
    }

    /**
     * Clears all values stored in the encrypted shared preferences.
     *
     * This removes all saved sensitive data (for example API keys and OAuth tokens).
     */
    fun clearAll() {
        securePrefs.edit { clear() }
    }
}

/**
 * Retrieve the stored OAuth token from secure preferences.
 *
 * @return The stored OAuth token, or `null` if none is present.
 */
fun getOAuthToken(securePreferences: SecurePreferences): String? {
    return securePreferences.securePrefs.getString("oauth_token", null)
}

/**
 * Retrieve the stored API key for Generative AI models.
 *
 * @return The API key as a `String`, or `null` if no key is stored.
 */
fun getApiKey(securePreferences: SecurePreferences): String? {
    return securePreferences.securePrefs.getString("api_key", null)
}

/**
 * Stores the provided OAuth token in secure preferences.
 *
 * @param token The OAuth token to store. If `null`, removes any stored token.
 */
fun saveOAuthToken(securePreferences: SecurePreferences, token: String?) {
    securePreferences.securePrefs.edit { putString("oauth_token", token) }
}