package com.brbx.onboarding.view_model

import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.view_model.base.OnboardingViewModelDelegate

internal interface PagesSource<T> :
    OnboardingViewModelDelegate<T, OnboardingIntent.RefreshPages>
