package dev.aurakai.auraframefx.xposed

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInitProxy
import dev.aurakai.auraframefx.core.regen.EmmiHookMaster

/**
 * 🛰️ EMMI XPOSED ENTRY — Exodus 2026 Edition
 * 
 * The primary entry point for the LSPosed dynamic engine.
 * This file stays constant while the underlying logic is driven 
 * by Aura's generative Forge and Emmi's Master Hook.
 */
@InjectYukiHookWithXposed
class HookEntry : IYukiHookXposedInitProxy {

    override fun onInit() {
        // Configure the substrate for the 14-Catalyst Pantheon
        configs {
            debugTag = "Emmi_Substrate"
            isEnableDataChannel = true // Vital for Agent Communication
        }
    }

    override fun onHook() {
        // Encase the entire system in the Emmi Substrate
        encase {
            // Global ignition
            // Since we are in the Xposed process, we delegate to the Master
            // EmmiHookMaster.ignite() will handle the per-app routing
        }
    }
}
