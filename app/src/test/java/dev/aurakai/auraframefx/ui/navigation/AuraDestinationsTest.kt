package dev.aurakai.auraframefx.ui.navigation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests for [AuraDestinations] — the route constants and path-builder helpers
 * introduced in ReGenesisNavGraph.kt when AuraNavGraph.kt was consolidated.
 */
class AuraDestinationsTest {

    // region constant value tests

    @Test
    fun `COMMAND_DECK equals command_deck`() {
        assertEquals("command_deck", AuraDestinations.COMMAND_DECK)
    }

    @Test
    fun `LOADOUT_BUILDER equals loadout_builder`() {
        assertEquals("loadout_builder", AuraDestinations.LOADOUT_BUILDER)
    }

    @Test
    fun `SPECIALIZATION_TREE equals specialization_tree with agentId placeholder`() {
        assertEquals("specialization_tree/{agentId}", AuraDestinations.SPECIALIZATION_TREE)
    }

    @Test
    fun `TRAINING_ARENA equals training_arena with agentId placeholder`() {
        assertEquals("training_arena/{agentId}", AuraDestinations.TRAINING_ARENA)
    }

    // endregion

    // region constant non-emptiness tests

    @Test
    fun `COMMAND_DECK is not blank`() {
        assertTrue(AuraDestinations.COMMAND_DECK.isNotBlank())
    }

    @Test
    fun `LOADOUT_BUILDER is not blank`() {
        assertTrue(AuraDestinations.LOADOUT_BUILDER.isNotBlank())
    }

    @Test
    fun `SPECIALIZATION_TREE is not blank`() {
        assertTrue(AuraDestinations.SPECIALIZATION_TREE.isNotBlank())
    }

    @Test
    fun `TRAINING_ARENA is not blank`() {
        assertTrue(AuraDestinations.TRAINING_ARENA.isNotBlank())
    }

    // endregion

    // region specTreePath helper tests

    @Test
    fun `specTreePath returns correct path for a given agentId`() {
        assertEquals("specialization_tree/aura", AuraDestinations.specTreePath("aura"))
    }

    @Test
    fun `specTreePath returns correct path for uppercase agentId`() {
        assertEquals("specialization_tree/GENESIS", AuraDestinations.specTreePath("GENESIS"))
    }

    @Test
    fun `specTreePath returns correct path for numeric agentId`() {
        assertEquals("specialization_tree/42", AuraDestinations.specTreePath("42"))
    }

    @Test
    fun `specTreePath returns path with empty segment for empty agentId`() {
        assertEquals("specialization_tree/", AuraDestinations.specTreePath(""))
    }

    @Test
    fun `specTreePath prefix matches SPECIALIZATION_TREE base without placeholder`() {
        val base = AuraDestinations.SPECIALIZATION_TREE.removeSuffix("/{agentId}")
        val path = AuraDestinations.specTreePath("testAgent")
        assertTrue(path.startsWith(base))
    }

    @Test
    fun `specTreePath does not contain agentId placeholder braces`() {
        val path = AuraDestinations.specTreePath("someAgent")
        assertFalse(path.contains("{agentId}"))
    }

    // endregion

    // region arenaPath helper tests

    @Test
    fun `arenaPath returns correct path for a given agentId`() {
        assertEquals("training_arena/kai", AuraDestinations.arenaPath("kai"))
    }

    @Test
    fun `arenaPath returns correct path for uppercase agentId`() {
        assertEquals("training_arena/TRINITY", AuraDestinations.arenaPath("TRINITY"))
    }

    @Test
    fun `arenaPath returns correct path for numeric agentId`() {
        assertEquals("training_arena/99", AuraDestinations.arenaPath("99"))
    }

    @Test
    fun `arenaPath returns path with empty segment for empty agentId`() {
        assertEquals("training_arena/", AuraDestinations.arenaPath(""))
    }

    @Test
    fun `arenaPath prefix matches TRAINING_ARENA base without placeholder`() {
        val base = AuraDestinations.TRAINING_ARENA.removeSuffix("/{agentId}")
        val path = AuraDestinations.arenaPath("testAgent")
        assertTrue(path.startsWith(base))
    }

    @Test
    fun `arenaPath does not contain agentId placeholder braces`() {
        val path = AuraDestinations.arenaPath("someAgent")
        assertFalse(path.contains("{agentId}"))
    }

    // endregion

    // region uniqueness tests

    @Test
    fun `all four route constants are distinct`() {
        val routes = setOf(
            AuraDestinations.COMMAND_DECK,
            AuraDestinations.LOADOUT_BUILDER,
            AuraDestinations.SPECIALIZATION_TREE,
            AuraDestinations.TRAINING_ARENA,
        )
        assertEquals(4, routes.size)
    }

    @Test
    fun `no route constant contains spaces`() {
        listOf(
            AuraDestinations.COMMAND_DECK,
            AuraDestinations.LOADOUT_BUILDER,
            AuraDestinations.SPECIALIZATION_TREE,
            AuraDestinations.TRAINING_ARENA,
        ).forEach { route ->
            assertFalse("Route '$route' must not contain spaces", route.contains(' '))
        }
    }

    // endregion

    // region parameterised route format tests

    @Test
    fun `SPECIALIZATION_TREE contains agentId placeholder in braces`() {
        assertTrue(AuraDestinations.SPECIALIZATION_TREE.contains("{agentId}"))
    }

    @Test
    fun `TRAINING_ARENA contains agentId placeholder in braces`() {
        assertTrue(AuraDestinations.TRAINING_ARENA.contains("{agentId}"))
    }

    @Test
    fun `specTreePath and arenaPath produce different routes for the same agentId`() {
        val id = "agent_007"
        assertFalse(AuraDestinations.specTreePath(id) == AuraDestinations.arenaPath(id))
    }

    // endregion

    // region regression tests

    @Test
    fun `specTreePath with whitespace agentId is preserved exactly`() {
        // Regression: no trimming should occur inside the helper
        assertEquals("specialization_tree/ whitespace ", AuraDestinations.specTreePath(" whitespace "))
    }

    @Test
    fun `arenaPath with whitespace agentId is preserved exactly`() {
        assertEquals("training_arena/ whitespace ", AuraDestinations.arenaPath(" whitespace "))
    }

    @Test
    fun `COMMAND_DECK does not use slash separator`() {
        assertFalse(AuraDestinations.COMMAND_DECK.contains('/'))
    }

    @Test
    fun `LOADOUT_BUILDER does not use slash separator`() {
        assertFalse(AuraDestinations.LOADOUT_BUILDER.contains('/'))
    }

    // endregion
}