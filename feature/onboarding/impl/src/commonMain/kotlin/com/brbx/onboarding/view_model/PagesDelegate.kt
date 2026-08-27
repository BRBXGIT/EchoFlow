package com.brbx.onboarding.view_model

import com.brbx.feature_common.view_model.EchoFlowMviDelegate
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.model.OnboardingState

internal interface PagesDelegate<T : OnboardingPage> :
    EchoFlowMviDelegate<OnboardingState<T>, OnboardingIntent.RefreshPages, Unit>