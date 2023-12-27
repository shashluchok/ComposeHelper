package ru.shashluchokdesign

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@Composable
fun ComponentsScreen(
    navHost: NavigationHost,
    componentsList: List<SampleScreens>
) {
    LazyColumn {
        items(componentsList) { screen ->
            NavigationMenuItem(
                navHost = navHost,
                screen = screen
            )
        }
    }
}
