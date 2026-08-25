package com.brbx.onboarding.composable

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.onboarding.view_model.OnboardingEffect
import com.brbx.onboarding.view_model.OnboardingIntent
import com.brbx.onboarding.view_model.onboarding_page.OnboardingAction
import kotlinx.coroutines.flow.SharedFlow

@Composable
internal actual fun HandleScreenEffects(
    screenEffects: SharedFlow<OnboardingEffect>,
    pagerState: PagerState,
    postEffect: (EchoFlowEffect) -> Unit,
    dispatchIntent: (OnboardingIntent) -> Unit,
) =
    HandleScreenEffectsInternal(screenEffects, pagerState) { action ->
        when (action) {
            is OnboardingAction.Authenticate -> TODO()
            is OnboardingAction.RequestPermission -> Unit
        }
    }
