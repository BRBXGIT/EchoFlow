package com.brbx.design_system.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import echoflow.core.design_system.theme.generated.resources.Res
import echoflow.core.design_system.theme.generated.resources.gsans_variable
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource

private const val GoogleSansFlexRond = 100f
private val baseline = Typography()

@Composable
internal fun echoFlowType(): Typography {
    val fontFamily = gSansFontFamily()

    return remember(fontFamily) {
        Typography(
            displayLarge = baseline.displayLarge.copy(fontFamily = fontFamily),
            displayMedium = baseline.displayMedium.copy(fontFamily = fontFamily),
            displaySmall = baseline.displaySmall.copy(fontFamily = fontFamily),
            headlineLarge = baseline.headlineLarge.copy(fontFamily = fontFamily),
            headlineMedium = baseline.headlineMedium.copy(fontFamily = fontFamily),
            headlineSmall = baseline.headlineSmall.copy(fontFamily = fontFamily),
            titleLarge = baseline.titleLarge.copy(fontFamily = fontFamily),
            titleMedium = baseline.titleMedium.copy(fontFamily = fontFamily),
            titleSmall = baseline.titleSmall.copy(fontFamily = fontFamily),
            bodyLarge = baseline.bodyLarge.copy(fontFamily = fontFamily),
            bodyMedium = baseline.bodyMedium.copy(fontFamily = fontFamily),
            bodySmall = baseline.bodySmall.copy(fontFamily = fontFamily),
            labelLarge = baseline.labelLarge.copy(fontFamily = fontFamily),
            labelMedium = baseline.labelMedium.copy(fontFamily = fontFamily),
            labelSmall = baseline.labelSmall.copy(fontFamily = fontFamily),
        )
    }
}

@Composable
private fun gSansFontFamily(): FontFamily {
    val w100 = fontVariable(weight = 100)
    val w200 = fontVariable(weight = 200)
    val w300 = fontVariable(weight = 300)
    val w400 = fontVariable(weight = 400)
    val w500 = fontVariable(weight = 500)
    val w600 = fontVariable(weight = 600)
    val w700 = fontVariable(weight = 700)
    val w800 = fontVariable(weight = 800)
    val w900 = fontVariable(weight = 900)

    return remember(w100, w200, w300, w400, w500, w600, w700, w800, w900) {
        FontFamily(w100, w200, w300, w400, w500, w600, w700, w800, w900)
    }
}

@Composable
private fun fontVariable(
    weight: Int,
    resource: FontResource = Res.font.gsans_variable,
    width: Float = 100f,
    style: FontStyle = FontStyle.Normal,
) = Font(
    resource = resource,
    weight = FontWeight(weight),
    style = style,
    variationSettings = FontVariation.Settings(
        FontVariation.weight(value = weight),
        FontVariation.width(value = width),
        FontVariation.Setting(name = "rond", value = GoogleSansFlexRond)
    )
)