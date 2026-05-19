package dev.aurakai.auraframefx.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**
 * Shape definitions for AuraFrameFX
 */
val AppShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp)
)

val ChatBubbleIncomingShape = RoundedCornerShape(
    topStart = 4.dp,
    topEnd = 16.dp,
    bottomStart = 16.dp,
    bottomEnd = 16.dp
)

val ChatBubbleOutgoingShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 4.dp,
    bottomStart = 16.dp,
    bottomEnd = 16.dp
)

val ButtonShape = RoundedCornerShape(12.dp)
val CardShape = RoundedCornerShape(16.dp)
val InputFieldShape = RoundedCornerShape(12.dp)
val FloatingActionButtonShape = RoundedCornerShape(16.dp)

object CyberpunkShapes {
    val hexWindowShape = object : Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density,
        ): Outline {
            val path = Path().apply {
                val cornerSize = size.width.coerceAtMost(size.height) * 0.1f
                moveTo(0f, cornerSize)
                lineTo(cornerSize, 0f)
                lineTo(size.width - cornerSize, 0f)
                lineTo(size.width, cornerSize)
                lineTo(size.width, size.height - cornerSize)
                lineTo(size.width - cornerSize, size.height)
                lineTo(cornerSize, size.height)
                lineTo(0f, size.height - cornerSize)
                close()
            }
            return Outline.Generic(path)
        }
    }

    val angledButtonShape = object : Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density,
        ): Outline {
            val cornerSize = size.height * 0.3f
            val path = Path().apply {
                moveTo(0f, cornerSize)
                lineTo(cornerSize, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                lineTo(cornerSize, size.height)
                lineTo(0f, size.height - cornerSize)
                close()
            }
            return Outline.Generic(path)
        }
    }
}
