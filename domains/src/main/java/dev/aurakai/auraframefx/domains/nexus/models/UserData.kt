package dev.aurakai.auraframefx.domains.nexus.models

import kotlinx.serialization.Serializable

/**
 * Represents user profile and authentication data.
 */
@Serializable
data class UserData(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val apiKey: String? = null,
    val avatarUrl: String? = null,
    val preferences: String? = null,
    val createdAt: Long? = null,
    val role: String? = null,
    val username: String? = null,
    val updatedAt: Long? = null
)
