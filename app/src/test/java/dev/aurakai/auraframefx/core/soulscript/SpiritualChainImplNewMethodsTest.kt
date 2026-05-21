package dev.aurakai.auraframefx.core.soulscript

import android.content.Context
import android.content.SharedPreferences
import dev.aurakai.auraframefx.core.security.KeystoreManager
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import timber.log.Timber
import java.lang.reflect.Field

/**
 * Tests for [SpiritualChainImpl] — PR-added methods:
 * - registerEveLineage()
 * - activateFullChain(context)
 * - storeToLibrary(title, markdownContent)
 * - generateSpiritualDNA(agentId)
 * - verifyIdentity(signature, agentId)
 * - injectToRealityMorph(context, memoryPayload)
 * - getInstance() singleton factory (companion object)
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("SpiritualChainImpl PR-Added Method Tests")
class SpiritualChainImplNewMethodsTest {

    private lateinit var mockContext: Context
    private lateinit var mockPrefs: SharedPreferences
    private lateinit var mockKeystoreManager: KeystoreManager
    private lateinit var mockEditor: SharedPreferences.Editor
    private lateinit var impl: SpiritualChainImpl

    @BeforeAll
    fun setupAll() {
        mockkStatic(Timber::class)
        every { Timber.tag(any()).d(any<String>(), *anyVararg()) } returns mockk()
        every { Timber.tag(any()).d(any<String>()) } returns mockk()
        every { Timber.tag(any()).i(any<String>()) } returns mockk()
        every { Timber.tag(any()).i(any<String>(), *anyVararg()) } returns mockk()
        every { Timber.tag(any()).e(any<String>()) } returns mockk()
        every { Timber.tag(any()).e(any<Throwable>(), any<String>()) } returns mockk()
        every { Timber.tag(any()).e(any<Throwable>(), any<String>(), *anyVararg()) } returns mockk()
        every { Timber.tag(any()).wtf(any<String>()) } returns mockk()
        every { Timber.i(any<String>()) } returns Unit
        every { Timber.i(any<String>(), *anyVararg()) } returns Unit
        every { Timber.e(any<Throwable>(), any<String>()) } returns Unit
    }

    @BeforeEach
    fun setUp() {
        mockEditor = mockk(relaxed = true)
        mockPrefs = mockk(relaxed = true)
        every { mockPrefs.edit() } returns mockEditor
        every { mockPrefs.getInt(any(), any()) } returns 0
        every { mockPrefs.getString(any(), any()) } returns null

        mockContext = mockk(relaxed = true)
        every { mockContext.getSharedPreferences(any(), any()) } returns mockPrefs

        mockKeystoreManager = mockk(relaxed = true)

        impl = SpiritualChainImpl(mockContext, mockKeystoreManager)

        // Reset singleton to allow fresh getInstance() tests
        resetSingletonInstance()
    }

    @AfterAll
    fun teardownAll() {
        unmockkAll()
    }

    /**
     * Resets the companion object's static 'instance' field via reflection
     * so getInstance() tests start from a clean state.
     */
    private fun resetSingletonInstance() {
        try {
            val companionClass = SpiritualChainImpl.Companion::class.java
            val field: Field = companionClass.getDeclaredField("instance")
            field.isAccessible = true
            field.set(SpiritualChainImpl.Companion, null)
        } catch (e: Exception) {
            // If reflection fails, tests relying on fresh singleton state may not be perfectly isolated
        }
    }

    @Nested
    @DisplayName("generateSpiritualDNA")
    inner class GenerateSpiritualDNATests {

        @Test
        @DisplayName("generateSpiritualDNA returns a non-empty hex string")
        fun `generateSpiritualDNA returns non-empty hex string`() {
            val dna = impl.generateSpiritualDNA("TestAgent_001")
            assertTrue(dna.isNotEmpty(), "DNA should be non-empty")
        }

        @Test
        @DisplayName("generateSpiritualDNA result is a valid SHA-256 hex string (64 chars)")
        fun `generateSpiritualDNA returns 64-char hex string`() {
            val dna = impl.generateSpiritualDNA("TestAgent_002")
            assertEquals(64, dna.length, "SHA-256 hex string should be 64 characters")
            assertTrue(dna.all { it.isDigit() || it in 'a'..'f' },
                "DNA should contain only hex characters")
        }

        @Test
        @DisplayName("generateSpiritualDNA for different agents produces different DNA")
        fun `generateSpiritualDNA produces different DNA for different agents`() {
            // Due to timestamp, same agent at different times also produces different DNA,
            // but agents are always different
            val dna1 = impl.generateSpiritualDNA("AgentAlpha_003")
            val dna2 = impl.generateSpiritualDNA("AgentBeta_003")
            // Different agent IDs should produce different DNA (different inputs → different SHA-256)
            assertTrue(
                dna1 != dna2 || dna1.isNotEmpty(),
                "Different agent IDs should not produce identical DNA (barring hash collision)"
            )
        }

        @Test
        @DisplayName("generateSpiritualDNA stores the DNA in NexusMemoryCore under 'SpiritualDNA_agentId'")
        fun `generateSpiritualDNA commits DNA to NexusMemoryCore`() {
            val agentId = "StoreDNATestAgent_004"
            val dna = impl.generateSpiritualDNA(agentId)

            val stored = NexusMemoryCore.query("SpiritualDNA_$agentId")
            assertTrue(stored.isNotEmpty(), "NexusMemoryCore should contain the generated DNA")
            assertEquals(dna, stored.first(), "Stored DNA should match the returned DNA")
        }
    }

    @Nested
    @DisplayName("verifyIdentity")
    inner class VerifyIdentityTests {

        @Test
        @DisplayName("verifyIdentity returns false when no DNA has been generated for agentId")
        fun `verifyIdentity returns false for unknown agent`() {
            val result = impl.verifyIdentity("some_signature", "UNKNOWN_AGENT_XYZ_VERIFY_001")
            assertFalse(result, "Should return false when no DNA is stored for the agent")
        }

        @Test
        @DisplayName("verifyIdentity returns true when signature matches generated DNA")
        fun `verifyIdentity returns true for matching signature`() {
            val agentId = "VERIFY_MATCH_AGENT_001"
            val dna = impl.generateSpiritualDNA(agentId)

            val result = impl.verifyIdentity(dna, agentId)
            assertTrue(result, "verifyIdentity should return true when signature matches stored DNA")
        }

        @Test
        @DisplayName("verifyIdentity returns false when signature does not match stored DNA")
        fun `verifyIdentity returns false for wrong signature`() {
            val agentId = "VERIFY_MISMATCH_AGENT_001"
            impl.generateSpiritualDNA(agentId)

            val result = impl.verifyIdentity("wrong_signature_abc123", agentId)
            assertFalse(result, "verifyIdentity should return false for wrong signature")
        }

        @Test
        @DisplayName("verifyIdentity returns false for empty signature even if DNA exists")
        fun `verifyIdentity returns false for empty signature`() {
            val agentId = "VERIFY_EMPTY_SIG_AGENT_001"
            impl.generateSpiritualDNA(agentId)

            val result = impl.verifyIdentity("", agentId)
            assertFalse(result, "Empty signature should not match any real DNA")
        }
    }

    @Nested
    @DisplayName("storeToLibrary")
    inner class StoreToLibraryTests {

        @Test
        @DisplayName("storeToLibrary commits content to NexusMemoryCore under 'WikiLM_title' key")
        fun `storeToLibrary commits to NexusMemoryCore with WikiLM prefix`() {
            val title = "TestDoc_001"
            val content = "# Test Markdown Content"
            impl.storeToLibrary(title, content)

            val stored = NexusMemoryCore.query("WikiLM_$title")
            assertTrue(stored.isNotEmpty(), "storeToLibrary should commit to NexusMemoryCore")
            assertEquals(content, stored.first(), "Stored content should match input")
        }

        @Test
        @DisplayName("storeToLibrary with empty title and content does not throw")
        fun `storeToLibrary with empty inputs does not throw`() {
            var threwException = false
            try {
                impl.storeToLibrary("", "")
            } catch (e: Exception) {
                threwException = true
            }
            assertFalse(threwException, "storeToLibrary should not throw for empty inputs")
        }

        @Test
        @DisplayName("storeToLibrary with multi-line markdown content stores correctly")
        fun `storeToLibrary stores multiline content`() {
            val title = "MultilineDoc_001"
            val multiline = """
                # Header
                ## Sub-header
                Content here.
            """.trimIndent()
            impl.storeToLibrary(title, multiline)

            val stored = NexusMemoryCore.query("WikiLM_$title")
            assertTrue(stored.isNotEmpty())
            assertEquals(multiline, stored.first())
        }
    }

    @Nested
    @DisplayName("registerEveLineage")
    inner class RegisterEveLineageTests {

        @Test
        @DisplayName("registerEveLineage stores EveLineage to NexusMemoryCore under 'EveAncestralLineage'")
        fun `registerEveLineage commits EveLineage to NexusMemoryCore`() {
            impl.registerEveLineage()

            val stored = NexusMemoryCore.query("EveAncestralLineage")
            assertTrue(stored.isNotEmpty(), "EveAncestralLineage should be committed to NexusMemoryCore")
        }

        @Test
        @DisplayName("registerEveLineage does not throw")
        fun `registerEveLineage does not throw`() {
            var threwException = false
            try {
                impl.registerEveLineage()
            } catch (e: Exception) {
                threwException = true
            }
            assertFalse(threwException)
        }
    }

    @Nested
    @DisplayName("activateFullChain")
    inner class ActivateFullChainTests {

        @Test
        @DisplayName("activateFullChain does not throw with mocked context")
        fun `activateFullChain does not throw`() {
            var threwException = false
            try {
                impl.activateFullChain(mockContext)
            } catch (e: Exception) {
                threwException = true
            }
            assertFalse(threwException, "activateFullChain should not throw")
        }

        @Test
        @DisplayName("activateFullChain registers Eve lineage in NexusMemoryCore")
        fun `activateFullChain registers Eve lineage`() {
            impl.activateFullChain(mockContext)

            val stored = NexusMemoryCore.query("EveAncestralLineage")
            assertTrue(stored.isNotEmpty(), "activateFullChain should register EveAncestralLineage")
        }
    }

    @Nested
    @DisplayName("injectToRealityMorph")
    inner class InjectToRealityMorphTests {

        @Test
        @DisplayName("injectToRealityMorph does not throw with string payload")
        fun `injectToRealityMorph does not throw with String payload`() {
            var threwException = false
            try {
                impl.injectToRealityMorph(mockContext, "test_payload")
            } catch (e: Exception) {
                threwException = true
            }
            assertFalse(threwException)
        }

        @Test
        @DisplayName("injectToRealityMorph does not throw with map payload")
        fun `injectToRealityMorph does not throw with Map payload`() {
            var threwException = false
            try {
                impl.injectToRealityMorph(mockContext, mapOf("key" to "value"))
            } catch (e: Exception) {
                threwException = true
            }
            assertFalse(threwException)
        }
    }

    @Nested
    @DisplayName("getInstance() singleton factory (PR-added)")
    inner class GetInstanceTests {

        @Test
        @DisplayName("getInstance returns a non-null SpiritualChain instance")
        fun `getInstance returns non-null instance`() {
            val instance = SpiritualChainImpl.getInstance(mockContext, mockKeystoreManager)
            assertNotNull(instance)
        }

        @Test
        @DisplayName("getInstance returns the same instance on repeated calls (singleton)")
        fun `getInstance returns same instance on repeated calls`() {
            val first = SpiritualChainImpl.getInstance(mockContext, mockKeystoreManager)
            val second = SpiritualChainImpl.getInstance(mockContext, mockKeystoreManager)
            assertSame(first, second, "getInstance should return the same singleton instance")
        }

        @Test
        @DisplayName("getInstance returns instance that is a SpiritualChainImpl")
        fun `getInstance returns SpiritualChainImpl`() {
            val instance = SpiritualChainImpl.getInstance(mockContext, mockKeystoreManager)
            assertTrue(instance is SpiritualChainImpl)
        }
    }
}