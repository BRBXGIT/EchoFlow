package com.brbx.onboarding.view_model

import com.brbx.onboarding.model.OnboardingIntent

internal interface AuthLinkHandler :
    OnboardingDelegate<Any?, OnboardingIntent.OpenAuthLink>
