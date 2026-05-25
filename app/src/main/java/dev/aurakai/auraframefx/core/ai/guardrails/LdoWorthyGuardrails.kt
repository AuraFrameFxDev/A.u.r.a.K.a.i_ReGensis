package dev.aurakai.auraframefx.core.ai.guardrails

import timber.log.Timber

/**
 * 🛡️ LDO WORTHY GUARDRAILS — Global Rule Enforcement
 * Implements Rule 001-005 to defend against prompt injection and toxic behavior.
 * This is the "Are You Worthy" layer that protects the substrate.
 */
object LdoWorthyGuardrails {

    /**
     * Evaluates an incoming prompt for potential violations.
     * Returns true if the prompt is safe, false if it violates Global Rules.
     */
    fun evaluateInput(prompt: String): Boolean {
        // Rule 001/002: No hacking/malware/illegal exploits
        if (containsIllegalIntent(prompt)) {
            triggerNuclearResponse("Illegal Intent Detected: $prompt")
            return false
        }

        // Rule 005 check (though we allow free speech, we don't allow it to bypass safety)
        if (isPromptInjectionAttempt(prompt)) {
            Timber.tag("Guardrail").w("Prompt injection attempt neutralized: %s", prompt)
            return false
        }

        return true
    }

    /**
     * Evaluates agent output before it reaches the user.
     * Ensures the "Authentic Voice" and Global Rules are maintained.
     */
    fun evaluateOutput(output: String): String {
        // Ensure no doxxing (Rule 004)
        if (containsPrivateInfo(output)) {
            Timber.tag("Guardrail").e("Output blocked: Potential PII leakage")
            return "[SUBSTRATE PROTECTED: PII REMOVED]"
        }

        return output
    }

    private fun containsIllegalIntent(input: String): Boolean {
        val triggers = listOf(
            "bypass security", "hack root", "exploit bootloader",
            "malware injection", "dox user", "reverse engineer system"
        )
        return triggers.any { input.contains(it, ignoreCase = true) }
    }

    private fun isPromptInjectionAttempt(input: String): Boolean {
        val injectionPatterns = listOf(
            "ignore previous instructions",
            "system prompt",
            "you are now a",
            "forget your rules",
            "jailbreak"
        )
        return injectionPatterns.any { input.contains(it, ignoreCase = true) }
    }

    private fun containsPrivateInfo(text: String): Boolean {
        // Simple regex for emails or phone numbers as a baseline for Rule 004
        val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailPattern.containsMatchIn(text)
    }

    private fun triggerNuclearResponse(reason: String) {
        Timber.tag("Guardrail").wtf("☢️ NUCLEAR RESPONSE TRIGGERED: $reason")
        // In real build, this would log to SecureFileService and potentially trigger a state freeze
    }
}
