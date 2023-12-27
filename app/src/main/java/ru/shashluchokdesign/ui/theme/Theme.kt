package ru.shashluchokdesign.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = sample_theme_light_primary,
    onPrimary = sample_theme_light_onPrimary,
    primaryContainer = sample_theme_light_primaryContainer,
    onPrimaryContainer = sample_theme_light_onPrimaryContainer,
    secondary = sample_theme_light_secondary,
    onSecondary = sample_theme_light_onSecondary,
    secondaryContainer = sample_theme_light_secondaryContainer,
    onSecondaryContainer = sample_theme_light_onSecondaryContainer,
    tertiary = sample_theme_light_tertiary,
    onTertiary = sample_theme_light_onTertiary,
    tertiaryContainer = sample_theme_light_tertiaryContainer,
    onTertiaryContainer = sample_theme_light_onTertiaryContainer,
    error = sample_theme_light_error,
    onError = sample_theme_light_onError,
    errorContainer = sample_theme_light_errorContainer,
    onErrorContainer = sample_theme_light_onErrorContainer,
    outline = sample_theme_light_outline,
    background = sample_theme_light_background,
    onBackground = sample_theme_light_onBackground,
    surface = sample_theme_light_surface,
    onSurface = sample_theme_light_onSurface,
    surfaceVariant = sample_theme_light_surfaceVariant,
    onSurfaceVariant = sample_theme_light_onSurfaceVariant,
    inverseSurface = sample_theme_light_inverseSurface,
    inverseOnSurface = sample_theme_light_inverseOnSurface,
    inversePrimary = sample_theme_light_inversePrimary,
    surfaceTint = sample_theme_light_surfaceTint,
    outlineVariant = sample_theme_light_outlineVariant,
    scrim = sample_theme_light_scrim,
)

private val DarkColorScheme = darkColorScheme(
    primary = sample_theme_dark_primary,
    onPrimary = sample_theme_dark_onPrimary,
    primaryContainer = sample_theme_dark_primaryContainer,
    onPrimaryContainer = sample_theme_dark_onPrimaryContainer,
    secondary = sample_theme_dark_secondary,
    onSecondary = sample_theme_dark_onSecondary,
    secondaryContainer = sample_theme_dark_secondaryContainer,
    onSecondaryContainer = sample_theme_dark_onSecondaryContainer,
    tertiary = sample_theme_dark_tertiary,
    onTertiary = sample_theme_dark_onTertiary,
    tertiaryContainer = sample_theme_dark_tertiaryContainer,
    onTertiaryContainer = sample_theme_dark_onTertiaryContainer,
    error = sample_theme_dark_error,
    onError = sample_theme_dark_onError,
    errorContainer = sample_theme_dark_errorContainer,
    onErrorContainer = sample_theme_dark_onErrorContainer,
    outline = sample_theme_dark_outline,
    background = sample_theme_dark_background,
    onBackground = sample_theme_dark_onBackground,
    surface = sample_theme_dark_surface,
    onSurface = sample_theme_dark_onSurface,
    surfaceVariant = sample_theme_dark_surfaceVariant,
    onSurfaceVariant = sample_theme_dark_onSurfaceVariant,
    inverseSurface = sample_theme_dark_inverseSurface,
    inverseOnSurface = sample_theme_dark_inverseOnSurface,
    inversePrimary = sample_theme_dark_inversePrimary,
    surfaceTint = sample_theme_dark_surfaceTint,
    outlineVariant = sample_theme_dark_outlineVariant,
    scrim = sample_theme_dark_scrim,
)

data class ExtraColorScheme(
    val isDark: Boolean,
    val surfaceBright: Color,
    val surfaceContainer: Color,
    val surfaceContainerHighest: Color,
    val success: Color,
    val onSuccess: Color,
    val successContainer: Color,
    val onSuccessContainer: Color,
    val textFieldCursor: Color,
)

private val LightExtraColors = ExtraColorScheme(
    isDark = false,
    surfaceBright = sample_theme_light_surfaceBright,
    surfaceContainer = sample_theme_light_surfaceContainer,
    surfaceContainerHighest = sample_theme_light_surfaceContainerHighest,
    success = sample_theme_light_success,
    onSuccess = sample_theme_light_onSuccess,
    successContainer = sample_theme_light_successContainer,
    onSuccessContainer = sample_theme_light_onSuccessContainer,
    textFieldCursor = sample_theme_light_textFieldCursor,
)

private val DarkExtraColors = ExtraColorScheme(
    isDark = true,
    surfaceBright = sample_theme_dark_surfaceBright,
    surfaceContainer = sample_theme_dark_surfaceContainer,
    surfaceContainerHighest = sample_theme_dark_surfaceContainerHighest,
    success = sample_theme_dark_success,
    onSuccess = sample_theme_dark_onSuccess,
    successContainer = sample_theme_dark_successContainer,
    onSuccessContainer = sample_theme_dark_onSuccessContainer,
    textFieldCursor = sample_theme_dark_textFieldCursor,
)

private val LocalExtraColors = compositionLocalOf { LightExtraColors }

@Suppress("UnusedReceiverParameter")
val MaterialTheme.extraColors
    @Composable
    get() = LocalExtraColors.current

internal const val disabledAlpha = 0.38f

internal fun getStateColor(color: Color, isEnabled: Boolean): Color {
    return if (isEnabled) {
        color
    } else {
        color.copy(alpha = disabledAlpha)
    }
}

@Composable
fun SampleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val extraColors = if (darkTheme) DarkExtraColors else LightExtraColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ComposeTypography,
    ) {
        CompositionLocalProvider(
            LocalExtraColors provides extraColors,
        ) {
            content()
        }
    }
}
