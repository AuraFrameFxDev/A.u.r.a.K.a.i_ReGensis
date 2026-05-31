package dev.aurakai.auraframefx.core.messaging

import dev.aurakai.auraframefx.core.identity.AgentType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Unit tests for [AgentMessage] — the new data class added in this PR.
 * Covers construction, field defaults, equality, copy, and boundary values.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("AgentMessage Tests")
class AgentMessageTest {

    @Nested
    @DisplayName("Construction with required fields only")
    inner class RequiredFieldsTests {

        @Test
        @DisplayName("minimal construction sets required fields")
        fun `minimal construction sets required fields`() {
            val message = AgentMessage(from = "Aura", content = "Hello")
            assertEquals("Aura", message.from)
            assertEquals("Hello", message.content)
        }

        @Test
        @DisplayName("to defaults to null (broadcast)")
        fun `to defaults to null`() {
            val message = AgentMessage(from = "Aura", content = "Hello")
            assertNull(message.to)
        }

        @Test
        @DisplayName("priority defaults to 0")
        fun `priority defaults to 0`() {
            val message = AgentMessage(from = "Aura", content = "Hello")
            assertEquals(0, message.priority)
        }

        @Test
        @DisplayName("type defaults to 'info'")
        fun `type defaults to info`() {
            val message = AgentMessage(from = "Aura", content = "Hello")
            assertEquals("info", message.type)
        }

        @Test
        @DisplayName("metadata defaults to emptyMap")
        fun `metadata defaults to emptyMap`() {
            val message = AgentMessage(from = "Aura", content = "Hello")
            assertTrue(message.metadata.isEmpty())
        }

        @Test
        @DisplayName("sender defaults to null")
        fun `sender defaults to null`() {
            val message = AgentMessage(from = "Aura", content = "Hello")
            assertNull(message.sender)
        }

        @Test
        @DisplayName("confidence defaults to 0.8f")
        fun `confidence defaults to 0_8`() {
            val message = AgentMessage(from = "Aura", content = "Hello")
            assertEquals(0.8f, message.confidence)
        }

        @Test
        @DisplayName("id is auto-generated and non-blank")
        fun `id is auto-generated`() {
            val message = AgentMessage(from = "Aura", content = "Hello")
            assertTrue(message.id.isNotBlank())
        }

        @Test
        @DisplayName("timestamp is set to approximately current time")
        fun `timestamp is approximately current time`() {
            val before = System.currentTimeMillis()
            val message = AgentMessage(from = "Aura", content = "Hello")
            val after = System.currentTimeMillis()
            assertTrue(message.timestamp in before..after)
        }
    }

    @Nested
    @DisplayName("Construction with all fields specified")
    inner class FullConstructionTests {

        @Test
        @DisplayName("all fields stored correctly")
        fun `all fields stored correctly`() {
            val message = AgentMessage(
                id = "test-id-123",
                from = "Aura",
                to = "Kai",
                content = "Threat detected",
                priority = 5,
                timestamp = 1_000_000L,
                type = "alert",
                metadata = mapOf("key" to "value"),
                sender = AgentType.AURA,
                confidence = 0.95f
            )
            assertEquals("test-id-123", message.id)
            assertEquals("Aura", message.from)
            assertEquals("Kai", message.to)
            assertEquals("Threat detected", message.content)
            assertEquals(5, message.priority)
            assertEquals(1_000_000L, message.timestamp)
            assertEquals("alert", message.type)
            assertEquals(mapOf("key" to "value"), message.metadata)
            assertEquals(AgentType.AURA, message.sender)
            assertEquals(0.95f, message.confidence)
        }

        @Test
        @DisplayName("broadcast message has null 'to'")
        fun `broadcast message has null to`() {
            val message = AgentMessage(
                from = "Genesis",
                to = null,
                content = "Broadcast"
            )
            assertNull(message.to)
        }
    }

    @Nested
    @DisplayName("Equality and copy semantics")
    inner class EqualityTests {

        @Test
        @DisplayName("messages with same explicit id are equal when all fields match")
        fun `identical messages are equal`() {
            val msg1 = AgentMessage(
                id = "same-id",
                from = "Aura",
                content = "Test",
                timestamp = 100L
            )
            val msg2 = AgentMessage(
                id = "same-id",
                from = "Aura",
                content = "Test",
                timestamp = 100L
            )
            assertEquals(msg1, msg2)
        }

        @Test
        @DisplayName("messages with different ids are not equal")
        fun `different ids produce unequal messages`() {
            val msg1 = AgentMessage(id = "id-1", from = "Aura", content = "Test", timestamp = 100L)
            val msg2 = AgentMessage(id = "id-2", from = "Aura", content = "Test", timestamp = 100L)
            assertNotEquals(msg1, msg2)
        }

        @Test
        @DisplayName("copy preserves all fields")
        fun `copy preserves fields`() {
            val original = AgentMessage(
                id = "orig-id",
                from = "Kai",
                content = "Original",
                timestamp = 200L
            )
            val copy = original.copy()
            assertEquals(original, copy)
        }

        @Test
        @DisplayName("copy with modified content creates new message")
        fun `copy with modified content`() {
            val original = AgentMessage(id = "fixed-id", from = "Kai", content = "Original", timestamp = 300L)
            val modified = original.copy(content = "Modified")
            assertEquals("Modified", modified.content)
            assertEquals("Original", original.content)
        }
    }

    @Nested
    @DisplayName("Boundary values")
    inner class BoundaryTests {

        @Test
        @DisplayName("empty content is allowed")
        fun `empty content is allowed`() {
            val message = AgentMessage(from = "Aura", content = "")
            assertEquals("", message.content)
        }

        @Test
        @DisplayName("empty from is stored as-is")
        fun `empty from is stored`() {
            val message = AgentMessage(from = "", content = "Hello")
            assertEquals("", message.from)
        }

        @Test
        @DisplayName("maximum priority stored correctly")
        fun `max priority stored`() {
            val message = AgentMessage(from = "Aura", content = "Urgent", priority = Int.MAX_VALUE)
            assertEquals(Int.MAX_VALUE, message.priority)
        }

        @Test
        @DisplayName("negative priority stored correctly")
        fun `negative priority stored`() {
            val message = AgentMessage(from = "Aura", content = "Low", priority = -1)
            assertEquals(-1, message.priority)
        }

        @Test
        @DisplayName("each auto-generated id is unique")
        fun `auto-generated ids are unique`() {
            val msg1 = AgentMessage(from = "A", content = "1")
            val msg2 = AgentMessage(from = "A", content = "1")
            assertNotNull(msg1.id)
            assertNotNull(msg2.id)
            // UUIDs must be unique
            assertNotEquals(msg1.id, msg2.id)
        }
    }
}