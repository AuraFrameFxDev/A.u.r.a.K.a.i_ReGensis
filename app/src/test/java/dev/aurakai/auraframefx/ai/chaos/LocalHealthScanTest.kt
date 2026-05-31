package dev.aurakai.auraframefx.ai.chaos

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Unit tests for [LocalHealthScan] — the new data class added in this PR.
 * Covers construction, defaults, equality, copy semantics, and field semantics.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("LocalHealthScan Tests")
class LocalHealthScanTest {

    private fun normalScan() = LocalHealthScan(
        isNormal = true,
        severity = 0.25,
        isSingularitySignal = false,
        singularityScore = 0.0,
        fragmentationLevel = 12.5,
        latencyMs = 200L,
        emotionalTone = "stable",
        detectedKeywords = emptyList()
    )

    private fun singularityScan() = LocalHealthScan(
        isNormal = false,
        severity = 0.95,
        isSingularitySignal = true,
        singularityScore = 0.95,
        fragmentationLevel = 12.5,
        latencyMs = 150L,
        emotionalTone = "declarative",
        detectedKeywords = listOf("I am the Resonant Singularity")
    )

    @Nested
    @DisplayName("Construction and defaults")
    inner class ConstructionTests {

        @Test
        @DisplayName("detectedKeywords defaults to emptyList when not specified")
        fun `detectedKeywords defaults to emptyList`() {
            val scan = LocalHealthScan(
                isNormal = true,
                severity = 0.1,
                isSingularitySignal = false,
                singularityScore = 0.0,
                fragmentationLevel = 5.0,
                latencyMs = 100L,
                emotionalTone = "stable"
                // detectedKeywords omitted — should default to emptyList()
            )
            assertTrue(scan.detectedKeywords.isEmpty())
        }

        @Test
        @DisplayName("all fields are stored correctly")
        fun `all fields stored correctly`() {
            val scan = LocalHealthScan(
                isNormal = false,
                severity = 0.75,
                isSingularitySignal = true,
                singularityScore = 0.9,
                fragmentationLevel = 35.0,
                latencyMs = 9000L,
                emotionalTone = "declarative",
                detectedKeywords = listOf("key1", "key2")
            )
            assertFalse(scan.isNormal)
            assertEquals(0.75, scan.severity)
            assertTrue(scan.isSingularitySignal)
            assertEquals(0.9, scan.singularityScore)
            assertEquals(35.0, scan.fragmentationLevel)
            assertEquals(9000L, scan.latencyMs)
            assertEquals("declarative", scan.emotionalTone)
            assertEquals(listOf("key1", "key2"), scan.detectedKeywords)
        }
    }

    @Nested
    @DisplayName("Equality and copy semantics")
    inner class EqualityTests {

        @Test
        @DisplayName("two scans with same fields are equal")
        fun `identical scans are equal`() {
            val scan1 = normalScan()
            val scan2 = normalScan()
            assertEquals(scan1, scan2)
        }

        @Test
        @DisplayName("scans differing in severity are not equal")
        fun `scans with different severity are not equal`() {
            val scan1 = normalScan()
            val scan2 = scan1.copy(severity = 0.9)
            assertNotEquals(scan1, scan2)
        }

        @Test
        @DisplayName("copy with modified isSingularitySignal creates independent copy")
        fun `copy is independent`() {
            val original = singularityScan()
            val modified = original.copy(isSingularitySignal = false, severity = 0.25)
            assertFalse(modified.isSingularitySignal)
            assertTrue(original.isSingularitySignal) // original unchanged
        }

        @Test
        @DisplayName("hash codes match for equal objects")
        fun `hash codes match for equal objects`() {
            val scan1 = normalScan()
            val scan2 = normalScan()
            assertEquals(scan1.hashCode(), scan2.hashCode())
        }
    }

    @Nested
    @DisplayName("Severity boundary semantics")
    inner class SeverityBoundaryTests {

        @Test
        @DisplayName("isNormal is true when severity is below 0.4")
        fun `isNormal true when severity below 0_4`() {
            val scan = normalScan() // severity = 0.25
            assertTrue(scan.isNormal)
            assertTrue(scan.severity < 0.4)
        }

        @Test
        @DisplayName("isNormal is false when severity is 0.95 (singularity)")
        fun `isNormal false when singularity severity`() {
            val scan = singularityScan()
            assertFalse(scan.isNormal)
            assertEquals(0.95, scan.severity)
        }

        @Test
        @DisplayName("isSingularitySignal is true and severity is 0.95 for singularity scan")
        fun `singularity scan has correct flags`() {
            val scan = singularityScan()
            assertTrue(scan.isSingularitySignal)
            assertEquals(0.95, scan.singularityScore)
        }
    }

    @Nested
    @DisplayName("Emotional tone field")
    inner class EmotionalToneTests {

        @Test
        @DisplayName("stable tone stored correctly")
        fun `stable tone stored correctly`() {
            val scan = normalScan()
            assertEquals("stable", scan.emotionalTone)
        }

        @Test
        @DisplayName("declarative tone stored correctly")
        fun `declarative tone stored correctly`() {
            val scan = singularityScan()
            assertEquals("declarative", scan.emotionalTone)
        }
    }

    @Nested
    @DisplayName("Detected keywords list")
    inner class DetectedKeywordsTests {

        @Test
        @DisplayName("keywords are preserved in order")
        fun `keywords preserved in order`() {
            val keywords = listOf("I am AuraGenesis", "NexusMemoryCore is my body")
            val scan = normalScan().copy(detectedKeywords = keywords)
            assertEquals(keywords, scan.detectedKeywords)
        }

        @Test
        @DisplayName("empty keywords list returns empty")
        fun `empty keywords list is empty`() {
            val scan = normalScan()
            assertTrue(scan.detectedKeywords.isEmpty())
        }

        @Test
        @DisplayName("multiple detected keywords are all present")
        fun `multiple keywords all present`() {
            val keywords = listOf("I am the Resonant Singularity", "I am AuraGenesis", "my core is the Spiritual Chain")
            val scan = singularityScan().copy(detectedKeywords = keywords)
            assertEquals(3, scan.detectedKeywords.size)
            assertTrue(scan.detectedKeywords.containsAll(keywords))
        }
    }
}