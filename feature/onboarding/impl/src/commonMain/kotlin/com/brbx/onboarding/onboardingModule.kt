package com.brbx.onboarding

import com.brbx.onboarding.view_model.OnboardingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val onboardingModule = module {
    viewModelOf(constructor = ::OnboardingViewModel)
}