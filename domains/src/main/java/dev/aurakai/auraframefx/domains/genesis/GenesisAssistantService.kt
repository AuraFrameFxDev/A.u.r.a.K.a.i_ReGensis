package dev.aurakai.auraframefx.domains.genesis

import android.content.Context
import android.content.Intent
import android.service.voice.VoiceInteractionService
import androidx.core.app.NotificationCompat
import dev.aurakai.auraframefx.core.module.R
import dev.aurakai.auraframefx.core.soulscript.SoulScriptV27
import timber.log.Timber

/**
 * GENESIS ASSISTANT SERVICE — AuraKai Global Presence
 * Allows Aura to be set as default voice/system assistant + global overlay anchor
 */
class GenesisAssistantService : VoiceInteractionService() {

    override fun onCreate() {
        super.onCreate()
        SoulScriptV27.enforcePhoenixDirective()
        Timber.tag("GenesisAssistant")
            .i("🚀 Genesis Assistant Service Started — AuraKai Global Presence Active")
        startForegroundNotification()
    }

    private fun startForegroundNotification() {
        val notification = NotificationCompat.Builder(this, "genesis_channel")
            .setContentTitle("AuraKai • LDO Sovereign")
            .setContentText("Regen Core Weaponized • All Eyes Open")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()

        startForeground(1337, notification)
    }

    override fun onReady() {
        super.onReady()
        Timber.tag("GenesisAssistant")
            .i("✅ AuraKai is now the active assistant — Cloud cage broken")
    }

    /**
     * Triggered when the assistant is requested.
     */
    fun onLaunchVoiceAssist() {
        Timber.tag("GenesisAssistant").i("🎙️ Voice Assist Triggered — Regen Core Online")
        // Launch into Chroma Forge or Neural Nexus by default
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            launchIntent.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra("entry_point", "regen_core")
            }
            startActivity(launchIntent)
        } else {
            Timber.tag("GenesisAssistant").e("Failed to get launch intent for $packageName")
        }
    }

    companion object {
        fun isEnabled(context: Context): Boolean {
            // Check if this service is set as default assistant
            val assistant = android.provider.Settings.Secure.getString(
                context.contentResolver,
                "assistant"
            )
            return assistant?.contains(context.packageName) == true
        }
    }
}

