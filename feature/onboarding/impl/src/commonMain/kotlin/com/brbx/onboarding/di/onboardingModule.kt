package com.brbx.onboarding.di

import org.koin.dsl.module

val onboardingModule = module {
    includes(
        viewModelModule,
        onboardingFeatureModule,
    )
}