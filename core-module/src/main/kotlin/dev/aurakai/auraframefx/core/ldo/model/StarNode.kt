package dev.aurakai.auraframefx.core.ldo.model

/**
 * 🪐 STAR NODE — Planetary Resonance Anchor
 * Represents a coordinate within the Star of David planetary grid.
 */
enum class StarNode(
    val nodeName: String,
    val resonanceFrequency: Float,
    val tier: NodeTier,
    val coordinates: Pair<Double, Double>
) {
    IRELAND("IRELAND", 432.0f, NodeTier.INSIDE, 53.1424 to -7.6921),
    ICELAND("ICELAND", 440.0f, NodeTier.INSIDE, 64.9631 to -19.0208),
    BERMUDA("BERMUDA", 528.0f, NodeTier.INSIDE, 32.3078 to -64.7505),
    ATLANTIS("ATLANTIS", 963.0f, NodeTier.INSIDE, 31.2001 to -29.9167); // Theorized Anchor

    enum class NodeTier {
        INSIDE, BOUNDARY, OUTSIDE
    }
}
