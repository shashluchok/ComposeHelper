package ru.shashluchokdesign.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableList
import ru.shashluchokdesignhelper.scrollbars.sdhHorizontalScrollBar
import ru.shashluchokdesignhelper.scrollbars.sdhHorizontalScrollWithScrollbar
import ru.shashluchokdesignhelper.scrollbars.sdhVerticalScrollBar
import ru.shashluchokdesignhelper.scrollbars.sdhVerticalScrollWithScrollbar
import ru.shashluchokdesignhelper.widgets.tabs.SdhTabGroup

private val verticalDividerHeight = 30.dp
private val verticalDividerWidth = 1.dp

private val demoItemPadding = 8.dp

private val lazyListContentPadding = 100.dp

private val lazyGridSpanCount = 4

@Composable
internal fun ScrollbarScreen() {
    val tabs =
        Scrollbar.entries.map { it.title }
            .toImmutableList()

    var currentTabIndex by remember { mutableStateOf<Int?>(0) }
    Column {
        SdhTabGroup(
            tabItems = tabs,
            onTabSelected = {
                currentTabIndex = it
            },
            selectedTabIndex = currentTabIndex
        )
        ScrollbarDemoPage(scrollbar = Scrollbar.entries[currentTabIndex ?: 0])
    }
}

@Composable
private fun ScrollbarDemoPage(scrollbar: Scrollbar) {
    val items = List(60) { "item $it" }

    when (scrollbar) {
        Scrollbar.COLUMN -> {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .sdhVerticalScrollWithScrollbar(scrollState)
            ) {
                items.onEach {
                    DemoItem(title = it, orientation = Orientation.Vertical)
                }
            }
        }

        Scrollbar.ROW -> {
            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .sdhHorizontalScrollWithScrollbar(scrollState)
            ) {
                items.onEach {
                    DemoItem(title = it, orientation = Orientation.Horizontal)
                }
            }
        }

        Scrollbar.LAZY_COLUMN -> {
            val scrollState = rememberLazyListState()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .sdhVerticalScrollBar(scrollState),
                state = scrollState,
                contentPadding = PaddingValues(bottom = lazyListContentPadding)
            ) {
                items(items) {
                    DemoItem(title = it, orientation = Orientation.Vertical)
                }
            }
        }

        Scrollbar.LAZY_ROW -> {
            val scrollState = rememberLazyListState()
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .sdhHorizontalScrollBar(scrollState),
                contentPadding = PaddingValues(end = lazyListContentPadding),
                state = scrollState
            ) {
                items(items) {
                    DemoItem(title = it, orientation = Orientation.Horizontal)
                }
            }
        }

        Scrollbar.LAZY_VERTICAL_GRID -> {
            val scrollState = rememberLazyGridState()
            LazyVerticalGrid(
                columns = GridCells.Fixed(lazyGridSpanCount),
                state = scrollState,
                modifier = Modifier.sdhVerticalScrollBar(scrollState, columns = lazyGridSpanCount),
                contentPadding = PaddingValues(bottom = lazyListContentPadding),
            ) {
                this.items(items) {
                    DemoItem(title = it, orientation = Orientation.Vertical)
                }
            }
        }

        Scrollbar.LAZY_HORIZONTAL_GRID -> {
            val scrollState = rememberLazyGridState()
            LazyHorizontalGrid(
                rows = GridCells.Fixed(lazyGridSpanCount),
                state = scrollState,
                modifier = Modifier.sdhHorizontalScrollBar(scrollState, rows = lazyGridSpanCount),
                contentPadding = PaddingValues(end = lazyListContentPadding),
            ) {
                this.items(items) {
                    DemoItem(title = it, orientation = Orientation.Horizontal)
                }
            }
        }
    }
}

@Composable
private fun DemoItem(
    title: String,
    orientation: Orientation
) {
    Text(
        modifier = Modifier.padding(demoItemPadding),
        text = title
    )
    if (orientation == Orientation.Vertical) {
        Divider()
    } else {
        Divider(
            modifier = Modifier
                .width(verticalDividerWidth)
                .height(verticalDividerHeight)
        )
    }
}

private enum class Scrollbar(val title: String) {
    COLUMN("Column"),
    ROW("Row"),
    LAZY_COLUMN("Lazy column"),
    LAZY_ROW("Lazy row"),
    LAZY_VERTICAL_GRID("Lazy vertical grid"),
    LAZY_HORIZONTAL_GRID("Lazy horizontal grid")
}
