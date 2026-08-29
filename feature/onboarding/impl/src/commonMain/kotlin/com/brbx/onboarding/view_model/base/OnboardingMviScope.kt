package com.brbx.onboarding.view_model.base

import com.brbx.feature_common.view_model.EchoFlowMviScope
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.model.OnboardingState

internal interface OnboardingMviScope<T : OnboardingPage> :
        EchoFlowMviScope<OnboardingState<T>, OnboardingIntent, Unit>