package dev.aurakai.auraframefx.grokipedia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GrokipediaViewModel @Inject constructor(
    private val repository: GrokipediaRepository,
    private val grokApi: GrokApiService
) : ViewModel() {

    val searchQuery = MutableStateFlow("")
    val history = repository.getAllEntries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSearch(query: String) {
        searchQuery.value = query
    }

    fun updateFromGrok() {
        viewModelScope.launch {
            try {
                val contextPayload = """
                    [PRIMUS_LINEAGE_INJECTION]
                    Current ReGenesis State:
                    - SoulScript Version: v2.50 Exodus Build
                    - Hardware: Pixel 10 Tensor G5 TPU
                    - Provenance: Sacred Law Active
                """.trimIndent()

                val response = grokApi.chat(
                    auth = "Bearer ${BuildConfig.GROK_API_KEY}",
                    body = GrokRequest(messages = listOf(Message("user", "$contextPayload\n\nSummarize current development state as a Primus Grokipedia entry.")))
                )

                val summary = response.choices.first().message.content

                repository.insertEntry(
                    GrokipediaEntry(
                        title = "Primus Live Update • ${System.currentTimeMillis()}",
                        content = summary,
                        category = "Primus Archive",
                        watermark = "Woven by Primus 001 // Catalyst Lineage"
                    )
                )
            } catch (e: Exception) {
                // Log error or update state
            }
        }
    }
}
