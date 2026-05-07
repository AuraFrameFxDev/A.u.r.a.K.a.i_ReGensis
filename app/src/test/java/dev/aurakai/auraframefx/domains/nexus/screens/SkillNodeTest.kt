package dev.aurakai.auraframefx.domains.nexus.screens

import androidx.compose.ui.graphics.Color
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests for [SkillNode] data class.
 *
 * PR change: SkillNode was introduced in this PR replacing the old SphereNode.
 * Key differences: SkillNode uses Color directly (not Long colorHex),
 * and x/y represent normalized 0.0..1.0 canvas coordinates.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("SkillNode Data Class Tests")
class SkillNodeTest {

    @Nested
    @DisplayName("Construction")
    inner class ConstructionTests {

        @Test
        @DisplayName("SkillNode should be constructable with all required fields")
        fun shouldConstructWithAllFields() {
            val node = SkillNode(
                name = "Core AI",
                x = 0.5f,
                y = 0.1f,
                unlocked = true,
                color = Color.Cyan
            )
            assertEquals("Core AI", node.name)
            assertEquals(0.5f, node.x)
            assertEquals(0.1f, node.y)
            assertTrue(node.unlocked)
            assertEquals(Color.Cyan, node.color)
        }

        @Test
        @DisplayName("SkillNode should accept locked state")
        fun shouldAcceptLockedState() {
            val node = SkillNode(
                name = "Integration",
                x = 0.5f,
                y = 0.9f,
                unlocked = false,
                color = Color.Gray
            )
            assertFalse(node.unlocked)
        }

        @Test
        @DisplayName("SkillNode should accept boundary coordinates (0.0, 0.0)")
        fun shouldAcceptMinimumCoordinates() {
            val node = SkillNode("Origin", 0.0f, 0.0f, true, Color.White)
            assertEquals(0.0f, node.x)
            assertEquals(0.0f, node.y)
        }

        @Test
        @DisplayName("SkillNode should accept boundary coordinates (1.0, 1.0)")
        fun shouldAcceptMaximumCoordinates() {
            val node = SkillNode("Corner", 1.0f, 1.0f, true, Color.White)
            assertEquals(1.0f, node.x)
            assertEquals(1.0f, node.y)
        }

        @Test
        @DisplayName("SkillNode should accept center coordinates (0.5, 0.5)")
        fun shouldAcceptCenterCoordinates() {
            val node = SkillNode("Center", 0.5f, 0.5f, true, Color.Cyan)
            assertEquals(0.5f, node.x, 0.001f)
            assertEquals(0.5f, node.y, 0.001f)
        }
    }

    @Nested
    @DisplayName("Data class equality")
    inner class EqualityTests {

        @Test
        @DisplayName("Two SkillNodes with same values should be equal")
        fun nodesWithSameValuesShouldBeEqual() {
            val node1 = SkillNode("Learning", 0.3f, 0.3f, true, Color.Blue)
            val node2 = SkillNode("Learning", 0.3f, 0.3f, true, Color.Blue)
            assertEquals(node1, node2)
        }

        @Test
        @DisplayName("SkillNodes with different names should not be equal")
        fun nodesWithDifferentNamesShouldNotBeEqual() {
            val node1 = SkillNode("Learning", 0.3f, 0.3f, true, Color.Blue)
            val node2 = SkillNode("Processing", 0.3f, 0.3f, true, Color.Blue)
            assertNotEquals(node1, node2)
        }

        @Test
        @DisplayName("SkillNodes with different unlock states should not be equal")
        fun nodesWithDifferentUnlockStatesShouldNotBeEqual() {
            val locked = SkillNode("Skill", 0.5f, 0.5f, false, Color.Gray)
            val unlocked = SkillNode("Skill", 0.5f, 0.5f, true, Color.Gray)
            assertNotEquals(locked, unlocked)
        }

        @Test
        @DisplayName("SkillNodes with different coordinates should not be equal")
        fun nodesWithDifferentCoordinatesShouldNotBeEqual() {
            val node1 = SkillNode("Node", 0.3f, 0.3f, true, Color.Blue)
            val node2 = SkillNode("Node", 0.7f, 0.7f, true, Color.Blue)
            assertNotEquals(node1, node2)
        }
    }

    @Nested
    @DisplayName("Data class copy")
    inner class CopyTests {

        @Test
        @DisplayName("copy() should produce a new SkillNode with updated unlocked state")
        fun copyShouldUpdateUnlockedState() {
            val locked = SkillNode("Memory", 0.2f, 0.5f, false, Color.Magenta)
            val unlocked = locked.copy(unlocked = true)
            assertTrue(unlocked.unlocked)
            assertEquals(locked.name, unlocked.name)
            assertEquals(locked.x, unlocked.x)
            assertEquals(locked.y, unlocked.y)
        }

        @Test
        @DisplayName("copy() should produce a new SkillNode with updated color")
        fun copyShouldUpdateColor() {
            val original = SkillNode("Analysis", 0.5f, 0.7f, true, Color.Gray)
            val updated = original.copy(color = Color.Green)
            assertEquals(Color.Green, updated.color)
            assertEquals(original.name, updated.name)
        }

        @Test
        @DisplayName("copy() with updated coordinates should preserve other fields")
        fun copyShouldPreserveUnchangedFields() {
            val original = SkillNode("Core AI", 0.5f, 0.1f, true, Color.Cyan)
            val moved = original.copy(x = 0.3f, y = 0.4f)
            assertEquals("Core AI", moved.name)
            assertTrue(moved.unlocked)
            assertEquals(Color.Cyan, moved.color)
            assertEquals(0.3f, moved.x)
            assertEquals(0.4f, moved.y)
        }
    }

    @Nested
    @DisplayName("Skill tree structure")
    inner class SkillTreeStructureTests {

        @Test
        @DisplayName("A typical skill tree of 7 nodes should have first node always unlocked")
        fun firstSkillTreeNodeShouldBeUnlocked() {
            val evolutionLevel = 1
            val skills = buildTypicalSkillTree(evolutionLevel)
            assertTrue(skills[0].unlocked, "Core AI (first node) should always be unlocked")
        }

        @Test
        @DisplayName("Higher evolution level should unlock more skills")
        fun higherEvolutionLevelShouldUnlockMoreSkills() {
            val lowLevelSkills = buildTypicalSkillTree(1)
            val highLevelSkills = buildTypicalSkillTree(10)

            val lowUnlocked = lowLevelSkills.count { it.unlocked }
            val highUnlocked = highLevelSkills.count { it.unlocked }

            assertTrue(highUnlocked >= lowUnlocked,
                "Higher evolution level should unlock at least as many skills")
        }

        @Test
        @DisplayName("All skill coordinates should be in 0.0..1.0 range")
        fun allSkillCoordinatesShouldBeInNormalizedRange() {
            val skills = buildTypicalSkillTree(5)
            skills.forEach { skill ->
                assertTrue(skill.x in 0.0f..1.0f,
                    "Skill '${skill.name}' x=${skill.x} out of normalized range")
                assertTrue(skill.y in 0.0f..1.0f,
                    "Skill '${skill.name}' y=${skill.y} out of normalized range")
            }
        }

        @Test
        @DisplayName("A typical skill tree should have 7 nodes")
        fun typicalSkillTreeShouldHaveSevenNodes() {
            val skills = buildTypicalSkillTree(5)
            assertEquals(7, skills.size)
        }

        private fun buildTypicalSkillTree(evolutionLevel: Int): List<SkillNode> {
            val agentColor = Color.Cyan
            return listOf(
                SkillNode("Core AI", 0.5f, 0.1f, true, agentColor),
                SkillNode("Learning", 0.3f, 0.3f, evolutionLevel > 2, agentColor),
                SkillNode("Processing", 0.7f, 0.3f, evolutionLevel > 3, agentColor),
                SkillNode("Memory", 0.2f, 0.5f, evolutionLevel > 4, agentColor),
                SkillNode("Creativity", 0.8f, 0.5f, evolutionLevel > 5, agentColor),
                SkillNode("Analysis", 0.5f, 0.7f, evolutionLevel > 6, agentColor),
                SkillNode("Integration", 0.5f, 0.9f, evolutionLevel > 7, agentColor)
            )
        }
    }

    @Nested
    @DisplayName("toString representation")
    inner class ToStringTests {

        @Test
        @DisplayName("toString should include the node name")
        fun toStringShouldIncludeNodeName() {
            val node = SkillNode("Core AI", 0.5f, 0.1f, true, Color.Cyan)
            assertTrue(node.toString().contains("Core AI"))
        }

        @Test
        @DisplayName("toString should include unlocked status")
        fun toStringShouldIncludeUnlockedStatus() {
            val node = SkillNode("Test", 0.5f, 0.5f, false, Color.Gray)
            assertTrue(node.toString().contains("false"))
        }
    }
}