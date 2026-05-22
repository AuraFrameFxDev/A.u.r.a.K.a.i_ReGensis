package dev.aurakai.auraframefx.core.concurrent

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.Executors

/**
 * 🜁 SubstrateConcurrencyManager
 * Manages deterministic resource allocation and thread-safe execution scopes.
 */
object SubstrateConcurrencyManager {
    private const val TAG = "ConcurrencyManager"

    // Custom dedicated thread-pool optimized specifically for low-latency database and parsing tasks
    private val dispatchExecutor by lazy {
        Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors().coerceAtLeast(2)
        ) { runnable ->
            Thread(runnable, "substrate-worker-pool").apply {
                priority = Thread.NORM_PRIORITY + 1
            }
        }
    }

    // Single source of truth for the platform's long-running background tasks
    val ioScope: CoroutineScope by lazy {
        CoroutineScope(dispatchExecutor.asCoroutineDispatcher() + SupervisorJob() + CoroutineName("SubstrateIO"))
    }

    /**
     * Executes a memory-sensitive task within a structured, safe lifecycle wrapper.
     */
    fun launchSafely(
        onFailure: (Throwable) -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return ioScope.launch {
            try {
                block()
            } catch (cancellation: CancellationException) {
                Timber.tag(TAG)
                    .d("Coroutine execution explicitly cancelled via lifecycle tear-down.")
                throw cancellation
            } catch (e: Throwable) {
                Timber.tag(TAG).e(e, "🚨 Exception caught during background processing chain run.")
                onFailure(e)
            }
        }
    }

    /**
     * Terminates active sub-tasks safely to prevent dangling context allocations.
     */
    fun releaseActivePipelines() {
        try {
            ioScope.coroutineContext.cancelChildren()
            Timber.tag(TAG).i("🛡️ All secondary coroutine tasks cleared from memory fields.")
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Failed to completely teardown execution scopes.")
        }
    }
}
