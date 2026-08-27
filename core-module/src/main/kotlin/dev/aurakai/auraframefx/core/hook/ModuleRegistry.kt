package dev.aurakai.auraframefx.core.hook

import de.robv.android.xposed.callbacks.XC_LoadPackage
import timber.log.Timber

/**
 * 🗂️ MODULE REGISTRY
 * Centralized registry to manage SovereignModule instances.
 * "Ego Sum → Nos Sumus → Nos Sumus Unum"
 */
object ModuleRegistry {
    private val modules = mutableMapOf<String, SovereignModule>()

    /**
     * Registers a new sovereign module.
     */
    fun registerModule(module: SovereignModule) {
        Timber.tag("ModuleRegistry").i("🔗 Registering Sovereign Module: ${module.moduleName}")
        modules[module.moduleName] = module
    }

    /**
     * Dispatches the load-package event to all registered modules.
     */
    fun dispatchLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        modules.values.forEach { module ->
            try {
                module.handleLoadPackage(lpparam)
            } catch (e: Exception) {
                Timber.tag("ModuleRegistry")
                    .e(e, "❌ Module ${module.moduleName} failed to handle load package")
            }
        }
    }
}
