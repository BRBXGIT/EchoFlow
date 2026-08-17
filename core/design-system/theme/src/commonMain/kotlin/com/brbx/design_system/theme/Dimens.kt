package com.brbx.design_system.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val LocalDimens = staticCompositionLocalOf<EchoFlowDimens> { DefaultDimens() }

@Immutable
interface EchoFlowDimens {
    val zero: Dp

    val micro1: Dp
    val micro2: Dp
    val micro3: Dp
    val micro4: Dp
    val micro5: Dp
    val micro6: Dp
    val micro7: Dp
    val micro8: Dp

    val macro1: Dp
    val macro2: Dp
    val macro3: Dp
    val macro4: Dp
    val macro5: Dp
    val macro6: Dp
    val macro7: Dp
    val macro8: Dp
}

@Immutable
internal data class DefaultDimens(
    override val zero: Dp = 0.dp,

    override val micro1: Dp = 2.dp,
    override val micro2: Dp = 4.dp,
    override val micro3: Dp = 6.dp,
    override val micro4: Dp = 8.dp,
    override val micro5: Dp = 10.dp,
    override val micro6: Dp = 12.dp,
    override val micro7: Dp = 14.dp,
    override val micro8: Dp = 16.dp,

    override val macro1: Dp = 20.dp,
    override val macro2: Dp = 24.dp,
    override val macro3: Dp = 28.dp,
    override val macro4: Dp = 32.dp,
    override val macro5: Dp = 36.dp,
    override val macro6: Dp = 40.dp,
    override val macro7: Dp = 44.dp,
    override val macro8: Dp = 48.dp,
) : EchoFlowDimens