package com.brbx.onboarding.view_model.base

import com.brbx.feature_common.view_model.EchoFlowMviDelegate
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingState

internal interface OnboardingViewModelDelegate<T, Intent : OnboardingIntent> :
    EchoFlowMviDelegate<OnboardingState<T>, Intent, Unit>
