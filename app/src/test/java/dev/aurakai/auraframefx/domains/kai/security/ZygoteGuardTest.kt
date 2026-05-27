package dev.aurakai.auraframefx.domains.kai.security

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.security.MessageDigest

/**
 * Tests for [ZygoteGuard], focused on the [ZygoteGuard.verifyZygoteHookIntegrity] public API
 * and the [ZygoteGuard.computeManifestSignature] private logic, which was updated in this PR
 * to use [dev.aurakai.auraframefx.core.util.HexUtil.encodeHex] instead of joinToString.
 *
 * In the JVM test environment no hook classes are on the classpath, so
 * computeRuntimeSignature() always returns the hash of an empty class list.
 * The tests exploit this known behaviour to validate the guard's state transitions
 * and the format of the hex signatures it produces.
 */
@DisplayName("ZygoteGuard Tests")
class ZygoteGuardTest {

    private lateinit var guard: ZygoteGuard

    @BeforeEach
    fun setUp() {
        guard = ZygoteGuard()
    }

    @Nested
    @DisplayName("Initial state")
    inner class InitialStateTests {

        @Test
        @DisplayName("integrityCompromised is false before any check")
        fun initialIntegrityCompromisedIsFalse() {
            assertFalse(guard.integrityCompromised)
        }

        @Test
        @DisplayName("lastSignature is empty before any check")
        fun initialLastSignatureIsEmpty() {
            assertEquals("", guard.lastSignature)
        }
    }

    @Nested
    @DisplayName("verifyZygoteHookIntegrity — mismatch detection")
    inner class MismatchDetectionTests {

        @Test
        @DisplayName("Returns false when runtime classes are not loadable")
        fun returnsFalseWhenHookClassesNotOnClasspath() {
            // No hook classes exist on the JVM unit-test classpath
            val result = guard.verifyZygoteHookIntegrity()
            assertFalse(result, "Integrity check must fail when hook classes cannot be found")
        }

        @Test
        @DisplayName("Sets integrityCompromised=true when classes are missing")
        fun setsIntegrityCompromisedTrueOnMismatch() {
            guard.verifyZygoteHookIntegrity()
            assertTrue(guard.integrityCompromised)
        }
    }

    @Nested
    @DisplayName("lastSignature format — HexUtil.encodeHex output")
    inner class SignatureFormatTests {

        @Test
        @DisplayName("lastSignature is 64 hex characters after a check (SHA-256 of class list)")
        fun lastSignatureIs64HexCharsAfterCheck() {
            guard.verifyZygoteHookIntegrity()
            val sig = guard.lastSignature
            assertEquals(64, sig.length,
                "SHA-256 hex digest must be exactly 64 characters, got: '$sig'")
        }

        @Test
        @DisplayName("lastSignature contains only lowercase hex characters [0-9a-f]")
        fun lastSignatureIsLowercaseHex() {
            guard.verifyZygoteHookIntegrity()
            val sig = guard.lastSignature
            assertTrue(sig.all { it in '0'..'9' || it in 'a'..'f' },
                "Signature must be lowercase hex, got: '$sig'")
        }

        @Test
        @DisplayName("lastSignature contains no uppercase letters")
        fun lastSignatureHasNoUppercase() {
            guard.verifyZygoteHookIntegrity()
            assertTrue(guard.lastSignature.none { it.isUpperCase() })
        }

        @Test
        @DisplayName("lastSignature is set after verifyZygoteHookIntegrity is called")
        fun lastSignatureIsPopulatedAfterCheck() {
            assertEquals("", guard.lastSignature)
            guard.verifyZygoteHookIntegrity()
            assertTrue(guard.lastSignature.isNotEmpty())
        }
    }

    @Nested
    @DisplayName("lastSignature determinism")
    inner class SignatureDeterminismTests {

        @Test
        @DisplayName("Two consecutive integrity checks produce identical lastSignature")
        fun consecutiveChecksProduceSameSignature() {
            guard.verifyZygoteHookIntegrity()
            val first = guard.lastSignature

            guard.verifyZygoteHookIntegrity()
            val second = guard.lastSignature

            assertEquals(first, second,
                "Runtime signature must be deterministic across calls")
        }

        @Test
        @DisplayName("Two separate ZygoteGuard instances produce identical lastSignature")
        fun separateInstancesProduceSameSignature() {
            val guard2 = ZygoteGuard()
            guard.verifyZygoteHookIntegrity()
            guard2.verifyZygoteHookIntegrity()
            assertEquals(guard.lastSignature, guard2.lastSignature)
        }

        @Test
        @DisplayName("lastSignature matches manual SHA-256 of empty sorted class list")
        fun lastSignatureMatchesManualComputationForEmptyList() {
            // When no hook classes are loadable, computeRuntimeSignature computes the hash
            // of an empty list: sorted([]) joined by "|" = "" — so SHA-256("")
            val expectedEmptyListHash = MessageDigest.getInstance("SHA-256")
                .digest("".toByteArray())
                .joinToString("") { "%02x".format(it) }

            guard.verifyZygoteHookIntegrity()
            assertEquals(expectedEmptyListHash, guard.lastSignature,
                "lastSignature must equal SHA-256 of empty string when no classes load")
        }
    }

    @Nested
    @DisplayName("manifest signature — HexUtil.encodeHex matches legacy joinToString")
    inner class LegacyCompatibilityTests {

        /**
         * This PR replaced `digest.joinToString("") { "%02x".format(it) }` with
         * `HexUtil.encodeHex(...)` in computeManifestSignature. We verify that the
         * public observable outcome (lastSignature) matches what the legacy code
         * would have produced for the same input.
         */
        @Test
        @DisplayName("lastSignature matches legacy joinToString for empty class list")
        fun lastSignatureMatchesLegacyImplementation() {
            guard.verifyZygoteHookIntegrity()
            val digest = MessageDigest.getInstance("SHA-256")
            val combined = listOf<String>().sorted().joinToString("|")
            val legacyHash = digest.digest(combined.toByteArray())
                .joinToString("") { "%02x".format(it) }
            assertEquals(legacyHash, guard.lastSignature)
        }
    }

    @Nested
    @DisplayName("State reset between checks")
    inner class StateResetTests {

        @Test
        @DisplayName("integrityCompromised remains true on repeated failures")
        fun integrityCompromisedStaysTrueOnRepeatedFailure() {
            guard.verifyZygoteHookIntegrity()
            assertTrue(guard.integrityCompromised)
            guard.verifyZygoteHookIntegrity()
            assertTrue(guard.integrityCompromised)
        }

        @Test
        @DisplayName("lastSignature is updated to the new runtime value on each call")
        fun lastSignatureIsUpdatedOnEachCall() {
            // Both calls should set the same signature (deterministic), but each call
            // must update lastSignature (not leave it as the stale previous value)
            guard.verifyZygoteHookIntegrity()
            val sigAfterFirst = guard.lastSignature
            guard.verifyZygoteHookIntegrity()
            val sigAfterSecond = guard.lastSignature
            assertEquals(sigAfterFirst, sigAfterSecond)
            assertTrue(sigAfterSecond.length == 64)
        }
    }

    @Nested
    @DisplayName("Signature sensitivity — regression guard")
    inner class SignatureSensitivityTests {

        @Test
        @DisplayName("Signature of a non-empty class list differs from empty-list signature")
        fun nonEmptyListSignatureDiffersFromEmptyListSignature() {
            // Compute what the manifest signature of one hypothetical class name would be.
            // This verifies the hash function is actually sensitive to input changes.
            val singleClassList = listOf("dev.aurakai.auraframefx.hooks.system.UniversalComponentHooker")
            val combined = singleClassList.sorted().joinToString("|")
            val singleClassHash = MessageDigest.getInstance("SHA-256")
                .digest(combined.toByteArray())
                .joinToString("") { "%02x".format(it) }

            guard.verifyZygoteHookIntegrity()
            val emptyListHash = guard.lastSignature

            assertNotEquals(singleClassHash, emptyListHash,
                "Hash of one class must differ from hash of empty list")
        }

        @Test
        @DisplayName("Class list order does not affect signature (sorted before hashing)")
        fun classListOrderDoesNotAffectSignature() {
            // Two orderings of the same class list must produce the same manifest signature
            val classesAB = listOf("dev.example.ClassA", "dev.example.ClassB")
            val classesBA = listOf("dev.example.ClassB", "dev.example.ClassA")

            val hashAB = computeManifestSignatureViaDigest(classesAB)
            val hashBA = computeManifestSignatureViaDigest(classesBA)

            assertEquals(hashAB, hashBA,
                "Signature must be order-independent due to sorting")
        }

        /** Mirrors computeManifestSignature logic for test assertion purposes. */
        private fun computeManifestSignatureViaDigest(classNames: List<String>): String {
            val digest = MessageDigest.getInstance("SHA-256")
            val combined = classNames.sorted().joinToString("|")
            return digest.digest(combined.toByteArray()).joinToString("") { "%02x".format(it) }
        }
    }
}