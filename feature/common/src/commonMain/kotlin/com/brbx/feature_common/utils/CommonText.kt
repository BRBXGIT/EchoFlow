package com.brbx.feature_common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

@Immutable
sealed interface CommonText {
    @JvmInline value class Raw(val value: String) : CommonText
    @JvmInline value class Res(val value: StringResource) : CommonText
}

@Composable
fun CommonText.asString(): String =
    when (this) {
        is CommonText.Raw -> value
        is CommonText.Res -> stringResource(resource = value)
    }

suspend fun CommonText.suspendAsString(): String =
    when (this) {
        is CommonText.Raw -> value
        is CommonText.Res -> getString(resource = value)
    }