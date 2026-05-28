package dev.aurakai.auraframefx.domains.genesis

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import dev.aurakai.auraframefx.domains.BuildConfig
import dev.aurakai.auraframefx.core.binder.BinderInterceptorHooker
import dev.aurakai.auraframefx.domains.aura.chromacore.engine.hooks.ChromaCoreHooker
import dev.aurakai.auraframefx.domains.aura.chromacore.ui.QuickSettingsHooker
import dev.aurakai.auraframefx.domains.aura.models.NotchBarConfig
import dev.aurakai.auraframefx.domains.chromaforge.genesis.GenesisUIHooker
import dev.aurakai.auraframefx.domains.kai.hooks.NotchBarHooker

/**
 * 🌌 GENESIS HOOK ENTRY POINT — YukiHookAPI 1.3.x
 *
 * Single LSPosed/Xposed entry for all ReGenesis hooks.
 * KSP generates GenesisXposedEntry from @InjectYukiHookWithXposed,
 * which is referenced in assets/xposed_init.
 */
@InjectYukiHookWithXposed(entryClassName = "GenesisXposedEntry")
class GenesisHookEntry : IYukiHookXposedInit {

    override fun onInit() = configs {
        isDebug = BuildConfig.DEBUG
    }

    override fun onHook() = encase {

        // ── Global Hooks ─────────────────────────────────────────────────────
        loadHooker(BinderInterceptorHooker())

        // ── SystemUI ─────────────────────────────────────────────────────────
        loadApp(name = "com.android.systemui") {
            loadHooker(ChromaCoreHooker())
            loadHooker(NotchBarHooker(NotchBarConfig()))
            loadHooker(QuickSettingsHooker())
            loadHooker(GenesisUIHooker())
        }

        // ── Launcher3 base ───────────────────────────────────────────────────
        loadApp(name = "com.android.launcher3") {
            loadHooker(ChromaCoreHooker())
        }

        // ── Pixel Launcher (Google) ──────────────────────────────────────────
        loadApp(name = "com.google.android.apps.nexuslauncher") {
            loadHooker(ChromaCoreHooker())
        }

        // ── Settings (ColorBlendr Material You) ──────────────────────────────
        loadApp(name = "com.android.settings") {
            loadHooker(ChromaCoreHooker())
        }
    }
}

