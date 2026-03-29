package com.am.stbus.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.GoogleFont.Provider
import androidx.compose.ui.unit.sp
import com.am.stbus.R

// Set of Material typography styles to start with
val provider = Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)
val displayFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Merriweather"),
        fontProvider = provider,
    )
)

// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),

    headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),

    titleLarge = baseline.titleLarge.copy(
        fontFamily = displayFontFamily,
        fontWeight = W600
    ),
    titleMedium = baseline.titleMedium.copy(
        fontFamily = displayFontFamily,
        fontWeight = W600
    ),
    titleSmall = baseline.titleSmall.copy(
        fontFamily = displayFontFamily,
        fontWeight = W600,
        fontSize = 14.sp
    ),

    // Body & labels use system default (Roboto on most Android devices)
    bodyLarge = baseline.bodyLarge.copy(fontFamily = FontFamily.Default),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = FontFamily.Default),
    bodySmall = baseline.bodySmall.copy(fontFamily = FontFamily.Default),

    labelLarge = baseline.labelLarge.copy(fontFamily = FontFamily.Default),
    labelMedium = baseline.labelMedium.copy(fontFamily = FontFamily.Default),
    labelSmall = baseline.labelSmall.copy(fontFamily = FontFamily.Default),
)
