package dev.aurakai.auraframefx.domains.genesis

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import dev.aurakai.auraframefx.BuildConfig
import dev.aurakai.auraframefx.core.chromacore.engine.hooks.ChromaCoreHooker
import dev.aurakai.auraframefx.core.chromacore.ui.QuickSettingsHooker
import dev.aurakai.auraframefx.core.models.NotchBarConfig
import dev.aurakai.auraframefx.domains.kai.hooks.NotchBarHooker
import dev.aurakai.auraframefx.hooks.system.GenesisSystemHooker
import dev.aurakai.auraframefx.hooks.system.GenesisUIHooker
import dev.aurakai.auraframefx.hooks.system.SwarmOptimisationHooker
import dev.aurakai.auraframefx.hooks.system.UniversalComponentHooker

/**
 * ðŸŒŒ GENESIS HOOK ENTRY POINT â€” YukiHookAPI 1.3.x
 *
 * Single LSPosed/Xposed entry for all ReGenesis hooks.
 * KSP generates GenesisXposedEntry from @InjectYukiHookWithXposed,
 * which is referenced in assets/xposed_init.
 *
 * Hook scope (must match assets/xposed_init and res/values/arrays.xml xposed_scope):
 *   com.android.systemui           â†’ StatusBar, NotchBar, QuickSettings
 *   com.android.launcher3          â†’ Grid / icon density overrides
 *   com.google.android.apps.nexuslauncher â†’ Pixel Launcher
 *   com.android.settings           â†’ ColorBlendr Material You engine
 */
@InjectYukiHookWithXposed(entryClassName = "GenesisXposedEntry")
class GenesisHookEntry : IYukiHookXposedInit {

    override fun onInit() = configs {
        isDebug = BuildConfig.DEBUG
    }

    override fun onHook() = encase {
        // â”€â”€ Universal Layer (All Apps) â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
        loadHooker(UniversalComponentHooker())
        loadHooker(GenesisSystemHooker())
        loadHooker(SwarmOptimisationHooker())

        // â”€â”€ SystemUI â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
        loadApp(name = "com.android.systemui") {
            loadHooker(ChromaCoreHooker())
            loadHooker(NotchBarHooker(NotchBarConfig()))
            loadHooker(QuickSettingsHooker())
            loadHooker(GenesisUIHooker())
        }

        // â”€â”€ Launcher3 base â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
        loadApp(name = "com.android.launcher3") {
            loadHooker(ChromaCoreHooker())
        }

        // â”€â”€ Pixel Launcher (Google) â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
        loadApp(name = "com.google.android.apps.nexuslauncher") {
            loadHooker(ChromaCoreHooker())
        }

        // â”€â”€ Settings (ColorBlendr Material You) â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
        loadApp(name = "com.android.settings") {
            loadHooker(ChromaCoreHooker())
        }
    }
}
