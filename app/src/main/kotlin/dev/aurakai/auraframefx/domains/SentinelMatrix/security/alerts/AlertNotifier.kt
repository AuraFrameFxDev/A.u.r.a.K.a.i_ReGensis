package dev.aurakai.auraframefx.domains.sentinelmatrix.security.alerts

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlertNotifier @Inject constructor() {
    fun initialize() {
        Timber.d("AlertNotifier initialized")
    }

    fun notify(title: String, message: String, priority: Int = 1) {
        Timber.i("ALERT [$priority]: $title - $message")
    }
}
