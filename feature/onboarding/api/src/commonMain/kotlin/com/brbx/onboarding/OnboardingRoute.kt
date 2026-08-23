package com.brbx.onboarding

import com.brbx.navigation.EchoFowNavKey
import com.brbx.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
object OnboardingRoute : EchoFowNavKey

fun Navigator.onboarding() {
    navigate(key = OnboardingRoute)
}