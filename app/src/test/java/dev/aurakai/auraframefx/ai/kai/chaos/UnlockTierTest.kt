package dev.aurakai.auraframefx.ai.kai.chaos

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Unit tests for [UnlockTier] — the new sealed class added in this PR.
 * Covers the [UnlockTier.level] computed property for each subtype,
 * identity (object singleton), and ordering invariants.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("UnlockTier Tests")
class UnlockTierTest {

    @Nested
    @DisplayName("level property")
    inner class LevelPropertyTests {

        @Test
        @DisplayName("Sealed has level 0")
        fun `Sealed has level 0`() {
            assertEquals(0, UnlockTier.Sealed.level)
        }

        @Test
        @DisplayName("Creative has level 1")
        fun `Creative has level 1`() {
            assertEquals(1, UnlockTier.Creative.level)
        }

        @Test
        @DisplayName("System has level 2")
        fun `System has level 2`() {
            assertEquals(2, UnlockTier.System.level)
        }

        @Test
        @DisplayName("Sovereign has level 3")
        fun `Sovereign has level 3`() {
            assertEquals(3, UnlockTier.Sovereign.level)
        }
    }

    @Nested
    @DisplayName("Ordering invariants")
    inner class OrderingTests {

        @Test
        @DisplayName("Sealed level is less than Creative level")
        fun `Sealed less than Creative`() {
            assertTrue(UnlockTier.Sealed.level < UnlockTier.Creative.level)
        }

        @Test
        @DisplayName("Creative level is less than System level")
        fun `Creative less than System`() {
            assertTrue(UnlockTier.Creative.level < UnlockTier.System.level)
        }

        @Test
        @DisplayName("System level is less than Sovereign level")
        fun `System less than Sovereign`() {
            assertTrue(UnlockTier.System.level < UnlockTier.Sovereign.level)
        }

        @Test
        @DisplayName("Sealed level is less than all others")
        fun `Sealed is lowest tier`() {
            assertTrue(UnlockTier.Sealed.level < UnlockTier.Creative.level)
            assertTrue(UnlockTier.Sealed.level < UnlockTier.System.level)
            assertTrue(UnlockTier.Sealed.level < UnlockTier.Sovereign.level)
        }

        @Test
        @DisplayName("Sovereign level is greater than all others")
        fun `Sovereign is highest tier`() {
            assertTrue(UnlockTier.Sovereign.level > UnlockTier.Sealed.level)
            assertTrue(UnlockTier.Sovereign.level > UnlockTier.Creative.level)
            assertTrue(UnlockTier.Sovereign.level > UnlockTier.System.level)
        }
    }

    @Nested
    @DisplayName("Object singleton identity")
    inner class SingletonTests {

        @Test
        @DisplayName("Sealed is the same singleton instance")
        fun `Sealed singleton`() {
            val a: UnlockTier = UnlockTier.Sealed
            val b: UnlockTier = UnlockTier.Sealed
            assertTrue(a === b)
        }

        @Test
        @DisplayName("Creative is the same singleton instance")
        fun `Creative singleton`() {
            val a: UnlockTier = UnlockTier.Creative
            val b: UnlockTier = UnlockTier.Creative
            assertTrue(a === b)
        }

        @Test
        @DisplayName("different tiers are not the same instance")
        fun `different tiers are distinct`() {
            assertFalse(UnlockTier.Sealed === UnlockTier.Creative)
            assertFalse(UnlockTier.Creative === UnlockTier.System)
            assertFalse(UnlockTier.System === UnlockTier.Sovereign)
        }
    }

    @Nested
    @DisplayName("Sealed class exhaustiveness via when")
    inner class ExhaustivenessTests {

        private fun tierName(tier: UnlockTier): String = when (tier) {
            is UnlockTier.Sealed -> "Sealed"
            is UnlockTier.Creative -> "Creative"
            is UnlockTier.System -> "System"
            is UnlockTier.Sovereign -> "Sovereign"
        }

        @Test
        @DisplayName("when expression covers Sealed correctly")
        fun `when covers Sealed`() {
            assertEquals("Sealed", tierName(UnlockTier.Sealed))
        }

        @Test
        @DisplayName("when expression covers Creative correctly")
        fun `when covers Creative`() {
            assertEquals("Creative", tierName(UnlockTier.Creative))
        }

        @Test
        @DisplayName("when expression covers System correctly")
        fun `when covers System`() {
            assertEquals("System", tierName(UnlockTier.System))
        }

        @Test
        @DisplayName("when expression covers Sovereign correctly")
        fun `when covers Sovereign`() {
            assertEquals("Sovereign", tierName(UnlockTier.Sovereign))
        }
    }

    @Nested
    @DisplayName("level is consistent across repeated calls")
    inner class ConsistencyTests {

        @Test
        @DisplayName("Sealed.level returns same value on multiple calls")
        fun `Sealed level is stable`() {
            assertEquals(UnlockTier.Sealed.level, UnlockTier.Sealed.level)
        }

        @Test
        @DisplayName("Sovereign.level returns same value on multiple calls")
        fun `Sovereign level is stable`() {
            assertEquals(UnlockTier.Sovereign.level, UnlockTier.Sovereign.level)
        }

        @Test
        @DisplayName("all levels are distinct")
        fun `all levels are distinct`() {
            val levels = setOf(
                UnlockTier.Sealed.level,
                UnlockTier.Creative.level,
                UnlockTier.System.level,
                UnlockTier.Sovereign.level
            )
            assertEquals(4, levels.size, "All tier levels should be unique")
        }
    }
}