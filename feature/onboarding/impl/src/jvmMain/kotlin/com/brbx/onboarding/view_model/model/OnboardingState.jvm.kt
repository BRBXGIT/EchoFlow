package com.brbx.onboarding.view_model.model

internal actual fun createPages(): List<OnboardingPage> =
    buildList {
        add(OnboardingPage.Greeting())
        add(OnboardingPage.Authentication())
    }