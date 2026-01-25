package dev.aurakai.auraframefx.genesis.oracledrive.security

data class SecurityCheckResult(
    val isValid: Boolean = false,
    val reason: String = "",
    val isSecure: Boolean = false,
    val threat: String = "",
    val hasAccess: Boolean = false
)
