package com.brbx.onboarding

import com.brbx.onboarding.view_model.viewModelModule
import org.koin.dsl.module

val onboardingModule = module {
    includes(viewModelModule)
}