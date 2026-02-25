package com.devtamuno.cashipayment.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import cashipayment.composeapp.generated.resources.Res
import cashipayment.composeapp.generated.resources.poppins_bold
import cashipayment.composeapp.generated.resources.poppins_light
import cashipayment.composeapp.generated.resources.poppins_medium
import cashipayment.composeapp.generated.resources.poppins_regular
import cashipayment.composeapp.generated.resources.poppins_semi_bold
import org.jetbrains.compose.resources.Font

@Composable
internal fun getPoppinsFontFamily() =
    FontFamily(
        Font(Res.font.poppins_regular, FontWeight.Normal),
        Font(Res.font.poppins_medium, FontWeight.Medium),
        Font(Res.font.poppins_light, FontWeight.Light),
        Font(Res.font.poppins_semi_bold, FontWeight.SemiBold),
        Font(Res.font.poppins_bold, FontWeight.Bold),
    )

// Set of Material typography styles to start with
@Composable
internal fun getTypography(): Typography {
  val fontFamily = getPoppinsFontFamily()
  val defaultTypography = Typography()
  return Typography(
      displayLarge = defaultTypography.displayLarge.copy(fontFamily = fontFamily),
      displayMedium = defaultTypography.displayMedium.copy(fontFamily = fontFamily),
      displaySmall = defaultTypography.displaySmall.copy(fontFamily = fontFamily),
      headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = fontFamily),
      headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = fontFamily),
      headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = fontFamily),
      titleLarge = defaultTypography.titleLarge.copy(fontFamily = fontFamily),
      titleMedium = defaultTypography.titleMedium.copy(fontFamily = fontFamily),
      titleSmall = defaultTypography.titleSmall.copy(fontFamily = fontFamily),
      bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = fontFamily),
      bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = fontFamily),
      bodySmall = defaultTypography.bodySmall.copy(fontFamily = fontFamily),
      labelLarge = defaultTypography.labelLarge.copy(fontFamily = fontFamily),
      labelMedium = defaultTypography.labelMedium.copy(fontFamily = fontFamily),
      labelSmall = defaultTypography.labelSmall.copy(fontFamily = fontFamily),
  )
}
