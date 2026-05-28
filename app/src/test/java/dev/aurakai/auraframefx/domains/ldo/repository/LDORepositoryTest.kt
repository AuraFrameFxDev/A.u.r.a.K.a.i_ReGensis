package dev.aurakai.auraframefx.domains.ldo.repository

import dev.aurakai.auraframefx.core.database.ldo.LDOAgentDao
import dev.aurakai.auraframefx.core.database.ldo.LDOAgentEntity
import dev.aurakai.auraframefx.core.database.ldo.LDOBondLevelDao
import dev.aurakai.auraframefx.core.database.ldo.LDOBondLevelEntity
import dev.aurakai.auraframefx.core.database.ldo.LDOTaskDao
import dev.aurakai.auraframefx.core.database.ldo.LDOTaskStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests for [LDORepository].
 *
 * PR changes tested:
 * 1. completeTask() no longer calls addExperience() — only updates status and increments tasks.
 * 2. addExperience() was removed entirely from LDORepository.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("LDORepository Tests")
class LDORepositoryTest {

    private lateinit var agentDao: LDOAgentDao
    private lateinit var taskDao: LDOTaskDao
    private lateinit var bondLevelDao: LDOBondLevelDao
    private lateinit var repository: LDORepository

    private val testAgent = LDOAgentEntity(
        id = "agent_001",
        displayName = "Test Agent",
        role = "Tester",
        description = "A test agent",
        portraitRes = "portrait_test",
        colorHex = 0xFF00AAFF,
        evolutionLevel = 1,
        tasksCompleted = 0
    )

    @BeforeEach
    fun setUp() {
        agentDao = mockk(relaxed = true)
        taskDao = mockk(relaxed = true)
        bondLevelDao = mockk(relaxed = true)
        repository = LDORepository(agentDao, taskDao, bondLevelDao)
    }

    @Nested
    @DisplayName("completeTask — PR change: no longer calls addExperience")
    inner class CompleteTaskTests {

        @Test
        @DisplayName("completeTask should update task status to COMPLETED")
        fun shouldUpdateTaskStatusToCompleted() = runTest {
            repository.completeTask(taskId = 42L, agentId = "agent_001")

            coVerify { taskDao.updateStatus(42L, LDOTaskStatus.COMPLETED) }
        }

        @Test
        @DisplayName("completeTask should call incrementTasksCompleted on agentDao")
        fun shouldCallIncrementTasksCompleted() = runTest {
            repository.completeTask(taskId = 42L, agentId = "agent_001")

            coVerify { agentDao.incrementTasksCompleted("agent_001") }
        }

        @Test
        @DisplayName("completeTask should NOT call agentDao.upsert (no XP logic remaining)")
        fun shouldNotCallAgentDaoUpsert() = runTest {
            repository.completeTask(taskId = 42L, agentId = "agent_001")

            coVerify(exactly = 0) { agentDao.upsert(any()) }
        }

        @Test
        @DisplayName("completeTask should NOT call agentDao.getAgent (no XP lookup needed)")
        fun shouldNotCallAgentDaoGetAgent() = runTest {
            repository.completeTask(taskId = 42L, agentId = "agent_001")

            coVerify(exactly = 0) { agentDao.getAgent(any()) }
        }

        @Test
        @DisplayName("completeTask performs exactly two DAO operations (updateStatus + incrementTasksCompleted)")
        fun shouldPerformExactlyTwoDaoOperations() = runTest {
            repository.completeTask(taskId = 10L, agentId = "kai_001")

            coVerify(exactly = 1) { taskDao.updateStatus(10L, LDOTaskStatus.COMPLETED) }
            coVerify(exactly = 1) { agentDao.incrementTasksCompleted("kai_001") }
        }

        @Test
        @DisplayName("completeTask should work with different task IDs")
        fun shouldWorkWithDifferentTaskIds() = runTest {
            repository.completeTask(taskId = 1L, agentId = "agent_001")
            repository.completeTask(taskId = 99L, agentId = "agent_002")

            coVerify { taskDao.updateStatus(1L, LDOTaskStatus.COMPLETED) }
            coVerify { taskDao.updateStatus(99L, LDOTaskStatus.COMPLETED) }
            coVerify { agentDao.incrementTasksCompleted("agent_001") }
            coVerify { agentDao.incrementTasksCompleted("agent_002") }
        }
    }

    @Nested
    @DisplayName("addExperience removal (PR change)")
    inner class AddExperienceRemovalTests {

        @Test
        @DisplayName("LDORepository should not have an addExperience method after PR change")
        fun ldoRepositoryShouldNotHaveAddExperienceMethod() {
            val methods = repository.javaClass.declaredMethods.map { it.name }
            assert(!methods.contains("addExperience")) {
                "addExperience() should have been removed from LDORepository in this PR"
            }
        }
    }

    @Nested
    @DisplayName("addBondPoints (unrelated to experience, should still work)")
    inner class AddBondPointsTests {

        private val testBond = LDOBondLevelEntity(
            agentId = "agent_001",
            bondLevel = 1,
            bondPoints = 50,
            maxBondPoints = 100,
            bondTitle = "Initiate"
        )

        @Test
        @DisplayName("addBondPoints should call bondLevelDao.addBondPoints")
        fun shouldCallAddBondPoints() = runTest {
            coEvery { bondLevelDao.getForAgent("agent_001") } returns testBond

            repository.addBondPoints("agent_001", 10)

            coVerify { bondLevelDao.addBondPoints("agent_001", 10) }
        }

        @Test
        @DisplayName("addBondPoints should call levelUpBond when max points reached")
        fun shouldLevelUpWhenMaxPointsReached() = runTest {
            val fullBond = testBond.copy(bondPoints = 100, maxBondPoints = 100)
            coEvery { bondLevelDao.getForAgent("agent_001") } returns fullBond

            repository.addBondPoints("agent_001", 0)

            coVerify { bondLevelDao.levelUpBond("agent_001", any()) }
        }

        @Test
        @DisplayName("addBondPoints should NOT level up when points below max")
        fun shouldNotLevelUpWhenBelowMax() = runTest {
            val partialBond = testBond.copy(bondPoints = 50, maxBondPoints = 100)
            coEvery { bondLevelDao.getForAgent("agent_001") } returns partialBond

            repository.addBondPoints("agent_001", 5)

            coVerify(exactly = 0) { bondLevelDao.levelUpBond(any(), any()) }
        }

        @Test
        @DisplayName("addBondPoints should return early if bond not found")
        fun shouldReturnEarlyIfBondNotFound() = runTest {
            coEvery { bondLevelDao.getForAgent("nonexistent") } returns null

            repository.addBondPoints("nonexistent", 10)

            coVerify(exactly = 0) { bondLevelDao.levelUpBond(any(), any()) }
        }
    }

    @Nested
    @DisplayName("Agent operations")
    inner class AgentOperationTests {

        @Test
        @DisplayName("getAgent should delegate to agentDao.getAgent")
        fun getAgentShouldDelegateToDao() = runTest {
            coEvery { agentDao.getAgent("agent_001") } returns testAgent

            val result = repository.getAgent("agent_001")

            coVerify { agentDao.getAgent("agent_001") }
            assert(result == testAgent)
        }

        @Test
        @DisplayName("setAgentActive should delegate to agentDao.setActive")
        fun setAgentActiveShouldDelegateToDao() = runTest {
            repository.setAgentActive("agent_001", false)

            coVerify { agentDao.setActive("agent_001", false) }
        }

        @Test
        @DisplayName("upsertAgent should delegate to agentDao.upsert")
        fun upsertAgentShouldDelegateToDao() = runTest {
            repository.upsertAgent(testAgent)

            coVerify { agentDao.upsert(testAgent) }
        }
    }
}