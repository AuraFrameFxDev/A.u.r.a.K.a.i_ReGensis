package dev.aurakai.auraframefx.core.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@DisplayName("HexUtil Tests")
class HexUtilTest {

    @Nested
    @DisplayName("encodeHex — basic correctness")
    inner class BasicCorrectnessTests {

        @Test
        @DisplayName("Empty byte array encodes to empty string")
        fun emptyByteArrayReturnsEmptyString() {
            assertEquals("", HexUtil.encodeHex(byteArrayOf()))
        }

        @Test
        @DisplayName("Single zero byte encodes to '00'")
        fun singleZeroByteEncodesToDoubleZero() {
            assertEquals("00", HexUtil.encodeHex(byteArrayOf(0x00)))
        }

        @Test
        @DisplayName("Single 0xFF byte encodes to 'ff' (lowercase)")
        fun singleMaxByteEncodesAsLowercaseFF() {
            assertEquals("ff", HexUtil.encodeHex(byteArrayOf(0xFF.toByte())))
        }

        @Test
        @DisplayName("Two bytes [0x00, 0xFF] encode to '00ff'")
        fun twoByteArrayEncodesCorrectly() {
            assertEquals("00ff", HexUtil.encodeHex(byteArrayOf(0x00, 0xFF.toByte())))
        }

        @Test
        @DisplayName("All nibble values 0x00–0x0F encode to single-hex-digit pairs")
        fun lowNibbleRangeEncoding() {
            val bytes = ByteArray(16) { i -> i.toByte() }
            val result = HexUtil.encodeHex(bytes)
            assertEquals("000102030405060708090a0b0c0d0e0f", result)
        }

        @Test
        @DisplayName("High nibble range 0xF0–0xFF encodes correctly")
        fun highNibbleRangeEncoding() {
            val bytes = ByteArray(16) { i -> (0xF0 + i).toByte() }
            val result = HexUtil.encodeHex(bytes)
            assertEquals("f0f1f2f3f4f5f6f7f8f9fafbfcfdfeff", result)
        }

        @Test
        @DisplayName("Output length is exactly 2 * input length")
        fun outputLengthIsDoubleInputLength() {
            for (size in listOf(0, 1, 4, 16, 32, 64, 128)) {
                val input = ByteArray(size) { it.toByte() }
                assertEquals(size * 2, HexUtil.encodeHex(input).length,
                    "Expected length ${size * 2} for input size $size")
            }
        }
    }

    @Nested
    @DisplayName("encodeHex — lowercase enforcement")
    inner class LowercaseTests {

        @Test
        @DisplayName("Output contains only lowercase hex characters")
        fun outputIsAlwaysLowercase() {
            val bytes = ByteArray(256) { i -> i.toByte() }
            val result = HexUtil.encodeHex(bytes)
            assertTrue(result.all { it in '0'..'9' || it in 'a'..'f' },
                "Result must contain only lowercase hex digits, got: $result")
        }

        @Test
        @DisplayName("Bytes producing a-f nibbles are lowercase")
        fun alphabeticNibblesAreLowercase() {
            // 0xAB, 0xCD, 0xEF each contain upper nibble letters
            val bytes = byteArrayOf(0xAB.toByte(), 0xCD.toByte(), 0xEF.toByte())
            assertEquals("abcdef", HexUtil.encodeHex(bytes))
        }
    }

    @Nested
    @DisplayName("encodeHex — known SHA-256 digest vectors")
    inner class KnownVectorTests {

        @Test
        @DisplayName("SHA-256 of empty string matches well-known vector")
        fun sha256EmptyStringVector() {
            // SHA-256("") = e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855
            val digest = java.security.MessageDigest.getInstance("SHA-256").digest(byteArrayOf())
            val expected = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
            assertEquals(expected, HexUtil.encodeHex(digest))
        }

        @Test
        @DisplayName("SHA-256 of 'hello' matches well-known vector")
        fun sha256HelloVector() {
            // SHA-256("hello") = 2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824
            val digest = java.security.MessageDigest.getInstance("SHA-256")
                .digest("hello".toByteArray(Charsets.UTF_8))
            val expected = "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824"
            assertEquals(expected, HexUtil.encodeHex(digest))
        }

        @Test
        @DisplayName("SHA-256 output is always 64 hex characters (32 bytes)")
        fun sha256OutputAlways64HexChars() {
            val inputs = listOf("", "a", "hello world", "AuraKAI-security-path")
            for (input in inputs) {
                val digest = java.security.MessageDigest.getInstance("SHA-256")
                    .digest(input.toByteArray(Charsets.UTF_8))
                val hex = HexUtil.encodeHex(digest)
                assertEquals(64, hex.length, "Expected 64 hex chars for input '$input'")
            }
        }
    }

    @Nested
    @DisplayName("encodeHex — matches legacy joinToString behavior")
    inner class LegacyCompatibilityTests {

        @Test
        @DisplayName("Result matches joinToString format string for all byte values 0x00–0xFF")
        fun matchesLegacyJoinToStringForAllByteValues() {
            for (b in 0..255) {
                val bytes = byteArrayOf(b.toByte())
                val legacy = bytes.joinToString("") { "%02x".format(it) }
                val actual = HexUtil.encodeHex(bytes)
                assertEquals(legacy, actual, "Mismatch at byte value 0x${"%02x".format(b)}")
            }
        }

        @Test
        @DisplayName("Multi-byte sequence matches legacy joinToString")
        fun multiByteMatchesLegacy() {
            val bytes = ByteArray(32) { (it * 8).toByte() }
            val legacy = bytes.joinToString("") { "%02x".format(it) }
            assertEquals(legacy, HexUtil.encodeHex(bytes))
        }
    }

    @Nested
    @DisplayName("encodeHex — security path: 16-byte SecureRandom output")
    inner class SecureRandomIdTests {

        @Test
        @DisplayName("16-byte input yields 32-character hex string (generateSecureId format)")
        fun sixteenByteInputYields32CharHex() {
            val bytes = ByteArray(16)
            java.security.SecureRandom().nextBytes(bytes)
            val hex = HexUtil.encodeHex(bytes)
            assertEquals(32, hex.length)
            assertTrue(hex.all { it in '0'..'9' || it in 'a'..'f' })
        }

        @Test
        @DisplayName("Different 16-byte inputs produce different hex strings")
        fun differentInputsProduceDifferentOutputs() {
            val bytes1 = ByteArray(16) { 0x00 }
            val bytes2 = ByteArray(16) { 0xFF.toByte() }
            val hex1 = HexUtil.encodeHex(bytes1)
            val hex2 = HexUtil.encodeHex(bytes2)
            assertTrue(hex1 != hex2)
        }

        @Test
        @DisplayName("Same byte array input always produces same output (deterministic)")
        fun sameInputAlwaysProducesSameOutput() {
            val bytes = byteArrayOf(0x01, 0x23, 0x45, 0x67, 0x89.toByte(),
                0xAB.toByte(), 0xCD.toByte(), 0xEF.toByte())
            val result1 = HexUtil.encodeHex(bytes)
            val result2 = HexUtil.encodeHex(bytes)
            assertEquals(result1, result2)
        }
    }

    @Nested
    @DisplayName("encodeHex — boundary and regression cases")
    inner class BoundaryAndRegressionTests {

        @Test
        @DisplayName("Single byte with value 0x0F encodes to '0f' (not 'f')")
        fun singleNibblePaddedWithLeadingZero() {
            assertEquals("0f", HexUtil.encodeHex(byteArrayOf(0x0F)))
        }

        @Test
        @DisplayName("Byte value 0x10 encodes to '10' (not '1')")
        fun byteValueSixteenEncodesToOneZero() {
            assertEquals("10", HexUtil.encodeHex(byteArrayOf(0x10)))
        }

        @Test
        @DisplayName("All-zero 32-byte array encodes to 64 zeros")
        fun allZeroThirtyTwoBytesEncodesToSixtyFourZeros() {
            val zeros = ByteArray(32)
            assertEquals("0".repeat(64), HexUtil.encodeHex(zeros))
        }

        @Test
        @DisplayName("All-0xFF 32-byte array encodes to 64 'f's")
        fun allMaxThirtyTwoBytesEncodesToSixtyFourFs() {
            val maxBytes = ByteArray(32) { 0xFF.toByte() }
            assertEquals("f".repeat(64), HexUtil.encodeHex(maxBytes))
        }

        @Test
        @DisplayName("Byte value 0x80 (sign bit set) encodes to '80' without sign extension")
        fun signedByteWithSignBitSetEncodesCorrectly() {
            // Signed byte 0x80 = -128 in Kotlin/JVM — must not produce negative result
            assertEquals("80", HexUtil.encodeHex(byteArrayOf(0x80.toByte())))
        }

        @Test
        @DisplayName("Byte value 0xAA encodes to 'aa' (alternating nibbles)")
        fun alternatingNibbleByteEncodesCorrectly() {
            assertEquals("aa", HexUtil.encodeHex(byteArrayOf(0xAA.toByte())))
        }
    }
}