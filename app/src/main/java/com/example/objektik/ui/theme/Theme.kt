package com.example.objektik.ui.theme


import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = GreenAccent,
    background = BackgroundLight,
    surface = BackgroundLight,
    onPrimary = Color.White,  // Texte sur fond bleu
    onSecondary = Color.White,  // Texte sur fond vert
    onBackground = PrimaryText,  // Texte sur fond clair
    onSurface = PrimaryText,  // Texte sur surfaces claires
    error = ErrorColor,
    onError = Color.White
)

// pas de theme sombre
@Composable
fun ObjektikTheme(
//    darkTheme: Boolean = False,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

//        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MyTypography,
        content = content
    )
}