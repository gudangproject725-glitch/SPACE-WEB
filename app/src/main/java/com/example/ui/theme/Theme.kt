package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val SpaceDarkColorScheme = darkColorScheme(
    primary = SpaceIvory,
    onPrimary = SpaceBlack,
    primaryContainer = SpaceGraphite,
    onPrimaryContainer = SpaceWhite,
    secondary = SpaceTitanium,
    onSecondary = SpaceBlack,
    secondaryContainer = SpaceCarbon,
    onSecondaryContainer = SpaceIvory,
    tertiary = SpaceAmberAccent,
    onTertiary = SpaceBlack,
    background = SpaceBlack,
    onBackground = SpaceWhite,
    surface = SpaceCarbon,
    onSurface = SpaceWhite,
    surfaceVariant = SpaceGraphite,
    onSurfaceVariant = SpaceTitanium,
    outline = SpaceBorderSubtle,
    outlineVariant = SpaceBorderStrong,
    error = SpaceError,
    onError = SpaceBlack
)

private val SpaceShapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(6.dp),
    large = RoundedCornerShape(10.dp),
    extraLarge = RoundedCornerShape(16.dp)
)

@Composable
fun SpaceAutomotiveTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SpaceDarkColorScheme,
        typography = SpaceTypography,
        shapes = SpaceShapes,
        content = content
    )
}
