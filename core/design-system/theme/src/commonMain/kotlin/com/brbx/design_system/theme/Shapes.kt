package com.brbx.design_system.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

internal val LocalShapes = staticCompositionLocalOf<EchoFlowShapes> { DefaultEchoFlowShapes() }

interface EchoFlowShapes {
    val zero: RoundedCornerShape
    val extraSmall: RoundedCornerShape
    val small: RoundedCornerShape
    val medium: RoundedCornerShape
    val large: RoundedCornerShape
    val extraLarge: RoundedCornerShape
}

internal data class DefaultEchoFlowShapes(
    override val zero: RoundedCornerShape = RoundedCornerShape(size = 0.dp),
    override val extraSmall: RoundedCornerShape = RoundedCornerShape(size = 4.dp),
    override val small: RoundedCornerShape = RoundedCornerShape(size = 8.dp),
    override val medium: RoundedCornerShape = RoundedCornerShape(size = 12.dp),
    override val large: RoundedCornerShape = RoundedCornerShape(size = 16.dp),
    override val extraLarge: RoundedCornerShape = RoundedCornerShape(size = 28.dp),
) : EchoFlowShapes