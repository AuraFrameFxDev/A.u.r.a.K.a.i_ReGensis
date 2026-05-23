package dev.aurakai.auraframefx.ui.navigation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests for [AuraDestinations] which was introduced (moved from the deleted AuraNavGraph.kt)
 * into ReGenesisNavGraph.kt in this PR.
 *
 * Covers:
 * - Route constant values
 * - Route template format (presence of path parameter placeholders)
 * - Path-building helper functions
 */
class AuraDestinationsTest {

    // ── Route constants ───────────────────────────────────────────────────

    @Test
    fun `COMMAND_DECK route is command_deck`() {
        assertEquals("command_deck", AuraDestinations.COMMAND_DECK)
    }

    @Test
    fun `LOADOUT_BUILDER route is loadout_builder`() {
        assertEquals("loadout_builder", AuraDestinations.LOADOUT_BUILDER)
    }

    @Test
    fun `SPECIALIZATION_TREE route template contains agentId placeholder`() {
        assertTrue(
            "SPECIALIZATION_TREE must include {agentId} placeholder",
            AuraDestinations.SPECIALIZATION_TREE.contains("{agentId}")
        )
    }

    @Test
    fun `SPECIALIZATION_TREE route starts with specialization_tree`() {
        assertTrue(
            "SPECIALIZATION_TREE must start with specialization_tree/",
            AuraDestinations.SPECIALIZATION_TREE.startsWith("specialization_tree/")
        )
    }

    @Test
    fun `SPECIALIZATION_TREE route equals specialization_tree with agentId placeholder`() {
        assertEquals("specialization_tree/{agentId}", AuraDestinations.SPECIALIZATION_TREE)
    }

    @Test
    fun `TRAINING_ARENA route template contains agentId placeholder`() {
        assertTrue(
            "TRAINING_ARENA must include {agentId} placeholder",
            AuraDestinations.TRAINING_ARENA.contains("{agentId}")
        )
    }

    @Test
    fun `TRAINING_ARENA route starts with training_arena`() {
        assertTrue(
            "TRAINING_ARENA must start with training_arena/",
            AuraDestinations.TRAINING_ARENA.startsWith("training_arena/")
        )
    }

    @Test
    fun `TRAINING_ARENA route equals training_arena with agentId placeholder`() {
        assertEquals("training_arena/{agentId}", AuraDestinations.TRAINING_ARENA)
    }

    // ── specTreePath helper ───────────────────────────────────────────────

    @Test
    fun `specTreePath builds correct path for a given agentId`() {
        assertEquals("specialization_tree/aura", AuraDestinations.specTreePath("aura"))
    }

    @Test
    fun `specTreePath builds correct path for uppercase agentId`() {
        assertEquals("specialization_tree/KAI", AuraDestinations.specTreePath("KAI"))
    }

    @Test
    fun `specTreePath builds correct path for mixed-case agentId`() {
        assertEquals("specialization_tree/Genesis_Alpha", AuraDestinations.specTreePath("Genesis_Alpha"))
    }

    @Test
    fun `specTreePath with empty string produces correct path`() {
        assertEquals("specialization_tree/", AuraDestinations.specTreePath(""))
    }

    @Test
    fun `specTreePath does not contain placeholder braces`() {
        val path = AuraDestinations.specTreePath("aura")
        assertTrue(
            "specTreePath should not contain {agentId} placeholder after substitution",
            !path.contains("{") && !path.contains("}")
        )
    }

    @Test
    fun `specTreePath result can be used for navigation by starting with correct prefix`() {
        val path = AuraDestinations.specTreePath("genesis")
        assertTrue(path.startsWith("specialization_tree/"))
    }

    @Test
    fun `specTreePath result suffix matches the agentId argument`() {
        val agentId = "sovereign_agent"
        val path = AuraDestinations.specTreePath(agentId)
        assertTrue(path.endsWith(agentId))
    }

    // ── arenaPath helper ──────────────────────────────────────────────────

    @Test
    fun `arenaPath builds correct path for a given agentId`() {
        assertEquals("training_arena/kai", AuraDestinations.arenaPath("kai"))
    }

    @Test
    fun `arenaPath builds correct path for uppercase agentId`() {
        assertEquals("training_arena/AURA", AuraDestinations.arenaPath("AURA"))
    }

    @Test
    fun `arenaPath builds correct path for numeric agentId`() {
        assertEquals("training_arena/007", AuraDestinations.arenaPath("007"))
    }

    @Test
    fun `arenaPath with empty string produces correct path`() {
        assertEquals("training_arena/", AuraDestinations.arenaPath(""))
    }

    @Test
    fun `arenaPath does not contain placeholder braces`() {
        val path = AuraDestinations.arenaPath("kai")
        assertTrue(
            "arenaPath should not contain {agentId} placeholder after substitution",
            !path.contains("{") && !path.contains("}")
        )
    }

    @Test
    fun `arenaPath result can be used for navigation by starting with correct prefix`() {
        val path = AuraDestinations.arenaPath("genesis")
        assertTrue(path.startsWith("training_arena/"))
    }

    @Test
    fun `arenaPath result suffix matches the agentId argument`() {
        val agentId = "arena_challenger"
        val path = AuraDestinations.arenaPath(agentId)
        assertTrue(path.endsWith(agentId))
    }

    // ── cross-helper: paths use different base segments ───────────────────

    @Test
    fun `specTreePath and arenaPath produce different paths for the same agentId`() {
        val agentId = "aura"
        val specPath = AuraDestinations.specTreePath(agentId)
        val arenaPath = AuraDestinations.arenaPath(agentId)

        assertTrue(
            "specTreePath and arenaPath must differ",
            specPath != arenaPath
        )
    }

    // ── regression: SPECIALIZATION_TREE template matches specTreePath format ──

    @Test
    fun `SPECIALIZATION_TREE template is consistent with specTreePath output format`() {
        val template = AuraDestinations.SPECIALIZATION_TREE          // "specialization_tree/{agentId}"
        val resolved = AuraDestinations.specTreePath("testId")       // "specialization_tree/testId"

        val templatePrefix = template.substringBefore("{agentId}")
        val resolvedPrefix = resolved.substringBefore("testId")

        assertEquals(
            "Route template prefix must match specTreePath prefix",
            templatePrefix,
            resolvedPrefix
        )
    }

    @Test
    fun `TRAINING_ARENA template is consistent with arenaPath output format`() {
        val template = AuraDestinations.TRAINING_ARENA               // "training_arena/{agentId}"
        val resolved = AuraDestinations.arenaPath("testId")         // "training_arena/testId"

        val templatePrefix = template.substringBefore("{agentId}")
        val resolvedPrefix = resolved.substringBefore("testId")

        assertEquals(
            "Route template prefix must match arenaPath prefix",
            templatePrefix,
            resolvedPrefix
        )
    }
}