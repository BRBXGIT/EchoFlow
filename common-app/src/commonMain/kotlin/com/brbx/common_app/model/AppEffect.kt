package com.brbx.common_app.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface AppEffect {
    data object RemoveAllKeysExceptCurrent : AppEffect
}