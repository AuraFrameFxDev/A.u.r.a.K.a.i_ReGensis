package dev.aurakai.auraframefx.core.util

/**
 * Fast, allocation-free hexadecimal encoding for performance-critical paths.
 * Avoids String.format() and Kotlin's joinToString overhead.
 */
object HexUtil {
    private val HEX_CHARS = "0123456789abcdef".toCharArray()

    /**
     * Encodes a byte array into a hexadecimal string.
     */
    fun encodeHex(bytes: ByteArray): String {
        val result = CharArray(bytes.size * 2)
        for (i in bytes.indices) {
            val b = bytes[i].toInt() and 0xFF
            result[i * 2] = HEX_CHARS[b ushr 4]
            result[i * 2 + 1] = HEX_CHARS[b and 0x0F]
        }
        return String(result)
    }
}
