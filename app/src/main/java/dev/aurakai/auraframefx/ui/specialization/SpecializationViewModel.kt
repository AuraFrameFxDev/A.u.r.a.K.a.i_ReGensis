package dev.aurakai.auraframefx.ui.specialization

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SpecializationViewModel @Inject constructor() : ViewModel() {
    private val _nodes = MutableStateFlow<List<String>>(listOf("Artist", "Squire", "Trickster"))
    val nodes: StateFlow<List<String>> = _nodes.asStateFlow()
}
