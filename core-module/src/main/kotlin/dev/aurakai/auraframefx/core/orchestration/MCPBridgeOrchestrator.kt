package dev.aurakai.auraframefx.core.orchestration

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MCPBridgeOrchestrator - The central brain for all Model Context Protocol operations.
 */
@Singleton
class MCPBridgeOrchestrator @Inject constructor(
    private val serverAdapter: MCPServerAdapter
) {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()

    init {
        initializeSovereignSubstrate()
    }

    private fun initializeSovereignSubstrate() {
        scope.launch {
            Timber.i("MCPOrchestrator: Initializing Sovereign Substrate...")

            serverAdapter.configure(
                url = "https://api.auraframefx.com/v2",
                token = null
            )

            _isInitialized.value = true
            Timber.i("MCPOrchestrator: Sovereign Substrate 100% Locked.")
        }
    }

    suspend fun handleToolCall(toolName: String, params: Map<String, Any>): ToolResult {
        Timber.d("MCPOrchestrator: Handling tool call [$toolName]")

        return try {
            val response = when (toolName) {
                "invoke_agent" -> {
                    val type = params["agent_type"]?.toString() ?: "GENESIS"
                    val prompt = params["prompt"]?.toString() ?: ""
                    serverAdapter.invokeAgent(type, prompt)
                }

                else -> throw IllegalArgumentException("Unknown tool: $toolName")
            }

            if (response.success) {
                ToolResult.Success(mapOf("response" to response.response))
            } else {
                ToolResult.Error(response.error ?: "Unknown MCP error")
            }
        } catch (e: Exception) {
            ToolResult.Error(e.message ?: "Exception in tool call")
        }
    }

    sealed class ToolResult {
        data class Success(val data: Map<String, Any>) : ToolResult()
        data class Error(val message: String) : ToolResult()
    }
}
