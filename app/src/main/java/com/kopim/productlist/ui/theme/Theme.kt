package com.kopim.productlist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = lightBlue,
    secondary = dirtyWhite,
    background = paleBlue,
    onBackground = textBlack,
    surface = surfaceWhite,
    onPrimary = surfaceWhite,
    primaryContainer = deepBlue,
    onPrimaryContainer = surfaceWhite,
    onSecondary = lightBlue,
)

@Composable
fun ProductsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}