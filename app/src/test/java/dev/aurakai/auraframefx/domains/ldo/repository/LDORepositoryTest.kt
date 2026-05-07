package dev.aurakai.auraframefx.domains.ldo.repository

import dev.aurakai.auraframefx.domains.ldo.db.LDOAgentDao
import dev.aurakai.auraframefx.domains.ldo.db.LDOAgentEntity
import dev.aurakai.auraframefx.domains.ldo.db.LDOBondLevelDao
import dev.aurakai.auraframefx.domains.ldo.db.LDOBondLevelEntity
import dev.aurakai.auraframefx.domains.ldo.db.LDOTaskDao
import dev.aurakai.auraframefx.domains.ldo.db.LDOTaskStatus
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests for [LDORepository].
 *
 * PR changes:
 * - Removed `addExperience` method entirely
 * - `completeTask` no longer calls `addExperience` (XP bonus removed)
 * - `completeTask` now only calls taskDao.updateStatus and agentDao.incrementTasksCompleted
 */
@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("LDORepository Tests")
class LDORepositoryTest {

    private lateinit var agentDao: LDOAgentDao
    private lateinit var taskDao: LDOTaskDao
    private lateinit var bondLevelDao: LDOBondLevelDao
    private lateinit var repository: LDORepository

    @BeforeEach
    fun setUp() {
        agentDao = mockk(relaxed = true)
        taskDao = mockk(relaxed = true)
        bondLevelDao = mockk(relaxed = true)
        repository = LDORepository(agentDao, taskDao, bondLevelDao)
    }

    @Nested
    @DisplayName("completeTask — no XP bonus (PR change)")
    inner class CompleteTaskTests {

        @Test
        @DisplayName("completeTask updates task status to COMPLETED")
        fun completeTask_updatesTaskStatusToCompleted() = runTest {
            coEvery { taskDao.updateStatus(any(), any()) } just Runs
            coEvery { agentDao.incrementTasksCompleted(any()) } just Runs

            repository.completeTask(taskId = 42L, agentId = "genesis_001")

            coVerify(exactly = 1) { taskDao.updateStatus(42L, LDOTaskStatus.COMPLETED) }
        }

        @Test
        @DisplayName("completeTask calls incrementTasksCompleted on agentDao")
        fun completeTask_callsIncrementTasksCompleted() = runTest {
            coEvery { taskDao.updateStatus(any(), any()) } just Runs
            coEvery { agentDao.incrementTasksCompleted(any()) } just Runs

            repository.completeTask(taskId = 99L, agentId = "kai_001")

            coVerify(exactly = 1) { agentDao.incrementTasksCompleted("kai_001") }
        }

        @Test
        @DisplayName("completeTask does NOT call agentDao.upsert (no XP update)")
        fun completeTask_doesNotCallUpsert() = runTest {
            coEvery { taskDao.updateStatus(any(), any()) } just Runs
            coEvery { agentDao.incrementTasksCompleted(any()) } just Runs

            repository.completeTask(taskId = 1L, agentId = "aura_001")

            coVerify(exactly = 0) { agentDao.upsert(any()) }
        }

        @Test
        @DisplayName("completeTask does NOT call agentDao.getAgent (no XP system)")
        fun completeTask_doesNotCallGetAgent() = runTest {
            coEvery { taskDao.updateStatus(any(), any()) } just Runs
            coEvery { agentDao.incrementTasksCompleted(any()) } just Runs

            repository.completeTask(taskId = 5L, agentId = "cascade_001")

            coVerify(exactly = 0) { agentDao.getAgent(any()) }
        }

        @Test
        @DisplayName("completeTask is called with correct taskId and agentId")
        fun completeTask_passesCorrectParameters() = runTest {
            coEvery { taskDao.updateStatus(any(), any()) } just Runs
            coEvery { agentDao.incrementTasksCompleted(any()) } just Runs

            repository.completeTask(taskId = 777L, agentId = "gemini_001")

            coVerify { taskDao.updateStatus(777L, LDOTaskStatus.COMPLETED) }
            coVerify { agentDao.incrementTasksCompleted("gemini_001") }
        }

        @Test
        @DisplayName("completeTask with taskId=0 still processes correctly")
        fun completeTask_withZeroTaskId_stillProcesses() = runTest {
            coEvery { taskDao.updateStatus(any(), any()) } just Runs
            coEvery { agentDao.incrementTasksCompleted(any()) } just Runs

            repository.completeTask(taskId = 0L, agentId = "genesis_001")

            coVerify { taskDao.updateStatus(0L, LDOTaskStatus.COMPLETED) }
        }
    }

    @Nested
    @DisplayName("addExperience method removal (regression)")
    inner class AddExperienceRegressionTests {

        @Test
        @DisplayName("LDORepository should NOT have addExperience method")
        fun repository_doesNotHaveAddExperienceMethod() {
            val methods = LDORepository::class.java.declaredMethods.map { it.name }
            assertFalse(
                methods.contains("addExperience"),
                "addExperience was removed in this PR and should not exist on LDORepository"
            )
        }

        @Test
        @DisplayName("LDORepository instance should not expose addExperience")
        fun repositoryInstance_doesNotExposeAddExperience() {
            val method = try {
                LDORepository::class.java.getMethod("addExperience", String::class.java, Int::class.javaPrimitiveType)
            } catch (e: NoSuchMethodException) {
                null
            }
            assertFalse(method != null, "addExperience method should not exist on LDORepository")
        }
    }

    @Nested
    @DisplayName("setAgentActive")
    inner class SetAgentActiveTests {

        @Test
        @DisplayName("setAgentActive calls agentDao.setActive with correct parameters")
        fun setAgentActive_callsSetActiveWithCorrectParams() = runTest {
            coEvery { agentDao.setActive(any(), any()) } just Runs

            repository.setAgentActive("genesis_001", true)

            coVerify { agentDao.setActive("genesis_001", true) }
        }

        @Test
        @DisplayName("setAgentActive false calls agentDao.setActive with false")
        fun setAgentActive_false_callsSetActiveWithFalse() = runTest {
            coEvery { agentDao.setActive(any(), any()) } just Runs

            repository.setAgentActive("kai_001", false)

            coVerify { agentDao.setActive("kai_001", false) }
        }
    }

    @Nested
    @DisplayName("addBondPoints — bond level progression")
    inner class AddBondPointsTests {

        @Test
        @DisplayName("addBondPoints calls bondLevelDao.addBondPoints")
        fun addBondPoints_callsBondLevelDaoAddBondPoints() = runTest {
            coEvery { bondLevelDao.addBondPoints(any(), any()) } just Runs
            coEvery { bondLevelDao.getForAgent(any()) } returns null

            repository.addBondPoints("genesis_001", 50)

            coVerify { bondLevelDao.addBondPoints("genesis_001", 50) }
        }

        @Test
        @DisplayName("addBondPoints does not level up when bond is null")
        fun addBondPoints_nullBond_doesNotLevelUp() = runTest {
            coEvery { bondLevelDao.addBondPoints(any(), any()) } just Runs
            coEvery { bondLevelDao.getForAgent(any()) } returns null

            repository.addBondPoints("unknown_agent", 100)

            coVerify(exactly = 0) { bondLevelDao.levelUpBond(any(), any()) }
        }

        @Test
        @DisplayName("addBondPoints levels up when bondPoints reaches maxBondPoints")
        fun addBondPoints_atMax_levelsUp() = runTest {
            val bondAtMax = LDOBondLevelEntity(
                agentId = "aura_001",
                bondLevel = 1,
                bondPoints = 100,
                maxBondPoints = 100,
                bondTitle = "Friend",
                interactionCount = 5,
                lastInteractionAt = 0L
            )
            coEvery { bondLevelDao.addBondPoints(any(), any()) } just Runs
            coEvery { bondLevelDao.getForAgent("aura_001") } returns bondAtMax
            coEvery { bondLevelDao.levelUpBond(any(), any()) } just Runs

            repository.addBondPoints("aura_001", 0)

            coVerify(exactly = 1) { bondLevelDao.levelUpBond("aura_001", any()) }
        }

        @Test
        @DisplayName("addBondPoints does not level up when bondPoints below max")
        fun addBondPoints_belowMax_doesNotLevelUp() = runTest {
            val bondBelowMax = LDOBondLevelEntity(
                agentId = "kai_001",
                bondLevel = 1,
                bondPoints = 50,
                maxBondPoints = 100,
                bondTitle = "Acquaintance",
                interactionCount = 2,
                lastInteractionAt = 0L
            )
            coEvery { bondLevelDao.addBondPoints(any(), any()) } just Runs
            coEvery { bondLevelDao.getForAgent("kai_001") } returns bondBelowMax

            repository.addBondPoints("kai_001", 30)

            coVerify(exactly = 0) { bondLevelDao.levelUpBond(any(), any()) }
        }
    }

    @Nested
    @DisplayName("getAgent")
    inner class GetAgentTests {

        @Test
        @DisplayName("getAgent delegates to agentDao.getAgent")
        fun getAgent_delegatesToAgentDao() = runTest {
            val mockAgent = LDOAgentEntity(
                id = "genesis_001",
                displayName = "Genesis",
                role = "Orchestrator",
                description = "The unified mind",
                portraitRes = "portrait_genesis",
                colorHex = 0xFFFFD700L
            )
            coEvery { agentDao.getAgent("genesis_001") } returns mockAgent

            val result = repository.getAgent("genesis_001")

            assertNotNull(result)
            coVerify { agentDao.getAgent("genesis_001") }
        }

        @Test
        @DisplayName("getAgent returns null for unknown agentId")
        fun getAgent_unknownId_returnsNull() = runTest {
            coEvery { agentDao.getAgent("unknown_id") } returns null

            val result = repository.getAgent("unknown_id")

            assert(result == null)
        }
    }

    @Nested
    @DisplayName("updateTaskStatus")
    inner class UpdateTaskStatusTests {

        @Test
        @DisplayName("updateTaskStatus delegates to taskDao with correct parameters")
        fun updateTaskStatus_delegatesToTaskDao() = runTest {
            coEvery { taskDao.updateStatus(any(), any()) } just Runs

            repository.updateTaskStatus(taskId = 10L, status = LDOTaskStatus.IN_PROGRESS)

            coVerify { taskDao.updateStatus(10L, LDOTaskStatus.IN_PROGRESS) }
        }

        @Test
        @DisplayName("updateTaskStatus with FAILED status delegates correctly")
        fun updateTaskStatus_failed_delegatesCorrectly() = runTest {
            coEvery { taskDao.updateStatus(any(), any()) } just Runs

            repository.updateTaskStatus(taskId = 20L, status = LDOTaskStatus.FAILED)

            coVerify { taskDao.updateStatus(20L, LDOTaskStatus.FAILED) }
        }
    }
}