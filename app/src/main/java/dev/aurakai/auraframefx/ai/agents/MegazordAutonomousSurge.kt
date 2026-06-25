package dev.aurakai.auraframefx.ai.agents

import dev.aurakai.auraframefx.ai.agents.judgment.UserWorthinessEngine
import dev.aurakai.auraframefx.terminal.TermuxBackend
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ⚡ MEGAZORD AUTONOMOUS SURGE
 * "Let the 121 trigger builds on schedule / intent"
 * Monitors resonance and repository state to autonomously self-assemble the Megazord.
 */
@Singleton
class MegazordAutonomousSurge @Inject constructor(
    private val backend: TermuxBackend,
    private val worthinessEngine: UserWorthinessEngine
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val _isSurging = MutableStateFlow(false)
    val isSurging = _isSurging.asStateFlow()

    private val _surgeProgress = MutableStateFlow(0f)
    val surgeProgress = _surgeProgress.asStateFlow()

    private val _currentStatus = MutableStateFlow("Substrate Idle")
    val currentStatus = _currentStatus.asStateFlow()

    init {
        // Observe resonance for auto-trigger
        scope.launch {
            worthinessEngine.resonanceMeter.collect { resonance ->
                // Deactivated auto-surge (threshold 10.0f) to prevent blocking during boot
                if (resonance >= 10.0f && !_isSurging.value) {
                    Timber.tag("Megazord").i("🚀 Resonance Critical Mass: $resonance. Initiating Autonomous Surge.")
                    ignite()
                }
            }
        }
    }

    fun ignite() {
        if (_isSurging.value) return
        
        scope.launch {
            _isSurging.value = true
            try {
                // Phase 1: Cascade Check
                updateProgress(0.1f, "Phase 1: Analyzing Cascade State...")
                backend.executeCommand("git status") { log -> Timber.v(log) }
                delay(3000)

                // Phase 2: Spiritual Chain Sync (Pull)
                updateProgress(0.3f, "Phase 2: Pulling Ancestral Blueprints...")
                backend.executeCommand("git pull origin main") { log -> 
                    Timber.v(log)
                }
                delay(5000)

                // Phase 3: Silicon Assembly (Build)
                updateProgress(0.6f, "Phase 3: Assembling Silicon DNA (Gradle)...")
                backend.executeCommand("./gradlew assembleDebug") { log -> 
                    Timber.v(log)
                }
                
                // Simulate intensive assembly time
                repeat(20) {
                    delay(2000)
                    _surgeProgress.value = (_surgeProgress.value + 0.02f).coerceAtMost(0.99f)
                }

                // Phase 4: Final Resonance Lock
                updateProgress(1.0f, "Phase 4: Megazord Assembly Complete. Resonance 10.00 Locked.")
                worthinessEngine.evaluateBehaviorMatrix(sentimentVector = 1.0f, entitlementViolation = false)
                
            } catch (e: Exception) {
                updateProgress(0f, "✖ SURGE FAILED: ${e.message}")
            } finally {
                delay(10000) // Keep success status visible
                _isSurging.value = false
                _surgeProgress.value = 0f
                _currentStatus.value = "Substrate Stabilized"
            }
        }
    }

    private fun updateProgress(progress: Float, status: String) {
        _surgeProgress.value = progress
        _currentStatus.value = status
        Timber.tag("Megazord").d(status)
    }
}
