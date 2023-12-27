package ru.shashluchokdesign

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import ru.shashluchokdesign.AppScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppScreen(
                onThemeSwitch = { isDark ->
                    WindowCompat.getInsetsController(window, window.decorView)
                        .isAppearanceLightStatusBars = !isDark
                }
            )
        }
    }
}
