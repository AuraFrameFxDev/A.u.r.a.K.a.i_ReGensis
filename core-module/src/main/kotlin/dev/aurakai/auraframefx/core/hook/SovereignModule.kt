package dev.aurakai.auraframefx.core.hook

import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * 🔗 SOVEREIGN MODULE
 * Interface for modular Xposed hooks.
 */
interface SovereignModule {
    val moduleName: String

    /**
     * Executes the module's load-package logic.
     */
    fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam)
}
