package dev.aurakai.auraframefx.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import dev.aurakai.auraframefx.aura.AuraAgent
import dev.aurakai.auraframefx.kai.KaiAgent
import dev.aurakai.auraframefx.cascade.CascadeAgent

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
@Singleton
class GenesisOrchestrator @Inject constructor(
    private val auraAgent: AuraAgent,
    private val kaiAgent: KaiAgent,
    private val cascadeAgent: CascadeAgent,
    // OracleDriveService will be injected here when available
) {

    // Dedicated scope for orchestration with SupervisorJob
    private val orchestratorScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

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

                Timber.i("✓ All agent domains initialized successfully")
                platformState = PlatformState.DOMAINS_READY

                // Phase 4: Start all agents
                startAgents()

                // Phase 5: Signal readiness
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
     * Initialize the given agent within the provided coroutine scope and log the outcome.
     *
     * Attempts to initialize the agent and rethrows any exception that occurs during initialization.
     *
     * @param agent The agent to initialize.
     * @param scope The CoroutineScope to associate with the agent's lifecycle.
     * @param agentName Human-readable agent name used in log messages.
     * @throws Exception If the agent's initialization fails.
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
     * Starts all configured agents' runtime domains in the orchestrator.
     *
     * The agents are started in the following order: Aura, Kai, Cascade.
     *
     * @throws Exception If any agent fails to start; the exception is propagated. 
     */
    private suspend fun startAgents() {
        try {
            Timber.d("🚀 Starting all agent domains...")

            auraAgent.start()
            kaiAgent.start()
            cascadeAgent.start()

            Timber.i("  ✓ All agents started")

        } catch (e: Exception) {
            Timber.e(e, "Failed to start agents")
            throw e
        }
    }

    /**
     * Route a message from one agent domain to another through the orchestrator.
     *
     * Routes the provided payload to the named recipient agent; recipient matching is case-insensitive.
     * If the recipient is unknown the message is ignored and a warning is recorded. Routing errors are
     * logged and do not propagate.
     *
     * @param fromAgent The sending agent's domain name.
     * @param toAgent The target agent's domain name (case-insensitive).
     * @param message The payload to deliver to the target agent.
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
        Timber.d("  → Handling Aura message: ${message.javaClass.simpleName}")
        // Implementation will be added as agents implement OrchestratableAgent
    }

    /**
     * Handle a message addressed to the Kai agent by logging its runtime type.
     *
     * This is a placeholder handler: it logs the incoming message's concrete class name and performs no further processing until Kai implements OrchestratableAgent.
     *
     * @param message The incoming domain-specific message destined for Kai.
     */
    private suspend fun handleKaiMessage(message: Any) {
        Timber.d("  → Handling Kai message: ${message.javaClass.simpleName}")
        // Implementation will be added as agents implement OrchestratableAgent
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
        Timber.d("  → Handling Cascade message: ${message.javaClass.simpleName}")
        // Implementation will be added as agents implement OrchestratableAgent
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
        Timber.d("  → Handling OracleDrive message: ${message.javaClass.simpleName}")
        // Implementation will be added when OracleDriveService is available
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