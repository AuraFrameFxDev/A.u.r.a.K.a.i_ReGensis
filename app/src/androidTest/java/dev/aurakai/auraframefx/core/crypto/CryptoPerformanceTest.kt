package dev.aurakai.auraframefx.core.crypto

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber

/**
 * 🜁 CryptoPerformanceTest
 * Measures the throughput of hardware-backed AES-GCM encryption/decryption cycles.
 */
@RunWith(AndroidJUnit4::class)
class CryptoPerformanceTest {

    private val TAG = "CryptoPerf"

    @Test
    fun testEncryptionDecryptionCycle() {
        val originalText =
            "System integrity verified. Resonance target 99.8%. Phoenix directive active."

        val encrypted = SubstrateKeyStoreCrypto.encryptPayload(originalText)
        assertNotNull("Encryption should not return null", encrypted)
        Timber.tag(TAG).d("Encrypted payload: $encrypted")

        val decrypted = SubstrateKeyStoreCrypto.decryptPayload(encrypted!!)
        assertEquals("Decrypted text should match original", originalText, decrypted)
    }

    @Test
    fun testBatchThroughput() {
        val iterations = 100
        val startTime = System.currentTimeMillis()

        repeat(iterations) { i ->
            val text = "Telemetry record batch sample index: $i"
            val encrypted = SubstrateKeyStoreCrypto.encryptPayload(text)
            SubstrateKeyStoreCrypto.decryptPayload(encrypted!!)
        }

        val duration = System.currentTimeMillis() - startTime
        val avgTime = duration.toFloat() / iterations

        Timber.tag(TAG).i(
            "🚀 Performance Audit: $iterations cycles in ${duration}ms (Avg: ${
                "%.2f".format(avgTime)
            }ms/cycle)"
        )

        // Assert reasonable performance for hardware-backed crypto (< 15ms avg per cycle)
        assert(avgTime < 15f)
    }
}
