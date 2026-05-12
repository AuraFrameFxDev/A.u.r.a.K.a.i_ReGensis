package dev.aurakai.auraframefx.core.chromacore.engine.hooks

import android.view.View
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.log.YLog

/**
 * ðŸŽ¨ CHROMA CORE HOOKER â€” Fully Migrated to KavaRef Substrate
 *
 * Uses KavaRef for all reflection operations (class, method).
 * Loaded by GenesisHookEntry into SystemUI, Launcher3, Pixel Launcher, Settings.
 */
class ChromaCoreHooker : YukiBaseHooker() {

    override fun onHook() {
        prefs("chromacore_xposed_prefs")

        hookStatusBar()
        hookLauncherGrid()
        hookDynamicColors()
        hookNotchBar()
    }

    // â”€â”€ Status Bar â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    private fun hookStatusBar() {
        "com.android.systemui.statusbar.phone.PhoneStatusBarView".toClassOrNull()?.resolve()
            ?.firstMethod {
                name = "onFinishInflate"
            }?.hook {
                after {
                    val bgTransparent = prefs.getBoolean("statusbar_bg_transparent", false)
                    val showIcons = prefs.getBoolean("statusbar_show_icons", true)
                    YLog.info("ChromaCoreÂ·StatusBar transparent=$bgTransparent icons=$showIcons")

                    if (bgTransparent) {
                        runCatching {
                            (instance as? View)?.setBackgroundColor(android.graphics.Color.TRANSPARENT)
                        }
                    }
                }
            }
    }

    // â”€â”€ Launcher Grid â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    private fun hookLauncherGrid() {
        val cls = "com.android.launcher3.InvariantDeviceProfile".toClassOrNull()
            ?: "com.google.android.apps.nexuslauncher.NexusLauncherActivity".toClassOrNull()
            ?: return

        cls.resolve().firstMethod {
            name = "init"
        }.hook {
            after {
                val grid = prefs.getString("launcher_grid_config", "5x5")
                YLog.info("ChromaCoreÂ·Launcher grid â†’ $grid")

                runCatching {
                    val parts = grid.split("x")
                    if (parts.size == 2) {
                        val cols = parts[0].toIntOrNull() ?: return@runCatching
                        val rows = parts[1].toIntOrNull() ?: return@runCatching

                        // Standard reflection for field access to ensure build stability across Yuki versions
                        instance.javaClass.getDeclaredField("numColumns")
                            .apply { isAccessible = true }.set(instance, cols)
                        instance.javaClass.getDeclaredField("numRows").apply { isAccessible = true }
                            .set(instance, rows)
                    }
                }
            }
        }
    }

    // â”€â”€ Dynamic Colors (ColorBlendr Material You) â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    private fun hookDynamicColors() {
        "com.android.systemui.monet.ColorScheme".toClassOrNull()?.resolve()?.firstMethod {
            name = "getColors"
        }?.hook {
            after {
                val customSeed = prefs.getInt("colorblendr_seed_color", -1)
                if (customSeed != -1) {
                    YLog.info("ChromaCoreÂ·ColorBlendr seed=#${Integer.toHexString(customSeed)}")
                }
            }
        }
    }

    // â”€â”€ Notch / Display Cutout â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    private fun hookNotchBar() {
        "com.android.systemui.statusbar.phone.StatusBarWindowView".toClassOrNull()?.resolve()
            ?.firstMethod {
                name = "onApplyWindowInsets"
            }?.hook {
                before {
                    val hide = prefs.getBoolean("hide_display_cutout", false)
                    if (hide) YLog.info("ChromaCoreÂ·Hiding display cutout")
                }
            }
    }
}
