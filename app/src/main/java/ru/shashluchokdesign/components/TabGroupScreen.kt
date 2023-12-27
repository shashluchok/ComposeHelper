package ru.shashluchokdesign.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.persistentListOf
import ru.shashluchokdesignhelper.widgets.tabs.SdhTabGroup

@Composable
internal fun TabGroupScreen() {
    val tabs = persistentListOf(
        "Tiny",
        "Small title",
        "Very long title",
        "Another very long title",
    )
    val singleTab = persistentListOf(
        "Single tab with no selection"
    )
    var currentTabIndex by remember { mutableStateOf<Int?>(0) }
    Column {
        SdhTabGroup(
            tabItems = tabs,
            onTabSelected = {
                currentTabIndex = it
            },
            selectedTabIndex = currentTabIndex
        )
        SdhTabGroup(
            tabItems = singleTab,
            onTabSelected = { },
            selectedTabIndex = null
        )
        TemplatePage(title = tabs[currentTabIndex ?: 0])
    }
}

@Composable
private fun TemplatePage(title: String) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Page: $title", modifier = Modifier.align(Alignment.Center))
    }
}
