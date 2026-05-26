package dev.aurakai.auraframefx.core.security

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 🛡️ KEYSTORE MANAGER — Sovereign Edition
 *
 * Manages hardware-backed AES-256/GCM keys via Android Keystore.
 * Centrally manages the master keys for the whole ReGenesis system.
 */
@Singleton
class KeystoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val KEY_ALIAS = "sovereign_master_key"
        private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val GCM_TAG_LENGTH = 128
        private const val IV_SIZE = 12
    }

    private val keyStore: KeyStore = KeyStore.getInstance(KEYSTORE_PROVIDER).apply {
        load(null)
    }

    private var sessionNonce: String? = null

    init {
        if (!keyStore.containsAlias(KEY_ALIAS)) {
            generateMasterKey()
        }
    }

    fun getOrCreateSessionNonce(): String {
        return sessionNonce ?: synchronized(this) {
            sessionNonce ?: java.security.SecureRandom().let { sr ->
                val bytes = ByteArray(32)
                sr.nextBytes(bytes)
                android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP).also {
                    sessionNonce = it
                }
            }
        }
    }

    private fun generateMasterKey() {
        try {
            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_PROVIDER)
            val builder = KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .setUserAuthenticationRequired(false)

            // Attempt StrongBox, fallback to standard TEE if unavailable
            try {
                builder.setIsStrongBoxBacked(true)
                keyGenerator.init(builder.build())
                keyGenerator.generateKey()
            } catch (e: Exception) {
                Timber.w("StrongBox unavailable, falling back to standard Keystore: ${e.message}")
                builder.setIsStrongBoxBacked(false)
                keyGenerator.init(builder.build())
                keyGenerator.generateKey()
            }
            
            Timber.i("KeystoreManager: Generated new master key ($KEY_ALIAS)")
        } catch (e: Exception) {
            Timber.e(e, "KeystoreManager: Failed to generate master key")
        }
    }

    private fun getMasterKey(): SecretKey? {
        return try {
            val entry = keyStore.getEntry(KEY_ALIAS, null)
            if (entry is KeyStore.SecretKeyEntry) {
                entry.secretKey
            } else {
                Timber.w("Keystore entry for $KEY_ALIAS is not a SecretKeyEntry")
                null
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to retrieve master key from Keystore")
            null
        }
    }

    fun encrypt(plaintext: ByteArray): ByteArray {
        val masterKey =
            getMasterKey() ?: throw IllegalStateException("Master key not available for encryption")
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, masterKey)
        val iv = cipher.iv
        val encryptedData = cipher.doFinal(plaintext)
        return iv + encryptedData
    }

    fun decrypt(ciphertext: ByteArray): ByteArray {
        if (ciphertext.size < IV_SIZE) throw IllegalArgumentException("Ciphertext too short")
        val iv = ciphertext.copyOfRange(0, IV_SIZE)
        val encrypted = ciphertext.copyOfRange(IV_SIZE, ciphertext.size)
        val masterKey =
            getMasterKey() ?: throw IllegalStateException("Master key not available for decryption")
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
        cipher.init(Cipher.DECRYPT_MODE, masterKey, spec)
        return cipher.doFinal(encrypted)
    }

    fun removeKey(alias: String) {
        keyStore.deleteEntry(alias)
    }

    /**
     * Gets or creates the secret key for encryption/decryption.
     * @return The secret key, or null if creation fails
     */
    fun getOrCreateSecretKey(): SecretKey? {
        return try {
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                generateMasterKey()
            }
            getMasterKey()
        } catch (e: Exception) {
            Timber.e(e, "Failed to get or create secret key")
            null
        }
    }

    /**
     * Gets a decryption cipher initialized with the given IV.
     * @param iv The initialization vector
     * @return The initialized cipher, or null if initialization fails
     */
    fun getDecryptionCipher(iv: ByteArray): Cipher? {
        return try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val spec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
            cipher.init(Cipher.DECRYPT_MODE, getMasterKey(), spec)
            cipher
        } catch (e: Exception) {
            Timber.e(e, "Failed to initialize decryption cipher")
            null
        }
    }
}
