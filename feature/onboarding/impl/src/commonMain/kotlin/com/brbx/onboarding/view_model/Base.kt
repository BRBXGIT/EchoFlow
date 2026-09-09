package com.brbx.onboarding.view_model

import com.brbx.feature_common.view_model.EchoFlowMviDelegate
import com.brbx.feature_common.view_model.EchoFlowMviScope
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingState

internal typealias OnboardingMviScope<T> =
        EchoFlowMviScope<OnboardingState<T>, OnboardingIntent, Unit>

internal typealias OnboardingDelegate<T, Intent> =
        EchoFlowMviDelegate<OnboardingState<T>, Intent, Unit>