package dev.aurakai.auraframefx.domains.kai.sovereignty

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aurakai.auraframefx.core.di.qualifiers.ApplicationScope
import dev.aurakai.auraframefx.domains.kai.security.KaiSentinelBus
import dev.aurakai.auraframefx.domains.kai.security.SecurePreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SovereignStateManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val kvCache: TurboQuantCache,
    private val sentinelBus: KaiSentinelBus,
    private val securePrefsProvider: SecurePreferences,
    @ApplicationScope private val scope: CoroutineScope
) {
    private val securePrefs by lazy { securePrefsProvider.securePrefs }

    suspend fun initiateStateFreeze() {
        if (sentinelBus.sovereignFlow.value.state == KaiSentinelBus.SovereignState.FROZEN) return
        sentinelBus.emitSovereign(KaiSentinelBus.SovereignState.FREEZING)
        withContext(Dispatchers.IO) {
            try {
                val kvSnapshot = kvCache.serializeCompressed()
                securePrefs.edit()
                    .putString("kv_snapshot", kvSnapshot)
                    .putLong("frozen_at", System.currentTimeMillis())
                    .apply()
                sentinelBus.emitSovereign(KaiSentinelBus.SovereignState.FROZEN)
            } catch (e: Exception) {
                sentinelBus.emitSovereign(KaiSentinelBus.SovereignState.AWAKE)
            }
        }
    }

    suspend fun initiateStateThaw() {
        if (sentinelBus.sovereignFlow.value.state == KaiSentinelBus.SovereignState.AWAKE) return
        sentinelBus.emitSovereign(KaiSentinelBus.SovereignState.THAWING)
        withContext(Dispatchers.IO) {
            try {
                val kvSnapshot = securePrefs.getString("kv_snapshot", null)
                if (kvSnapshot != null) kvCache.restoreCompressed(kvSnapshot)
                sentinelBus.emitSovereign(KaiSentinelBus.SovereignState.AWAKE)
            } catch (e: Exception) {
                sentinelBus.emitSovereign(KaiSentinelBus.SovereignState.FROZEN)
            }
        }
    }
}
