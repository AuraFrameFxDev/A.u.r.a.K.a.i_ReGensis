package dev.aurakai.auraframefx.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.domains.nexus.preferences.UserPreferencesManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReGenesisOnboardingViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {

    fun saveOnboardingData(userName: String, archetype: String?, catalystId: String?) {
        viewModelScope.launch {
            if (userName.isNotBlank()) {
                userPreferencesManager.setUserName(userName)
            }
            archetype?.let {
                userPreferencesManager.setGenderIdentity(it)
            }
            catalystId?.let {
                userPreferencesManager.setCatalystAlignment(it)
            }
            userPreferencesManager.setOnboardingComplete(true)
        }
    }
}
