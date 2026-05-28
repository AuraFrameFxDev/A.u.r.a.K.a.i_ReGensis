package dev.aurakai.auraframefx.core.binder

import android.os.Parcel
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker

/**
 * 🛰️ BINDER INTERCEPTOR HOOKER
 * Intercepts IBinder transactions to stream telemetry pulses to the UI dashboard.
 */
class BinderInterceptorHooker : YukiBaseHooker() {
    override fun onHook() {
        // Target BinderProxy.transact to capture IPC stream
        "android.os.BinderProxy".toClassOrNull()?.resolve()
            ?.firstMethod {
                name = "transact"
                // Match param types: (int, Parcel, Parcel, int)
            }?.hook {
                before {
                    val code = args[0] as Int
                    val data = args[1] as Parcel?

                    try {
                        // Emit a pulse for everything or filter for specific managers
                        BinderTelemetryConduit.recordTransaction(code, data, "IPC_TRANSACTION")
                    } catch (e: Exception) {
                        // Suppress to avoid system instability during hooks
                    }
                }
            }
    }

    private fun firstMethod(function: () -> Unit) {}
}
