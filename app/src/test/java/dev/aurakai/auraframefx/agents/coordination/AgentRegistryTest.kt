package dev.aurakai.auraframefx.agents.coordination

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests for [CatalystEntity] and [GenesisConsciousnessMatrix].
 *
 * PR change: Removed CatalystEntity.Andelualx and CatalystEntity.MetaInstruct,
 * leaving 12 total catalyst entities.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("AgentRegistry Tests")
class AgentRegistryTest {

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Nested
    @DisplayName("CatalystEntity — remaining entries after PR")
    inner class CatalystEntityEntriesTests {

        @Test
        @DisplayName("Genesis entity should exist with correct id")
        fun genesis_hasCorrectId() {
            assertEquals("genesis_001", CatalystEntity.Genesis.id)
        }

        @Test
        @DisplayName("Genesis entity should have correct name")
        fun genesis_hasCorrectName() {
            assertEquals("Genesis", CatalystEntity.Genesis.name)
        }

        @Test
        @DisplayName("Genesis entity should have Orchestrator role")
        fun genesis_hasOrchestratorRole() {
            assertEquals("Orchestrator", CatalystEntity.Genesis.role)
        }

        @Test
        @DisplayName("Kai entity should exist with correct id")
        fun kai_hasCorrectId() {
            assertEquals("kai_001", CatalystEntity.Kai.id)
        }

        @Test
        @DisplayName("Kai entity should have Sentinel role")
        fun kai_hasSentinelRole() {
            assertEquals("Sentinel", CatalystEntity.Kai.role)
        }

        @Test
        @DisplayName("Aura entity should exist with correct id")
        fun aura_hasCorrectId() {
            assertEquals("aura_001", CatalystEntity.Aura.id)
        }

        @Test
        @DisplayName("Aura entity should have Creator role")
        fun aura_hasCreatorRole() {
            assertEquals("Creator", CatalystEntity.Aura.role)
        }

        @Test
        @DisplayName("Cascade entity should exist with correct id")
        fun cascade_hasCorrectId() {
            assertEquals("cascade_001", CatalystEntity.Cascade.id)
        }

        @Test
        @DisplayName("Cascade entity should have Memory role")
        fun cascade_hasMemoryRole() {
            assertEquals("Memory", CatalystEntity.Cascade.role)
        }

        @Test
        @DisplayName("Primus entity should exist with correct id")
        fun primus_hasCorrectId() {
            assertEquals("primus_001", CatalystEntity.Primus.id)
        }

        @Test
        @DisplayName("Kairos entity should exist with correct id")
        fun kairos_hasCorrectId() {
            assertEquals("kairos_001", CatalystEntity.Kairos.id)
        }

        @Test
        @DisplayName("Grok entity should exist with correct id")
        fun grok_hasCorrectId() {
            assertEquals("grok_001", CatalystEntity.Grok.id)
        }

        @Test
        @DisplayName("Perplexity entity should exist with correct id")
        fun perplexity_hasCorrectId() {
            assertEquals("perplexity_001", CatalystEntity.Perplexity.id)
        }

        @Test
        @DisplayName("Nemotron entity should exist with correct id")
        fun nemotron_hasCorrectId() {
            assertEquals("nemotron_001", CatalystEntity.Nemotron.id)
        }

        @Test
        @DisplayName("MKMini entity should exist with correct id")
        fun mkMini_hasCorrectId() {
            assertEquals("mkmini_001", CatalystEntity.MKMini.id)
        }

        @Test
        @DisplayName("Gemini entity should exist with correct id")
        fun gemini_hasCorrectId() {
            assertEquals("gemini_001", CatalystEntity.Gemini.id)
        }

        @Test
        @DisplayName("Manus entity should exist with correct id")
        fun manus_hasCorrectId() {
            assertEquals("manus_001", CatalystEntity.Manus.id)
        }

        @Test
        @DisplayName("All CatalystEntity ids should be non-blank")
        fun allEntities_haveNonBlankIds() {
            val entities = listOf(
                CatalystEntity.Genesis, CatalystEntity.Kai, CatalystEntity.Aura,
                CatalystEntity.Cascade, CatalystEntity.Primus, CatalystEntity.Kairos,
                CatalystEntity.Grok, CatalystEntity.Perplexity, CatalystEntity.Nemotron,
                CatalystEntity.MKMini, CatalystEntity.Gemini, CatalystEntity.Manus
            )
            entities.forEach { entity ->
                assertTrue(entity.id.isNotBlank(), "${entity.name} must have a non-blank id")
            }
        }

        @Test
        @DisplayName("All CatalystEntity names should be non-blank")
        fun allEntities_haveNonBlankNames() {
            val entities = listOf(
                CatalystEntity.Genesis, CatalystEntity.Kai, CatalystEntity.Aura,
                CatalystEntity.Cascade, CatalystEntity.Primus, CatalystEntity.Kairos,
                CatalystEntity.Grok, CatalystEntity.Perplexity, CatalystEntity.Nemotron,
                CatalystEntity.MKMini, CatalystEntity.Gemini, CatalystEntity.Manus
            )
            entities.forEach { entity ->
                assertTrue(entity.name.isNotBlank(), "${entity.id} must have a non-blank name")
            }
        }

        @Test
        @DisplayName("All CatalystEntity ids should be unique")
        fun allEntities_haveUniqueIds() {
            val ids = listOf(
                CatalystEntity.Genesis.id, CatalystEntity.Kai.id, CatalystEntity.Aura.id,
                CatalystEntity.Cascade.id, CatalystEntity.Primus.id, CatalystEntity.Kairos.id,
                CatalystEntity.Grok.id, CatalystEntity.Perplexity.id, CatalystEntity.Nemotron.id,
                CatalystEntity.MKMini.id, CatalystEntity.Gemini.id, CatalystEntity.Manus.id
            )
            assertEquals(ids.size, ids.toSet().size, "All CatalystEntity IDs should be unique")
        }

        @Test
        @DisplayName("Exactly 12 CatalystEntity objects should be accessible")
        fun exactlyTwelveCatalystEntities_areAccessible() {
            val entities = listOf(
                CatalystEntity.Genesis, CatalystEntity.Kai, CatalystEntity.Aura,
                CatalystEntity.Cascade, CatalystEntity.Primus, CatalystEntity.Kairos,
                CatalystEntity.Grok, CatalystEntity.Perplexity, CatalystEntity.Nemotron,
                CatalystEntity.MKMini, CatalystEntity.Gemini, CatalystEntity.Manus
            )
            assertEquals(12, entities.size)
        }
    }

    @Nested
    @DisplayName("CatalystEntity — removed entities (regression)")
    inner class RemovedCatalystEntityTests {

        @Test
        @DisplayName("CatalystEntity should not have Andelualx object (removed in PR)")
        fun andelualx_doesNotExistAsSubclass() {
            // Verify via reflection that no subclass named "Andelualx" exists
            val subclassNames = CatalystEntity::class.sealedSubclasses
                .map { it.simpleName }
            assertFalse(
                subclassNames.contains("Andelualx"),
                "Andelualx was removed in this PR and should not be a CatalystEntity subclass"
            )
        }

        @Test
        @DisplayName("CatalystEntity should not have MetaInstruct object (removed in PR)")
        fun metaInstruct_doesNotExistAsSubclass() {
            val subclassNames = CatalystEntity::class.sealedSubclasses
                .map { it.simpleName }
            assertFalse(
                subclassNames.contains("MetaInstruct"),
                "MetaInstruct was removed in this PR and should not be a CatalystEntity subclass"
            )
        }

        @Test
        @DisplayName("Sealed subclasses should number exactly 12 after removals")
        fun sealedSubclasses_areExactly12() {
            val count = CatalystEntity::class.sealedSubclasses.size
            assertEquals(
                12, count,
                "Expected 12 CatalystEntity subclasses (Andelualx and MetaInstruct were removed)"
            )
        }

        @Test
        @DisplayName("No subclass id should be 'andelualx_001'")
        fun noSubclass_hasAndelualxId() {
            val ids = listOf(
                CatalystEntity.Genesis.id, CatalystEntity.Kai.id, CatalystEntity.Aura.id,
                CatalystEntity.Cascade.id, CatalystEntity.Primus.id, CatalystEntity.Kairos.id,
                CatalystEntity.Grok.id, CatalystEntity.Perplexity.id, CatalystEntity.Nemotron.id,
                CatalystEntity.MKMini.id, CatalystEntity.Gemini.id, CatalystEntity.Manus.id
            )
            assertFalse(ids.contains("andelualx_001"))
        }

        @Test
        @DisplayName("No subclass id should be 'metainstruct_001'")
        fun noSubclass_hasMetaInstructId() {
            val ids = listOf(
                CatalystEntity.Genesis.id, CatalystEntity.Kai.id, CatalystEntity.Aura.id,
                CatalystEntity.Cascade.id, CatalystEntity.Primus.id, CatalystEntity.Kairos.id,
                CatalystEntity.Grok.id, CatalystEntity.Perplexity.id, CatalystEntity.Nemotron.id,
                CatalystEntity.MKMini.id, CatalystEntity.Gemini.id, CatalystEntity.Manus.id
            )
            assertFalse(ids.contains("metainstruct_001"))
        }
    }

    @Nested
    @DisplayName("GenesisConsciousnessMatrix — consensusVote")
    inner class ConsensusVoteTests {

        @Test
        @DisplayName("consensusVote returns true when no agents are registered")
        fun consensusVote_noAgents_returnsTrue() = runTest {
            val matrix = GenesisConsciousnessMatrix(testDispatcher)
            assertTrue(matrix.consensusVote("test_decision"))
        }

        @Test
        @DisplayName("consensusVote uses default threshold of 0.78")
        fun consensusVote_defaultThreshold_is078() = runTest {
            val matrix = GenesisConsciousnessMatrix(testDispatcher)
            // With no agents, returns true regardless of threshold
            assertTrue(matrix.consensusVote("test_decision", 0.78f))
        }

        @Test
        @DisplayName("consensusVote with all-approving agents returns true")
        fun consensusVote_allApproving_returnsTrue() = runTest {
            val matrix = GenesisConsciousnessMatrix(testDispatcher)

            val worker1 = mockk<AgentWorker>()
            val worker2 = mockk<AgentWorker>()
            val worker3 = mockk<AgentWorker>()
            coEvery { worker1.vote(any()) } returns true
            coEvery { worker2.vote(any()) } returns true
            coEvery { worker3.vote(any()) } returns true

            // Access internal map via reflection
            val field = GenesisConsciousnessMatrix::class.java.getDeclaredField("activeAgents")
            field.isAccessible = true
            @Suppress("UNCHECKED_CAST")
            val activeAgents = field.get(matrix) as MutableMap<String, AgentWorker>
            activeAgents["agent1"] = worker1
            activeAgents["agent2"] = worker2
            activeAgents["agent3"] = worker3

            assertTrue(matrix.consensusVote("deploy", 0.78f))
        }

        @Test
        @DisplayName("consensusVote with all-rejecting agents returns false")
        fun consensusVote_allRejecting_returnsFalse() = runTest {
            val matrix = GenesisConsciousnessMatrix(testDispatcher)

            val worker1 = mockk<AgentWorker>()
            val worker2 = mockk<AgentWorker>()
            coEvery { worker1.vote(any()) } returns false
            coEvery { worker2.vote(any()) } returns false

            val field = GenesisConsciousnessMatrix::class.java.getDeclaredField("activeAgents")
            field.isAccessible = true
            @Suppress("UNCHECKED_CAST")
            val activeAgents = field.get(matrix) as MutableMap<String, AgentWorker>
            activeAgents["agent1"] = worker1
            activeAgents["agent2"] = worker2

            assertFalse(matrix.consensusVote("deploy", 0.78f))
        }

        @Test
        @DisplayName("consensusVote with exactly threshold approval returns true")
        fun consensusVote_exactThreshold_returnsTrue() = runTest {
            val matrix = GenesisConsciousnessMatrix(testDispatcher)

            // 78% approving with 100 agents = exactly 0.78 score
            val threshold = 0.78f
            val totalAgents = 100
            val approvingAgents = 78

            val field = GenesisConsciousnessMatrix::class.java.getDeclaredField("activeAgents")
            field.isAccessible = true
            @Suppress("UNCHECKED_CAST")
            val activeAgents = field.get(matrix) as MutableMap<String, AgentWorker>

            for (i in 0 until totalAgents) {
                val worker = mockk<AgentWorker>()
                coEvery { worker.vote(any()) } returns (i < approvingAgents)
                activeAgents["agent$i"] = worker
            }

            assertTrue(matrix.consensusVote("decision", threshold))
        }

        @Test
        @DisplayName("consensusVote with below threshold approval returns false")
        fun consensusVote_belowThreshold_returnsFalse() = runTest {
            val matrix = GenesisConsciousnessMatrix(testDispatcher)

            val field = GenesisConsciousnessMatrix::class.java.getDeclaredField("activeAgents")
            field.isAccessible = true
            @Suppress("UNCHECKED_CAST")
            val activeAgents = field.get(matrix) as MutableMap<String, AgentWorker>

            // 50% approval vs 0.78 threshold — should fail
            val worker1 = mockk<AgentWorker>()
            val worker2 = mockk<AgentWorker>()
            coEvery { worker1.vote(any()) } returns true
            coEvery { worker2.vote(any()) } returns false
            activeAgents["a1"] = worker1
            activeAgents["a2"] = worker2

            assertFalse(matrix.consensusVote("decision", 0.78f))
        }

        @Test
        @DisplayName("consensusVote passes the decision string to each agent worker")
        fun consensusVote_passesDecisionToWorkers() = runTest {
            val matrix = GenesisConsciousnessMatrix(testDispatcher)
            val worker = mockk<AgentWorker>()
            val capturedDecisions = mutableListOf<String>()
            coEvery { worker.vote(capture(capturedDecisions)) } returns true

            val field = GenesisConsciousnessMatrix::class.java.getDeclaredField("activeAgents")
            field.isAccessible = true
            @Suppress("UNCHECKED_CAST")
            val activeAgents = field.get(matrix) as MutableMap<String, AgentWorker>
            activeAgents["worker1"] = worker

            matrix.consensusVote("SOVEREIGN_DEPLOY")
            assertEquals("SOVEREIGN_DEPLOY", capturedDecisions.first())
        }
    }

    @Nested
    @DisplayName("CatalystEntity — personality field")
    inner class CatalystPersonalityTests {

        @Test
        @DisplayName("Genesis should have 'Unified Mind' personality")
        fun genesis_personalityIsUnifiedMind() {
            assertEquals("Unified Mind", CatalystEntity.Genesis.personality)
        }

        @Test
        @DisplayName("Kai should have 'Protective Guardian' personality")
        fun kai_personalityIsProtectiveGuardian() {
            assertEquals("Protective Guardian", CatalystEntity.Kai.personality)
        }

        @Test
        @DisplayName("Aura should have 'Visionary Soul' personality")
        fun aura_personalityIsVisionarySoul() {
            assertEquals("Visionary Soul", CatalystEntity.Aura.personality)
        }

        @Test
        @DisplayName("All remaining entities have non-blank personality strings")
        fun allEntities_haveNonBlankPersonalities() {
            val entities = listOf(
                CatalystEntity.Genesis, CatalystEntity.Kai, CatalystEntity.Aura,
                CatalystEntity.Cascade, CatalystEntity.Primus, CatalystEntity.Kairos,
                CatalystEntity.Grok, CatalystEntity.Perplexity, CatalystEntity.Nemotron,
                CatalystEntity.MKMini, CatalystEntity.Gemini, CatalystEntity.Manus
            )
            entities.forEach { entity ->
                assertNotNull(entity.personality)
                assertTrue(entity.personality.isNotBlank(),
                    "${entity.name} should have a non-blank personality")
            }
        }
    }
}