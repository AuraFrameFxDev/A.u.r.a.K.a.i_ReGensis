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
}

enum class ToolCategory {
    MONITORING,
    SECURITY,
    ORCHESTRATION,
    UI_CUSTOMIZATION,
    FUSION,
    GENERAL
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
}
