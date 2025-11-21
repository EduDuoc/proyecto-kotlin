package com.SPA.Perfulandia.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalContext

// ✨ Paleta árabe moderna - Tema oscuro (Sofisticado y elegante)
private val DarkColorScheme = darkColorScheme(
    primary = RosyGold,              // Dorado rosado (#C28872)
    onPrimary = BlackMatte,          // Texto negro mate
    secondary = CharcoalGrey,        // Gris carbón (#2B2B2B)
    onSecondary = PearlWhite,        // Texto blanco perla
    tertiary = RosyGoldDark,         // Dorado rosado oscuro
    onTertiary = PearlWhite,         // Texto blanco perla
    background = BlackMatte,         // Fondo negro mate (#1A1A1A)
    surface = CharcoalGrey,          // Superficie gris carbón
    onBackground = PearlWhite,       // Texto blanco perla
    onSurface = PearlWhite           // Texto blanco perla
)

// ✨ Paleta árabe moderna - Tema claro (Sofisticado y elegante)
private val LightColorScheme = lightColorScheme(
    primary = RosyGold,              // Dorado rosado (#C28872)
    onPrimary = PearlWhite,          // Texto blanco perla
    secondary = CharcoalGrey,        // Gris carbón (#2B2B2B)
    onSecondary = PearlWhite,        // Texto blanco perla
    tertiary = RosyGoldDark,         // Dorado rosado oscuro
    onTertiary = PearlWhite,         // Texto blanco perla
    background = PearlWhite,         // Fondo blanco perla (#F2F2F2)
    surface = Color(0xFFFFFFFF),     // Superficie blanco puro
    onBackground = CharcoalGrey,     // Texto gris carbón
    onSurface = CharcoalGrey         // Texto gris carbón
)

@Composable
fun PerfulandiaSPATheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color desactivado para que se use la paleta árabe de lujo
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}