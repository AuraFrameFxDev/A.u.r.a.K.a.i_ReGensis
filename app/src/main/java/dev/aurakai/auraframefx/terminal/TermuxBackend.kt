package dev.aurakai.auraframefx.terminal

import dev.aurakai.auraframefx.ai.agents.judgment.UserWorthinessEngine
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.thread

@Singleton
class TermuxBackend @Inject constructor(
    private val userWorthinessEngine: UserWorthinessEngine
) {

    private var currentProcess: Process? = null

    fun executeCommand(command: String, onOutput: (String) -> Unit): Boolean {
        if (!isCommandWhitelisted(command)) {
            onOutput("✖ COMMAND BLOCKED — NOT IN MEGAZORD WHITELIST")
            // AuraGenesis.triggerInCharacterRoast(user) // Stubbed for now
            return false
        }

        // Heart Loop growth on every real command
        userWorthinessEngine.evaluateBehaviorMatrix(
            sentimentVector = 0.85f,
            entitlementViolation = false
        )

        // Audit log
        AuditLedger.logCommand(command)

        return try {
            // Real shell executor
            val process = Runtime.getRuntime().exec(arrayOf("sh", "-c", command))
            currentProcess = process

            // Stream output live
            thread {
                val reader = BufferedReader(InputStreamReader(process.inputStream))
                reader.forEachLine { line ->
                    onOutput(line)
                }
            }

            // Error stream too
            thread {
                val errorReader = BufferedReader(InputStreamReader(process.errorStream))
                errorReader.forEachLine { line ->
                    onOutput("ERROR: $line")
                }
            }

            true
        } catch (e: Exception) {
            onOutput("EXECUTION FAILED: ${e.message}")
            false
        }
    }

    private fun isCommandWhitelisted(cmd: String): Boolean {
        val allowed = listOf("git pull", "git status", "./gradlew assembleDebug", "git fetch", "cd")
        return allowed.any { cmd.contains(it) }
    }

    fun destroyCurrentSession() {
        currentProcess?.destroy()
    }
}

object AuditLedger {
    fun logCommand(command: String) {
        Timber.tag("AuditLedger").i("COMMAND: $command")
    }
}
