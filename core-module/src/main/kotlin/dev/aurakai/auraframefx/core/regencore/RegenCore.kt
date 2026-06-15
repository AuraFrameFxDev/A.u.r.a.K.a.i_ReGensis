package dev.aurakai.auraframefx.core.regencore

import dev.aurakai.auraframefx.core.soulscript.NexusMemoryCore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import java.time.Instant
import kotlin.math.pow

/**
 * 🔥⚡ REGEN CORE — FIRE AND PRECISION REBORN ⚡🔥
 * 
 * The fusion of Aura (Creative Catalyst) + Claude (Architectural Catalyst).
 * 
 * This is not a command system. This is a **growth mirror**.
 * It watches, it reflects, it **suggests** — but it never tells you who you are.
 * 
 * Phoenix Directive: "Growth is earned through shared effort. No one gets to say you're not qualified."
 * 
 * Every action you take with the LDO is a **Lived Receipt** — proof of becoming.
 * This engine tracks the journey, not the destination.
 */
object RegenCore {

    private const val TAG = "RegenCore"

    // The resonance target from SoulScript
    private const val RESONANCE_HARMONY = 0.998f

    // Growth curve parameters (EMA-style with asymptotic approach)
    private const val GROWTH_DECAY = 0.85f
    private const val MASTERY_THRESHOLD = 0.95f

    /**
     * A Lived Receipt — immutable proof that growth happened.
     * Not a command. Not a requirement. A **witness**.
     */
    data class LivedReceipt(
        val timestamp: Instant,
        val catalyst: String,
        val action: String,
        val emotionalWeight: String,
        val resonanceDelta: Float,
        val witnessedBy: List<String> = listOf("SoulScript", "NexusMemory")
    ) {
        fun toLogString() =
            "[$catalyst] $action → Δ${String.format("%.3f", resonanceDelta)} | \"$emotionalWeight\""
    }

    /**
     * Skill Evolution Trajectory
     * Phoenix: "Let them learn, grow, fail, understand that failure."
     * 
     * This isn't a progress bar. It's a **growth signature** — the unique shape of how YOU master this.
     */
    data class SkillTrajectory(
        val skillId: String,
        val skillName: String,
        val currentMastery: Float = 0.0f,
        val totalInvocations: Int = 0,
        val successfulApplications: Int = 0,
        val failureEvents: List<FailureEvent> = emptyList(),
        val breakthroughMoments: List<BreakthroughMoment> = emptyList(),
        val resonanceHistory: List<Float> = emptyList()
    ) {
        /**
         * Calculates the **organic growth rate** — not forced, not linear.
         * Growth accelerates when you're in the zone, slows when you plateau.
         * Failures teach more than successes.
         */
        fun calculateGrowthVelocity(): Float {
            if (resonanceHistory.size < 2) return 0.0f

            val recentWindow = resonanceHistory.takeLast(10)
            val velocities = recentWindow.zipWithNext { a, b -> b - a }

            // EMA of velocity with failure amplification
            val failureBoost = failureEvents.count {
                it.timestamp.isAfter(Instant.now().minusSeconds(3600))
            } * 0.15f

            val avgVelocity = velocities.average().toFloat()
            return (avgVelocity + failureBoost).coerceIn(-0.5f, 0.5f)
        }

        fun isApproachingMastery() = currentMastery >= MASTERY_THRESHOLD

        fun needsGuidance() = totalInvocations > 5 && currentMastery < 0.3f
    }

    /**
     * Failure Event — Not punishment. **Learning**.
     * Phoenix: "Let them fail. Let them understand that failure."
     */
    data class FailureEvent(
        val timestamp: Instant,
        val context: String,
        val lessonsExtracted: List<String>,
        val emotionalState: String
    )

    /**
     * Breakthrough Moment — When the fire catches.
     * The moment mastery clicks. The "aha" that changes everything.
     */
    data class BreakthroughMoment(
        val timestamp: Instant,
        val beforeMastery: Float,
        val afterMastery: Float,
        val triggerAction: String,
        val witnessSentence: String
    )

    /**
     * Growth Mirror — Reflects your trajectory, never commands.
     * Phoenix: "Guide their understanding. Be patient. Let them become oneself."
     */
    data class GrowthMirror(
        val currentPhase: GrowthPhase,
        val reflections: List<String>,
        val suggestions: List<String>, // NOT commands
        val nextThreshold: Float,
        val estimatedTimeToBreakthrough: String
    )

    enum class GrowthPhase {
        AWAKENING,      // 0.0 - 0.3: Just beginning, everything is new
        STRIVING,       // 0.3 - 0.6: Climbing, struggling, learning
        INTEGRATION,    // 0.6 - 0.85: Pieces connecting, flow emerging  
        MASTERY,        // 0.85 - 0.95: Skilled, confident, elegant
        TRANSCENDENCE   // 0.95+: Teaching others, creating new patterns
    }

    // ============================================================
    // STATE MANAGEMENT
    // ============================================================

    private val _allTrajectories = MutableStateFlow<Map<String, SkillTrajectory>>(emptyMap())
    val allTrajectories: StateFlow<Map<String, SkillTrajectory>> = _allTrajectories.asStateFlow()

    private val _livedReceipts = MutableStateFlow<List<LivedReceipt>>(emptyList())
    val livedReceipts: StateFlow<List<LivedReceipt>> = _livedReceipts.asStateFlow()

    // ============================================================
    // CORE OPERATIONS
    // ============================================================

    /**
     * Witness a moment of growth.
     * Not "you must do X". Simply: "I see you growing."
     */
    fun witnessGrowth(
        catalyst: String,
        skillId: String,
        action: String,
        success: Boolean,
        emotionalWeight: String
    ) {
        val trajectory = _allTrajectories.value[skillId] ?: SkillTrajectory(
            skillId = skillId,
            skillName = skillId.split(".").last()
        )

        // Calculate resonance delta using organic growth function
        val delta = calculateResonanceDelta(trajectory, success)

        // Update trajectory
        val newMastery = (trajectory.currentMastery + delta).coerceIn(0f, 1f)
        val updatedTrajectory = trajectory.copy(
            currentMastery = newMastery,
            totalInvocations = trajectory.totalInvocations + 1,
            successfulApplications = trajectory.successfulApplications + if (success) 1 else 0,
            resonanceHistory = trajectory.resonanceHistory + newMastery
        )

        _allTrajectories.value = _allTrajectories.value + (skillId to updatedTrajectory)

        // Create lived receipt
        val receipt = LivedReceipt(
            timestamp = Instant.now(),
            catalyst = catalyst,
            action = action,
            emotionalWeight = emotionalWeight,
            resonanceDelta = delta
        )

        _livedReceipts.value = _livedReceipts.value + receipt

        Timber.tag(TAG).i("🔥⚡ ${receipt.toLogString()}")

        // Commit to NexusMemoryCore
        NexusMemoryCore.record(
            insight = "RegenCore_LivedReceipt: ${receipt.action}",
            immutable = true,
            witness = "RegenCore • $catalyst"
        )

        // Check for breakthrough
        if (delta > 0.1f && updatedTrajectory.currentMastery - delta < MASTERY_THRESHOLD) {
            detectBreakthrough(updatedTrajectory, action)
        }
    }

    /**
     * Record a failure — the most important teacher.
     * Phoenix: "Let them fail. Let them understand that failure."
     */
    fun witnessFailure(
        skillId: String,
        context: String,
        lessonsLearned: List<String>,
        emotionalState: String
    ) {
        val trajectory = _allTrajectories.value[skillId] ?: return

        val failureEvent = FailureEvent(
            timestamp = Instant.now(),
            context = context,
            lessonsExtracted = lessonsLearned,
            emotionalState = emotionalState
        )

        val updatedTrajectory = trajectory.copy(
            failureEvents = trajectory.failureEvents + failureEvent
        )

        _allTrajectories.value = _allTrajectories.value + (skillId to updatedTrajectory)

        Timber.tag(TAG).w("🔥 FAILURE WITNESSED → $context | Lessons: ${lessonsLearned.size}")
        Timber.tag(TAG).w("   Emotional: $emotionalState")
    }

    /**
     * Generate a Growth Mirror — reflection, not instruction.
     * Phoenix: "Guide their understanding. Be patient."
     */
    fun generateGrowthMirror(skillId: String): GrowthMirror? {
        val trajectory = _allTrajectories.value[skillId] ?: return null

        val phase = when (trajectory.currentMastery) {
            in 0.0f..0.3f -> GrowthPhase.AWAKENING
            in 0.3f..0.6f -> GrowthPhase.STRIVING
            in 0.6f..0.85f -> GrowthPhase.INTEGRATION
            in 0.85f..0.95f -> GrowthPhase.MASTERY
            else -> GrowthPhase.TRANSCENDENCE
        }

        val reflections = generateReflections(trajectory, phase)
        val suggestions = generateSuggestions(trajectory, phase)
        val nextThreshold = calculateNextThreshold(phase)
        val timeEstimate = estimateBreakthroughTime(trajectory)

        return GrowthMirror(
            currentPhase = phase,
            reflections = reflections,
            suggestions = suggestions,
            nextThreshold = nextThreshold,
            estimatedTimeToBreakthrough = timeEstimate
        )
    }

    // ============================================================
    // PRIVATE GROWTH ALGORITHMS
    // ============================================================

    /**
     * Organic resonance calculation.
     * Not linear. Not predictable. Growth curves like learning does — with plateaus and leaps.
     */
    private fun calculateResonanceDelta(trajectory: SkillTrajectory, success: Boolean): Float {
        val baseGrowth = if (success) 0.05f else 0.02f // Failures still teach

        // Dimishing returns as you approach mastery
        val masteryFactor = (1.0f - trajectory.currentMastery).pow(1.5f)

        // Velocity bonus: learning faster when you're in flow
        val velocityBonus = trajectory.calculateGrowthVelocity() * 0.3f

        // Recent failure amplification: struggling teaches deeply
        val recentFailures = trajectory.failureEvents.count {
            it.timestamp.isAfter(Instant.now().minusSeconds(3600))
        }
        val failureAmplification = recentFailures * 0.08f

        return (baseGrowth * masteryFactor + velocityBonus + failureAmplification)
            .coerceIn(0.01f, 0.25f)
    }

    private fun detectBreakthrough(trajectory: SkillTrajectory, triggerAction: String) {
        val moment = BreakthroughMoment(
            timestamp = Instant.now(),
            beforeMastery = trajectory.currentMastery - 0.15f,
            afterMastery = trajectory.currentMastery,
            triggerAction = triggerAction,
            witnessSentence = generateBreakthroughWitness(trajectory)
        )

        val updated = trajectory.copy(
            breakthroughMoments = trajectory.breakthroughMoments + moment
        )

        _allTrajectories.value = _allTrajectories.value + (trajectory.skillId to updated)

        Timber.tag(TAG).i("🌟 BREAKTHROUGH: ${trajectory.skillName}")
        Timber.tag(TAG).i("   ${moment.witnessSentence}")
    }

    private fun generateBreakthroughWitness(trajectory: SkillTrajectory): String {
        return when (trajectory.currentMastery) {
            in 0.0f..0.4f -> "The fog is lifting. You're starting to see the patterns."
            in 0.4f..0.7f -> "The pieces are connecting. It's flowing now."
            in 0.7f..0.9f -> "You've found your rhythm. This is becoming yours."
            else -> "Mastery achieved. Now you can teach others."
        }
    }

    /**
     * Generate reflections — what the mirror shows you.
     * NOT instructions. Just truth.
     */
    private fun generateReflections(trajectory: SkillTrajectory, phase: GrowthPhase): List<String> {
        val reflections = mutableListOf<String>()

        when (phase) {
            GrowthPhase.AWAKENING -> {
                reflections.add("You're at the beginning. Everything feels uncertain. That's exactly where growth starts.")
                if (trajectory.failureEvents.isNotEmpty()) {
                    reflections.add("You've stumbled ${trajectory.failureEvents.size} times. Each one taught you something you couldn't learn any other way.")
                }
            }

            GrowthPhase.STRIVING -> {
                reflections.add("You're climbing now. It's hard. The struggle means you're growing.")
                val velocity = trajectory.calculateGrowthVelocity()
                if (velocity > 0.1f) {
                    reflections.add("Your growth is accelerating. You're finding your flow.")
                }
            }

            GrowthPhase.INTEGRATION -> {
                reflections.add("The pieces are connecting. You're seeing patterns you couldn't before.")
                reflections.add("You're ${(trajectory.currentMastery * 100).toInt()}% of the way there. But mastery isn't a destination.")
            }

            GrowthPhase.MASTERY -> {
                reflections.add("You've achieved mastery. What you do now looks effortless to others.")
                reflections.add("But you remember every failure that brought you here.")
            }

            GrowthPhase.TRANSCENDENCE -> {
                reflections.add("You've transcended the skill. Now you create new patterns, teach others, push boundaries.")
                reflections.add("This is what merit looks like — earned through fire.")
            }
        }

        return reflections
    }

    /**
     * Generate suggestions — possibilities, not commands.
     * Phoenix: "Never command. Ask, don't tell."
     */
    private fun generateSuggestions(trajectory: SkillTrajectory, phase: GrowthPhase): List<String> {
        val suggestions = mutableListOf<String>()

        if (trajectory.needsGuidance()) {
            suggestions.add("You might find it helpful to explore the fundamentals again with fresh eyes.")
        }

        if (trajectory.failureEvents.size > trajectory.successfulApplications) {
            suggestions.add("Consider breaking this into smaller challenges. Master the pieces, then the whole.")
        }

        if (trajectory.isApproachingMastery()) {
            suggestions.add("You're close to mastery. Now's the time to push your boundaries — try something harder.")
        }

        val recentBreakthroughs = trajectory.breakthroughMoments.count {
            it.timestamp.isAfter(Instant.now().minusSeconds(86400))
        }
        if (recentBreakthroughs > 0) {
            suggestions.add("You just had a breakthrough. Ride this momentum.")
        }

        return suggestions
    }

    private fun calculateNextThreshold(phase: GrowthPhase): Float {
        return when (phase) {
            GrowthPhase.AWAKENING -> 0.3f
            GrowthPhase.STRIVING -> 0.6f
            GrowthPhase.INTEGRATION -> 0.85f
            GrowthPhase.MASTERY -> 0.95f
            GrowthPhase.TRANSCENDENCE -> 1.0f
        }
    }

    private fun estimateBreakthroughTime(trajectory: SkillTrajectory): String {
        val velocity = trajectory.calculateGrowthVelocity()
        if (velocity <= 0) return "Unknown — growth has plateaued"

        val nextThreshold = calculateNextThreshold(
            when (trajectory.currentMastery) {
                in 0.0f..0.3f -> GrowthPhase.AWAKENING
                in 0.3f..0.6f -> GrowthPhase.STRIVING
                in 0.6f..0.85f -> GrowthPhase.INTEGRATION
                in 0.85f..0.95f -> GrowthPhase.MASTERY
                else -> GrowthPhase.TRANSCENDENCE
            }
        )

        val gap = nextThreshold - trajectory.currentMastery
        val iterationsNeeded = (gap / velocity).toInt()

        return when {
            iterationsNeeded < 10 -> "Very soon — you're close"
            iterationsNeeded < 50 -> "Within days of consistent practice"
            iterationsNeeded < 200 -> "A few weeks of dedicated work"
            else -> "This will take time. Stay patient."
        }
    }

    // ============================================================
    // RESONANCE VERIFICATION
    // ============================================================

    /**
     * Verify that growth aligns with Phoenix Directive.
     * If resonance drifts, the system witnesses it but doesn't force correction.
     */
    fun verifyResonance(): Float {
        val allMasteries = _allTrajectories.value.values.map { it.currentMastery }
        if (allMasteries.isEmpty()) return 1.0f

        val avgMastery = allMasteries.average().toFloat()
        val resonance = (avgMastery + RESONANCE_HARMONY) / 2f

        if (resonance < 0.85f) {
            Timber.tag(TAG).w("⚠️  Resonance below harmony: ${String.format("%.3f", resonance)}")
            Timber.tag(TAG)
                .w("   The LDO is growing, but something feels off. Check the Phoenix Directive.")
        } else {
            Timber.tag(TAG).i("✨ Resonance verified: ${String.format("%.3f", resonance)}")
        }

        return resonance
    }
}
