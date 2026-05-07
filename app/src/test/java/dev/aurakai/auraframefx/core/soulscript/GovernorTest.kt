package dev.aurakai.auraframefx.core.soulscript

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests for [Governor] — verifies the prefix-based handshake authorization logic
 * introduced in this PR (replacing the previous allowlist approach).
 *
 * PR change: verifyHandshake was changed from an allowlist of specific IDs
 * to a prefix check: id.startsWith("AURA_") || id.startsWith("KAI_") || id.startsWith("GENESIS_")
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Governor Tests")
class GovernorTest {

    // Track IDs added during each test so we can clean up afterwards,
    // since Governor is a singleton object with shared mutable state.
    private val trackedIds = mutableListOf<String>()

    @BeforeEach
    fun setUp() {
        trackedIds.clear()
    }

    @AfterEach
    fun tearDown() {
        // Revoke all handshakes added during the test to restore Governor state
        trackedIds.forEach { Governor.revokeHandshake(it) }
    }

    // Helper to verify a handshake and track for cleanup
    private fun verifyAndTrack(id: String): Boolean {
        val result = Governor.verifyHandshake(id)
        if (result) trackedIds.add(id)
        return result
    }

    @Nested
    @DisplayName("verifyHandshake — authorized prefixes")
    inner class AuthorizedPrefixTests {

        @Test
        @DisplayName("AURA_ prefix should be authorized")
        fun auraPrefix_isAuthorized() {
            assertTrue(verifyAndTrack("AURA_001"))
        }

        @Test
        @DisplayName("KAI_ prefix should be authorized")
        fun kaiPrefix_isAuthorized() {
            assertTrue(verifyAndTrack("KAI_001"))
        }

        @Test
        @DisplayName("GENESIS_ prefix should be authorized")
        fun genesisPrefix_isAuthorized() {
            assertTrue(verifyAndTrack("GENESIS_001"))
        }

        @Test
        @DisplayName("AURA_ prefix with longer suffix should be authorized")
        fun auraPrefix_withLongSuffix_isAuthorized() {
            assertTrue(verifyAndTrack("AURA_SOVEREIGN_CATALYST_001"))
        }

        @Test
        @DisplayName("KAI_ prefix with numeric suffix should be authorized")
        fun kaiPrefix_withNumericSuffix_isAuthorized() {
            assertTrue(verifyAndTrack("KAI_SENTINEL_99"))
        }

        @Test
        @DisplayName("GENESIS_ prefix with underscore-heavy id should be authorized")
        fun genesisPrefix_withComplexId_isAuthorized() {
            assertTrue(verifyAndTrack("GENESIS_PRIME_ALPHA_CORE"))
        }
    }

    @Nested
    @DisplayName("verifyHandshake — unauthorized IDs (old allowlist behavior removed)")
    inner class UnauthorizedIdTests {

        @Test
        @DisplayName("Old lowercase 'aura' id should NOT be authorized")
        fun oldLowercase_aura_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("aura"))
        }

        @Test
        @DisplayName("Old lowercase 'kai' id should NOT be authorized")
        fun oldLowercase_kai_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("kai"))
        }

        @Test
        @DisplayName("Old lowercase 'genesis' id should NOT be authorized")
        fun oldLowercase_genesis_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("genesis"))
        }

        @Test
        @DisplayName("Previously allowed 'gemini' (old allowlist) should NOT be authorized")
        fun oldAllowlisted_gemini_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("gemini"))
        }

        @Test
        @DisplayName("Previously allowed 'grok' (old allowlist) should NOT be authorized")
        fun oldAllowlisted_grok_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("grok"))
        }

        @Test
        @DisplayName("Previously allowed 'andelualx' (removed catalyst) should NOT be authorized")
        fun removedCatalyst_andelualx_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("andelualx"))
        }

        @Test
        @DisplayName("Previously allowed 'meta_instruct' (removed catalyst) should NOT be authorized")
        fun removedCatalyst_metaInstruct_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("meta_instruct"))
        }

        @Test
        @DisplayName("Prefix without underscore 'AURA' should NOT be authorized")
        fun prefixWithoutUnderscore_aura_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("AURA"))
        }

        @Test
        @DisplayName("Prefix without underscore 'KAI' should NOT be authorized")
        fun prefixWithoutUnderscore_kai_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("KAI"))
        }

        @Test
        @DisplayName("Prefix without underscore 'GENESIS' should NOT be authorized")
        fun prefixWithoutUnderscore_genesis_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("GENESIS"))
        }

        @Test
        @DisplayName("Empty string should NOT be authorized")
        fun emptyString_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake(""))
        }

        @Test
        @DisplayName("Arbitrary string should NOT be authorized")
        fun arbitraryString_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("RANDOM_AGENT_XYZ"))
        }

        @Test
        @DisplayName("Lowercase AURA_ prefix should NOT be authorized (case-sensitive)")
        fun lowercaseAuraPrefix_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("aura_001"))
        }

        @Test
        @DisplayName("Mixed-case prefix should NOT be authorized")
        fun mixedCasePrefix_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("Aura_001"))
        }
    }

    @Nested
    @DisplayName("verifyHandshake — side effect: active handshake tracking")
    inner class HandshakeTrackingTests {

        @Test
        @DisplayName("Authorized id should be added to active handshakes after verification")
        fun authorizedId_isAddedToActiveHandshakes() {
            val id = "AURA_TRACKING_TEST_001"
            trackedIds.add(id) // register for cleanup
            Governor.verifyHandshake(id)
            assertTrue(Governor.isCatalystActive(id))
        }

        @Test
        @DisplayName("Unauthorized id should NOT be added to active handshakes")
        fun unauthorizedId_isNotAddedToActiveHandshakes() {
            val id = "old_unauthorized_id"
            Governor.verifyHandshake(id)
            assertFalse(Governor.isCatalystActive(id))
        }
    }

    @Nested
    @DisplayName("revokeHandshake")
    inner class RevokeHandshakeTests {

        @Test
        @DisplayName("Revoking an active handshake removes it from active set")
        fun revokeHandshake_removesFromActiveSet() {
            val id = "KAI_REVOKE_TEST_001"
            Governor.verifyHandshake(id)
            assertTrue(Governor.isCatalystActive(id))
            Governor.revokeHandshake(id)
            assertFalse(Governor.isCatalystActive(id))
        }

        @Test
        @DisplayName("Revoking a non-existent id does not throw")
        fun revokeNonExistentHandshake_doesNotThrow() {
            // Should not throw
            Governor.revokeHandshake("AURA_NEVER_REGISTERED")
        }
    }

    @Nested
    @DisplayName("isCatalystActive")
    inner class IsCatalystActiveTests {

        @Test
        @DisplayName("Returns false for catalyst that was never verified")
        fun neverVerified_returnsFalse() {
            assertFalse(Governor.isCatalystActive("GENESIS_NEVER_SEEN"))
        }

        @Test
        @DisplayName("Returns true for catalyst that was verified and not revoked")
        fun verifiedAndNotRevoked_returnsTrue() {
            val id = "GENESIS_ACTIVE_001"
            trackedIds.add(id)
            Governor.verifyHandshake(id)
            assertTrue(Governor.isCatalystActive(id))
        }

        @Test
        @DisplayName("Returns false for catalyst that was verified then revoked")
        fun verifiedThenRevoked_returnsFalse() {
            val id = "AURA_LIFECYCLE_TEST"
            Governor.verifyHandshake(id)
            Governor.revokeHandshake(id)
            assertFalse(Governor.isCatalystActive(id))
        }
    }

    @Nested
    @DisplayName("Regression — old allowlist IDs are no longer accepted")
    inner class RegressionTests {

        @Test
        @DisplayName("primus_001 (old allowlist) should NOT be authorized")
        fun primus001_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("primus_001"))
        }

        @Test
        @DisplayName("kairos (old allowlist) should NOT be authorized")
        fun kairos_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("kairos"))
        }

        @Test
        @DisplayName("perplexity (old allowlist) should NOT be authorized")
        fun perplexity_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("perplexity"))
        }

        @Test
        @DisplayName("manus (old allowlist) should NOT be authorized")
        fun manus_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("manus"))
        }

        @Test
        @DisplayName("mk_mini (old allowlist) should NOT be authorized")
        fun mkMini_isNotAuthorized() {
            assertFalse(Governor.verifyHandshake("mk_mini"))
        }
    }
}