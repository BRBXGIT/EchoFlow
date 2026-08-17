package com.brbx.design_system.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import echoflow.core.design_system.theme.generated.resources.Res
import echoflow.core.design_system.theme.generated.resources.gsans_variable
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource

private val baseline = Typography()

@Composable
internal fun echoFlowType(): Typography =
    Typography(
        displayLarge = baseline.displayLarge.applyFontFamily(),
        displayMedium = baseline.displayMedium.applyFontFamily(),
        displaySmall = baseline.displaySmall.applyFontFamily(),
        headlineLarge = baseline.headlineLarge.applyFontFamily(),
        headlineMedium = baseline.headlineMedium.applyFontFamily(),
        headlineSmall = baseline.headlineSmall.applyFontFamily(),
        titleLarge = baseline.titleLarge.applyFontFamily(),
        titleMedium = baseline.titleMedium.applyFontFamily(),
        titleSmall = baseline.titleSmall.applyFontFamily(),
        bodyLarge = baseline.bodyLarge.applyFontFamily(),
        bodyMedium = baseline.bodyMedium.applyFontFamily(),
        bodySmall = baseline.bodySmall.applyFontFamily(),
        labelLarge = baseline.labelLarge.applyFontFamily(),
        labelMedium = baseline.labelMedium.applyFontFamily(),
        labelSmall = baseline.labelSmall.applyFontFamily(),
    )

@Composable
private fun TextStyle.applyFontFamily(
    fontFamily: FontFamily = gSansFontFamily(),
): TextStyle = copy(fontFamily = fontFamily)

@Composable
private fun gSansFontFamily(): FontFamily = FontFamily(
    fontVariable(weight = 100),
    fontVariable(weight = 200),
    fontVariable(weight = 300),
    fontVariable(weight = 400),
    fontVariable(weight = 500),
    fontVariable(weight = 600),
    fontVariable(weight = 700),
    fontVariable(weight = 800),
    fontVariable(weight = 900),
)

@Composable
private fun fontVariable(
    weight: Int,
    resource: FontResource = Res.font.gsans_variable,
    width: Float = 87.5f,
    style: FontStyle = FontStyle.Normal,
): Font = Font(
    resource = resource,
    weight = FontWeight(weight),
    style = style,
    variationSettings = FontVariation.Settings(
        FontVariation.weight(value = weight),
        FontVariation.width(value = width),
    )
)