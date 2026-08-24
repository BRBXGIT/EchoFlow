package com.brbx.onboarding.view_model

import androidx.compose.runtime.Stable
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.mvi_core.base.ContainedMviViewModel
import com.brbx.mvi_core.helpers.shareInWhileSubscribed
import com.brbx.mvi_core.helpers.stateInWhileSubscribed
import com.brbx.onboarding.view_model.onboarding_page.PermissionChecker
import kotlinx.coroutines.flow.update

@Stable
internal class OnboardingViewModel(
    private val permissionChecker: PermissionChecker
) : ContainedMviViewModel<OnboardingState, EchoFlowEffect, OnboardingEffect, OnboardingIntent>(
    initialState = OnboardingState(pages = createPages(permissionChecker))
) {

    override val state = _state.stateInWhileSubscribed(
        initialValue = OnboardingState(pages = createPages(permissionChecker))
    )
    override val effects = _effects.shareInWhileSubscribed()
    override val screenEffects = _screenEffects.shareInWhileSubscribed()

    override fun dispatchIntent(intent: OnboardingIntent) {
        when (intent) {
            OnboardingIntent.RefreshPermissions -> refreshPermissions()
        }
    }

    private fun refreshPermissions() {
        _state.update { it.copy(pages = createPages(permissionChecker)) }
    }
}
