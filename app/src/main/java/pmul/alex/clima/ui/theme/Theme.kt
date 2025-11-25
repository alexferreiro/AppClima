package pmul.alex.clima.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = AccentBlue,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = AccentBlue,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun ClimaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val backgroundBrush = if (darkTheme) {
        Brush.verticalGradient(colors = listOf(BackgroundDark, SurfaceDark))
    } else {
        Brush.verticalGradient(colors = listOf(SkyBlue, SteelBlue))
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = backgroundBrush)
        ) {
            content()
        }
    }
}
