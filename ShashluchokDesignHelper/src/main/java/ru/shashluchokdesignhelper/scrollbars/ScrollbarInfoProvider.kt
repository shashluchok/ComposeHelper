package ru.shashluchokdesignhelper.scrollbars

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.ui.graphics.drawscope.DrawScope

internal interface ScrollbarInfoProvider {
    val state: ScrollableState
    val orientation: Orientation
    val shouldDraw: Boolean
    val DrawScope.thumbSize: Float
    val DrawScope.startOffset: Float
}

internal class LazyGridStateInfoProvider(
    override val orientation: Orientation,
    override val state: LazyGridState,
    private val spans: Int,
) : ScrollbarInfoProvider {

    private val layoutInfo = state.layoutInfo
    private val viewportSize = with(layoutInfo) {
        viewportEndOffset - viewportStartOffset - afterContentPadding
    }
    private val items = layoutInfo.visibleItemsInfo
    private val crossSpans = (items.size + spans - 1) / spans
    private val itemsSize: Int
        get() {
            var size = 0
            for (i in 0 until crossSpans) {
                size += if (orientation == Orientation.Vertical) {
                    items[i * spans].size.height
                } else {
                    items[i * spans].size.width
                }
            }
            return size
        }
    private val estimatedItemSize = if (crossSpans == 0) 0f else itemsSize.toFloat() / crossSpans
    private val totalRow = (layoutInfo.totalItemsCount + spans - 1) / spans
    private val totalSize = estimatedItemSize * totalRow

    override val DrawScope.thumbSize: Float
        get() {
            val canvasSize = if (orientation == Orientation.Vertical) {
                size.height
            } else {
                size.width
            }
            return viewportSize / totalSize * (canvasSize - layoutInfo.afterContentPadding)
        }
    override val DrawScope.startOffset: Float
        get() {
            val firstItem = items.first()
            return if (crossSpans == 0) {
                0f
            } else {
                val offset = if (orientation == Orientation.Vertical) {
                    firstItem.offset.y
                } else {
                    firstItem.offset.x
                }
                val rowIndex = firstItem.index / spans
                val canvasSize = if (orientation == Orientation.Vertical) {
                    size.height
                } else {
                    size.width
                }
                (estimatedItemSize * rowIndex - offset) / totalSize * (canvasSize - layoutInfo.afterContentPadding)
            }
        }
    override val shouldDraw: Boolean
        get() = items.size < layoutInfo.totalItemsCount || itemsSize > viewportSize
}

internal class LazyListStateInfoProvider(
    override val state: LazyListState,
    override val orientation: Orientation,
) : ScrollbarInfoProvider {

    private val layoutInfo = state.layoutInfo
    private val viewportSize = with(layoutInfo) {
        viewportEndOffset - viewportStartOffset - afterContentPadding
    }
    private val items = layoutInfo.visibleItemsInfo
    private val itemsSize = items.sumOf { it.size }
    private val estimatedItemSize = if (items.isEmpty()) 0f else itemsSize.toFloat() / items.size
    private val totalSize = estimatedItemSize * layoutInfo.totalItemsCount

    override val DrawScope.thumbSize: Float
        get() {
            val canvasSize = if (orientation == Orientation.Horizontal) size.width else size.height
            return viewportSize / totalSize * (canvasSize - layoutInfo.afterContentPadding)
        }
    override val DrawScope.startOffset: Float
        get() {
            return if (items.isEmpty()) {
                0f
            } else {
                val firstItem = items.first()
                val canvasSize =
                    if (orientation == Orientation.Horizontal) size.width else size.height
                (estimatedItemSize * firstItem.index - firstItem.offset) /
                        totalSize * (canvasSize - layoutInfo.afterContentPadding)
            }
        }

    override val shouldDraw: Boolean
        get() = items.size < layoutInfo.totalItemsCount || itemsSize > viewportSize
}

internal class ScrollStateInfoProvider(
    override val state: ScrollState,
    override val orientation: Orientation,
) : ScrollbarInfoProvider {

    override val DrawScope.thumbSize: Float
        get() {
            val canvasSize = if (orientation == Orientation.Horizontal) size.width else size.height
            val totalSize = canvasSize + state.maxValue
            return canvasSize / totalSize * canvasSize
        }
    override val DrawScope.startOffset: Float
        get() {
            val canvasSize = if (orientation == Orientation.Horizontal) size.width else size.height
            val totalSize = canvasSize + state.maxValue
            return state.value / totalSize * canvasSize
        }
    override val shouldDraw: Boolean
        get() = state.maxValue > 0
}
