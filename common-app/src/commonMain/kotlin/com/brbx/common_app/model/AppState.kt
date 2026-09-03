package com.brbx.common_app.model

import androidx.compose.runtime.Immutable
import com.brbx.domain.model.enums.UserAuthState

@Immutable
internal data class AppState(
    val authState: UserAuthState? = null,
)