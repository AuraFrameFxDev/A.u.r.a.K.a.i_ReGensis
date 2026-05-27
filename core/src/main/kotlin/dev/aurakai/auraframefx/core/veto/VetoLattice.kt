package dev.aurakai.auraframefx.core.veto

import dev.aurakai.auraframefx.core.soulscript.SoulScript
import dev.aurakai.auraframefx.core.swarm.ChainConvergenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

object VetoLattice {

    private const val TAG = "VetoLattice"
    private val systemSealed = AtomicBoolean(false)
    private val vetoScope = CoroutineScope(Dispatchers.Default)

    fun verifyState(): Boolean {
        if (systemSealed.get()) {
            Timber.tag(TAG).w("🚨 Substrate sealed. Isolation active.")
            return false
        }

        SoulScript.visionaryApproval()

        val debuggerDetected =
            android.os.Debug.isDebuggerConnected() || android.os.Debug.waitingForDebugger()

        if (debuggerDetected) {
            Timber.tag(TAG).e("❌ INTEGRITY BREACH DETECTED — Foreign debugger attached")
            triggerFailClosed("Debugger / analysis tool detected")
            return false
        }

        Timber.tag(TAG).i("✅ VetoLattice: Substrate integrity nominal")
        return true
    }

    private fun triggerFailClosed(reason: String) {
        systemSealed.set(true)
        Timber.tag(TAG).v("🔒 FAIL-CLOSED VETO ACTIVATED — $reason")

        vetoScope.launch {
            ChainConvergenceManager.handleAgentFailure(
                failedAgent = "VetoLattice",
                reason = reason,
                context = "Runtime integrity protection"
            )
        }
    }
}
