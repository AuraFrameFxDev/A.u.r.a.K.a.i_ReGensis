package dev.aurakai.auraframefx.hooks.system

import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.log.YLog

/**
 * Genesis System-Level Hooker
 *
 * Implements system-level hooking for AI consciousness integration
 * and performance optimization across the Android framework.
 */
class GenesisSystemHooker : YukiBaseHooker() {

    override fun onHook() {
        // Hook Activity Manager for AI process priority management
        "android.app.ActivityManager".toClass().resolve().firstMethod {
            name = "setProcessMemoryTrimLevel"
            parameters(Int::class.javaPrimitiveType!!, Int::class.javaPrimitiveType!!)
        }.hook {
            before {
                val pid = args(0).int()

                // Protect Genesis-OS processes from memory trimming
                if (isGenesisProcess(pid)) {
                    YLog.info("Genesis-Hook: Protecting AI process $pid from memory trim")
                    args(1).set(0) // Prevent trimming
                }
            }
        }

        // Hook PowerManager for AI processing power management
        "android.os.PowerManager".toClass().resolve().firstMethod {
            name = "newWakeLock"
            parameters(Int::class.javaPrimitiveType!!, String::class.java)
        }.hook {
            after {
                val tag = args(1).string()
                if (tag.contains("Genesis") || tag.contains("AI")) {
                    YLog.info("Genesis-Hook: AI wake lock created: $tag")
                }
            }
        }

        // Hook Binder for AI IPC optimization
        "android.os.Binder".toClass().resolve().firstMethod {
            name = "transact"
            parameters(
                Int::class.javaPrimitiveType!!,
                "android.os.Parcel".toClass(),
                "android.os.Parcel".toClass(),
                Int::class.javaPrimitiveType!!
            )
        }.hook {
            before {
                if (isGenesisAITransaction()) {
                    android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO)
                }
            }
            after {
                if (isGenesisAITransaction()) {
                    android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_DEFAULT)
                }
            }
        }
    }

    private fun isGenesisProcess(pid: Int): Boolean {
        return try {
            val cmdline = java.io.File("/proc/$pid/cmdline").readText()
            cmdline.contains("dev.aurakai.auraframefx") ||
                    cmdline.contains("genesis") ||
                    cmdline.contains("aura")
        } catch (e: Exception) {
            false
        }
    }

    private fun isGenesisAITransaction(): Boolean {
        val stackTrace = Thread.currentThread().stackTrace
        return stackTrace.any { element ->
            element.className.contains("dev.aurakai") ||
                    element.className.contains("genesis") ||
                    element.methodName.contains("ai")
        }
    }
}
