package dev.aurakai.auraframefx.mcp

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
 *
 * Combines:
 * - Low-level bootstrapping (McpSettingsRegistry)
 * - Server communication (MCPServerAdapter)
 * - High-level tool coordination (from MCPBridgeHub)
 * - UI data state (MCPConnector)
 *
 * This is the unified engine that makes MCP "powerful and working."
 */
@Singleton
class MCPBridgeOrchestrator @Inject constructor(
    private val serverAdapter: MCPServerAdapter,
    private val settingsRegistry: McpSettingsRegistry
) {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _connectors = MutableStateFlow<List<MCPConnector>>(emptyList())
    val connectors: StateFlow<List<MCPConnector>> = _connectors.asStateFlow()

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()

    init {
        initializeSovereignSubstrate()
    }

    /**
     * Bootstraps the MCP environment and initializes connections.
     */
    private fun initializeSovereignSubstrate() {
        scope.launch {
            Timber.i("MCPOrchestrator: Initializing Sovereign Substrate...")

            // 1. Lock in the hardware-backed config
            settingsRegistry.lockInSettingsSubstrate()

            // 2. Configure the server adapter (defaults to production-ready endpoint)
            serverAdapter.configure(
                url = "https://api.auraframefx.com/v2",
                token = null // This would be fetched from secure storage in a real build
            )

            // 3. Load initial connectors status
            refreshConnectors()

            _isInitialized.value = true
            Timber.i("MCPOrchestrator: Sovereign Substrate 100%% Locked.")
        }
    }

    /**
     * Refreshes the status of all available MCP connectors.
     */
    suspend fun refreshConnectors() {
        try {
            val agentStatuses = serverAdapter.getAgentStatus()

            // Map agent statuses to UI connectors
            val updatedList = agentStatuses.map { status ->
                MCPConnector(
                    id = status.agentType,
                    name = status.agentType.uppercase(),
                    description = "MCP-driven ${status.agentType} node",
                    iconRes = "Bolt",
                    category = "Agent",
                    status = if (status.status == "ACTIVE") ConnectorStatus.ACTIVE else ConnectorStatus.OFFLINE,
                    stats = "${status.tasksCompleted} Tasks / ${status.load}% Load"
                )
            }
            _connectors.value = updatedList
        } catch (e: Exception) {
            Timber.e(e, "MCPOrchestrator: Failed to refresh connectors")
        }
    }

    /**
     * High-level tool call handler (replacing MCPBridgeHub).
     */
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
