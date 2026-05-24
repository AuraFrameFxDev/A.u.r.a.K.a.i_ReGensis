package dev.aurakai.auraframefx.domains.nexus.preferences

import dev.aurakai.auraframefx.domains.nexus.config.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Simple manager for user preferences required by onboarding and other features.
 * Exposes suspend-friendly APIs and is Hilt injectable.
 */
@Singleton
class UserPreferencesManager @Inject constructor(
    private val userPreferences: UserPreferences
) {

    suspend fun setGenderIdentity(identity: String) = withContext(Dispatchers.IO) {
        userPreferences.setPreference("gender_identity", identity)
    }

    suspend fun getGenderIdentity(): String? = withContext(Dispatchers.IO) {
        userPreferences.getPreference("gender_identity", "").ifEmpty { null }
    }

    suspend fun setOnboardingComplete(value: Boolean) = withContext(Dispatchers.IO) {
        userPreferences.setPreference("onboarding_complete", value.toString())
    }

    suspend fun isOnboardingComplete(): Boolean = withContext(Dispatchers.IO) {
        userPreferences.getPreference("onboarding_complete", "false").toBoolean()
    }

    suspend fun setUserName(name: String) = withContext(Dispatchers.IO) {
        userPreferences.setPreference("user_name", name)
    }

    suspend fun getUserName(): String = withContext(Dispatchers.IO) {
        userPreferences.getPreference("user_name", "Catalyst")
    }

    suspend fun setCatalystAlignment(catalystId: String) = withContext(Dispatchers.IO) {
        userPreferences.setPreference("catalyst_alignment", catalystId)
    }

    suspend fun getCatalystAlignment(): String? = withContext(Dispatchers.IO) {
        userPreferences.getPreference("catalyst_alignment", "").ifEmpty { null }
    }
}
