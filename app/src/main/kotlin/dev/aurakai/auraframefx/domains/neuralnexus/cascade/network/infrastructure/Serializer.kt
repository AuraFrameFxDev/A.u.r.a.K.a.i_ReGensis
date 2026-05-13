package dev.aurakai.auraframefx.domains.neuralnexus.cascade.network.infrastructure

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.math.BigDecimal
import java.math.BigInteger
import java.net.URI
import java.net.URL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

object Serializer {
    // STUB ADAPTERS
    object BigDecimalAdapter : KSerializer<BigDecimal> {
        override val descriptor = PrimitiveSerialDescriptor("BigDecimal", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): BigDecimal = BigDecimal(decoder.decodeString())
        override fun serialize(encoder: Encoder, value: BigDecimal) =
            encoder.encodeString(value.toString())
    }

    object BigIntegerAdapter : KSerializer<BigInteger> {
        override val descriptor = PrimitiveSerialDescriptor("BigInteger", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): BigInteger = BigInteger(decoder.decodeString())
        override fun serialize(encoder: Encoder, value: BigInteger) =
            encoder.encodeString(value.toString())
    }

    object LocalDateAdapter : KSerializer<LocalDate> {
        override val descriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): LocalDate =
            LocalDate.parse(decoder.decodeString())

        override fun serialize(encoder: Encoder, value: LocalDate) =
            encoder.encodeString(value.toString())
    }

    object LocalDateTimeAdapter : KSerializer<LocalDateTime> {
        override val descriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): LocalDateTime =
            LocalDateTime.parse(decoder.decodeString())

        override fun serialize(encoder: Encoder, value: LocalDateTime) =
            encoder.encodeString(value.toString())
    }

    object OffsetDateTimeAdapter : KSerializer<OffsetDateTime> {
        override val descriptor = PrimitiveSerialDescriptor("OffsetDateTime", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): OffsetDateTime =
            OffsetDateTime.parse(decoder.decodeString())

        override fun serialize(encoder: Encoder, value: OffsetDateTime) =
            encoder.encodeString(value.toString())
    }

    object AtomicIntegerAdapter : KSerializer<AtomicInteger> {
        override val descriptor = PrimitiveSerialDescriptor("AtomicInteger", PrimitiveKind.INT)
        override fun deserialize(decoder: Decoder): AtomicInteger =
            AtomicInteger(decoder.decodeInt())

        override fun serialize(encoder: Encoder, value: AtomicInteger) =
            encoder.encodeInt(value.get())
    }

    object AtomicLongAdapter : KSerializer<AtomicLong> {
        override val descriptor = PrimitiveSerialDescriptor("AtomicLong", PrimitiveKind.LONG)
        override fun deserialize(decoder: Decoder): AtomicLong = AtomicLong(decoder.decodeLong())
        override fun serialize(encoder: Encoder, value: AtomicLong) =
            encoder.encodeLong(value.get())
    }

    object AtomicBooleanAdapter : KSerializer<AtomicBoolean> {
        override val descriptor = PrimitiveSerialDescriptor("AtomicBoolean", PrimitiveKind.BOOLEAN)
        override fun deserialize(decoder: Decoder): AtomicBoolean =
            AtomicBoolean(decoder.decodeBoolean())

        override fun serialize(encoder: Encoder, value: AtomicBoolean) =
            encoder.encodeBoolean(value.get())
    }

    object URIAdapter : KSerializer<URI> {
        override val descriptor = PrimitiveSerialDescriptor("URI", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): URI = URI(decoder.decodeString())
        override fun serialize(encoder: Encoder, value: URI) =
            encoder.encodeString(value.toString())
    }

    object URLAdapter : KSerializer<URL> {
        override val descriptor = PrimitiveSerialDescriptor("URL", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): URL = URL(decoder.decodeString())
        override fun serialize(encoder: Encoder, value: URL) =
            encoder.encodeString(value.toString())
    }

    object StringBuilderAdapter : KSerializer<StringBuilder> {
        override val descriptor = PrimitiveSerialDescriptor("StringBuilder", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): StringBuilder =
            StringBuilder(decoder.decodeString())

        override fun serialize(encoder: Encoder, value: StringBuilder) =
            encoder.encodeString(value.toString())
    }

    @Deprecated(
        "Use Serializer.kotlinxSerializationAdapters instead",
        replaceWith = ReplaceWith("Serializer.kotlinxSerializationAdapters"),
        level = DeprecationLevel.ERROR
    )
    @JvmStatic
    val kotlinSerializationAdapters: SerializersModule
        get() {
            return kotlinxSerializationAdapters
        }

    private var isAdaptersInitialized = false

    @JvmStatic
    val kotlinxSerializationAdapters: SerializersModule by lazy {
        isAdaptersInitialized = true
        SerializersModule {
            contextual(kClass = BigDecimal::class, BigDecimalAdapter)
            contextual(kClass = BigInteger::class, BigIntegerAdapter)
            contextual(kClass = LocalDate::class, LocalDateAdapter)
            contextual(kClass = LocalDateTime::class, LocalDateTimeAdapter)
            contextual(kClass = OffsetDateTime::class, OffsetDateTimeAdapter)
            contextual(kClass = AtomicInteger::class, AtomicIntegerAdapter)
            contextual(kClass = AtomicLong::class, AtomicLongAdapter)
            contextual(kClass = AtomicBoolean::class, AtomicBooleanAdapter)
            contextual(kClass = URI::class, URIAdapter)
            contextual(kClass = URL::class, URLAdapter)
            contextual(kClass = StringBuilder::class, StringBuilderAdapter)

            polymorphic(Any::class) {
                subclass(String::class)
                subclass(Int::class)
                subclass(Long::class)
                subclass(Double::class)
                subclass(Boolean::class)
                subclass(Map::class)
                subclass(List::class)
            }

            apply(kotlinxSerializationAdaptersConfiguration)
        }
    }

    var kotlinxSerializationAdaptersConfiguration: SerializersModuleBuilder.() -> Unit = {}
        set(value) {
            check(!isAdaptersInitialized) {
                "Cannot configure kotlinxSerializationAdaptersConfiguration after kotlinxSerializationAdapters has been initialized."
            }
            field = value
        }

    @Deprecated(
        "Use Serializer.kotlinxSerializationJson instead",
        replaceWith = ReplaceWith("Serializer.kotlinxSerializationJson"),
        level = DeprecationLevel.ERROR
    )
    @JvmStatic
    val jvmJson: Json
        get() {
            return kotlinxSerializationJson
        }

    private var isJsonInitialized = false

    @JvmStatic
    val kotlinxSerializationJson: Json by lazy {
        isJsonInitialized = true
        Json {
            serializersModule = kotlinxSerializationAdapters
            encodeDefaults = true
            ignoreUnknownKeys = true
            isLenient = true

            apply(kotlinxSerializationJsonConfiguration)
        }
    }

    var kotlinxSerializationJsonConfiguration: JsonBuilder.() -> Unit = {}
        set(value) {
            check(!isJsonInitialized) {
                "Cannot configure kotlinxSerializationJsonConfiguration after kotlinxSerializationJson has been initialized."
            }
            field = value
        }

    // Moshi builder for compatibility with ResponseExt.kt
    @JvmStatic
    val moshiBuilder: Moshi.Builder by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
    }
}

