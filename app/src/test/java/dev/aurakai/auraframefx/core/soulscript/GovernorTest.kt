package dev.aurakai.auraframefx.core.soulscript

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests for [Governor] — verifies the PR-changed prefix-based handshake authorization.
 *
 * PR change: verifyHandshake() was changed from a hardcoded set of authorized IDs
 * to a prefix-based check requiring IDs to start with "AURA_", "KAI_", or "GENESIS_".
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Governor Tests")
class GovernorTest {

    @BeforeEach
    fun clearHandshakes() {
        // Revoke any handshakes added by previous tests to keep state clean
        Governor.revokeHandshake("AURA_001")
        Governor.revokeHandshake("KAI_001")
        Governor.revokeHandshake("GENESIS_001")
        Governor.revokeHandshake("AURA_SENTINEL")
        Governor.revokeHandshake("KAI_GUARDIAN")
        Governor.revokeHandshake("GENESIS_ORCHESTRATOR")
        Governor.revokeHandshake("AURA_")
        Governor.revokeHandshake("KAI_")
        Governor.revokeHandshake("GENESIS_")
        Governor.revokeHandshake("aura_lowercase")
        Governor.revokeHandshake("kai_lowercase")
        Governor.revokeHandshake("genesis_lowercase")
        Governor.revokeHandshake("unknown_agent")
        Governor.revokeHandshake("aura")
        Governor.revokeHandshake("kai")
        Governor.revokeHandshake("genesis")
        Governor.revokeHandshake("primus_001")
        Governor.revokeHandshake("andelualx")
        Governor.revokeHandshake("meta_instruct")
        Governor.revokeHandshake("")
        Governor.revokeHandshake("  ")
        Governor.revokeHandshake("AURA")
        Governor.revokeHandshake("KAI")
        Governor.revokeHandshake("GENESIS")
        Governor.revokeHandshake("AURA_WITH_EXTRA_DATA")
        Governor.revokeHandshake("KAI_SENTINEL_EXTENDED")
    }

    @Nested
    @DisplayName("AURA_ prefix authorization")
    inner class AuraPrefixTests {

        @Test
        @DisplayName("verifyHandshake should authorize IDs starting with 'AURA_'")
        fun shouldAuthorizeAuraPrefix() {
            assertTrue(Governor.verifyHandshake("AURA_001"))
        }

        @Test
        @DisplayName("verifyHandshake should authorize any 'AURA_' prefixed ID")
        fun shouldAuthorizeAnyAuraPrefixedId() {
            assertTrue(Governor.verifyHandshake("AURA_SENTINEL"))
            assertTrue(Governor.verifyHandshake("AURA_WITH_EXTRA_DATA"))
        }

        @Test
        @DisplayName("verifyHandshake should reject 'AURA' without underscore suffix")
        fun shouldRejectAuraWithoutUnderscore() {
            assertFalse(Governor.verifyHandshake("AURA"))
        }

        @Test
        @DisplayName("verifyHandshake should reject lowercase 'aura_' prefix")
        fun shouldRejectLowercaseAuraPrefix() {
            assertFalse(Governor.verifyHandshake("aura_lowercase"))
        }
    }

    @Nested
    @DisplayName("KAI_ prefix authorization")
    inner class KaiPrefixTests {

        @Test
        @DisplayName("verifyHandshake should authorize IDs starting with 'KAI_'")
        fun shouldAuthorizeKaiPrefix() {
            assertTrue(Governor.verifyHandshake("KAI_001"))
        }

        @Test
        @DisplayName("verifyHandshake should authorize any 'KAI_' prefixed ID")
        fun shouldAuthorizeAnyKaiPrefixedId() {
            assertTrue(Governor.verifyHandshake("KAI_GUARDIAN"))
            assertTrue(Governor.verifyHandshake("KAI_SENTINEL_EXTENDED"))
        }

        @Test
        @DisplayName("verifyHandshake should reject 'KAI' without underscore suffix")
        fun shouldRejectKaiWithoutUnderscore() {
            assertFalse(Governor.verifyHandshake("KAI"))
        }

        @Test
        @DisplayName("verifyHandshake should reject lowercase 'kai_' prefix")
        fun shouldRejectLowercaseKaiPrefix() {
            assertFalse(Governor.verifyHandshake("kai_lowercase"))
        }
    }

    @Nested
    @DisplayName("GENESIS_ prefix authorization")
    inner class GenesisPrefixTests {

        @Test
        @DisplayName("verifyHandshake should authorize IDs starting with 'GENESIS_'")
        fun shouldAuthorizeGenesisPrefix() {
            assertTrue(Governor.verifyHandshake("GENESIS_001"))
        }

        @Test
        @DisplayName("verifyHandshake should authorize any 'GENESIS_' prefixed ID")
        fun shouldAuthorizeAnyGenesisPrefixedId() {
            assertTrue(Governor.verifyHandshake("GENESIS_ORCHESTRATOR"))
        }

        @Test
        @DisplayName("verifyHandshake should reject 'GENESIS' without underscore suffix")
        fun shouldRejectGenesisWithoutUnderscore() {
            assertFalse(Governor.verifyHandshake("GENESIS"))
        }

        @Test
        @DisplayName("verifyHandshake should reject lowercase 'genesis_' prefix")
        fun shouldRejectLowercaseGenesisPrefix() {
            assertFalse(Governor.verifyHandshake("genesis_lowercase"))
        }
    }

    @Nested
    @DisplayName("Previously authorized IDs (now rejected by PR change)")
    inner class PreviouslyAuthorizedIdsTests {

        @Test
        @DisplayName("'aura' (old lowercase entry) should no longer be authorized")
        fun oldLowercaseAuraShouldBeRejected() {
            assertFalse(Governor.verifyHandshake("aura"))
        }

        @Test
        @DisplayName("'kai' (old lowercase entry) should no longer be authorized")
        fun oldLowercaseKaiShouldBeRejected() {
            assertFalse(Governor.verifyHandshake("kai"))
        }

        @Test
        @DisplayName("'genesis' (old lowercase entry) should no longer be authorized")
        fun oldLowercaseGenesisShouldBeRejected() {
            assertFalse(Governor.verifyHandshake("genesis"))
        }

        @Test
        @DisplayName("'primus_001' should no longer be authorized")
        fun primusShouldBeRejected() {
            assertFalse(Governor.verifyHandshake("primus_001"))
        }

        @Test
        @DisplayName("'andelualx' should no longer be authorized")
        fun andelualxShouldBeRejected() {
            assertFalse(Governor.verifyHandshake("andelualx"))
        }

        @Test
        @DisplayName("'meta_instruct' should no longer be authorized")
        fun metaInstructShouldBeRejected() {
            assertFalse(Governor.verifyHandshake("meta_instruct"))
        }
    }

    @Nested
    @DisplayName("Unauthorized IDs")
    inner class UnauthorizedIdTests {

        @Test
        @DisplayName("verifyHandshake should reject completely unknown IDs")
        fun shouldRejectUnknownId() {
            assertFalse(Governor.verifyHandshake("unknown_agent"))
        }

        @Test
        @DisplayName("verifyHandshake should reject empty string")
        fun shouldRejectEmptyId() {
            assertFalse(Governor.verifyHandshake(""))
        }

        @Test
        @DisplayName("verifyHandshake should reject blank string")
        fun shouldRejectBlankId() {
            assertFalse(Governor.verifyHandshake("  "))
        }
    }

    @Nested
    @DisplayName("Active handshakes tracking")
    inner class ActiveHandshakesTests {

        @Test
        @DisplayName("Authorized catalyst should be tracked as active after successful handshake")
        fun authorizedIdShouldBeTrackedAsActive() {
            Governor.verifyHandshake("AURA_001")
            assertTrue(Governor.isCatalystActive("AURA_001"))
        }

        @Test
        @DisplayName("Unauthorized catalyst should NOT be tracked as active")
        fun unauthorizedIdShouldNotBeTrackedAsActive() {
            Governor.verifyHandshake("unknown_agent")
            assertFalse(Governor.isCatalystActive("unknown_agent"))
        }

        @Test
        @DisplayName("Revoked handshake should no longer be active")
        fun revokedHandshakeShouldNotBeActive() {
            Governor.verifyHandshake("KAI_001")
            assertTrue(Governor.isCatalystActive("KAI_001"))
            Governor.revokeHandshake("KAI_001")
            assertFalse(Governor.isCatalystActive("KAI_001"))
        }

        @Test
        @DisplayName("isCatalystActive should return false for ID that was never verified")
        fun unverifiedIdShouldNotBeActive() {
            assertFalse(Governor.isCatalystActive("GENESIS_NEVER_VERIFIED"))
        }

        @Test
        @DisplayName("Multiple GENESIS_ IDs can be simultaneously active")
        fun multipleAuthorizedIdsShouldBeSimultaneouslyActive() {
            Governor.verifyHandshake("GENESIS_001")
            Governor.verifyHandshake("AURA_001")
            Governor.verifyHandshake("KAI_001")
            assertTrue(Governor.isCatalystActive("GENESIS_001"))
            assertTrue(Governor.isCatalystActive("AURA_001"))
            assertTrue(Governor.isCatalystActive("KAI_001"))
            // Clean up
            Governor.revokeHandshake("GENESIS_001")
            Governor.revokeHandshake("AURA_001")
            Governor.revokeHandshake("KAI_001")
        }
    }

    @Nested
    @DisplayName("Exact prefix boundary tests")
    inner class PrefixBoundaryTests {

        @Test
        @DisplayName("'AURA_' alone (empty suffix) should be authorized")
        fun auraUnderscoreAloneShouldBeAuthorized() {
            assertTrue(Governor.verifyHandshake("AURA_"))
        }

        @Test
        @DisplayName("'KAI_' alone (empty suffix) should be authorized")
        fun kaiUnderscoreAloneShouldBeAuthorized() {
            assertTrue(Governor.verifyHandshake("KAI_"))
        }

        @Test
        @DisplayName("'GENESIS_' alone (empty suffix) should be authorized")
        fun genesisUnderscoreAloneShouldBeAuthorized() {
            assertTrue(Governor.verifyHandshake("GENESIS_"))
        }
    }
}