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
import echoflow.core.design_system.theme.generated.resources.gflex_variable
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource

private const val GoogleRond = 100f
private val Weights = (100..900 step 100).map(transform = ::FontWeight)

@Composable
fun gFlexFontFamily(
    vararg variationSettings: FontVariation.Setting,
): FontFamily = rememberVariableFontFamily(
    resource = Res.font.gflex_variable,
    additionalVariationSettings = variationSettings,
)

@Composable
fun gSansFontFamily(
    vararg variationSettings: FontVariation.Setting,
): FontFamily = rememberVariableFontFamily(
    resource = Res.font.gsans_variable,
    additionalVariationSettings = variationSettings,
)

@Composable
private fun rememberVariableFontFamily(
    resource: FontResource,
    width: Float = 100f,
    style: FontStyle = FontStyle.Normal,
    vararg additionalVariationSettings: FontVariation.Setting,
): FontFamily {
    val fonts = Weights.map { fontWeight ->
        val defaultSettings = listOf(
            FontVariation.weight(value = fontWeight.weight),
            FontVariation.width(value = width),
            FontVariation.Setting(name = "ROND", value = GoogleRond),
        )
        val additionalAxes = additionalVariationSettings.map { it.axisName }.toSet()
        val filteredDefaults = defaultSettings.filterNot { it.axisName in additionalAxes }
        val allSettings = filteredDefaults + additionalVariationSettings
        Font(
            resource = resource,
            weight = fontWeight,
            style = style,
            variationSettings = FontVariation.Settings(*allSettings.toTypedArray()),
        )
    }

    return remember(
        resource,
        width,
        style,
        additionalVariationSettings.contentHashCode(),
    ) {
        FontFamily(fonts)
    }
}

@Composable
internal fun rememberEchoFlowType(): Typography {
    val fontFamily = gSansFontFamily()

    return remember(key1 = fontFamily) {
        val base = Typography()
        Typography(
            displayLarge = base.displayLarge.copy(fontFamily = fontFamily),
            displayMedium = base.displayMedium.copy(fontFamily = fontFamily),
            displaySmall = base.displaySmall.copy(fontFamily = fontFamily),
            headlineLarge = base.headlineLarge.copy(fontFamily = fontFamily),
            headlineMedium = base.headlineMedium.copy(fontFamily = fontFamily),
            headlineSmall = base.headlineSmall.copy(fontFamily = fontFamily),
            titleLarge = base.titleLarge.copy(fontFamily = fontFamily),
            titleMedium = base.titleMedium.copy(fontFamily = fontFamily),
            titleSmall = base.titleSmall.copy(fontFamily = fontFamily),
            bodyLarge = base.bodyLarge.copy(fontFamily = fontFamily),
            bodyMedium = base.bodyMedium.copy(fontFamily = fontFamily),
            bodySmall = base.bodySmall.copy(fontFamily = fontFamily),
            labelLarge = base.labelLarge.copy(fontFamily = fontFamily),
            labelMedium = base.labelMedium.copy(fontFamily = fontFamily),
            labelSmall = base.labelSmall.copy(fontFamily = fontFamily),
        )
    }
}