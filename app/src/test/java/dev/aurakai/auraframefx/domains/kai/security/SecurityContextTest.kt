package dev.aurakai.auraframefx.domains.kai.security

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dev.aurakai.auraframefx.core.identity.AgentType
import dev.aurakai.auraframefx.core.security.KeystoreManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Unit tests for [SecurityContext] — focused on the functions that were changed or documented
 * in this PR:
 * - [SecurityContext.shareSecureContextWith] (uses [dev.aurakai.auraframefx.core.util.HexUtil.encodeHex] via generateSecureId)
 * - [SecurityContext.verifyApplicationIntegrity] (uses HexUtil.encodeHex for signatureHash)
 * - [SecurityContext.logSecurityEvent]
 * - Supporting data models and enums defined in SecurityContext.kt
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("SecurityContext Tests")
class SecurityContextTest {

    private lateinit var mockContext: Context
    private lateinit var mockPackageManager: PackageManager
    private lateinit var mockKeystoreManager: KeystoreManager
    private lateinit var securityContext: SecurityContext

    @BeforeEach
    fun setUp() {
        mockkStatic(ContextCompat::class)
        every { ContextCompat.checkSelfPermission(any(), any()) } returns PackageManager.PERMISSION_DENIED

        mockPackageManager = mockk(relaxed = true)
        mockContext = mockk(relaxed = true)
        every { mockContext.packageManager } returns mockPackageManager
        every { mockContext.packageName } returns "dev.aurakai.auraframefx.test"

        mockKeystoreManager = mockk(relaxed = true)

        securityContext = SecurityContext(mockContext, mockKeystoreManager)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    // ─────────────────────────────────────────────
    // shareSecureContextWith
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("shareSecureContextWith — id format (HexUtil.encodeHex via generateSecureId)")
    inner class ShareSecureContextIdFormatTests {

        @Test
        @DisplayName("returned id is exactly 32 characters")
        fun `id is 32 characters`() {
            val result = securityContext.shareSecureContextWith(AgentType.AURA, "test context")
            assertEquals(32, result.id.length,
                "generateSecureId encodes 16 random bytes to 32 hex chars via HexUtil.encodeHex")
        }

        @Test
        @DisplayName("returned id contains only lowercase hex characters [0-9a-f]")
        fun `id contains only lowercase hex`() {
            val result = securityContext.shareSecureContextWith(AgentType.GENESIS, "ctx")
            assertTrue(result.id.all { it in '0'..'9' || it in 'a'..'f' },
                "HexUtil.encodeHex must produce lowercase hex, got: '${result.id}'")
        }

        @Test
        @DisplayName("returned id contains no uppercase letters")
        fun `id has no uppercase letters`() {
            val result = securityContext.shareSecureContextWith(AgentType.CASCADE, "data")
            assertTrue(result.id.none { it.isUpperCase() })
        }

        @Test
        @DisplayName("each call produces a unique id (SecureRandom source)")
        fun `each call produces unique id`() {
            val id1 = securityContext.shareSecureContextWith(AgentType.AURA, "ctx").id
            val id2 = securityContext.shareSecureContextWith(AgentType.AURA, "ctx").id
            assertNotEquals(id1, id2,
                "generateSecureId is based on SecureRandom — ids must differ across calls")
        }
    }

    @Nested
    @DisplayName("shareSecureContextWith — field semantics")
    inner class ShareSecureContextFieldTests {

        @Test
        @DisplayName("originatingAgent is always AgentType.KAI")
        fun `originatingAgent is KAI`() {
            val result = securityContext.shareSecureContextWith(AgentType.GENESIS, "ctx")
            assertEquals(AgentType.KAI, result.originatingAgent)
        }

        @Test
        @DisplayName("targetAgent is set to the provided agentType")
        fun `targetAgent matches provided agent`() {
            val result = securityContext.shareSecureContextWith(AgentType.AURA, "ctx")
            assertEquals(AgentType.AURA, result.targetAgent)
        }

        @Test
        @DisplayName("targetAgent cascade is set correctly")
        fun `targetAgent CASCADE is set correctly`() {
            val result = securityContext.shareSecureContextWith(AgentType.CASCADE, "payload")
            assertEquals(AgentType.CASCADE, result.targetAgent)
        }

        @Test
        @DisplayName("encryptedContent is the UTF-8 bytes of the provided context string")
        fun `encryptedContent is UTF-8 bytes of context`() {
            val contextString = "Hello AuraKAI security"
            val result = securityContext.shareSecureContextWith(AgentType.GENESIS, contextString)
            val expectedBytes = contextString.toByteArray(Charsets.UTF_8)
            assertTrue(result.encryptedContent.contentEquals(expectedBytes),
                "encryptedContent must be the UTF-8 encoding of the context string")
        }

        @Test
        @DisplayName("encryptedContent for empty string is empty byte array")
        fun `encryptedContent for empty string`() {
            val result = securityContext.shareSecureContextWith(AgentType.KAI, "")
            assertTrue(result.encryptedContent.isEmpty())
        }

        @Test
        @DisplayName("encryptedContent for unicode string encodes correctly")
        fun `encryptedContent for unicode string`() {
            val unicode = "Sentinel \u2605 KAI \u00e9"
            val result = securityContext.shareSecureContextWith(AgentType.AURA, unicode)
            assertTrue(result.encryptedContent.contentEquals(unicode.toByteArray(Charsets.UTF_8)))
        }

        @Test
        @DisplayName("timestamp is set to approximately current time")
        fun `timestamp is approximately current time`() {
            val before = System.currentTimeMillis()
            val result = securityContext.shareSecureContextWith(AgentType.AURA, "ctx")
            val after = System.currentTimeMillis()
            assertTrue(result.timestamp in before..after,
                "timestamp must be in [before, after], got ${result.timestamp}")
        }

        @Test
        @DisplayName("expiresAt is timestamp plus one hour (3_600_000 ms)")
        fun `expiresAt is timestamp plus one hour`() {
            val result = securityContext.shareSecureContextWith(AgentType.KAI, "ctx")
            assertEquals(3_600_000L, result.expiresAt - result.timestamp,
                "expiresAt must be exactly 1 hour after timestamp")
        }

        @Test
        @DisplayName("expiresAt is strictly greater than timestamp")
        fun `expiresAt is after timestamp`() {
            val result = securityContext.shareSecureContextWith(AgentType.CLAUDE, "ctx")
            assertTrue(result.expiresAt > result.timestamp)
        }
    }

    // ─────────────────────────────────────────────
    // verifyApplicationIntegrity — error path
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("verifyApplicationIntegrity — error path")
    inner class VerifyApplicationIntegrityErrorTests {

        @Test
        @DisplayName("returns verified=false when PackageManager throws NameNotFoundException")
        fun `returns verified false on NameNotFoundException`() {
            every {
                mockPackageManager.getPackageInfo(any<String>(), any<PackageManager.PackageInfoFlags>())
            } throws PackageManager.NameNotFoundException("Test package not found")

            val result = securityContext.verifyApplicationIntegrity()
            assertFalse(result.verified)
        }

        @Test
        @DisplayName("errorMessage is non-null when PackageManager throws")
        fun `errorMessage is non-null on exception`() {
            every {
                mockPackageManager.getPackageInfo(any<String>(), any<PackageManager.PackageInfoFlags>())
            } throws PackageManager.NameNotFoundException("Package missing")

            val result = securityContext.verifyApplicationIntegrity()
            assertNotNull(result.errorMessage)
        }

        @Test
        @DisplayName("signatureHash is 'error' on failure")
        fun `signatureHash is error on failure`() {
            every {
                mockPackageManager.getPackageInfo(any<String>(), any<PackageManager.PackageInfoFlags>())
            } throws RuntimeException("Simulated failure")

            val result = securityContext.verifyApplicationIntegrity()
            assertEquals("error", result.signatureHash)
        }

        @Test
        @DisplayName("appVersion is 'unknown' on failure")
        fun `appVersion is unknown on failure`() {
            every {
                mockPackageManager.getPackageInfo(any<String>(), any<PackageManager.PackageInfoFlags>())
            } throws RuntimeException("Simulated failure")

            val result = securityContext.verifyApplicationIntegrity()
            assertEquals("unknown", result.appVersion)
        }

        @Test
        @DisplayName("installTime and lastUpdateTime are 0 on failure")
        fun `times are zero on failure`() {
            every {
                mockPackageManager.getPackageInfo(any<String>(), any<PackageManager.PackageInfoFlags>())
            } throws RuntimeException("Simulated failure")

            val result = securityContext.verifyApplicationIntegrity()
            assertEquals(0L, result.installTime)
            assertEquals(0L, result.lastUpdateTime)
        }

        @Test
        @DisplayName("returns an ApplicationIntegrity object on any exception (no rethrow)")
        fun `does not throw on any exception`() {
            every {
                mockPackageManager.getPackageInfo(any<String>(), any<PackageManager.PackageInfoFlags>())
            } throws IllegalStateException("Unexpected state")

            // Must not throw
            val result = securityContext.verifyApplicationIntegrity()
            assertNotNull(result)
            assertFalse(result.verified)
        }
    }

    // ─────────────────────────────────────────────
    // logSecurityEvent — fire-and-forget smoke test
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("logSecurityEvent — smoke tests")
    inner class LogSecurityEventTests {

        @Test
        @DisplayName("logSecurityEvent with INFO severity does not throw")
        fun `INFO event does not throw`() {
            val event = SecurityEvent(
                type = SecurityEventType.VALIDATION,
                details = "Test info event",
                severity = EventSeverity.INFO
            )
            securityContext.logSecurityEvent(event)
        }

        @Test
        @DisplayName("logSecurityEvent with WARNING severity does not throw")
        fun `WARNING event does not throw`() {
            val event = SecurityEvent(
                type = SecurityEventType.THREAT_DETECTED,
                details = "Test warning event",
                severity = EventSeverity.WARNING
            )
            securityContext.logSecurityEvent(event)
        }

        @Test
        @DisplayName("logSecurityEvent with ERROR severity does not throw")
        fun `ERROR event does not throw`() {
            val event = SecurityEvent(
                type = SecurityEventType.AI_ERROR,
                details = "Test error event",
                severity = EventSeverity.ERROR
            )
            securityContext.logSecurityEvent(event)
        }

        @Test
        @DisplayName("logSecurityEvent with CRITICAL severity does not throw")
        fun `CRITICAL event does not throw`() {
            val event = SecurityEvent(
                type = SecurityEventType.INTEGRITY_CHECK,
                details = "Critical integrity failure",
                severity = EventSeverity.CRITICAL
            )
            securityContext.logSecurityEvent(event)
        }
    }

    // ─────────────────────────────────────────────
    // SecurityEvent data class (defined in SecurityContext.kt)
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("SecurityEvent model")
    inner class SecurityEventModelTests {

        @Test
        @DisplayName("id is auto-generated as non-blank UUID")
        fun `id is auto-generated`() {
            val event = SecurityEvent(
                type = SecurityEventType.VALIDATION,
                details = "detail",
                severity = EventSeverity.INFO
            )
            assertTrue(event.id.isNotBlank())
        }

        @Test
        @DisplayName("each SecurityEvent has a unique auto-generated id")
        fun `ids are unique`() {
            val e1 = SecurityEvent(type = SecurityEventType.VALIDATION, details = "d", severity = EventSeverity.INFO)
            val e2 = SecurityEvent(type = SecurityEventType.VALIDATION, details = "d", severity = EventSeverity.INFO)
            assertNotEquals(e1.id, e2.id)
        }

        @Test
        @DisplayName("timestamp is set to approximately current time")
        fun `timestamp is current time`() {
            val before = System.currentTimeMillis()
            val event = SecurityEvent(type = SecurityEventType.VALIDATION, details = "d", severity = EventSeverity.INFO)
            val after = System.currentTimeMillis()
            assertTrue(event.timestamp in before..after)
        }

        @Test
        @DisplayName("all fields stored correctly")
        fun `all fields stored correctly`() {
            val event = SecurityEvent(
                id = "custom-id",
                type = SecurityEventType.AUTHENTICATION_EVENT,
                timestamp = 12345L,
                details = "auth detail",
                severity = EventSeverity.WARNING
            )
            assertEquals("custom-id", event.id)
            assertEquals(SecurityEventType.AUTHENTICATION_EVENT, event.type)
            assertEquals(12345L, event.timestamp)
            assertEquals("auth detail", event.details)
            assertEquals(EventSeverity.WARNING, event.severity)
        }
    }

    // ─────────────────────────────────────────────
    // ApplicationIntegrity data class
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("ApplicationIntegrity model")
    inner class ApplicationIntegrityModelTests {

        @Test
        @DisplayName("errorMessage defaults to null")
        fun `errorMessage defaults to null`() {
            val integrity = ApplicationIntegrity(
                verified = true,
                appVersion = "1.0",
                signatureHash = "abc123",
                installTime = 1000L,
                lastUpdateTime = 2000L
            )
            assertNull(integrity.errorMessage)
        }

        @Test
        @DisplayName("all fields stored correctly")
        fun `all fields stored correctly`() {
            val integrity = ApplicationIntegrity(
                verified = false,
                appVersion = "2.0.0",
                signatureHash = "deadbeef",
                installTime = 100L,
                lastUpdateTime = 200L,
                errorMessage = "Package not found"
            )
            assertFalse(integrity.verified)
            assertEquals("2.0.0", integrity.appVersion)
            assertEquals("deadbeef", integrity.signatureHash)
            assertEquals(100L, integrity.installTime)
            assertEquals(200L, integrity.lastUpdateTime)
            assertEquals("Package not found", integrity.errorMessage)
        }

        @Test
        @DisplayName("verified=true and verified=false produce unequal instances")
        fun `verified flag differentiates instances`() {
            val success = ApplicationIntegrity(true, "1.0", "abc", 100L, 200L)
            val failure = ApplicationIntegrity(false, "1.0", "abc", 100L, 200L)
            assertNotEquals(success, failure)
        }
    }

    // ─────────────────────────────────────────────
    // SharedSecureContext data class
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("SharedSecureContext model")
    inner class SharedSecureContextModelTests {

        @Test
        @DisplayName("all fields stored correctly")
        fun `all fields stored correctly`() {
            val content = "secret".toByteArray()
            val ctx = SharedSecureContext(
                id = "abc123",
                originatingAgent = AgentType.KAI,
                targetAgent = AgentType.AURA,
                encryptedContent = content,
                timestamp = 5000L,
                expiresAt = 8600000L
            )
            assertEquals("abc123", ctx.id)
            assertEquals(AgentType.KAI, ctx.originatingAgent)
            assertEquals(AgentType.AURA, ctx.targetAgent)
            assertTrue(ctx.encryptedContent.contentEquals(content))
            assertEquals(5000L, ctx.timestamp)
            assertEquals(8600000L, ctx.expiresAt)
        }

        @Test
        @DisplayName("two SharedSecureContexts with same fields are equal")
        fun `equal instances are equal`() {
            val content = byteArrayOf(1, 2, 3)
            val ctx1 = SharedSecureContext("id1", AgentType.KAI, AgentType.AURA, content, 100L, 200L)
            val ctx2 = SharedSecureContext("id1", AgentType.KAI, AgentType.AURA, content, 100L, 200L)
            assertEquals(ctx1, ctx2)
        }

        @Test
        @DisplayName("different ids produce unequal SharedSecureContexts")
        fun `different ids are unequal`() {
            val content = byteArrayOf(1, 2, 3)
            val ctx1 = SharedSecureContext("id1", AgentType.KAI, AgentType.AURA, content, 100L, 200L)
            val ctx2 = SharedSecureContext("id2", AgentType.KAI, AgentType.AURA, content, 100L, 200L)
            assertNotEquals(ctx1, ctx2)
        }
    }

    // ─────────────────────────────────────────────
    // ThreatSeverity enum (defined in SecurityContext.kt)
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("ThreatSeverity enum")
    inner class ThreatSeverityEnumTests {

        @Test
        @DisplayName("has all four values: LOW, MEDIUM, HIGH, CRITICAL")
        fun `has all four values`() {
            val values = ThreatSeverity.entries
            assertTrue(values.contains(ThreatSeverity.LOW))
            assertTrue(values.contains(ThreatSeverity.MEDIUM))
            assertTrue(values.contains(ThreatSeverity.HIGH))
            assertTrue(values.contains(ThreatSeverity.CRITICAL))
            assertEquals(4, values.size)
        }

        @Test
        @DisplayName("LOW ordinal is less than CRITICAL ordinal")
        fun `LOW ordinal less than CRITICAL`() {
            assertTrue(ThreatSeverity.LOW.ordinal < ThreatSeverity.CRITICAL.ordinal)
        }
    }

    // ─────────────────────────────────────────────
    // ThreatType enum (defined in SecurityContext.kt)
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("ThreatType enum")
    inner class ThreatTypeEnumTests {

        @Test
        @DisplayName("has all five values")
        fun `has all five values`() {
            val values = ThreatType.entries
            assertTrue(values.contains(ThreatType.PERMISSION_ABUSE))
            assertTrue(values.contains(ThreatType.NETWORK_VULNERABILITY))
            assertTrue(values.contains(ThreatType.MALWARE))
            assertTrue(values.contains(ThreatType.DATA_LEAK))
            assertTrue(values.contains(ThreatType.UNKNOWN))
            assertEquals(5, values.size)
        }
    }

    // ─────────────────────────────────────────────
    // EventSeverity enum (defined in SecurityContext.kt)
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("EventSeverity enum")
    inner class EventSeverityEnumTests {

        @Test
        @DisplayName("has all four values: INFO, WARNING, ERROR, CRITICAL")
        fun `has all four values`() {
            val values = EventSeverity.entries
            assertTrue(values.contains(EventSeverity.INFO))
            assertTrue(values.contains(EventSeverity.WARNING))
            assertTrue(values.contains(EventSeverity.ERROR))
            assertTrue(values.contains(EventSeverity.CRITICAL))
            assertEquals(4, values.size)
        }
    }

    // ─────────────────────────────────────────────
    // SecurityEventType enum (defined in SecurityContext.kt)
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("SecurityEventType enum")
    inner class SecurityEventTypeEnumTests {

        @Test
        @DisplayName("has all seven event types")
        fun `has all seven values`() {
            val values = SecurityEventType.entries
            assertTrue(values.contains(SecurityEventType.VALIDATION))
            assertTrue(values.contains(SecurityEventType.PERMISSION_CHANGE))
            assertTrue(values.contains(SecurityEventType.THREAT_DETECTED))
            assertTrue(values.contains(SecurityEventType.ENCRYPTION_EVENT))
            assertTrue(values.contains(SecurityEventType.AUTHENTICATION_EVENT))
            assertTrue(values.contains(SecurityEventType.INTEGRITY_CHECK))
            assertTrue(values.contains(SecurityEventType.AI_ERROR))
            assertEquals(7, values.size)
        }
    }

    // ─────────────────────────────────────────────
    // KaiSecurityState data class defaults
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("KaiSecurityState defaults")
    inner class KaiSecurityStateDefaultsTests {

        @Test
        @DisplayName("default instance has empty detectedThreats")
        fun `default detectedThreats is empty`() {
            val state = KaiSecurityState()
            assertTrue(state.detectedThreats.isEmpty())
        }

        @Test
        @DisplayName("default threatLevel is LOW")
        fun `default threatLevel is LOW`() {
            val state = KaiSecurityState()
            assertEquals(dev.aurakai.auraframefx.domains.kai.models.ThreatLevel.LOW, state.threatLevel)
        }

        @Test
        @DisplayName("default errorState is false")
        fun `default errorState is false`() {
            val state = KaiSecurityState()
            assertFalse(state.errorState)
        }

        @Test
        @DisplayName("default errorMessage is null")
        fun `default errorMessage is null`() {
            val state = KaiSecurityState()
            assertNull(state.errorMessage)
        }

        @Test
        @DisplayName("default lastScanTime is 0")
        fun `default lastScanTime is 0`() {
            val state = KaiSecurityState()
            assertEquals(0L, state.lastScanTime)
        }
    }

    // ─────────────────────────────────────────────
    // Regression: generateSecureId via shareSecureContextWith
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("Regression: generateSecureId HexUtil.encodeHex migration")
    inner class GenerateSecureIdRegressionTests {

        /**
         * This PR replaced `bytes.joinToString("") { "%02x".format(it) }` with
         * `HexUtil.encodeHex(bytes)` in generateSecureId. This test verifies the
         * output format matches the expected 32-char lowercase hex produced by
         * encoding 16 random bytes.
         */
        @Test
        @DisplayName("id from shareSecureContextWith is 32-char lowercase hex (HexUtil migration)")
        fun `id matches expected hex format after HexUtil migration`() {
            repeat(5) {
                val result = securityContext.shareSecureContextWith(AgentType.KAI, "regression test")
                assertEquals(32, result.id.length,
                    "HexUtil.encodeHex(ByteArray(16)) must produce exactly 32 hex chars")
                assertTrue(result.id.all { c -> c in '0'..'9' || c in 'a'..'f' },
                    "All chars must be lowercase hex [0-9a-f], got: '${result.id}'")
            }
        }

        @Test
        @DisplayName("id does not contain any non-hex character after migration")
        fun `id contains no non-hex characters`() {
            val result = securityContext.shareSecureContextWith(AgentType.GENESIS, "test")
            val invalidChars = result.id.filterNot { it in '0'..'9' || it in 'a'..'f' }
            assertTrue(invalidChars.isEmpty(),
                "Found invalid chars in id '${result.id}': '$invalidChars'")
        }
    }
}
