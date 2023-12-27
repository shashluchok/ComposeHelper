package ru.shashluchokdesign

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.shashluchokdesign.BaseSampleConstants.cardElevation
import ru.shashluchokdesign.BaseSampleConstants.screenPaddings

@Composable
fun MenuScreen(navHost: NavigationHost) {
    LazyColumn {
        items(
            listOf(
                SampleScreens.Components,
            )
        ) {
            NavigationMenuItem(navHost = navHost, screen = it)
        }
    }
}

@Composable
fun NavigationMenuItem(
    navHost: NavigationHost,
    screen: SampleScreens
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(screenPaddings)
            .clickable { navHost.navigateTo(screen) },
        elevation = CardDefaults.cardElevation(cardElevation)
    ) {
        Column(
            modifier = Modifier.padding(screenPaddings)
        ) {
            Text(text = screen.title)
        }
    }
}
