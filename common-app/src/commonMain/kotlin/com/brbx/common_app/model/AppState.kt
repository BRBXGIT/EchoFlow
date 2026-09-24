package com.brbx.common_app.model

import androidx.compose.runtime.Immutable
import com.brbx.domain.model.enums.UserAuthState
import com.brbx.home.HomeRoute
import com.brbx.navigation.EchoFlowNavKey
import com.brbx.onboarding.OnboardingRoute

@Immutable
internal data class AppState(
    val authState: UserAuthState? = null,
) {
    fun startKey(deeplink: String?): EchoFlowNavKey? =
        authState?.let {
            if (authState == UserAuthState.Unauthorized) OnboardingRoute(deeplink) else HomeRoute
        }
}