package com.brbx.onboarding

import com.brbx.onboarding.composable.composableModule
import com.brbx.onboarding.feature.onboardingFeatureModule
import com.brbx.onboarding.view_model.viewModelModule
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect val platformOnboardingModule: Module

val onboardingModule = module {
    includes(
        composableModule,
        viewModelModule,
        onboardingFeatureModule,
        platformOnboardingModule,
    )
}
