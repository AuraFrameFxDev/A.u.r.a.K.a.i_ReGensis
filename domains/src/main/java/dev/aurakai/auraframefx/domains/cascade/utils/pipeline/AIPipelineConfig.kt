package dev.aurakai.auraframefx.domains.cascade.utils.pipeline

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIPipelineConfig @Inject constructor() {
    val maxRetries = 3
    val timeoutMs = 30000L

    // Task weights for Kai's TaskScheduler
    val priorityWeight = 0.4f
    val urgencyWeight = 0.3f
    val importanceWeight = 0.3f

    // Execution limits
    val maxActiveTasks = 5
}
