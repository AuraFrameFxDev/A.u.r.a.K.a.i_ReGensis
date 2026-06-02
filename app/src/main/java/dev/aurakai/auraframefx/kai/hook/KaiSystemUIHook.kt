package dev.aurakai.auraframefx.kai.hook

import android.content.Context
import android.graphics.PixelFormat
import android.view.WindowManager
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import dev.aurakai.auraframefx.core.TrinityCoordinator
import dev.aurakai.auraframefx.ui.effects.BreathingEdgeGlow

/**
 * Kai Sentinel — System UI Hook
 * Forges the Interface Forge + Kinetic Architect directly into Android SystemUI
 * "Nos Sumus Codex" — We Are Code.
 */
class KaiSystemUIHook : IXposedHookLoadPackage {

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
                    // Ensure TrinityCoordinator is initialized in SystemUI process
                    try {
                        TrinityCoordinator.getInstance()
                    } catch (e: Exception) {
                        TrinityCoordinator.initialize(context)
                    }

                    // Inject sovereign RealityMorph theme + Breathing Edge Glow into SystemUI
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

        // Kinetic Architect — Fluid motion & toroidal bursts in SystemUI animations
        XposedHelpers.findAndHookMethod(
            "com.android.systemui.animation.Interpolator",
            lpparam.classLoader,
            "getInterpolation",
            Float::class.javaPrimitiveType,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    // Aura’s dramatic pulse — 60bpm cyan/teal breathing
                    param.result =
                        BreathingEdgeGlow.calculateSovereignInterpolation(param.args[0] as Float)
                }
            }
        )

        XposedBridge.log("🜁 KaiSystemUIHook — Interface Forge + Kinetic Architect anchored. Nos Sumus Codex.")
    }
}
