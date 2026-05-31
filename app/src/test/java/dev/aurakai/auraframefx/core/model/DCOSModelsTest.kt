package dev.aurakai.auraframefx.core.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Unit tests for the DCOS orchestration models added in this PR:
 * [Proposal], [Critique], [LDOTask], [AgentActivityEvent], and associated enums.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("DCOS Models Tests")
class DCOSModelsTest {

    // ─────────────────────────────────────────────
    // Proposal.applyCritique
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("Proposal.applyCritique")
    inner class ApplyCritiqueTests {

        private fun makeProposal(critiques: List<Critique> = emptyList()) = Proposal(
            id = "proposal-001",
            agentId = "aura",
            taskId = "task-123",
            content = "Implement feature X",
            reasoning = "Because Y",
            timestamp = 1_000L,
            critiques = critiques,
            resonanceScore = 1.0f
        )

        private fun makeCritique(
            fromAgent: String = "kai",
            feedback: String = "Needs security review",
            score: Float = -0.1f,
            valid: Boolean = true
        ) = Critique(fromAgentId = fromAgent, feedback = feedback, scoreAdjustment = score, isValid = valid)

        @Test
        @DisplayName("applyCritique appends critique to empty list")
        fun `applyCritique appends to empty list`() {
            val proposal = makeProposal()
            val critique = makeCritique()

            val updated = proposal.applyCritique(critique)

            assertEquals(1, updated.critiques.size)
            assertEquals(critique, updated.critiques.first())
        }

        @Test
        @DisplayName("applyCritique appends to existing critiques")
        fun `applyCritique appends to existing critiques`() {
            val firstCritique = makeCritique("kai", "First")
            val proposal = makeProposal(listOf(firstCritique))
            val secondCritique = makeCritique("genesis", "Second")

            val updated = proposal.applyCritique(secondCritique)

            assertEquals(2, updated.critiques.size)
            assertEquals(firstCritique, updated.critiques[0])
            assertEquals(secondCritique, updated.critiques[1])
        }

        @Test
        @DisplayName("applyCritique does not mutate original proposal")
        fun `applyCritique does not mutate original`() {
            val proposal = makeProposal()
            val critique = makeCritique()

            proposal.applyCritique(critique)

            assertTrue(proposal.critiques.isEmpty(), "Original proposal should not be mutated")
        }

        @Test
        @DisplayName("applyCritique preserves all other fields unchanged")
        fun `applyCritique preserves other fields`() {
            val proposal = makeProposal()
            val critique = makeCritique()

            val updated = proposal.applyCritique(critique)

            assertEquals(proposal.id, updated.id)
            assertEquals(proposal.agentId, updated.agentId)
            assertEquals(proposal.taskId, updated.taskId)
            assertEquals(proposal.content, updated.content)
            assertEquals(proposal.reasoning, updated.reasoning)
            assertEquals(proposal.timestamp, updated.timestamp)
            assertEquals(proposal.resonanceScore, updated.resonanceScore)
        }

        @Test
        @DisplayName("multiple sequential applyCritique calls build full history")
        fun `sequential applyCritique builds history`() {
            var proposal = makeProposal()
            val critiques = listOf(
                makeCritique("kai", "Review 1"),
                makeCritique("genesis", "Review 2"),
                makeCritique("cascade", "Review 3")
            )

            for (c in critiques) {
                proposal = proposal.applyCritique(c)
            }

            assertEquals(3, proposal.critiques.size)
            assertEquals(critiques, proposal.critiques)
        }

        @Test
        @DisplayName("applyCritique with invalid critique still appended")
        fun `invalid critique is still appended`() {
            val proposal = makeProposal()
            val invalidCritique = makeCritique(valid = false)

            val updated = proposal.applyCritique(invalidCritique)

            assertEquals(1, updated.critiques.size)
            assertFalse(updated.critiques.first().isValid)
        }
    }

    // ─────────────────────────────────────────────
    // Critique data class
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("Critique")
    inner class CritiqueTests {

        @Test
        @DisplayName("isValid defaults to true")
        fun `isValid defaults to true`() {
            val critique = Critique(fromAgentId = "kai", feedback = "LGTM", scoreAdjustment = 0.1f)
            assertTrue(critique.isValid)
        }

        @Test
        @DisplayName("all fields stored correctly")
        fun `all fields stored correctly`() {
            val critique = Critique(
                fromAgentId = "genesis",
                feedback = "Needs rework",
                scoreAdjustment = -0.5f,
                isValid = false
            )
            assertEquals("genesis", critique.fromAgentId)
            assertEquals("Needs rework", critique.feedback)
            assertEquals(-0.5f, critique.scoreAdjustment)
            assertFalse(critique.isValid)
        }
    }

    // ─────────────────────────────────────────────
    // LDOTask data class
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("LDOTask")
    inner class LDOTaskTests {

        private fun makeTask() = LDOTask(
            id = "task-001",
            title = "Build feature",
            description = "Details here",
            category = TaskCategory.DEVELOPMENT,
            priority = TaskPriority.HIGH
        )

        @Test
        @DisplayName("status defaults to PENDING")
        fun `status defaults to PENDING`() {
            assertEquals(LDOTaskStatus.PENDING, makeTask().status)
        }

        @Test
        @DisplayName("isComplete defaults to false")
        fun `isComplete defaults to false`() {
            assertFalse(makeTask().isComplete)
        }

        @Test
        @DisplayName("isFlashing defaults to false")
        fun `isFlashing defaults to false`() {
            assertFalse(makeTask().isFlashing)
        }

        @Test
        @DisplayName("promptOnDeparture defaults to true")
        fun `promptOnDeparture defaults to true`() {
            assertTrue(makeTask().promptOnDeparture)
        }

        @Test
        @DisplayName("assignedAgentId defaults to null")
        fun `assignedAgentId defaults to null`() {
            assertNull(makeTask().assignedAgentId)
        }

        @Test
        @DisplayName("all required fields stored correctly")
        fun `all required fields stored correctly`() {
            val task = makeTask()
            assertEquals("task-001", task.id)
            assertEquals("Build feature", task.title)
            assertEquals("Details here", task.description)
            assertEquals(TaskCategory.DEVELOPMENT, task.category)
            assertEquals(TaskPriority.HIGH, task.priority)
        }
    }

    // ─────────────────────────────────────────────
    // Enum completeness
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("Enum values")
    inner class EnumTests {

        @Test
        @DisplayName("TaskPriority has all four values")
        fun `TaskPriority has all four values`() {
            val values = TaskPriority.entries
            assertTrue(values.contains(TaskPriority.LOW))
            assertTrue(values.contains(TaskPriority.MEDIUM))
            assertTrue(values.contains(TaskPriority.HIGH))
            assertTrue(values.contains(TaskPriority.CRITICAL))
            assertEquals(4, values.size)
        }

        @Test
        @DisplayName("LDOTaskStatus has all five values")
        fun `LDOTaskStatus has all five values`() {
            val values = LDOTaskStatus.entries
            assertTrue(values.contains(LDOTaskStatus.PENDING))
            assertTrue(values.contains(LDOTaskStatus.IN_PROGRESS))
            assertTrue(values.contains(LDOTaskStatus.COMPLETED))
            assertTrue(values.contains(LDOTaskStatus.FAILED))
            assertTrue(values.contains(LDOTaskStatus.BLOCKED))
            assertEquals(5, values.size)
        }

        @Test
        @DisplayName("ConsensusResult has all four values")
        fun `ConsensusResult has all four values`() {
            val values = ConsensusResult.entries
            assertTrue(values.contains(ConsensusResult.COMMITTED))
            assertTrue(values.contains(ConsensusResult.REJECTED_FOR_REANCHORING))
            assertTrue(values.contains(ConsensusResult.STALEMATE))
            assertTrue(values.contains(ConsensusResult.SOVEREIGN_VETO))
            assertEquals(4, values.size)
        }

        @Test
        @DisplayName("ConsensusPhase has all five phases")
        fun `ConsensusPhase has all five phases`() {
            val values = ConsensusPhase.entries
            assertTrue(values.contains(ConsensusPhase.PROPOSAL))
            assertTrue(values.contains(ConsensusPhase.CRITIQUE))
            assertTrue(values.contains(ConsensusPhase.VOTING))
            assertTrue(values.contains(ConsensusPhase.RESONANCE_CHECK))
            assertTrue(values.contains(ConsensusPhase.COMMIT))
            assertEquals(5, values.size)
        }

        @Test
        @DisplayName("TaskCategory has all nine categories")
        fun `TaskCategory has all nine categories`() {
            val values = TaskCategory.entries
            assertEquals(9, values.size)
            assertTrue(values.contains(TaskCategory.DEVELOPMENT))
            assertTrue(values.contains(TaskCategory.SECURITY))
            assertTrue(values.contains(TaskCategory.CREATIVE))
        }
    }

    // ─────────────────────────────────────────────
    // AgentActivityEvent (DCOS model variant)
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("AgentActivityEvent (DCOSModels)")
    inner class AgentActivityEventTests {

        @Test
        @DisplayName("fields stored correctly")
        fun `fields stored correctly`() {
            val event = AgentActivityEvent(
                agentId = "genesis-001",
                activityType = "processing",
                details = "Analyzing prompt",
                timestamp = 12345L
            )
            assertEquals("genesis-001", event.agentId)
            assertEquals("processing", event.activityType)
            assertEquals("Analyzing prompt", event.details)
            assertEquals(12345L, event.timestamp)
        }

        @Test
        @DisplayName("timestamp defaults to approximately current time")
        fun `timestamp defaults to current time`() {
            val before = System.currentTimeMillis()
            val event = AgentActivityEvent(agentId = "a", activityType = "test", details = "d")
            val after = System.currentTimeMillis()
            assertTrue(event.timestamp in before..after)
        }

        @Test
        @DisplayName("two events with same fields are equal")
        fun `equal events`() {
            val e1 = AgentActivityEvent("a", "t", "d", 100L)
            val e2 = AgentActivityEvent("a", "t", "d", 100L)
            assertEquals(e1, e2)
        }
    }

    // ─────────────────────────────────────────────
    // Proposal defaults and regression
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("Proposal defaults")
    inner class ProposalDefaultsTests {

        @Test
        @DisplayName("critiques defaults to emptyList")
        fun `critiques defaults to emptyList`() {
            val proposal = Proposal(
                id = "p1",
                agentId = "aura",
                taskId = "t1",
                content = "content",
                reasoning = "reason"
            )
            assertTrue(proposal.critiques.isEmpty())
        }

        @Test
        @DisplayName("resonanceScore defaults to 1.0f")
        fun `resonanceScore defaults to 1`() {
            val proposal = Proposal("p", "a", "t", "c", "r")
            assertEquals(1.0f, proposal.resonanceScore)
        }

        @Test
        @DisplayName("non-null required fields stored")
        fun `required fields stored`() {
            val proposal = Proposal("p", "a", "t", "c", "r")
            assertNotNull(proposal.id)
            assertNotNull(proposal.content)
        }
    }
}
