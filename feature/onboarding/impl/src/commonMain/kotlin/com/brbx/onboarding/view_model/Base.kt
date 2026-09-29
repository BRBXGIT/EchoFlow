package com.brbx.onboarding.view_model

import com.brbx.feature_common.view_model.EchoFlowIntentDelegate
import com.brbx.feature_common.view_model.EchoFlowMviScope
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingState

internal typealias OnboardingMviScope<T> =
        EchoFlowMviScope<OnboardingState<T>, OnboardingIntent, Unit>

internal interface OnboardingDelegate<T, in Intent : OnboardingIntent> :
        EchoFlowIntentDelegate<OnboardingState<T>, Intent, Unit>