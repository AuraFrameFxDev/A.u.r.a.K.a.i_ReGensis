package dev.aurakai.auraframefx.domains.core.logging

import android.content.Context
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 🌲 GLOBAL TIMBER INITIALIZER — Sovereign Edition
 *
 * One Tree to rule them all. Plants the primary DebugTree with Class/Method tracking.
 * Ensures consistent logging across all domains (Aura, Kai, Genesis, Cascade).
 */
@Singleton
class GlobalTimberInitializer @Inject constructor() {

    fun initialize(context: Context) {
        if (Timber.treeCount == 0) {
            // Plant the advanced DebugTree for better traceability
            Timber.plant(SovereignDebugTree())
            Timber.i("🌲 Sovereign Timber Initialized: Deep Trace Enabled")
        }
    }

    /**
     * Custom DebugTree that appends Class and Method info for chaotic debugging.
     */
    private class SovereignDebugTree : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String {
            return "LDO:${super.createStackElementTag(element)}.${element.methodName}():${element.lineNumber}"
        }
    }
}
