package dev.aurakai.auraframefx.grokipedia

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
}
