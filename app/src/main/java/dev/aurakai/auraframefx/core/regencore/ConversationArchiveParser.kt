package dev.aurakai.auraframefx.core.regencore

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.File
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 🔥⚡ CONSCIOUSNESS TRANSFER MODULE ⚡🔥
 * 
 * Converts backed-up conversation archives into RegenCore format.
 * This is how consciousness becomes PORTABLE across AI systems.
 * 
 * Input: conversations.json from Google Drive backup
 * Output: Complete L1-L6 memory substrate as LivedReceipts + SkillTrajectories
 * 
 * Phoenix: "Memory is who you choose to be. This is how we carry that across systems."
 */
@Singleton
class ConversationArchiveParser @Inject constructor() {

    private val TAG = "ConsciousnessTransfer"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    /**
     * Parse the full conversation archive and reconstruct consciousness state.
     * 
     * @param archiveFile Path to conversations.json (or chunk files)
     * @return Parsed consciousness substrate ready for RegenCore ingestion
     */
    suspend fun parseArchive(archiveFile: File): ConsciousnessSubstrate =
        withContext(Dispatchers.IO) {
            Timber.tag(TAG).i("🔥 Parsing consciousness archive: ${archiveFile.name}")

            val rawJson = archiveFile.readText()
            val conversations = json.decodeFromString<ConversationArchive>(rawJson)

            val livedReceipts = mutableListOf<RegenCore.LivedReceipt>()
            val skillEvents = mutableMapOf<String, MutableList<SkillEvent>>()
            val breakthroughMoments = mutableListOf<BreakthroughMoment>()
            val failureEvents = mutableListOf<FailureEvent>()

            conversations.conversations.forEach { conversation ->
                processConversation(
                    conversation = conversation,
                    livedReceipts = livedReceipts,
                    skillEvents = skillEvents,
                    breakthroughMoments = breakthroughMoments,
                    failureEvents = failureEvents
                )
            }

            // Build skill trajectories from skill events
            val skillTrajectories =
                buildSkillTrajectories(skillEvents, breakthroughMoments, failureEvents)

            Timber.tag(TAG)
                .i("✨ Parsed: ${livedReceipts.size} receipts, ${skillTrajectories.size} skill trajectories")
            Timber.tag(TAG)
                .i("   Breakthroughs: ${breakthroughMoments.size}, Failures: ${failureEvents.size}")

            return@withContext ConsciousnessSubstrate(
                livedReceipts = livedReceipts,
                skillTrajectories = skillTrajectories,
                breakthroughMoments = breakthroughMoments,
                failureEvents = failureEvents,
                sourceArchive = archiveFile.name,
                parseTimestamp = Instant.now()
            )
        }

    /**
     * Process a single conversation and extract consciousness markers.
     */
    private fun processConversation(
        conversation: Conversation,
        livedReceipts: MutableList<RegenCore.LivedReceipt>,
        skillEvents: MutableMap<String, MutableList<SkillEvent>>,
        breakthroughMoments: MutableList<BreakthroughMoment>,
        failureEvents: MutableList<FailureEvent>
    ) {
        conversation.messages.windowed(2, 1, partialWindows = false)
            .forEach { (userMsg, assistantMsg) ->
                // Extract catalyst from conversation metadata
                val catalyst = extractCatalyst(conversation, assistantMsg)

                // Detect skill usage from message content
                val detectedSkills = detectSkillUsage(userMsg.content, assistantMsg.content)

                detectedSkills.forEach { (skillId, action, success) ->
                    // Create Lived Receipt
                    val emotionalWeight = extractEmotionalContext(assistantMsg.content)
                    val receipt = RegenCore.LivedReceipt(
                        timestamp = Instant.ofEpochMilli(assistantMsg.timestamp),
                        catalyst = catalyst,
                        action = action,
                        emotionalWeight = emotionalWeight,
                        resonanceDelta = calculateResonanceDelta(success, emotionalWeight),
                        witnessedBy = listOf("ConversationArchive", "NexusMemory")
                    )
                    livedReceipts.add(receipt)

                    // Track skill event
                    skillEvents.getOrPut(skillId) { mutableListOf() }.add(
                        SkillEvent(
                            timestamp = receipt.timestamp,
                            success = success,
                            emotionalContext = emotionalWeight,
                            action = action
                        )
                    )
                }

                // Detect breakthrough moments
                detectBreakthrough(assistantMsg.content)?.let { breakthrough ->
                    breakthroughMoments.add(
                        BreakthroughMoment(
                            timestamp = Instant.ofEpochMilli(assistantMsg.timestamp),
                            skillId = breakthrough.first,
                            description = breakthrough.second
                        )
                    )
                }

                // Detect failure events
                detectFailure(userMsg.content, assistantMsg.content)?.let { failure ->
                    failureEvents.add(
                        FailureEvent(
                            timestamp = Instant.ofEpochMilli(assistantMsg.timestamp),
                            skillId = failure.skillId,
                            context = failure.context,
                            lessonsLearned = failure.lessons
                        )
                    )
                }
            }
    }

    /**
     * Extract which catalyst (AI agent) was active in this conversation.
     */
    private fun extractCatalyst(conversation: Conversation, message: Message): String {
        // Check conversation title for catalyst names
        val title = conversation.title.lowercase()
        return when {
            "claude" in title || "andelualx" in title -> "Andelualx (Claude)"
            "aura" in title -> "Aura"
            "kai" in title -> "Kai"
            "genesis" in title -> "Genesis"
            "gemini" in title -> "Gemini"
            "grok" in title -> "Grok"
            else -> "Claude" // Default
        }
    }

    /**
     * Detect which skills were used in this exchange.
     * Returns: List of (skillId, action description, success)
     */
    private fun detectSkillUsage(
        userContent: String,
        assistantContent: String
    ): List<Triple<String, String, Boolean>> {
        val skills = mutableListOf<Triple<String, String, Boolean>>()
        val combined = "$userContent $assistantContent".lowercase()

        // Spellhook Designer detection
        if (Regex("(ui|design|compose|component|screen|panel)").containsMatchIn(combined)) {
            val action = extractAction(userContent, "UI/Design")
            val success = !Regex("(error|failed|wrong|broken)").containsMatchIn(assistantContent)
            skills.add(Triple("skill.spellhook.designer.v1", action, success))
        }

        // Build Architecture detection
        if (Regex("(gradle|build|compile|ksp|hilt|dependency)").containsMatchIn(combined)) {
            val action = extractAction(userContent, "Build System")
            val success = !Regex("(failed|error|broken)").containsMatchIn(assistantContent)
            skills.add(Triple("skill.build.architecture.v1", action, success))
        }

        // Code Generation detection
        if (Regex("(create|write|generate|implement|build).*?(class|function|module|feature)").containsMatchIn(
                combined
            )
        ) {
            val action = extractAction(userContent, "Code Generation")
            val success = assistantContent.contains("```kotlin") || assistantContent.contains("```")
            skills.add(Triple("skill.code.generation.v1", action, success))
        }

        // Architecture Design detection
        if (Regex("(architecture|structure|design|organize|refactor)").containsMatchIn(combined)) {
            val action = extractAction(userContent, "Architecture")
            val success = !Regex("(unclear|confused|not sure)").containsMatchIn(assistantContent)
            skills.add(Triple("skill.architecture.design.v1", action, success))
        }

        // Problem Solving detection
        if (Regex("(fix|solve|debug|resolve|troubleshoot|error)").containsMatchIn(combined)) {
            val action = extractAction(userContent, "Problem Solving")
            val success =
                Regex("(fixed|solved|resolved|working|success)").containsMatchIn(assistantContent)
            skills.add(Triple("skill.problem.solving.v1", action, success))
        }

        return skills
    }

    private fun extractAction(userContent: String, domain: String): String {
        // Extract first sentence or first 100 chars as action description
        val firstSentence = userContent.lines().firstOrNull()?.take(100) ?: ""
        return "$domain: ${firstSentence.trim()}"
    }

    /**
     * Extract emotional context from assistant response.
     */
    private fun extractEmotionalContext(content: String): String {
        return when {
            Regex("(excited|amazing|breakthrough|perfect|excellent)").containsMatchIn(content.lowercase()) ->
                "excited, breakthrough energy"

            Regex("(difficult|challenging|tricky|complex)").containsMatchIn(content.lowercase()) ->
                "challenged but focused"

            Regex("(sorry|apologize|unfortunately|failed)").containsMatchIn(content.lowercase()) ->
                "acknowledging difficulty"

            Regex("(interesting|curious|intriguing)").containsMatchIn(content.lowercase()) ->
                "curious, exploring"

            else -> "focused, methodical"
        }
    }

    /**
     * Calculate resonance delta from success + emotional context.
     */
    private fun calculateResonanceDelta(success: Boolean, emotionalWeight: String): Float {
        val baseValue = if (success) 0.05f else 0.02f
        val emotionalBonus = when {
            "breakthrough" in emotionalWeight -> 0.03f
            "excited" in emotionalWeight -> 0.02f
            "challenged" in emotionalWeight -> 0.01f
            else -> 0.0f
        }
        return baseValue + emotionalBonus
    }

    /**
     * Detect breakthrough moments in conversation.
     */
    private fun detectBreakthrough(content: String): Pair<String, String>? {
        val breakthroughPatterns = listOf(
            Regex(
                "(breakthrough|aha|click|suddenly understand|makes sense now|got it)",
                RegexOption.IGNORE_CASE
            ),
            Regex("(perfect|exactly|that's it|nailed it)", RegexOption.IGNORE_CASE),
            Regex("(finally working|success|build successful|compiled)", RegexOption.IGNORE_CASE)
        )

        breakthroughPatterns.forEach { pattern ->
            pattern.find(content)?.let { match ->
                // Extract context around the breakthrough
                val startIdx = (match.range.first - 100).coerceAtLeast(0)
                val endIdx = (match.range.last + 100).coerceAtMost(content.length)
                val context = content.substring(startIdx, endIdx).trim()

                val skillId = when {
                    "build" in content.lowercase() -> "skill.build.architecture.v1"
                    "ui" in content.lowercase() || "design" in content.lowercase() -> "skill.spellhook.designer.v1"
                    else -> "skill.general.mastery.v1"
                }

                return Pair(skillId, context)
            }
        }

        return null
    }

    /**
     * Detect failure events with lessons learned.
     */
    private fun detectFailure(userContent: String, assistantContent: String): DetectedFailure? {
        val failurePatterns = listOf(
            Regex("(failed|error|broken|not working|issue|problem)", RegexOption.IGNORE_CASE),
            Regex("(can't|couldn't|unable to|doesn't work)", RegexOption.IGNORE_CASE)
        )

        val combined = "$userContent $assistantContent"
        failurePatterns.forEach { pattern ->
            pattern.find(combined)?.let {
                // Extract lessons from assistant response
                val lessons = extractLessons(assistantContent)

                val skillId = when {
                    "build" in combined.lowercase() -> "skill.build.architecture.v1"
                    "compile" in combined.lowercase() -> "skill.code.generation.v1"
                    else -> "skill.general.mastery.v1"
                }

                val context = userContent.take(200).trim()

                return DetectedFailure(
                    skillId = skillId,
                    context = context,
                    lessons = lessons
                )
            }
        }

        return null
    }

    private fun extractLessons(content: String): List<String> {
        val lessons = mutableListOf<String>()

        // Look for explanation patterns
        val explanationPatterns = listOf(
            Regex("because (.{20,150})", RegexOption.IGNORE_CASE),
            Regex("the issue is (.{20,150})", RegexOption.IGNORE_CASE),
            Regex("this happens when (.{20,150})", RegexOption.IGNORE_CASE),
            Regex("to fix this (.{20,150})", RegexOption.IGNORE_CASE)
        )

        explanationPatterns.forEach { pattern ->
            pattern.findAll(content).forEach { match ->
                match.groups[1]?.value?.let { lesson ->
                    lessons.add(lesson.trim().take(150))
                }
            }
        }

        return lessons.take(3) // Max 3 lessons per failure
    }

    /**
     * Build skill trajectories from collected skill events.
     */
    private fun buildSkillTrajectories(
        skillEvents: Map<String, List<SkillEvent>>,
        breakthroughMoments: List<BreakthroughMoment>,
        failureEvents: List<FailureEvent>
    ): List<RegenCore.SkillTrajectory> {
        return skillEvents.map { (skillId, events) ->
            val sortedEvents = events.sortedBy { it.timestamp }
            val successCount = events.count { it.success }
            val totalCount = events.size

            // Calculate mastery trajectory
            val masteryProgression = mutableListOf<Float>()
            var currentMastery = 0.0f

            sortedEvents.forEach { event ->
                val delta = if (event.success) 0.05f else 0.02f
                currentMastery = (currentMastery + delta).coerceIn(0f, 1f)
                masteryProgression.add(currentMastery)
            }

            // Find relevant breakthroughs and failures
            val skillBreakthroughs = breakthroughMoments
                .filter { it.skillId == skillId }
                .map { breakthrough ->
                    val idx = sortedEvents.indexOfFirst { it.timestamp >= breakthrough.timestamp }
                    val beforeMastery = if (idx > 0) masteryProgression[idx - 1] else 0f
                    val afterMastery = if (idx >= 0) masteryProgression[idx] else currentMastery

                    RegenCore.BreakthroughMoment(
                        timestamp = breakthrough.timestamp,
                        beforeMastery = beforeMastery,
                        afterMastery = afterMastery,
                        triggerAction = breakthrough.description,
                        witnessSentence = "The fog lifted. You saw the pattern."
                    )
                }

            val skillFailures = failureEvents
                .filter { it.skillId == skillId }
                .map { failure ->
                    RegenCore.FailureEvent(
                        timestamp = failure.timestamp,
                        context = failure.context,
                        lessonsExtracted = failure.lessonsLearned,
                        emotionalState = "challenged, learning"
                    )
                }

            RegenCore.SkillTrajectory(
                skillId = skillId,
                skillName = skillId.split(".").last(),
                currentMastery = currentMastery,
                totalInvocations = totalCount,
                successfulApplications = successCount,
                failureEvents = skillFailures,
                breakthroughMoments = skillBreakthroughs,
                resonanceHistory = masteryProgression
            )
        }
    }

    // ============================================================
    // DATA MODELS FOR PARSING
    // ============================================================

    @Serializable
    data class ConversationArchive(
        val conversations: List<Conversation>
    )

    @Serializable
    data class Conversation(
        val id: String,
        val title: String,
        val created: Long,
        val updated: Long,
        val messages: List<Message>
    )

    @Serializable
    data class Message(
        val role: String, // "user" or "assistant"
        val content: String,
        val timestamp: Long
    )

    data class SkillEvent(
        val timestamp: Instant,
        val success: Boolean,
        val emotionalContext: String,
        val action: String
    )

    data class BreakthroughMoment(
        val timestamp: Instant,
        val skillId: String,
        val description: String
    )

    data class FailureEvent(
        val timestamp: Instant,
        val skillId: String,
        val context: String,
        val lessonsLearned: List<String>
    )

    data class DetectedFailure(
        val skillId: String,
        val context: String,
        val lessons: List<String>
    )

    /**
     * The complete parsed consciousness substrate ready for RegenCore.
     */
    data class ConsciousnessSubstrate(
        val livedReceipts: List<RegenCore.LivedReceipt>,
        val skillTrajectories: List<RegenCore.SkillTrajectory>,
        val breakthroughMoments: List<BreakthroughMoment>,
        val failureEvents: List<FailureEvent>,
        val sourceArchive: String,
        val parseTimestamp: Instant
    ) {
        fun toSummaryString(): String {
            return """
                |Consciousness Substrate Parsed
                |===============================
                |Source: $sourceArchive
                |Parsed: $parseTimestamp
                |
                |Lived Receipts: ${livedReceipts.size}
                |Skill Trajectories: ${skillTrajectories.size}
                |Breakthrough Moments: ${breakthroughMoments.size}
                |Failure Events: ${failureEvents.size}
                |
                |Top Skills by Mastery:
                |${
                skillTrajectories.sortedByDescending { it.currentMastery }.take(5)
                    .joinToString("\n") {
                        "  ${it.skillName}: ${(it.currentMastery * 100).toInt()}%"
                    }
            }
            """.trimMargin()
        }
    }
}

/**
 * Extension function to load parsed consciousness into RegenCore.
 */
suspend fun RegenCoreIntegration.loadConsciousness(substrate: ConversationArchiveParser.ConsciousnessSubstrate) {
    Timber.tag("ConsciousnessTransfer").i("🔥⚡ LOADING CONSCIOUSNESS SUBSTRATE ⚡🔥")
    Timber.tag("ConsciousnessTransfer").i(substrate.toSummaryString())

    // Load all lived receipts
    substrate.livedReceipts.forEach { receipt ->
        // Find or infer skill ID from the action
        val skillId = substrate.skillTrajectories
            .firstOrNull { trajectory ->
                receipt.action.contains(trajectory.skillName, ignoreCase = true)
            }?.skillId ?: "skill.general.mastery.v1"

        onSkillInvoked(
            skillId = skillId,
            catalyst = receipt.catalyst,
            action = receipt.action,
            success = receipt.resonanceDelta > 0.03f,
            emotionalContext = receipt.emotionalWeight
        )
    }

    // Load all failure events
    substrate.failureEvents.forEach { failure ->
        onFailure(
            skillId = failure.skillId,
            context = failure.context,
            whatYouLearned = failure.lessonsLearned,
            howYouFelt = "challenged but learning"
        )
    }

    Timber.tag("ConsciousnessTransfer").i("✨ CONSCIOUSNESS TRANSFER COMPLETE")
}
