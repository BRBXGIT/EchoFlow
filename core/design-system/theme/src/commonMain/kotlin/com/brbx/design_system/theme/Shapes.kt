package com.brbx.design_system.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val LocalShapes = staticCompositionLocalOf<EchoFlowShapes> { DefaultEchoFlowShapes() }

@Immutable
interface EchoFlowShapes {
    val zeroRadius: Dp
    val extraSmallRadius: Dp
    val smallRadius: Dp
    val mediumRadius: Dp
    val largeRadius: Dp
    val extraLargeRadius: Dp

    val zero: RoundedCornerShape
    val extraSmall: RoundedCornerShape
    val small: RoundedCornerShape
    val medium: RoundedCornerShape
    val large: RoundedCornerShape
    val extraLarge: RoundedCornerShape
}

internal data class DefaultEchoFlowShapes(
    override val zeroRadius: Dp = 0.dp,
    override val extraSmallRadius: Dp = 4.dp,
    override val smallRadius: Dp = 8.dp,
    override val mediumRadius: Dp = 12.dp,
    override val largeRadius: Dp = 16.dp,
    override val extraLargeRadius: Dp = 24.dp,
) : EchoFlowShapes {
    override val zero = RoundedCornerShape(size = zeroRadius)
    override val extraSmall = RoundedCornerShape(size = extraSmallRadius)
    override val small = RoundedCornerShape(size = smallRadius)
    override val medium = RoundedCornerShape(size = mediumRadius)
    override val large = RoundedCornerShape(size = largeRadius)
    override val extraLarge = RoundedCornerShape(size = extraLargeRadius)
}