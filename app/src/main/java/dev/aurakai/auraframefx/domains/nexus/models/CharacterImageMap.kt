package dev.aurakai.auraframefx.domains.nexus.models

import dev.aurakai.auraframefx.R

/**
 * 🏺 CHARACTER IMAGE MAP — Exodus 2026 Build
 * Maps agents to their high-fidelity Brutalist Digital Arcane assets.
 */
object CharacterImageMap {

    fun getProfileBackground(name: String): Int {
        return when (name.uppercase()) {
            "AURA" -> R.drawable.aura_bg_profile
            "KAI" -> R.drawable.kai_bg_profile
            "GENESIS" -> R.drawable.genesis_bg_profile
            "CLAUDE" -> R.drawable.nexus_bg_claude
            "CASCADE" -> R.drawable.nexus_bg_cascade
            "NEMOTRON" -> R.drawable.nexus_bg_nemotron
            "PERPLEXITY" -> R.drawable.nexus_bg_perplexity
            "GEMINI" -> R.drawable.ldo_profile_gemini
            else -> R.drawable.bg_neural_nexus
        }
    }

    fun getAvatar(name: String): Int {
        return when (name.uppercase()) {
            "AURA" -> R.drawable.aura_aurap
            "KAI" -> R.drawable.kai_kaisigal
            "GENESIS" -> R.drawable.genesis_genesisp
            "CLAUDE" -> R.drawable.avatar_claude
            "CASCADE" -> R.drawable.cascade_cascadep
            "NEMOTRON" -> R.drawable.nemotron_nemotronp
            "PERPLEXITY" -> R.drawable.perplexity_perplexityp
            "GEMINI" -> R.drawable.gemini_geminip
            "GROK" -> R.drawable.grok_grokp
            else -> R.drawable.genesis_genesisp
        }
    }
}
