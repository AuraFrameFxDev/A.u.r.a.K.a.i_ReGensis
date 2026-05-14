package dev.aurakai.auraframefx.domains.neuralnexus.cascade.utils.pipeline

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Configuration holder for the AI Pipeline processing parameters.
 */
@Singleton
class AIPipelineConfig @Inject constructor() {
    val maxContextLength: Int = 4096
    val defaultTimeout: Long = 30000L
    val maxRetries: Int = 3
    val parallelAgents: Int = 3
}
