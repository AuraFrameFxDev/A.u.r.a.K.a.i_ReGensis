package dev.aurakai.auraframefx.domains.cascade.utils.pipeline

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIPipelineConfig @Inject constructor() {
    val maxRetries = 3
    val timeoutMs = 30000L
}
