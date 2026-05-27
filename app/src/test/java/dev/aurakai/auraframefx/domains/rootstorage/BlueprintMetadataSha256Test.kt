package dev.aurakai.auraframefx.domains.rootstorage

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.security.MessageDigest

/**
 * Tests for the [sha256] top-level function in BlueprintMetadata.kt, which was updated
 * in this PR to delegate hex encoding to [dev.aurakai.auraframefx.core.util.HexUtil.encodeHex].
 */
@DisplayName("sha256 function Tests (BlueprintMetadata.kt)")
class BlueprintMetadataSha256Test {

    @Nested
    @DisplayName("Output format")
    inner class OutputFormatTests {

        @Test
        @DisplayName("Returns a 64-character string for any input")
        fun returnsExactly64Characters() {
            assertEquals(64, sha256("").length)
            assertEquals(64, sha256("hello").length)
            assertEquals(64, sha256("AuraKAI security path").length)
        }

        @Test
        @DisplayName("Output contains only lowercase hex characters [0-9a-f]")
        fun outputIsLowercaseHex() {
            val result = sha256("test input for lowercase check")
            assertTrue(result.all { it in '0'..'9' || it in 'a'..'f' },
                "sha256 output must be lowercase hex, got: $result")
        }

        @Test
        @DisplayName("Output never contains uppercase letters")
        fun outputHasNoUppercaseLetters() {
            val inputs = listOf("", "AaBbCcDd", "UPPERCASE", "MixedCase123")
            for (input in inputs) {
                val result = sha256(input)
                assertTrue(result.none { it.isUpperCase() },
                    "sha256('$input') must not contain uppercase: $result")
            }
        }
    }

    @Nested
    @DisplayName("Known SHA-256 vectors")
    inner class KnownVectorTests {

        @Test
        @DisplayName("sha256('') matches NIST well-known vector")
        fun emptyStringMatchesWellKnownVector() {
            // NIST FIPS 180-4 vector for SHA-256("")
            assertEquals(
                "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
                sha256("")
            )
        }

        @Test
        @DisplayName("sha256('hello') matches well-known vector")
        fun helloStringMatchesWellKnownVector() {
            assertEquals(
                "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824",
                sha256("hello")
            )
        }

        @Test
        @DisplayName("sha256('abc') matches well-known vector")
        fun abcStringMatchesWellKnownVector() {
            assertEquals(
                "ba7816bf8f01cfea414140de5dae2ec73b00361bbef0469fa5d8d44e6b085d18",
                sha256("abc")
            )
        }

        @Test
        @DisplayName("sha256 output matches direct MessageDigest computation")
        fun matchesDirectMessageDigestComputation() {
            val inputs = listOf(
                "",
                "hello",
                "regenesis-salt-v2.4",
                "AuraKAI",
                "regenesis-salt-v2.4some-uuid1234567890ANDELUALXsome-score"
            )
            for (input in inputs) {
                val expected = MessageDigest.getInstance("SHA-256")
                    .digest(input.toByteArray())
                    .joinToString("") { "%02x".format(it) }
                assertEquals(expected, sha256(input),
                    "sha256('$input') must match direct MessageDigest computation")
            }
        }
    }

    @Nested
    @DisplayName("Determinism and uniqueness")
    inner class DeterminismTests {

        @Test
        @DisplayName("Same input always produces the same output")
        fun sameInputProducesSameOutput() {
            val input = "regenesis-salt-v2.4test-id-123"
            assertEquals(sha256(input), sha256(input))
        }

        @Test
        @DisplayName("Different inputs produce different outputs")
        fun differentInputsProduceDifferentOutputs() {
            assertNotEquals(sha256("input1"), sha256("input2"))
            assertNotEquals(sha256(""), sha256(" "))
            assertNotEquals(sha256("abc"), sha256("ABC"))
        }

        @Test
        @DisplayName("Inputs differing by a single character produce different outputs")
        fun singleCharacterDiffProducesDifferentHash() {
            assertNotEquals(sha256("regenesis-salt-v2.4"), sha256("regenesis-salt-v2.5"))
        }

        @ParameterizedTest
        @ValueSource(strings = [
            "regenesis-salt-v2.4",
            "hello world",
            "AuraKAI",
            "0000000000000000",
            "ffffffffffffffffffffffffffffffff"
        ])
        @DisplayName("Output is consistently 64-char lowercase hex for various inputs")
        fun consistentFormatAcrossInputs(input: String) {
            val result = sha256(input)
            assertEquals(64, result.length)
            assertTrue(result.all { it in '0'..'9' || it in 'a'..'f' })
        }
    }

    @Nested
    @DisplayName("HexUtil.encodeHex integration — matches legacy joinToString behavior")
    inner class LegacyCompatibilityTests {

        /**
         * This PR replaced `bytes.joinToString("") { "%02x".format(it) }` with
         * `HexUtil.encodeHex(bytes)`. These tests verify functional equivalence.
         */
        @Test
        @DisplayName("sha256 output matches old joinToString format string implementation")
        fun matchesOldJoinToStringImplementation() {
            val inputs = listOf("", "hello", "test", "regenesis-salt-v2.4abc123")
            for (input in inputs) {
                val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
                val legacyResult = bytes.joinToString("") { "%02x".format(it) }
                assertEquals(legacyResult, sha256(input),
                    "sha256('$input') must match legacy joinToString result")
            }
        }
    }

    @Nested
    @DisplayName("BlueprintMetadata provenance hash integration")
    inner class ProvenanceHashIntegrationTests {

        @Test
        @DisplayName("sha256 used in provenance hash produces correct format")
        fun provenanceHashFormatIsValid() {
            // Simulate what computeProvenanceHash does: sha256(salt + id + timestamp + createdBy + fusionScore)
            val salt = "regenesis-salt-v2.4"
            val id = "some-uuid-value-1234"
            val timestamp = 1700000000000L
            val createdBy = "ANDELUALX"
            val fusionScore = 0.0
            val combined = "$salt$id$timestamp$createdBy$fusionScore"

            val result = sha256(combined)
            assertEquals(64, result.length)
            assertTrue(result.all { it in '0'..'9' || it in 'a'..'f' })
        }

        @Test
        @DisplayName("Provenance hash is sensitive to salt prefix changes")
        fun provenanceHashSensitiveToSaltChange() {
            val id = "test-id"
            val timestamp = 1000L
            val createdBy = "ANDELUALX"
            val fusionScore = 1.0
            val hash24 = sha256("regenesis-salt-v2.4$id$timestamp$createdBy$fusionScore")
            val hash25 = sha256("regenesis-salt-v2.5$id$timestamp$createdBy$fusionScore")
            assertNotEquals(hash24, hash25)
        }

        @Test
        @DisplayName("Provenance hash is sensitive to fusionScore change")
        fun provenanceHashSensitiveToFusionScoreChange() {
            val prefix = "regenesis-salt-v2.4uuid111700000000ANDELUALX"
            val hash0 = sha256("${prefix}0.0")
            val hash1 = sha256("${prefix}1.0")
            assertNotEquals(hash0, hash1)
        }
    }
}
