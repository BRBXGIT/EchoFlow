package com.brbx.common_app

import androidx.compose.runtime.Immutable
import com.brbx.domain.model.UserAuthState

@Immutable
internal data class AppState(
    val authState: UserAuthState = UserAuthState.Unauthorized,
)
