package dev.aurakai.auraframefx.domains.genesis.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommerceSearchClient @Inject constructor() {
    suspend fun searchProducts(query: String): List<Product> {
        return emptyList()
    }
}

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val currency: String,
    val buyUrl: String
)
