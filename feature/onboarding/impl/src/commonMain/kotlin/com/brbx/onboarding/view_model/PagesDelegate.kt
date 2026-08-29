package com.brbx.onboarding.view_model

import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingPage

internal interface PagesDelegate<T : OnboardingPage> :
    OnboardingViewModelDelegate<T, OnboardingIntent.RefreshPages>