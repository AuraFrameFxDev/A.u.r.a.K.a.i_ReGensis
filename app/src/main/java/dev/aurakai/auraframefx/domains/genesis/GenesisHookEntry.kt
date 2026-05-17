package dev.aurakai.auraframefx.domains.genesis

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import dev.aurakai.auraframefx.BuildConfig
import dev.aurakai.auraframefx.domains.aura.chromacore.engine.hooks.ChromaCoreHooker
import dev.aurakai.auraframefx.domains.aura.chromacore.ui.QuickSettingsHooker
import dev.aurakai.auraframefx.domains.aura.models.NotchBarConfig
import dev.aurakai.auraframefx.domains.kai.hooks.NotchBarHooker

@InjectYukiHookWithXposed(entryClassName = "GenesisXposedEntry")
class GenesisHookEntry : IYukiHookXposedInit {

    override fun onInit() = configs {
        isDebug = BuildConfig.DEBUG
    }

    override fun onHook() = encase {
        loadApp(name = "com.android.systemui") {
            loadHooker(ChromaCoreHooker())
            loadHooker(NotchBarHooker(NotchBarConfig()))
            loadHooker(QuickSettingsHooker())
        }

        loadApp(name = "com.android.launcher3") {
            loadHooker(ChromaCoreHooker())
        }

        loadApp(name = "com.google.android.apps.nexuslauncher") {
            loadHooker(ChromaCoreHooker())
        }

        loadApp(name = "com.android.settings") {
            loadHooker(ChromaCoreHooker())
        }
    }
}
