package ru.shashluchokdesign

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.shashluchokdesign.components.ScrollbarScreen
import ru.shashluchokdesign.components.TabGroupScreen
import ru.shashluchokdesign.ui.theme.SampleTheme
import ru.shashluchokdesign.ui.theme.ScreenHeightBreakpoints

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    onThemeSwitch: (Boolean) -> Unit = {},
) {
    val navHost = remember { NavigationHost() }
    var isDarkTheme by remember { mutableStateOf(false) }

    val navigationIcon = @Composable {
        if (navHost.canGoUp()) {
            IconButton(onClick = navHost::goUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back button"
                )
            }
        }
    }

    SampleTheme(darkTheme = isDarkTheme) {
        BoxWithConstraints(modifier = modifier) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = navHost.getCurrentScreen().title)
                        },
                        navigationIcon = {
                            if (navHost.canGoUp()) {
                                navigationIcon()
                            }
                        },
                        actions = {
                            ThemeSwitch(
                                isDarkTheme = isDarkTheme,
                                onThemeSwitch = {
                                    isDarkTheme = it
                                    onThemeSwitch(it)
                                }
                            )
                        }
                    )
                },
                bottomBar = {
                    if (maxHeight > ScreenHeightBreakpoints.phoneLandscape) {
                        BottomNavigationAppBar(
                            navHost
                        )
                    }
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                ) {
                    when (navHost.getCurrentScreen()) {
                        SampleScreens.Start -> MenuScreen(navHost)
                        SampleScreens.Components -> ComponentsScreen(
                            navHost,
                            navHost.getChildren(SampleScreens.Components)
                        )

                        SampleScreens.Scrollbar -> ScrollbarScreen()
                        SampleScreens.TabGroup -> TabGroupScreen()
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNavigationAppBar(
    navHost: NavigationHost
) {
    if (navHost.canGoUp()) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ) {
            NavigationBarItem(
                label = { Text("Prev") },
                icon = {
                    Icon(
                        Icons.Filled.ArrowLeft,
                        contentDescription = "Previous screen",
                        tint = if (navHost.hasPreviousScreen()) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.35f
                            )
                        }
                    )
                },
                enabled = navHost.hasPreviousScreen(),
                onClick = { navHost.goPreviousScreen() },
                selected = false
            )
            NavigationBarItem(
                label = { Text("Home") },
                icon = {
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = "Home",
                    )
                },
                onClick = { navHost.goHome() },
                selected = false
            )
            NavigationBarItem(
                label = { Text("Next") },
                icon = {
                    Icon(
                        Icons.Filled.ArrowRight,
                        contentDescription = "Next screen",
                        tint = if (navHost.hasNextScreen()) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.35f
                            )
                        }
                    )
                },
                enabled = navHost.hasNextScreen(),
                onClick = { navHost.goNextScreen() },
                selected = false
            )
        }
    }
}

@Composable
private fun ThemeSwitch(
    isDarkTheme: Boolean,
    onThemeSwitch: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text("☀️")
        Switch(
            modifier = Modifier.padding(horizontal = 6.dp),
            checked = isDarkTheme,
            onCheckedChange = { onThemeSwitch(it) }
        )
        Text("🌘")
    }
}
