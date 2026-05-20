package dev.aurakai.auraframefx.ui.theme

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * A sleek, futuristic vertical scrollbar for LazyLists.
 */
fun Modifier.verticalScrollbar(
    state: LazyListState,
    color: Color = Color.Cyan
): Modifier = this.drawWithContent {
    drawContent()

    val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
    val totalItems = state.layoutInfo.totalItemsCount
    val visibleItems = state.layoutInfo.visibleItemsInfo.size

    if (totalItems > visibleItems && firstVisibleElementIndex != null) {
        val elementHeight = size.height / totalItems
        val scrollbarHeight = visibleItems * elementHeight
        val scrollbarOffsetY = firstVisibleElementIndex * elementHeight

        drawRoundRect(
            color = color.copy(alpha = 0.2f),
            topLeft = Offset(size.width - 4.dp.toPx(), 0f),
            size = Size(2.dp.toPx(), size.height),
            cornerRadius = CornerRadius(1.dp.toPx(), 1.dp.toPx())
        )

        drawRoundRect(
            color = color.copy(alpha = 0.8f),
            topLeft = Offset(size.width - 4.dp.toPx(), scrollbarOffsetY),
            size = Size(2.dp.toPx(), scrollbarHeight),
            cornerRadius = CornerRadius(1.dp.toPx(), 1.dp.toPx())
        )
    }
}
