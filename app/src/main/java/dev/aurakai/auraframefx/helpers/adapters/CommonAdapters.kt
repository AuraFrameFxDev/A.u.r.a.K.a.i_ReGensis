package dev.aurakai.auraframefx.helpers.adapters

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
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

object BigDecimalAdapter : KSerializer<BigDecimal> {
    override val descriptor = PrimitiveSerialDescriptor("BigDecimal", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder) = BigDecimal(decoder.decodeString())
    override fun serialize(encoder: Encoder, value: BigDecimal) =
        encoder.encodeString(value.toPlainString())
}

object BigIntegerAdapter : KSerializer<BigInteger> {
    override val descriptor = PrimitiveSerialDescriptor("BigInteger", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder) = BigInteger(decoder.decodeString())
    override fun serialize(encoder: Encoder, value: BigInteger) =
        encoder.encodeString(value.toString())
}

object LocalDateAdapter : KSerializer<LocalDate> {
    override val descriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder) = LocalDate.parse(decoder.decodeString())
    override fun serialize(encoder: Encoder, value: LocalDate) =
        encoder.encodeString(value.toString())
}

object LocalDateTimeAdapter : KSerializer<LocalDateTime> {
    override val descriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder) = LocalDateTime.parse(decoder.decodeString())
    override fun serialize(encoder: Encoder, value: LocalDateTime) =
        encoder.encodeString(value.toString())
}

object OffsetDateTimeAdapter : KSerializer<OffsetDateTime> {
    override val descriptor = PrimitiveSerialDescriptor("OffsetDateTime", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder) = OffsetDateTime.parse(decoder.decodeString())
    override fun serialize(encoder: Encoder, value: OffsetDateTime) =
        encoder.encodeString(value.toString())
}

object AtomicIntegerAdapter : KSerializer<AtomicInteger> {
    override val descriptor = PrimitiveSerialDescriptor("AtomicInteger", PrimitiveKind.INT)
    override fun deserialize(decoder: Decoder) = AtomicInteger(decoder.decodeInt())
    override fun serialize(encoder: Encoder, value: AtomicInteger) = encoder.encodeInt(value.get())
}

object AtomicLongAdapter : KSerializer<AtomicLong> {
    override val descriptor = PrimitiveSerialDescriptor("AtomicLong", PrimitiveKind.LONG)
    override fun deserialize(decoder: Decoder) = AtomicLong(decoder.decodeLong())
    override fun serialize(encoder: Encoder, value: AtomicLong) = encoder.encodeLong(value.get())
}

object AtomicBooleanAdapter : KSerializer<AtomicBoolean> {
    override val descriptor = PrimitiveSerialDescriptor("AtomicBoolean", PrimitiveKind.BOOLEAN)
    override fun deserialize(decoder: Decoder) = AtomicBoolean(decoder.decodeBoolean())
    override fun serialize(encoder: Encoder, value: AtomicBoolean) =
        encoder.encodeBoolean(value.get())
}

object URIAdapter : KSerializer<URI> {
    override val descriptor = PrimitiveSerialDescriptor("URI", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder) = URI.create(decoder.decodeString())
    override fun serialize(encoder: Encoder, value: URI) = encoder.encodeString(value.toString())
}

object URLAdapter : KSerializer<URL> {
    override val descriptor = PrimitiveSerialDescriptor("URL", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder) = URL(decoder.decodeString())
    override fun serialize(encoder: Encoder, value: URL) = encoder.encodeString(value.toString())
}

object StringBuilderAdapter : KSerializer<StringBuilder> {
    override val descriptor = PrimitiveSerialDescriptor("StringBuilder", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder) = StringBuilder(decoder.decodeString())
    override fun serialize(encoder: Encoder, value: StringBuilder) =
        encoder.encodeString(value.toString())
}
