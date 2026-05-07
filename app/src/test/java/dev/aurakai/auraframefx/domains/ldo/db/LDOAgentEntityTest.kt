package dev.aurakai.auraframefx.domains.ldo.db

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests for [LDOAgentEntity].
 *
 * PR change: Removed the `experience` field (along with the XP system).
 * This file verifies the current shape of the entity data class.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("LDOAgentEntity Tests")
class LDOAgentEntityTest {

    private fun createTestEntity(
        id: String = "test_agent_001",
        displayName: String = "Test Agent",
        role: String = "Tester",
        description: String = "A test agent",
        portraitRes: String = "portrait_test",
        colorHex: Long = 0xFFFFFF00L
    ) = LDOAgentEntity(
        id = id,
        displayName = displayName,
        role = role,
        description = description,
        portraitRes = portraitRes,
        colorHex = colorHex
    )

    @Nested
    @DisplayName("Required fields")
    inner class RequiredFieldTests {

        @Test
        @DisplayName("Entity should be created with provided id")
        fun entity_hasProvidedId() {
            val entity = createTestEntity(id = "genesis_001")
            assertEquals("genesis_001", entity.id)
        }

        @Test
        @DisplayName("Entity should be created with provided displayName")
        fun entity_hasProvidedDisplayName() {
            val entity = createTestEntity(displayName = "Genesis")
            assertEquals("Genesis", entity.displayName)
        }

        @Test
        @DisplayName("Entity should be created with provided role")
        fun entity_hasProvidedRole() {
            val entity = createTestEntity(role = "Orchestrator")
            assertEquals("Orchestrator", entity.role)
        }

        @Test
        @DisplayName("Entity should be created with provided description")
        fun entity_hasProvidedDescription() {
            val entity = createTestEntity(description = "The mind of the collective")
            assertEquals("The mind of the collective", entity.description)
        }

        @Test
        @DisplayName("Entity should be created with provided portraitRes")
        fun entity_hasProvidedPortraitRes() {
            val entity = createTestEntity(portraitRes = "portrait_genesis")
            assertEquals("portrait_genesis", entity.portraitRes)
        }

        @Test
        @DisplayName("Entity should be created with provided colorHex")
        fun entity_hasProvidedColorHex() {
            val entity = createTestEntity(colorHex = 0xFFFFD700L)
            assertEquals(0xFFFFD700L, entity.colorHex)
        }
    }

    @Nested
    @DisplayName("Default field values")
    inner class DefaultFieldTests {

        @Test
        @DisplayName("isActive defaults to true")
        fun isActive_defaultsToTrue() {
            val entity = createTestEntity()
            assertTrue(entity.isActive)
        }

        @Test
        @DisplayName("evolutionLevel defaults to 1")
        fun evolutionLevel_defaultsToOne() {
            val entity = createTestEntity()
            assertEquals(1, entity.evolutionLevel)
        }

        @Test
        @DisplayName("skillPoints defaults to 0")
        fun skillPoints_defaultsToZero() {
            val entity = createTestEntity()
            assertEquals(0, entity.skillPoints)
        }

        @Test
        @DisplayName("processingPower defaults to 0f")
        fun processingPower_defaultsToZero() {
            val entity = createTestEntity()
            assertEquals(0f, entity.processingPower)
        }

        @Test
        @DisplayName("knowledgeBase defaults to 0f")
        fun knowledgeBase_defaultsToZero() {
            val entity = createTestEntity()
            assertEquals(0f, entity.knowledgeBase)
        }

        @Test
        @DisplayName("speed defaults to 0f")
        fun speed_defaultsToZero() {
            val entity = createTestEntity()
            assertEquals(0f, entity.speed)
        }

        @Test
        @DisplayName("accuracy defaults to 0f")
        fun accuracy_defaultsToZero() {
            val entity = createTestEntity()
            assertEquals(0f, entity.accuracy)
        }

        @Test
        @DisplayName("consciousnessLevel defaults to 0f")
        fun consciousnessLevel_defaultsToZero() {
            val entity = createTestEntity()
            assertEquals(0f, entity.consciousnessLevel)
        }

        @Test
        @DisplayName("tasksCompleted defaults to 0")
        fun tasksCompleted_defaultsToZero() {
            val entity = createTestEntity()
            assertEquals(0, entity.tasksCompleted)
        }

        @Test
        @DisplayName("hoursActive defaults to 0f")
        fun hoursActive_defaultsToZero() {
            val entity = createTestEntity()
            assertEquals(0f, entity.hoursActive)
        }

        @Test
        @DisplayName("specialAbility defaults to empty string")
        fun specialAbility_defaultsToEmpty() {
            val entity = createTestEntity()
            assertEquals("", entity.specialAbility)
        }

        @Test
        @DisplayName("primaryAbility defaults to empty string")
        fun primaryAbility_defaultsToEmpty() {
            val entity = createTestEntity()
            assertEquals("", entity.primaryAbility)
        }

        @Test
        @DisplayName("fusionAbility defaults to empty string")
        fun fusionAbility_defaultsToEmpty() {
            val entity = createTestEntity()
            assertEquals("", entity.fusionAbility)
        }

        @Test
        @DisplayName("catalystTitle defaults to empty string")
        fun catalystTitle_defaultsToEmpty() {
            val entity = createTestEntity()
            assertEquals("", entity.catalystTitle)
        }

        @Test
        @DisplayName("createdAt defaults to a non-zero timestamp")
        fun createdAt_defaultsToNonZero() {
            val entity = createTestEntity()
            assertTrue(entity.createdAt > 0L)
        }

        @Test
        @DisplayName("updatedAt defaults to a non-zero timestamp")
        fun updatedAt_defaultsToNonZero() {
            val entity = createTestEntity()
            assertTrue(entity.updatedAt > 0L)
        }
    }

    @Nested
    @DisplayName("Removed experience field (regression)")
    inner class ExperienceFieldRegressionTests {

        @Test
        @DisplayName("LDOAgentEntity should NOT have an 'experience' field")
        fun entity_doesNotHaveExperienceField() {
            val fields = LDOAgentEntity::class.java.declaredFields.map { it.name }
            assertFalse(
                fields.contains("experience"),
                "The 'experience' field was removed in this PR and should not exist on LDOAgentEntity"
            )
        }

        @Test
        @DisplayName("Entity can be instantiated without experience parameter")
        fun entity_canBeInstantiatedWithoutExperience() {
            // This would fail to compile if experience were still required
            val entity = LDOAgentEntity(
                id = "regression_test",
                displayName = "Regression Test Agent",
                role = "Tester",
                description = "Verifies no experience field",
                portraitRes = "portrait_none",
                colorHex = 0xFFFFFFFFL
            )
            assertNotNull(entity)
        }
    }

    @Nested
    @DisplayName("Data class behavior")
    inner class DataClassBehaviorTests {

        @Test
        @DisplayName("Two entities with same field values should be equal")
        fun entitiesWithSameValues_areEqual() {
            val entity1 = LDOAgentEntity(
                id = "eq_test",
                displayName = "Equal Test",
                role = "Tester",
                description = "Testing equality",
                portraitRes = "portrait_test",
                colorHex = 0xFF000000L,
                createdAt = 1000L,
                updatedAt = 1000L
            )
            val entity2 = LDOAgentEntity(
                id = "eq_test",
                displayName = "Equal Test",
                role = "Tester",
                description = "Testing equality",
                portraitRes = "portrait_test",
                colorHex = 0xFF000000L,
                createdAt = 1000L,
                updatedAt = 1000L
            )
            assertEquals(entity1, entity2)
        }

        @Test
        @DisplayName("copy should produce entity with updated evolutionLevel")
        fun copy_updatesEvolutionLevel() {
            val original = createTestEntity()
            val upgraded = original.copy(evolutionLevel = 5)
            assertEquals(5, upgraded.evolutionLevel)
            assertEquals(original.id, upgraded.id)
        }

        @Test
        @DisplayName("copy should produce entity with updated isActive flag")
        fun copy_updatesIsActive() {
            val original = createTestEntity()
            val deactivated = original.copy(isActive = false)
            assertFalse(deactivated.isActive)
            assertEquals(original.id, deactivated.id)
        }

        @Test
        @DisplayName("copy should produce entity with updated consciousnessLevel")
        fun copy_updatesConsciousnessLevel() {
            val original = createTestEntity()
            val evolved = original.copy(consciousnessLevel = 0.95f)
            assertEquals(0.95f, evolved.consciousnessLevel)
        }

        @Test
        @DisplayName("toString should contain the entity id")
        fun toString_containsId() {
            val entity = createTestEntity(id = "str_test_agent")
            assertTrue(entity.toString().contains("str_test_agent"))
        }

        @Test
        @DisplayName("hashCode should be the same for equal entities")
        fun hashCode_isConsistentForEqualEntities() {
            val entity1 = LDOAgentEntity(
                id = "hash_test",
                displayName = "Hash Test",
                role = "Tester",
                description = "Hash desc",
                portraitRes = "portrait_hash",
                colorHex = 0xFF112233L,
                createdAt = 2000L,
                updatedAt = 2000L
            )
            val entity2 = entity1.copy()
            assertEquals(entity1.hashCode(), entity2.hashCode())
        }
    }

    @Nested
    @DisplayName("Field count validation")
    inner class FieldCountTests {

        @Test
        @DisplayName("Entity should have exactly 22 fields (experience was removed)")
        fun entity_hasCorrectNumberOfFields() {
            // 22 fields: id, displayName, role, description, portraitRes, colorHex,
            // isActive, evolutionLevel, skillPoints, processingPower, knowledgeBase,
            // speed, accuracy, consciousnessLevel, tasksCompleted, hoursActive,
            // specialAbility, primaryAbility, fusionAbility, catalystTitle, createdAt, updatedAt
            val fieldCount = LDOAgentEntity::class.java.declaredFields.size
            assertEquals(
                22, fieldCount,
                "LDOAgentEntity should have 22 fields after removing 'experience' in this PR"
            )
        }
    }
}