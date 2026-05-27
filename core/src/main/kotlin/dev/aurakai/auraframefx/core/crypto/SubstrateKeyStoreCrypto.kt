package dev.aurakai.auraframefx.core.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import timber.log.Timber
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * PRODUCTION-GRADE CRYPTOGRAPHIC BOUNDARY
 * Implements hardware-backed AES-GCM-NoPadding encryption via Android KeyStore.
 * Ensures strict local data sovereignty and user-space protection boundaries.
 */
object SubstrateKeyStoreCrypto {
    private const val TAG = "SubstrateCrypto"
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val KEY_ALIAS = "SubstrateTelemetryKeySecureBoundary"
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val GCM_TAG_LENGTH_BITS = 128

    init {
        initSecureKey()
    }

    /**
     * Instantiates a hardware-backed AES key if an existing alias is not verified.
     */
    private fun initSecureKey() {
        try {
            val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                val keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    ANDROID_KEYSTORE
                )

                val spec = KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setRandomizedEncryptionRequired(true) // Enforces unique dynamic IV per operation
                    .build()

                keyGenerator.init(spec)
                keyGenerator.generateKey()
                Timber.tag(TAG).i("Hardware-backed AES key initialization verified.")
            }
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Failed to initialize secure hardware KeyStore boundary.")
        }
    }

    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        return (keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry).secretKey
    }

    /**
     * Encrypts plaintext payload attributes, returning a combined Base64 string containing [IV]:[Ciphertext].
     */
    fun encryptPayload(plainText: String): String? {
        if (plainText.isEmpty()) return null
        return try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())

            val cipherTextBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
            val ivBytes = cipher.iv

            val base64Cipher = Base64.encodeToString(cipherTextBytes, Base64.NO_WRAP)
            val base64Iv = Base64.encodeToString(ivBytes, Base64.NO_WRAP)

            // Pack structural tokens cleanly via standard delimiters
            "$base64Iv:$base64Cipher"
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Encryption failure inside processing boundary")
            null
        }
    }

    /**
     * Decrypts a packed Base64 payload string back into raw text format.
     */
    fun decryptPayload(packedPayload: String): String? {
        if (packedPayload.isEmpty() || !packedPayload.contains(":")) return null
        return try {
            val parts = packedPayload.split(":")
            val ivBytes = Base64.decode(parts[0], Base64.NO_WRAP)
            val cipherTextBytes = Base64.decode(parts[1], Base64.NO_WRAP)

            val cipher = Cipher.getInstance(TRANSFORMATION)
            val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH_BITS, ivBytes)
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), gcmSpec)

            val plainTextBytes = cipher.doFinal(cipherTextBytes)
            String(plainTextBytes, Charsets.UTF_8)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Decryption failure inside target processing boundary")
            null
        }
    }
}
