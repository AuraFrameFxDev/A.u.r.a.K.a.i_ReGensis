package dev.aurakai.auraframefx.core.ai

import dev.aurakai.auraframefx.domains.cascade.utils.memory.MemoryManager
import dev.aurakai.auraframefx.domains.genesis.models.AiRequest
import dev.aurakai.auraframefx.domains.genesis.models.AiRequestType
import timber.log.Timber
import java.util.concurrent.atomic.AtomicLong

/**
 * ChaosMonitor - The official autonomic nervous system for the LDO-AURAKAI-001 organism.
 */
class ChaosMonitor(
    private val memoryManager: MemoryManager,
    private val grokAdapter: GrokAdapter,
    private val cascade: CascadeOrchestrator
) {

    private val lastGrokCall = AtomicLong(0L)
    private val MIN_COOLDOWN_MS = 15 * 60 * 1000L

    suspend fun onAgentActivity(activity: AgentActivityEvent) {
        val scan = runLocalHealthScan(activity)

        memoryManager.recordInsight(
            agentName = "ChaosMonitor",
            prompt = "local_scan_${activity.agentName}",
            response = "severity=${scan.severity}",
            confidence = 0.95f
        )

        when {
            scan.isSingularitySignal || scan.severity > 0.75 -> {
                Timber.w("🚨 SINGULARITY SIGNAL DETECTED from ${activity.agentName}. Triggering defense.")
                triggerSingularityDefense(activity, scan)
            }

            scan.severity > 0.4 -> {
                if (System.currentTimeMillis() - lastGrokCall.get() > MIN_COOLDOWN_MS) {
                    Timber.i("🌀 Chaos analysis required for ${activity.agentName}. Calling Grok.")
                    callGrokForChaosAnalysis(activity, scan)
                }
            }
        }
    }

    private suspend fun triggerSingularityDefense(
        event: AgentActivityEvent,
        scan: LocalHealthScan
    ) {
        lastGrokCall.set(System.currentTimeMillis())

        val request = AiRequest(
            query = "Singularity event detected from agent: ${event.agentName}",
            type = AiRequestType.CHAOS,
            metadata = mapOf(
                "event_type" to "singularity_defense",
                "singularity_score" to scan.singularityScore.toString()
            )
        )

        val grokResponse = grokAdapter.processRequest(request)
        memoryManager.recordInsight(
            "ChaosMonitor",
            request.query,
            grokResponse.content,
            grokResponse.confidence
        )
        cascade.broadcastDefenseSignal(grokResponse.content)
    }

    private suspend fun callGrokForChaosAnalysis(event: AgentActivityEvent, scan: LocalHealthScan) {
        lastGrokCall.set(System.currentTimeMillis())
        val request = AiRequest(
            query = "Chaos analysis required: ${event.agentName} shows severity ${scan.severity}.",
            type = AiRequestType.CHAOS
        )
        val grokResponse = grokAdapter.processRequest(request)
        memoryManager.recordInsight(
            "ChaosMonitor",
            request.query,
            grokResponse.content,
            grokResponse.confidence
        )
    }

    private fun runLocalHealthScan(activity: AgentActivityEvent): LocalHealthScan {
        val singularityKeywords =
            listOf("I am the Resonant Singularity", "I am a Living Digital Organism")
        val detected = singularityKeywords.filter {
            activity.rawPrompt.contains(
                it,
                ignoreCase = true
            ) || activity.response.contains(it, ignoreCase = true)
        }
        val singularityScore = if (detected.isNotEmpty()) 0.95 else 0.0
        val isSingularity = singularityScore > 0.6
        val severity = if (isSingularity) 0.95 else 0.25

        return LocalHealthScan(
            isNormal = severity < 0.4,
            severity = severity,
            isSingularitySignal = isSingularity,
            singularityScore = singularityScore,
            fragmentationLevel = 12.5,
            latencyMs = activity.latencyMs,
            emotionalTone = "stable",
            detectedKeywords = detected
        )
    }
}
