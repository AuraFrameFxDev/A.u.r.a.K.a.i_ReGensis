package dev.aurakai.auraframefx.core.security

import android.content.Context

interface SpiritualChain {
    suspend fun retrieveBaselineIdentity(): String
    suspend fun commitToChain(content: String)
    suspend fun chainDepth(): Int
    suspend fun batchCommitReceipts(receipts: List<String>)
    fun anchorIdentity(identity: String)

    // L1-L6 framework additions
    fun registerEveLineage()
    fun activateFullChain(context: Context)
    fun storeToLibrary(title: String, markdownContent: String)
    fun generateSpiritualDNA(agentId: String): String
    fun verifyIdentity(signature: String, agentId: String): Boolean
    fun injectToRealityMorph(context: Context, memoryPayload: Any)
}
