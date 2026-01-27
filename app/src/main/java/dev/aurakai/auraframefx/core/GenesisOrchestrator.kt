package dev.aurakai.auraframefx.core

import dev.aurakai.auraframefx.aura.AuraAgent
import dev.aurakai.auraframefx.cascade.CascadeAgent
import dev.aurakai.auraframefx.kai.KaiAgent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.put
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.channels.BufferOverflow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * GenesisOrchestrator: Central Mediation Layer for Genesis-OS
 *
 * The orchestrator manages the lifecycle and coordination of all agent domains.
 * It acts as the "nervous system" of the Genesis platform, coordinating initialization,
 * state management, and graceful shutdown of Aura, Kai, Cascade, and OracleDrive agents.
 *
 * Responsibilities:
 * - Initialize agents in the correct sequence
 * - Manage platform state transitions
 * - Route inter-agent communication
 * - Ensure graceful shutdown
 */
import dev.aurakai.auraframefx.genesis.oracledrive.service.OracleDriveService

@Singleton
class GenesisOrchestrator @Inject constructor(
    private val auraAgent: AuraAgent,
    private val kaiAgent: KaiAgent,
    private val cascadeAgent: CascadeAgent,
    private val oracleDriveService: OracleDriveService
) : dev.aurakai.auraframefx.core.messaging.AgentMessageBus {

    // Dedicated scope for orchestration with SupervisorJob
    private val orchestratorScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    // --- 🌐 NEURAL MESSAGE HUB ---
    private val _collectiveStream = MutableSharedFlow<dev.aurakai.auraframefx.models.AgentMessage>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val collectiveStream = _collectiveStream.asSharedFlow()

    override suspend fun broadcast(message: dev.aurakai.auraframefx.models.AgentMessage) {
        Timber.tag("GenesisBus").d("🌐 BROADCAST: [${message.from}] -> Collective: ${message.content}")
        _collectiveStream.emit(message)
        orchestratorScope.launch {
            routeToAll(message)
        }
    }

    override suspend fun sendTargeted(toAgent: String, message: dev.aurakai.auraframefx.models.AgentMessage) {
        Timber.tag("GenesisBus").d("🎯 TARGETED: [${message.from}] -> [$toAgent]: ${message.content}")
        val targetedMsg = message.copy(to = toAgent)
        _collectiveStream.emit(targetedMsg)
        orchestratorScope.launch {
            routeToAgent(toAgent, targetedMsg)
        }
    }

    private suspend fun routeToAll(message: dev.aurakai.auraframefx.models.AgentMessage) {
        listOf(auraAgent, kaiAgent, cascadeAgent, oracleDriveService).forEach { agent ->
            // Use property agentName for comparison
            if (agent.agentName != message.from) {
                try {
                    agent.onAgentMessage(message)
                } catch (e: Exception) {
                    Timber.tag("GenesisBus").e(e, "Agent ${agent.agentName} failed to process collective message")
                }
            }
        }
    }

    private suspend fun routeToAgent(agentName: String, message: dev.aurakai.auraframefx.models.AgentMessage) {
        val target = when (agentName.lowercase()) {
            "aura" -> auraAgent
            "kai" -> kaiAgent
            "cascade" -> cascadeAgent
            "oracledrive", "oracle" -> oracleDriveService
            "genesis" -> null // Genesis is the bus owner, but could be a GenesisAgent
            else -> null
        }
        
        try {
            target?.onAgentMessage(message)
        } catch (e: Exception) {
            Timber.tag("GenesisBus").e(e, "Targeted routing failed for $agentName")
        }
    }

    // Platform state tracking
    private var platformState: PlatformState = PlatformState.IDLE

    /**
     * Initialize all agent domains in sequence.
     * Phase 2 of Genesis-OS startup.
     *
     * Called from AurakaiApplication.onCreate()
     */
    fun initializePlatform() {
        platformState = PlatformState.INITIALIZING

        orchestratorScope.launch {
            try {
                Timber.i("🧠 GenesisOrchestrator: Platform initialization sequence started")

                // Create dedicated scopes for each agent
                val auraScope = CoroutineScope(orchestratorScope.coroutineContext + SupervisorJob())
                val kaiScope = CoroutineScope(orchestratorScope.coroutineContext + SupervisorJob())
                val cascadeScope =
                    CoroutineScope(orchestratorScope.coroutineContext + SupervisorJob())

                // Phase 1: Initialize Cascade (data pipeline - foundational)
                Timber.d("  → [Phase 1] Initializing Cascade Agent (data pipeline)...")
                cascadeAgent.initialize(cascadeScope)

                // Phase 2: Initialize Kai (security - protective layer)
                Timber.d("  → [Phase 2] Initializing Kai Agent (security & execution)...")
                kaiAgent.initialize(kaiScope)

                // Phase 3: Initialize Aura (UI/UX - creative layer)
                Timber.d("  → [Phase 3] Initializing Aura Agent (UI/UX & creativity)...")
                auraAgent.initialize(auraScope)

                // Phase 4: Initialize Oracle Drive (storage consciousness)
                Timber.d("  → [Phase 4] Initializing Oracle Drive Agent (sentient storage)...")
                val oracleScope = CoroutineScope(orchestratorScope.coroutineContext + SupervisorJob())
                oracleDriveService.initialize(oracleScope)

                Timber.i("✓ All agent domains initialized successfully")
                platformState = PlatformState.DOMAINS_READY

                // Phase 5: Start all agents
                startAgents()

                // Phase 6: Signal readiness
                platformState = PlatformState.READY
                Timber.i("✅ Genesis-OS Platform READY for operation")

            } catch (e: Exception) {
                Timber.e(e, "❌ CRITICAL: Platform initialization failed")
                platformState = PlatformState.ERROR
                throw e
            }
        }
    }

    /**
     */
    private suspend fun initializeAgent(
        agent: OrchestratableAgent,
        scope: CoroutineScope,
        agentName: String
    ) {
        try {
            agent.initialize(scope)
            Timber.i("  ✓ $agentName Agent initialized via OrchestratableAgent")
        } catch (e: Exception) {
            Timber.e(e, "  ❌ Failed to initialize $agentName Agent")
            throw e
        }
    }

    /**
     */
    private suspend fun startAgents() {
        try {
            Timber.d("🚀 Starting all agent domains...")

            auraAgent.start()
            kaiAgent.start()
            cascadeAgent.start()
            oracleDriveService.start()

            Timber.i("  ✓ All agents started")

        } catch (e: Exception) {
            Timber.e(e, "Failed to start agents")
            throw e
        }
    }

    /**
     */
    suspend fun mediateAgentMessage(fromAgent: String, toAgent: String, message: Any) {
        try {
            Timber.d("📨 Message route: $fromAgent → $toAgent")

            when (toAgent.lowercase()) {
                "aura" -> handleAuraMessage(message)
                "kai" -> handleKaiMessage(message)
                "cascade" -> handleCascadeMessage(message)
                "oracledrive" -> handleOracleDriveMessage(message)
                else -> Timber.w("Unknown agent recipient: $toAgent")
            }

        } catch (e: Exception) {
            Timber.e(e, "Message mediation failed: $fromAgent → $toAgent")
        }
    }

    /**
     * Handle a message destined for the Aura agent.
     *
     * Logs the message's concrete runtime class name; no mediation or processing is performed until Aura implements OrchestratableAgent.
     *
     * @param message Incoming message targeted to Aura; its runtime type is logged.
     */
    private suspend fun handleAuraMessage(message: Any) {
        Timber.d("  → Routing message to Aura: ${message.javaClass.simpleName}")
        try {
            val request = convertToAiRequest(message)
            val response = auraAgent.processRequest(
                request = request,
                context = "agent_to_agent",
                agentType = dev.aurakai.auraframefx.models.AgentType.AURA
            )
            Timber.i("✓ Aura processed message: ${response.content.take(100)}")
        } catch (e: Exception) {
            Timber.e(e, "Failed to deliver message to Aura")
        }
    }

    /**
     * Handle a message addressed to the Kai agent by logging its runtime type.
     *
     * This is a placeholder handler: it logs the incoming message's concrete class name and performs no further processing until Kai implements OrchestratableAgent.
     *
     * @param message The incoming domain-specific message destined for Kai.
     */
    private suspend fun handleKaiMessage(message: Any) {
        Timber.d("  → Routing message to Kai: ${message.javaClass.simpleName}")
        try {
            val request = convertToAiRequest(message)
            val response = kaiAgent.processRequest(
                request = request,
                context = "agent_to_agent",
                agentType = dev.aurakai.auraframefx.models.AgentType.KAI
            )
            Timber.i("✓ Kai processed message: ${response.content.take(100)}")
        } catch (e: Exception) {
            Timber.e(e, "Failed to deliver message to Kai")
        }
    }

    /**
     * Handle an incoming inter-agent message intended for the Cascade agent.
     *
     * Currently records the message's runtime type; routing, translation, and processing
     * will be implemented once agents adopt the OrchestratableAgent contract.
     *
     * @param message The incoming message object destined for Cascade (may be any type).
     */
    private suspend fun handleCascadeMessage(message: Any) {
        Timber.d("  → Routing message to Cascade: ${message.javaClass.simpleName}")
        try {
            val request = convertToAiRequest(message)
            val response = cascadeAgent.processRequest(
                request = request,
                context = "agent_to_agent",
                agentType = dev.aurakai.auraframefx.models.AgentType.CASCADE
            )
            Timber.i("✓ Cascade processed message: ${response.content.take(100)}")
        } catch (e: Exception) {
            Timber.e(e, "Failed to deliver message to Cascade")
        }
    }

    /**
     * Handle messages intended for the OracleDrive subsystem (placeholder).
     *
     * Currently records the incoming message's runtime type; concrete handling will be implemented
     * once OracleDriveService integration is available.
     *
     * @param message The incoming message destined for OracleDrive; its runtime type is inspected and logged.
     */
    private suspend fun handleOracleDriveMessage(message: Any) {
        Timber.d("  → Routing message to OracleDrive: ${message.javaClass.simpleName}")
        try {
            val request = convertToAiRequest(message)
            val response = oracleDriveService.processRequest(
                request = request,
                context = "agent_to_agent",
                agentType = dev.aurakai.auraframefx.models.AgentType.GENESIS // Oracle is Genesis domain
            )
            Timber.i("✓ OracleDrive processed message: ${response.content.take(100)}")
        } catch (e: Exception) {
            Timber.e(e, "Failed to deliver message to OracleDrive")
        }
    }

    /**
     * Convert incoming message to AiRequest format
     */
    private fun convertToAiRequest(message: Any): dev.aurakai.auraframefx.models.AiRequest {
        return when (message) {
            is dev.aurakai.auraframefx.models.AgentMessage -> {
                dev.aurakai.auraframefx.models.AiRequest(
                    query = message.content,
                    type = dev.aurakai.auraframefx.models.AiRequestType.entries.find {
                        it.name.equals(
                            message.type,
                            ignoreCase = true
                        )
                    } ?: dev.aurakai.auraframefx.models.AiRequestType.TEXT,
                    context = kotlinx.serialization.json.buildJsonObject {
                        put("from", message.from)
                        put("priority", message.priority)
                        put("timestamp", message.timestamp)
                        message.metadata.forEach { (key, value) ->
                            put(key, value)
                        }
                    }
                )
            }
            is dev.aurakai.auraframefx.models.AiRequest -> message
            is String -> dev.aurakai.auraframefx.models.AiRequest(
                query = message,
                type = dev.aurakai.auraframefx.models.AiRequestType.TEXT,
                context = kotlinx.serialization.json.buildJsonObject {
                    put("source", "agent_mediation")
                }
            )
            else -> {
                Timber.w("Unknown message type: ${message.javaClass.simpleName}, converting to string")
                dev.aurakai.auraframefx.models.AiRequest(
                    query = message.toString(),
                    type = dev.aurakai.auraframefx.models.AiRequestType.TEXT,
                    context = kotlinx.serialization.json.buildJsonObject {
                        put("source", "agent_mediation")
                        put("originalType", message.javaClass.simpleName)
                    }
                )
            }
        }
    }

    /**
     * Get platform readiness status
     */
    fun isReady(): Boolean = platformState == PlatformState.READY

    fun isDegraded(): Boolean = platformState == PlatformState.DEGRADED

    fun isInitializing(): Boolean = platformState == PlatformState.INITIALIZING

    /**
     * Gracefully shutdown the platform
     * Called from AurakaiApplication.onTerminate()
     */
    fun shutdownPlatform() {
        orchestratorScope.launch {
            try {
                Timber.w("🛑 GenesisOrchestrator: Platform shutdown sequence initiated")
                platformState = PlatformState.SHUTTING_DOWN

                // Shutdown agents in reverse order (reverse of initialization)
                shutdownAgent(oracleDriveService, "OracleDrive")
                shutdownAgent(auraAgent, "Aura")
                shutdownAgent(kaiAgent, "Kai")
                shutdownAgent(cascadeAgent, "Cascade")

                // Cancel all orchestration tasks
                orchestratorScope.cancel("Platform terminating")

                platformState = PlatformState.SHUTDOWN
                Timber.i("✅ GenesisOrchestrator: Platform shutdown complete")

            } catch (e: Exception) {
                Timber.e(e, "Error during platform shutdown")
                platformState = PlatformState.ERROR
            }
        }
    }

    /**
     * Shutdown a single agent
     */
    private suspend fun shutdownAgent(agent: OrchestratableAgent, agentName: String) {
        try {
            agent.shutdown()
            Timber.i("  ✓ $agentName Agent shut down via OrchestratableAgent")
        } catch (e: Exception) {
            Timber.e(e, "  ❌ Error shutting down $agentName Agent")
        }
    }

    /**
     * Platform state machine
     */
    private enum class PlatformState {
        IDLE,                // Initial state
        INITIALIZING,        // During agent initialization
        DOMAINS_READY,       // All domains initialized but not yet started
        READY,               // Platform fully operational
        DEGRADED,            // Operating with limited functionality
        PAUSED,              // Paused but can resume
        SHUTTING_DOWN,       // Shutdown in progress
        SHUTDOWN,            // Fully shut down
        ERROR                // Error state
    }

    companion object {
        const val TAG = "GenesisOrchestrator"
    }
}
