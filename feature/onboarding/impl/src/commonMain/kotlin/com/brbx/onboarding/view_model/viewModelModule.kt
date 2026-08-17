package com.brbx.onboarding.view_model

import com.brbx.onboarding.view_model.delegate.delegatesModule
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModelOf(constructor = ::OnboardingViewModel)

    includes(delegatesModule)
}