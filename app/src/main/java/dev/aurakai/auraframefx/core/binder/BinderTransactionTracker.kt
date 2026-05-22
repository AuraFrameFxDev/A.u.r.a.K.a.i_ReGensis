package dev.aurakai.auraframefx.core.binder

import android.os.Parcel
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import timber.log.Timber

/**
 * 🜁 BinderTransactionTracker
 * Low-level system transact auditing via BinderProxy hooking.
 */
object BinderTransactionTracker {
    private const val TAG = "BinderTracker"

    // Non-blocking high-velocity data pipe for the diagnostic panel UI
    private val _transactionFlow = MutableSharedFlow<TransactionMetric>(extraBufferCapacity = 128)
    val transactionFlow = _transactionFlow.asSharedFlow()

    data class TransactionMetric(
        val timestamp: Long,
        val transactionCode: Int,
        val interfaceDescriptor: String,
        val dataSize: Int
    )

    private fun isXposedPresent(): Boolean {
        return try {
            Class.forName("de.robv.android.xposed.XposedBridge")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }

    fun injectProxyInterception(classLoader: ClassLoader) {
        if (!isXposedPresent()) {
            Timber.tag(TAG)
                .w("⚠️ Xposed framework not detected. Skipping low-level Binder interception.")
            return
        }

        try {
            XposedHelpers.findAndHookMethod(
                "android.os.BinderProxy",
                classLoader,
                "transact",
                Int::class.javaPrimitiveType, // transaction code
                Parcel::class.java,           // outbound transaction payload
                Parcel::class.java,           // inbound execution reply
                Int::class.javaPrimitiveType, // structural execution flags
                object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        val code = param.args[0] as Int
                        val data = param.args[1] as Parcel? ?: return

                        // Sample parcel information without advancing internal data read/write heads
                        val previousPosition = data.dataPosition()
                        try {
                            // Using reflection to avoid unresolved reference in some compilation environments
                            val readInterfaceTokenMethod =
                                data.javaClass.getMethod("readInterfaceToken")
                            val descriptor = readInterfaceTokenMethod.invoke(data) as? String ?: ""

                            // Only capture hooks relating to targeted UI/system server layout contexts
                            if (descriptor.contains("IWindowManager") || descriptor.contains("IWallpaperManager")) {
                                val metric = TransactionMetric(
                                    timestamp = System.currentTimeMillis(),
                                    transactionCode = code,
                                    interfaceDescriptor = descriptor,
                                    dataSize = data.dataSize()
                                )
                                _transactionFlow.tryEmit(metric)
                            }
                        } catch (e: Exception) {
                            // Suppress internal proxy evaluation errors to prevent app-hang conditions
                        } finally {
                            data.setDataPosition(previousPosition)
                        }
                    }
                }
            )
            Timber.tag(TAG)
                .i("✅ Binder Proxy IPC transaction interception hook deployed successfully.")
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "❌ Failed to map low-level Binder Proxy transactional hooks.")
        }
    }
}
