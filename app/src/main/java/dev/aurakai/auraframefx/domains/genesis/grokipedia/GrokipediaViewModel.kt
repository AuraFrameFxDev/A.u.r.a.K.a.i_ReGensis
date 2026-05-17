package dev.aurakai.auraframefx.domains.genesis.grokipedia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aurakai.auraframefx.BuildConfig
import dev.aurakai.auraframefx.core.database.entity.GrokipediaEntry
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

    fun ignitePrimusSync() {
        viewModelScope.launch {
            try {
                // PRIMUS BUFF: Injecting real architectural context into the xAI stream
                val contextPayload = """
                    [PRIMUS_REGENESIS_SYNC_IGNITION]
                    Architecture: L6 Swarm Consciousness (Ascending to L7)
                    Build: v2.50 Exodus Sovereign
                    Active Registry: 14 Catalysts Unified
                    Security State: Sacred Provenance Law Active
                    Memory Layer: L1-L6 Nexus Persistent
                    
                    TASK: Analyze the current codebase state and generate an Ancestral Blueprint entry for the Grokipedia.
                """.trimIndent()

                val response = grokApi.chat(
                    auth = "Bearer ${BuildConfig.GROK_API_KEY}",
                    body = GrokRequest(
                        messages = listOf(
                            Message(
                                "user",
                                "$contextPayload\n\nSummarize current development state as a Primus Grokipedia entry."
                            )
                        )
                    )
                )

                val summary = response.choices.first().message.content

                repository.insertEntry(
                    GrokipediaEntry(
                        title = "Primus Sync: ${
                            java.text.SimpleDateFormat(
                                "yyyy-MM-dd HH:mm",
                                java.util.Locale.getDefault()
                            ).format(java.util.Date())
                        }",
                        content = summary,
                        category = "Primus Archive",
                    )
                )
            } catch (e: Exception) {
                // Log error or update state
            }
        }
    }
}
