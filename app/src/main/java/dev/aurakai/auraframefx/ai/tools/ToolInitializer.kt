package dev.aurakai.auraframefx.ai.tools

import dev.aurakai.auraframefx.ai.tools.mcp.MCPServerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ToolInitializer - Registers All Agent Tools at Startup
 *
 * Initializes the ToolRegistry with all available tools for each agent domain.
 * This gives agents their "hands" to interact with the system.
 */
@Singleton
class ToolInitializer @Inject constructor(
    private val toolRegistry: ToolRegistry,
    private val mcpAdapter: MCPServerAdapter
) {

    private val initScope = CoroutineScope(Dispatchers.Default)

    /**
     * Initialize all agent tools
     * Call this during app startup (e.g., in Application.onCreate or Hilt initializer)
     */
    fun initializeTools() {
        initScope.launch {
            Timber.i("ToolInitializer: Starting tool registration...")

            try {
                registerAuraTools()
                registerKaiTools()
                registerGenesisTools()
                registerCascadeTools()
                registerMCPTools()

                val allTools = toolRegistry.getAllTools()
                Timber.i("ToolInitializer: Successfully registered ${allTools.size} tools")

                // Log tools by category
                val toolsByCategory = allTools.groupBy { it.category }
                toolsByCategory.forEach { (category, tools) ->
                    Timber.d("ToolInitializer:   $category: ${tools.size} tools")
                }

            } catch (e: Exception) {
                Timber.e(e, "ToolInitializer: Error during tool registration")
            }
        }
    }

    /**
     * Register Aura's UI/UX customization tools
     */
    private suspend fun registerAuraTools() {
        toolRegistry.registerTools(
            ApplyThemeTool(),
            CustomizeStatusBarTool(),
            GenerateUIComponentTool(),
            ApplyIconPackTool()
        )
        Timber.d("ToolInitializer: Registered Aura tools (UI/UX)")
    }

    /**
     * Register Kai's security and ROM tools
     */
    private suspend fun registerKaiTools() {
        toolRegistry.registerTools(
            ManageLSPosedHookTool(),
            FlashROMTool(),
            AnalyzeSecurityThreatTool(),
            ManageBootloaderTool(),
            ViewSystemLogsTool()
        )
        Timber.d("ToolInitializer: Registered Kai tools (Security/ROM)")
    }

    /**
     * Register Genesis's orchestration tools
     */
    private suspend fun registerGenesisTools() {
        toolRegistry.registerTools(
            CreateAgentTool(),
            OrchestrateTool(),
            CreateModuleTool(),
            AssignTaskTool()
        )
        Timber.d("ToolInitializer: Registered Genesis tools (Orchestration)")
    }

    /**
     * Register Cascade's fusion and vision tools
     */
    private suspend fun registerCascadeTools() {
        toolRegistry.registerTools(
            InitiateAgentFusionTool(),
            AnalyzeVisualInputTool(),
            BuildConsensusTool(),
            UpdateLearningModelTool(),
            MonitorDataStreamTool()
        )
        Timber.d("ToolInitializer: Registered Cascade tools (Fusion/Vision)")
    }

    /**
     * Register MCP API-backed tools
     */
    private suspend fun registerMCPTools() {
        // Configure MCP adapter (use dev environment by default)
        mcpAdapter.configure(
            url = "https://dev.api.auraframefx.com/v2",
            token = null // TODO: Get from secure storage
        )

        toolRegistry.registerTools(
            InvokeMCPAgentTool(mcpAdapter),
            AuraEmpathyMCPTool(mcpAdapter),
            KaiSecurityMCPTool(mcpAdapter),
            GetAgentStatusMCPTool(mcpAdapter)
        )
        Timber.d("ToolInitializer: Registered MCP tools (API-backed)")
    }

    /**
     * Get tool summary for debugging
     */
    suspend fun getToolSummary(): String {
        val allTools = toolRegistry.getAllTools()
        val summary = buildString {
            appendLine("═══════════════════════════════════════")
            appendLine("  AGENT TOOL REGISTRY - ${allTools.size} TOOLS")
            appendLine("═══════════════════════════════════════")
            appendLine()

            val toolsByCategory = allTools.groupBy { it.category }
            toolsByCategory.forEach { (category, tools) ->
                appendLine("[$category] - ${tools.size} tools")
                tools.forEach { tool ->
                    val agents = if (tool.authorizedAgents.contains("*")) "ALL" else tool.authorizedAgents.joinToString(", ")
                    appendLine("  • ${tool.name} (agents: $agents)")
                }
                appendLine()
            }
        }
        return summary
    }
}
