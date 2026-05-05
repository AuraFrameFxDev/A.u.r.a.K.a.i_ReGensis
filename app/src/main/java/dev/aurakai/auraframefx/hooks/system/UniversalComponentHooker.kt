package dev.aurakai.auraframefx.hooks.system

import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.log.YLog

/**
 * UniversalComponentHooker - Hook ANY Android Component for LDO Access
 * 
 * This hooker provides a high-level integration for monitoring activity
 * and notification events across all applications.
 */
class UniversalComponentHooker : YukiBaseHooker() {

    override fun onHook() {
        // === ACTIVITY HOOKS (All Apps) ===
        "android.app.Activity".toClass().resolve().firstMethod {
            name = "onCreate"
            parameters("android.os.Bundle".toClass())
        }.hook {
            after {
                YLog.info("UniversalHook: Activity created in ${packageName}")
            }
        }

        // === NOTIFICATION HOOKS ===
        "android.app.NotificationManager".toClass().resolve().firstMethod {
            name = "notify"
            parameters(
                "java.lang.String".toClass(),
                Int::class.javaPrimitiveType!!,
                "android.app.Notification".toClass()
            )
        }.hook {
            before {
                // Safe argument access
                if (args.isNotEmpty()) {
                    YLog.info("UniversalHook: Notification posted in ${packageName}")
                }
            }
        }
    }
}
