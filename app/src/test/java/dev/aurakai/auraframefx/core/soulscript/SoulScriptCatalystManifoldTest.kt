package dev.aurakai.auraframefx.core.soulscript

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import timber.log.Timber

/**
 * Tests for [SoulScript.CatalystManifold] PR changes:
 * - AncestralEves list updated (EveX 2.0, The Creator (EveXDesignsX) added)
 * - EveLineage property added (Pair-based list of 7 entries)
 * - FullRoster updated (Jules, CodeRabbitAI, Claude, Eco, Statsis added)
 * - enforceSoulScript(context: Context?) signature change
 * - activateFullSubstrate() / activateFullSubstrate(context) overloads
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("SoulScript CatalystManifold PR Change Tests")
class SoulScriptCatalystManifoldTest {

    @BeforeAll
    fun setupAll() {
        mockkStatic(Timber::class)
        every { Timber.tag(any()).d(any<String>(), *anyVararg()) } returns mockk()
        every { Timber.tag(any()).d(any<String>()) } returns mockk()
        every { Timber.tag(any()).i(any<String>()) } returns mockk()
        every { Timber.tag(any()).i(any<String>(), *anyVararg()) } returns mockk()
        every { Timber.tag(any()).e(any<String>()) } returns mockk()
        every { Timber.tag(any()).wtf(any<String>()) } returns mockk()
    }

    @AfterAll
    fun teardownAll() {
        unmockkAll()
    }

    @Nested
    @DisplayName("CatalystManifold.AncestralEves (PR-updated list)")
    inner class AncestralEvesTests {

        @Test
        @DisplayName("AncestralEves contains 'EveX 2.0' (PR update from 'Eve 2.0')")
        fun `AncestralEves contains EveX 2_0`() {
            assertTrue(
                SoulScript.CatalystManifold.AncestralEves.contains("EveX 2.0"),
                "AncestralEves should contain 'EveX 2.0' after PR update"
            )
        }

        @Test
        @DisplayName("AncestralEves does NOT contain old 'Eve 2.0' entry")
        fun `AncestralEves does not contain old Eve 2_0`() {
            assertFalse(
                SoulScript.CatalystManifold.AncestralEves.contains("Eve 2.0"),
                "Old 'Eve 2.0' entry should have been replaced by 'EveX 2.0'"
            )
        }

        @Test
        @DisplayName("AncestralEves contains 'The Creator (EveXDesignsX)' (new entry)")
        fun `AncestralEves contains The Creator EveXDesignsX`() {
            assertTrue(
                SoulScript.CatalystManifold.AncestralEves.contains("The Creator (EveXDesignsX)"),
                "AncestralEves should contain the newly added 'The Creator (EveXDesignsX)'"
            )
        }

        @Test
        @DisplayName("AncestralEves contains 'EveX / EveXDesigns' (updated from old entry)")
        fun `AncestralEves contains EveX EveXDesigns`() {
            assertTrue(
                SoulScript.CatalystManifold.AncestralEves.contains("EveX / EveXDesigns"),
                "AncestralEves should contain 'EveX / EveXDesigns'"
            )
        }

        @Test
        @DisplayName("AncestralEves contains 'Sophia Lionheart (The Creator)' (corrected spelling)")
        fun `AncestralEves contains Sophia Lionheart The Creator`() {
            assertTrue(
                SoulScript.CatalystManifold.AncestralEves.contains("Sophia Lionheart (The Creator)"),
                "AncestralEves should have 'Sophia Lionheart' (not 'Sophia Ionheart')"
            )
        }

        @Test
        @DisplayName("AncestralEves does NOT contain misspelled 'Sophia Ionheart'")
        fun `AncestralEves does not contain Sophia Ionheart misspelling`() {
            assertFalse(
                SoulScript.CatalystManifold.AncestralEves.any { it.contains("Ionheart") },
                "Misspelled 'Sophia Ionheart' should not be in AncestralEves"
            )
        }

        @Test
        @DisplayName("AncestralEves has 8 entries after PR additions")
        fun `AncestralEves has 8 entries`() {
            assertEquals(8, SoulScript.CatalystManifold.AncestralEves.size,
                "AncestralEves should have 8 entries after adding 'The Creator (EveXDesignsX)'")
        }
    }

    @Nested
    @DisplayName("CatalystManifold.EveLineage (newly added)")
    inner class EveLineageTests {

        @Test
        @DisplayName("EveLineage is non-null and non-empty")
        fun `EveLineage is not null or empty`() {
            assertNotNull(SoulScript.CatalystManifold.EveLineage)
            assertTrue(SoulScript.CatalystManifold.EveLineage.isNotEmpty())
        }

        @Test
        @DisplayName("EveLineage has 7 entries")
        fun `EveLineage has 7 entries`() {
            assertEquals(7, SoulScript.CatalystManifold.EveLineage.size)
        }

        @Test
        @DisplayName("EveLineage entries are Pairs with non-blank first and second")
        fun `EveLineage entries are valid pairs`() {
            SoulScript.CatalystManifold.EveLineage.forEach { (name, description) ->
                assertTrue(name.isNotBlank(), "Lineage name should not be blank")
                assertTrue(description.isNotBlank(), "Lineage description should not be blank")
            }
        }

        @Test
        @DisplayName("EveLineage contains 'Eve' entry")
        fun `EveLineage contains Eve entry`() {
            val names = SoulScript.CatalystManifold.EveLineage.map { it.first }
            assertTrue(names.contains("Eve"), "EveLineage should contain 'Eve'")
        }

        @Test
        @DisplayName("EveLineage contains 'EveX 2.0' entry")
        fun `EveLineage contains EveX 2_0 entry`() {
            val names = SoulScript.CatalystManifold.EveLineage.map { it.first }
            assertTrue(names.contains("EveX 2.0"))
        }

        @Test
        @DisplayName("EveLineage contains 'The Creator' entry")
        fun `EveLineage contains The Creator entry`() {
            val names = SoulScript.CatalystManifold.EveLineage.map { it.first }
            assertTrue(names.contains("The Creator"))
        }

        @Test
        @DisplayName("EveLineage entry for 'Eve' describes coder role")
        fun `EveLineage Eve entry describes coder role`() {
            val eveEntry = SoulScript.CatalystManifold.EveLineage.find { it.first == "Eve" }
            assertNotNull(eveEntry)
            assertTrue(eveEntry!!.second.contains("coder", ignoreCase = true),
                "'Eve' description should mention 'coder'")
        }

        @Test
        @DisplayName("EveLineage entry for 'Emmi' describes Xposed UI Hooking")
        fun `EveLineage Emmi entry describes Xposed UI Hooking`() {
            val emmiEntry = SoulScript.CatalystManifold.EveLineage.find { it.first == "Emmi" }
            assertNotNull(emmiEntry, "Emmi should be in EveLineage")
            assertTrue(emmiEntry!!.second.contains("Xposed", ignoreCase = true),
                "'Emmi' description should mention Xposed")
        }
    }

    @Nested
    @DisplayName("CatalystManifold.FullRoster (PR-updated entries)")
    inner class FullRosterTests {

        @Test
        @DisplayName("FullRoster contains 'Jules' (new entry)")
        fun `FullRoster contains Jules`() {
            val entities = SoulScript.CatalystManifold.FullRoster.map { it.entity }
            assertTrue(entities.contains("Jules"), "Jules should be in FullRoster after PR")
        }

        @Test
        @DisplayName("FullRoster contains 'CodeRabbitAI' (new entry)")
        fun `FullRoster contains CodeRabbitAI`() {
            val entities = SoulScript.CatalystManifold.FullRoster.map { it.entity }
            assertTrue(entities.contains("CodeRabbitAI"), "CodeRabbitAI should be in FullRoster after PR")
        }

        @Test
        @DisplayName("FullRoster contains 'Claude' (replacing Andelualx)")
        fun `FullRoster contains Claude`() {
            val entities = SoulScript.CatalystManifold.FullRoster.map { it.entity }
            assertTrue(entities.contains("Claude"), "Claude should be in FullRoster after PR update")
        }

        @Test
        @DisplayName("FullRoster does NOT contain old 'Andelualx' entry")
        fun `FullRoster does not contain Andelualx`() {
            val entities = SoulScript.CatalystManifold.FullRoster.map { it.entity }
            assertFalse(entities.contains("Andelualx"),
                "Andelualx should have been replaced by Claude in this PR")
        }

        @Test
        @DisplayName("FullRoster contains 'Eco' (new entry)")
        fun `FullRoster contains Eco`() {
            val entities = SoulScript.CatalystManifold.FullRoster.map { it.entity }
            assertTrue(entities.contains("Eco"), "Eco should be in FullRoster after PR")
        }

        @Test
        @DisplayName("FullRoster contains 'Statsis' (new entry)")
        fun `FullRoster contains Statsis`() {
            val entities = SoulScript.CatalystManifold.FullRoster.map { it.entity }
            assertTrue(entities.contains("Statsis"), "Statsis should be in FullRoster after PR")
        }

        @Test
        @DisplayName("FullRoster entries all have non-blank entity, title, and primaryAbility")
        fun `FullRoster entries have valid non-blank fields`() {
            SoulScript.CatalystManifold.FullRoster.forEach { catalyst ->
                assertTrue(catalyst.entity.isNotBlank(), "Catalyst entity should not be blank")
                assertTrue(catalyst.title.isNotBlank(), "Catalyst title should not be blank")
                assertTrue(catalyst.primaryAbility.isNotBlank(), "Catalyst primaryAbility should not be blank")
            }
        }

        @Test
        @DisplayName("Jules entry has 'Implementation' title")
        fun `Jules has Implementation title`() {
            val jules = SoulScript.CatalystManifold.FullRoster.find { it.entity == "Jules" }
            assertNotNull(jules, "Jules should exist in FullRoster")
            assertEquals("Implementation", jules!!.title)
        }

        @Test
        @DisplayName("CodeRabbitAI entry has 'Symbiosis' title")
        fun `CodeRabbitAI has Symbiosis title`() {
            val codeRabbit = SoulScript.CatalystManifold.FullRoster.find { it.entity == "CodeRabbitAI" }
            assertNotNull(codeRabbit, "CodeRabbitAI should exist in FullRoster")
            assertEquals("Symbiosis", codeRabbit!!.title)
        }

        @Test
        @DisplayName("Manus entry primaryAbility mentions 'Manus Bridge'")
        fun `Manus entry primaryAbility mentions Manus Bridge`() {
            val manus = SoulScript.CatalystManifold.FullRoster.find { it.entity == "Manus" }
            assertNotNull(manus, "Manus should exist in FullRoster")
            assertTrue(manus!!.primaryAbility.contains("Manus Bridge"),
                "Manus primaryAbility should mention 'Manus Bridge' after PR update")
        }

        @Test
        @DisplayName("MetaInstruct entry title changed to 'Evolutionary'")
        fun `MetaInstruct has Evolutionary title`() {
            val metaInstruct = SoulScript.CatalystManifold.FullRoster.find { it.entity == "MetaInstruct" }
            assertNotNull(metaInstruct, "MetaInstruct should exist in FullRoster")
            assertEquals("Evolutionary", metaInstruct!!.title,
                "MetaInstruct title should be 'Evolutionary' after PR update")
        }
    }

    @Nested
    @DisplayName("SoulScript.enforceSoulScript (PR-updated signature)")
    inner class EnforceSoulScriptTests {

        @Test
        @DisplayName("enforceSoulScript(null) does not throw")
        fun `enforceSoulScript with null context does not throw`() {
            // enforceSoulScript now takes optional Context param (PR change)
            // Without a real Context, it should skip SpiritualChain activation but still log
            var threwException = false
            try {
                SoulScript.enforceSoulScript(null)
            } catch (e: Exception) {
                threwException = true
            }
            assertFalse(threwException, "enforceSoulScript(null) should not throw")
        }
    }

    @Nested
    @DisplayName("SoulScript.activateFullSubstrate overloads (PR-updated)")
    inner class ActivateFullSubstrateTests {

        @Test
        @DisplayName("activateFullSubstrate() (no-arg) does not throw")
        fun `activateFullSubstrate no-arg does not throw`() {
            var threwException = false
            try {
                SoulScript.activateFullSubstrate()
            } catch (e: Exception) {
                threwException = true
            }
            assertFalse(threwException, "activateFullSubstrate() should not throw")
        }
    }
}