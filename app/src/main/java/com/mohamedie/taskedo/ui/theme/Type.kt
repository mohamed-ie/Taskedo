package com.mohamedie.taskedo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mohamedie.taskedo.R

private val nunito = FontFamily(
    Font(resId = R.font.nunito_sans_extralight, weight = FontWeight.ExtraLight),
    Font(resId = R.font.nunito_sans_extralight_italic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),

    Font(resId = R.font.nunito_sans_light, weight = FontWeight.Light),
    Font(resId = R.font.nunito_sans_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),

    Font(resId = R.font.nunito_sans, weight = FontWeight.Normal),
    Font(resId = R.font.nunito_sans_italic, weight = FontWeight.Normal, style = FontStyle.Italic),

    Font(resId = R.font.nunito_sans_semibold, weight = FontWeight.SemiBold),
    Font(resId = R.font.nunito_sans_semibold_italic, weight = FontWeight.SemiBold, style = FontStyle.Italic),

    Font(resId = R.font.nunito_sans_bold, weight = FontWeight.Bold),
    Font(resId = R.font.nunito_sans_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),

    Font(resId = R.font.nunito_sans_extrabold, weight = FontWeight.ExtraBold),
    Font(resId = R.font.nunito_sans_extrabold_italic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),

    Font(resId = R.font.nunito_sans_black, weight = FontWeight.Black),
    Font(resId = R.font.nunito_sans_black_italic, weight = FontWeight.Black, style = FontStyle.Italic),
)
private val defaultTypography = Typography()

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = nunito),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = nunito),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = nunito),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = nunito),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = nunito),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = nunito),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = nunito),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = nunito),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = nunito),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = nunito),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = nunito),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = nunito),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = nunito),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = nunito),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = nunito)

)