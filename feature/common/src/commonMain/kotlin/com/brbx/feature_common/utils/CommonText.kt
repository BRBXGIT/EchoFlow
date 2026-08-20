package com.brbx.feature_common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.brbx.design_system.components.utils.safeStringResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

@Immutable
sealed interface CommonText {
    @JvmInline value class Raw(val value: String) : CommonText
    @JvmInline value class Res(val value: StringResource) : CommonText
}

@Composable
fun CommonText.asString(): String =
    when (this) {
        is CommonText.Raw -> value
        is CommonText.Res -> safeStringResource(res = value)
    }

suspend fun CommonText.suspendAsString(): String =
    when (this) {
        is CommonText.Raw -> value
        is CommonText.Res -> getString(resource = value)
    }