package com.brbx.onboarding.view_model

import com.brbx.onboarding.model.OnboardingIntent

internal interface PagesSource<T> :
    OnboardingDelegate<T, OnboardingIntent.RefreshPages>
