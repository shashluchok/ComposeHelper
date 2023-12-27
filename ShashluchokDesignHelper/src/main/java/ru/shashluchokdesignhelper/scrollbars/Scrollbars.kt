@file:Suppress("ktlint:twitter-compose:modifier-composable-check")

package ru.shashluchokdesignhelper.scrollbars

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

private const val fadeInDurationMillis = 150
private const val fadeOutDurationMillis = 500
private const val scrollbarFadeOutDelay = 200

private val scrollbarPadding = PaddingValues(2.dp)

data class ScrollbarConfig internal constructor(
    val color: Color,
    val thickness: Dp,
    val padding: PaddingValues,
    val fadeOutDelay: Int,
    val alwaysVisible: Boolean
) {
    companion object {
        @Composable
        fun defaults(
            scrollbarColor: Color = MaterialTheme.colorScheme.outlineVariant,
            scrollbarThickness: Dp = 4.dp,
            padding: PaddingValues = scrollbarPadding,
            fadeOutDelay: Int = scrollbarFadeOutDelay,
            alwaysVisible: Boolean = false
        ): ScrollbarConfig = ScrollbarConfig(
            scrollbarColor,
            scrollbarThickness,
            padding,
            fadeOutDelay,
            alwaysVisible
        )
    }
}

@Composable
fun Modifier.sdhHorizontalScrollWithScrollbar(
    state: ScrollState,
    scrollbarConfig: ScrollbarConfig = ScrollbarConfig.defaults(),
    enabled: Boolean = true,
    flingBehavior: FlingBehavior? = null,
    reverseScrolling: Boolean = false,
): Modifier {
    val scrollbarInfoProvider = ScrollStateInfoProvider(
        state = state,
        orientation = Orientation.Horizontal
    )
    return drawScrollbar(
        scrollbarInfoProvider = scrollbarInfoProvider,
        scrollbarConfig = scrollbarConfig
    ).horizontalScroll(state, enabled, flingBehavior, reverseScrolling)
}

@Composable
fun Modifier.sdhVerticalScrollWithScrollbar(
    state: ScrollState,
    scrollbarConfig: ScrollbarConfig = ScrollbarConfig.defaults(),
    enabled: Boolean = true,
    flingBehavior: FlingBehavior? = null,
    reverseScrolling: Boolean = false,
): Modifier {
    val scrollbarInfoProvider = ScrollStateInfoProvider(
        state = state,
        orientation = Orientation.Vertical
    )
    return drawScrollbar(
        scrollbarInfoProvider = scrollbarInfoProvider,
        scrollbarConfig = scrollbarConfig
    ).verticalScroll(state, enabled, flingBehavior, reverseScrolling)
}

@Composable
fun Modifier.sdhHorizontalScrollBar(
    state: LazyListState,
    scrollbarConfig: ScrollbarConfig = ScrollbarConfig.defaults(),
): Modifier {
    val scrollbarInfoProvider = LazyListStateInfoProvider(
        state = state,
        orientation = Orientation.Horizontal
    )
    return drawScrollbar(
        scrollbarInfoProvider = scrollbarInfoProvider,
        scrollbarConfig = scrollbarConfig
    )
}

@Composable
fun Modifier.sdhVerticalScrollBar(
    state: LazyListState,
    scrollbarConfig: ScrollbarConfig = ScrollbarConfig.defaults(),
): Modifier {
    val scrollbarInfoProvider = LazyListStateInfoProvider(
        state = state,
        orientation = Orientation.Vertical
    )
    return drawScrollbar(
        scrollbarInfoProvider = scrollbarInfoProvider,
        scrollbarConfig = scrollbarConfig
    )
}

@Composable
fun Modifier.sdhVerticalScrollBar(
    state: LazyGridState,
    columns: Int,
    scrollbarConfig: ScrollbarConfig = ScrollbarConfig.defaults(),
): Modifier {
    val scrollbarInfoProvider = LazyGridStateInfoProvider(
        state = state,
        spans = columns,
        orientation = Orientation.Vertical
    )
    return drawScrollbar(
        scrollbarInfoProvider = scrollbarInfoProvider,
        scrollbarConfig = scrollbarConfig
    )
}

@Composable
fun Modifier.sdhHorizontalScrollBar(
    state: LazyGridState,
    rows: Int,
    scrollbarConfig: ScrollbarConfig = ScrollbarConfig.defaults(),
): Modifier {
    val scrollbarInfoProvider = LazyGridStateInfoProvider(
        state = state,
        spans = rows,
        orientation = Orientation.Horizontal
    )
    return drawScrollbar(
        scrollbarInfoProvider = scrollbarInfoProvider,
        scrollbarConfig = scrollbarConfig
    )
}

@Composable
private fun Modifier.drawScrollbar(
    scrollbarConfig: ScrollbarConfig,
    scrollbarInfoProvider: ScrollbarInfoProvider
): Modifier {
    if (scrollbarConfig.alwaysVisible) {
        return drawWithContent {
            drawContent()
            with(scrollbarInfoProvider) {
                drawScrollbar(
                    orientation,
                    scrollbarConfig.color,
                    1f,
                    thumbSize,
                    startOffset,
                    scrollbarConfig.thickness,
                    scrollbarConfig.padding
                )
            }
        }
    }

    val duration = if (scrollbarInfoProvider.state.isScrollInProgress) fadeInDurationMillis else fadeOutDurationMillis
    val alpha by animateFloatAsState(
        targetValue = if (scrollbarInfoProvider.state.isScrollInProgress) 1f else 0f,
        animationSpec = tween(
            durationMillis = duration,
            delayMillis = if (scrollbarInfoProvider.state.isScrollInProgress) 0 else scrollbarConfig.fadeOutDelay,
        ),
    )
    return drawWithContent {
        drawContent()
        if (alpha != 0F) {
            with(scrollbarInfoProvider) {
                drawScrollbar(
                    orientation,
                    scrollbarConfig.color,
                    alpha,
                    thumbSize,
                    startOffset,
                    scrollbarConfig.thickness,
                    scrollbarConfig.padding
                )
            }
        }
    }
}

private fun DrawScope.drawScrollbar(
    orientation: Orientation,
    color: Color,
    alpha: Float,
    thumbSize: Float,
    startOffset: Float,
    scrollbarThickness: Dp,
    padding: PaddingValues
) {
    val thicknessPx = scrollbarThickness.toPx()
    val topPadding = padding.calculateTopPadding().toPx()
    val bottomPadding = padding.calculateBottomPadding().toPx()
    val startPadding = padding.calculateStartPadding(layoutDirection).toPx()
    val endPadding = padding.calculateEndPadding(layoutDirection).toPx()

    val viewPortCrossAxisLength = if (orientation == Orientation.Vertical) {
        size.width
    } else {
        size.height
    }
    val viewPortLength = if (orientation == Orientation.Vertical) {
        size.height
    } else {
        size.width
    }
    val topLeft = if (orientation == Orientation.Vertical) {
        Offset(
            x = if (layoutDirection == LayoutDirection.Ltr) {
                viewPortCrossAxisLength - thicknessPx - endPadding
            } else {
                startPadding
            },
            y = startOffset + topPadding
        )
    } else {
        Offset(
            x = if (layoutDirection == LayoutDirection.Ltr) {
                startOffset + startPadding
            } else {
                viewPortLength - startOffset - thumbSize - endPadding
            },
            y = viewPortCrossAxisLength - thicknessPx - bottomPadding
        )
    }

    val indicatorOffset = if (orientation == Orientation.Vertical) {
        topPadding + bottomPadding
    } else {
        startPadding + endPadding
    }

    val indicatorLength = thumbSize - indicatorOffset

    val size = if (orientation == Orientation.Vertical) {
        Size(thicknessPx, indicatorLength)
    } else {
        Size(indicatorLength, thicknessPx)
    }

    val cornerRadius = CornerRadius(
        x = scrollbarThickness.toPx() / 2,
        y = scrollbarThickness.toPx() / 2
    )

    drawRoundRect(
        color = color,
        topLeft = topLeft,
        size = size,
        cornerRadius = cornerRadius,
        alpha = alpha
    )
}
