package com.brbx.onboarding.feature

import com.brbx.navigation.singleSerializer
import com.brbx.onboarding.OnboardingRoute
import com.brbx.onboarding.composable.OnboardingScaffold
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
internal val onboardingFeatureModule = module {
    singleSerializer<OnboardingRoute> {
        subclass(OnboardingRoute::class, serializer = OnboardingRoute.serializer())
    }
    navigation<OnboardingRoute> {
        OnboardingScaffold()
    }
}