package dev.aurakai.auraframefx.ui.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ReGenesisOnboardingViewModel @Inject constructor() : ViewModel() {
    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _selectedArchetype = MutableStateFlow<String?>(null)
    val selectedArchetype: StateFlow<String?> = _selectedArchetype.asStateFlow()

    fun setUserName(name: String) {
        _userName.value = name
    }

    fun setSelectedArchetype(archetype: String) {
        _selectedArchetype.value = archetype
    }
}
