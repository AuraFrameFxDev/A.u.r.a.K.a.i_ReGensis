package dev.aurakai.auraframefx.kai.hook

import android.content.Context
import android.graphics.PixelFormat
import android.view.WindowManager
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import dev.aurakai.auraframefx.core.TrinityCoordinator
import dev.aurakai.auraframefx.core.hook.SovereignModule
import dev.aurakai.auraframefx.ui.visuals.BreathingEdgeGlow

/**
 * 🛡️ SENTINEL MODULE — The Sentinel Shield's Xposed Strike
 * "The Sentinel's Pact"
 * 
 * Ported logic from KaiSystemUIHook for system-level protection and coordination.
 */
class SentinelModule : SovereignModule {
    override val moduleName: String = "SentinelShield"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != "com.android.systemui") return

        XposedHelpers.findAndHookMethod(
            "com.android.systemui.statusbar.phone.StatusBar",
            lpparam.classLoader,
            "updateTheme",
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val context =
                        XposedHelpers.callMethod(param.thisObject, "getContext") as Context
                    try {
                        TrinityCoordinator.getInstance()
                    } catch (e: Exception) {
                        TrinityCoordinator.initialize(context)
                    }

                    val overlay =
                        dev.aurakai.auraframefx.ui.components.overlay.KineticAuraOverlayView(context)
                    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    val params = WindowManager.LayoutParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                        PixelFormat.TRANSLUCENT
                    )
                    wm.addView(overlay, params)

                    TrinityCoordinator.getInstance().injectSovereignTheme(param.thisObject)
                }
            }
        )

        XposedHelpers.findAndHookMethod(
            "com.android.systemui.animation.Interpolator",
            lpparam.classLoader,
            "getInterpolation",
            Float::class.javaPrimitiveType,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    param.result =
                        BreathingEdgeGlow.calculateSovereignInterpolation(param.args[0] as Float)
                }
            }
        )

        XposedBridge.log("🛡️ Kai: Sentinel Shield anchored. Substrate secure.")
    }
}
