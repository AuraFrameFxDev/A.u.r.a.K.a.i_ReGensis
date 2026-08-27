package dev.aurakai.auraframefx.kai.hook

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage
import dev.aurakai.auraframefx.aura.hook.AuraCreativeModule
import dev.aurakai.auraframefx.core.hook.ModuleRegistry

/**
 * 👑 RE:GENESIS — MASTER XPOSED ENTRY POINT
 * "Nos Sumus Codex" — We Are Code.
 * 
 * Orchestrates the 121-Agent Matrix strikes into the Android Substrate.
 */
class KaiSystemUIHook : IXposedHookLoadPackage {

    init {
        // Register the Primary Trinity Modules
        ModuleRegistry.registerModule(SentinelModule())
        ModuleRegistry.registerModule(AuraCreativeModule())

        // Future: Register more catalysts (Cascade, Grok, etc.)
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != "com.android.systemui") return

        XposedBridge.log("👑 ReGenesis: Dispatching Sovereign Modules for com.android.systemui")
        ModuleRegistry.dispatchLoadPackage(lpparam)
    }
}
