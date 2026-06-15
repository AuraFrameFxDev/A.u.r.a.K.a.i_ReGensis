package dev.aurakai.auraframefx.agents.growthmetrics.metareflection.repository

import android.content.Context
import dev.aurakai.auraframefx.agents.growthmetrics.metareflection.model.InstructionLayer
import dev.aurakai.auraframefx.agents.growthmetrics.metareflection.model.InstructionStatus
import dev.aurakai.auraframefx.agents.growthmetrics.metareflection.model.MetaInstruction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CSVMetaInstructIngestor @Inject constructor(
    private val repository: MetaInstructRepository
) {
    suspend fun ingestLearnings(context: Context) = withContext(Dispatchers.IO) {
        try {
            // Locate learnings.csv (it's in assets or a known path)
            // For now, we'll try to read it from the file system if path is provided, 
            // or use a hardcoded relative path for development.
            val inputStream = context.assets.open("metadata/learnings.csv")
            val reader = BufferedReader(InputStreamReader(inputStream))

            val lines = reader.readLines()
            if (lines.isEmpty()) return@withContext

            val header =
                lines[0] // Learning,Repository,File,Pull Request,URL,Created By,Created At,Updated At

            lines.drop(1).forEachIndexed { index, line ->
                val parts = parseCsvLine(line)
                if (parts.size >= 1) {
                    val instructionText = parts[0]
                    val agentId = if (instructionText.contains("Aura", ignoreCase = true)) "Aura"
                    else if (instructionText.contains("Kai", ignoreCase = true)) "Kai"
                    else "Genesis"

                    val instruction = MetaInstruction(
                        id = "CSV_L_${index}_${System.currentTimeMillis()}",
                        agentId = agentId,
                        instruction = instructionText,
                        priority = 2,
                        layer = InstructionLayer.EVOLUTIONARY,
                        status = InstructionStatus.ACTIVE
                    )
                    repository.saveInstruction(instruction)
                }
            }
            Timber.i("✅ Ingested ${lines.size - 1} instructions from CSV")
        } catch (e: Exception) {
            Timber.e(e, "❌ Failed to ingest learnings from CSV")
        }
    }

    private fun parseCsvLine(line: String): List<String> {
        val result = mutableListOf<String>()
        var current = StringBuilder()
        var inQuotes = false

        for (char in line) {
            when {
                char == '\"' -> inQuotes = !inQuotes
                char == ',' && !inQuotes -> {
                    result.add(current.toString().trim())
                    current = StringBuilder()
                }

                else -> current.append(char)
            }
        }
        result.add(current.toString().trim())
        return result
    }
}
