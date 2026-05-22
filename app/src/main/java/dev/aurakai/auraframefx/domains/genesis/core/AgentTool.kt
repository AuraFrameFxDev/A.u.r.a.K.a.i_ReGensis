package dev.aurakai.auraframefx.domains.genesis.core

import kotlinx.serialization.json.JsonObject

/**
 * AgentTool Interface
 * Core contract for all tools that can be invoked by AI agents.
 */
interface AgentTool {
    val name: String
    val description: String
    val authorizedAgents: Set<String>
    val category: ToolCategory
    val inputSchema: ToolInputSchema

    suspend fun execute(params: JsonObject, agentId: String): ToolResult

    /**
     * Checks if the specified agent is authorized to use this tool.
     */
    fun isAuthorized(agentId: String): Boolean {
        return authorizedAgents.contains("*") || authorizedAgents.contains(agentId)
    }
}

enum class ToolCategory {
    MONITORING,
    SECURITY,
    ORCHESTRATION,
    UI_CUSTOMIZATION,
    FUSION,
    VISION,
    GENERAL,
    AGENT_MANAGEMENT,
    MODULE_CREATION,
    ROM_TOOLS,
    BOOTLOADER
}

data class ToolInputSchema(
    val properties: Map<String, PropertySchema>,
    val required: List<String> = emptyList()
)

data class PropertySchema(
    val type: String,
    val description: String,
    val enum: List<String>? = null,
    val default: String? = null,
    val items: PropertySchema? = null
)

sealed class ToolResult {
    data class Success(
        val output: String,
        val metadata: Map<String, Any> = emptyMap()
    ) : ToolResult()

    data class Failure(
        val error: String,
        val errorCode: String? = null
    ) : ToolResult()

    data class Pending(
        val taskId: String,
        val estimatedDuration: Long
    ) : ToolResult()
}

/**
 * Request to use a tool
 */
data class ToolUseRequest(
    val toolName: String,
    val agentId: String,
    val parameters: JsonObject,
    val requestId: String = java.util.UUID.randomUUID().toString()
)

/**
 * Response from a tool execution
 */
data class ToolUseResponse(
    val requestId: String,
    val result: String,
    val executionTimeMs: Long,
    val success: Boolean
)
