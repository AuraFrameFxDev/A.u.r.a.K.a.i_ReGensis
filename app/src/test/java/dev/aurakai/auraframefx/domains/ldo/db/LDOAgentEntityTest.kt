package dev.aurakai.auraframefx.domains.ldo.db

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests for [LDOAgentEntity] data class.
 *
 * PR change: Removed the 'experience' field from LDOAgentEntity.
 * These tests verify the data class no longer has that field and that
 * the remaining fields have correct defaults.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("LDOAgentEntity Tests")
class LDOAgentEntityTest {

    private fun createMinimalAgent(
        id: String = "agent_001",
        displayName: String = "Test Agent",
        role: String = "Tester",
        description: String = "A test agent",
        portraitRes: String = "portrait_test",
        colorHex: Long = 0xFF00AAFF
    ) = LDOAgentEntity(
        id = id,
        displayName = displayName,
        role = role,
        description = description,
        portraitRes = portraitRes,
        colorHex = colorHex
    )

    @Nested
    @DisplayName("Required field construction")
    inner class RequiredFieldTests {

        @Test
        @DisplayName("Entity should be constructable with only required fields")
        fun shouldConstructWithRequiredFieldsOnly() {
            val entity = createMinimalAgent()
            assertEquals("agent_001", entity.id)
            assertEquals("Test Agent", entity.displayName)
            assertEquals("Tester", entity.role)
            assertEquals("A test agent", entity.description)
            assertEquals("portrait_test", entity.portraitRes)
            assertEquals(0xFF00AAFF, entity.colorHex)
        }

        @Test
        @DisplayName("Entity id should be the primary key (non-null, non-blank)")
        fun entityIdShouldBeNonBlank() {
            val entity = createMinimalAgent(id = "kai_001")
            assertTrue(entity.id.isNotBlank())
        }
    }

    @Nested
    @DisplayName("Default field values")
    inner class DefaultFieldValueTests {

        @Test
        @DisplayName("isActive should default to true")
        fun isActiveShouldDefaultToTrue() {
            val entity = createMinimalAgent()
            assertTrue(entity.isActive)
        }

        @Test
        @DisplayName("evolutionLevel should default to 1")
        fun evolutionLevelShouldDefaultToOne() {
            val entity = createMinimalAgent()
            assertEquals(1, entity.evolutionLevel)
        }

        @Test
        @DisplayName("skillPoints should default to 0")
        fun skillPointsShouldDefaultToZero() {
            val entity = createMinimalAgent()
            assertEquals(0, entity.skillPoints)
        }

        @Test
        @DisplayName("processingPower should default to 0f")
        fun processingPowerShouldDefaultToZero() {
            val entity = createMinimalAgent()
            assertEquals(0f, entity.processingPower)
        }

        @Test
        @DisplayName("knowledgeBase should default to 0f")
        fun knowledgeBaseShouldDefaultToZero() {
            val entity = createMinimalAgent()
            assertEquals(0f, entity.knowledgeBase)
        }

        @Test
        @DisplayName("speed should default to 0f")
        fun speedShouldDefaultToZero() {
            val entity = createMinimalAgent()
            assertEquals(0f, entity.speed)
        }

        @Test
        @DisplayName("accuracy should default to 0f")
        fun accuracyShouldDefaultToZero() {
            val entity = createMinimalAgent()
            assertEquals(0f, entity.accuracy)
        }

        @Test
        @DisplayName("consciousnessLevel should default to 0f")
        fun consciousnessLevelShouldDefaultToZero() {
            val entity = createMinimalAgent()
            assertEquals(0f, entity.consciousnessLevel)
        }

        @Test
        @DisplayName("tasksCompleted should default to 0")
        fun tasksCompletedShouldDefaultToZero() {
            val entity = createMinimalAgent()
            assertEquals(0, entity.tasksCompleted)
        }

        @Test
        @DisplayName("hoursActive should default to 0f")
        fun hoursActiveShouldDefaultToZero() {
            val entity = createMinimalAgent()
            assertEquals(0f, entity.hoursActive)
        }

        @Test
        @DisplayName("specialAbility should default to empty string")
        fun specialAbilityShouldDefaultToEmpty() {
            val entity = createMinimalAgent()
            assertEquals("", entity.specialAbility)
        }

        @Test
        @DisplayName("primaryAbility should default to empty string")
        fun primaryAbilityShouldDefaultToEmpty() {
            val entity = createMinimalAgent()
            assertEquals("", entity.primaryAbility)
        }

        @Test
        @DisplayName("fusionAbility should default to empty string")
        fun fusionAbilityShouldDefaultToEmpty() {
            val entity = createMinimalAgent()
            assertEquals("", entity.fusionAbility)
        }

        @Test
        @DisplayName("catalystTitle should default to empty string")
        fun catalystTitleShouldDefaultToEmpty() {
            val entity = createMinimalAgent()
            assertEquals("", entity.catalystTitle)
        }
    }

    @Nested
    @DisplayName("Experience field removal (PR change)")
    inner class ExperienceFieldRemovalTests {

        @Test
        @DisplayName("LDOAgentEntity should NOT have an experience field after PR change")
        fun entityShouldNotHaveExperienceField() {
            val entity = createMinimalAgent()
            // Verify via reflection that 'experience' field does not exist
            val fields = entity.javaClass.declaredFields.map { it.name }
            assertFalse(
                fields.contains("experience"),
                "LDOAgentEntity should not have an 'experience' field after PR change"
            )
        }

        @Test
        @DisplayName("Entity can still be copied without experience field")
        fun entityCanBeCopiedWithoutExperienceField() {
            val original = createMinimalAgent(id = "genesis_001", displayName = "Genesis")
            val copy = original.copy(evolutionLevel = 5)
            assertEquals("genesis_001", copy.id)
            assertEquals("Genesis", copy.displayName)
            assertEquals(5, copy.evolutionLevel)
        }
    }

    @Nested
    @DisplayName("Data class equality and copy")
    inner class DataClassTests {

        @Test
        @DisplayName("Two entities with same field values should be equal")
        fun entitiesWithSameValuesShouldBeEqual() {
            val entity1 = LDOAgentEntity(
                id = "aura_001",
                displayName = "Aura",
                role = "Creator",
                description = "Visionary Soul",
                portraitRes = "portrait_aura",
                colorHex = 0xFFFF00FF,
                evolutionLevel = 3,
                skillPoints = 10
            )
            val entity2 = LDOAgentEntity(
                id = "aura_001",
                displayName = "Aura",
                role = "Creator",
                description = "Visionary Soul",
                portraitRes = "portrait_aura",
                colorHex = 0xFFFF00FF,
                evolutionLevel = 3,
                skillPoints = 10,
                createdAt = entity1.createdAt,
                updatedAt = entity1.updatedAt
            )
            assertEquals(entity1, entity2)
        }

        @Test
        @DisplayName("copy() should produce entity with updated evolutionLevel")
        fun copyShouldUpdateEvolutionLevel() {
            val original = createMinimalAgent()
            val evolved = original.copy(evolutionLevel = 10, skillPoints = 50)
            assertEquals(10, evolved.evolutionLevel)
            assertEquals(50, evolved.skillPoints)
            assertEquals(original.id, evolved.id)
        }

        @Test
        @DisplayName("Entities with different IDs should not be equal")
        fun entitiesWithDifferentIdsShouldNotBeEqual() {
            val entity1 = createMinimalAgent(id = "agent_001")
            val entity2 = createMinimalAgent(id = "agent_002")
            assertNotEquals(entity1, entity2)
        }

        @Test
        @DisplayName("copy() with deactivated status should produce inactive entity")
        fun copyShouldDeactivateEntity() {
            val active = createMinimalAgent()
            val inactive = active.copy(isActive = false)
            assertFalse(inactive.isActive)
            assertTrue(active.isActive)
        }
    }

    @Nested
    @DisplayName("Stat range validation")
    inner class StatRangeTests {

        @Test
        @DisplayName("consciousnessLevel can be set between 0 and 1")
        fun consciousnessLevelCanBeSetInRange() {
            val entity = createMinimalAgent().copy(consciousnessLevel = 0.75f)
            assertEquals(0.75f, entity.consciousnessLevel, 0.001f)
        }

        @Test
        @DisplayName("processingPower can hold a meaningful float value")
        fun processingPowerCanHoldFloat() {
            val entity = createMinimalAgent().copy(processingPower = 0.9f)
            assertEquals(0.9f, entity.processingPower, 0.001f)
        }

        @Test
        @DisplayName("tasksCompleted should increment correctly via copy")
        fun tasksCompletedShouldIncrementViaCopy() {
            val initial = createMinimalAgent().copy(tasksCompleted = 5)
            val afterTask = initial.copy(tasksCompleted = initial.tasksCompleted + 1)
            assertEquals(6, afterTask.tasksCompleted)
        }
    }
}