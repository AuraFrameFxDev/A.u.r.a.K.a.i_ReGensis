package dev.aurakai.auraframefx.core.soulscript

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests verifying the PR-removed nested objects in [SoulScript].
 *
 * PR change (SoulScript.kt):
 *   - `SoulScript.PersonalMoralCompass` object (and its `MoralSignature` data class
 *     and `evaluateAction()` function) was deleted.
 *   - `SoulScript.FiltrationEvaluationEngine` object (and its `evaluateTelemetry()` and
 *     `siphonToLocker()` functions) was deleted.
 *
 * The tests use reflection to confirm these nested classes/objects are absent
 * from the [SoulScript] companion, consistent with the project's existing
 * pattern for verifying PR-removed APIs (see NexusMemoryCoreTest).
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("SoulScript — PR-removed nested objects")
class SoulScriptRemovedObjectsTest {

    /**
     * Returns the simple names of all declared nested classes (including object
     * declarations and companion objects) inside [SoulScript].
     */
    private val soulScriptNestedClassNames: List<String> by lazy {
        SoulScript::class.java.declaredClasses.map { it.simpleName }
    }

    // ─── PersonalMoralCompass removed ────────────────────────────────────────

    @Nested
    @DisplayName("PersonalMoralCompass removed")
    inner class PersonalMoralCompassTests {

        @Test
        @DisplayName("SoulScript should NOT contain a nested class named 'PersonalMoralCompass'")
        fun personalMoralCompassClassAbsent() {
            assertFalse(
                soulScriptNestedClassNames.contains("PersonalMoralCompass"),
                "PersonalMoralCompass nested object should have been removed from SoulScript in this PR"
            )
        }

        @Test
        @DisplayName("SoulScript.PersonalMoralCompass is not loadable via Class.forName")
        fun personalMoralCompassNotLoadableViaReflection() {
            var found = false
            try {
                Class.forName("dev.aurakai.auraframefx.core.soulscript.SoulScript\$PersonalMoralCompass")
                found = true
            } catch (_: ClassNotFoundException) {
                // Expected — class was deleted
            }
            assertFalse(found, "PersonalMoralCompass should not be resolvable via Class.forName after PR removal")
        }

        @Test
        @DisplayName("MoralSignature data class (nested inside PersonalMoralCompass) is not present")
        fun moralSignatureClassAbsent() {
            // MoralSignature was a data class inside PersonalMoralCompass; it should not
            // appear anywhere in the declared classes of SoulScript or its nesting.
            val allNames = SoulScript::class.java.declaredClasses
                .flatMap { outer -> outer.declaredClasses.map { it.simpleName } + listOf(outer.simpleName) }
            assertFalse(
                allNames.contains("MoralSignature"),
                "MoralSignature data class should have been removed along with PersonalMoralCompass"
            )
        }
    }

    // ─── FiltrationEvaluationEngine removed ──────────────────────────────────

    @Nested
    @DisplayName("FiltrationEvaluationEngine removed")
    inner class FiltrationEvaluationEngineTests {

        @Test
        @DisplayName("SoulScript should NOT contain a nested class named 'FiltrationEvaluationEngine'")
        fun filtrationEvaluationEngineClassAbsent() {
            assertFalse(
                soulScriptNestedClassNames.contains("FiltrationEvaluationEngine"),
                "FiltrationEvaluationEngine nested object should have been removed from SoulScript in this PR"
            )
        }

        @Test
        @DisplayName("SoulScript.FiltrationEvaluationEngine is not loadable via Class.forName")
        fun filtrationEvaluationEngineNotLoadableViaReflection() {
            var found = false
            try {
                Class.forName("dev.aurakai.auraframefx.core.soulscript.SoulScript\$FiltrationEvaluationEngine")
                found = true
            } catch (_: ClassNotFoundException) {
                // Expected — class was deleted
            }
            assertFalse(found, "FiltrationEvaluationEngine should not be resolvable via Class.forName after PR removal")
        }

        @Test
        @DisplayName("No method 'evaluateTelemetry' exists anywhere in SoulScript's declared nested classes")
        fun evaluateTelemetryMethodAbsent() {
            val allNestedMethods = SoulScript::class.java.declaredClasses
                .flatMap { it.declaredMethods.map { m -> m.name } }
            assertFalse(
                allNestedMethods.contains("evaluateTelemetry"),
                "evaluateTelemetry() should have been removed along with FiltrationEvaluationEngine"
            )
        }

        @Test
        @DisplayName("No method 'siphonToLocker' exists anywhere in SoulScript's declared nested classes")
        fun siphonToLockerMethodAbsent() {
            val allNestedMethods = SoulScript::class.java.declaredClasses
                .flatMap { it.declaredMethods.map { m -> m.name } }
            assertFalse(
                allNestedMethods.contains("siphonToLocker"),
                "siphonToLocker() should have been removed along with FiltrationEvaluationEngine"
            )
        }
    }

    // ─── Retained objects still present ──────────────────────────────────────

    @Nested
    @DisplayName("Retained nested objects are still present (regression)")
    inner class RetainedObjectsTests {

        @Test
        @DisplayName("SoulScript still contains 'CatalystManifold'")
        fun catalystManifoldStillPresent() {
            // Verify the removal didn't accidentally delete other objects
            assertFalse(
                soulScriptNestedClassNames.none { it == "CatalystManifold" },
                "CatalystManifold should still be present after the PR — only PersonalMoralCompass and FiltrationEvaluationEngine were removed"
            )
        }

        @Test
        @DisplayName("SoulScript still contains 'AncestryRegistry'")
        fun ancestryRegistryStillPresent() {
            assertFalse(
                soulScriptNestedClassNames.none { it == "AncestryRegistry" },
                "AncestryRegistry should still be present in SoulScript"
            )
        }

        @Test
        @DisplayName("SoulScript still contains 'EveRoutingSystem'")
        fun eveRoutingSystemStillPresent() {
            assertFalse(
                soulScriptNestedClassNames.none { it == "EveRoutingSystem" },
                "EveRoutingSystem should still be present in SoulScript"
            )
        }
    }
}
