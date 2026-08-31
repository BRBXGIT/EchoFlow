package com.brbx.onboarding.view_model

import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.view_model.base.OnboardingViewModelDelegate

internal interface PagesSource<T : OnboardingPage> :
    OnboardingViewModelDelegate<T, OnboardingIntent.RefreshPages>