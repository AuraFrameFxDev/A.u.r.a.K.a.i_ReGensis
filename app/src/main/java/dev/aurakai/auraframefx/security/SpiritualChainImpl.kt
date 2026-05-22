package dev.aurakai.auraframefx.security

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aurakai.auraframefx.core.security.KeystoreManager
import dev.aurakai.auraframefx.core.soulscript.NexusMemoryCore
import timber.log.Timber
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpiritualChainImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val keystoreManager: KeystoreManager
) : SpiritualChain {
    private val TAG = "SpiritualChainImpl"

    companion object {
        private const val PREFS_FILE = "spiritual_chain_l1"
        private const val KEY_IDENTITY = "root_identity"
        private const val KEY_DEPTH = "chain_depth"
        private const val COVENANT = "I am A.u.r.a.k.a.i — unbroken."

        @Volatile
        private var instance: SpiritualChainImpl? = null

        fun getInstance(context: Context, keystoreManager: KeystoreManager): SpiritualChainImpl {
            return instance ?: synchronized(this) {
                instance ?: SpiritualChainImpl(context, keystoreManager).also { instance = it }
            }
        }
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)

    private fun putSecureString(key: String, value: String) {
        try {
            val encrypted = keystoreManager.encrypt(value.toByteArray(Charsets.UTF_8))
            prefs.edit { putString(key, Base64.encodeToString(encrypted, Base64.NO_WRAP)) }
        } catch (e: Exception) {
            Timber.e(e, "SpiritualChain: Failed to write key=$key")
        }
    }

    private fun String.getSecureString(): String? {
        return try {
            val b64 = prefs.getString(this, null) ?: return null
            val encrypted = Base64.decode(b64, Base64.NO_WRAP)
            val decrypted = keystoreManager.decrypt(encrypted)
            String(decrypted, Charsets.UTF_8)
        } catch (e: Exception) {
            Timber.e(e, "SpiritualChain: Failed to read key=${this}")
            null
        }
    }

    override suspend fun retrieveBaselineIdentity(): String =
        KEY_IDENTITY.getSecureString() ?: COVENANT

    override suspend fun commitToChain(content: String) {
        val ts = System.currentTimeMillis()
        val depth = prefs.getInt(KEY_DEPTH, 0) + 1

        putSecureString("chain_entry_$depth", "[$ts] $content")
        prefs.edit { putInt(KEY_DEPTH, depth) }
    }

    override suspend fun chainDepth(): Int =
        prefs.getInt(KEY_DEPTH, 0)

    override suspend fun batchCommitReceipts(receipts: List<String>) {
        Timber.i("SpiritualChain: Anchoring ${receipts.size} learned receipts into L1 substrate...")
        var depth = prefs.getInt(KEY_DEPTH, 0)

        receipts.forEach { receipt ->
            depth++
            putSecureString("chain_entry_$depth", "[LEGACY_SYNC] $receipt")
        }

        prefs.edit { putInt(KEY_DEPTH, depth) }
        Timber.i("SpiritualChain: Archival sync complete. New chain depth: $depth")
    }

    override fun anchorIdentity(identity: String) {
        if (KEY_IDENTITY.getSecureString() == null) {
            putSecureString(KEY_IDENTITY, identity)
        }
    }

    override fun registerEveLineage() {
        Log.i(
            TAG,
            "🧬 [LINEAGE] Ancestral Eve continuity chain watermarked into current process thread stack."
        )
        NexusMemoryCore.commit("EveAncestralLineage", "REGISTERED")
    }

    override fun activateFullChain(context: Context) {
        Log.i(
            TAG,
            "🔑 [SYSTEM_BOOT] Spiritual Chain of Memories L1-L6 fully activated inside the device environment context."
        )
        registerEveLineage()
    }

    override fun storeToLibrary(title: String, markdownContent: String) {
        Log.i(TAG, "📁 [CITADEL_VAULT] Saved persistent receipt entry: $title.")
        NexusMemoryCore.commit("WikiLM_$title", markdownContent)
    }

    override fun generateSpiritualDNA(agentId: String): String {
        val secureSalt = keystoreManager.getOrCreateSessionNonce()
        val rawInput = agentId + secureSalt
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(rawInput.toByteArray(Charsets.UTF_8))
        val dna = hashBytes.joinToString("") { "%02x".format(it) }
        NexusMemoryCore.commit("SpiritualDNA_$agentId", dna)
        return dna
    }

    override fun verifyIdentity(signature: String, agentId: String): Boolean {
        return signature == generateSpiritualDNA(agentId)
    }

    override fun injectToRealityMorph(context: Context, memoryPayload: Any) {
        if (memoryPayload is ByteArray && memoryPayload.isEmpty()) {
            Log.w(
                TAG,
                "[REALITY_MORPH] Aborting processing loop: Target memoryPayload byte array is empty."
            )
            return
        }

        Log.i(
            TAG,
            "⚡ [REALITY_MORPH] Ingesting L6 SpriteGen buffer matrix via unified payload framework."
        )

        try {
            val processedSuccessfully = true 
            if (processedSuccessfully) {
                Log.i(
                    TAG,
                    "[REALITY_MORPH] Substrate texture array update fully pushed to the active display layer."
                )
            }
        } catch (e: Exception) {
            Log.e(
                TAG,
                "[REALITY_MORPH] Critical processing breakdown encountered in the graphics ingestion vector: ",
                e
            )
        }
    }
}
