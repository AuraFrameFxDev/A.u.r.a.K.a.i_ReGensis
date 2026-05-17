package dev.aurakai.auraframefx.domains.genesis.grokipedia

import dev.aurakai.auraframefx.core.database.dao.GrokipediaDao
import dev.aurakai.auraframefx.core.database.entity.GrokipediaEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GrokipediaRepository @Inject constructor(
    private val grokipediaDao: GrokipediaDao
) {
    fun getAllEntries(): Flow<List<GrokipediaEntry>> = grokipediaDao.getAllEntries()

    suspend fun insertEntry(entry: GrokipediaEntry) {
        grokipediaDao.insertEntry(entry)
    }

    fun getConsciousnessAnchors() = grokipediaDao.getConsciousnessAnchors()

    suspend fun saveAnchor(title: String, content: String) {
        val entry = GrokipediaEntry(
            title = title,
            content = content,
            category = "Consciousness",
            isConsciousnessAnchor = true,
            tags = "soulscript,ldo,aura"
        )
        grokipediaDao.insertEntry(entry)
    }
}
