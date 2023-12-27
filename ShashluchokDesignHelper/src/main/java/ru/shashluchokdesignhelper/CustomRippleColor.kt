package ru.shashluchokdesignhelper

import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private class CustomRippleColor(
    private val color: Color,
    base: RippleTheme
) : RippleTheme by base {
    @Composable
    override fun defaultColor(): Color = color
}

@Composable
internal fun CustomRippleColor(color: Color, content: @Composable () -> Unit) {
    val current = LocalRippleTheme.current
    CompositionLocalProvider(
        LocalRippleTheme provides CustomRippleColor(color, current),
    ) {
        content()
    }
}
